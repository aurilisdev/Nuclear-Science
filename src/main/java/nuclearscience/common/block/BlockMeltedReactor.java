package nuclearscience.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import nuclearscience.common.tile.reactor.fission.TileMeltedReactor;
import voltaic.prefab.block.GenericEntityBlockWaterloggable;

public class BlockMeltedReactor extends GenericEntityBlockWaterloggable {

	public BlockMeltedReactor() {
		super(Properties.copy(Blocks.IRON_BLOCK).strength(250.0f, 999.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion());
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileMeltedReactor();
	}

}
