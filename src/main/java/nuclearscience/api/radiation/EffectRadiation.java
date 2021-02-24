package nuclearscience.api.radiation;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import nuclearscience.DeferredRegisters;
import nuclearscience.References;

public class EffectRadiation extends Effect {

    public static final EffectRadiation INSTANCE = (EffectRadiation) new EffectRadiation()
	    .setRegistryName(References.ID, "radiation");

    protected EffectRadiation(EffectType typeIn, int liquidColorIn) {
	super(typeIn, liquidColorIn);
    }

    public EffectRadiation() {
	this(EffectType.HARMFUL, 5149489);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
	if (entityLivingBaseIn.world.rand.nextFloat() < 0.033) {
	    entityLivingBaseIn.attackEntityFrom(DamageSourceRadiation.INSTANCE, (float) (Math.pow(amplifier, 1.3) + 1));
	    if (entityLivingBaseIn instanceof PlayerEntity) {
		((PlayerEntity) entityLivingBaseIn).addExhaustion(0.05F * (amplifier + 1));
	    }
	}
    }

    @Override
    public List<ItemStack> getCurativeItems() {
	ArrayList<ItemStack> ret = new ArrayList<>();
	ret.add(new ItemStack(DeferredRegisters.ITEM_ANTIDOTE.get()));
	return ret;
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
	return true;
    }
}
