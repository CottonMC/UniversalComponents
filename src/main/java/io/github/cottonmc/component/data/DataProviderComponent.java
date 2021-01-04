package io.github.cottonmc.component.data;

import io.github.cottonmc.component.data.api.DataElement;
import io.github.cottonmc.component.data.api.Unit;
import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Component representing an object's ability to provide detailed information to some kind of probe or UI element. The
 * simple case is something like WAILA, HWYLA, or TheOneProbe. However, this API could be used by snap-on monitor
 * blocks, remote monitoring systems, or data-based automation. Picture this: "activate redstone when bar labeled
 * 'temperature' is greater than 80%". Structured data is very useful data.
 *
 * <p>This interface addresses how you get the data. What you do with it is up to you.
 *
 * <p>Probes and other devices should gather data only on the server Side. Implementors are encouraged to ignore
 * clientside probe requests.
 */
public interface DataProviderComponent extends Component {
	/**
	 * Append all data to the provided list.
	 * @param data The list of data to append to.
	 */
	void provideData(List<DataElement> data);

	/**
	 * Get a data element of a specific unit, for easier interaction.
	 * @param unit The unit this element should be in.
	 * @return The data element for this unit type.
	 */
	@Nullable
	DataElement getElementFor(Unit unit);

	@Override
	default void readFromNbt(CompoundTag tag) { }

	@Override
	default void writeToNbt(CompoundTag tag) { }
}
