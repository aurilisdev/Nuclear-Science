package nuclearscience.common.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.inventory.container.ContainerRadioactiveProcessor;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.registers.NuclearScienceRecipies;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.*;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.RadiationUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileRadioactiveProcessor extends GenericTile {

    public static final int MAX_TANK_CAPACITY = 5000;

    public TileRadioactiveProcessor(BlockPos pos, BlockState state) {
        super(NuclearScienceTiles.TILE_RADIOACTIVEPROCESSOR.get(), pos, state);
        addComponent(new ComponentTickable(this));
        addComponent(new ComponentPacketHandler(this));
        addComponent(new ComponentElectrodynamic(this, false, true).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 4).setInputDirections(BlockEntityUtils.MachineDirection.BACK));
        addComponent(new ComponentFluidHandlerMulti(this).setInputTanks(1, MAX_TANK_CAPACITY).setInputDirections(BlockEntityUtils.MachineDirection.TOP).setRecipeType(NuclearScienceRecipies.RADIOACTIVE_PROCESSOR_TYPE.get()));
        addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().processors(1, 1, 1, 0).bucketInputs(1).upgrades(3)).validUpgrades(ContainerRadioactiveProcessor.VALID_UPGRADES).valid(machineValidator())
                //
                .setDirectionsBySlot(0, BlockEntityUtils.MachineDirection.LEFT).setDirectionsBySlot(1, BlockEntityUtils.MachineDirection.RIGHT, BlockEntityUtils.MachineDirection.BOTTOM));
        addComponent(new ComponentProcessor(this).canProcess(this::shouldProcessRecipe).process(ComponentProcessor::processFluidItem2ItemRecipe));
        addComponent(new ComponentContainerProvider("radioactiveprocessor", this).createMenu((id, player) -> new ContainerRadioactiveProcessor(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
    }

    private boolean shouldProcessRecipe(ComponentProcessor component, int procNumber) {
        component.consumeBucket();

        boolean canProcess = component.canProcessFluidItem2ItemRecipe(procNumber, NuclearScienceRecipies.RADIOACTIVE_PROCESSOR_TYPE.get());
        if (BlockEntityUtils.isLit(this) ^ canProcess) {
            BlockEntityUtils.updateLit(this, canProcess);
        }

        RadiationUtils.handleRadioactiveFluids(this, (ComponentFluidHandlerMulti) getComponent(IComponentType.FluidHandler), NuclearConstants.RADIOACTIVE_PROCESSOR_RADIATION_RADIUS, true, 0, false);
        RadiationUtils.handleRadioactiveItems(this, (ComponentInventory) getComponent(IComponentType.Inventory), NuclearConstants.RADIOACTIVE_PROCESSOR_RADIATION_RADIUS, true, 0, false);

        return canProcess;
    }

    @Override
    public int getComparatorSignal() {
        return this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive(0) ? 15 : 0;
    }

}
