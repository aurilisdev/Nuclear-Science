package nuclearscience.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import nuclearscience.References;
import nuclearscience.api.radiation.EffectRadiation;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class BlockRadioactiveAir extends AirBlock {

	public BlockRadioactiveAir() {
		super(Properties.of(Material.AIR).noCollission().noDrops().air());
	}

	@Override
	public void entityInside(BlockState state, Level lvl, BlockPos pos, Entity entityIn) {
		if (entityIn instanceof LivingEntity living) {
			living.addEffect(new MobEffectInstance(EffectRadiation.INSTANCE, (int) (20 * 40 * lvl.random.nextFloat())));
		}
	}

}