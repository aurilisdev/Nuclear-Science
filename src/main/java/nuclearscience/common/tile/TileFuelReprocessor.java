package nuclearscience.common.tile;

import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.registers.NuclearScienceRecipies;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.common.inventory.container.ContainerO2OProcessor;
import voltaic.prefab.sound.ITickableSound;
import voltaic.prefab.sound.SoundBarrierMethods;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentProcessor;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.RadiationUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileFuelReprocessor extends GenericTile implements ITickableSound {

	private boolean isSoundPlaying = false;

	public TileFuelReprocessor(BlockPos pos, BlockState state) {
		super(NuclearScienceTiles.TILE_FUELREPROCESSOR.get(), pos, state);

		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentTickable(this).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this, false, true).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 4).setInputDirections(BlockEntityUtils.MachineDirection.BACK));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().processors(1, 1, 1, 1).upgrades(3)).setSlotsByDirection(BlockEntityUtils.MachineDirection.TOP, 0).setDirectionsBySlot(1, BlockEntityUtils.MachineDirection.BOTTOM)
				//
				.setSlotsByDirection(BlockEntityUtils.MachineDirection.BOTTOM, 2).validUpgrades(ContainerO2OProcessor.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentProcessor(this).canProcess(this::shouldProcessRecipe).process(ComponentProcessor::processItem2ItemRecipe));
		addComponent(new ComponentContainerProvider("fuelreprocessor", this).createMenu((id, player) -> new ContainerO2OProcessor(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	private boolean shouldProcessRecipe(ComponentProcessor component, int procNumber) {
		boolean canProcess = component.canProcessItem2ItemRecipe(procNumber, NuclearScienceRecipies.FUEL_REPROCESSOR_TYPE.get());
		if (BlockEntityUtils.isLit(this) ^ canProcess) {
			BlockEntityUtils.updateLit(this, canProcess);
		}

		if(this.<ComponentTickable>getComponent(IComponentType.Tickable).getTicks() % 2 == 0) {
			RadiationUtils.handleRadioactiveItems(this, (ComponentInventory) getComponent(IComponentType.Inventory), NuclearConstants.FUEL_REPROCESSOR_RADIATION_RADIUS, true, 1, false);
		}

		return canProcess;
	}

	public void tickClient(ComponentTickable tickable) {
		if (!isSoundPlaying && shouldPlaySound()) {
			isSoundPlaying = true;
			SoundBarrierMethods.playTileSound(ElectrodynamicsSounds.SOUND_HUM.get(), this, true);
		}
		if(this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive(0) && getLevel().getRandom().nextFloat() < 0.3) {
			this.level.addParticle(ParticleTypes.SMOKE, this.worldPosition.getX() + level.random.nextFloat(), this.worldPosition.getY() + level.random.nextFloat(), this.worldPosition.getZ() + level.random.nextFloat(), 0.0, 0.0, 0.0);
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
	public int getComparatorSignal() {
		return this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive(0) ? 15 : 0;
	}
}
