package nuclearscience.common.tile;

import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.inventory.container.ContainerO2OProcessor;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentProcessorType;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.common.recipe.categories.o2o.specificmachines.FuelReprocessorRecipe;
import nuclearscience.common.settings.Constants;

public class TileFuelReprocessor extends GenericTile {

    public TileFuelReprocessor(BlockPos pos, BlockState state) {
	super(DeferredRegisters.TILE_FUELREPROCESSOR.get(), pos, state);
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 4).relativeInput(Direction.NORTH));
	addComponent(new ComponentInventory(this).size(5).faceSlots(Direction.UP, 0).faceSlots(Direction.DOWN, 1).relativeFaceSlots(Direction.EAST, 1)
		.relativeFaceSlots(Direction.WEST, 2)
		.valid((slot, stack) -> slot == 0 || slot > 2 && stack.getItem() instanceof ItemProcessorUpgrade));
	addProcessor(new ComponentProcessor(this).upgradeSlots(2, 3, 4)
		.canProcess(component -> component.canProcessO2ORecipe(component, FuelReprocessorRecipe.class,
			NuclearScienceRecipeInit.FUEL_REPROCESSOR_TYPE))
		.process(component -> component.processO2ORecipe(component, FuelReprocessorRecipe.class))
		.requiredTicks((long) Constants.FUELREPROCESSOR_REQUIRED_TICKS).usage(Constants.FUELREPROCESSOR_USAGE_PER_TICK)
		.type(ComponentProcessorType.ObjectToObject));
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
