package nuclearscience.common.block;

import electrodynamics.prefab.block.GenericEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import nuclearscience.api.fusion.IElectromagnet;
import nuclearscience.common.tile.TileElectromagneticSwitch;

public class BlockElectromagneticSwitch extends GenericEntityBlock implements IElectromagnet {
    private static final VoxelShape shape = Shapes.box(0, 0, 0, 1.0, 2.0 / 16.0, 1.0);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
	return shape;
    }

    public BlockElectromagneticSwitch() {
	super(Properties.of(Material.METAL).strength(3.5f, 20).requiresCorrectToolForDrops().noOcclusion()
		.isRedstoneConductor((p1, p2, p3) -> false));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
	return new TileElectromagneticSwitch(pos, state);
    }

}
