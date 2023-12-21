package nuclearscience.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.editbox.ScreenComponentEditBox;
import electrodynamics.prefab.screen.component.types.ScreenComponentMultiLabel;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.common.inventory.container.ContainerQuantumCapacitor;
import nuclearscience.common.tile.TileQuantumCapacitor;
import nuclearscience.prefab.utils.NuclearTextUtils;

@OnlyIn(Dist.CLIENT)
public class ScreenQuantumCapacitor extends GenericScreen<ContainerQuantumCapacitor> {

	private ScreenComponentEditBox outputField;
	private ScreenComponentEditBox frequencyField;

	private boolean needsUpdate = true;

	public ScreenQuantumCapacitor(ContainerQuantumCapacitor container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		addEditBox(frequencyField = new ScreenComponentEditBox(115, 14, 46, 13, getFontRenderer()).setTextColor(-1).setTextColorUneditable(-1).setMaxLength(6).setResponder(this::updateFreq).setFilter(ScreenComponentEditBox.INTEGER));
		addEditBox(outputField = new ScreenComponentEditBox(115, 34, 46, 13, getFontRenderer()).setTextColor(-1).setTextColorUneditable(-1).setMaxLength(6).setResponder(this::updateOutput).setFilter(ScreenComponentEditBox.POSITIVE_DECIMAL));
		addComponent(new ScreenComponentMultiLabel(0, 0, stack -> {
			TileQuantumCapacitor box = menu.getHostFromIntArray();
			if (box == null) {
				return;
			}
			font.draw(stack, NuclearTextUtils.gui("machine.current", ChatFormatter.getChatDisplayShort(box.getOutputJoules() * 20.0 / TileQuantumCapacitor.DEFAULT_VOLTAGE, DisplayUnit.AMPERE)), inventoryLabelX, inventoryLabelY - 55, 4210752);
			font.draw(stack, NuclearTextUtils.gui("machine.transfer", ChatFormatter.getChatDisplayShort(box.getOutputJoules() * 20.0, DisplayUnit.WATT)), inventoryLabelX, inventoryLabelY - 42, 4210752);
			font.draw(stack, NuclearTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(TileQuantumCapacitor.DEFAULT_VOLTAGE, DisplayUnit.VOLTAGE)), inventoryLabelX, inventoryLabelY - 29, 4210752);
			font.draw(stack, NuclearTextUtils.gui("machine.stored", ElectroTextUtils.ratio(ChatFormatter.getChatDisplayShort(box.storedJoules.get(), DisplayUnit.JOULES), ChatFormatter.getChatDisplayShort(TileQuantumCapacitor.DEFAULT_MAX_JOULES, DisplayUnit.JOULES))), inventoryLabelX, inventoryLabelY - 16, 4210752);
		}));
	}

	private void updateFreq(String val) {
		frequencyField.setFocus(true);
		outputField.setFocus(false);
		handleFrequency(val);
	}

	private void updateOutput(String val) {
		frequencyField.setFocus(false);
		outputField.setFocus(true);
		handleOutput(val);
	}

	private void handleFrequency(String freq) {
		if (freq.isEmpty()) {
			return;
		}

		Integer frequency = 0;

		try {
			frequency = Integer.parseInt(frequencyField.getValue());
		} catch (Exception e) {
			// Not required
		}

		TileQuantumCapacitor cap = menu.getHostFromIntArray();

		if (cap == null) {
			return;
		}

		cap.frequency.set(frequency);

		cap.frequency.updateServer();

	}

	private void handleOutput(String out) {

		if (out.isEmpty()) {
			return;
		}

		Double output = 0.0;
		try {
			output = Double.parseDouble(outputField.getValue());
		} catch (Exception e) {
			// Not required
		}

		TileQuantumCapacitor cap = menu.getHostFromIntArray();

		if (cap == null) {
			return;
		}

		cap.outputJoules.set(output);

		cap.outputJoules.updateServer();

	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
		super.render(stack, mouseX, mouseY, partialTicks);
		if (needsUpdate && menu.getHostFromIntArray() != null) {
			needsUpdate = false;
			outputField.setValue("" + menu.getHostFromIntArray().outputJoules);
			frequencyField.setValue("" + menu.getHostFromIntArray().frequency);
		}
	}

}