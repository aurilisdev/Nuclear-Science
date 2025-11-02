package nuclearscience.client.screen;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.common.entity.EntityParticle;
import nuclearscience.common.inventory.container.ContainerParticleInjector;
import nuclearscience.common.settings.NuclearConfig;
import nuclearscience.common.tile.accelerator.TileElectromagneticGateway;
import nuclearscience.common.tile.accelerator.TileParticleInjector;
import nuclearscience.compatibility.jei.utils.NuclearJeiTextures;
import nuclearscience.prefab.screen.component.NuclearArrows;
import nuclearscience.prefab.screen.component.NuclearIconTypes;
import nuclearscience.prefab.utils.NuclearDisplayUnits;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.ScreenComponentGeneric;
import voltaic.prefab.screen.component.button.ScreenComponentButton;
import voltaic.prefab.screen.component.types.ScreenComponentSimpleLabel;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentGuiTab;
import voltaic.prefab.screen.component.types.wrapper.WrapperInventoryIO;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.utilities.math.Color;

public class ScreenParticleInjector extends GenericScreen<ContainerParticleInjector> {

    public ScreenParticleInjector(ContainerParticleInjector container, Inventory playerInventory, Component title) {
	super(container, playerInventory, title);

	imageHeight += 10;
	inventoryLabelY += 10;

	addComponent(new ScreenComponentGeneric(NuclearArrows.PARTICLE_INJECTOR_ARROWS, 44, 24));
	addComponent(new ScreenComponentSimpleLabel(titleLabelX, titleLabelY + 20, 10, Color.TEXT_GRAY,
		NuclearTextUtils.gui("particleinjector.matter")));
	addComponent(new ScreenComponentSimpleLabel(titleLabelX, titleLabelY + 56, 10, Color.TEXT_GRAY,
		NuclearTextUtils.gui("particleinjector.cells")));
	addComponent(
		new ScreenComponentElectricInfo(this::getElectricInformation, -AbstractScreenComponentInfo.SIZE + 1, 2)
			.wattage(NuclearConfig.INSTANCE.PARTICLEINJECTOR_USAGE_PER_PARTICLE.get()));
	addComponent(new ScreenComponentButton<>(ScreenComponentGuiTab.GuiInfoTabTextures.REGULAR,
		-AbstractScreenComponentInfo.SIZE + 1, 2 * AbstractScreenComponentInfo.SIZE + 2).setOnPress(button -> {
		    TileParticleInjector injector = menu.getSafeHost();
		    if (injector == null) {
			return;
		    }
		    injector.usingGateway.setValue(!injector.usingGateway.getValue());
		}).setIcon(NuclearIconTypes.GATEWAY).onTooltip((graphics, button, x, y) -> {

		    ArrayList<FormattedCharSequence> list = new ArrayList<>();

		    TileParticleInjector injector = menu.getSafeHost();
		    if (injector == null) {
			return;
		    }

		    list.add(NuclearTextUtils.tooltip("particleinjector.gatewaymode")
			    .withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		    if (injector.usingGateway.getValue()) {
			list.add(NuclearTextUtils.tooltip("particleinjector.gatewayenabled")
				.withStyle(ChatFormatting.GREEN).getVisualOrderText());
		    } else {
			list.add(NuclearTextUtils.tooltip("particleinjector.gatewaydisabled")
				.withStyle(ChatFormatting.RED).getVisualOrderText());
		    }

		    graphics.renderTooltip(getFontRenderer(), list, x, y);

		}));

	new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75,
		82, 8, 72);

	addComponent(
		new ScreenComponentGeneric(ScreenComponentGuiTab.GuiInfoTabTextures.REGULAR_RIGHT, imageWidth - 1, 2)
			.setIcon(NuclearJeiTextures.PARTICLEACCELERATOR_DMATOM)
			.onTooltip((graphics, component, x, y) -> {

			    ArrayList<FormattedCharSequence> list = new ArrayList<>();

			    TileParticleInjector injector = menu.getSafeHost();
			    if (injector == null) {
				return;
			    }

			    EntityParticle one = injector.particles[0];

			    float oneSpeed = 0.0F;

			    if (one != null && one.isAlive() && !one.isRemoved()) {
				oneSpeed = one.speed;
			    }

			    list.add(NuclearTextUtils
				    .tooltip("particleinjector.particle1speed", ChatFormatter
					    .getChatDisplayShort(TileElectromagneticGateway.getLightSpeedPerc(oneSpeed),
						    DisplayUnits.PERCENTAGE)
					    .append(" ").append(NuclearDisplayUnits.SPEEDOFLIGHT.getSymbol())
					    .withStyle(ChatFormatting.GRAY))
				    .withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());

			    EntityParticle two = injector.particles[1];

			    float twoSpeed = 0.0F;

			    if (two != null && two.isAlive() && !two.isRemoved()) {
				twoSpeed = two.speed;
			    }

			    list.add(NuclearTextUtils
				    .tooltip("particleinjector.particle2speed", ChatFormatter
					    .getChatDisplayShort(TileElectromagneticGateway.getLightSpeedPerc(twoSpeed),
						    DisplayUnits.PERCENTAGE)
					    .append(" ").append(NuclearDisplayUnits.SPEEDOFLIGHT.getSymbol())
					    .withStyle(ChatFormatting.GRAY))
				    .withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());

			    graphics.renderTooltip(getFontRenderer(), list, x, y);

			}));
    }

    private List<? extends FormattedCharSequence> getElectricInformation() {
	ArrayList<FormattedCharSequence> list = new ArrayList<>();

	TileParticleInjector injector = menu.getSafeHost();
	if (injector == null) {
	    return list;
	}

	ComponentElectrodynamic el = injector.getComponent(IComponentType.Electrodynamic);
	list.add(NuclearTextUtils
		.tooltip("particleinjector.charge",
			ChatFormatter.getChatDisplayShort(el.getJoulesStored(), DisplayUnits.JOULES)
				.withStyle(ChatFormatting.GRAY),
			ChatFormatter
				.getChatDisplayShort(NuclearConfig.INSTANCE.PARTICLEINJECTOR_USAGE_PER_PARTICLE.get(),
					DisplayUnits.JOULES)
				.withStyle(ChatFormatting.GRAY))
		.withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
	list.add(ElectroTextUtils
		.gui("machine.voltage",
			ChatFormatter.getChatDisplayShort(el.getVoltage(), DisplayUnits.VOLTAGE)
				.withStyle(ChatFormatting.GRAY))
		.withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());

	return list;
    }

}