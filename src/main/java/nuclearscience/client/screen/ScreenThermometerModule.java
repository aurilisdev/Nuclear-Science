package nuclearscience.client.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import nuclearscience.client.screen.util.GenericInterfaceBoundScreen;
import nuclearscience.common.inventory.container.ContainerThermometerModule;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileThermometerModule;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.TileFissionInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.TileMSInterface;
import nuclearscience.common.tile.reactor.moltensalt.TileMSReactorCore;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.prefab.inventory.container.slot.item.SlotGeneric;
import voltaic.prefab.screen.component.button.ScreenComponentButton;
import voltaic.prefab.screen.component.editbox.ScreenComponentEditBox;
import voltaic.prefab.screen.component.types.ScreenComponentCustomRender;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.math.Color;

public class ScreenThermometerModule extends GenericInterfaceBoundScreen<ContainerThermometerModule> {

    public ScreenComponentButton<?> modeButton;
    public ScreenComponentButton<?> invertButton;
    public ScreenComponentEditBox targetTempBox;

    private boolean hidden = false;

    private boolean needsUpdate = true;

    public ScreenThermometerModule(ContainerThermometerModule container, Inventory inv, Component title) {
        super(container, inv, title, true, false);

        for (int i = 0; i < getMenu().slots.size(); i++) {

            ((SlotGeneric) getMenu().slots.get(i)).setActive(false);

        }

        addComponent(new ScreenComponentCustomRender(0, 0, graphics -> {
            if(hidden) {
                return;
            }

            TileThermometerModule tile = menu.getSafeHost();

            if(tile == null) {
                modeButton.setVisible(false);
                invertButton.setVisible(false);
                targetTempBox.setVisible(false);
                return;
            }

            GenericTileInterface.InterfaceType type = GenericTileInterface.InterfaceType.values()[tile.interfaceType.getValue()];

            Font font = getFontRenderer();

            int guiWidth = (int) getGuiWidth();
            int guiHeight = (int) getGuiHeight();


            graphics.fill(guiWidth + 17, guiHeight + 17, guiWidth + 159, guiHeight + 149, new Color(112, 112, 112, 255).color());



            if(!tile.linked.getValue() || type == GenericTileInterface.InterfaceType.NONE || tile.interfaceLocation.getValue().equals(BlockEntityUtils.OUT_OF_REACH)) {
                graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.unlinked"), guiWidth + 20, guiHeight + 20, Color.TEXT_GRAY.color(), false);
                modeButton.setVisible(false);
                invertButton.setVisible(false);
                targetTempBox.setVisible(false);
                return;
            }

            BlockEntity blockEntity = tile.getLevel().getBlockEntity(tile.interfaceLocation.getValue());

            double currTemp = 0;

            switch (type) {
                case FISSION:

                    if(!(blockEntity instanceof TileFissionInterface)) {
                        graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.unlinked"), guiWidth + 20, guiHeight + 20, Color.TEXT_GRAY.color(), false);
                        modeButton.setVisible(false);
                        invertButton.setVisible(false);
                        targetTempBox.setVisible(false);
                        return;
                    }

                    TileFissionInterface fissionInterface = (TileFissionInterface) blockEntity;

                    if(fissionInterface.reactor == null || !fissionInterface.reactor.valid() || !(fissionInterface.reactor.getSafe() instanceof TileFissionReactorCore)) {
                        graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.unlinked"), guiWidth + 20, guiHeight + 20, Color.TEXT_GRAY.color(), false);
                        modeButton.setVisible(false);
                        invertButton.setVisible(false);
                        targetTempBox.setVisible(false);
                        return;
                    }

                    TileFissionReactorCore fissionCore = fissionInterface.reactor.getSafe();

                    currTemp = TileFissionReactorCore.getActualTemp(fissionCore.temperature.getValue());

                    break;

                case MS:

                    if(!(blockEntity instanceof TileMSInterface)) {
                        graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.unlinked"), guiWidth + 20, guiHeight + 20, Color.TEXT_GRAY.color(), false);
                        modeButton.setVisible(false);
                        invertButton.setVisible(false);
                        targetTempBox.setVisible(false);
                        return;
                    }

                    TileMSInterface msInterface = (TileMSInterface) blockEntity;

                    if(msInterface.reactor == null || !msInterface.reactor.valid() || !(msInterface.reactor.getSafe() instanceof TileMSReactorCore)) {
                        graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.unlinked"), guiWidth + 20, guiHeight + 20, Color.TEXT_GRAY.color(), false);
                        modeButton.setVisible(false);
                        invertButton.setVisible(false);
                        targetTempBox.setVisible(false);
                        return;
                    }

                    TileMSReactorCore msCore = msInterface.reactor.getSafe();

                    currTemp = msCore.temperature.getValue();

                    break;

                default:
                    graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.unlinked"), guiWidth + 20, guiHeight + 20, Color.TEXT_GRAY.color(), false);
                    modeButton.setVisible(false);
                    invertButton.setVisible(false);
                    targetTempBox.setVisible(false);
                    return;
            }

            modeButton.setVisible(true);
            invertButton.setVisible(true);
            targetTempBox.setVisible(true);

            graphics.renderItem(GenericTileInterface.getItemFromType(type), guiWidth + 80, guiHeight + 20);

            graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.temperature", ChatFormatter.getChatDisplayShort(currTemp, DisplayUnits.TEMPERATURE_CELCIUS).withStyle(ChatFormatting.GOLD)), guiWidth + 20, guiHeight + 45, Color.TEXT_GRAY.color(), false);

            Component text = NuclearTextUtils.gui("logisticsnetwork.outputmode");

            int width = font.width(text);
            int maxWidth = 68;

            int offset = (maxWidth - width) / 2;

            graphics.drawString(font, text, guiWidth + 20 + offset, guiHeight + 60, Color.TEXT_GRAY.color(), false);

            text = NuclearTextUtils.gui("logisticsnetwork.signalmode");

            width = font.width(text);

            offset = (maxWidth - width) / 2;

            graphics.drawString(font, text, guiWidth + 20 + offset + maxWidth, guiHeight + 60, Color.TEXT_GRAY.color(), false);

            graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.targettemp"), guiWidth + 20, guiHeight + 100, Color.TEXT_GRAY.color(), false);

            graphics.drawString(font, DisplayUnits.TEMPERATURE_CELCIUS.getSymbol().copy().withStyle(ChatFormatting.WHITE), guiWidth + 20 + 120 + 2, guiHeight + 113, Color.TEXT_GRAY.color(), false);

            graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.signalstrength", Component.literal("" + tile.redstoneSignal.getValue()).withStyle(ChatFormatting.WHITE)), guiWidth + 20, guiHeight + 135, Color.TEXT_GRAY.color(), false);



        }));

        addComponent(modeButton = new ScreenComponentButton<>(20, 70, 68, 20).setLabel(() -> {
            TileThermometerModule tile = menu.getSafeHost();

            if(tile == null) {
                return Component.empty();
            }

            return switch(TileThermometerModule.Mode.values()[tile.mode.getValue()]) {
                case BUILD_UP -> NuclearTextUtils.gui("logisticsnetwork.modebuildup");
                case CONSTANT -> NuclearTextUtils.gui("logisticsnetwork.modeconstant");
                default -> Component.empty();
            };
        }).setOnPress(button -> {

            TileThermometerModule tile = menu.getSafeHost();

            if(tile == null) {
                return;
            }

            int currMode = tile.mode.getValue();

            if(currMode >= TileThermometerModule.Mode.values().length - 1) {
                currMode = 0;
            } else {
                currMode++;
            }

            tile.mode.setValue(currMode);

        }));

        addComponent(invertButton = new ScreenComponentButton<>(88, 70, 68, 20).setLabel(() -> {
            TileThermometerModule tile = menu.getSafeHost();

            if(tile == null) {
                return Component.empty();
            }

            return tile.inverted.getValue() ? NuclearTextUtils.gui("logisticsnetwork.signalinverted") : NuclearTextUtils.gui("logisticsnetwork.signalnormal");
        }).setOnPress(button -> {

            TileThermometerModule tile = menu.getSafeHost();

            if(tile == null) {
                return;
            }

            tile.inverted.setValue(!tile.inverted.getValue());

        }));

        addEditBox(targetTempBox = new ScreenComponentEditBox(20, 110, 120, 15, getFontRenderer()).setFilter(ScreenComponentEditBox.POSITIVE_DECIMAL).setTextColor(Color.WHITE).setTextColorUneditable(Color.WHITE).setMaxLength(20).setResponder(val -> {

            TileThermometerModule tile = menu.getSafeHost();

            if(tile == null) {
                return;
            }

            double temp = 0;

            try {
                temp = Double.parseDouble(val);
            } catch (Exception e) {

            }

            if(temp < 0) {
                temp = 0;
            }

            tile.targetTemperature.setValue(temp);




        }));

    }

    @Override
    public void updateNonSelectorVisibility(boolean visible) {
        modeButton.setVisible(visible);
        invertButton.setVisible(visible);
        hidden = !visible;
        targetTempBox.setVisible(visible);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);
        TileThermometerModule module =  getMenu().getSafeHost();
        if(needsUpdate && module != null) {
            targetTempBox.setValue(module.targetTemperature.getValue() + "");
            needsUpdate = false;
        }
    }

}
