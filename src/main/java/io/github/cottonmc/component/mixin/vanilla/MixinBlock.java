package io.github.cottonmc.component.mixin.vanilla;

import io.github.cottonmc.component.api.ComponentHelper;
import io.github.cottonmc.component.compat.vanilla.InvBEWrapper;
import io.github.cottonmc.component.compat.vanilla.SidedInventoryWrapper;
import io.github.cottonmc.component.item.InventoryComponent;
import io.github.cottonmc.component.item.InventoryComponentHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class MixinBlock implements InventoryProvider {

	//TODO: should this logic just go in MixinHopperBlockEntity instead? Does this even actually work?
	@Override
	public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
		if (ComponentHelper.INVENTORY.hasComponent(world, pos, null, "minecraft")) {
			InventoryComponent comp = ComponentHelper.INVENTORY.getComponent(world, pos, null, "minecraft");
			if (comp.asLocalInventory(world, pos) != null) return comp.asLocalInventory(world, pos);
			return SidedInventoryWrapper.of(side -> ComponentHelper.INVENTORY.getComponent(world, pos, side, "minecraft"));
		}
		//necessary due to our forcing everything to have an inv provider
		BlockEntity be = world.getBlockEntity(pos);
		if (be instanceof SidedInventory) {
			return (SidedInventory)be;
		} else if (be instanceof Inventory) {
			Inventory inv = (Inventory)be;
			if (inv instanceof ChestBlockEntity && state.getBlock() instanceof ChestBlock) {
				inv = ChestBlock.getInventory((ChestBlock)state.getBlock(), state, (World)world, pos, true);
			}
			return new InvBEWrapper(inv);
		}
		return null;
	}
}
