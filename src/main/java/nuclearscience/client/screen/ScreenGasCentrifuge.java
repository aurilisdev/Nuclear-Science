package nuclearscience.client.screen;

import electrodynamics.prefab.screen.component.types.ScreenComponentMultiLabel;
import electrodynamics.prefab.screen.component.types.gauges.ScreenComponentFluidGaugeInput;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.types.wrapper.InventoryIOWrapper;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.screen.types.GenericMaterialScreen;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.common.inventory.container.ContainerGasCentrifuge;
import nuclearscience.common.tile.TileGasCentrifuge;
import nuclearscience.prefab.screen.component.ScreenComponentGasCentrifuge;

@OnlyIn(Dist.CLIENT)
public class ScreenGasCentrifuge extends GenericMaterialScreen<ContainerGasCentrifuge> {

	public ScreenGasCentrifuge(ContainerGasCentrifuge container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title);

		addComponent(new ScreenComponentFluidGaugeInput(() -> {
			TileGasCentrifuge boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentFluidHandlerMulti>getComponent(IComponentType.FluidHandler).getInputTanks()[0];
			}
			return null;
		}, 18, 19));
		addComponent(new ScreenComponentGasCentrifuge(() -> {
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
		}, 34, 14));

		new InventoryIOWrapper(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82, 8, 72);
		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));

		addComponent(new ScreenComponentMultiLabel(0, 0, stack -> {
			TileGasCentrifuge centrifuge = menu.getHostFromIntArray();
			if (centrifuge == null) {
				return;
			}
			font.draw(stack, new StringTextComponent("U235 " + getIntString(centrifuge.stored235.get()) + "%"), 54, 17, 4210752);
			font.draw(stack, new StringTextComponent("U238 " + getIntString(centrifuge.stored238.get()) + "%"), 54, 37, 4210752);
			font.draw(stack, new StringTextComponent("DUST " + getIntString(centrifuge.storedWaste.get()) + "%"), 54, 58, 4210752);
		}));
	}

	private static String getIntString(double value) {
		int perc = (int) (value / (float) TileGasCentrifuge.REQUIRED * 100);
		if (perc < 10) {
			return "0" + perc;
		}
		return "" + perc;
	}

}