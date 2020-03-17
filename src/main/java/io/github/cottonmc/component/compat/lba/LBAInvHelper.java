package io.github.cottonmc.component.compat.lba;

import alexiil.mc.lib.attributes.SearchOptions;
import alexiil.mc.lib.attributes.item.FixedItemInv;
import alexiil.mc.lib.attributes.item.ItemAttributes;
import alexiil.mc.lib.attributes.item.impl.EmptyFixedItemInv;
import io.github.cottonmc.component.item.InventoryComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LBAInvHelper {
	public static boolean hasInvAttribute(World world, BlockPos pos, @Nullable Direction dir) {
		if (dir == null) return ItemAttributes.FIXED_INV.get(world, pos) != EmptyFixedItemInv.INSTANCE;
		return ItemAttributes.FIXED_INV.get(world, pos, SearchOptions.inDirection(dir)) != EmptyFixedItemInv.INSTANCE;
	}

	@Nullable
	public static InventoryComponent getWrappedInvAttribute(World world, BlockPos pos, @Nullable Direction dir) {
		FixedItemInv inv = dir == null? ItemAttributes.FIXED_INV.get(world, pos) : ItemAttributes.FIXED_INV.get(world, pos, SearchOptions.inDirection(dir));
		if (inv == EmptyFixedItemInv.INSTANCE) return null;
		return new WrappedInvAttributeComponent(inv);
	}

	public static boolean hasInvAttribute(ItemStack stack) {
		return ItemAttributes.FIXED_INV.get(stack) != EmptyFixedItemInv.INSTANCE;
	}

	public static InventoryComponent getWrappedInvAttribute(ItemStack stack) {
		FixedItemInv inv = ItemAttributes.FIXED_INV.get(stack);
		if (inv == EmptyFixedItemInv.INSTANCE) return null;
		return new WrappedInvAttributeComponent(inv);
	}

	private LBAInvHelper() { }
}
