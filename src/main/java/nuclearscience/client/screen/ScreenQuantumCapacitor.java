package nuclearscience.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.prefab.screen.GenericContainerScreenUpgradeable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.References;
import nuclearscience.common.inventory.container.ContainerQuantumCapacitor;
import nuclearscience.common.packet.NetworkHandler;
import nuclearscience.common.packet.PacketSetQuantumCapacitorData;
import nuclearscience.common.tile.TileQuantumCapacitor;

@OnlyIn(Dist.CLIENT)
public class ScreenQuantumCapacitor extends GenericContainerScreenUpgradeable<ContainerQuantumCapacitor> {
    public static final ResourceLocation SCREEN_BACKGROUND = new ResourceLocation(References.ID + ":textures/gui/quantumcapacitor.png");

    public ScreenQuantumCapacitor(ContainerQuantumCapacitor container, PlayerInventory playerInventory, ITextComponent title) {
	super(container, playerInventory, title);
	xSize = 176;
    }

    @Override
    public ResourceLocation getScreenBackground() {
	return SCREEN_BACKGROUND;
    }

    private TextFieldWidget outputField;
    private TextFieldWidget frequencyField;

    @Override
    public void tick() {
	super.tick();
	outputField.tick();
	frequencyField.tick();
    }

    @Override
    protected void init() {
	super.init();
	initFields();
    }

    protected void initFields() {
	minecraft.keyboardListener.enableRepeatEvents(true);
	int i = (width - xSize) / 2;
	int j = (height - ySize) / 2;
	outputField = new TextFieldWidget(font, i + 120, j + 18, 46, 13, new TranslationTextComponent("container.quantumcapacitor.joulesoutput"));
	outputField.setTextColor(-1);
	outputField.setDisabledTextColour(-1);
	outputField.setEnableBackgroundDrawing(false);
	outputField.setMaxStringLength(6);
	outputField.setResponder(this::updateOutput);

	frequencyField = new TextFieldWidget(font, i + 120, j + 18 + 20, 46, 13,
		new TranslationTextComponent("container.quantumcapacitor.frequency"));
	frequencyField.setTextColor(-1);
	frequencyField.setDisabledTextColour(-1);
	frequencyField.setEnableBackgroundDrawing(false);
	frequencyField.setMaxStringLength(6);
	frequencyField.setResponder(this::updateFreq);

	children.add(outputField);
	children.add(frequencyField);
    }

    private boolean needsUpdate = true;

    private void updateValues(String coord) {
	if (!coord.isEmpty()) {
	    Double triedOutput = 0.0;
	    try {
		triedOutput = Double.parseDouble(outputField.getText());
	    } catch (Exception e) {
		// Not required
	    }
	    Integer frequency = 0;
	    try {
		frequency = Integer.parseInt(frequencyField.getText());
	    } catch (Exception e) {
		// Not required
	    }
	    if (container.getHostFromIntArray() != null) {
		NetworkHandler.CHANNEL
			.sendToServer(new PacketSetQuantumCapacitorData(container.getHostFromIntArray().getPos(), triedOutput, frequency));
	    }
	}
    }

    private void updateFreq(String val) {
	frequencyField.setFocused2(true);
	outputField.setFocused2(false);
	updateValues(val);
    }

    private void updateOutput(String val) {
	frequencyField.setFocused2(false);
	outputField.setFocused2(true);
	updateValues(val);
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
	String s = outputField.getText();
	String s1 = frequencyField.getText();
	init(minecraft, width, height);
	outputField.setText(s);
	frequencyField.setText(s1);
    }

    @Override
    public void onClose() {
	super.onClose();
	minecraft.keyboardListener.enableRepeatEvents(false);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
	super.render(matrixStack, mouseX, mouseY, partialTicks);
	if (needsUpdate && container.getHostFromIntArray() != null) {
	    needsUpdate = false;
	    outputField.setText("" + container.getHostFromIntArray().outputJoules);
	    frequencyField.setText("" + container.getHostFromIntArray().frequency);
	}
	outputField.render(matrixStack, mouseX, mouseY, partialTicks);
	frequencyField.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
	super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
	TileQuantumCapacitor box = container.getHostFromIntArray();
	if (box != null) {
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.quantumcapacitor.current", ChatFormatter
			    .getElectricDisplayShort(box.getOutputJoules() * 20.0 / TileQuantumCapacitor.DEFAULT_VOLTAGE, ElectricUnit.AMPERE)),
		    playerInventoryTitleX, (float) playerInventoryTitleY - 55, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.quantumcapacitor.transfer",
			    ChatFormatter.getElectricDisplayShort(box.getOutputJoules() * 20.0, ElectricUnit.WATT)),
		    playerInventoryTitleX, (float) playerInventoryTitleY - 42, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.quantumcapacitor.voltage",
			    ChatFormatter.getElectricDisplayShort(TileQuantumCapacitor.DEFAULT_VOLTAGE, ElectricUnit.VOLTAGE)),
		    playerInventoryTitleX, (float) playerInventoryTitleY - 29, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.quantumcapacitor.stored",
			    ChatFormatter.getElectricDisplayShort(box.joulesClient, ElectricUnit.JOULES) + " / "
				    + ChatFormatter.getElectricDisplayShort(TileQuantumCapacitor.DEFAULT_MAX_JOULES, ElectricUnit.JOULES)),
		    playerInventoryTitleX, (float) playerInventoryTitleY - 16, 4210752);
	}
    }
}