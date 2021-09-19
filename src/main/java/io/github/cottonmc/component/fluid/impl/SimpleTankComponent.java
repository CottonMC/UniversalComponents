package io.github.cottonmc.component.fluid.impl;

import io.github.cottonmc.component.api.ActionType;
import io.github.cottonmc.component.fluid.TankComponent;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.FluidVolume;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.Fraction;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.collection.DefaultedList;

public class SimpleTankComponent implements TankComponent {
	protected DefaultedList<FluidVolume> contents;
	private final List<Runnable> listeners = new ArrayList<>();
	private Fraction maxCapacity;

	public SimpleTankComponent(int size, Fraction maxCapacity) {
		contents = DefaultedList.ofSize(size, FluidVolume.EMPTY);
		this.maxCapacity = maxCapacity;
	}

	@Override
	public int getTanks() {
		return contents.size();
	}

	@Override
	public Fraction getMaxCapacity(int tank) {
		return maxCapacity;
	}

	@Override
	public List<FluidVolume> getAllContents() {
		List<FluidVolume> ret = new ArrayList<>();
		for (FluidVolume vol : contents) {
			ret.add(vol.copy());
		}
		return ret;
	}

	@Override
	public FluidVolume getContents(int slot) {
		return contents.get(slot).copy();
	}

	@Override
	public boolean canInsert(int slot) {
		return true;
	}

	@Override
	public boolean canExtract(int slot) {
		return true;
	}

	@Override
	public FluidVolume removeFluid(int slot, Fraction amount, ActionType action) {
		FluidVolume vol = contents.get(slot);
		if (!action.shouldPerform()) {
			vol = vol.copy();
		} else {
			onChanged();
		}
		return vol.split(amount);
	}

	@Override
	public FluidVolume removeFluid(int slot, ActionType action) {
		FluidVolume vol = contents.get(slot);
		if (action.shouldPerform()) {
			setFluid(slot, FluidVolume.EMPTY);
			onChanged();
		}
		return vol;
	}

	@Override
	public void setFluid(int slot, FluidVolume stack) {
		contents.set(slot, stack);
		onChanged();
	}

	@Override
	public FluidVolume insertFluid(int tank, FluidVolume fluid, ActionType action) {
		FluidVolume target = contents.get(tank);

		if (!target.isEmpty() && !FluidVolume.areFluidsEqual(fluid, target))  { //TODO: NBT comparison eventually
			//unstackable, can't merge!
			return fluid;
		}
		Fraction count = target.getAmount();
		Fraction maxSize = getMaxCapacity(tank);
		if (count.equals(getMaxCapacity(tank))) {
			//target stack is already full, can't merge!
			return fluid;
		}
		Fraction sizeLeft = maxSize.subtract(count);
		if (sizeLeft.compareTo(fluid.getAmount()) >= 0) {
			//the target stack can accept our whole stack!
			if (action.shouldPerform()) {
				if (target.isEmpty()) {
					setFluid(tank, fluid);
				} else {
					target.increment(fluid.getAmount());
				}
				onChanged();
			}
			return FluidVolume.EMPTY;
		} else {
			//the target can't accept our whole stack, we're gonna have a remainder
			if (action.shouldPerform()) {
				if (target.isEmpty()) {
					FluidVolume newVol = fluid.copy();
					newVol.setAmount(maxSize);
					setFluid(tank, newVol);
				} else {
					target.setAmount(maxSize);
				}
				onChanged();
			}
			fluid.decrement(sizeLeft);
			return fluid;
		}
	}

	@Override
	public FluidVolume insertFluid(FluidVolume fluid, ActionType action) {
		for (int i = 0; i < contents.size(); i++) {
			fluid = insertFluid(i, fluid, action);
			if (fluid.isEmpty()) return fluid;
		}
		return fluid;
	}

	@Override
	public List<Runnable> getListeners() {
		return listeners;
	}
}
