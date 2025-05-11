package nuclearscience.client.screen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import nuclearscience.api.quantumtunnel.TunnelFrequencyBuffer;
import nuclearscience.common.inventory.container.ContainerQuantumTunnel;
import nuclearscience.common.tile.TileQuantumTunnel;
import nuclearscience.prefab.screen.component.NuclearIconTypes;
import nuclearscience.prefab.screen.component.quantumtunnel.WrapperEditFrequency;
import nuclearscience.prefab.screen.component.quantumtunnel.WrapperIOEditor;
import nuclearscience.prefab.screen.component.quantumtunnel.WrapperNewFrequency;
import nuclearscience.prefab.screen.component.quantumtunnel.WrapperQuantumTunnelFrequencies;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.ScreenComponentVerticalSlider;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentGuiTab;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.utilities.object.TransferPack;

public class ScreenQuantumTunnel extends GenericScreen<ContainerQuantumTunnel> {

    public WrapperQuantumTunnelFrequencies frequencyWrapper;
    public WrapperIOEditor ioWrapper;
    public WrapperNewFrequency newFrequencyWrapper;
    public WrapperEditFrequency editFrequencyWrapper;

    public ScreenComponentVerticalSlider slider;

    @SuppressWarnings("removal")
	public ScreenQuantumTunnel(ContainerQuantumTunnel container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);

        imageHeight += 35;

        frequencyWrapper = new WrapperQuantumTunnelFrequencies(this, 0, 0);

        addComponent(slider = new ScreenComponentVerticalSlider(5, 64, 125).setClickConsumer(frequencyWrapper.getSliderClickedConsumer()).setDragConsumer(frequencyWrapper.getSliderDraggedConsumer()));

        ioWrapper = new WrapperIOEditor(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 80, 28, 8, 23);

        newFrequencyWrapper = new WrapperNewFrequency(this, -AbstractScreenComponentInfo.SIZE + 1, 2, 0, 15);

        editFrequencyWrapper = new WrapperEditFrequency(this, 0, 10);

        addComponent(new ScreenComponentGuiTab(ScreenComponentGuiTab.GuiInfoTabTextures.REGULAR, NuclearIconTypes.BUFFER, () -> {

            TileQuantumTunnel tile = getMenu().getSafeHost();

            if(tile == null) {
                return Collections.emptyList();
            }

            TunnelFrequencyBuffer buffer = tile.clientBuffer;

            List<IReorderingProcessor> info = new ArrayList<>();

            info.add(NuclearTextUtils.tooltip("quantumtunnel.buffer").withStyle(TextFormatting.BOLD, TextFormatting.YELLOW).getVisualOrderText());

            ItemStack item = buffer.getBufferedItem();

            info.add(new TranslationTextComponent(item.getDescriptionId()).getVisualOrderText());
            info.add(new StringTextComponent(" " + item.getCount()).withStyle(TextFormatting.GRAY).getVisualOrderText());

            FluidStack fluid = buffer.getBufferedFluid();

            info.add(new TranslationTextComponent(fluid.getTranslationKey()).getVisualOrderText());
            info.add(new StringTextComponent(" ").append(ChatFormatter.formatFluidMilibuckets(fluid.getAmount()).withStyle(TextFormatting.GRAY)).getVisualOrderText());

            TransferPack energy = buffer.getBufferedEnergy();

            info.add(ChatFormatter.getChatDisplayShort(energy.getJoules(), DisplayUnits.JOULES).getVisualOrderText());
            info.add(new StringTextComponent(" ").append(ChatFormatter.getChatDisplayShort(energy.getVoltage(), DisplayUnits.VOLTAGE).withStyle(TextFormatting.GRAY)).getVisualOrderText());

            return info;

        }, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE * 2 + 2));


    }

    @Override
	public void tick() {
        super.tick();
        frequencyWrapper.tick();
    }

    @Override
    protected void initializeComponents() {
        super.initializeComponents();
        playerInvLabel.setVisible(false);
    }
    
    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
    	if (frequencyWrapper != null) {
            if (pDelta > 0) {
                // scroll up
                frequencyWrapper.handleMouseScroll(-1);
            } else if (pDelta < 0) {
                // scroll down
                frequencyWrapper.handleMouseScroll(1);
            }
        }
    	return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (slider != null && slider.isVisible()) {
            slider.mouseClicked(mouseX, mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (slider != null && slider.isVisible()) {
            slider.mouseReleased(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
    	InputMappings.Input mouseKey = InputMappings.getKey(pKeyCode, pScanCode);
        if (this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey) && newFrequencyWrapper.nameEditBox.isFocused()) {
            return false;
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if(slider.isVisible()) {
            return slider.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }
}