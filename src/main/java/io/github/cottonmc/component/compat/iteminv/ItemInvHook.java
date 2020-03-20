package io.github.cottonmc.component.compat.iteminv;

import dev.emi.iteminventory.api.ItemInventory;
import io.github.cottonmc.component.item.InventoryComponent;
import io.github.cottonmc.component.item.InventoryComponentHelper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ItemInvHook implements InventoryComponentHelper.ItemInventoryHook {
	public static final ItemInvHook INSTANCE = new ItemInvHook();

	public boolean hasComponent(ItemStack stack) {
		return stack.getItem() instanceof ItemInventory;
	}

	@Nullable
	public InventoryComponent getComponent(ItemStack stack) {
		if (stack.getItem() instanceof ItemInventory) {
			return new WrappedItemInventory(stack, (ItemInventory) stack.getItem());
		}
		return null;
	}

	private ItemInvHook() { }
}
