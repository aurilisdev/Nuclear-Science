package nuclearscience.common.block;

import electrodynamics.prefab.block.GenericEntityBlockWaterloggable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import nuclearscience.common.tile.fissionreactor.TileMeltedReactor;

public class BlockMeltedReactor extends GenericEntityBlockWaterloggable {

	public BlockMeltedReactor() {
		super(Properties.copy(Blocks.IRON_BLOCK).strength(250.0f, 999.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion().harvestTool(ToolType.PICKAXE).harvestLevel(2));
	}

	@Override
	public TileEntity createTileEntity(BlockState arg0, IBlockReader arg1) {
		return new TileMeltedReactor();
	}

}