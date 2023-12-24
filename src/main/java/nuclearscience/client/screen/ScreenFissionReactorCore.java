package nuclearscience.client.screen;

import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentGeneric;
import electrodynamics.prefab.screen.component.types.ScreenComponentMultiLabel;
import electrodynamics.prefab.screen.component.types.ScreenComponentSimpleLabel;
import electrodynamics.prefab.screen.component.types.wrapper.InventoryIOWrapper;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.References;
import nuclearscience.common.inventory.container.ContainerReactorCore;
import nuclearscience.common.tile.fissionreactor.TileFissionReactorCore;
import nuclearscience.prefab.screen.component.NuclearArrows;
import nuclearscience.prefab.utils.NuclearTextUtils;

@OnlyIn(Dist.CLIENT)
public class ScreenFissionReactorCore extends GenericScreen<ContainerReactorCore> {
	public static final ResourceLocation SCREEN_BACKGROUND = new ResourceLocation(References.ID + ":textures/gui/fissionreactor.png");

	public ScreenFissionReactorCore(ContainerReactorCore container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title);

		imageHeight += 10;
		inventoryLabelY += 10;

		addComponent(new ScreenComponentGeneric(NuclearArrows.FISSION_REACTOR_ARROW_LR, 59, 29));
		addComponent(new ScreenComponentGeneric(NuclearArrows.FISSION_REACTOR_ARROW_DOWN, 117, 53));

		addComponent(new ScreenComponentSimpleLabel(titleLabelX, titleLabelY + 24, height, 4210752, NuclearTextUtils.gui("fissionreactor.deuterium")));
		addComponent(new ScreenComponentMultiLabel(0, 0, stack -> {
			TileFissionReactorCore core = menu.getHostFromIntArray();
			if (core == null) {
				return;
			}
			font.draw(stack, NuclearTextUtils.gui("fissionreactor.temperature", core.temperature.get().intValue() / 4 + 15 + " C"), titleLabelX, titleLabelY + 55, 4210752);
			if (core.temperature.get() > TileFissionReactorCore.MELTDOWN_TEMPERATURE_ACTUAL && System.currentTimeMillis() % 1000 < 500) {
				font.draw(stack, NuclearTextUtils.gui("fissionreactor.warning"), titleLabelX, titleLabelY + 65, 16711680);
			}
		}));
		new InventoryIOWrapper(this, -AbstractScreenComponentInfo.SIZE + 1, 2, 75, 82 + 10, 8, 72 + 10);

	}

}