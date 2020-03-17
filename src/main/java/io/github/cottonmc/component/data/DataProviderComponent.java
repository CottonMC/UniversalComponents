package io.github.cottonmc.component.data;

import io.github.cottonmc.component.data.api.DataElement;
import nerdhub.cardinal.components.api.component.Component;
import net.minecraft.nbt.CompoundTag;

import java.util.List;

/**
 * Capability representing an object's ability to provide detailed information to some kind of probe or UI element. The
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
	void provideData(List<DataElement> data);

	@Override
	default void fromTag(CompoundTag tag) { }

	@Override
	default CompoundTag toTag(CompoundTag tag) {
		return null;
	}
}
