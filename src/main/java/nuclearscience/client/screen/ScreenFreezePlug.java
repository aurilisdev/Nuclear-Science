package nuclearscience.client.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
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

	public ScreenFreezePlug(ContainerFreezePlug container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2).wattage(NuclearConstants.FREEZEPLUG_USAGE_PER_TICK * 20));
		addComponent(new ScreenComponentMultiLabel(0, 0, poseStack -> {
			TileFreezePlug plug = menu.getSafeHost();
			if (plug == null) {
				return;
			}
			if (plug.isFrozen()) {
				font.draw(poseStack, NuclearTextUtils.gui("freezeplug.status", NuclearTextUtils.gui("freezeplug.frozen").withStyle(TextFormatting.GREEN)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText(), 40, 30, 0);

			} else {
				font.draw(poseStack, NuclearTextUtils.gui("freezeplug.status", NuclearTextUtils.gui("freezeplug.off").withStyle(TextFormatting.RED)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText(), 40, 30, 0);
			}
			font.draw(poseStack, NuclearTextUtils.gui("freezeplug.saltbonus", ChatFormatter.getChatDisplayShort(plug.getSaltBonus() * 100.0, DisplayUnits.PERCENTAGE).withStyle(TextFormatting.BLACK)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText(), 40, 50, 0);
		}));

	}
}