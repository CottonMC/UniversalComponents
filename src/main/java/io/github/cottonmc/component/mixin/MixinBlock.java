package io.github.cottonmc.component.mixin;

import io.github.cottonmc.component.CommonComponents;
import io.github.cottonmc.component.item.impl.SimpleSidedInventoryWrapper;
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
		if (provider.hasComponent(world, pos, CommonComponents.INVENTORY_COMPONENT, null)) {
			return new SimpleSidedInventoryWrapper(world, pos, state);
		}
		return null;
	}
}
