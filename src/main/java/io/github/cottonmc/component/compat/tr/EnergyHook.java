package io.github.cottonmc.component.compat.tr;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.energy.CapacitorComponent;
import io.github.cottonmc.component.energy.CapacitorComponentHelper;
import nerdhub.cardinal.components.api.component.BlockComponentProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import team.reborn.energy.Energy;

import javax.annotation.Nullable;
import java.util.Optional;

public class EnergyHook implements CapacitorComponentHelper.DualCapacitorHook {
	public static final EnergyHook INSTANCE = new EnergyHook();

	public static void init() {
		CapacitorComponentHelper.addDualHook(INSTANCE);
		Energy.registerHolder(object -> {
			if (object instanceof BlockEntity) {
				BlockEntity be = (BlockEntity)object;
				return BlockComponentProvider.get(be.getCachedState()).hasComponent(be.getWorld(), be.getPos(), UniversalComponents.CAPACITOR_COMPONENT, null);
			} else if (object instanceof ItemStack) {
				return UniversalComponents.CAPACITOR_COMPONENT.maybeGet(object).isPresent();
			}
			return false;
		}, object -> {
			if (object instanceof BlockEntity) {
				BlockEntity be = (BlockEntity) object;
				CapacitorComponent comp = BlockComponentProvider.get(be.getCachedState()).getComponent(be.getWorld(), be.getPos(), UniversalComponents.CAPACITOR_COMPONENT, null);
				if (comp != null) new EnergyStorageWrapper(comp);
			} else if (object instanceof ItemStack) {
				Optional<CapacitorComponent> comp = UniversalComponents.CAPACITOR_COMPONENT.maybeGet(object);
				if (comp.isPresent()) return new EnergyStorageWrapper(comp.get());
			}
			return null;
		});
	}

	@Override
	public boolean hasCapComponent(World world, BlockPos pos, @Nullable Direction dir) {
		BlockEntity be = world.getBlockEntity(pos);
		if (be != null) return Energy.valid(be);
		return false;
	}

	@Nullable
	@Override
	public CapacitorComponent getCapComponent(World world, BlockPos pos, @Nullable Direction dir) {
		BlockEntity be = world.getBlockEntity(pos);
		if (be != null) {
			return new WrappedEnergyHandler(() -> Energy.of(be), dir);
		}
		return null;
	}

	@Override
	public boolean hasCapComponent(ItemStack stack) {
		return Energy.valid(stack);
	}

	@Nullable
	@Override
	public CapacitorComponent getCapComponent(ItemStack stack) {
		return new WrappedEnergyHandler(() -> Energy.of(stack));
	}
}
