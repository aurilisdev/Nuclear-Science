package nuclearscience.common.tile.reactor.moltensalt;

import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.block.connect.BlockMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.network.MoltenSaltNetwork;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.prefab.tile.types.GenericRefreshingConnectTile;

public class TileMoltenSaltPipe extends GenericRefreshingConnectTile<SubtypeMoltenSaltPipe, TileMoltenSaltPipe, MoltenSaltNetwork> {

    public SubtypeMoltenSaltPipe pipe = null;

    public TileMoltenSaltPipe(BlockPos pos, BlockState state) {
        super(NuclearScienceTiles.TILE_MOLTENSALTPIPE.get(), pos, state);
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
    protected void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putInt("ord", getCableType().ordinal());
    }

    @Override
	public void load(CompoundTag compound) {
        super.load(compound);
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
