package io.github.cottonmc.component.mixin.vanilla;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.compat.vanilla.InvBEWrapper;
import io.github.cottonmc.component.compat.vanilla.SidedInventoryWrapper;
import io.github.cottonmc.component.item.InventoryComponent;
import nerdhub.cardinal.components.api.component.BlockComponentProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class MixinBlock implements InventoryProvider {

	@Override
	public SidedInventory getInventory(BlockState state, IWorld world, BlockPos pos) {
		BlockComponentProvider provider = BlockComponentProvider.get(state);
		if (provider.hasComponent(world, pos, UniversalComponents.INVENTORY_COMPONENT, null)) {
			InventoryComponent comp = provider.getComponent(world, pos, UniversalComponents.INVENTORY_COMPONENT, null);
			if (comp.asLocalInventory(world, pos) != null) return comp.asLocalInventory(world, pos);
			return SidedInventoryWrapper.of(side -> provider.getComponent(world, pos, UniversalComponents.INVENTORY_COMPONENT, side));
		}
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
