package nuclearscience.api.radiation;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import nuclearscience.References;
import nuclearscience.registers.NuclearScienceItems;

public class EffectRadiation extends Effect {

	public static final EffectRadiation INSTANCE = (EffectRadiation) new EffectRadiation().setRegistryName(References.ID, "radiation");

	protected EffectRadiation(EffectType typeIn, int liquidColorIn) {
		super(typeIn, liquidColorIn);
	}

	public EffectRadiation() {
		this(EffectType.HARMFUL, 5149489);
	}

	@Override
	public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
		if (entityLivingBaseIn.level.random.nextFloat() < 0.033) {
			entityLivingBaseIn.hurt(DamageSourceRadiation.INSTANCE, (float) (Math.pow(amplifier, 1.3) + 1));
			if (entityLivingBaseIn instanceof PlayerEntity) {
				((PlayerEntity) entityLivingBaseIn).causeFoodExhaustion(0.05F * (amplifier + 1));
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
