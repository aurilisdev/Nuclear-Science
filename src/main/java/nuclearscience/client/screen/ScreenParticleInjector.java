package nuclearscience.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.formatting.ElectricUnit;
import electrodynamics.api.utilities.ElectricityChatFormatter;
import electrodynamics.client.screen.generic.GenericContainerScreen;
import electrodynamics.common.tile.generic.component.ComponentType;
import electrodynamics.common.tile.generic.component.type.ComponentElectrodynamic;
import electrodynamics.common.tile.generic.component.type.ComponentProcessor;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.References;
import nuclearscience.common.inventory.container.ContainerParticleInjector;
import nuclearscience.common.tile.TileParticleInjector;

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
		titleY + 12f, 4210752);
	font.func_243248_b(matrixStack, new TranslationTextComponent("gui.particleinjector.cells"), titleX,
		titleY + 48f, 4210752);
	TileParticleInjector injector = container.getHostFromIntArray();
	if (injector != null) {
	    ComponentElectrodynamic electro = injector.getComponent(ComponentType.Electrodynamic);
	    ComponentProcessor processor = injector.getComponent(ComponentType.Processor);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.particleinjector.charge",
			    (int) (electro.getJoulesStored() / processor.getJoulesPerTick() * 100.0)).appendString("%"),
		    titleX, titleY + 30f, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.particleinjector.usage", ElectricityChatFormatter
			    .getDisplayShort(processor.getJoulesPerTick() * 20, ElectricUnit.WATT)),
		    playerInventoryTitleX, playerInventoryTitleY, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.particleinjector.voltage",
			    ElectricityChatFormatter.getDisplayShort(electro.getVoltage(), ElectricUnit.VOLTAGE)),
		    (float) playerInventoryTitleX + 85, playerInventoryTitleY, 4210752);
	}
    }

}