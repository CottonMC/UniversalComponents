package io.github.cottonmc.component.compat.lba;

public class LBAInvHook {

	public static LBAInvHookImpl getInstance() {
		return LBAInvHookImpl.getInstance();
	}

	private LBAInvHook() { }
}
