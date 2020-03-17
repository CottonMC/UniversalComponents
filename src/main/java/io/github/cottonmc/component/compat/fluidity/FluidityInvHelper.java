package io.github.cottonmc.component.compat.fluidity;

import io.github.cottonmc.component.item.InventoryComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

//TODO: implement
public class FluidityInvHelper {
	public static boolean hasInvStorage(IWorld world, BlockPos pos, @Nullable Direction dir) {
		return false;
	}

	@Nullable
	public static InventoryComponent getWrappedInvStorage(IWorld world, BlockPos pos, @Nullable Direction dir) {
		return null;
	}

	//TODO: impl
	public static boolean hasInvStorage(ItemStack stack) {
		return false;
	}

	//TODO: impl
	public static InventoryComponent getWrappedInvStorage(ItemStack stack) {
		return null;
	}

	private FluidityInvHelper() { }
}
