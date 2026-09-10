package nuclearscience.client.screen;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.common.inventory.container.ContainerMSReactorCore;
import nuclearscience.common.tile.reactor.moltensalt.TileFreezePlug;
import nuclearscience.common.tile.reactor.moltensalt.TileMSReactorCore;
import nuclearscience.prefab.screen.component.ScreenComponentReactorFuel;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.ScreenComponentMultiLabel;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentTemperature;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.utilities.math.Color;

public class ScreenMSReactorCore extends GenericScreen<ContainerMSReactorCore> {

    public static final Color WARNING_COLOR = new Color(255, 0, 0, 255);

    public ScreenMSReactorCore(ContainerMSReactorCore container, Inventory playerInventory, Component title) {
	super(container, playerInventory, title);

	addComponent(new ScreenComponentTemperature(() -> menu.getSafeHost().map(core -> {
	    List<FormattedCharSequence> list = new ArrayList<>();
	    double temperature = core.temperature.getValue();
	    MutableComponent text = ChatFormatter.getChatDisplayShort(temperature, DisplayUnits.TEMPERATURE_CELCIUS);

	    if (temperature > TileMSReactorCore.MELTDOWN_TEMPERATURE) {
		text = text.withStyle(ChatFormatting.RED);
	    } else if (temperature > TileMSReactorCore.MELTDOWN_TEMPERATURE - 100) {
		text = text.withStyle(ChatFormatting.YELLOW);
	    } else {
		text = text.withStyle(ChatFormatting.GREEN);
	    }

	    list.add(text.getVisualOrderText());
	    return list;
	}).orElseGet(ArrayList::new), -AbstractScreenComponentInfo.SIZE + 1, 2));

	addComponent(new ScreenComponentMultiLabel(0, 0, graphics -> menu.getSafeHost().ifPresent(core -> {
	    TileFreezePlug plug = core.getFreezePlug().orElse(null);
	    double temperature = core.temperature.getValue();

	    graphics.drawString(font, NuclearTextUtils.gui("msreactor.status"), titleLabelX, titleLabelY + 14,
		    Color.BLACK.color(), false);

	    if (plug == null) {
		graphics.drawString(font, NuclearTextUtils.gui("msreactor.status.nofreezeplug"), titleLabelX + 5,
			titleLabelY + 24, Color.TEXT_GRAY.color(), false);
	    } else if (core.wasteIsFull.getValue()) {
		graphics.drawString(font, NuclearTextUtils.gui("msreactor.status.wastefull"), titleLabelX + 5,
			titleLabelY + 24, Color.TEXT_GRAY.color(), false);
	    } else {
		graphics.drawString(font, NuclearTextUtils.gui("msreactor.status.good").withStyle(ChatFormatting.GREEN),
			titleLabelX + 5, titleLabelY + 24, Color.WHITE.color(), false);
	    }

	    graphics.drawString(font, NuclearTextUtils.gui("msreactor.warning"), titleLabelX, titleLabelY + 38, 0,
		    false);

	    if (temperature > TileMSReactorCore.MELTDOWN_TEMPERATURE) {
		int colour = System.currentTimeMillis() % 1000 < 500 ? WARNING_COLOR.color() : Color.TEXT_GRAY.color();

		graphics.drawString(font, NuclearTextUtils.gui("msreactor.warning.overheat"), titleLabelX + 5,
			titleLabelY + 48, colour, false);
	    } else if (plug != null && !plug.isFrozen()) {
		graphics.drawString(font,
			NuclearTextUtils.gui("msreactor.warning.freezeoff").withStyle(ChatFormatting.YELLOW),
			titleLabelX + 5, titleLabelY + 48, Color.WHITE.color(), false);
	    } else {
		graphics.drawString(font,
			NuclearTextUtils.gui("msreactor.warning.none").withStyle(ChatFormatting.GREEN), titleLabelX + 5,
			titleLabelY + 48, Color.WHITE.color(), false);
	    }
	})));

	addComponent(new ScreenComponentReactorFuel(140, 20));
    }
}