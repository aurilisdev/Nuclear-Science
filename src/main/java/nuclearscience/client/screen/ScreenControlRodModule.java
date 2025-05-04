package nuclearscience.client.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.client.screen.util.GenericInterfaceBoundScreen;
import nuclearscience.common.inventory.container.ContainerControlRodModule;

public class ScreenControlRodModule extends GenericInterfaceBoundScreen<ContainerControlRodModule> {

    public ScreenControlRodModule(ContainerControlRodModule container, Inventory inv, Component title) {
        super(container, inv, title, false, false);
        binderWrapper.hideSlots();
    }

    @Override
    public void updateNonSelectorVisibility(boolean visible) {

    }
}
