package nuclearscience.client.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import nuclearscience.common.inventory.container.ContainerCloudChamber;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.common.tile.TileCloudChamber;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.ScreenComponentSimpleLabel;
import voltaic.prefab.screen.component.types.gauges.ScreenComponentFluidGauge;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerSimple;
import voltaic.prefab.utilities.math.Color;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.StringTextComponent;

public class ScreenCloudChamber extends GenericScreen<ContainerCloudChamber> {
    public ScreenCloudChamber(ContainerCloudChamber container, PlayerInventory inv, ITextComponent title) {
        super(container, inv, title);

        addComponent(new ScreenComponentFluidGauge(() -> {
            TileCloudChamber boiler = container.getSafeHost();
            if (boiler != null) {
                return boiler.<ComponentFluidHandlerSimple>getComponent(IComponentType.FluidHandler).getInputTanks()[0];
            }
            return null;
        }, 21, 18));

        addComponent(new ScreenComponentSimpleLabel(60, 25, 10, Color.TEXT_GRAY, () -> {
            TileCloudChamber boiler = container.getSafeHost();
            if (boiler == null) {
                return NuclearTextUtils.gui("cloudchamber.status", NuclearTextUtils.gui("cloudchamber.offline").withStyle(TextFormatting.DARK_RED));
            }
            IFormattableTextComponent status = boiler.active.getValue() ? NuclearTextUtils.gui("cloudchamber.active").withStyle(TextFormatting.GREEN) : NuclearTextUtils.gui("cloudchamber.offline").withStyle(TextFormatting.DARK_RED);
            return NuclearTextUtils.gui("cloudchamber.status", status);
        }));

        addComponent(new ScreenComponentSimpleLabel(60, 45, 10, Color.TEXT_GRAY, () -> {
            TileCloudChamber boiler = container.getSafeHost();
            if (boiler == null) {
                return NuclearTextUtils.gui("cloudchamber.detected", 0);
            }
            int count = boiler.active.getValue() ? boiler.sources.getValue().size() : 0;
            return NuclearTextUtils.gui("cloudchamber.detected", new StringTextComponent("" + count).withStyle(TextFormatting.BOLD, TextFormatting.DARK_GRAY));
        }));

        addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2).wattage(NuclearConstants.CLOUD_CHAMBER_ENERGY_USAGE_PER_TICK * 20));

    }
}
