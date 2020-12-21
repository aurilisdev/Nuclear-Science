package nuclearscience.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.formatting.ElectricUnit;
import electrodynamics.api.utilities.ElectricityChatFormatter;
import electrodynamics.client.screen.generic.GenericContainerScreenUpgradeable;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
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
		font.func_243248_b(matrixStack, title, titleX, titleY, 4210752);
		font.func_243248_b(matrixStack, new TranslationTextComponent("gui.gascentrifuge.usage", ElectricityChatFormatter.getDisplayShort(container.getJoulesPerTick() * 20, ElectricUnit.WATT)), playerInventoryTitleX,
				playerInventoryTitleY, 4210752);
		font.func_243248_b(matrixStack, new TranslationTextComponent("gui.gascentrifuge.voltage", ElectricityChatFormatter.getDisplayShort(container.getVoltage(), ElectricUnit.VOLTAGE)),
				(float) playerInventoryTitleX + 85, playerInventoryTitleY, 4210752);
		font.func_243248_b(matrixStack, new TranslationTextComponent("U-238"), (float) playerInventoryTitleX + 30, playerInventoryTitleY - 33 + 17, 4210752);
		font.func_243248_b(matrixStack, new TranslationTextComponent("U-235"), (float) playerInventoryTitleX + 30, playerInventoryTitleY - 33 - 17, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(stack, partialTicks, mouseX, mouseY);
		blit(stack, guiLeft + 9, guiTop + 67 - container.getU6FLevelScaled(), 214, 31, 16, container.getU6FLevelScaled());
		blit(stack, guiLeft + 72, guiTop + 38 - container.getU235LevelScaled(), 214, 31, 16, container.getU235LevelScaled());
		blit(stack, guiLeft + 72, guiTop + 69 - container.getU238LevelScaled(), 214, 31, 16, container.getU238LevelScaled());
	}

}