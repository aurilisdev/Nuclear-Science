package nuclearscience.client.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.common.inventory.container.ContainerFreezePlug;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.common.tile.reactor.moltensalt.TileFreezePlug;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.ScreenComponentMultiLabel;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;

public class ScreenFreezePlug extends GenericScreen<ContainerFreezePlug> {

	public ScreenFreezePlug(ContainerFreezePlug container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2).wattage(NuclearConstants.FREEZEPLUG_USAGE_PER_TICK * 20));
		addComponent(new ScreenComponentMultiLabel(0, 0, poseStack -> {
			TileFreezePlug plug = menu.getSafeHost();
			if (plug == null) {
				return;
			}
			if (plug.isFrozen()) {
				font.draw(poseStack, NuclearTextUtils.gui("freezeplug.status", NuclearTextUtils.gui("freezeplug.frozen").withStyle(ChatFormatting.GREEN)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText(), 40, 30, 0);

			} else {
				font.draw(poseStack, NuclearTextUtils.gui("freezeplug.status", NuclearTextUtils.gui("freezeplug.off").withStyle(ChatFormatting.RED)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText(), 40, 30, 0);
			}
			font.draw(poseStack, NuclearTextUtils.gui("freezeplug.saltbonus", ChatFormatter.getChatDisplayShort(plug.getSaltBonus() * 100.0, DisplayUnits.PERCENTAGE).withStyle(ChatFormatting.BLACK)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText(), 40, 50, 0);
		}));

	}
}