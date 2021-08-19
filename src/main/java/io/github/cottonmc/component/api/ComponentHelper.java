package io.github.cottonmc.component.api;

import io.github.cottonmc.component.energy.CapacitorComponent;
import io.github.cottonmc.component.energy.CapacitorComponentHelper;
import io.github.cottonmc.component.fluid.TankComponent;
import io.github.cottonmc.component.fluid.TankComponentHelper;
import io.github.cottonmc.component.item.InventoryComponent;
import io.github.cottonmc.component.item.InventoryComponentHelper;
import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Helpers for converting between different systems of item, fluid, and energy transfer.
 * @param <T> The type of component this helps get.
 */
public interface ComponentHelper<T extends Component> {
	ComponentHelper<InventoryComponent> INVENTORY = InventoryComponentHelper.INSTANCE;
	ComponentHelper<TankComponent> TANK = TankComponentHelper.INSTANCE;
	ComponentHelper<CapacitorComponent> CAPACITOR = CapacitorComponentHelper.INSTANCE;

	/**
	 * Query whether a block has a compatible component.
	 * @param world The world the block is in.
	 * @param pos The position the block is at.
	 * @param direction The direction to access the component from, or null.
	 * @return Whether this block has a component we can access.
	 */
	default boolean hasComponent(BlockView world, BlockPos pos, @Nullable Direction direction) {
		return hasComponent(world, pos, direction, "");
	}

	/**
	 * Query whether a block has a compatible component, used from inside other hooks.
	 * @param world The world the block is in.
	 * @param pos The position the block is at.
	 * @param direction The direction to access the component from, or null.
	 * @param ignore The ID of the hook calling this, to prevent infinite loops.
	 * @return Whether this block has a component we can access.
	 */
	boolean hasComponent(BlockView world, BlockPos pos, @Nullable Direction direction, String ignore);
	
	/**
	 * Get a compatible component on a block.
	 * @param world The world the block is in.
	 * @param pos The position the block is at.
	 * @param direction The direction to access the component from, or null.
	 * @return The component on this block, or null if it doesn't exist or is incompatible.
	 */
	default T getComponent(BlockView world, BlockPos pos, @Nullable Direction direction) {
		return getComponent(world, pos, direction, "");
	}
	
	/**
	 * Get a compatible component on a block, used from inside other hooks.
	 * @param world The world the block is in.
	 * @param pos The position the block is at.
	 * @param direction The direction to access the component from, or null.
	 * @param ignore The ID of the hook calling this, to prevent infinite loops.
	 * @return The component on this block, or null if it doesn't exist or is incompatible.
	 */
	T getComponent(BlockView world, BlockPos pos, @Nullable Direction direction, String ignore);

	/**
	 * Query whether a block or entity in a block space has a compatible component.
	 * @param world The world the block is in.
	 * @param pos The position the block is at.
	 * @param direction The direction to access the component from, or null.
	 * @return Whether this block has a component we can access.
	 */
	default boolean hasExtendedComponent(World world, BlockPos pos, @Nullable Direction direction) {
		return hasExtendedComponent(world, pos, direction, "");
	}

	/**
	 * Query whether a block or entity in a block space has a compatible component, used from inside other hooks.
	 * @param world The world the block is in.
	 * @param pos The position the block is at.
	 * @param direction The direction to access the component from, or null.
	 * @param ignore The ID of the hook calling this, to prevent infinite loops.
	 * @return Whether this block has a component we can access.
	 */
	boolean hasExtendedComponent(World world, BlockPos pos, @Nullable Direction direction, String ignore);
	
	/**
	 * Get a compatible component on a block or entity in a block space.
	 * @param world The world the block is in.
	 * @param pos The position the block is at.
	 * @param direction The direction to access the component from, or null.
	 * @return The component on this block, or null if it doesn't exist or is incompatible.
	 */
	default T getExtendedComponent(World world, BlockPos pos, @Nullable Direction direction) {
		return getExtendedComponent(world, pos, direction, "");
	}

	/**
	 * Get a compatible component on a block or entity in a block space, used from inside other hooks.
	 * @param world The world the block is in.
	 * @param pos The position the block is at.
	 * @param direction The direction to access the component from, or null.
	 * @param ignore The ID of the hook calling this, to prevent infinite loops.
	 * @return The component on this block, or null if it doesn't exist or is incompatible.
	 */
	T getExtendedComponent(World world, BlockPos pos, @Nullable Direction direction, String ignore);

	/**
	 * Query whether a stack has a compatible component.
	 * @param stack The stack to check on.
	 * @return Whether a this stack has a component we can access.
	 */
	default boolean hasComponent(ItemStack stack) {
		return hasComponent(stack, "");
	}

	/**
	 * Query whether a stack has a compatible component, used from inside other hooks.
	 * @param stack The stack to check on.
	 * @param ignore The ID of the hook calling this, to prevent infinite loops.
	 * @return Whether a this stack has a component we can access.
	 */
	boolean hasComponent(ItemStack stack, String ignore);

	/**
	 * Get a compatible component on a stack.
	 * @param stack The stack to check on.
	 * @return The component on this stack, or null if it doesn't exist or is incompatible.
	 */
	default T getComponent(ItemStack stack) {
		return getComponent(stack, "");
	}

	/**
	 * Get a compatible component on a stack, used from inside other hooks.
	 * @param stack The stack to check on.
	 * @param ignore The ID of the hook calling this, to prevent infinite loops.
	 * @return The component on this stack, or null if it doesn't exist or is incompatible.
	 */
	T getComponent(ItemStack stack, String ignore);
}
