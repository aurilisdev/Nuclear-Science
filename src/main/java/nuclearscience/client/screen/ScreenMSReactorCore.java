package nuclearscience.client.screen;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentMultiLabel;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentTemperature;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.common.inventory.container.ContainerMSRReactorCore;
import nuclearscience.common.tile.TileFreezePlug;
import nuclearscience.common.tile.TileMSReactorCore;
import nuclearscience.prefab.screen.component.ScreenComponentReactorFuel;
import nuclearscience.prefab.utils.NuclearTextUtils;

@OnlyIn(Dist.CLIENT)
public class ScreenMSReactorCore extends GenericScreen<ContainerMSRReactorCore> {

	public ScreenMSReactorCore(ContainerMSRReactorCore container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentTemperature(() -> {

			List<FormattedCharSequence> list = new ArrayList<>();

			TileMSReactorCore core = menu.getHostFromIntArray();
			if (core == null) {
				return list;
			}

			MutableComponent text = ChatFormatter.getChatDisplayShort(core.temperature.get(), DisplayUnit.TEMPERATURE_CELCIUS);

			if (core.temperature.get() > TileMSReactorCore.MELTDOWN_TEMPERATURE) {
				text = text.withStyle(ChatFormatting.RED);
			} else if (core.temperature.get() > TileMSReactorCore.MELTDOWN_TEMPERATURE - 100) {
				text = text.withStyle(ChatFormatting.YELLOW);
			} else {
				text = text.withStyle(ChatFormatting.GREEN);
			}

			list.add(text.getVisualOrderText());

			return list;
		}, -AbstractScreenComponentInfo.SIZE + 1, 2));
		addComponent(new ScreenComponentMultiLabel(0, 0, stack -> {
			TileMSReactorCore core = menu.getHostFromIntArray();
			if (core == null) {
				return;
			}

			font.draw(stack, NuclearTextUtils.gui("msreactor.status"), titleLabelX, (float) titleLabelY + 14, 0);

			if (!(core.plugCache.getSafe() instanceof TileFreezePlug)) {
				font.draw(stack, NuclearTextUtils.gui("msreactor.status.nofreezeplug"), titleLabelX + 5, (float) titleLabelY + 24, 4210752);
			} else if (core.wasteIsFull.get()) {
				font.draw(stack, NuclearTextUtils.gui("msreactor.status.wastefull"), titleLabelX + 5, (float) titleLabelY + 24, 4210752);
			} else {
				font.draw(stack, NuclearTextUtils.gui("msreactor.status.good").withStyle(ChatFormatting.GREEN), titleLabelX + 5, (float) titleLabelY + 24, -1);

			}
			font.draw(stack, NuclearTextUtils.gui("msreactor.warning"), titleLabelX, (float) titleLabelY + 38, 0);

			if (core.temperature.get() > TileMSReactorCore.MELTDOWN_TEMPERATURE) {

				if (System.currentTimeMillis() % 1000 < 500) {
					font.draw(stack, NuclearTextUtils.gui("msreactor.warning.overheat"), titleLabelX + 5, (float) titleLabelY + 48, 16711680);
				} else {
					font.draw(stack, NuclearTextUtils.gui("msreactor.warning.overheat"), titleLabelX + 5, (float) titleLabelY + 48, 4210752);
				}

			} else if (core.plugCache.getSafe() instanceof TileFreezePlug plug && !plug.isFrozen()) {
				font.draw(stack, NuclearTextUtils.gui("msreactor.warning.freezeoff").withStyle(ChatFormatting.YELLOW), titleLabelX + 5, (float) titleLabelY + 48, -1);
			} else {
				font.draw(stack, NuclearTextUtils.gui("msreactor.warning.none").withStyle(ChatFormatting.GREEN), titleLabelX + 5, (float) titleLabelY + 48, -1);
			}

		}));
		addComponent(new ScreenComponentReactorFuel(140, 20));
	}

}