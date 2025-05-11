package nuclearscience.common.tile;

import electrodynamics.prefab.utilities.ElectricityUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import nuclearscience.common.inventory.container.ContainerRadioisotopeGenerator;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.api.radiation.util.RadioactiveObject;
import voltaic.common.reloadlistener.RadioactiveItemRegister;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.*;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.RadiationUtils;
import voltaic.prefab.utilities.object.CachedTileOutput;
import voltaic.prefab.utilities.object.TransferPack;

public class TileRadioisotopeGenerator extends GenericTile {

	protected CachedTileOutput output1;
	protected CachedTileOutput output2;

	public TileRadioisotopeGenerator() {
		super(NuclearScienceTiles.TILE_RADIOISOTOPEGENERATOR.get());

		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, true, false).voltage(NuclearConstants.RADIOISOTOPEGENERATOR_VOLTAGE).extractPower((x, y) -> TransferPack.EMPTY).setOutputDirections(BlockEntityUtils.MachineDirection.BOTTOM, BlockEntityUtils.MachineDirection.TOP));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().inputs(1)).setDirectionsBySlot(0, BlockEntityUtils.MachineDirection.values()).valid((slot, stack, i) -> RadioactiveItemRegister.getValue(stack.getItem()).amount() > 0));
		addComponent(new ComponentContainerProvider("radioisotopegenerator", this).createMenu((id, player) -> new ContainerRadioisotopeGenerator(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	public void tickServer(ComponentTickable tickable) {
		if (output1 == null) {
			output1 = new CachedTileOutput(level, worldPosition.relative(Direction.UP));
		}
		if (output2 == null) {
			output2 = new CachedTileOutput(level, worldPosition.relative(Direction.DOWN));
		}
		if (tickable.getTicks() % 40 == 0) {
			output1.update(worldPosition.relative(Direction.UP));
			output2.update(worldPosition.relative(Direction.DOWN));
		}

		ComponentInventory inv = getComponent(IComponentType.Inventory);
		ItemStack input = inv.getItem(0);

		if(input.isEmpty()) {
			return;
		}

		RadioactiveObject radiation = RadioactiveItemRegister.getValue(input.getItem());

		if(radiation.amount() <= 0) {
			return;
		}

		RadiationUtils.handleRadioactiveItems(this, inv, NuclearConstants.RADIO_GENATOR_RADIATION_RADIUS, true, 0, false);

		double currentOutput = input.getCount() * NuclearConstants.RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER * radiation.amount();

		if (currentOutput > 0) {
			TransferPack transfer = TransferPack.ampsVoltage(currentOutput / (NuclearConstants.RADIOISOTOPEGENERATOR_VOLTAGE * 2.0), NuclearConstants.RADIOISOTOPEGENERATOR_VOLTAGE);
			if (output1.valid()) {
				ElectricityUtils.receivePower(output1.getSafe(), Direction.DOWN, transfer, false);
			}
			if (output2.valid()) {
				ElectricityUtils.receivePower(output2.getSafe(), Direction.UP, transfer, false);
			}
		}
	}

	@Override
	public int getComparatorSignal() {

		ItemStack stack = this.<ComponentInventory>getComponent(IComponentType.Inventory).getItem(0);

		if (stack.isEmpty()) {
			return 0;
		}

		return (int) (((double) stack.getCount() / (double) stack.getMaxStackSize()) * 15.0);
	}

}
