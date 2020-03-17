package io.github.cottonmc.component.api;

/**
 * The type of action to be taken when interacting with a resource component.
 */
public enum ActionType {
	/**
	 * Test what would happen if this action was performed. Will not modify the resource store.
	 */
	TEST,
	/**
	 * Perform the action. Will modify the resource store.
	 */
	EXECUTE;

	public boolean shouldExecute() {
		return this == EXECUTE;
	}
}
