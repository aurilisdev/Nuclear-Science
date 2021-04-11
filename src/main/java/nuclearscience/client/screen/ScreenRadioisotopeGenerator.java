package nuclearscience.client.screen;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentInfo;
import electrodynamics.prefab.screen.component.ScreenComponentProgress;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.api.radiation.IRadioactiveObject;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.common.inventory.container.ContainerRadioisotopeGenerator;
import nuclearscience.common.settings.Constants;

@OnlyIn(Dist.CLIENT)
public class ScreenRadioisotopeGenerator extends GenericScreen<ContainerRadioisotopeGenerator> {

    public ScreenRadioisotopeGenerator(ContainerRadioisotopeGenerator container, PlayerInventory playerInventory, ITextComponent title) {
	super(container, playerInventory, title);
	components.add(new ScreenComponentProgress(() -> {
	    ItemStack in = container.getSlot(0).getStack();
	    IRadioactiveObject rad = RadiationRegister.get(in.getItem());
	    double currentOutput = in.getCount() * Constants.RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER * rad.getRadiationStrength();
	    if (currentOutput > 0) {
		return 1;
	    }
	    return 0;
	}, this, 25, 24).flame());
	components.add(new ScreenComponentElectricInfo(this::getEnergyInformation, this, -ScreenComponentInfo.SIZE + 1, 2));
    }

    private List<? extends ITextProperties> getEnergyInformation() {
	ArrayList<ITextProperties> list = new ArrayList<>();
	ItemStack in = container.getSlot(0).getStack();
	IRadioactiveObject rad = RadiationRegister.get(in.getItem());
	double currentOutput = in.getCount() * Constants.RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER * rad.getRadiationStrength();
	TransferPack transfer = TransferPack.ampsVoltage(currentOutput / Constants.RADIOISOTOPEGENERATOR_VOLTAGE,
		Constants.RADIOISOTOPEGENERATOR_VOLTAGE);
	list.add(new TranslationTextComponent("gui.radioisotopegenerator.current",
		new StringTextComponent(ChatFormatter.getElectricDisplayShort(transfer.getAmps(), ElectricUnit.AMPERE))
			.mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	list.add(new TranslationTextComponent("gui.radioisotopegenerator.output",
		new StringTextComponent(ChatFormatter.getElectricDisplayShort(transfer.getWatts(), ElectricUnit.WATT))
			.mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	list.add(new TranslationTextComponent("gui.radioisotopegenerator.voltage",
		new StringTextComponent(ChatFormatter.getElectricDisplayShort(transfer.getVoltage(), ElectricUnit.VOLTAGE))
			.mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	return list;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
	super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
	ItemStack in = container.getSlot(0).getStack();
	IRadioactiveObject rad = RadiationRegister.get(in.getItem());
	double currentOutput = in.getCount() * Constants.RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER * rad.getRadiationStrength();
	TransferPack transfer = TransferPack.ampsVoltage(currentOutput / Constants.RADIOISOTOPEGENERATOR_VOLTAGE,
		Constants.RADIOISOTOPEGENERATOR_VOLTAGE);
	font.func_243248_b(matrixStack,
		new TranslationTextComponent("gui.radioisotopegenerator.current",
			ChatFormatter.getElectricDisplayShort(transfer.getAmps(), ElectricUnit.AMPERE)),
		(float) playerInventoryTitleX + 60, (float) playerInventoryTitleY - 48, 4210752);
	font.func_243248_b(matrixStack,
		new TranslationTextComponent("gui.radioisotopegenerator.output",
			ChatFormatter.getElectricDisplayShort(transfer.getWatts(), ElectricUnit.WATT)),
		(float) playerInventoryTitleX + 60, (float) playerInventoryTitleY - 35, 4210752);
	font.func_243248_b(matrixStack,
		new TranslationTextComponent("gui.radioisotopegenerator.voltage",
			ChatFormatter.getElectricDisplayShort(transfer.getVoltage(), ElectricUnit.VOLTAGE)),
		(float) playerInventoryTitleX + 60, (float) playerInventoryTitleY - 22, 4210752);
    }
}