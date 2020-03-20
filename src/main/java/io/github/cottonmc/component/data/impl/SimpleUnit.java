package io.github.cottonmc.component.data.impl;

import io.github.cottonmc.component.data.api.Unit;

import java.text.NumberFormat;

public class SimpleUnit implements Unit {
	public static final NumberFormat FORMAT_STANDARD = NumberFormat.getNumberInstance();
	static {
		FORMAT_STANDARD.setMinimumFractionDigits(0);
		FORMAT_STANDARD.setMaximumFractionDigits(2);
	}

	private final String name;
	private final String abbreviation;
	private final int barColor;
	protected final NumberFormat format;
	protected final boolean spaceAfterNumber;

	public SimpleUnit(String name, String abbreviation) {
		this(name, abbreviation, 0xAAAAAA, FORMAT_STANDARD, true);
	}

	public SimpleUnit(String name, String abbreviation, int barColor) {
		this(name, abbreviation, barColor, FORMAT_STANDARD, true);
	}

	public SimpleUnit(String name, String abbreviation, int barColor, NumberFormat nfmt, boolean spaceAfterNumber) {
		this.name = name;
		this.abbreviation = abbreviation;
		this.barColor = barColor;
		this.format = nfmt;
		this.spaceAfterNumber = spaceAfterNumber;
	}

	@Override
	public String getFullName() {
		return name;
	}

	@Override
	public String getAbbreviation() {
		return abbreviation;
	}

	@Override
	public int getBarColor() {
		return barColor;
	}

	@Override
	public String format(double d) {
		String space = (spaceAfterNumber) ? " " : "";
		if (d == Double.POSITIVE_INFINITY) {
			return "∞"+space+getAbbreviation();
		} else if (d == Double.NEGATIVE_INFINITY) {
			return "-∞"+space+getAbbreviation();
		} else if (Double.isNaN(d)) {
			return "NaN"+space+getAbbreviation();
		}
		return format.format(d)+space+getAbbreviation();
	}
}
