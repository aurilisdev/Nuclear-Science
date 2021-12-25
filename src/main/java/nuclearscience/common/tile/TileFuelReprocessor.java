package nuclearscience.common.tile;

import electrodynamics.api.capability.electrodynamic.CapabilityElectrodynamic;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.inventory.container.tile.ContainerO2OProcessor;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.common.settings.Constants;

public class TileFuelReprocessor extends GenericTile {

	public TileFuelReprocessor(BlockPos pos, BlockState state) {
		super(DeferredRegisters.TILE_FUELREPROCESSOR.get(), pos, state);
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentTickable().tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 4).relativeInput(Direction.NORTH));
		addComponent(new ComponentInventory(this).size(6).faceSlots(Direction.UP, 0).faceSlots(Direction.DOWN, 1).relativeFaceSlots(Direction.EAST, 1)
				.relativeFaceSlots(Direction.WEST, 2).inputs(1).outputs(1).upgrades(3).processors(1).processorInputs(1).biproducts(1)
				.valid(machineValidator()));
		addProcessor(new ComponentProcessor(this).setProcessorNumber(0)
				.canProcess(component -> component.canProcessItem2ItemRecipe(component, NuclearScienceRecipeInit.FUEL_REPROCESSOR_TYPE))
				.process(component -> component.processItem2ItemRecipe(component)).requiredTicks((long) Constants.FUELREPROCESSOR_REQUIRED_TICKS)
				.usage(Constants.FUELREPROCESSOR_USAGE_PER_TICK));
		addComponent(new ComponentContainerProvider("container.fuelreprocessor")
				.createMenu((id, player) -> new ContainerO2OProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	public void tickClient(ComponentTickable tickable) {
		boolean running = getProcessor(0).operatingTicks > 0;
		if (running && tickable.getTicks() % 100 == 0) {
			SoundAPI.playSound(electrodynamics.SoundRegister.SOUND_MINERALCRUSHER.get(), SoundSource.BLOCKS, 1, 1, worldPosition);
		}
	}

}
