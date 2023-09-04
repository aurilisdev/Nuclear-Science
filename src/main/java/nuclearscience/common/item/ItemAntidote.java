package nuclearscience.common.item;

import java.util.function.Supplier;

import electrodynamics.common.item.ItemElectrodynamics;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ItemAntidote extends ItemElectrodynamics {

	public ItemAntidote(Properties properties, Supplier<CreativeModeTab> creativeTab) {
		super(properties, creativeTab);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
		if (!worldIn.isClientSide) {
			entityLiving.curePotionEffects(stack);
		}
		if (entityLiving instanceof ServerPlayer serverplayerentity) {
			CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, stack);
			serverplayerentity.awardStat(Stats.ITEM_USED.get(this));
		}
		if (entityLiving instanceof Player pl && !pl.getAbilities().instabuild) {
			stack.shrink(1);
		}
		return stack;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 32;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.DRINK;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		return ItemUtils.startUsingInstantly(worldIn, playerIn, handIn);
	}

}
