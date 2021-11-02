package nuclearscience.common.tile;

import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.Direction;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.inventory.container.ContainerFreezePlug;
import nuclearscience.common.settings.Constants;

public class TileFreezePlug extends GenericTileTicking {
    private boolean isFrozen = false;

    public TileFreezePlug() {
	super(DeferredRegisters.TILE_FREEZEPLUG.get());
	addComponent(new ComponentTickable().tickServer(this::tickServer));
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentElectrodynamic(this).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE).extractPower((x, y) -> TransferPack.EMPTY)
		.output(Direction.UP).output(Direction.DOWN));
	addComponent(new ComponentInventory(this).size(1).slotFaces(0, Direction.values())
		.valid((slot, stack) -> stack.getItem() == DeferredRegisters.ITEM_FLINAK.get()));
	addComponent(new ComponentContainerProvider("container.freezeplug")
		.createMenu((id, player) -> new ContainerFreezePlug(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
    }

    public void tickServer(ComponentTickable tickable) {
	ComponentElectrodynamic el = getComponent(ComponentType.Electrodynamic);
	ComponentInventory inv = getComponent(ComponentType.Inventory);
	if (!inv.getItem(0).isEmpty()) {
	    isFrozen = el.getJoulesStored() >= Constants.FREEZEPLUG_USAGE_PER_TICK;
	    if (isFrozen) {
		el.extractPower(TransferPack.joulesVoltage(Constants.FREEZEPLUG_USAGE_PER_TICK, 120), false);
	    }
	} else {
	    isFrozen = false;
	}
    }

    public boolean isFrozen() {
	return isFrozen;
    }
}
