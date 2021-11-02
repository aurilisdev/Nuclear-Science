package nuclearscience.common.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import nuclearscience.api.radiation.EffectRadiation;

public class BlockRadioactiveSoil extends SnowyDirtBlock {

    public BlockRadioactiveSoil() {
	super(BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_GREEN).randomTicks().strength(0.6F)
		.sound(SoundType.GRASS));
    }

    @Override
    public void stepOn(Level worldIn, BlockPos pos, Entity entityIn) {
	if (entityIn instanceof LivingEntity) {
	    LivingEntity living = (LivingEntity) entityIn;
	    living.addEffect(new MobEffectInstance(EffectRadiation.INSTANCE, (int) (20 * 40 * worldIn.random.nextFloat())));
	}
    }
}
