package nuclearscience.common.tile;

import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.common.item.ItemProcessorUpgrade;
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
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.inventory.container.ContainerMSRFuelPreProcessor;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.common.recipe.categories.fluid3items2item.specificmachines.MSRFuelPreProcessorRecipe;
import nuclearscience.common.settings.Constants;

public class TileMSRFuelPreProcessor extends GenericTileTicking {
    public static final int MAX_TANK_CAPACITY = 5000;
    public long clientTicks = 0;

    public TileMSRFuelPreProcessor() {
	super(DeferredRegisters.TILE_MSRFUELPREPROCESSOR.get());
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 2)
		.maxJoules(Constants.MSRFUELPREPROCESSOR_USAGE_PER_TICK * 10));
	addComponent(((ComponentFluidHandlerMulti) new ComponentFluidHandlerMulti(this).relativeInput(Direction.EAST).relativeOutput(Direction.WEST))
		.setAddFluidsValues(MSRFuelPreProcessorRecipe.class, NuclearScienceRecipeInit.MSR_FUEL_PREPROCESSOR_TYPE, MAX_TANK_CAPACITY, true,
			true));
	addComponent(new ComponentInventory(this).size(8).relativeFaceSlots(Direction.EAST, 0, 1, 2).relativeFaceSlots(Direction.UP, 0, 1, 2)
		.relativeSlotFaces(3, Direction.DOWN).valid((slot, stack) -> slot < 5 || stack.getItem() instanceof ItemProcessorUpgrade));
	addComponent(new ComponentProcessor(this).upgradeSlots(5, 6, 7)
		.canProcess(component -> component.outputToPipe(component).consumeBucket(4).canProcessFluid3Items2ItemRecipe(component,
			MSRFuelPreProcessorRecipe.class, NuclearScienceRecipeInit.MSR_FUEL_PREPROCESSOR_TYPE))
		.process(component -> component.processFluid3Items2ItemRecipe(component, MSRFuelPreProcessorRecipe.class))
		.usage(Constants.MSRFUELPREPROCESSOR_USAGE_PER_TICK).type(ComponentProcessorType.TripleObjectToObject)
		.requiredTicks(Constants.MSRFUELPREPROCESSOR_REQUIRED_TICKS));
	addComponent(new ComponentContainerProvider("container.msrfuelpreprocessor")
		.createMenu((id, player) -> new ContainerMSRFuelPreProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));

    }

    protected void tickClient(ComponentTickable tickable) {
	boolean running = this.<ComponentProcessor>getComponent(ComponentType.Processor).operatingTicks > 0;
	if (running) {
	    if (level.random.nextDouble() < 0.15) {
		level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble() * 0.4 + 0.5,
			worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
	    }
	    clientTicks++;
	}
    }

}
