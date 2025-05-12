package nuclearscience.client.screen;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import nuclearscience.NuclearScience;
import nuclearscience.common.inventory.container.ContainerFissionReactorCore;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.ScreenComponentGeneric;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentTemperature;
import voltaic.prefab.screen.component.types.wrapper.WrapperInventoryIO;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;

public class ScreenFissionReactorCore extends GenericScreen<ContainerFissionReactorCore> {
	public static final ResourceLocation SCREEN_BACKGROUND = NuclearScience.rl("textures/gui/fissionreactor.png");

	public ScreenFissionReactorCore(ContainerFissionReactorCore container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title);

		imageHeight += 10;
		inventoryLabelY += 10;

		addComponent(new ScreenComponentGeneric(ScreenComponentProgress.ProgressTextures.ARROW_RIGHT_OFF, 77, 38));

		addComponent(new ScreenComponentTemperature(() -> {

			List<IReorderingProcessor> list = new ArrayList<>();

			TileFissionReactorCore core = menu.getSafeHost();
			if (core == null) {
				return list;
			}

			list.add(NuclearTextUtils.tooltip("fissionreactor.maxtemp", ChatFormatter.getChatDisplayShort( TileFissionReactorCore.getActualTemp(TileFissionReactorCore.MELTDOWN_TEMPERATURE_ACTUAL), DisplayUnits.TEMPERATURE_CELCIUS).withStyle(TextFormatting.BOLD, TextFormatting.YELLOW)).withStyle(TextFormatting.GRAY).getVisualOrderText());

			IFormattableTextComponent text = ChatFormatter.getChatDisplayShort(TileFissionReactorCore.getActualTemp(core.temperature.getValue()), DisplayUnits.TEMPERATURE_CELCIUS);

			if (core.temperature.getValue() > TileFissionReactorCore.MELTDOWN_TEMPERATURE_ACTUAL) {
				text = text.withStyle(TextFormatting.RED);
			} else if (core.temperature.getValue() > TileFissionReactorCore.MELTDOWN_TEMPERATURE_ACTUAL - 100) {
				text = text.withStyle(TextFormatting.YELLOW);
			} else {
				text = text.withStyle(TextFormatting.GREEN);
			}

			list.add(NuclearTextUtils.tooltip("fissionreactor.currtemp", text).withStyle(TextFormatting.GRAY).getVisualOrderText());
			if (core.temperature.getValue() > TileFissionReactorCore.MELTDOWN_TEMPERATURE_ACTUAL && System.currentTimeMillis() % 1000 < 500) {
				list.add(NuclearTextUtils.tooltip("fissionreactor.warning").withStyle(TextFormatting.BOLD, TextFormatting.RED).getVisualOrderText());
			}

			return list;
		}, -AbstractScreenComponentInfo.SIZE + 1, 2));

		new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82 + 10, 8, 72 + 10);

	}

}