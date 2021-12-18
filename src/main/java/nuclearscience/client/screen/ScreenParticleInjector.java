package nuclearscience.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.prefab.screen.GenericCustomScreen;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.References;
import nuclearscience.common.inventory.container.ContainerParticleInjector;
import nuclearscience.common.tile.TileParticleInjector;

@OnlyIn(Dist.CLIENT)
public class ScreenParticleInjector extends GenericCustomScreen<ContainerParticleInjector> {
	public static final ResourceLocation SCREEN_BACKGROUND = new ResourceLocation(References.ID + ":textures/gui/particleinjector.png");

	public ScreenParticleInjector(ContainerParticleInjector container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
	}

	@Override
	public ResourceLocation getScreenBackground() {
		return SCREEN_BACKGROUND;
	}

	@Override
	protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
		font.draw(matrixStack, title, titleLabelX, titleLabelY, 4210752);
		font.draw(matrixStack, new TranslatableComponent("gui.particleinjector.matter"), titleLabelX, titleLabelY + 12f, 4210752);
		font.draw(matrixStack, new TranslatableComponent("gui.particleinjector.cells"), titleLabelX, titleLabelY + 48f, 4210752);
		TileParticleInjector injector = menu.getHostFromIntArray();
		if (injector != null) {
			ComponentElectrodynamic electro = injector.getComponent(ComponentType.Electrodynamic);
			ComponentProcessor processor = injector.getComponent(ComponentType.Processor);
			font.draw(matrixStack,
					new TranslatableComponent("gui.particleinjector.charge", (int) (electro.getJoulesStored() / processor.getUsage() * 100.0))
							.append("%"),
					titleLabelX, titleLabelY + 30f, 4210752);
			font.draw(matrixStack,
					new TranslatableComponent("gui.particleinjector.usage",
							ChatFormatter.getElectricDisplayShort(processor.getUsage(), ElectricUnit.JOULES)),
					inventoryLabelX, inventoryLabelY, 4210752);
			font.draw(matrixStack,
					new TranslatableComponent("gui.particleinjector.voltage",
							ChatFormatter.getElectricDisplayShort(electro.getVoltage(), ElectricUnit.VOLTAGE)),
					(float) inventoryLabelX + 85, inventoryLabelY, 4210752);
		}
	}

}