package nuclearscience.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentFluidInput;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.common.inventory.container.ContainerGasCentrifuge;
import nuclearscience.common.tile.TileGasCentrifuge;
import nuclearscience.prefab.screen.component.ScreenComponentGasCentrifuge;

@OnlyIn(Dist.CLIENT)
public class ScreenGasCentrifuge extends GenericScreen<ContainerGasCentrifuge> {

	public ScreenGasCentrifuge(ContainerGasCentrifuge container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);

		components.add(new ScreenComponentFluidInput(() -> {
			TileGasCentrifuge boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler).getInputTanks()[0];
			}
			return null;
		}, this, 18, 19));
		components.add(new ScreenComponentGasCentrifuge(() -> {
			TileGasCentrifuge box = menu.getHostFromIntArray();
			if (box != null && box.isRunning.get()) {
				// return (box.ticks % 100) / 100.0;
				return 13;
			}
			return 0;
		}, () -> {
			TileGasCentrifuge boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.stored235.get() / TileGasCentrifuge.REQUIRED;
			}
			return 0;
		}, () -> {
			TileGasCentrifuge boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.stored238.get() / TileGasCentrifuge.REQUIRED;
			}
			return 0;
		}, () -> {
			TileGasCentrifuge boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.storedWaste.get() / TileGasCentrifuge.REQUIRED;
			}
			return 0;
		}, this, 34, 14));
		components.add(new ScreenComponentElectricInfo(this, -AbstractScreenComponentInfo.SIZE + 1, 2));
	}

	@Override
	protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
		super.renderLabels(matrixStack, mouseX, mouseY);
		TileGasCentrifuge centrifuge = menu.getHostFromIntArray();
		if (centrifuge != null) {
			String u235String = getIntString(centrifuge.stored235.get());
			String u238String = getIntString(centrifuge.stored238.get());
			String wasteString = getIntString(centrifuge.storedWaste.get());
			font.draw(matrixStack, Component.literal("U235 " + u235String + "%"), 54, 17, 4210752);
			font.draw(matrixStack, Component.literal("U238 " + u238String + "%"), 54, 37, 4210752);
			font.draw(matrixStack, Component.literal("DUST " + wasteString + "%"), 54, 58, 4210752);
		}
	}

	private static String getIntString(int value) {
		int perc = (int) (value / (float) TileGasCentrifuge.REQUIRED * 100);
		if (perc < 10) {
			return "0" + perc;
		}
		return "" + perc;
	}

}