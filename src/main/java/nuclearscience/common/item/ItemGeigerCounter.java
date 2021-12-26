package nuclearscience.common.item;

import electrodynamics.api.sound.SoundAPI;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import nuclearscience.SoundRegister;
import nuclearscience.api.radiation.RadiationSystem;

public class ItemGeigerCounter extends Item {

	public ItemGeigerCounter(Properties properties) {
		super(properties);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		if (entityIn instanceof Player player) {
			if (!worldIn.isClientSide) {
				if ((isSelected || player.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof ItemGeigerCounter)
						&& RadiationSystem.radiationMap.get().containsKey(entityIn)) {
					player.displayClientMessage(
							new TranslatableComponent("message.geigercounter.text", RadiationSystem.radiationMap.get().get(entityIn)), true);
				}
			}
			if (worldIn.isClientSide && RadiationSystem.radiationMap.get().containsKey(entityIn)
					&& (isSelected || player.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof ItemGeigerCounter)) {
				double amount = RadiationSystem.radiationMap.get().get(entityIn);
				if (worldIn.random.nextFloat() * 50 * 60.995 / 3 < amount) {
					SoundAPI.playSound(SoundRegister.SOUND_GEIGER.get(), SoundSource.BLOCKS, 1, 1, player.blockPosition());
				}
			}
		}
	}
}
