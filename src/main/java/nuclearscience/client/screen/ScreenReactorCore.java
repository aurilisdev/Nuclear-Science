package nuclearscience.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.screen.generic.GenericContainerScreen;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.References;
import nuclearscience.common.inventory.container.ContainerReactorCore;

@OnlyIn(Dist.CLIENT)
public class ScreenReactorCore extends GenericContainerScreen<ContainerReactorCore> implements IHasContainer<ContainerReactorCore> {
	public static final ResourceLocation SCREEN_BACKGROUND = new ResourceLocation(References.ID + ":textures/gui/reactorcore.png");

	public ScreenReactorCore(ContainerReactorCore container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title);
	}

	@Override
	public ResourceLocation getScreenBackground() {
		return SCREEN_BACKGROUND;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
		font.func_243248_b(matrixStack, new StringTextComponent("Deuterium"), (float) titleX, (float) titleY + 14, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(stack, partialTicks, mouseX, mouseY);
	}
}