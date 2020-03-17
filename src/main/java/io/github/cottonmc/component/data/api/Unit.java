package io.github.cottonmc.component.data.api;

/**
 * Represents information about a quantity being expressed. Nominally, the unit of measurement, but also the kind of
 * thing being measured.
 */
public interface Unit {
	public String getFullName();
	public String getAbbreviation();
	/**
	 * @return a color to represent this unit, in srgb "00RRGGBB" format
	 */
	public int getBarColor();
	/**
	 * Take a quantity expressed in this unit, and produce a human-readable display, such as 200.0D becoming "200 kFU"
	 * @param d the quantity being represented
	 * @return A full String expressing the number and abbreviated unit, with any SI prefixing needed.
	 */
	public String format(double d);
}
