package nuclearscience.client.screen;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentMultiLabel;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress.ProgressBars;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.api.radiation.IRadioactiveObject;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.common.inventory.container.ContainerRadioisotopeGenerator;
import nuclearscience.common.settings.Constants;
import nuclearscience.prefab.utils.NuclearTextUtils;

@OnlyIn(Dist.CLIENT)
public class ScreenRadioisotopeGenerator extends GenericScreen<ContainerRadioisotopeGenerator> {

	public ScreenRadioisotopeGenerator(ContainerRadioisotopeGenerator container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentProgress(ProgressBars.COUNTDOWN_FLAME, () -> {
			ItemStack in = container.getSlot(0).getItem();
			IRadioactiveObject rad = RadiationRegister.get(in.getItem());
			double currentOutput = in.getCount() * Constants.RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER * rad.getRadiationStrength();
			if (currentOutput > 0) {
				return 1;
			}
			return 0;
		}, 25, 24));
		addComponent(new ScreenComponentElectricInfo(this::getEnergyInformation, -AbstractScreenComponentInfo.SIZE + 1, 2));
		
		addComponent(new ScreenComponentMultiLabel(0, 0, stack -> {
			ItemStack in = menu.getSlot(0).getItem();
			IRadioactiveObject rad = RadiationRegister.get(in.getItem());
			double currentOutput = in.getCount() * Constants.RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER * rad.getRadiationStrength();
			TransferPack transfer = TransferPack.ampsVoltage(currentOutput / Constants.RADIOISOTOPEGENERATOR_VOLTAGE, Constants.RADIOISOTOPEGENERATOR_VOLTAGE);
			font.draw(stack, NuclearTextUtils.gui("machine.current", ChatFormatter.getChatDisplayShort(transfer.getAmps(), DisplayUnit.AMPERE)), (float) inventoryLabelX + 60, (float) inventoryLabelY - 48, 4210752);
			font.draw(stack, NuclearTextUtils.gui("machine.output", ChatFormatter.getChatDisplayShort(transfer.getWatts(), DisplayUnit.WATT)), (float) inventoryLabelX + 60, (float) inventoryLabelY - 35, 4210752);
			font.draw(stack, NuclearTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(transfer.getVoltage(), DisplayUnit.VOLTAGE)), (float) inventoryLabelX + 60, (float) inventoryLabelY - 22, 4210752);
		}));
	}

	private List<? extends FormattedCharSequence> getEnergyInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		ItemStack in = menu.getSlot(0).getItem();
		IRadioactiveObject rad = RadiationRegister.get(in.getItem());
		double currentOutput = in.getCount() * Constants.RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER * rad.getRadiationStrength();
		TransferPack transfer = TransferPack.ampsVoltage(currentOutput / Constants.RADIOISOTOPEGENERATOR_VOLTAGE, Constants.RADIOISOTOPEGENERATOR_VOLTAGE);
		list.add(NuclearTextUtils.gui("machine.current", ChatFormatter.getChatDisplayShort(transfer.getAmps(), DisplayUnit.AMPERE)).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		list.add(NuclearTextUtils.gui("machine.output", ChatFormatter.getChatDisplayShort(transfer.getWatts(), DisplayUnit.WATT).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		list.add(NuclearTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(transfer.getVoltage(), DisplayUnit.VOLTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		return list;
	}

}