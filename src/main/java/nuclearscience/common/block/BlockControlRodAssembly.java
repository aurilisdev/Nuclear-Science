package nuclearscience.common.block;

import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import nuclearscience.common.tile.TileControlRodAssembly;

public class BlockControlRodAssembly extends GenericMachineBlock {

	public BlockControlRodAssembly() {
		super(world -> new TileControlRodAssembly());
	}

	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}

}