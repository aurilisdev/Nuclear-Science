package nuclearscience.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.prefab.screen.GenericContainerScreenUpgradeable;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.DeferredRegisters;
import nuclearscience.References;
import nuclearscience.common.inventory.container.ContainerChemicalBoiler;
import nuclearscience.common.tile.TileNuclearBoiler;

@OnlyIn(Dist.CLIENT)
public class ScreenChemicalBoiler extends GenericContainerScreenUpgradeable<ContainerChemicalBoiler> {
    public static final ResourceLocation SCREEN_BACKGROUND = new ResourceLocation(References.ID + ":textures/gui/chemicalboiler.png");

    public ScreenChemicalBoiler(ContainerChemicalBoiler container, PlayerInventory playerInventory, ITextComponent title) {
	super(container, playerInventory, title);
    }

    @Override
    public ResourceLocation getScreenBackground() {
	return SCREEN_BACKGROUND;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
	font.func_243248_b(matrixStack, title, titleX, titleY, 4210752);
	TileNuclearBoiler boiler = container.getHostFromIntArray();
	if (boiler != null) {
	    ComponentElectrodynamic electro = boiler.getComponent(ComponentType.Electrodynamic);
	    ComponentProcessor processor = boiler.getComponent(ComponentType.Processor);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.chemicalboiler.usage",
			    ChatFormatter.getElectricDisplayShort(processor.getUsage() * 20, ElectricUnit.WATT)),
		    playerInventoryTitleX, playerInventoryTitleY, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.chemicalboiler.voltage",
			    ChatFormatter.getElectricDisplayShort(electro.getVoltage(), ElectricUnit.VOLTAGE)),
		    (float) playerInventoryTitleX + 85, playerInventoryTitleY, 4210752);
	}
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
	super.drawGuiContainerBackgroundLayer(stack, partialTicks, mouseX, mouseY);
	TileNuclearBoiler boiler = container.getHostFromIntArray();
	if (boiler != null) {
	    ComponentProcessor processor = boiler.getComponent(ComponentType.Processor);
	    ComponentFluidHandler handler = boiler.getComponent(ComponentType.FluidHandler);
	    int burnLeftScaled = (int) (processor.operatingTicks * 34.0 / processor.requiredTicks);
	    blit(stack, guiLeft + 44, guiTop + 30, 212, 14, Math.min(burnLeftScaled * 2 + 1, 34), 16);
	    if (burnLeftScaled > 17) {
		blit(stack, guiLeft + 44 + 60, guiTop + 30, 212, 14, Math.min(burnLeftScaled * 2 - 34 + 1, 34), 16);
	    }
	    blit(stack, guiLeft + 21,
		    guiTop + 68 - (int) (handler.getStackFromFluid(Fluids.WATER).getAmount() / (float) TileNuclearBoiler.TANKCAPACITY * 50), 214 + 18,
		    31, 16, (int) (handler.getStackFromFluid(Fluids.WATER).getAmount() / (float) TileNuclearBoiler.TANKCAPACITY * 50));
	    blit(stack, guiLeft + 139,
		    guiTop + 68
			    - (int) (handler.getStackFromFluid(DeferredRegisters.fluidUraniumHexafluoride).getAmount()
				    / (float) TileNuclearBoiler.TANKCAPACITY * 50),
		    214, 31, 16, (int) (handler.getStackFromFluid(DeferredRegisters.fluidUraniumHexafluoride).getAmount()
			    / (float) TileNuclearBoiler.TANKCAPACITY * 50));
	}
    }

}