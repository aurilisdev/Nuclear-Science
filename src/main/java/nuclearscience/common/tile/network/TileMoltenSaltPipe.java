package nuclearscience.common.tile.network;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.block.connect.BlockMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.tile.generic.GenericTileMoltenSaltPipe;

public class TileMoltenSaltPipe extends GenericTileMoltenSaltPipe {
    public double transmit = 0;

    public TileMoltenSaltPipe() {
	super(DeferredRegisters.TILE_MOLTENSALTPIPE.get());
    }

    public SubtypeMoltenSaltPipe pipe = null;

    @Override
    public SubtypeMoltenSaltPipe getPipeType() {
	if (pipe == null) {
	    pipe = ((BlockMoltenSaltPipe) getBlockState().getBlock()).pipe;
	}
	return pipe;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
	compound.putInt("ord", getPipeType().ordinal());
	return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
	super.read(state, compound);
	pipe = SubtypeMoltenSaltPipe.values()[compound.getInt("ord")];
    }

    @Override
    protected void writeCustomPacket(CompoundNBT nbt) {
	nbt.putDouble("transmit", transmit);
    }

    @Override
    protected void readCustomPacket(CompoundNBT nbt) {
	transmit = nbt.getDouble("transmit");
    }
}
