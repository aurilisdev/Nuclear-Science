package nuclearscience.common.item;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import nuclearscience.api.radiation.RadiationSystem;

public class ItemGeigerCounter extends Item {

    public ItemGeigerCounter(Properties properties) {
	super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
	super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	if (!worldIn.isClientSide && entityIn instanceof Player) {
	    Player player = (Player) entityIn;
	    if ((isSelected || player.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof ItemGeigerCounter)
		    && RadiationSystem.radiationMap.containsKey(entityIn)) {
		player.displayClientMessage(new TranslatableComponent("message.geigercounter.text", RadiationSystem.radiationMap.get(entityIn)),
			true);
	    }
	}
    }
}
