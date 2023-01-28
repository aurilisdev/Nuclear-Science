package nuclearscience.common.tile;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
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
import nuclearscience.common.inventory.container.ContainerMSRFuelPreProcessor;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.registers.NuclearScienceBlockTypes;

public class TileMSRFuelPreProcessor extends GenericTile {
	public static final int MAX_TANK_CAPACITY = 5000;
	public long clientTicks = 0;

	public TileMSRFuelPreProcessor(BlockPos pos, BlockState state) {
		super(NuclearScienceBlockTypes.TILE_MSRFUELPREPROCESSOR.get(), pos, state);
		addComponent(new ComponentTickable().tickClient(this::tickClient));
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2));
		addComponent(new ComponentFluidHandlerMulti(this).setTanks(1, 1, MAX_TANK_CAPACITY).setInputDirections(Direction.EAST).setOutputDirections(Direction.WEST).setRecipeType(NuclearScienceRecipeInit.MSR_FUEL_PREPROCESSOR_TYPE.get()));
		addComponent(new ComponentInventory(this).size(8).relativeFaceSlots(Direction.EAST, 0, 1, 2).relativeFaceSlots(Direction.UP, 0, 1, 2).relativeSlotFaces(3, Direction.DOWN).inputs(3).outputs(1).bucketInputs(1).upgrades(3).processors(1).processorInputs(3).validUpgrades(ContainerMSRFuelPreProcessor.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentProcessor(this).canProcess(component -> component.outputToPipe().consumeBucket().canProcessFluidItem2ItemRecipe(component, NuclearScienceRecipeInit.MSR_FUEL_PREPROCESSOR_TYPE.get())).process(component -> component.processFluidItem2ItemRecipe(component)));
		addComponent(new ComponentContainerProvider("container.msrfuelpreprocessor").createMenu((id, player) -> new ContainerMSRFuelPreProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));

	}

	protected void tickClient(ComponentTickable tickable) {
		boolean running = this.<ComponentProcessor>getComponent(ComponentType.Processor).operatingTicks.get() > 0;
		if (running) {
			if (level.random.nextDouble() < 0.15) {
				level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble() * 0.4 + 0.5, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
			}
			clientTicks++;
		}
	}
}
