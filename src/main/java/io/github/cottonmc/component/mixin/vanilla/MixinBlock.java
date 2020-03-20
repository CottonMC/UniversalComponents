package io.github.cottonmc.component.mixin.vanilla;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.compat.vanilla.SidedInventoryWrapper;
import io.github.cottonmc.component.item.InventoryComponent;
import nerdhub.cardinal.components.api.component.BlockComponentProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
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
		return null;
	}
}
