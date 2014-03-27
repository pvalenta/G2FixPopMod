package hk.valenta.g2fixpopmod;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Fixer implements IXposedHookLoadPackage {

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		// capture LGE tangible
		if (!lpparam.packageName.equals("com.lge.tangible")) return;
		
		// hook it
		findAndHookMethod("com.lge.tangible.core.ScreenshotLayout", lpparam.classLoader, "getScreenShot", boolean.class, new XC_MethodHook() {

			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				// no more bottom navigation bar
				param.args[0] = false;
				
				// continue
				super.beforeHookedMethod(param);
			}
		}); 
		
		try {		
			// hook another method
			findAndHookMethod("com.lge.tangible.core.TangibleManager", lpparam.classLoader, "isExceptionalState", new XC_MethodReplacement() {
				
				@Override
				protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
					// return always FALSE
					return false;
				}
			});
	
			// hook another method
			findAndHookMethod("com.lge.tangible.core.TangibleManager", lpparam.classLoader, "isExceptionalScene", new XC_MethodReplacement() {
				
				@Override
				protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
					// return always FALSE
					return false;
				}
			});
		} catch (Exception e) { }		
	}
}
