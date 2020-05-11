package io.github.cottonmc.component.mixin.vanilla;

import io.github.cottonmc.component.compat.vanilla.SidedInventoryWrapper;
import io.github.cottonmc.component.item.InventoryComponent;
import io.github.cottonmc.component.item.InventoryComponentHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class MixinBlock implements InventoryProvider {

	@Override
	public SidedInventory getInventory(BlockState state, IWorld world, BlockPos pos) {
		if (InventoryComponentHelper.hasInventoryComponent((World)world, pos, null, "minecraft")) {
			InventoryComponent comp = InventoryComponentHelper.getInventoryComponent((World)world, pos, null, "minecraft");
			if (comp.asLocalInventory(world, pos) != null) return comp.asLocalInventory(world, pos);
			return SidedInventoryWrapper.of(side -> InventoryComponentHelper.getInventoryComponent((World)world, pos, side, "minecraft"));
		}
		//TODO: is this a good idea to have here?
//		BlockEntity be = world.getBlockEntity(pos);
//		if (be instanceof SidedInventory) {
//			return (SidedInventory)be;
//		} else if (be instanceof Inventory) {
//			Inventory inv = (Inventory)be;
//			if (inv instanceof ChestBlockEntity && state.getBlock() instanceof ChestBlock) {
//				inv = ChestBlock.getInventory((ChestBlock)state.getBlock(), state, (World)world, pos, true);
//			}
//			return new InvBEWrapper(inv);
//		}
		return null;
	}
}
