package nuclearscience.client.screen;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import nuclearscience.common.inventory.container.ContainerRadioisotopeGenerator;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.radiation.util.RadioactiveObject;
import voltaic.common.reloadlistener.RadioactiveItemRegister;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.ScreenComponentMultiLabel;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.types.wrapper.WrapperInventoryIO;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.utilities.math.Color;
import voltaic.prefab.utilities.object.TransferPack;

public class ScreenRadioisotopeGenerator extends GenericScreen<ContainerRadioisotopeGenerator> {

	public ScreenRadioisotopeGenerator(ContainerRadioisotopeGenerator container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.COUNTDOWN_FLAME, () -> {
			ItemStack in = container.getSlot(0).getItem();
			RadioactiveObject rad = RadioactiveItemRegister.getValue(in.getItem());
			double currentOutput = in.getCount() * NuclearConstants.RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER * rad.amount();
			if (currentOutput > 0) {
				return 1;
			}
			return 0;
		}, 25, 24));
		addComponent(new ScreenComponentElectricInfo(this::getEnergyInformation, -AbstractScreenComponentInfo.SIZE + 1, 2));

		addComponent(new ScreenComponentMultiLabel(0, 0, poseStack -> {
			ItemStack in = menu.getSlot(0).getItem();
			RadioactiveObject rad = RadioactiveItemRegister.getValue(in.getItem());
			double currentOutput = in.getCount() * NuclearConstants.RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER * rad.amount();
			TransferPack transfer = TransferPack.ampsVoltage(currentOutput / NuclearConstants.RADIOISOTOPEGENERATOR_VOLTAGE, NuclearConstants.RADIOISOTOPEGENERATOR_VOLTAGE);
			font.draw(poseStack, NuclearTextUtils.gui("machine.current", ChatFormatter.getChatDisplayShort(transfer.getAmps(), DisplayUnits.AMPERE)), inventoryLabelX + 60, inventoryLabelY - 48, Color.TEXT_GRAY.color());
			font.draw(poseStack, NuclearTextUtils.gui("machine.output", ChatFormatter.getChatDisplayShort(transfer.getWatts(), DisplayUnits.WATT)), inventoryLabelX + 60, inventoryLabelY - 35, Color.TEXT_GRAY.color());
			font.draw(poseStack, NuclearTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(transfer.getVoltage(), DisplayUnits.VOLTAGE)), inventoryLabelX + 60, inventoryLabelY - 22, Color.TEXT_GRAY.color());
		}));

		new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82, 8, 72);
	}

	private List<? extends FormattedCharSequence> getEnergyInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		ItemStack in = menu.getSlot(0).getItem();
		RadioactiveObject rad = RadioactiveItemRegister.getValue(in.getItem());
		double currentOutput = in.getCount() * NuclearConstants.RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER * rad.amount();
		TransferPack transfer = TransferPack.ampsVoltage(currentOutput / NuclearConstants.RADIOISOTOPEGENERATOR_VOLTAGE, NuclearConstants.RADIOISOTOPEGENERATOR_VOLTAGE);
		list.add(NuclearTextUtils.gui("machine.current", ChatFormatter.getChatDisplayShort(transfer.getAmps(), DisplayUnits.AMPERE)).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		list.add(NuclearTextUtils.gui("machine.output", ChatFormatter.getChatDisplayShort(transfer.getWatts(), DisplayUnits.WATT).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		list.add(NuclearTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(transfer.getVoltage(), DisplayUnits.VOLTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		return list;
	}

}