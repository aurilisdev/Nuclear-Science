package nuclearscience.client.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import nuclearscience.client.screen.util.GenericInterfaceBoundScreen;
import nuclearscience.common.inventory.container.ContainerControlRodModule;

public class ScreenControlRodModule extends GenericInterfaceBoundScreen<ContainerControlRodModule> {

    public ScreenControlRodModule(ContainerControlRodModule container, PlayerInventory inv, ITextComponent title) {
        super(container, inv, title, false, false);
        binderWrapper.hideSlots();
    }

    @Override
    public void updateNonSelectorVisibility(boolean visible) {

    }
}
