package nuclearscience.common.tile.reactor.moltensalt;

import java.util.Set;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import nuclearscience.common.block.connect.BlockMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.network.MoltenSaltNetwork;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.prefab.tile.types.GenericRefreshingConnectTile;

public class TileMoltenSaltPipe extends GenericRefreshingConnectTile<SubtypeMoltenSaltPipe, TileMoltenSaltPipe, MoltenSaltNetwork> {

    public SubtypeMoltenSaltPipe pipe = null;

    public TileMoltenSaltPipe() {
        super(NuclearScienceTiles.TILE_MOLTENSALTPIPE.get());
    }

    @Override
    public SubtypeMoltenSaltPipe getCableType() {
        if (pipe == null) {
            pipe = ((BlockMoltenSaltPipe) getBlockState().getBlock()).pipe;
        }
        return pipe;
    }

    @Override
    public double getMaxTransfer() {
        return 0;
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        compound.putInt("ord", getCableType().ordinal());
        return super.save(compound);
    }

    @Override
	public void load(BlockState state, CompoundNBT compound) {
        super.load(state, compound);
        pipe = SubtypeMoltenSaltPipe.values()[compound.getInt("ord")];
    }

    @Override
    public MoltenSaltNetwork createInstanceConductor(Set<TileMoltenSaltPipe> set) {
        return new MoltenSaltNetwork(set);
    }

    @Override
    public MoltenSaltNetwork createInstance(Set<MoltenSaltNetwork> set) {
        return new MoltenSaltNetwork(set);
    }

    @Override
    public void destroyViolently() {

    }
}
