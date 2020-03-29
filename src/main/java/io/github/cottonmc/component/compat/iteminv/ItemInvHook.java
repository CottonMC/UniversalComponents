package io.github.cottonmc.component.compat.iteminv;

import dev.emi.iteminventory.api.ItemInventory;
import io.github.cottonmc.component.item.InventoryComponent;
import io.github.cottonmc.component.item.InventoryComponentHelper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ItemInvHook implements InventoryComponentHelper.ItemInventoryHook {
	private static final ItemInvHook INSTANCE = new ItemInvHook();

	public static ItemInvHook getInstance() {
		return INSTANCE;
	}

	public boolean hasInvComponent(ItemStack stack) {
		return stack.getItem() instanceof ItemInventory;
	}

	@Nullable
	public InventoryComponent getInvComponent(ItemStack stack) {
		if (stack.getItem() instanceof ItemInventory) {
			return new WrappedItemInventory(stack, (ItemInventory) stack.getItem());
		}
		return null;
	}

	private ItemInvHook() { }
}
