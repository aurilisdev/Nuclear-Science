package nuclearscience.common.tile.reactor.moltensalt;

import net.minecraft.item.ItemStack;
import nuclearscience.common.inventory.container.ContainerFreezePlug;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.*;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.object.TransferPack;
import voltaic.registers.VoltaicCapabilities;

public class TileFreezePlug extends GenericTile {

	public final SingleProperty<Boolean> isFrozen = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "isfrozen", false));
	public final SingleProperty<Double> saltBonus = property(new SingleProperty<>(PropertyTypes.DOUBLE, "saltbonus", 1.0));

	public TileFreezePlug() {
		super(NuclearScienceTiles.TILE_FREEZEPLUG.get());
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, false, true).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE).extractPower((x, y) -> TransferPack.EMPTY).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM).maxJoules(NuclearConstants.FREEZEPLUG_USAGE_PER_TICK * 20));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().inputs(1)).valid((slot, stack, i) -> stack.getItem() == NuclearScienceItems.ITEM_FLINAK.get()));
		addComponent(new ComponentContainerProvider("freezeplug", this).createMenu((id, player) -> new ContainerFreezePlug(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	public void tickServer(ComponentTickable tickable) {
		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);
		ComponentInventory inv = getComponent(IComponentType.Inventory);

		ItemStack stack = inv.getItem(0);

		if (stack.isEmpty()) {
			isFrozen.setValue(false);
			saltBonus.setValue(0.0);
			return;
		}

		if (electro.getJoulesStored() < NuclearConstants.FREEZEPLUG_USAGE_PER_TICK) {
			isFrozen.setValue(false);
			saltBonus.setValue(0.0);
			return;
		}

		electro.joules(electro.getJoulesStored() - NuclearConstants.FREEZEPLUG_USAGE_PER_TICK);

		isFrozen.setValue(true);

		double bonus = 1.0 + ((stack.getCount() - 1) / 63.0);

		saltBonus.setValue(bonus);

	}

	public boolean isFrozen() {
		return isFrozen.getValue();
	}

	public double getSaltBonus() {
		return saltBonus.getValue();
	}

}
