package io.github.cottonmc.component.item;

import dev.emi.iteminventory.api.ItemInventory;
import io.github.cottonmc.component.CommonComponents;
import io.github.cottonmc.component.compat.fluidity.FluidityInvHelper;
import io.github.cottonmc.component.compat.iteminv.WrappedItemInventory;
import io.github.cottonmc.component.compat.lba.LBAInvHelper;
import io.github.cottonmc.component.compat.vanilla.WrappedInvComponent;
import io.github.cottonmc.component.compat.vanilla.WrappedSidedInvComponent;
import nerdhub.cardinal.components.api.component.BlockComponentProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class InventoryComponentHelper {
	/**
	 * Query whether a block has a compatible inventory component.
	 * @param world The world the block is in.
	 * @param pos The position the block is at.
	 * @param dir The direction to access the inventory from, or null.
	 * @return Whether this block has an inventory we can access.
	 */
	public static boolean hasInventoryComponent(World world, BlockPos pos, @Nullable Direction dir) {
		BlockState state = world.getBlockState(pos);
		BlockComponentProvider provider = BlockComponentProvider.get(state);
		//check for a full-fledged component
		if (provider.hasComponent(world, pos, CommonComponents.INVENTORY_COMPONENT, dir)) {
			return true;
		}
		//no component, so check for vanilla inventories
		//TODO: this adds support for chest/hopper carts as well, do folks want that by default?
		if (HopperBlockEntity.getInventoryAt(world, pos) != null) return true;
//		//no component, so check for an inventory provider
//		if (block instanceof InventoryProvider) {
//			if (((InventoryProvider)block).getInventory(state, world, pos) != null) return true;
//		}
//		//no inventory provider, so check for a block entity that implements Inventory
//		if (block.hasBlockEntity()) {
//			if (world.getBlockEntity(pos) instanceof Inventory) return true;
//		}
		//no block entity inventory, so check for LBA item attributes
		if (FabricLoader.getInstance().isModLoaded("libblockattributes_items")) {
			if (LBAInvHelper.hasInvAttribute(world, pos, dir)) return true;
		}
		//no LBA item attributes, so check for a Fluidity inv store
		if (FabricLoader.getInstance().isModLoaded("fluidity")) {
			if (FluidityInvHelper.hasInvStorage(world, pos, dir)) return true;
		}
		//nothing else to check, no inventory here
		return false;
	}

	/**
	 * Get a compatible inventory component on a block.
	 * @param world The world the block is in.
	 * @param pos The position the block is at.
	 * @param dir The direction to access the inventory from, or null.
	 * @return The inventory component on this block, or null if it doesn't exist or is incompatible.
	 */
	@Nullable
	public static InventoryComponent getInvComponent(World world, BlockPos pos, @Nullable Direction dir) {
		BlockState state = world.getBlockState(pos);
		BlockComponentProvider provider = BlockComponentProvider.get(state);
		if (provider.hasComponent(world, pos, CommonComponents.INVENTORY_COMPONENT, dir)) {
			return provider.getComponent(world, pos, CommonComponents.INVENTORY_COMPONENT, dir);
		}
		Inventory inv = HopperBlockEntity.getInventoryAt(world, pos);
		if (inv instanceof SidedInventory) {
			return new WrappedSidedInvComponent((SidedInventory)inv, dir);
		}
		if (inv != null) {
			return new WrappedInvComponent(inv);
		}
//		if (block instanceof InventoryProvider) {
//			SidedInventory inv = ((InventoryProvider)block).getInventory(state, world, pos);
//			if (inv != null) return new WrappedSidedInvComponent(inv, dir);
//		}
//		if (block.hasBlockEntity()) {
//			BlockEntity be = world.getBlockEntity(pos);
//			if (be instanceof SidedInventory) {
//				return new WrappedSidedInvComponent((SidedInventory)be, dir);
//			}
//			if (be instanceof Inventory) {
//				return new WrappedInvComponent((Inventory)be);
//			}
//		}
		if (FabricLoader.getInstance().isModLoaded("libblockattributes_items")) {
			InventoryComponent component = LBAInvHelper.getWrappedInvAttribute(world, pos, dir);
			if (component != null) return component;
		}
		if (FabricLoader.getInstance().isModLoaded("fluidity")) {
			InventoryComponent component = FluidityInvHelper.getWrappedInvStorage(world, pos, dir);
			if (component != null) return component;
		}
		return null;
	}

	/**
	 * Query whether a stack has a compatible inventory component.
	 * @param stack The stack to check on.
	 * @return Whether a this stack has an inventory we can access.
	 */
	public static boolean hasInventoryComponent(ItemStack stack) {
		//check for a full-fledged component
		if (CommonComponents.INVENTORY_COMPONENT.maybeGet(stack).isPresent()) return true;
		//no full component, so check for an ItemInventory
		if (stack.getItem() instanceof ItemInventory) return true;
		//no ItemInventory, so check for LBA item attributes
		if (FabricLoader.getInstance().isModLoaded("libblockattributes_item")) {
			if (LBAInvHelper.hasInvAttribute(stack)) return true;
		}
		//no LBA item attributes, so check for fluidity storage
		if (FluidityInvHelper.hasInvStorage(stack)) return true;
		//nothing else to check, no inventory here
		return false;
	}

	/**
	 * Get a compatible inventory component on a stack.
	 * @param stack The stack to check on.
	 * @return The inventory component on this stack, or null if it doesn't exist or is incompatible.
	 */
	@Nullable
	public static InventoryComponent getInventoryComponent(ItemStack stack) {
		if (CommonComponents.INVENTORY_COMPONENT.maybeGet(stack).isPresent()) {
			return CommonComponents.INVENTORY_COMPONENT.get(stack);
		}
		if (stack.getItem() instanceof ItemInventory) {
			return new WrappedItemInventory(stack, (ItemInventory)stack.getItem());
		}
		if (FabricLoader.getInstance().isModLoaded("libblockattributes_item")) {
			InventoryComponent component = LBAInvHelper.getWrappedInvAttribute(stack);
			if (component != null) return component;
		}
		if (FabricLoader.getInstance().isModLoaded("libblockattributes_item")) {
			InventoryComponent component = FluidityInvHelper.getWrappedInvStorage(stack);
			if (component != null) return component;
		}
		return null;
	}

	private InventoryComponentHelper() { }
}
