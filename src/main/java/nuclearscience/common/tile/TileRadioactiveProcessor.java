package nuclearscience.common.tile;

import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipe;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentProcessorType;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.util.Direction;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.inventory.container.ContainerRadioactiveProcessor;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.common.settings.Constants;

public class TileRadioactiveProcessor extends GenericTileTicking {

    public static final int MAX_TANK_CAPACITY = 5000;

    public TileRadioactiveProcessor() {
	super(DeferredRegisters.TILE_RADIOACTIVEPROCESSOR.get());
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentElectrodynamic(this).enableUniversalInput().voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 4)
		.maxJoules(Constants.RADIOACTIVEPROCESSOR_USAGE_PER_TICK * 10));
	addComponent(new ComponentFluidHandlerMulti(this)
		.setAddFluidsValues(FluidItem2ItemRecipe.class, NuclearScienceRecipeInit.RADIOACTIVE_PROCESSOR_TYPE, MAX_TANK_CAPACITY, true, false)
		.universalInput());
	addComponent(new ComponentInventory(this).size(6).faceSlots(Direction.UP, 0).faceSlots(Direction.DOWN, 1).slotFaces(2, Direction.SOUTH,
		Direction.NORTH, Direction.EAST, Direction.WEST));
	addComponent(new ComponentProcessor(this).upgradeSlots(3, 4, 5).type(ComponentProcessorType.ObjectToObject)
		.usage(Constants.RADIOACTIVEPROCESSOR_USAGE_PER_TICK).requiredTicks((long) Constants.RADIOACTIVEPROCESSOR_REQUIRED_TICKS)
		.canProcess(component -> component.consumeBucket(2).canProcessFluidItem2ItemRecipe(component, FluidItem2ItemRecipe.class,
			NuclearScienceRecipeInit.RADIOACTIVE_PROCESSOR_TYPE))
		.process(component -> component.processFluidItem2ItemRecipe(component, FluidItem2ItemRecipe.class)));
	addComponent(new ComponentContainerProvider("container.radioactiveprocessor")
		.createMenu((id, player) -> new ContainerRadioactiveProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
    }

    protected void tickClient(ComponentTickable tickable) {

    }

}
