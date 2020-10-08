package dev.tr7zw.guicer.implementation;

import dev.tr7zw.guicer.Implementation;

@Implementation(iface = SomeInterface.class)
public class SomeImplementation implements SomeInterface{

	@Override
	public int addValue(int a, int b) {
		return a + b;
	}
	
}