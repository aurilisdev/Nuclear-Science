package nuclearscience.client.screen;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentMultiLabel;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.common.inventory.container.ContainerFreezePlug;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tile.msreactor.TileFreezePlug;
import nuclearscience.prefab.utils.NuclearTextUtils;

@OnlyIn(Dist.CLIENT)
public class ScreenFreezePlug extends GenericScreen<ContainerFreezePlug> {

	public ScreenFreezePlug(ContainerFreezePlug container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2).wattage(Constants.FREEZEPLUG_USAGE_PER_TICK * 20));
		addComponent(new ScreenComponentMultiLabel(0, 0, stack -> {
			TileFreezePlug plug = menu.getHostFromIntArray();
			if (plug == null) {
				return;
			}
			if (plug.isFrozen()) {
				font.draw(stack, NuclearTextUtils.gui("freezeplug.status", NuclearTextUtils.gui("freezeplug.frozen").withStyle(TextFormatting.GREEN)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText(), 40, 30, 0);

			} else {
				font.draw(stack, NuclearTextUtils.gui("freezeplug.status", NuclearTextUtils.gui("freezeplug.off").withStyle(TextFormatting.RED)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText(), 40, 30, 0);
			}
			font.draw(stack, NuclearTextUtils.gui("freezeplug.saltbonus", ChatFormatter.getChatDisplayShort(plug.getSaltBonus() * 100.0, DisplayUnit.PERCENTAGE).withStyle(TextFormatting.BLACK)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText(), 40, 50, 0);
		}));

	}
}