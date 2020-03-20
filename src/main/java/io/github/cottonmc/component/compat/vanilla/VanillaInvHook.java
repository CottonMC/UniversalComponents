package io.github.cottonmc.component.compat.vanilla;

import io.github.cottonmc.component.item.InventoryComponent;
import io.github.cottonmc.component.item.InventoryComponentHelper;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class VanillaInvHook implements InventoryComponentHelper.BlockInventoryHook {
	public static final VanillaInvHook INSTANCE = new VanillaInvHook();

	@Override
	public boolean hasComponent(World world, BlockPos pos, @Nullable Direction dir) {
		return HopperBlockEntity.getInventoryAt(world, pos) != null;
	}

	@Nullable
	@Override
	public InventoryComponent getComponent(World world, BlockPos pos, @Nullable Direction dir) {
		Inventory inv = HopperBlockEntity.getInventoryAt(world, pos);
		if (inv instanceof SidedInventory) {
			return new WrappedSidedInvComponent((SidedInventory)inv, dir);
		}
		if (inv != null) {
			return new WrappedInvComponent(inv);
		}
		return null;
	}

	private VanillaInvHook() { }
}
