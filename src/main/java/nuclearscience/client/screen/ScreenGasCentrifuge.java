package nuclearscience.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.prefab.screen.GenericCustomScreenUpgradeable;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.DeferredRegisters;
import nuclearscience.References;
import nuclearscience.common.inventory.container.ContainerGasCentrifuge;
import nuclearscience.common.tile.TileGasCentrifuge;

@OnlyIn(Dist.CLIENT)
public class ScreenGasCentrifuge extends GenericCustomScreenUpgradeable<ContainerGasCentrifuge> {
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
	TileGasCentrifuge centrifuge = container.getHostFromIntArray();
	if (centrifuge != null) {
	    ComponentElectrodynamic electro = centrifuge.getComponent(ComponentType.Electrodynamic);
	    ComponentProcessor processor = centrifuge.getComponent(ComponentType.Processor);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.gascentrifuge.usage",
			    ChatFormatter.getElectricDisplayShort(processor.getUsage() * 20, ElectricUnit.WATT)),
		    playerInventoryTitleX, playerInventoryTitleY, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.gascentrifuge.voltage",
			    ChatFormatter.getElectricDisplayShort(electro.getVoltage(), ElectricUnit.VOLTAGE)),
		    (float) playerInventoryTitleX + 85, playerInventoryTitleY, 4210752);
	}
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
	super.drawGuiContainerBackgroundLayer(stack, partialTicks, mouseX, mouseY);
	TileGasCentrifuge centrifuge = container.getHostFromIntArray();
	if (centrifuge != null) {
	    ComponentFluidHandler handler = centrifuge.getComponent(ComponentType.FluidHandler);
	    blit(stack, guiLeft + 9,
		    (int) (guiTop + 68
			    - handler.getStackFromFluid(DeferredRegisters.fluidUraniumHexafluoride).getAmount()
				    / (float) TileGasCentrifuge.TANKCAPACITY * 50),
		    214, 31, 16, (int) (handler.getStackFromFluid(DeferredRegisters.fluidUraniumHexafluoride).getAmount()
			    / (float) TileGasCentrifuge.TANKCAPACITY * 50));
	    blit(stack, guiLeft + 72, (int) (guiTop + 39 - centrifuge.stored235 / (float) TileGasCentrifuge.TANKCAPACITY * 47), 214, 31, 16,
		    (int) (centrifuge.stored235 / (float) TileGasCentrifuge.TANKCAPACITY * 47));
	    blit(stack, guiLeft + 72, (int) (guiTop + 70 - centrifuge.stored238 / (float) TileGasCentrifuge.TANKCAPACITY * 47), 214, 31, 16,
		    (int) (centrifuge.stored238 / (float) TileGasCentrifuge.TANKCAPACITY * 47));
	}
    }

}