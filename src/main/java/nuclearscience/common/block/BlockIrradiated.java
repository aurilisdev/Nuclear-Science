package nuclearscience.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.block.subtype.SubtypeIrradiatedBlock;
import nuclearscience.registers.NuclearScienceBlocks;
import voltaic.api.radiation.RadiationManager;
import voltaic.registers.VoltaicAttachmentTypes;

public class BlockIrradiated extends Block {

    public final SubtypeIrradiatedBlock subtype;

    public BlockIrradiated(SubtypeIrradiatedBlock subtype) {
        super(subtype.properties);
        this.subtype = subtype;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        RadiationManager manager = level.getData(VoltaicAttachmentTypes.RADIATION_MANAGER);
        int radius = manager.getReachOfSource(level, pos);
        if (radius <= 0) {
            return;
        }
        BlockState other = level.getBlockState(pos);
        pos = pos.offset(level.random.nextIntBetweenInclusive(-radius, radius), level.random.nextIntBetweenInclusive(-radius, radius), level.random.nextIntBetweenInclusive(-radius, radius));
        if (isValidPlacement(other)) {
            level.setBlockAndUpdate(pos, getIrradiatedBlockstate(other));
        }

    }

    public static boolean isValidPlacement(BlockState state) {
        return state.is(BlockTags.DIRT) || state.is(Blocks.GRASS_BLOCK) || state.is(BlockTags.LOGS);
    }

    public static BlockState getIrradiatedBlockstate(BlockState state) {
        if (state.is(BlockTags.DIRT)) {
            return NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.soil).defaultBlockState();
        } else if (state.is(Blocks.GRASS_BLOCK)) {
            return NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.grass).defaultBlockState();
        } else if (state.is(BlockTags.LOGS)) {
            return NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.petrifiedwood).defaultBlockState();
        }

        return state;
    }
}
