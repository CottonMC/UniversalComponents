package io.github.cottonmc.component.compat.lba;

import io.github.cottonmc.component.item.InventoryComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

//TODO: implement
public class LBAInvHelper {
	public static boolean hasInvAttribute(IWorld world, BlockPos pos, @Nullable Direction dir) {
		return false;
	}

	@Nullable
	public static InventoryComponent getWrappedInvAttribute(IWorld world, BlockPos pos, @Nullable Direction dir) {
		return null;
	}

	private LBAInvHelper() { }
}
