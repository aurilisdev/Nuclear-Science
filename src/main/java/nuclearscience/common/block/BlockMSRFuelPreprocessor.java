package nuclearscience.common.block;

import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockMSRFuelPreprocessor extends GenericMachineBlock {

	public BlockMSRFuelPreprocessor(BlockEntitySupplier<BlockEntity> blockEntitySupplier) {
		super(blockEntitySupplier);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Block.box(0, 0, 0, 16, 13, 16);
	}

}
