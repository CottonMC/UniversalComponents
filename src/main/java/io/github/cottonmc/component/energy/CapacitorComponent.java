package io.github.cottonmc.component.energy;

import io.github.cottonmc.component.api.ActionType;
import io.github.cottonmc.component.api.Observable;
import io.github.cottonmc.component.energy.event.PowerGenCallback;
import io.github.cottonmc.component.energy.type.EnergyType;
import nerdhub.cardinal.components.api.component.Component;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

//TODO: better name?
public interface CapacitorComponent extends Component, Observable {
	/**
	 * @return The maximum amount of energy the storage can hold.
	 */
	int getMaxEnergy();

	/**
	 * @return The current amount of energy the storage is holding.
	 */
	int getCurrentEnergy();

	/**
	 * @return Whether energy can be inserted.
	 */
	boolean canInsertEnergy();

	/**
	 * Attempt to insert energy.
	 *
	 * @param amount The amount of energy to insert.
	 * @param action Whether to test or perform the insertion.
	 * @return the amount of leftover energy, or 0 if the insertion was completely successful.
	 */
	int insertEnergy(EnergyType type, int amount, ActionType action);

	/**
	 * Generate energy of the preferred type, firing a {@link PowerGenCallback}.
	 * @param world The world this energy was generated in.
	 * @param amount The amount of energy to generate.
	 * @return The amount of energy that was not able to generate, or 0 if it was all generated successfully.
	 */
	//TODO: better way to do this than forcing to provide a world and pos?
	default int generateEnergy(World world, BlockPos pos, int amount) {
		int leftover = amount - insertEnergy(getPreferredType(), amount, ActionType.PERFORM);
		PowerGenCallback.EVENT.invoker().generate(world, pos, getPreferredType(), amount - leftover);
		return leftover;
	}

	/**
	 * @return Whether energy can be extracted.
	 */
	boolean canExtractEnergy();

	/**
	 * Attempt to extract energy.
	 * @param amount The amount of energy to extract.
	 * @param action Whether to test or perform the extraction.
	 * @return The amount of energy actually extracted, or zero if none was extracted.
	 */
	int extractEnergy(EnergyType type, int amount, ActionType action);

	/**
	 * @return The typical kind of energy this component works with. Alternative energy types may not work if they're not compatible with this type.
	 */
	@Nonnull
	EnergyType getPreferredType();

	/**
	 * @return The total amount of energy gained from harmful energy types since the last reset.
	 */
	int getHarm();

	/**
	 * Indicate to this component that its holder has been exposed to extremely powerful and sudden
	 * electromagnetic radiation. If this component stores electrical energy, it should at least
	 * remove 'strength' energy from its internal buffer, and can optionally trigger volatile
	 * behavior or shut the machine down temporarily. However, specially designed induction coils
	 * might actually charge if strength is below their capacity.
	 * @param strength The strength of the received electromagnetic pulse.
	 */
	default void emp(int strength) {
		extractEnergy(getPreferredType(), strength, ActionType.PERFORM);
	}
}
