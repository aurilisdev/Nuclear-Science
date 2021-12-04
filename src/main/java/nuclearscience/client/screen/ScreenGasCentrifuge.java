package nuclearscience.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.prefab.screen.GenericCustomScreenUpgradeable;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.DeferredRegisters;
import nuclearscience.References;
import nuclearscience.common.inventory.container.ContainerGasCentrifuge;
import nuclearscience.common.tile.TileGasCentrifuge;

@OnlyIn(Dist.CLIENT)
public class ScreenGasCentrifuge extends GenericCustomScreenUpgradeable<ContainerGasCentrifuge> {
    public static final ResourceLocation SCREEN_BACKGROUND = new ResourceLocation(References.ID + ":textures/gui/gascentrifuge.png");

    public ScreenGasCentrifuge(ContainerGasCentrifuge container, Inventory playerInventory, Component title) {
	super(container, playerInventory, title);
    }

    @Override
    public ResourceLocation getScreenBackground() {
	return SCREEN_BACKGROUND;
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
	font.draw(matrixStack, title, titleLabelX, titleLabelY, 4210752);
	TileGasCentrifuge centrifuge = menu.getHostFromIntArray();
	if (centrifuge != null) {
	    ComponentElectrodynamic electro = centrifuge.getComponent(ComponentType.Electrodynamic);
	    ComponentProcessor processor = centrifuge.getComponent(ComponentType.Processor);
	    font.draw(matrixStack,
		    new TranslatableComponent("gui.gascentrifuge.usage",
			    ChatFormatter.getElectricDisplayShort(processor.getUsage() * 20, ElectricUnit.WATT)),
		    inventoryLabelX, inventoryLabelY, 4210752);
	    font.draw(matrixStack,
		    new TranslatableComponent("gui.gascentrifuge.voltage",
			    ChatFormatter.getElectricDisplayShort(electro.getVoltage(), ElectricUnit.VOLTAGE)),
		    (float) inventoryLabelX + 85, inventoryLabelY, 4210752);
	}
    }

    @Override
    protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
	super.renderBg(stack, partialTicks, mouseX, mouseY);
	TileGasCentrifuge centrifuge = menu.getHostFromIntArray();
	if (centrifuge != null) {
	    AbstractFluidHandler<?> handler = centrifuge.getComponent(ComponentType.FluidHandler);
	    blit(stack, leftPos + 9,
		    (int) (topPos + 68
			    - handler.getTankFromFluid(DeferredRegisters.fluidUraniumHexafluoride, true).getFluidAmount()
				    / (float) TileGasCentrifuge.TANKCAPACITY * 50),
		    214, 31, 16, (int) (handler.getTankFromFluid(DeferredRegisters.fluidUraniumHexafluoride, true).getFluidAmount()
			    / (float) TileGasCentrifuge.TANKCAPACITY * 50));
	    blit(stack, leftPos + 72, (int) (topPos + 39 - centrifuge.stored235 / (float) TileGasCentrifuge.TANKCAPACITY * 47), 214, 31, 16,
		    (int) (centrifuge.stored235 / (float) TileGasCentrifuge.TANKCAPACITY * 47));
	    blit(stack, leftPos + 72, (int) (topPos + 70 - centrifuge.stored238 / (float) TileGasCentrifuge.TANKCAPACITY * 47), 214, 31, 16,
		    (int) (centrifuge.stored238 / (float) TileGasCentrifuge.TANKCAPACITY * 47));
	}
    }

}