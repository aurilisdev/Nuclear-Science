package nuclearscience.common.block.connect;

import java.util.HashSet;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import nuclearscience.api.network.reactorlogistics.ILogisticsMember;
import nuclearscience.common.block.subtype.SubtypeReactorLogisticsCable;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileReactorLogisticsCable;
import voltaic.common.block.connect.AbstractRefreshingConnectBlock;
import voltaic.common.block.connect.EnumConnectType;

public class BlockReactorLogisticsCable extends AbstractRefreshingConnectBlock<TileReactorLogisticsCable> {

    public static final HashSet<Block> PIPESET = new HashSet<>();

    public final SubtypeReactorLogisticsCable cable;

    public BlockReactorLogisticsCable(SubtypeReactorLogisticsCable cable) {
        super(Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL).strength(0.15f).dynamicShape().harvestTool(ToolType.PICKAXE).harvestLevel(1), 5);
        this.cable = cable;
        PIPESET.add(this);
    }

    @Override
    public EnumConnectType getConnection(BlockState otherState, TileEntity otherTile, TileReactorLogisticsCable thisCable, Direction dir) {
        EnumConnectType connection = EnumConnectType.NONE;
        if (otherTile instanceof TileReactorLogisticsCable) {
            connection = EnumConnectType.WIRE;
        } else if (otherTile instanceof ILogisticsMember) {
        	ILogisticsMember member = (ILogisticsMember) otherTile;
        	if(thisCable.getNetwork() != null && member.isValidConnection(dir.getOpposite()) && member.canConnect(thisCable.getNetwork())) {
        		connection = EnumConnectType.INVENTORY;
        	}	
        }
        return connection;
    }

    @Nullable
    @Override
    public TileReactorLogisticsCable getCableIfValid(TileEntity tile) {
        if (tile instanceof TileReactorLogisticsCable) {
            return (TileReactorLogisticsCable) tile;
        }
        return null;
    }

    @Override
    public TileEntity createTileEntity(BlockState blockState, IBlockReader world) {
        return new TileReactorLogisticsCable();
    }

}
