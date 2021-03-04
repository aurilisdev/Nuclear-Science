package nuclearscience.common.tile;

import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.api.tile.electric.CapabilityElectrodynamic;
import electrodynamics.api.tile.electric.IElectrodynamic;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.tile.generic.GenericTileBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.settings.Constants;

public class TileFusionReactorCore extends GenericTileBase implements ITickableTileBase, IElectrodynamic {
    private double joules;
    private int timeLeft = 0;
    public int deuterium;
    public int tritium;

    public TileFusionReactorCore() {
	super(DeferredRegisters.TILE_FUSIONREACTORCORE.get());
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
	if (capability == CapabilityElectrodynamic.ELECTRODYNAMIC
		&& (facing == Direction.DOWN || facing == Direction.UP)) {
	    return (LazyOptional<T>) LazyOptional.of(() -> this);
	}
	return super.getCapability(capability, facing);
    }

    @Override
    public TransferPack extractPower(TransferPack transfer, boolean debug) {
	return TransferPack.EMPTY;
    }

    @Override
    public void tickServer() {
	if (world.getWorldInfo().getDayTime() % 20 == 0) {
	    sendCustomPacket();
	}
	if (tritium > 0 && deuterium > 0 && timeLeft <= 0 && joules > Constants.FUSIONREACTOR_USAGE_PER_TICK) {
	    deuterium -= 1;
	    tritium -= 1;
	    timeLeft = 15 * 20;
	}
	if (timeLeft > 0 && joules > Constants.FUSIONREACTOR_USAGE_PER_TICK) {
	    for (Direction dir : Direction.values()) {
		if (dir != Direction.UP && dir != Direction.DOWN) {
		    BlockPos offset = pos.offset(dir);
		    BlockState state = world.getBlockState(offset);
		    if (state.getBlock() == DeferredRegisters.blockPlasma) {
			TileEntity tile = world.getTileEntity(offset);
			if (tile instanceof TilePlasma && ((TilePlasma) tile).ticksExisted > 30) {
			    ((TilePlasma) tile).ticksExisted = 0;
			}
		    } else if (state.getBlock() == Blocks.AIR) {
			world.setBlockState(offset, DeferredRegisters.blockPlasma.getDefaultState());
		    }
		}
	    }
	    joules -= Constants.FUSIONREACTOR_USAGE_PER_TICK;
	}
	timeLeft--;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
	compound.putInt("deuterium", deuterium);
	compound.putInt("tritium", tritium);
	return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
	super.read(state, compound);
	deuterium = compound.getInt("deuterium");
	tritium = compound.getInt("tritium");
    }

    @Override
    public CompoundNBT writeCustomPacket() {
	CompoundNBT nbt = super.writeCustomPacket();
	nbt.putInt("deuterium", deuterium);
	nbt.putInt("tritium", tritium);
	return nbt;
    }

    @Override
    public void readCustomPacket(CompoundNBT nbt) {
	super.readCustomPacket(nbt);
	deuterium = nbt.getInt("deuterium");
	tritium = nbt.getInt("tritium");
    }

    @Override
    public double getVoltage() {
	return IElectrodynamic.super.getVoltage() * 4;
    }

    @Override
    @Deprecated
    public void setJoulesStored(double joules) {
	this.joules = joules;
    }

    @Override
    public double getJoulesStored() {
	return joules;
    }

    @Override
    public double getMaxJoulesStored() {
	return Constants.FUSIONREACTOR_USAGE_PER_TICK * 20.0;
    }
}
