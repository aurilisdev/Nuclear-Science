package nuclearscience.api.radiation;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import nuclearscience.References;
import nuclearscience.registers.NuclearScienceItems;

public class EffectRadiation extends MobEffect {

	public static final EffectRadiation INSTANCE = (EffectRadiation) new EffectRadiation().setRegistryName(References.ID, "radiation");

	protected EffectRadiation(MobEffectCategory typeIn, int liquidColorIn) {
		super(typeIn, liquidColorIn);
	}

	public EffectRadiation() {
		this(MobEffectCategory.HARMFUL, 5149489);
	}

	@Override
	public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
		if (entityLivingBaseIn.level.random.nextFloat() < 0.033) {
			entityLivingBaseIn.hurt(DamageSourceRadiation.INSTANCE, (float) (Math.pow(amplifier, 1.3) + 1));
			if (entityLivingBaseIn instanceof Player pl) {
				pl.causeFoodExhaustion(0.05F * (amplifier + 1));
			}
		}
	}

	@Override
	public List<ItemStack> getCurativeItems() {
		ArrayList<ItemStack> ret = new ArrayList<>();
		ret.add(new ItemStack(NuclearScienceItems.ITEM_ANTIDOTE.get()));
		return ret;
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}