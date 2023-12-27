package nuclearscience.common.item;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.sound.SoundAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceSounds;

public class ItemGeigerCounter extends Item {

	public ItemGeigerCounter(Properties properties) {
		super(properties);
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		if (entityIn instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entityIn;
			if (!worldIn.isClientSide) {
				if ((isSelected || player.getItemBySlot(EquipmentSlotType.OFFHAND).getItem() instanceof ItemGeigerCounter) && RadiationSystem.radiationMap.get().containsKey(entityIn)) {
					player.displayClientMessage(NuclearTextUtils.chatMessage("geigercounter.text", ChatFormatter.formatDecimals(RadiationSystem.radiationMap.get().get(entityIn), 3)), true);
				}
			}
			if (worldIn.isClientSide && RadiationSystem.radiationMap.get().containsKey(entityIn) && (isSelected || player.getItemBySlot(EquipmentSlotType.OFFHAND).getItem() instanceof ItemGeigerCounter)) {
				double amount = RadiationSystem.radiationMap.get().get(entityIn);
				if (worldIn.random.nextFloat() * 50 * 60.995 / 3 < amount) {
					SoundAPI.playSound(NuclearScienceSounds.SOUND_GEIGER.get(), SoundCategory.BLOCKS, 1, 1, player.blockPosition());
				}
			}
		}
	}
}