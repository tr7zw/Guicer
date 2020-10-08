package dev.tr7zw.guicer;

public class Bootstrap {

	public static void setupGuice(Object target) {
		if(Guicer.getHandler() == null) {
			Guicer.setupInstance(new GuicerInjector());
			Guicer.getHandler().addPackage("dev.tr7zw.guicer");
			Guicer.getHandler().init();
		}
		Guicer.getInjector().injectMembers(target);
	}
	
}