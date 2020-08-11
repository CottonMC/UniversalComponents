package io.github.cottonmc.component.mixin.vanilla;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.compat.core.EntityComponentHook;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(HopperBlockEntity.class)
public abstract class MixinHopperBlockEntity extends BlockEntity {

	public MixinHopperBlockEntity(BlockEntityType<?> type) {
		super(type);
	}

	//should only search for entities, since that's all we care about at this point in injection
	@Inject(method = "getInventoryAt(Lnet/minecraft/world/World;DDD)Lnet/minecraft/inventory/Inventory;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getOtherEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;)Ljava/util/List;"), cancellable = true)
	private static void injectInventoryComponents(World world, double x, double y, double z, CallbackInfoReturnable<Inventory> info) {
		List<Entity> list = world.getOtherEntities(null, new Box(x - 0.5D, y - 0.5D, z - 0.5D, x + 0.5D, y + 0.5D, z + 0.5D), EntityComponentHook.HAS_INV_COMPONENT);
		if (!list.isEmpty()) {
			info.setReturnValue(UniversalComponents.INVENTORY_COMPONENT.get(list.get(world.random.nextInt(list.size()))).asInventory());
		}
	}

}
