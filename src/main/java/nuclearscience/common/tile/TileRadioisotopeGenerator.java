package nuclearscience.common.tile;

import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.api.tile.electric.CapabilityElectrodynamic;
import electrodynamics.api.tile.electric.IElectrodynamic;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.tile.generic.GenericTileInventory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import nuclearscience.DeferredRegisters;
import nuclearscience.api.radiation.IRadioactiveObject;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.common.inventory.container.ContainerRadioisotopeGenerator;
import nuclearscience.common.settings.Constants;

public class TileRadioisotopeGenerator extends GenericTileInventory implements ITickableTileBase, IElectrodynamic {
    public static final int[] SLOTS_INPUT = new int[] { 0 };

    protected CachedTileOutput output1;
    protected CachedTileOutput output2;

    public TileRadioisotopeGenerator() {
	super(DeferredRegisters.TILE_RADIOISOTOPEGENERATOR.get());
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
	if (capability == CapabilityElectrodynamic.ELECTRODYNAMIC
		&& (facing == Direction.UP || facing == Direction.DOWN)) {
	    return (LazyOptional<T>) LazyOptional.of(() -> this);
	}
	return super.getCapability(capability, facing);
    }

    @Override
    public double getVoltage() {
	return Constants.RADIOISOTOPEGENERATOR_VOLTAGE;
    }

    @Override
    public void tickServer() {
	if (output1 == null) {
	    output1 = new CachedTileOutput(world, new BlockPos(pos).offset(Direction.UP));
	}
	if (output2 == null) {
	    output2 = new CachedTileOutput(world, new BlockPos(pos).offset(Direction.DOWN));
	}
	ItemStack in = getStackInSlot(0);
	IRadioactiveObject rad = RadiationRegister.get(in.getItem());
	double currentOutput = in.getCount() * Constants.RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER
		* rad.getRadiationStrength();
	if (currentOutput > 0) {
	    TransferPack transfer = TransferPack.ampsVoltage(currentOutput / (getVoltage() * 2.0), getVoltage());
	    ElectricityUtilities.receivePower(output1.get(), Direction.DOWN, transfer, false);
	    ElectricityUtilities.receivePower(output2.get(), Direction.UP, transfer, false);
	}
    }

    @Override
    public int getSizeInventory() {
	return 1;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
	return side == Direction.UP ? SLOTS_INPUT : SLOTS_EMPTY;
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
	return new ContainerRadioisotopeGenerator(id, player, this, inventorydata);
    }

    protected final IIntArray inventorydata = new IIntArray() {
	@Override
	public int get(int index) {
	    switch (index) {
	    default:
		return 0;
	    }
	}

	@Override
	public void set(int index, int value) {
	}

	@Override
	public int size() {
	    return 0;
	}
    };

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
	return RadiationRegister.get(stack.getItem()) != RadiationRegister.NULL;
    }

    @Override
    public ITextComponent getDisplayName() {
	return new TranslationTextComponent("container.radioisotopegenerator");
    }

    @Override
    public TransferPack extractPower(TransferPack transfer, boolean debug) {
	return TransferPack.EMPTY;
    }

    @Override
    public double getJoulesStored() {
	return 0;
    }

    @Override
    public double getMaxJoulesStored() {
	return 0;
    }

}
