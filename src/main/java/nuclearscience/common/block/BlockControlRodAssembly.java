package nuclearscience.common.block;

import electrodynamics.prefab.block.GenericMachineBlock;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import nuclearscience.common.tile.TileControlRodAssembly;

public class BlockControlRodAssembly extends GenericMachineBlock {

    public BlockControlRodAssembly() {
	super(TileControlRodAssembly::new);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
	return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
	BlockEntity tile = worldIn.getBlockEntity(pos);
	if (tile instanceof TileControlRodAssembly assembly) {
	    assembly.insertion = assembly.insertion + 10;
	    if (assembly.insertion > 100) {
		assembly.insertion = 0;
	    }
	    assembly.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
	}
	return super.use(state, worldIn, pos, player, handIn, hit);
    }
}
