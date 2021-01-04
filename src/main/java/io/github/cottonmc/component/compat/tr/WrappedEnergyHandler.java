package io.github.cottonmc.component.compat.tr;

import io.github.cottonmc.component.api.ActionType;
import io.github.cottonmc.component.energy.CapacitorComponent;
import io.github.cottonmc.component.energy.type.EnergyType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.Direction;
import team.reborn.energy.EnergyHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class WrappedEnergyHandler implements CapacitorComponent {
	private Supplier<EnergyHandler> handler;
	private Direction side = null;

	public WrappedEnergyHandler(Supplier<EnergyHandler> handler) {
		this.handler = handler;
	}

	public WrappedEnergyHandler(Supplier<EnergyHandler> handler, @Nullable Direction side) {
		this.handler = handler;
		this.side = side;
	}

	@Override
	public int getMaxEnergy() {
		return (int)handler.get().getMaxStored();
	}

	@Override
	public int getCurrentEnergy() {
		return (int)handler.get().getEnergy();
	}

	@Override
	public boolean canInsertEnergy() {
		return handler.get().getMaxInput() != 0;
	}

	@Override
	public int insertEnergy(EnergyType type, int amount, ActionType action) {
		EnergyHandler energy = handler.get();
		energy.side(side);
		if (!action.shouldPerform()) energy.simulate();
		return (int)energy.insert(amount);
	}

	@Override
	public boolean canExtractEnergy() {
		return handler.get().getMaxOutput() != 0;
	}

	@Override
	public int extractEnergy(EnergyType type, int amount, ActionType action) {
		EnergyHandler energy = handler.get();
		energy.side(side);
		if (!action.shouldPerform()) energy.simulate();
		return (int)energy.extract(amount);
	}

	@Nonnull
	@Override
	public EnergyType getPreferredType() {
		//TODO: figure out how to impl
		return RebornEnergyTypes.INFINITE_TIER;
	}

	@Override
	public int getHarm() {
		return 0;
	}

	@Override
	public List<Runnable> getListeners() {
		return null;
	}

	@Override
	public void readFromNbt(CompoundTag tag) {

	}

	@Override
	public void writeToNbt(CompoundTag tag) {
	}
}
