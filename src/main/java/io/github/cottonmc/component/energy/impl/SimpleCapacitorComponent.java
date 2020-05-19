package io.github.cottonmc.component.energy.impl;

import io.github.cottonmc.component.api.ActionType;
import io.github.cottonmc.component.energy.CapacitorComponent;
import io.github.cottonmc.component.energy.type.EnergyType;
import io.github.cottonmc.component.energy.type.EnergyTypes;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleCapacitorComponent implements CapacitorComponent {
	protected EnergyType energyType;
	protected int maxEnergy;
	protected int currentEnergy = 0;
	protected int harm = 0;
	protected final List<Runnable> listeners = new ArrayList<>();

	public SimpleCapacitorComponent(int maxEnergy, EnergyType type) {
		this.maxEnergy = maxEnergy;
		this.energyType = type;
	}

	public SimpleCapacitorComponent setCurrentEnergy(int amount) {
		int prev = currentEnergy;
		if (amount <= maxEnergy) currentEnergy = amount;
		else currentEnergy = maxEnergy;
		if (currentEnergy != prev) onChanged();
		return this;
	}

	public SimpleCapacitorComponent setMaxEnergy(int amount) {
		int prev = maxEnergy;
		maxEnergy = amount;
		if (currentEnergy > maxEnergy) currentEnergy = maxEnergy;
		if (maxEnergy != prev) onChanged();
		return this;
	}

	@Override
	public int getHarm() {
		return harm;
	}

	public void resetHarm() {
		this.harm = 0;
		onChanged();
	}

	@Override
	public int getMaxEnergy() {
		return maxEnergy;
	}

	@Override
	public int getCurrentEnergy() {
		return currentEnergy;
	}

	@Override
	public boolean canInsertEnergy() {
		return true;
	}

	@Override
	public boolean canExtractEnergy() {
		return true;
	}

	@Override
	public int insertEnergy(EnergyType type, int amount, ActionType actionType) {
		Optional<Integer> converted = EnergyType.convert(type, amount, this.energyType);
		if (!converted.isPresent()) return amount; //converted is now in our locally-understood EnergyType.

		int insertAmount = converted.get();
		if (insertAmount < this.energyType.getMinimumTransferSize()) return amount; //Don't accept a transfer below the granularity.
		if (insertAmount > this.energyType.getMaximumTransferSize()) insertAmount = this.energyType.getMaximumTransferSize();

		int insertRoom = maxEnergy - currentEnergy;
		if (insertAmount > insertRoom) insertAmount = insertRoom;

		if (actionType == ActionType.PERFORM) {
			currentEnergy += insertAmount;

			if (this.energyType.isHarmful(type)) {
				harm += insertAmount;
			}

			if (insertAmount != 0) onChanged();
		}

		return amount - EnergyType.convert(this.energyType, insertAmount, type).orElse(0); //Result is now in the *requested* EnergyType
	}

	@Override
	public int extractEnergy(EnergyType type, int amount, ActionType actionType) {
		Optional<Integer> converted = EnergyType.convert(type, amount, this.energyType);
		if (!converted.isPresent()) return 0; //converted is now in our locally-understood EnergyType.

		int extractAmount = converted.get();
		if (extractAmount < this.energyType.getMinimumTransferSize()) return 0; //Don't accept a transfer below the granularity.
		if (extractAmount > this.energyType.getMaximumTransferSize()) extractAmount = this.energyType.getMaximumTransferSize();

		if (extractAmount > currentEnergy) extractAmount = currentEnergy;

		if (actionType == ActionType.PERFORM) {
			currentEnergy -= extractAmount;
			if (extractAmount != 0) onChanged();
		}
		return EnergyType.convert(this.energyType, extractAmount, type).orElse(0); //Result is now in the *requested* EnergyType
	}

	@Nonnull
	@Override
	public EnergyType getPreferredType() {
		return energyType;
	}

	@Override
	public List<Runnable> getListeners() {
		return listeners;
	}

	@Override
	public void fromTag(CompoundTag tag) {
		currentEnergy = tag.getInt("Energy");
		maxEnergy = tag.getInt("MaxEnergy");
		if (tag.contains("Harm", NbtType.NUMBER)) {
			harm = tag.getInt("Harm");
		}
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		tag.putInt("Energy", currentEnergy);
		tag.putInt("MaxEnergy", maxEnergy);
		tag.putInt("Harm", harm);
		return tag;
	}
}
