package nuclearscience.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.api.radiation.RadiationSystem;
import voltaic.api.radiation.util.IRadiationRecipient;
import voltaic.registers.VoltaicCapabilities;

public class BlockRadioactiveAir extends AirBlock {

    public BlockRadioactiveAir() {
        super(Blocks.AIR.properties().noCollission().air().randomTicks());
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        RadiationSystem.removeRadiationSource(level, pos, true);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (level.getLevelData().getGameTime() % 10 == 0 && !level.isClientSide && entity instanceof LivingEntity living) {
            IRadiationRecipient cap = living.getCapability(VoltaicCapabilities.CAPABILITY_RADIATIONRECIPIENT);
            if (cap == null) {
                return;
            }

            cap.recieveRadiation(living, 20, 1);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        if (random.nextFloat() < 0.01F) {
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
    }

}