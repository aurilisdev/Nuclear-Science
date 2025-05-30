package nuclearscience.common.item;

import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
	
	public static final double POWER_USAGE = 1666666.66667 / (120.0 * 20.0);

	public ItemGeigerCounter(ElectricItemProperties properties, Supplier<CreativeModeTab> creativeTab, Function<Item, Item> getBatteryItem) {
		super(properties, creativeTab, getBatteryItem);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		if (entityIn instanceof Player player && !worldIn.isClientSide) {

            boolean noPower = getJoulesStored(stack) < POWER_USAGE;

            IRadiationRecipient capability = player.getCapability(VoltaicCapabilities.CAPABILITY_RADIATIONRECIPIENT).orElse(CapabilityUtils.EMPTY_RADIATION_REPIPIENT);
            if (capability == CapabilityUtils.EMPTY_RADIATION_REPIPIENT) {
                return;
            }

            RadioactiveObject recievedRads = capability.getRecievedRadiation(player);

            if (isSelected || player.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof ItemGeigerCounter) {
                if(noPower) {
                    player.displayClientMessage(NuclearTextUtils.chatMessage("geigercounter.nopower"), true);
                } else {
                    player.displayClientMessage(ChatFormatter.getChatDisplay(recievedRads.amount(), DisplayUnits.RAD, 3, true), true);
                }

            }

            if (!noPower && recievedRads.amount() > 0 && worldIn.random.nextFloat() * 50 * 60.995 / 3 < recievedRads.amount()) {

                SoundEvent sound = switch(worldIn.random.nextInt(1, 7)) {
                    case 2 -> NuclearScienceSounds.SOUND_GEIGERCOUNTER_2.get();
                    case 3 -> NuclearScienceSounds.SOUND_GEIGERCOUNTER_3.get();
                    case 4 -> NuclearScienceSounds.SOUND_GEIGERCOUNTER_4.get();
                    case 5 -> NuclearScienceSounds.SOUND_GEIGERCOUNTER_5.get();
                    case 6 -> NuclearScienceSounds.SOUND_GEIGERCOUNTER_6.get();
                    default -> NuclearScienceSounds.SOUND_GEIGERCOUNTER_1.get();
                };

                worldIn.playSound(null, player.blockPosition(), sound, SoundSource.BLOCKS, 1.0F, 1.0F);
                IItemElectric.setEnergyStored(stack, this.getJoulesStored(stack) - POWER_USAGE);
                player.getInventory().setItem(itemSlot, stack);
                player.getInventory().setChanged();

            }


        }
	}
}
