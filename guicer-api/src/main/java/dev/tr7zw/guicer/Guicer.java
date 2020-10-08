package dev.tr7zw.guicer;

import com.google.inject.Injector;

/**
 * Main entry point to setup and use Guicer.
 * 
 * @author tr7zw
 *
 */
public class Guicer {

	private static InjectorHandler instance;
	
	/**
	 * Sets up Guicer using the given implementation.
	 * 
	 * @param handler Implementation to use
	 */
	public static void setupInstance(InjectorHandler handler) {
		if(handler == null) {
			throw new NullPointerException();
		}
		if(instance != null) {
			throw new RuntimeException("Guicer handler instance is already created!");
		}
		instance = handler;
	}
	
	/**
	 * @return The Guicer handler
	 */
	public static InjectorHandler getHandler() {
		return instance;
	}
	
	/**
	 * @return The Guice Injector that is managed by Guicer
	 */
	public static Injector getInjector() {
		return instance.getInjector();
	}
	
}