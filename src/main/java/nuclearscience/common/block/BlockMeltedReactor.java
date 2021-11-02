package nuclearscience.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.common.ToolType;
import nuclearscience.common.tile.TileMeltedReactor;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BlockMeltedReactor extends Block {

    public BlockMeltedReactor() {
	super(Properties.of(Material.METAL).strength(250.0f, 999.0f).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).noOcclusion());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
	return true;
    }

    @Override
    public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
	return new TileMeltedReactor();
    }
}
