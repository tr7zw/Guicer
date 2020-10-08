package dev.tr7zw.guicer;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Classes using this Annotation will be bound to the defined interface/class.
 * 
 * 
 * @author tr7zw
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface Implementation {
	/**
	 * Target interface to bind to.
	 * 
	 * @return A superclass of the Annotated class
	 */
	Class<?> iface();

	/**
	 * The highest priority gets binded during conflicts.
	 * 
	 * @return The priority, default 0
	 */
	int prio() default 0;
}