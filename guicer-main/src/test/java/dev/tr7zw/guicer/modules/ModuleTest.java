package dev.tr7zw.guicer.modules;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import dev.tr7zw.guicer.Bootstrap;
import junit.framework.TestCase;

public class ModuleTest extends TestCase{

	@Inject
	@Named("somename")
	String a;
	
	@Inject
	@Named("othername")
	String b;
	
	public void testImplementation() {
		Bootstrap.setupGuice(this);
		assertEquals("abc", a);
		assertEquals("xyz", b);
	}
	
}