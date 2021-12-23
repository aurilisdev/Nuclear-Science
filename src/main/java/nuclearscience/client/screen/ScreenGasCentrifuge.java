package nuclearscience.client.screen;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentFluid;
import electrodynamics.prefab.screen.component.ScreenComponentInfo;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import nuclearscience.common.inventory.container.ContainerGasCentrifuge;
import nuclearscience.common.tile.TileGasCentrifuge;
import nuclearscience.prefab.screen.component.ScreenComponentGasCentrifuge;

@OnlyIn(Dist.CLIENT)
public class ScreenGasCentrifuge extends GenericScreen<ContainerGasCentrifuge> {

	public ScreenGasCentrifuge(ContainerGasCentrifuge container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);

		components.add(new ScreenComponentFluid(() -> {
			TileGasCentrifuge boiler = container.getHostFromIntArray();
			if (boiler != null) {
				AbstractFluidHandler<?> handler = boiler.getComponent(ComponentType.FluidHandler);
				for (Fluid fluid : handler.getValidInputFluids()) {
					FluidTank tank = handler.getTankFromFluid(fluid, true);
					if (tank.getFluidAmount() > 0) {
						return tank;
					}
				}
			}
			return null;
		}, this, 18, 19));
		components.add(new ScreenComponentGasCentrifuge(() -> {
			TileGasCentrifuge box = menu.getHostFromIntArray();
			if (box != null && box.isRunning) {
				// return (box.ticks % 100) / 100.0;
				return 13;
			}
			return 0;
		}, () -> {
			TileGasCentrifuge boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.stored235 / TileGasCentrifuge.REQUIRED;
			}
			return 0;
		}, () -> {
			TileGasCentrifuge boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.stored238 / TileGasCentrifuge.REQUIRED;
			}
			return 0;
		}, () -> {
			TileGasCentrifuge boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.storedWaste / TileGasCentrifuge.REQUIRED;
			}
			return 0;
		}, this, 34, 14));
		components.add(new ScreenComponentElectricInfo(this::getEnergyInformation, this, -ScreenComponentInfo.SIZE + 1, 2));
	}

	private List<? extends FormattedCharSequence> getEnergyInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		GenericTile box = menu.getHostFromIntArray();
		if (box != null) {
			ComponentElectrodynamic electro = box.getComponent(ComponentType.Electrodynamic);
			ComponentProcessor processor = box.getComponent(ComponentType.Processor);

			list.add(new TranslatableComponent("gui.chemicalextractor.usage",
					new TextComponent(ChatFormatter.getElectricDisplayShort(processor.getUsage() * 20, ElectricUnit.WATT))
							.withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(new TranslatableComponent("gui.chemicalextractor.voltage",
					new TextComponent(ChatFormatter.getElectricDisplayShort(electro.getVoltage(), ElectricUnit.VOLTAGE))
							.withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		}
		return list;
	}

	@Override
	protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
		super.renderLabels(matrixStack, mouseX, mouseY);
		TileGasCentrifuge centrifuge = menu.getHostFromIntArray();
		if (centrifuge != null) {
			String u235String = getIntString(centrifuge.stored235);
			String u238String = getIntString(centrifuge.stored238);
			String wasteString = getIntString(centrifuge.storedWaste);
			font.draw(matrixStack, new TextComponent("U235 " + u235String + "%"), 54, 17, 4210752);
			font.draw(matrixStack, new TextComponent("U238 " + u238String + "%"), 54, 37, 4210752);
			font.draw(matrixStack, new TextComponent("DUST " + wasteString + "%"), 54, 58, 4210752);
		}
	}

	private static String getIntString(int value) {
		int perc = (int) (value / TileGasCentrifuge.REQUIRED * 100);
		if (perc < 10) {
			return "0" + perc;
		}
		return "" + perc;
	}

}