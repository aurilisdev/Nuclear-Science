package nuclearscience.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import nuclearscience.common.tile.TileMeltedReactor;

public class BlockMeltedReactor extends Block {

    public BlockMeltedReactor() {
	super(Properties.create(Material.IRON).hardnessAndResistance(250.0f, 999.0f).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).notSolid());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
	return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
	return new TileMeltedReactor();
    }
}
