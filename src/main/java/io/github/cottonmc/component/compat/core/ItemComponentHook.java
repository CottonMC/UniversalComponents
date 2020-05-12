package io.github.cottonmc.component.compat.core;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.energy.CapacitorComponent;
import io.github.cottonmc.component.energy.CapacitorComponentHelper;
import io.github.cottonmc.component.fluid.TankComponent;
import io.github.cottonmc.component.fluid.TankComponentHelper;
import io.github.cottonmc.component.item.InventoryComponent;
import io.github.cottonmc.component.item.InventoryComponentHelper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Optional;

public class ItemComponentHook implements InventoryComponentHelper.ItemInventoryHook, TankComponentHelper.ItemTankHook, CapacitorComponentHelper.ItemCapacitorHook {
	private static final ItemComponentHook INSTANCE = new ItemComponentHook();

	public static void initInventory() {
		InventoryComponentHelper.INSTANCE.addItemHook(INSTANCE);
	}

	public static void initTank() {
		TankComponentHelper.INSTANCE.addItemHook(INSTANCE);
	}

	public static void initCap() {
		CapacitorComponentHelper.INSTANCE.addItemHook(INSTANCE);
	}

	public boolean hasInvComponent(ItemStack stack) {
		return UniversalComponents.INVENTORY_COMPONENT.maybeGet(stack).isPresent();
	}

	@Nullable
	public InventoryComponent getInvComponent(ItemStack stack) {
		Optional<InventoryComponent> component = UniversalComponents.INVENTORY_COMPONENT.maybeGet(stack);
		return component.orElse(null);
	}

	@Override
	public boolean hasTankComponent(ItemStack stack) {
		return UniversalComponents.TANK_COMPONENT.maybeGet(stack).isPresent();
	}

	@Nullable
	@Override
	public TankComponent getTankComponent(ItemStack stack) {
		Optional<TankComponent> component = UniversalComponents.TANK_COMPONENT.maybeGet(stack);
		return component.orElse(null);
	}

	@Override
	public boolean hasCapComponent(ItemStack stack) {
		return UniversalComponents.CAPACITOR_COMPONENT.maybeGet(stack).isPresent();
	}

	@Nullable
	@Override
	public CapacitorComponent getCapComponent(ItemStack stack) {
		Optional<CapacitorComponent> component = UniversalComponents.CAPACITOR_COMPONENT.maybeGet(stack);
		return component.orElse(null);
	}

	@Override
	public String getId() {
		return "cardinal-components-item";
	}

	private ItemComponentHook() { }
}
