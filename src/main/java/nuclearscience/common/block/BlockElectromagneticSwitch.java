package nuclearscience.common.block;

import java.util.Arrays;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import nuclearscience.api.fusion.IElectromagnet;
import nuclearscience.common.tile.TileElectromagneticSwitch;

public class BlockElectromagneticSwitch extends BaseEntityBlock implements IElectromagnet {
    private static final VoxelShape shape = Shapes.box(0, 0, 0, 1.0, 2.0 / 16.0, 1.0);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
	return shape;
    }

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    @Override
    public List<ItemStack> getDrops(BlockState state, Builder builder) {
	return Arrays.asList(new ItemStack(this));
    }

    public BlockElectromagneticSwitch() {
	super(Properties.of(Material.METAL).strength(3.5f, 20).requiresCorrectToolForDrops().noOcclusion()
		.isRedstoneConductor((p1, p2, p3) -> false));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
	return new TileElectromagneticSwitch(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
	return RenderShape.MODEL;
    }

}
