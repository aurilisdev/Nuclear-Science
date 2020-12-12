package nuclearscience.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.formatting.ElectricUnit;
import electrodynamics.api.utilities.ElectricityChatFormatter;
import electrodynamics.client.screen.generic.GenericContainerScreenUpgradeable;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.References;
import nuclearscience.common.inventory.container.ContainerGasCentrifuge;

@OnlyIn(Dist.CLIENT)
public class ScreenGasCentrifuge extends GenericContainerScreenUpgradeable<ContainerGasCentrifuge> implements IHasContainer<ContainerGasCentrifuge> {
	public static final ResourceLocation SCREEN_BACKGROUND = new ResourceLocation(References.ID + ":textures/gui/gascentrifuge.png");

	public ScreenGasCentrifuge(ContainerGasCentrifuge container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title);
	}

	@Override
	public ResourceLocation getScreenBackground() {
		return SCREEN_BACKGROUND;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
		font.func_243248_b(matrixStack, new TranslationTextComponent("gui.gascentrifuge.235chance").mergeStyle(TextFormatting.DARK_GRAY), (float) playerInventoryTitleX + 83, playerInventoryTitleY - 54, 4210752);
		font.func_243248_b(matrixStack, new TranslationTextComponent("gui.gascentrifuge.usage", ElectricityChatFormatter.getDisplayShort(container.getJoulesPerTick() * 20, ElectricUnit.WATT)),
				(float) playerInventoryTitleX + 83, (float) playerInventoryTitleY - 39, 4210752);
		font.func_243248_b(matrixStack, new TranslationTextComponent("gui.gascentrifuge.voltage", ElectricityChatFormatter.getDisplayShort(container.getVoltage(), ElectricUnit.VOLTAGE)),
				(float) playerInventoryTitleX + 83, playerInventoryTitleY - 28, 4210752);
		font.func_243248_b(matrixStack, new TranslationTextComponent("gui.gascentrifuge.238chance").mergeStyle(TextFormatting.DARK_GRAY), (float) playerInventoryTitleX + 83, playerInventoryTitleY - 13, 4210752);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(stack, partialTicks, mouseX, mouseY);
		blit(stack, guiLeft + 27, guiTop + 34, 212, 14, container.getBurnLeftScaled() + 1, 16);
		blit(stack, guiLeft + 8, guiTop + 67 - container.getLiquidLevelScaled(), 213, 31, 18, container.getLiquidLevelScaled());
	}

}