package nuclearscience.common.block.connect;

import java.util.HashSet;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.tile.reactor.moltensalt.TileHeatExchanger;
import nuclearscience.common.tile.reactor.moltensalt.TileMSReactorCore;
import nuclearscience.common.tile.reactor.moltensalt.TileMoltenSaltPipe;
import voltaic.common.block.connect.AbstractRefreshingConnectBlock;
import voltaic.common.block.connect.EnumConnectType;

public class BlockMoltenSaltPipe extends AbstractRefreshingConnectBlock<TileMoltenSaltPipe> {

    public static final HashSet<Block> PIPESET = new HashSet<>();

    public final SubtypeMoltenSaltPipe pipe;

    public BlockMoltenSaltPipe(SubtypeMoltenSaltPipe pipe) {
        super(Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.METAL).strength(0.15f).dynamicShape(), 3);
        this.pipe = pipe;
        PIPESET.add(this);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileMoltenSaltPipe(pos, state);
    }

    @Override
    public TileMoltenSaltPipe getCableIfValid(BlockEntity tile) {
        if (tile instanceof TileMoltenSaltPipe pipe) {
            return pipe;
        }
        return null;
    }

    @Override
    public EnumConnectType getConnection(BlockState otherState, BlockEntity otherTile, TileMoltenSaltPipe thisCable, Direction dir) {
        EnumConnectType connection = EnumConnectType.NONE;
        if (otherTile instanceof TileMoltenSaltPipe) {
            connection = EnumConnectType.WIRE;
        } else if ((otherTile instanceof TileMSReactorCore && dir.getOpposite() == Direction.UP) || (otherTile instanceof TileHeatExchanger && dir.getOpposite() == Direction.DOWN)) {
            connection = EnumConnectType.INVENTORY;
        }
        return connection;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }
}
