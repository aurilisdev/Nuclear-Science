package nuclearscience.client.screen;

import java.text.DecimalFormat;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.prefab.screen.GenericScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.common.inventory.container.ContainerMSRReactorCore;
import nuclearscience.common.tile.TileFreezePlug;
import nuclearscience.common.tile.TileMSRReactorCore;

@OnlyIn(Dist.CLIENT)
public class ScreenMSRReactorCore extends GenericScreen<ContainerMSRReactorCore> {

    public ScreenMSRReactorCore(ContainerMSRReactorCore container, PlayerInventory playerInventory, ITextComponent title) {
	super(container, playerInventory, title);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
	super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
	TileMSRReactorCore core = container.getHostFromIntArray();
	if (core != null) {
	    font.func_243248_b(matrixStack, new TranslationTextComponent("gui.reactorcore.temperature", (int) core.temperature + " C"), titleX,
		    (float) titleY + 14 * 1, 4210752);
	    if (core.temperature > TileMSRReactorCore.MELTDOWN_TEMPERATURE && System.currentTimeMillis() % 1000 < 500) {
		font.func_243248_b(matrixStack, new TranslationTextComponent("gui.reactorcore.warning"), titleX, (float) titleY + 55, 16711680);
	    }
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.msrreactorcore.fuel", new DecimalFormat("#.##").format(core.currentFuel)), titleX,
		    (float) titleY + 14 * 2, 4210752);
	    if (!(core.plugCache.getSafe() instanceof TileFreezePlug)) {
		font.func_243248_b(matrixStack, new TranslationTextComponent("gui.msrreactorcore.nofreezeplug"), titleX, (float) titleY + 14 * 3, 0);
	    }
	}
    }
}