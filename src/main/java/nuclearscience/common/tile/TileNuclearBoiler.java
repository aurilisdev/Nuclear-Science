package nuclearscience.common.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import nuclearscience.common.inventory.container.ContainerNuclearBoiler;
import nuclearscience.registers.NuclearScienceRecipies;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.registers.NuclearScienceSounds;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.api.gas.GasAction;
import voltaic.api.gas.GasStack;
import voltaic.api.gas.GasTank;
import voltaic.prefab.sound.ITickableSound;
import voltaic.prefab.sound.SoundBarrierMethods;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.*;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.RadiationUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileNuclearBoiler extends GenericTile implements ITickableSound {

	public static final int MAX_FLUID_TANK_CAPACITY = 5000;

	public static final int MAX_GAS_TANK_CAPACITY = 5000;
	public static final int MAX_TEMPERATURE = 1000;
	public static final int MAX_PRESSURE = 10;

	private boolean isSoundPlaying = false;

	public TileNuclearBoiler(BlockPos pos, BlockState state) {
		super(NuclearScienceTiles.TILE_CHEMICALBOILER.get(), pos, state);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 2));
		addComponent(new ComponentFluidHandlerMulti(this).setInputTanks(1, new int[] { MAX_FLUID_TANK_CAPACITY }).setInputDirections(BlockEntityUtils.MachineDirection.RIGHT).setRecipeType(NuclearScienceRecipies.NUCLEAR_BOILER_TYPE.get()));
		addComponent(new ComponentGasHandlerMulti(this).setOutputTanks(1, arr(MAX_GAS_TANK_CAPACITY), arr(MAX_TEMPERATURE), arr(MAX_PRESSURE)).setOutputDirections(BlockEntityUtils.MachineDirection.LEFT).setRecipeType(NuclearScienceRecipies.NUCLEAR_BOILER_TYPE.get()));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().processors(1, 1, 0, 0).bucketInputs(1).gasOutputs(1).upgrades(3)).setDirectionsBySlot(0, BlockEntityUtils.MachineDirection.FRONT, BlockEntityUtils.MachineDirection.TOP).validUpgrades(ContainerNuclearBoiler.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentProcessor(this).canProcess((component, procNumber) -> component.outputToGasPipe().consumeBucket().dispenseGasCylinder().canProcessFluidItem2GasRecipe(procNumber, NuclearScienceRecipies.NUCLEAR_BOILER_TYPE.get())).process(ComponentProcessor::processFluidItem2GasRecipe));
		addComponent(new ComponentContainerProvider("nuclearboiler", this).createMenu((id, player) -> new ContainerNuclearBoiler(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	protected void tickServer(ComponentTickable tickable) {
		Level world = getLevel();

		if(this.<ComponentTickable>getComponent(IComponentType.Tickable).getTicks() % 2 == 0) {
			RadiationUtils.handleRadioactiveGases(this, (ComponentGasHandlerMulti) getComponent(IComponentType.GasHandler), NuclearConstants.NUCLEAR_BOILER_RADIATION_RADIUS, true, 1, false);
			RadiationUtils.handleRadioactiveFluids(this, (ComponentFluidHandlerMulti) getComponent(IComponentType.FluidHandler), NuclearConstants.NUCLEAR_BOILER_RADIATION_RADIUS, true, 1, false);
			RadiationUtils.handleRadioactiveItems(this, (ComponentInventory) getComponent(IComponentType.Inventory), NuclearConstants.NUCLEAR_BOILER_RADIATION_RADIUS, true, 1, false);
		}


		Direction centrifugeDir = getFacing().getCounterClockWise();
		BlockEntity tile = world.getBlockEntity(getBlockPos().relative(centrifugeDir));
		if (tile != null && tile instanceof TileGasCentrifuge centrifuge) {
			ComponentGasHandlerMulti centrifugeHandler = centrifuge.getComponent(IComponentType.GasHandler);
			if (centrifugeHandler != null && centrifuge.getFacing() == centrifugeDir) {
				ComponentGasHandlerMulti boilerHandler = getComponent(IComponentType.GasHandler);
				GasTank boilerTank = boilerHandler.getOutputTanks()[0];
				GasTank centrifugeTank = centrifugeHandler.getInputTanks()[0];
				int accepted = centrifugeTank.fill(boilerTank.getGas(), GasAction.SIMULATE);
				centrifugeTank.fill(new GasStack(boilerTank.getGas().getGas(), accepted, boilerTank.getGas().getTemperature(), boilerTank.getGas().getPressure()), GasAction.EXECUTE);
				boilerTank.drain(accepted, GasAction.EXECUTE);

			}
		}
	}

	protected void tickClient(ComponentTickable tickable) {
		boolean running = this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive(0);
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
		return this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive(0);
	}
	
	@Override
	public AABB getRenderBoundingBox() {
		return super.getRenderBoundingBox().inflate(1);
	}

}
