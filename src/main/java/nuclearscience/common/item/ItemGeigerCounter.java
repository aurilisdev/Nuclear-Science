package nuclearscience.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import nuclearscience.api.radiation.RadiationSystem;

public class ItemGeigerCounter extends Item {

	public ItemGeigerCounter(Properties properties) {
		super(properties);
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		if (!worldIn.isRemote) {
			if (entityIn instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) entityIn;
				if (isSelected || player.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getItem() instanceof ItemGeigerCounter) {
					if (RadiationSystem.radiationMap.containsKey(entityIn)) {
						player.sendStatusMessage(new TranslationTextComponent("message.geigercounter.text", RadiationSystem.radiationMap.get(entityIn)), true);
					}
				}
			}
		}
	}
}
