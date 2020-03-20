package io.github.cottonmc.component.plugin;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UniversalComponentsMixinPlugin implements IMixinConfigPlugin {
	public static final String PACKAGE = "io.github.cottonmc.component.mixin";

	public static final Map<String, Set<String>> HOOKS;

	@Override
	public void onLoad(String mixinPackage) {
		if (!PACKAGE.equals(mixinPackage)) throw new IllegalArgumentException("Universal Components plugin attempted to apply to foreign package " + mixinPackage);
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		for (String name : HOOKS.keySet()) {
			if (mixinClassName.equals(PACKAGE + "." + name)) {
				for (String string : HOOKS.get(name)) {
					if (!FabricLoader.getInstance().isModLoaded(name)) return false;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

	}

	@Override
	public List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

	}

	static {
		ImmutableMap.Builder<String, Set<String>> builder = ImmutableMap.builder();
		builder.put("vanilla.MixinBlock", Collections.singleton("cardinal-components-block")); //only inject if block components exist
		builder.put("vanilla.MixinHopperBlockEntity", Collections.singleton("cardinal-components-entity")); //only inject if entity components exist
		builder.put("lba.MixinItemAttributesBlock", ImmutableSet.of("cardinal-components-block", "libblockattributes_item")); //only inject if both block components and LBA exist
		builder.put("lba.MixinItemAttributesItem", ImmutableSet.of("cardinal-components-item", "libblockattributes_item")); //only inject if both item components and LBA exist

		HOOKS = builder.build();
	}
}
