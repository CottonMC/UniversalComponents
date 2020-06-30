package io.github.cottonmc.component.mixin.lba;

import alexiil.mc.lib.attributes.ItemAttributeList;
import alexiil.mc.lib.attributes.item.FixedItemInv;
import alexiil.mc.lib.attributes.item.ItemAttributes;
import alexiil.mc.lib.attributes.misc.LimitedConsumer;
import alexiil.mc.lib.attributes.misc.Reference;
import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.api.ComponentHelper;
import io.github.cottonmc.component.compat.lba.AttributeWrapper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(ItemAttributes.class)
public class MixinItemAttributesItem {

	@Inject(method = "appendItemAttributes", at = @At("HEAD"), remap = false)
	private static <T> void injectComponentAdder(Reference<ItemStack> ref, LimitedConsumer<ItemStack> access, ItemAttributeList<T> list, Function<FixedItemInv, T> convertor, CallbackInfo info) {
		ItemStack stack = ref.get();
		if (ComponentHelper.INVENTORY.hasComponent(stack, "lba-items")) {
			list.add(convertor.apply(new AttributeWrapper(UniversalComponents.INVENTORY_COMPONENT.get(stack))));
		}
	}
}
