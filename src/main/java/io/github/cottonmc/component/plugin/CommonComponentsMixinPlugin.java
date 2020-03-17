package io.github.cottonmc.component.plugin;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommonComponentsMixinPlugin implements IMixinConfigPlugin {
	public static final String PACKAGE = "io.github.cottonmc.component.mixin";

	public static final Map<String, String> HOOKS;

	@Override
	public void onLoad(String mixinPackage) {
		if (!PACKAGE.equals(mixinPackage)) throw new IllegalArgumentException(mixinPackage);
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		for (String name : HOOKS.keySet()) {
			if (mixinClassName.equals(PACKAGE+"."+name)) {
				return FabricLoader.getInstance().isModLoaded(HOOKS.get(name));
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
		ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
		builder.put("lba.MixinItemAttributes", "libblockattributes_item");

		HOOKS = builder.build();
	}
}
