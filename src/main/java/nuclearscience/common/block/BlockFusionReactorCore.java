package nuclearscience.common.block;

import electrodynamics.common.block.BlockMachine;
import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import nuclearscience.common.tile.fusionreactor.TileFusionReactorCore;

public class BlockFusionReactorCore extends GenericMachineBlock {

  public BlockFusionReactorCore() {
    super(TileFusionReactorCore::new);
    registerDefaultState(stateDefinition.any().setValue(BlockMachine.ON, false));
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    return super.getStateForPlacement(context).setValue(BlockMachine.ON, false);
  }

  @Override
  protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
    super.createBlockStateDefinition(builder);
    builder.add(BlockMachine.ON);
  }

  @Override
  public RenderShape getRenderShape(BlockState state) {
    return RenderShape.MODEL;
  }

}
