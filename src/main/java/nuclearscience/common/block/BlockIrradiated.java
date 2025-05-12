package nuclearscience.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;
import nuclearscience.common.block.subtype.SubtypeIrradiatedBlock;
import nuclearscience.registers.NuclearScienceBlocks;
import voltaic.api.radiation.util.IRadiationManager;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.registers.VoltaicCapabilities;

public class BlockIrradiated extends Block {

    public final SubtypeIrradiatedBlock subtype;

    public BlockIrradiated(SubtypeIrradiatedBlock subtype) {
        super(subtype.properties);
        this.subtype = subtype;
    }

    @Override
	public void randomTick(BlockState state, ServerWorld level, BlockPos pos, Random random) {
    	IRadiationManager manager = level.getCapability(VoltaicCapabilities.CAPABILITY_RADIATIONMANAGER).orElse(CapabilityUtils.EMPTY_MANAGER);
    	if(manager == CapabilityUtils.EMPTY_MANAGER) {
    		return;
    	}
        int radius = manager.getReachOfSource(level, pos);
        if (radius <= 0) {
            return;
        }
        BlockState other = level.getBlockState(pos);
        pos = pos.offset(randomInt(-radius, radius, level.random), randomInt(-radius, radius, level.random), randomInt(-radius, radius, level.random));
        if (isValidPlacement(other)) {
            level.setBlockAndUpdate(pos, getIrradiatedBlockstate(other));
        }

    }

    public static boolean isValidPlacement(BlockState state) {
        return state.is(Tags.Blocks.DIRT) || state.is(Blocks.GRASS_BLOCK) || state.is(BlockTags.LOGS);
    }

    public static BlockState getIrradiatedBlockstate(BlockState state) {
        if (state.is(Tags.Blocks.DIRT)) {
            return NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.soil).defaultBlockState();
        } else if (state.is(Blocks.GRASS_BLOCK)) {
            return NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.grass).defaultBlockState();
        } else if (state.is(BlockTags.LOGS)) {
            return NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.petrifiedwood).defaultBlockState();
        }

        return state;
    }
    
    private static int randomInt(int min, int max, Random random) {
    	return min + (int)(random.nextDouble() * ((max - min) + 1));
    }
    
}
