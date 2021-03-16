package nuclearscience.common.tile;

import electrodynamics.api.tile.GenericTileTicking;
import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentContainerProvider;
import electrodynamics.api.tile.components.type.ComponentElectrodynamic;
import electrodynamics.api.tile.components.type.ComponentInventory;
import electrodynamics.api.tile.components.type.ComponentPacketHandler;
import electrodynamics.api.tile.components.type.ComponentTickable;
import electrodynamics.api.utilities.object.CachedTileOutput;
import electrodynamics.api.utilities.object.TransferPack;
import electrodynamics.common.network.ElectricityUtilities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import nuclearscience.DeferredRegisters;
import nuclearscience.api.radiation.IRadioactiveObject;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.common.inventory.container.ContainerRadioisotopeGenerator;
import nuclearscience.common.settings.Constants;

public class TileRadioisotopeGenerator extends GenericTileTicking {

    protected CachedTileOutput output1;
    protected CachedTileOutput output2;

    public TileRadioisotopeGenerator() {
	super(DeferredRegisters.TILE_RADIOISOTOPEGENERATOR.get());
	addComponent(new ComponentTickable().addTickServer(this::tickServer));
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentElectrodynamic(this).setVoltage(Constants.RADIOISOTOPEGENERATOR_VOLTAGE)
		.setFunctionExtractPower((x, y) -> TransferPack.EMPTY).addOutputDirection(Direction.UP).addOutputDirection(Direction.DOWN));
	addComponent(new ComponentInventory().setInventorySize(1).addSlotsOnFace(Direction.UP, 0)
		.setItemValidPredicate((slot, stack) -> RadiationRegister.get(stack.getItem()) != RadiationRegister.NULL));
	addComponent(new ComponentContainerProvider("container.radioisotopegenerator").setCreateMenuFunction(
		(id, player) -> new ContainerRadioisotopeGenerator(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
    }

    public void tickServer(ComponentTickable tickable) {
	if (output1 == null) {
	    output1 = new CachedTileOutput(world, pos.offset(Direction.UP));
	}
	if (output2 == null) {
	    output2 = new CachedTileOutput(world, pos.offset(Direction.DOWN));
	}
	ItemStack in = this.<ComponentInventory>getComponent(ComponentType.Inventory).getStackInSlot(0);
	IRadioactiveObject rad = RadiationRegister.get(in.getItem());
	double currentOutput = in.getCount() * Constants.RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER * rad.getRadiationStrength();
	if (currentOutput > 0) {
	    TransferPack transfer = TransferPack.ampsVoltage(currentOutput / (Constants.RADIOISOTOPEGENERATOR_VOLTAGE * 2.0),
		    Constants.RADIOISOTOPEGENERATOR_VOLTAGE);
	    ElectricityUtilities.receivePower(output1.get(), Direction.DOWN, transfer, false);
	    ElectricityUtilities.receivePower(output2.get(), Direction.UP, transfer, false);
	}
    }
}
