package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
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
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.common.inventory.container.ContainerMoltenSaltSupplier;
import nuclearscience.common.settings.Constants;

public class TileMoltenSaltSupplier extends GenericTileTicking {

    protected CachedTileOutput output;

    public TileMoltenSaltSupplier() {
	super(DeferredRegisters.TILE_MOLTENSALTSUPPLIER.get());
	addComponent(new ComponentTickable().tickServer(this::tickServer));
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentElectrodynamic(this).voltage(Constants.MOLTENSALTSUPPLIER_VOLTAGE)
		.extractPower((x, y) -> TransferPack.EMPTY).output(Direction.UP).output(Direction.DOWN));
	addComponent(new ComponentInventory(this).size(1).slotFaces(0, Direction.values())
		.valid((slot, stack) -> RadiationRegister.get(stack.getItem()) != RadiationRegister.NULL));
	addComponent(new ComponentContainerProvider("container.moltensaltsupplier")
		.createMenu((id, player) -> new ContainerMoltenSaltSupplier(id, player,
			getComponent(ComponentType.Inventory), getCoordsArray())));
    }

    public void tickServer(ComponentTickable tickable) {
	if (output == null) {
	    output = new CachedTileOutput(world, pos.offset(null /* TODO: FIX THIS */));
	}
	if (tickable.getTicks() % 40 == 0) {
	    output.update();
	}
	ItemStack in = this.<ComponentInventory>getComponent(ComponentType.Inventory).getStackInSlot(0);
	if (in.getCount() > 0 && output.valid() && output.<TileEntity>getSafe() instanceof TileMSRReactorCore) {
	    TileMSRReactorCore core = output.getSafe();
	    if (TileMSRReactorCore.FUEL_CAPACITY - core.currentFuel >= 250) {
		in.shrink(1);
		core.currentFuel += 250;
	    }
	}
    }
}
