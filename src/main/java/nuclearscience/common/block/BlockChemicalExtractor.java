package nuclearscience.common.block;

import electrodynamics.common.block.BlockGenericMachine;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.tile.TileChemicalExtractor;

public class BlockChemicalExtractor extends BlockGenericMachine {
    @Override
    public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
	return new TileChemicalExtractor();
    }

}
