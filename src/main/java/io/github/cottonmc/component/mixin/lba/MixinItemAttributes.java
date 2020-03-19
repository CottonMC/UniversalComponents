package io.github.cottonmc.component.mixin.lba;

import alexiil.mc.lib.attributes.CustomAttributeAdder;
import alexiil.mc.lib.attributes.DefaultedAttribute;
import alexiil.mc.lib.attributes.ItemAttributeList;
import alexiil.mc.lib.attributes.item.FixedItemInv;
import alexiil.mc.lib.attributes.item.ItemAttributes;
import alexiil.mc.lib.attributes.item.compat.FixedInventoryVanillaWrapper;
import alexiil.mc.lib.attributes.item.compat.FixedSidedInventoryVanillaWrapper;
import alexiil.mc.lib.attributes.misc.LimitedConsumer;
import alexiil.mc.lib.attributes.misc.Reference;
import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.compat.lba.AttributeWrapper;
import io.github.cottonmc.component.item.InventoryComponent;
import nerdhub.cardinal.components.api.component.BlockComponentProvider;
import net.minecraft.block.Block;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(ItemAttributes.class)
public class MixinItemAttributes {

	//TODO: better way to do this without replacing the whole thing?
	@Inject(method = "createBlockAdder", at = @At("HEAD"), cancellable = true, remap = false)
	private static <T> void injectComponentAdder(Function<FixedItemInv, T> convertor, CallbackInfoReturnable<CustomAttributeAdder<T>> info) {
		info.setReturnValue((world, pos, state, list) -> {
			Block block = state.getBlock();
			Direction direction = list.getSearchDirection();
			Direction blockSide = direction == null ? null : direction.getOpposite();
			BlockComponentProvider componentProvider = BlockComponentProvider.get(block);
			SidedInventory sidedInv;
			FixedItemInv wrapper;
			if (componentProvider.hasComponent(world, pos, UniversalComponents.INVENTORY_COMPONENT, blockSide)) {
				InventoryComponent component = componentProvider.getComponent(world, pos, UniversalComponents.INVENTORY_COMPONENT, blockSide);
				list.add(convertor.apply(new AttributeWrapper(component)));
			} else if (block instanceof InventoryProvider) {
				InventoryProvider provider = (InventoryProvider)block;
				sidedInv = provider.getInventory(state, world, pos);
				if (sidedInv != null) {
					if (sidedInv.getInvSize() > 0) {
						if (direction != null) {
							wrapper = FixedSidedInventoryVanillaWrapper.create(sidedInv, blockSide);
						} else {
							wrapper = new FixedInventoryVanillaWrapper(sidedInv);
						}

						list.add(convertor.apply(wrapper));
					} else {
						list.add(((DefaultedAttribute<T>)list.attribute).defaultValue);
					}
				}
			} else if (block.hasBlockEntity()) {
				BlockEntity be = world.getBlockEntity(pos);
				if (be instanceof ChestBlockEntity && state.getBlock() instanceof ChestBlock) {
					boolean checkForBlockingCats = false;
					Inventory chestInv = ChestBlock.getInventory((ChestBlock)state.getBlock(), state, world, pos, checkForBlockingCats);
					if (chestInv != null) {
						list.add(convertor.apply(new FixedInventoryVanillaWrapper(chestInv)));
					}
				} else if (be instanceof SidedInventory) {
					sidedInv = (SidedInventory)be;
					if (direction != null) {
						wrapper = FixedSidedInventoryVanillaWrapper.create(sidedInv, blockSide);
					} else {
						wrapper = new FixedInventoryVanillaWrapper(sidedInv);
					}

					list.add(convertor.apply(wrapper));
				} else if (be instanceof Inventory) {
					list.add(convertor.apply(new FixedInventoryVanillaWrapper((Inventory)be)));
				}
			}

		});
	}

	@Inject(method = "appendItemAttributes", at = @At("HEAD"), remap = false)
	private static <T> void injectComponentAdder(Reference<ItemStack> ref, LimitedConsumer<ItemStack> access, ItemAttributeList<T> list, Function<FixedItemInv, T> convertor, CallbackInfo info) {
		ItemStack stack = ref.get();
		if (UniversalComponents.INVENTORY_COMPONENT.maybeGet(stack).isPresent()) {
			list.add(convertor.apply(new AttributeWrapper(UniversalComponents.INVENTORY_COMPONENT.get(stack))));
		}
	}
}
