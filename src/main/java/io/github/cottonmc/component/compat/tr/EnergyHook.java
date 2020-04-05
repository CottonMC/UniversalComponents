package io.github.cottonmc.component.compat.tr;

import io.github.cottonmc.component.energy.CapacitorComponent;
import io.github.cottonmc.component.energy.CapacitorComponentHelper;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import team.reborn.energy.Energy;

import javax.annotation.Nullable;

public class EnergyHook implements CapacitorComponentHelper.DualCapacitorHook {
	public static final EnergyHook INSTANCE = new EnergyHook();

	public static void init() {
		CapacitorComponentHelper.addDualHook(INSTANCE);
		Energy.registerHolder(object -> {
			if (object instanceof BlockEntity) {
				BlockEntity be = (BlockEntity)object;
				return CapacitorComponentHelper.hasCapacitorComponent(be.getWorld(), be.getPos(), null);
			} else if (object instanceof ItemStack) {
				return CapacitorComponentHelper.hasCapacitorComponent((ItemStack)object);
			}
			return false;
		}, object -> {
			if (object instanceof BlockEntity) {
				BlockEntity be = (BlockEntity) object;
				CapacitorComponent comp = CapacitorComponentHelper.getCapacitorComponent(be.getWorld(), be.getPos(), null);
				if (comp != null) new EnergyStorageWrapper(comp);
			} else if (object instanceof ItemStack) {
				CapacitorComponent comp = CapacitorComponentHelper.getCapacitorComponent((ItemStack)object);
				if (comp != null) return new EnergyStorageWrapper(comp);
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
