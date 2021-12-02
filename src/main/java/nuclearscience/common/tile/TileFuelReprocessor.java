package nuclearscience.common.tile;

import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.inventory.container.ContainerO2OProcessor;
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
import nuclearscience.common.recipe.categories.item2item.specificmachines.FuelReprocessorRecipe;
import nuclearscience.common.settings.Constants;

public class TileFuelReprocessor extends GenericTile {

    private static int inputSlots = 1;
    private static int outputSize = 1;
    private static int itemBiSize = 0;
    private static int inputBucketSlots = 0;
    private static int outputBucketSlots = 0;
    private static int upgradeSlots = 3;

    private static int processorCount = 1;
    private static int inputPerProc = 1;

    private static int invSize = inputSlots + outputSize + inputBucketSlots + outputBucketSlots + upgradeSlots + itemBiSize;

    public TileFuelReprocessor(BlockPos pos, BlockState state) {
	super(DeferredRegisters.TILE_FUELREPROCESSOR.get(), pos, state);
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 4).relativeInput(Direction.NORTH));
	addComponent(new ComponentInventory(this).size(invSize).faceSlots(Direction.UP, 0).faceSlots(Direction.DOWN, 1)
		.relativeFaceSlots(Direction.EAST, 1).relativeFaceSlots(Direction.WEST, 2)
		.valid(getPredicate(inputSlots, outputSize, itemBiSize, inputBucketSlots + outputBucketSlots, upgradeSlots, invSize))
		.slotSizes(inputSlots, outputSize, itemBiSize, upgradeSlots, inputBucketSlots, outputBucketSlots, processorCount, inputPerProc));
	addProcessor(new ComponentProcessor(this).setProcessorNumber(0)
		.canProcess(component -> component.canProcessItem2ItemRecipe(component, FuelReprocessorRecipe.class,
			NuclearScienceRecipeInit.FUEL_REPROCESSOR_TYPE))
		.process(component -> component.processItem2ItemRecipe(component, FuelReprocessorRecipe.class))
		.requiredTicks((long) Constants.FUELREPROCESSOR_REQUIRED_TICKS).usage(Constants.FUELREPROCESSOR_USAGE_PER_TICK));
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
