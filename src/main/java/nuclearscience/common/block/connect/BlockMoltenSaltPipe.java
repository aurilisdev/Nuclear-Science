package nuclearscience.common.block.connect;

import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
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
        super(Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL).strength(0.15f).dynamicShape().harvestTool(ToolType.PICKAXE).harvestLevel(1), 3);
        this.pipe = pipe;
        PIPESET.add(this);
    }
    
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    	return new TileMoltenSaltPipe();
    }

    @Override
    public TileMoltenSaltPipe getCableIfValid(TileEntity tile) {
        if (tile instanceof TileMoltenSaltPipe) {
            return (TileMoltenSaltPipe) tile;
        }
        return null;
    }

    @Override
    public EnumConnectType getConnection(BlockState otherState, TileEntity otherTile, TileMoltenSaltPipe thisCable, Direction dir) {
        EnumConnectType connection = EnumConnectType.NONE;
        if (otherTile instanceof TileMoltenSaltPipe) {
            connection = EnumConnectType.WIRE;
        } else if (otherTile instanceof TileMSReactorCore && dir.getOpposite() == Direction.UP || otherTile instanceof TileHeatExchanger && dir.getOpposite() == Direction.DOWN) {
            connection = EnumConnectType.INVENTORY;
        }
        return connection;
    }

}
