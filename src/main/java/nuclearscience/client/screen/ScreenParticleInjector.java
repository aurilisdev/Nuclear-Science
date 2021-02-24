package nuclearscience.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.formatting.ElectricUnit;
import electrodynamics.api.utilities.ElectricityChatFormatter;
import electrodynamics.client.screen.generic.GenericContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.References;
import nuclearscience.common.inventory.container.ContainerParticleInjector;

@OnlyIn(Dist.CLIENT)
public class ScreenParticleInjector extends GenericContainerScreen<ContainerParticleInjector> {
    public static final ResourceLocation SCREEN_BACKGROUND = new ResourceLocation(
	    References.ID + ":textures/gui/particleinjector.png");

    public ScreenParticleInjector(ContainerParticleInjector container, PlayerInventory playerInventory,
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
	font.func_243248_b(matrixStack, new TranslationTextComponent("gui.particleinjector.matter"), titleX,
		titleY + 12, 4210752);
	font.func_243248_b(matrixStack, new TranslationTextComponent("gui.particleinjector.cells"), titleX, titleY + 48,
		4210752);
	font.func_243248_b(matrixStack,
		new TranslationTextComponent("gui.particleinjector.charge", container.getCharge()).appendString("%"),
		titleX, titleY + 30, 4210752);
	font.func_243248_b(matrixStack,
		new TranslationTextComponent("gui.particleinjector.usage",
			ElectricityChatFormatter.getDisplayShort(container.getJoulesPerTick() * 20, ElectricUnit.WATT)),
		playerInventoryTitleX, playerInventoryTitleY, 4210752);
	font.func_243248_b(matrixStack,
		new TranslationTextComponent("gui.particleinjector.voltage",
			ElectricityChatFormatter.getDisplayShort(container.getVoltage(), ElectricUnit.VOLTAGE)),
		(float) playerInventoryTitleX + 85, playerInventoryTitleY, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
	super.drawGuiContainerBackgroundLayer(stack, partialTicks, mouseX, mouseY);
    }

}