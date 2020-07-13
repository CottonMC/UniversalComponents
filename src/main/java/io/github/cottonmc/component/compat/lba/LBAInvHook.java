package io.github.cottonmc.component.compat.lba;

import alexiil.mc.lib.attributes.SearchOptions;
import alexiil.mc.lib.attributes.item.FixedItemInv;
import alexiil.mc.lib.attributes.item.ItemAttributes;
import alexiil.mc.lib.attributes.item.impl.EmptyFixedItemInv;
import io.github.cottonmc.component.item.InventoryComponent;
import io.github.cottonmc.component.item.InventoryComponentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LBAInvHook implements InventoryComponentHelper.DualInventoryHook {
	private static final LBAInvHook INSTANCE = new LBAInvHook();

	public static void initInv() {
		InventoryComponentHelper.INSTANCE.addDualHook(INSTANCE);
	}

	@Override
	public boolean hasInvComponent(BlockView world, BlockPos pos, @Nullable Direction dir) {
		if (world instanceof World) {
			if (dir == null) return ItemAttributes.FIXED_INV.get((World)world, pos) != EmptyFixedItemInv.INSTANCE;
			return ItemAttributes.FIXED_INV.get((World)world, pos, SearchOptions.inDirection(dir)) != EmptyFixedItemInv.INSTANCE;
		}
		return false;
	}

	@Override
	@Nullable
	public InventoryComponent getInvComponent(BlockView world, BlockPos pos, @Nullable Direction dir) {
		if (world instanceof World) {
			FixedItemInv inv = dir == null ? ItemAttributes.FIXED_INV.get((World)world, pos) : ItemAttributes.FIXED_INV.get((World)world, pos, SearchOptions.inDirection(dir));
			if (inv == EmptyFixedItemInv.INSTANCE) return null;
			return new WrappedInvAttributeComponent(inv);
		}
		return null;
	}

	@Override
	public boolean hasInvComponent(ItemStack stack) {
		return ItemAttributes.FIXED_INV.get(stack) != EmptyFixedItemInv.INSTANCE;
	}

	@Override
	@Nullable
	public InventoryComponent getInvComponent(ItemStack stack) {
		FixedItemInv inv = ItemAttributes.FIXED_INV.get(stack);
		if (inv == EmptyFixedItemInv.INSTANCE) return null;
		return new WrappedInvAttributeComponent(inv);
	}

	@Override
	public String getId() {
		return "lba-items";
	}

	private LBAInvHook() { }
}
