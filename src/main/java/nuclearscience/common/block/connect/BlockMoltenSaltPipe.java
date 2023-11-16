package nuclearscience.common.block.connect;

import java.util.HashSet;

import electrodynamics.api.network.cable.IRefreshableCable;
import electrodynamics.common.block.connect.util.AbstractRefreshingConnectBlock;
import electrodynamics.common.block.connect.util.EnumConnectType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import nuclearscience.api.network.moltensalt.IMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.tile.msreactor.TileHeatExchanger;
import nuclearscience.common.tile.msreactor.TileMSReactorCore;
import nuclearscience.common.tile.saltpipe.TileMoltenSaltPipe;

public class BlockMoltenSaltPipe extends AbstractRefreshingConnectBlock {

	public static final HashSet<Block> PIPESET = new HashSet<>();

	public final SubtypeMoltenSaltPipe pipe;

	public BlockMoltenSaltPipe(SubtypeMoltenSaltPipe pipe) {
		super(Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL).strength(0.15f).dynamicShape(), 3);
		this.pipe = pipe;
		PIPESET.add(this);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileMoltenSaltPipe(pos, state);
	}

	@Override
	public IRefreshableCable getCableIfValid(BlockEntity tile) {
		if (tile instanceof IMoltenSaltPipe pipe) {
			return pipe;
		}
		return null;
	}

	@Override
	public BlockState refreshConnections(BlockState otherState, BlockEntity tile, BlockState state, Direction dir) {
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

}
