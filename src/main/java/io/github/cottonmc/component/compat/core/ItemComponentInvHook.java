package io.github.cottonmc.component.compat.core;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.item.InventoryComponent;
import io.github.cottonmc.component.item.InventoryComponentHelper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Optional;

public class ItemComponentInvHook implements InventoryComponentHelper.ItemInventoryHook {
	public static final ItemComponentInvHook INSTANCE = new ItemComponentInvHook();

	public boolean hasComponent(ItemStack stack) {
		return UniversalComponents.INVENTORY_COMPONENT.maybeGet(stack).isPresent();
	}

	@Nullable
	public InventoryComponent getComponent(ItemStack stack) {
		Optional<InventoryComponent> component = UniversalComponents.INVENTORY_COMPONENT.maybeGet(stack);
		return component.orElse(null);
	}

	private ItemComponentInvHook() { }
}
