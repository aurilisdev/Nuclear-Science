package nuclearscience.common.block;

import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.tile.TileControlRodAssembly;

public class BlockControlRodAssembly extends GenericMachineBlock {

	public BlockControlRodAssembly() {
		super(TileControlRodAssembly::new);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

}
