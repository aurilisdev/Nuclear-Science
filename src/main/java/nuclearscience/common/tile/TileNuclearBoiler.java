package nuclearscience.common.tile;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;
import electrodynamics.api.gas.GasTank;
import electrodynamics.prefab.sound.SoundBarrierMethods;
import electrodynamics.prefab.sound.utils.ITickableSound;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentGasHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import nuclearscience.common.inventory.container.ContainerNuclearBoiler;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.registers.NuclearScienceBlockTypes;
import nuclearscience.registers.NuclearScienceSounds;

public class TileNuclearBoiler extends GenericTile implements ITickableSound {

	public static final int MAX_FLUID_TANK_CAPACITY = 5000;
	
	public static final double MAX_GAS_TANK_CAPACITY = 5000;
	public static final double MAX_TEMPERATURE = 1000;
	public static final int MAX_PRESSURE = 10;
	
	private boolean isSoundPlaying = false;

	public TileNuclearBoiler(BlockPos pos, BlockState state) {
		super(NuclearScienceBlockTypes.TILE_CHEMICALBOILER.get(), pos, state);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentDirection(this));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this).input(Direction.DOWN).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2));
		addComponent(new ComponentFluidHandlerMulti(this).setInputTanks(1, new int[] { MAX_FLUID_TANK_CAPACITY }).setInputDirections(Direction.EAST).setRecipeType(NuclearScienceRecipeInit.NUCLEAR_BOILER_TYPE.get()));
		addComponent(new ComponentGasHandlerMulti(this).setOutputTanks(1, arr(MAX_GAS_TANK_CAPACITY), arr(MAX_TEMPERATURE), arr(MAX_PRESSURE)).setOutputDirections(Direction.WEST).setRecipeType(NuclearScienceRecipeInit.NUCLEAR_BOILER_TYPE.get()));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().processors(1, 1, 0, 0).bucketInputs(1).gasOutputs(1).upgrades(3)).relativeSlotFaces(0, Direction.EAST, Direction.UP).relativeSlotFaces(1, Direction.DOWN).validUpgrades(ContainerNuclearBoiler.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentProcessor(this).canProcess(component -> component.outputToGasPipe().consumeBucket().dispenseGasCylinder().canProcessFluidItem2GasRecipe(component, NuclearScienceRecipeInit.NUCLEAR_BOILER_TYPE.get())).process(component -> component.processFluidItem2GasRecipe(component)));
		addComponent(new ComponentContainerProvider("container.nuclearboiler", this).createMenu((id, player) -> new ContainerNuclearBoiler(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	@Override
	public AABB getRenderBoundingBox() {
		return super.getRenderBoundingBox().inflate(1);
	}

	protected void tickServer(ComponentTickable tickable) {
		Level world = getLevel();
		ComponentDirection boilerComponentDir = getComponent(ComponentType.Direction);
		Direction centrifugeDir = boilerComponentDir.getDirection().getCounterClockWise();
		BlockEntity tile = world.getBlockEntity(getBlockPos().relative(centrifugeDir));
		if (tile != null && tile instanceof TileGasCentrifuge centrifuge) {
			ComponentGasHandlerMulti centrifugeHandler = centrifuge.getComponent(ComponentType.GasHandler);
			if (centrifugeHandler != null) {
				ComponentDirection centrifugeComponentDir = centrifuge.getComponent(ComponentType.Direction);
				if (centrifugeComponentDir.getDirection() == centrifugeDir) {
					ComponentGasHandlerMulti boilerHandler = getComponent(ComponentType.GasHandler);
					GasTank boilerTank = boilerHandler.getOutputTanks()[0];
					GasTank centrifugeTank = centrifugeHandler.getInputTanks()[0];
					double accepted = centrifugeTank.fill(boilerTank.getGas(), GasAction.SIMULATE);
					centrifugeTank.fill(new GasStack(boilerTank.getGas().getGas(), accepted, boilerTank.getGas().getTemperature(), boilerTank.getGas().getPressure()), GasAction.EXECUTE);
					boilerTank.drain(accepted, GasAction.EXECUTE);
				}
			}
		}
	}

	protected void tickClient(ComponentTickable tickable) {
		boolean running = this.<ComponentProcessor>getComponent(ComponentType.Processor).isActive();
		if (running && level.random.nextDouble() < 0.15) {
			level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble() * 0.4 + 0.5, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
		if (shouldPlaySound() && !isSoundPlaying) {
			SoundBarrierMethods.playTileSound(NuclearScienceSounds.SOUND_NUCLEARBOILER.get(), this, true);
			isSoundPlaying = true;
		}
	}

	@Override
	public void setNotPlaying() {
		isSoundPlaying = false;
	}

	@Override
	public boolean shouldPlaySound() {
		return this.<ComponentProcessor>getComponent(ComponentType.Processor).isActive();
	}

}
