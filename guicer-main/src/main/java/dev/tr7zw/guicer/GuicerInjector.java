package dev.tr7zw.guicer;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

import org.reflections8.Reflections;
import org.reflections8.scanners.SubTypesScanner;
import org.reflections8.scanners.TypeAnnotationsScanner;
import org.reflections8.util.ClasspathHelper;
import org.reflections8.util.ConfigurationBuilder;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.binder.AnnotatedBindingBuilder;

import lombok.Getter;
import lombok.NonNull;

/**
 * Default Injector implementation of Guicer
 * 
 * @author tr7zw
 *
 */
public final class GuicerInjector implements InjectorHandler {

	@Getter
	private Injector injector;
	private List<Module> modules = new ArrayList<>();
	private List<String> packages = new ArrayList<>();
	private Reflections reflections;

	public void addModule(@NonNull Module module) {
		if (injector != null)
			throw new IllegalStateException("Injector is already initialized!");
		modules.add(module);
	}

	public void addPackage(@NonNull String pack) {
		if (injector != null)
			throw new IllegalStateException("Injector is already initialized!");
		packages.add(pack);
	}

	public void init() {
		if (injector != null)
			throw new IllegalStateException("Injector is already initialized!");

		Map<Class<?>, Integer> prioMap = new HashMap<Class<?>, Integer>();
		final Map<Class<?>, Class<?>> bindingMap = new HashMap<Class<?>, Class<?>>();
		ConfigurationBuilder conf = new ConfigurationBuilder()
				.forPackages(packages.toArray(new String[0]))
				.setUrls(effectiveClassPathUrls(ClasspathHelper.contextClassLoader()))
				.setScanners(new SubTypesScanner(), new TypeAnnotationsScanner());
		reflections = new Reflections(conf);
		
		Set<Class<?>> foundModules = reflections.getTypesAnnotatedWith(EnabledModule.class);
		for(Class<?> cl : foundModules) {
			if(Module.class.isAssignableFrom(cl)) {
				try {
					modules.add((Module) cl.getDeclaredConstructor().newInstance());
				}catch(Exception e) {
					throw new RuntimeException("Error creating module instance of " + cl.getName(), e);
				}
			}else {
				throw new RuntimeException("The class " + cl.getName() + " has the EnabledModules Annotation, but is not a valid module!");
			}
		}
		
		Set<Class<?>> implementations = reflections.getTypesAnnotatedWith(Implementation.class);
		for (Class<?> cl : implementations) {
			Implementation ano = cl.getAnnotation(Implementation.class);
			if (!prioMap.containsKey(ano.iface()) || (prioMap.get(ano.iface()) < ano.prio())) {
				prioMap.put(ano.iface(), ano.prio());
				bindingMap.put(ano.iface(), cl);
			}
		}

		modules.add(new AbstractModule() {
			@Override
			protected void configure() {
				for (Entry<Class<?>, Class<?>> entry : bindingMap.entrySet()) {
					AnnotatedBindingBuilder<?> binder = bind(entry.getKey());
					try {
						binder.getClass().getMethod("to", Class.class).invoke(binder, entry.getValue());
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
							| NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
					}
				}
			}
		});

		try {
			injector = Guice.createInjector(modules);
		}catch(Throwable e) { // Throwable instead of Exception to catch some nasty to debug exceptions inside Guice's logger
			throw new RuntimeException("Error while creating Guice instance!", e);
		}
		
	}
	
	
	/**
	 * Bypasses some issues with running junit tests in maven.
	 * https://stackoverflow.com/questions/13576665/
	 * 
	 * @param classLoaders
	 * @return
	 */
	private static Collection<URL> effectiveClassPathUrls(ClassLoader... classLoaders) {
	    return ClasspathHelper.forManifest(ClasspathHelper.forClassLoader(classLoaders));
	}

	public void locateClasses(@NonNull Class<? extends Annotation> ano, @NonNull Consumer<Object> consumer) {
		Set<Class<?>> annotedClasses = reflections.getTypesAnnotatedWith(ano);
		for (Class<?> cl : annotedClasses) {
			try {
				consumer.accept(injector.getInstance(cl));
			} catch (Throwable ex) {
				ex.printStackTrace();
				throw new RuntimeException();
			}
		}
	}

	@Override
	public String getBindingOverview() {
		return injector.getAllBindings().toString().replace("}, ", "}, \n");
	}

}