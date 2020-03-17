package io.github.cottonmc.component.data.impl;

import com.google.common.collect.ImmutableList;
import io.github.cottonmc.component.data.api.DataElement;
import io.github.cottonmc.component.data.api.Unit;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class SimpleDataElement implements DataElement {
	private Text label = null;
	private double barMin = Double.NaN;
	private double barCur = Double.NaN;
	private double barMax = Double.NaN;
	private Unit barUnit = null;
	private ImmutableList<ItemStack> inventory = null;

	/**
	 * Creates a blank data line
	 */
	public SimpleDataElement() {}

	/**
	 * Creates a data line with text information
	 * @param label the information to be displayed on this line
	 */
	public SimpleDataElement(String label) {
		this.label = new LiteralText(label);
	}

	/**
	 * Creates a data line with text information
	 * @param label the information to be displayed on this line
	 */
	public SimpleDataElement(Text label) {
		this.label = label;
	}

	/**
	 * Adds an label to this SimpleDataElement
	 * @param label the text to add to this SimpleDataElement
	 * @return this SimpleDataElement
	 */
	public SimpleDataElement withLabel(String label) {
		this.label = new LiteralText(label);
		return this;
	}

	/**
	 * Adds an label to this SimpleDataElement
	 * @param label the text to add to this SimpleDataElement
	 * @return this SimpleDataElement
	 */
	public SimpleDataElement withLabel(Text label) {
		this.label = label;
		return this;
	}

	/**
	 * Adds a bar to this SimpleDataElement
	 * @param minimum The lowest possible value for the bar
	 * @param current The current value of the bar. Must be between minimum and maximum, inclusive
	 * @param maximum The highest possible value for the bar
	 * @param unit The unit that the quantities in the bar are expressed in. Use an empty string to specify no units.
	 * @return this SimpleDataElement
	 */
	public SimpleDataElement withBar(double minimum, double current, double maximum, Unit unit) {
		this.barMin = minimum;
		this.barCur = current;
		this.barMax = maximum;
		this.barUnit = unit;
		return this;
	}

	/**
	 * Adds an inventory to this SimpleDataElement
	 * @param inventory the contents of all itemslots, full or empty, in this datum.
	 * @return this SimpleDataElement
	 */
	public SimpleDataElement withInventory(@Nonnull ImmutableList<ItemStack> inventory) {
		this.inventory = inventory;
		return this;
	}

	@Override
	public boolean hasLabel() {
		return label!=null;
	}

	@Override
	public boolean hasBar() {
		return !Double.isNaN(barMin) && !Double.isNaN(barCur) && !Double.isNaN(barMax);
	}

	@Override
	@Nonnull
	public Text getLabel() {
		return label!=null ? label : new LiteralText("");
	}

	@Override
	public double getBarMinimum() {
		return barMin;
	}

	@Override
	public double getBarCurrent() {
		return barCur;
	}

	@Override
	public double getBarMaximum() {
		return barMax;
	}

	@Override
	@Nullable
	public Unit getBarUnit() {
		return barUnit;
	}

	@Override
	public boolean hasInventory() {
		return inventory!=null;
	}

	@Override
	@Nullable
	public ImmutableList<ItemStack> getInventory() {
		return inventory;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(barCur);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(barMax);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(barMin);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((barUnit == null) ? 0 : barUnit.hashCode());
		result = prime * result + ((inventory == null) ? 0 : stackListHashCode(inventory));
		try {
			result = prime * result + ((label == null) ? 0 : label.hashCode());
		} catch (NullPointerException e) {
			// Some TextComponent implementations have broken hashCode methods
			// Ignore Mojang's quality code
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SimpleDataElement other = (SimpleDataElement) obj;
		if (Double.doubleToLongBits(barCur) != Double
				.doubleToLongBits(other.barCur)) {
			return false;
		}
		if (Double.doubleToLongBits(barMax) != Double
				.doubleToLongBits(other.barMax)) {
			return false;
		}
		if (Double.doubleToLongBits(barMin) != Double
				.doubleToLongBits(other.barMin)) {
			return false;
		}
		if (barUnit == null) {
			if (other.barUnit != null) {
				return false;
			}
		} else if (!barUnit.equals(other.barUnit)) {
			return false;
		}
		if (inventory == null) {
			if (other.inventory != null) {
				return false;
			}
		} else if (!stackListsEqual(inventory, other.inventory)) {
			return false;
		}
		if (label == null) {
			if (other.label != null) {
				return false;
			}
		} else if (!label.equals(other.label)) {
			return false;
		}
		return true;
	}

	private static int stackListHashCode(List<ItemStack> li) {
		int result = 1;
		for (ItemStack is : li) {
			result = 31 * result + (is == null ? 0 : stackHashCode(is));
		}
		return result;
	}

	private static int stackHashCode(ItemStack stack) {
		if (stack == null || stack.isEmpty()) return 0;
		final int prime = 31;
		int result = 1;
		result = prime * result + stack.getItem().hashCode();
		result = prime * result + stack.getCount();
		result = prime * result + (stack.hasTag() ? stack.getTag().hashCode() : 0);
		return result;
	}

	private static boolean stackListsEqual(List<ItemStack> a, List<ItemStack> b) {
		if (a == b) return true;
		if (a == null || b == null) return false;
		if (a.size() != b.size()) return false;
		for (int i = 0; i < a.size(); i++) {
			ItemStack isa = a.get(i);
			ItemStack isb = b.get(i);
			if (!ItemStack.areEqualIgnoreDamage(isa, isb)) {
				return false;
			}
		}
		return true;
	}
}
