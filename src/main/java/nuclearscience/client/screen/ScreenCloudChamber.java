package nuclearscience.client.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.common.inventory.container.ContainerCloudChamber;
import nuclearscience.common.settings.NuclearConfig;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.ScreenComponentSimpleLabel;
import voltaic.prefab.screen.component.types.gauges.ScreenComponentFluidGauge;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerSimple;
import voltaic.prefab.utilities.math.Color;

public class ScreenCloudChamber extends GenericScreen<ContainerCloudChamber> {
    public ScreenCloudChamber(ContainerCloudChamber container, Inventory inv, Component title) {
	super(container, inv, title);

	addComponent(new ScreenComponentFluidGauge(() -> container
		.getSafeHost().map(boiler -> boiler
			.<ComponentFluidHandlerSimple>requireComponent(IComponentType.FluidHandler).getInputTanks()[0])
		.orElse(null), 21, 18));

	addComponent(new ScreenComponentSimpleLabel(60, 25, 10, Color.TEXT_GRAY,
		() -> container.getSafeHost().map(boiler -> {
		    MutableComponent status = boiler.active.getValue()
			    ? NuclearTextUtils.gui("cloudchamber.active").withStyle(ChatFormatting.GREEN)
			    : NuclearTextUtils.gui("cloudchamber.offline").withStyle(ChatFormatting.DARK_RED);
		    return NuclearTextUtils.gui("cloudchamber.status", status);
		}).orElseGet(() -> NuclearTextUtils.gui("cloudchamber.status",
			NuclearTextUtils.gui("cloudchamber.offline").withStyle(ChatFormatting.DARK_RED)))));

	addComponent(new ScreenComponentSimpleLabel(60, 45, 10, Color.TEXT_GRAY,
		() -> container.getSafeHost().map(boiler -> {
		    int count = boiler.active.getValue() ? boiler.sources.getValue().size() : 0;
		    return NuclearTextUtils.gui("cloudchamber.detected",
			    Component.literal("" + count).withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY));
		}).orElseGet(() -> NuclearTextUtils.gui("cloudchamber.detected", 0))));

	addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2)
		.wattage(NuclearConfig.getInstance().CLOUD_CHAMBER_ENERGY_USAGE_PER_TICK.get() * 20));

    }
}
