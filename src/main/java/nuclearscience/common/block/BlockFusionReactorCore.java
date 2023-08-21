package nuclearscience.common.block;

import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.tile.TileFusionReactorCore;

public class BlockFusionReactorCore extends GenericMachineBlock {

	public BlockFusionReactorCore() {
		super(TileFusionReactorCore::new);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

}
