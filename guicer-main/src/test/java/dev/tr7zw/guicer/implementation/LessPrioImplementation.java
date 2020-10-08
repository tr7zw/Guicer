package dev.tr7zw.guicer.implementation;

import dev.tr7zw.guicer.Implementation;

@Implementation(iface = SomeInterface.class, prio = -1) // Any prio >= 0 will fail the test because then this
														// implementation would be used
public class LessPrioImplementation implements SomeInterface {

	@Override
	public int addValue(int a, int b) {
		return 0;
	}

}