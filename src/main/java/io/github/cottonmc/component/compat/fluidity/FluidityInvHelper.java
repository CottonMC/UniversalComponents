package io.github.cottonmc.component.compat.fluidity;

import io.github.cottonmc.component.item.InventoryComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

//TODO: implement
public class FluidityInvHelper {
	public static boolean hasInvDevice(IWorld world, BlockPos pos, @Nullable Direction dir) {
		return false;
	}

	@Nullable
	public static InventoryComponent getWrappedInvDevice(IWorld world, BlockPos pos, @Nullable Direction dir) {
		return null;
	}

	private FluidityInvHelper() { }
}
