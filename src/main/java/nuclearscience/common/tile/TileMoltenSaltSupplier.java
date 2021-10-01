package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.inventory.container.ContainerMoltenSaltSupplier;
import nuclearscience.common.settings.Constants;

public class TileMoltenSaltSupplier extends GenericTileTicking {

    protected CachedTileOutput output;

    public TileMoltenSaltSupplier() {
	super(DeferredRegisters.TILE_MOLTENSALTSUPPLIER.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().tickServer(this::tickServer));
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentElectrodynamic(this).voltage(Constants.MOLTENSALTSUPPLIER_VOLTAGE).extractPower((x, y) -> TransferPack.EMPTY)
		.output(Direction.UP).output(Direction.DOWN).maxJoules(Constants.MOLTENSALTSUPPLIER_USAGE_PER_TICK * 20));
	addComponent(new ComponentInventory(this).size(1).slotFaces(0, Direction.values())
		.valid((slot, stack) -> stack.getItem() == DeferredRegisters.ITEM_LIFHT4PUF3.get()));
	addComponent(new ComponentContainerProvider("container.moltensaltsupplier")
		.createMenu((id, player) -> new ContainerMoltenSaltSupplier(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
    }

    public void tickServer(ComponentTickable tickable) {
	Direction dir = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	if (output == null) {
	    output = new CachedTileOutput(world, pos.offset(dir.getOpposite()));
	}
	if (tickable.getTicks() % 50 == 0) {
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	}
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	if (electro.getJoulesStored() > Constants.MOLTENSALTSUPPLIER_USAGE_PER_TICK) {
	    electro.extractPower(TransferPack.joulesVoltage(Constants.MOLTENSALTSUPPLIER_USAGE_PER_TICK, Constants.MOLTENSALTSUPPLIER_VOLTAGE),
		    false);
	    if (tickable.getTicks() % 40 == 0) {
		output.update();
		ItemStack in = this.<ComponentInventory>getComponent(ComponentType.Inventory).getStackInSlot(0);
		if (in.getCount() > 0 && output.valid() && output.<TileEntity>getSafe() instanceof TileMSRReactorCore) {
		    TileMSRReactorCore core = output.getSafe();
		    if (core.<ComponentDirection>getComponent(ComponentType.Direction).getDirection() == dir.getOpposite()) {
			if (TileMSRReactorCore.FUEL_CAPACITY - core.currentFuel >= 250) {
			    in.shrink(1);
			    core.currentFuel += 250;
			}
		    }
		}
	    }
	}
    }
}
