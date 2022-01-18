package nuclearscience.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.gui.type.ScreenComponentTextInputBar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.common.inventory.container.ContainerQuantumCapacitor;
import nuclearscience.common.packet.NetworkHandler;
import nuclearscience.common.packet.PacketSetQuantumCapacitorData;
import nuclearscience.common.tile.TileQuantumCapacitor;

@OnlyIn(Dist.CLIENT)
public class ScreenQuantumCapacitor extends GenericScreen<ContainerQuantumCapacitor> {
	public ScreenQuantumCapacitor(ContainerQuantumCapacitor container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		components.add(new ScreenComponentTextInputBar(this, 115, 14));
		components.add(new ScreenComponentTextInputBar(this, 115, 34));
	}

	private EditBox outputField;
	private EditBox frequencyField;

	@Override
	protected void containerTick() {
		super.containerTick();
		outputField.tick();
		frequencyField.tick();
	}

	@Override
	protected void init() {
		super.init();
		initFields();
	}

	protected void initFields() {
		minecraft.keyboardHandler.setSendRepeatsToGui(true);
		int i = (width - imageWidth) / 2;
		int j = (height - imageHeight) / 2;
		outputField = new EditBox(font, i + 120, j + 18, 46, 13, new TranslatableComponent("container.quantumcapacitor.joulesoutput"));
		outputField.setTextColor(-1);
		outputField.setTextColorUneditable(-1);
		outputField.setBordered(false);
		outputField.setMaxLength(6);
		outputField.setResponder(this::updateOutput);

		frequencyField = new EditBox(font, i + 120, j + 18 + 20, 46, 13, new TranslatableComponent("container.quantumcapacitor.frequency"));
		frequencyField.setTextColor(-1);
		frequencyField.setTextColorUneditable(-1);
		frequencyField.setBordered(false);
		frequencyField.setMaxLength(6);
		frequencyField.setResponder(this::updateFreq);

		addWidget(outputField);
		addWidget(frequencyField);
	}

	private boolean needsUpdate = true;

	private void updateValues(String coord) {
		if (!coord.isEmpty()) {
			Double triedOutput = 0.0;
			try {
				triedOutput = Double.parseDouble(outputField.getValue());
			} catch (Exception e) {
				// Not required
			}
			Integer frequency = 0;
			try {
				frequency = Integer.parseInt(frequencyField.getValue());
			} catch (Exception e) {
				// Not required
			}
			if (menu.getHostFromIntArray() != null) {
				NetworkHandler.CHANNEL
						.sendToServer(new PacketSetQuantumCapacitorData(menu.getHostFromIntArray().getBlockPos(), triedOutput, frequency));
			}
		}
	}

	private void updateFreq(String val) {
		frequencyField.setFocus(true);
		outputField.setFocus(false);
		updateValues(val);
	}

	private void updateOutput(String val) {
		frequencyField.setFocus(false);
		outputField.setFocus(true);
		updateValues(val);
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String s = outputField.getValue();
		String s1 = frequencyField.getValue();
		init(minecraft, width, height);
		outputField.setValue(s);
		frequencyField.setValue(s1);
	}

	@Override
	public void removed() {
		super.removed();
		minecraft.keyboardHandler.setSendRepeatsToGui(false);
	}

	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		if (needsUpdate && menu.getHostFromIntArray() != null) {
			needsUpdate = false;
			outputField.setValue("" + menu.getHostFromIntArray().outputJoules);
			frequencyField.setValue("" + menu.getHostFromIntArray().frequency);
		}
		outputField.render(matrixStack, mouseX, mouseY, partialTicks);
		frequencyField.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	@Override
	protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
		super.renderLabels(matrixStack, mouseX, mouseY);
		TileQuantumCapacitor box = menu.getHostFromIntArray();
		if (box != null) {
			font.draw(matrixStack,
					new TranslatableComponent("gui.machine.current", ChatFormatter
							.getChatDisplayShort(box.getOutputJoules() * 20.0 / TileQuantumCapacitor.DEFAULT_VOLTAGE, DisplayUnit.AMPERE)),
					inventoryLabelX, (float) inventoryLabelY - 55, 4210752);
			font.draw(matrixStack,
					new TranslatableComponent("gui.machine.transfer",
							ChatFormatter.getChatDisplayShort(box.getOutputJoules() * 20.0, DisplayUnit.WATT)),
					inventoryLabelX, (float) inventoryLabelY - 42, 4210752);
			font.draw(matrixStack,
					new TranslatableComponent("gui.machine.voltage",
							ChatFormatter.getChatDisplayShort(TileQuantumCapacitor.DEFAULT_VOLTAGE, DisplayUnit.VOLTAGE)),
					inventoryLabelX, (float) inventoryLabelY - 29, 4210752);
			font.draw(matrixStack,
					new TranslatableComponent("gui.machine.stored",
							ChatFormatter.getChatDisplayShort(box.joulesClient, DisplayUnit.JOULES) + " / "
									+ ChatFormatter.getChatDisplayShort(TileQuantumCapacitor.DEFAULT_MAX_JOULES, DisplayUnit.JOULES)),
					inventoryLabelX, (float) inventoryLabelY - 16, 4210752);
		}
	}
}