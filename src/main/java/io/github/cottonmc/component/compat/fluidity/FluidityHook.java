package io.github.cottonmc.component.compat.fluidity;

import io.github.cottonmc.component.item.InventoryComponent;
import io.github.cottonmc.component.item.InventoryComponentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import javax.annotation.Nullable;

//TODO: implement
public class FluidityHook implements InventoryComponentHelper.DualInventoryHook {
	private static final FluidityHook INSTANCE = new FluidityHook();

	public static void initInv() {
		InventoryComponentHelper.addDualHook(INSTANCE);
	}

	public boolean hasInvComponent(World world, BlockPos pos, @Nullable Direction dir) {
		return false;
	}

	@Nullable
	public InventoryComponent getInvComponent(World world, BlockPos pos, @Nullable Direction dir) {
		return null;
	}

	public boolean hasInvComponent(ItemStack stack) {
		return false;
	}

	@Nullable
	public InventoryComponent getInvComponent(ItemStack stack) {
		return null;
	}

	@Override
	public String getId() {
		return "fluidity";
	}

	private FluidityHook() { }
}
