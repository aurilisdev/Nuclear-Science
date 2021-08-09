package nuclearscience.common.block;

import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import nuclearscience.common.tile.TileControlRodAssembly;

public class BlockControlRodAssembly extends BlockGenericMachine {
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
	return new TileControlRodAssembly();
    }

    @Override
    @Deprecated
    public BlockRenderType getRenderType(BlockState state) {
	return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
	    BlockRayTraceResult hit) {
	TileEntity tile = worldIn.getTileEntity(pos);
	if (tile instanceof TileControlRodAssembly) {
	    TileControlRodAssembly assembly = (TileControlRodAssembly) tile;
	    assembly.insertion = assembly.insertion + 10;
	    if(assembly.insertion > 100)
	    {
		assembly.insertion = 0;
	    }
	    assembly.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
	}
	return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
