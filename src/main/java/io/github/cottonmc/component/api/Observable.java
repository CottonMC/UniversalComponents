package io.github.cottonmc.component.api;

import javax.annotation.Nonnull;
import java.util.List;

public interface Observable {
	/**
	 * Alert listeners that the component has been updated.
	 */
	default void onChanged() {
		getListeners().forEach(Runnable::run);
	}

	/**
	 * Add a listener for when {@link Observable#onChanged()} ()} is called.
	 * @param listener A lambda that should run when the component is updated.
	 */
	default void listen(@Nonnull Runnable listener) {
		getListeners().add(listener);
	}

	/**
	 * @return All listeners listening to this component.
	 */
	List<Runnable> getListeners();
}
