package dev.tr7zw.guicer;

import java.lang.annotation.Annotation;
import java.util.function.Consumer;

import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Interface for the Guicer handler
 * 
 * @author tr7zw
 *
 */
public interface InjectorHandler {

	/**
	 * @return The Guice Injector that is managed by Guicer
	 */
	public Injector getInjector();
	
	/**
	 * Adds a module to Guice
	 * 
	 * @param module
	 */
	public void addModule(Module module);
	
	/**
	 * Adds a package to be scanned recursively
	 * 
	 * @param pack
	 */
	public void addPackage(String pack);
	
	/**
	 * Scanns for the added packages for {@link EnabledModule} and {@link Implementation},
	 * bundles it with the added modules and creates the Guice Injector.
	 */
	public void init();
	
	/**
	 * Can be used after init to locate other Annotated classes inside the declared packages.
	 * 
	 * @param ano
	 * @param consumer
	 */
	public void locateClasses(Class<? extends Annotation> ano, Consumer<Object> consumer);
	
	/**
	 * Returns a overview of everything that Guice has in its Bindings. Mainly targeted for debugging.
	 * 
	 * @return
	 */
	public String getBindingOverview();
	
}