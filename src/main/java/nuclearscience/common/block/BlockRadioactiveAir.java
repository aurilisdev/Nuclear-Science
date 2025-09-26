package nuclearscience.common.block;

import java.util.Random;

import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import voltaic.api.radiation.RadiationSystem;
import voltaic.api.radiation.util.IRadiationRecipient;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.registers.VoltaicCapabilities;

public class BlockRadioactiveAir extends AirBlock {

    public BlockRadioactiveAir() {
        super(Properties.copy(Blocks.AIR).noCollission().air().randomTicks());
    }

    @Override
	public void onRemove(BlockState state, World level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        RadiationSystem.removeRadiationSource(level, pos, true);
    }

    @Override
    public void entityInside(BlockState state, World level, BlockPos pos, Entity entity) {
    	if (level.getLevelData().getGameTime() % 10 == 0 && !level.isClientSide && entity instanceof LivingEntity) {
    		LivingEntity living = (LivingEntity) entity;
            IRadiationRecipient cap = living.getCapability(VoltaicCapabilities.CAPABILITY_RADIATIONRECIPIENT).orElse(CapabilityUtils.EMPTY_RADIATION_REPIPIENT);
            if (cap == CapabilityUtils.EMPTY_RADIATION_REPIPIENT) {
                return;
            }

            cap.recieveRadiation(living, 20, 1);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld level, BlockPos pos, Random random) {
        super.randomTick(state, level, pos, random);
        if (random.nextFloat() < 0.01F) {
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
    }

}