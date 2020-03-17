package io.github.cottonmc.component.data.impl;

import net.minecraft.client.MinecraftClient;

public class TicksUnit extends SimpleUnit {
	public TicksUnit(String name, int barColor) {
		super(name, "t", barColor);
	}

	public TicksUnit(String name) {
		super(name, "t");
	}

	@Override
	public String format(double ticks) {
		if (ticks == Double.POSITIVE_INFINITY) {
			return "∞";
		} else if (ticks == Double.NEGATIVE_INFINITY) {
			return "-∞";
		} else if (Double.isNaN(ticks)) {
			return "NaN";
		}

		ticks -= MinecraftClient.getInstance().getTickDelta();
		if (ticks < 0) ticks = 0;
		int millisrem = (int)((ticks*50D)%1000D);
		long sec = (long)(ticks/20D);
		int secrem = (int)(sec%60L);
		long min = (long)(ticks/1200L);

		String secstr = (secrem < 10 ? "0" : "")+secrem;
		String millisstr = (millisrem < 100 ? millisrem < 10 ? "00" : "0" : "")+millisrem;

		return min+":"+secstr+"."+millisstr;
	}
}
