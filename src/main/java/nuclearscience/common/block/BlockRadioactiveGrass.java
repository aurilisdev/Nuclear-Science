package nuclearscience.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.SpreadableSnowyDirtBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nuclearscience.api.radiation.EffectRadiation;

public class BlockRadioactiveGrass extends SpreadableSnowyDirtBlock {

    public BlockRadioactiveGrass() {
	super(AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.GREEN).tickRandomly().hardnessAndResistance(0.6F)
		.sound(SoundType.PLANT));
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
	if (entityIn instanceof LivingEntity) {
	    LivingEntity living = (LivingEntity) entityIn;
	    living.addPotionEffect(new EffectInstance(EffectRadiation.INSTANCE, 20));
	}
    }
}
