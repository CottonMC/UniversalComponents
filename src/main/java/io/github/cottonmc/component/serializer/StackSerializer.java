package io.github.cottonmc.component.serializer;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class StackSerializer {
	//allows int amounts
	public static CompoundTag toTag(ItemStack stack, CompoundTag tag) {
		Identifier identifier = Registry.ITEM.getId(stack.getItem());
		tag.putString("id", identifier == null ? "minecraft:air" : identifier.toString());
		tag.putInt("Count", stack.getCount());
		if (stack.getTag() != null) {
			tag.put("tag", stack.getTag().copy());
		}

		return tag;
	}

	//allows int amounts
	public static ItemStack fromTag(CompoundTag tag) {
		Item item = Registry.ITEM.get(new Identifier(tag.getString("id")));
		int count = tag.getInt("Count");
		ItemStack ret = new ItemStack(item, count);
		if (tag.contains("tag", 10)) {
			ret.setTag(tag.getCompound("tag"));
		}

		return ret;
	}
}
