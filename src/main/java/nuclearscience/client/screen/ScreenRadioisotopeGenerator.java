package nuclearscience.client.screen;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentProgress;
import electrodynamics.prefab.screen.component.ScreenComponentProgress.ProgressBars;
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
import nuclearscience.prefab.utils.TextUtils;

@OnlyIn(Dist.CLIENT)
public class ScreenRadioisotopeGenerator extends GenericScreen<ContainerRadioisotopeGenerator> {

	public ScreenRadioisotopeGenerator(ContainerRadioisotopeGenerator container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		components.add(new ScreenComponentProgress(ProgressBars.COUNTDOWN_FLAME, () -> {
			ItemStack in = container.getSlot(0).getItem();
			IRadioactiveObject rad = RadiationRegister.get(in.getItem());
			double currentOutput = in.getCount() * Constants.RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER * rad.getRadiationStrength();
			if (currentOutput > 0) {
				return 1;
			}
			return 0;
		}, this, 25, 24));
		components.add(new ScreenComponentElectricInfo(this::getEnergyInformation, this, -AbstractScreenComponentInfo.SIZE + 1, 2));
	}

	private List<? extends FormattedCharSequence> getEnergyInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		ItemStack in = menu.getSlot(0).getItem();
		IRadioactiveObject rad = RadiationRegister.get(in.getItem());
		double currentOutput = in.getCount() * Constants.RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER * rad.getRadiationStrength();
		TransferPack transfer = TransferPack.ampsVoltage(currentOutput / Constants.RADIOISOTOPEGENERATOR_VOLTAGE, Constants.RADIOISOTOPEGENERATOR_VOLTAGE);
		list.add(TextUtils.gui("machine.current", Component.literal(ChatFormatter.getChatDisplayShort(transfer.getAmps(), DisplayUnit.AMPERE)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		list.add(TextUtils.gui("machine.output", Component.literal(ChatFormatter.getChatDisplayShort(transfer.getWatts(), DisplayUnit.WATT)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		list.add(TextUtils.gui("machine.voltage", Component.literal(ChatFormatter.getChatDisplayShort(transfer.getVoltage(), DisplayUnit.VOLTAGE)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		return list;
	}

	@Override
	protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
		super.renderLabels(matrixStack, mouseX, mouseY);
		ItemStack in = menu.getSlot(0).getItem();
		IRadioactiveObject rad = RadiationRegister.get(in.getItem());
		double currentOutput = in.getCount() * Constants.RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER * rad.getRadiationStrength();
		TransferPack transfer = TransferPack.ampsVoltage(currentOutput / Constants.RADIOISOTOPEGENERATOR_VOLTAGE, Constants.RADIOISOTOPEGENERATOR_VOLTAGE);
		font.draw(matrixStack, TextUtils.gui("machine.current", ChatFormatter.getChatDisplayShort(transfer.getAmps(), DisplayUnit.AMPERE)), (float) inventoryLabelX + 60, (float) inventoryLabelY - 48, 4210752);
		font.draw(matrixStack, TextUtils.gui("machine.output", ChatFormatter.getChatDisplayShort(transfer.getWatts(), DisplayUnit.WATT)), (float) inventoryLabelX + 60, (float) inventoryLabelY - 35, 4210752);
		font.draw(matrixStack, TextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(transfer.getVoltage(), DisplayUnit.VOLTAGE)), (float) inventoryLabelX + 60, (float) inventoryLabelY - 22, 4210752);
	}
}