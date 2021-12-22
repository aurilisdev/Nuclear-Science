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
import nuclearscience.prefab.screen.component.ScreenComponentGasCentrifugeArrow;

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
		}, this, 18, 18));
		components.add(new ScreenComponentGasCentrifugeArrow(this, 34, 14));
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
			font.draw(matrixStack, new TextComponent(u235String + "%"), 98, 17, 4210752);
			font.draw(matrixStack, new TextComponent(u238String + "%"), 98, 40, 4210752);
			font.draw(matrixStack, new TextComponent(wasteString + "%"), 72, 58, 4210752);
		}
	}

	private static String getIntString(int value) {
		int perc = (int) (value / TileGasCentrifuge.REQUIRED * 100);
		if (perc < 10) {
			return "00" + perc;
		} else if (perc < 100) {
			return "0" + perc;
		} else {
			return "" + perc;
		}
	}

}