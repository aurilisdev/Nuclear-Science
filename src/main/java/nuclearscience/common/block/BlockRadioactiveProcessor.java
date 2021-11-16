package nuclearscience.common.block;

import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.tile.TileRadioactiveProcessor;

public class BlockRadioactiveProcessor extends GenericMachineBlock {

    public BlockRadioactiveProcessor() {
	super(TileRadioactiveProcessor::new);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
	return RenderShape.ENTITYBLOCK_ANIMATED;
    }

}
