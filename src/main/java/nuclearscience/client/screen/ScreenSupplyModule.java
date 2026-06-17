package nuclearscience.client.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.client.screen.util.GenericInterfaceBoundScreen;
import nuclearscience.common.inventory.container.ContainerSupplyModule;
import voltaic.prefab.screen.component.types.wrapper.WrapperInventoryIO;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;

public class ScreenSupplyModule extends GenericInterfaceBoundScreen<ContainerSupplyModule> {

    private WrapperInventoryIO wrapper;

    public ScreenSupplyModule(ContainerSupplyModule container, Inventory inv, Component title) {
	super(container, inv, title, true, true);

	wrapper = new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1,
		AbstractScreenComponentInfo.SIZE + 2, 75, 82, 8, 72)
		//
		.hideAdditional(show -> {
		    if (!show) {
			binderWrapper.updateVisibility(false);
			binderSlider.setVisible(false);
			binderWrapper.button.isPressed = false;
			binderWrapper.showSlots();
		    }
		});

    }

    @Override
    public void updateNonSelectorVisibility(boolean visible) {
	if (!visible) {
	    wrapper.updateVisibility(false);
	    wrapper.button.isPressed = false;
	    wrapper.resetSlots();
	}
    }
}
