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
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.inventory.container.ContainerMSRFuelPreProcessor;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.common.settings.Constants;

public class TileMSRFuelPreProcessor extends GenericTile {
	public static final int MAX_TANK_CAPACITY = 5000;
	public long clientTicks = 0;

	public TileMSRFuelPreProcessor(BlockPos pos, BlockState state) {
		super(DeferredRegisters.TILE_MSRFUELPREPROCESSOR.get(), pos, state);
		addComponent(new ComponentTickable().tickClient(this::tickClient));
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 2)
				.maxJoules(Constants.MSRFUELPREPROCESSOR_USAGE_PER_TICK * 10));
		addComponent(((ComponentFluidHandlerMulti) new ComponentFluidHandlerMulti(this).relativeInput(Direction.EAST).relativeOutput(Direction.WEST))
				.setAddFluidsValues(NuclearScienceRecipeInit.MSR_FUEL_PREPROCESSOR_TYPE, MAX_TANK_CAPACITY, true, true));
		addComponent(new ComponentInventory(this).size(8).relativeFaceSlots(Direction.EAST, 0, 1, 2).relativeFaceSlots(Direction.UP, 0, 1, 2)
				.relativeSlotFaces(3, Direction.DOWN).inputs(3).outputs(1).bucketInputs(1).upgrades(3).processors(1).processorInputs(3)
				.valid(machineValidator()));
		addComponent(new ComponentProcessor(this).setProcessorNumber(0)
				.canProcess(component -> component.outputToPipe(component).consumeBucket().canProcessFluidItem2ItemRecipe(component,
						NuclearScienceRecipeInit.MSR_FUEL_PREPROCESSOR_TYPE))
				.process(component -> component.processFluidItem2ItemRecipe(component)).usage(Constants.MSRFUELPREPROCESSOR_USAGE_PER_TICK)
				.requiredTicks(Constants.MSRFUELPREPROCESSOR_REQUIRED_TICKS));
		addComponent(new ComponentContainerProvider("container.msrfuelpreprocessor")
				.createMenu((id, player) -> new ContainerMSRFuelPreProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));

	}

	protected void tickClient(ComponentTickable tickable) {
		boolean running = this.<ComponentProcessor>getComponent(ComponentType.Processor).operatingTicks > 0;
		if (running) {
			if (level.random.nextDouble() < 0.15) {
				level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(),
						worldPosition.getY() + level.random.nextDouble() * 0.4 + 0.5, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D,
						0.0D);
			}
			clientTicks++;
		}
	}

}
