package nuclearscience.common.item;

import java.util.function.Supplier;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceSounds;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.item.IItemElectric;
import voltaic.api.radiation.util.IRadiationRecipient;
import voltaic.api.radiation.util.RadioactiveObject;
import voltaic.prefab.item.ElectricItemProperties;
import voltaic.prefab.item.ItemElectric;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.registers.VoltaicCapabilities;

public class ItemGeigerCounter extends ItemElectric {

	public static final double POWER_USAGE = 20;

	public ItemGeigerCounter(ElectricItemProperties properties, Supplier<ItemGroup> creativeTab) {
		super(properties, creativeTab);
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		if (entityIn instanceof PlayerEntity && !worldIn.isClientSide) {

			PlayerEntity player = (PlayerEntity) entityIn;

			boolean noPower = getJoulesStored(stack) < POWER_USAGE;

			IRadiationRecipient capability = player.getCapability(VoltaicCapabilities.CAPABILITY_RADIATIONRECIPIENT).orElse(CapabilityUtils.EMPTY_RADIATION_REPIPIENT);
			if (capability == CapabilityUtils.EMPTY_RADIATION_REPIPIENT) {
				return;
			}

			RadioactiveObject recievedRads = capability.getRecievedRadiation(player);

			if (isSelected || player.getItemBySlot(EquipmentSlotType.OFFHAND).getItem() instanceof ItemGeigerCounter) {
				if (noPower) {
					player.displayClientMessage(NuclearTextUtils.chatMessage("geigercounter.nopower"), true);
				} else {
					player.displayClientMessage(ChatFormatter.getChatDisplay(recievedRads.amount(), DisplayUnits.RAD, 3, true), true);
				}

			}

			if (!noPower && recievedRads.amount() > 0 && worldIn.random.nextFloat() * 50 * 60.995 / 3 < recievedRads.amount()) {

				SoundEvent sound;
				switch (worldIn.random.nextInt(6)) {
				case 1:
					sound = NuclearScienceSounds.SOUND_GEIGERCOUNTER_2.get();
					break;
				case 2:
					sound = NuclearScienceSounds.SOUND_GEIGERCOUNTER_3.get();
					break;
				case 3:
					sound = NuclearScienceSounds.SOUND_GEIGERCOUNTER_4.get();
					break;
				case 4:
					sound = NuclearScienceSounds.SOUND_GEIGERCOUNTER_5.get();
					break;
				case 5:
					sound = NuclearScienceSounds.SOUND_GEIGERCOUNTER_6.get();
					break;
				default:
					sound = NuclearScienceSounds.SOUND_GEIGERCOUNTER_1.get();
					break;
				}

				worldIn.playSound(null, player.blockPosition(), sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
				IItemElectric.setEnergyStored(stack, this.getJoulesStored(stack) - POWER_USAGE);
				player.inventory.setChanged();

			}

		}
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return oldStack.getItem() != newStack.getItem();
	}
	
}
