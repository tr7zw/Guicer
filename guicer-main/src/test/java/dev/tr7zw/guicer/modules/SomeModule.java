package dev.tr7zw.guicer.modules;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import dev.tr7zw.guicer.EnabledModule;

@EnabledModule
public class SomeModule extends AbstractModule{

	@Override
	protected void configure() {
		binder().bind(String.class).annotatedWith(Names.named("somename")).toInstance("abc");
		binder().bind(String.class).annotatedWith(Names.named("othername")).toInstance("xyz");
	}
	
}