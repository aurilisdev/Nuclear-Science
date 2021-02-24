package nuclearscience.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.formatting.ElectricUnit;
import electrodynamics.api.utilities.ElectricityChatFormatter;
import electrodynamics.client.screen.generic.GenericContainerScreenUpgradeable;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.References;
import nuclearscience.common.inventory.container.ContainerChemicalBoiler;

@OnlyIn(Dist.CLIENT)
public class ScreenChemicalBoiler extends GenericContainerScreenUpgradeable<ContainerChemicalBoiler> {
    public static final ResourceLocation SCREEN_BACKGROUND = new ResourceLocation(
	    References.ID + ":textures/gui/chemicalboiler.png");

    public ScreenChemicalBoiler(ContainerChemicalBoiler container, PlayerInventory playerInventory,
	    ITextComponent title) {
	super(container, playerInventory, title);
    }

    @Override
    public ResourceLocation getScreenBackground() {
	return SCREEN_BACKGROUND;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
	font.func_243248_b(matrixStack, title, titleX, titleY, 4210752);
	font.func_243248_b(matrixStack,
		new TranslationTextComponent("gui.chemicalboiler.usage",
			ElectricityChatFormatter.getDisplayShort(container.getJoulesPerTick() * 20, ElectricUnit.WATT)),
		playerInventoryTitleX, playerInventoryTitleY, 4210752);
	font.func_243248_b(matrixStack,
		new TranslationTextComponent("gui.chemicalboiler.voltage",
			ElectricityChatFormatter.getDisplayShort(container.getVoltage(), ElectricUnit.VOLTAGE)),
		(float) playerInventoryTitleX + 85, playerInventoryTitleY, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
	super.drawGuiContainerBackgroundLayer(stack, partialTicks, mouseX, mouseY);
	int burnLeftScaled = container.getBurnLeftScaled();
	blit(stack, guiLeft + 44, guiTop + 30, 212, 14, Math.min(burnLeftScaled * 2 + 1, 34), 16);
	if (burnLeftScaled > 17) {
	    blit(stack, guiLeft + 44 + 60, guiTop + 30, 212, 14, Math.min(burnLeftScaled * 2 - 34 + 1, 34), 16);
	}
	blit(stack, guiLeft + 21, guiTop + 68 - container.getWaterLevelScaled(), 214 + 18, 31, 16,
		container.getWaterLevelScaled());
	blit(stack, guiLeft + 139, guiTop + 68 - container.getU6FLevelScaled(), 214, 31, 16,
		container.getU6FLevelScaled());
    }

}