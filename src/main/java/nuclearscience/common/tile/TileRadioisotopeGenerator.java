package nuclearscience.common.tile;

import electrodynamics.prefab.utilities.ElectricityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.inventory.container.ContainerRadioisotopeGenerator;
import nuclearscience.common.settings.NuclearConfig;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.api.radiation.util.RadioactiveObject;
import voltaic.common.reloadlistener.RadioactiveItemRegister;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.RadiationUtils;
import voltaic.prefab.utilities.object.TransferPack;

public class TileRadioisotopeGenerator extends GenericTile {

    public TileRadioisotopeGenerator(BlockPos pos, BlockState state) {
	super(NuclearScienceTiles.TILE_RADIOISOTOPEGENERATOR.get(), pos, state);

	addComponent(new ComponentTickable(this).tickServer(this::tickServer));
	addComponent(new ComponentElectrodynamic(this, true, false)
		.voltage(NuclearConfig.getInstance().RADIOISOTOPEGENERATOR_VOLTAGE.get())
		.extractPower((x, y) -> TransferPack.EMPTY)
		.setOutputDirections(BlockEntityUtils.MachineDirection.BOTTOM, BlockEntityUtils.MachineDirection.TOP));
	addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().inputs(1))
		.setDirectionsBySlot(0, BlockEntityUtils.MachineDirection.values())
		.valid((slot, stack, i) -> RadioactiveItemRegister.getValue(stack.getItem()).amount() > 0));
	addComponent(new ComponentContainerProvider("radioisotopegenerator", this)
		.createMenu((id, player) -> new ContainerRadioisotopeGenerator(id, player,
			requireComponent(IComponentType.Inventory), getCoordsArray())));
    }

    public void tickServer(Level level, ComponentTickable tickable) {
	ComponentInventory inv = requireComponent(IComponentType.Inventory);
	ItemStack input = inv.getItem(0);
	if (input.isEmpty())
	    return;

	RadioactiveObject radiation = RadioactiveItemRegister.getValue(input.getItem());
	if (radiation.amount() <= 0)
	    return;

	RadiationUtils.handleRadioactiveItems(level, this, inv,
		NuclearConfig.getInstance().RADIO_GENATOR_RADIATION_RADIUS.get(), true, 30, true, false);

	double currentOutput = input.getCount()
		* NuclearConfig.getInstance().RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER.get() * radiation.amount();

	if (currentOutput > 0) {
	    double voltage = NuclearConfig.getInstance().RADIOISOTOPEGENERATOR_VOLTAGE.get();
	    TransferPack transfer = TransferPack.ampsVoltage(currentOutput / (voltage * 2.0), voltage);
	    BlockEntity above = level.getBlockEntity(worldPosition.above());
	    if (above != null && !above.isRemoved()) {
		ElectricityUtils.receivePower(above, Direction.DOWN, transfer, false);
	    }
	    BlockEntity below = level.getBlockEntity(worldPosition.below());
	    if (below != null && !below.isRemoved()) {
		ElectricityUtils.receivePower(below, Direction.UP, transfer, false);
	    }
	}
    }

    @Override
    public int getComparatorSignal(Level level) {
	ComponentInventory inv = requireComponent(IComponentType.Inventory);
	ItemStack stack = inv.getItem(0);
	if (stack.isEmpty())
	    return 0;

	return (int) ((double) stack.getCount() / stack.getMaxStackSize() * 15.0);
    }

}
