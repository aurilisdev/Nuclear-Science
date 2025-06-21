package nuclearscience.common.tile;

import net.minecraft.particles.ParticleTypes;
import nuclearscience.common.inventory.container.ContainerChemicalExtractor;
import nuclearscience.registers.NuclearScienceRecipies;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.*;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.RadiationUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileChemicalExtractor extends GenericTile {

	public static final int MAX_TANK_CAPACITY = 5000;

	public TileChemicalExtractor() {
		super(NuclearScienceTiles.TILE_CHEMICALEXTRACTOR.get());
		addComponent(new ComponentTickable(this).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 2));
		addComponent(new ComponentFluidHandlerMulti(this).setInputTanks(1, MAX_TANK_CAPACITY)
				//
				.setInputDirections(BlockEntityUtils.MachineDirection.TOP, BlockEntityUtils.MachineDirection.FRONT, BlockEntityUtils.MachineDirection.RIGHT, BlockEntityUtils.MachineDirection.BACK, BlockEntityUtils.MachineDirection.LEFT).setRecipeType(NuclearScienceRecipies.CHEMICAL_EXTRACTOR_TYPE));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().processors(1, 1, 1, 0).bucketInputs(1).upgrades(3))
				//
				.setDirectionsBySlot(0, BlockEntityUtils.MachineDirection.TOP, BlockEntityUtils.MachineDirection.LEFT, BlockEntityUtils.MachineDirection.RIGHT, BlockEntityUtils.MachineDirection.FRONT, BlockEntityUtils.MachineDirection.BACK)
				//
				.setDirectionsBySlot(1, BlockEntityUtils.MachineDirection.TOP, BlockEntityUtils.MachineDirection.LEFT, BlockEntityUtils.MachineDirection.RIGHT, BlockEntityUtils.MachineDirection.FRONT, BlockEntityUtils.MachineDirection.BACK).validUpgrades(ContainerChemicalExtractor.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentProcessor(this).canProcess(this::canProcess).process(ComponentProcessor::processFluidItem2ItemRecipe));
		addComponent(new ComponentContainerProvider("chemicalextractor", this).createMenu((id, player) -> new ContainerChemicalExtractor(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	private boolean canProcess(ComponentProcessor processor, int procNumber) {
		processor.consumeBucket();

		RadiationUtils.handleRadioactiveItems(this, (ComponentInventory) getComponent(IComponentType.Inventory), NuclearConstants.CHEMICAL_EXTRACTOR_RADIATION_RADIUS, true, 1, true, false);
		RadiationUtils.handleRadioactiveFluids(this, (ComponentFluidHandlerMulti) getComponent(IComponentType.FluidHandler), NuclearConstants.CHEMICAL_EXTRACTOR_RADIATION_RADIUS, true, 1, true, false);

		return processor.canProcessFluidItem2ItemRecipe(procNumber, NuclearScienceRecipies.CHEMICAL_EXTRACTOR_TYPE);
	}

	private void tickClient(ComponentTickable tickable) {
		if (this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive(0) && level.random.nextDouble() < 0.15) {
			level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble() * 0.8 + 0.5, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public int getComparatorSignal() {
		return this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive(0) ? 15 : 0;
	}
}
