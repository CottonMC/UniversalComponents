package io.github.cottonmc.component.mixin.lba;

import alexiil.mc.lib.attributes.AttributeCombiner;
import alexiil.mc.lib.attributes.AttributeSourceType;
import alexiil.mc.lib.attributes.CombinableAttribute;
import alexiil.mc.lib.attributes.item.FixedItemInv;
import alexiil.mc.lib.attributes.item.ItemAttributes;
import io.github.cottonmc.component.api.ComponentHelper;
import io.github.cottonmc.component.compat.lba.AttributeWrapper;
import io.github.cottonmc.component.item.InventoryComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.Function;

@Mixin(ItemAttributes.class)
public class MixinItemAttributesBlock {
	@Inject(method = "create", at = @At("HEAD"), remap = false, locals = LocalCapture.CAPTURE_FAILHARD)
	private static <T> void injectComponentAdder(Class<T> clazz, T defaultValue, AttributeCombiner<T> combiner, Function<FixedItemInv, T> convertor, CallbackInfoReturnable<CombinableAttribute<T>> cir, CombinableAttribute<T> attribute, AttributeSourceType srcType) {
		attribute.appendBlockAdder((world, pos, state, attributeList) -> {
			InventoryComponent component = ComponentHelper.INVENTORY.getComponent(world, pos, attributeList.getSearchDirection() == null ? null : attributeList.getSearchDirection().getOpposite(), "lba-items");
			if (component != null) {
				attributeList.add(convertor.apply(new AttributeWrapper(component)));
			}
		});
	}
}
