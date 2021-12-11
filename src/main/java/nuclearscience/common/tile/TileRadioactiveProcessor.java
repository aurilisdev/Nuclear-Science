package nuclearscience.common.tile;

import electrodynamics.api.capability.electrodynamic.CapabilityElectrodynamic;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.inventory.container.ContainerRadioactiveProcessor;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.common.settings.Constants;

public class TileRadioactiveProcessor extends GenericTile {

    public static final int MAX_TANK_CAPACITY = 5000;

    public TileRadioactiveProcessor(BlockPos pos, BlockState state) {
	super(DeferredRegisters.TILE_RADIOACTIVEPROCESSOR.get(), pos, state);
	addComponent(new ComponentTickable());
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentElectrodynamic(this).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 4)
		.maxJoules(Constants.RADIOACTIVEPROCESSOR_USAGE_PER_TICK * 10.0).relativeInput(Direction.NORTH));
	addComponent(new ComponentFluidHandlerMulti(this)
		.setAddFluidsValues(NuclearScienceRecipeInit.RADIOACTIVE_PROCESSOR_TYPE, MAX_TANK_CAPACITY, true, false).input(Direction.UP));
	addComponent(new ComponentInventory(this).size(6).inputs(1).outputs(1).bucketInputs(1).upgrades(3).processors(1).processorInputs(1)
		.valid(machineValidator()).faceSlots(Direction.UP, 0).faceSlots(Direction.DOWN, 1)
		.slotFaces(2, Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST));
	addComponent(new ComponentProcessor(this).setProcessorNumber(0).usage(Constants.RADIOACTIVEPROCESSOR_USAGE_PER_TICK)
		.requiredTicks((long) Constants.RADIOACTIVEPROCESSOR_REQUIRED_TICKS).canProcess(component -> component.consumeBucket()
			.canProcessFluidItem2ItemRecipe(component, NuclearScienceRecipeInit.RADIOACTIVE_PROCESSOR_TYPE))
		.process(component -> component.processFluidItem2ItemRecipe(component)));
	addComponent(new ComponentContainerProvider("container.radioactiveprocessor")
		.createMenu((id, player) -> new ContainerRadioactiveProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
    }
}
