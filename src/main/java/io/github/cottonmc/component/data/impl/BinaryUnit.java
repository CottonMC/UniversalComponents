package io.github.cottonmc.component.data.impl;

import java.text.NumberFormat;

/**
 * A Unit that uses binary prefixes to format numbers, turning things like
 * "1200" into "1.17Ki"
 */
public class BinaryUnit extends SimpleUnit {
	public BinaryUnit(String name, String abbreviation, int barColor, NumberFormat nfmt, boolean spaceAfterNumber) {
		super(name, abbreviation, barColor, nfmt, spaceAfterNumber);
	}

	public BinaryUnit(String name, String abbreviation, int barColor) {
		super(name, abbreviation, barColor);
	}

	public BinaryUnit(String name, String abbreviation) {
		super(name, abbreviation);
	}


	private static final double KIBI = 1_024D;
	private static final double MEBI = KIBI*1024D;
	private static final double GIBI = MEBI*1024D;
	private static final double TEBI = GIBI*1024D;
	private static final double PEBI = TEBI*1024D;
	private static final double EXBI = PEBI*1024D;
	private static final double ZEBI = EXBI*1024D;
	private static final double YOBI = ZEBI*1024D;
	private static final double HEBI = YOBI*1024D;

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

		double magnitude = Math.abs(d);

		if (magnitude>HEBI) {
			return format.format(d/HEBI)+space+"Xi"+getAbbreviation();
		} else if (magnitude>YOBI) {
			return format.format(d/YOBI)+space+"Yi"+getAbbreviation();
		} else if (magnitude>ZEBI) {
			return format.format(d/ZEBI)+space+"Zi"+getAbbreviation();
		} else if (magnitude>EXBI) {
			return format.format(d/EXBI)+space+"Ei"+getAbbreviation();
		} else if (magnitude>PEBI) {
			return format.format(d/PEBI)+space+"Pi"+getAbbreviation();
		} else if (magnitude>TEBI) {
			return format.format(d/TEBI)+space+"Ti"+getAbbreviation();
		} else if (magnitude>GIBI) {
			return format.format(d/GIBI)+space+"Gi"+getAbbreviation();
		} else if (magnitude>MEBI) {
			return format.format(d/MEBI)+space+"Mi"+getAbbreviation();
		} else if (magnitude>KIBI) {
			return format.format(d/KIBI)+space+"Ki"+getAbbreviation();
		} else {
			return format.format(d)+space+getAbbreviation();
		}
	}
}
