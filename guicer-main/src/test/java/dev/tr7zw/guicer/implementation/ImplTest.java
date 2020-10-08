package dev.tr7zw.guicer.implementation;

import com.google.inject.Inject;

import dev.tr7zw.guicer.Bootstrap;
import junit.framework.TestCase;

public class ImplTest extends TestCase{

	@Inject
	SomeInterface someInterface;
	
	@Inject
	SomeInterface someInterface2;
	
	public void testImplementation() {
		Bootstrap.setupGuice(this);
		assertEquals(6, someInterface.addValue(5, 1));
	}
	
}