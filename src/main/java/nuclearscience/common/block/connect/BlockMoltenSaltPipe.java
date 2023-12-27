package nuclearscience.common.block.connect;

import java.util.HashSet;

import electrodynamics.api.network.cable.IRefreshableCable;
import electrodynamics.common.block.connect.util.AbstractRefreshingConnectBlock;
import electrodynamics.common.block.connect.util.EnumConnectType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.state.EnumProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import nuclearscience.api.network.moltensalt.IMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.tile.msreactor.TileHeatExchanger;
import nuclearscience.common.tile.msreactor.TileMSReactorCore;
import nuclearscience.common.tile.saltpipe.TileMoltenSaltPipe;

public class BlockMoltenSaltPipe extends AbstractRefreshingConnectBlock {

	public static final HashSet<Block> PIPESET = new HashSet<>();

	public final SubtypeMoltenSaltPipe pipe;

	public BlockMoltenSaltPipe(SubtypeMoltenSaltPipe pipe) {
		super(Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL).strength(0.15f).dynamicShape().requiresCorrectToolForDrops().harvestLevel(1).harvestTool(ToolType.PICKAXE), 3);
		this.pipe = pipe;
		PIPESET.add(this);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}

	@Override
	public IRefreshableCable getCableIfValid(TileEntity tile) {
		if (tile instanceof IMoltenSaltPipe) {
			return (IMoltenSaltPipe) tile;
		}
		return null;
	}

	@Override
	public BlockState refreshConnections(BlockState otherState, TileEntity tile, BlockState state, Direction dir) {
		EnumProperty<EnumConnectType> property = FACING_TO_PROPERTY_MAP.get(dir);
		if (tile instanceof IMoltenSaltPipe) {
			return state.setValue(property, EnumConnectType.WIRE);
		}
		if (tile instanceof TileMSReactorCore && dir.getOpposite() == Direction.UP || tile instanceof TileHeatExchanger && dir.getOpposite() == Direction.DOWN) {
			return state.setValue(property, EnumConnectType.INVENTORY);
		}
		if (state.hasProperty(property)) {
			return state.setValue(property, EnumConnectType.NONE);
		}
		return state;
	}

	@Override
	public TileEntity createTileEntity(BlockState arg0, IBlockReader arg1) {
		return new TileMoltenSaltPipe();
	}

}