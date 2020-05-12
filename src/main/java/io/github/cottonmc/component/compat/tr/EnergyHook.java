package io.github.cottonmc.component.compat.tr;

import io.github.cottonmc.component.api.ComponentHelper;
import io.github.cottonmc.component.energy.CapacitorComponent;
import io.github.cottonmc.component.energy.CapacitorComponentHelper;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import team.reborn.energy.Energy;

import javax.annotation.Nullable;

public class EnergyHook implements CapacitorComponentHelper.DualCapacitorHook {
	public static final EnergyHook INSTANCE = new EnergyHook();

	public static void init() {
		CapacitorComponentHelper.INSTANCE.addDualHook(INSTANCE);
		Energy.registerHolder(object -> {
			if (object instanceof BlockEntity) {
				BlockEntity be = (BlockEntity)object;
				return ComponentHelper.CAPACITOR.hasComponent(be.getWorld(), be.getPos(), null, "reborn-energy");
			} else if (object instanceof ItemStack) {
				return ComponentHelper.CAPACITOR.hasComponent((ItemStack)object, "reborn-energy");
			}
			return false;
		}, object -> {
			if (object instanceof BlockEntity) {
				BlockEntity be = (BlockEntity) object;
				CapacitorComponent comp = ComponentHelper.CAPACITOR.getComponent(be.getWorld(), be.getPos(), null, "reborn-energy");
				if (comp != null) new EnergyStorageWrapper(comp);
			} else if (object instanceof ItemStack) {
				CapacitorComponent comp = ComponentHelper.CAPACITOR.getComponent((ItemStack)object, "reborn-energy");
				if (comp != null) return new EnergyStorageWrapper(comp);
			}
			return null;
		});
	}

	@Override
	public boolean hasCapComponent(BlockView world, BlockPos pos, @Nullable Direction dir) {
		BlockEntity be = world.getBlockEntity(pos);
		if (be != null) return Energy.valid(be);
		return false;
	}

	@Nullable
	@Override
	public CapacitorComponent getCapComponent(BlockView world, BlockPos pos, @Nullable Direction dir) {
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

	@Override
	public String getId() {
		return "reborn-energy";
	}

	private EnergyHook() { }
}
