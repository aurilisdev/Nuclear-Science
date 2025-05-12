package nuclearscience.common.tile.reactor.moltensalt;

import net.minecraft.particles.ParticleTypes;
import nuclearscience.common.inventory.container.ContainerMSRFuelPreProcessor;
import nuclearscience.registers.NuclearScienceRecipies;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.*;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileMSRFuelPreProcessor extends GenericTile {
	public static final int MAX_TANK_CAPACITY = 5000;
	public long clientTicks = 0;

	public TileMSRFuelPreProcessor() {
		super(NuclearScienceTiles.TILE_MSRFUELPREPROCESSOR.get());
		addComponent(new ComponentTickable(this).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(BlockEntityUtils.MachineDirection.BACK).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 2));
		addComponent(new ComponentFluidHandlerMulti(this).setTanks(1, 1, new int[] { MAX_TANK_CAPACITY }, new int[] { MAX_TANK_CAPACITY }).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM).setRecipeType(NuclearScienceRecipies.MSR_FUEL_PREPROCESSOR_TYPE));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().processors(1, 3, 1, 0).bucketInputs(1).upgrades(3)).setSlotsByDirection(BlockEntityUtils.MachineDirection.TOP, 0).setSlotsByDirection(BlockEntityUtils.MachineDirection.LEFT, 1).setDirectionsBySlot(2, BlockEntityUtils.MachineDirection.FRONT).setDirectionsBySlot(3, BlockEntityUtils.MachineDirection.BOTTOM).validUpgrades(ContainerMSRFuelPreProcessor.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentProcessor(this).canProcess((component, procNumber) -> component.consumeBucket().canProcessFluidItem2ItemRecipe(procNumber, NuclearScienceRecipies.MSR_FUEL_PREPROCESSOR_TYPE)).process(ComponentProcessor::processFluidItem2ItemRecipe));
		addComponent(new ComponentContainerProvider("msrfuelpreprocessor", this).createMenu((id, player) -> new ContainerMSRFuelPreProcessor(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));

	}

	protected void tickClient(ComponentTickable tickable) {
		boolean running = this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive(0);
		if (running) {
			if (level.random.nextDouble() < 0.15) {
				level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble() * 0.4 + 0.5, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
			}
			clientTicks++;
		}
	}

	@Override
	public int getComparatorSignal() {
		return this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive(0) ? 15 : 0;
	}
}
