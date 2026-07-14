package nuclearscience.client.screen;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import nuclearscience.client.screen.util.GenericInterfaceBoundScreen;
import nuclearscience.common.inventory.container.ContainerMonitorModule;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;
import nuclearscience.common.tile.reactor.fusion.TileFusionReactorCore;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileMonitorModule;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.TileFissionInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.TileFusionInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.TileMSInterface;
import nuclearscience.common.tile.reactor.moltensalt.TileFreezePlug;
import nuclearscience.common.tile.reactor.moltensalt.TileMSReactorCore;
import nuclearscience.prefab.screen.component.NuclearIconTypes;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.screen.ITexture;
import voltaic.prefab.inventory.container.slot.item.SlotGeneric;
import voltaic.prefab.screen.component.types.ScreenComponentCustomRender;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.RenderingUtils;
import voltaic.prefab.utilities.VoltaicTextUtils;
import voltaic.prefab.utilities.math.Color;

public class ScreenMonitorModule extends GenericInterfaceBoundScreen<ContainerMonitorModule> {

    private boolean hidden = false;

    private static final ITexture EMPTY_FUEL = NuclearIconTypes.FUEL_CELL_DARK;

    public ScreenMonitorModule(ContainerMonitorModule container, PlayerInventory inv, ITextComponent title) {
        super(container, inv, title, true, false);

        for (int i = 0; i < getMenu().slots.size(); i++) {

            ((SlotGeneric) getMenu().slots.get(i)).setActive(false);

        }

        addComponent(new ScreenComponentCustomRender(0, 0, poseStack -> {
            if(hidden) {
                return;
            }

            TileMonitorModule tile = menu.getSafeHost();

            if(tile == null) {
                return;
            }

            GenericTileInterface.InterfaceType type = GenericTileInterface.InterfaceType.values()[tile.interfaceType.getValue()];

            FontRenderer font = getFontRenderer();

            int guiWidth = (int) getGuiWidth();
            int guiHeight = (int) getGuiHeight();

            fill(poseStack, guiWidth + 17, guiHeight + 17, guiWidth + 159, guiHeight + 149, new Color(112, 112, 112, 255).color());

            if(!tile.linked.getValue() || type == GenericTileInterface.InterfaceType.NONE || tile.interfaceLocation.getValue().equals(BlockEntityUtils.OUT_OF_REACH)) {
                font.draw(poseStack, NuclearTextUtils.gui("logisticsnetwork.unlinked"), guiWidth + 20, guiHeight + 20, Color.TEXT_GRAY.color());
                return;
            }

            TileEntity blockEntity = tile.getLevel().getBlockEntity(tile.interfaceLocation.getValue());

            switch (type) {
                case FISSION :

                    if(!(blockEntity instanceof TileFissionInterface)) {
                        font.draw(poseStack, NuclearTextUtils.gui("logisticsnetwork.unlinked"), guiWidth + 20, guiHeight + 20, Color.TEXT_GRAY.color());
                        return;
                    }

                    TileFissionInterface fissionInterface = (TileFissionInterface) blockEntity;

                    if(fissionInterface.reactor == null || !fissionInterface.reactor.valid() || !(fissionInterface.reactor.getSafe() instanceof TileFissionReactorCore)) {
                        font.draw(poseStack, NuclearTextUtils.gui("logisticsnetwork.unlinked"), guiWidth + 20, guiHeight + 20, Color.TEXT_GRAY.color());
                        return;
                    }

                    TileFissionReactorCore fissionCore = fissionInterface.reactor.getSafe();

                    Minecraft.getInstance().getItemRenderer().renderGuiItem(GenericTileInterface.getItemFromType(type), guiWidth + 80, guiHeight + 20);

                    font.draw(poseStack, NuclearTextUtils.gui("logisticsnetwork.temperature", ChatFormatter.getChatDisplayShort(TileFissionReactorCore.getActualTemp(fissionCore.temperature.getValue()), DisplayUnits.TEMPERATURE_CELCIUS).withStyle(TextFormatting.GOLD)), guiWidth + 20, guiHeight + 45, Color.TEXT_GRAY.color());

                    font.draw(poseStack, NuclearTextUtils.gui("logisticsnetwork.fuel"), guiWidth + 20, guiHeight + 65, Color.TEXT_GRAY.color());

                    ComponentInventory inventory = fissionCore.getComponent(IComponentType.Inventory);

                    List<ItemStack> fuels = inventory.getItems().subList(0, 4);

                    int i = 0;

                    int empty = 0;

                    for(ItemStack item : fuels) {
                        if(item.isEmpty()) {
                        	RenderingUtils.bindTexture(EMPTY_FUEL.getLocation());
                            blit(poseStack, guiWidth + 20 + i * 20 + 2, guiHeight + 75 + 2, EMPTY_FUEL.textureU(), EMPTY_FUEL.textureV(), EMPTY_FUEL.textureWidth(), EMPTY_FUEL.textureHeight(), EMPTY_FUEL.imageWidth(), EMPTY_FUEL.imageHeight());
                            empty++;
                        } else {
                        	Minecraft.getInstance().getItemRenderer().renderGuiItem(item, guiWidth + 20 + i * 20, guiHeight + 75);
                            Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, item, guiWidth + 20 + i * 20, guiHeight + 75);
                        }
                        i++;
                    }

                    font.draw(poseStack, NuclearTextUtils.gui("logisticsnetwork.other"), guiWidth + 110, guiHeight + 65, Color.TEXT_GRAY.color());

                    ItemStack deuterium = inventory.getItem(TileFissionReactorCore.DUETERIUM_SLOT);

                    if(deuterium.isEmpty()) {
                    	RenderingUtils.bindTexture(EMPTY_FUEL.getLocation());
                        blit(poseStack, guiWidth + 110 + 2, guiHeight + 75 + 2, EMPTY_FUEL.textureU(), EMPTY_FUEL.textureV(), EMPTY_FUEL.textureWidth(), EMPTY_FUEL.textureHeight(), EMPTY_FUEL.imageWidth(), EMPTY_FUEL.imageHeight());
                    } else {
                        Minecraft.getInstance().getItemRenderer().renderGuiItem(deuterium, guiWidth + 110, guiHeight + 75);
                        Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, deuterium, guiWidth + 110, guiHeight + 75);
                    }

                    ItemStack tritium = inventory.getOutputContents().get(0);

                    if(tritium.isEmpty()) {
                    	RenderingUtils.bindTexture(EMPTY_FUEL.getLocation());
                        blit(poseStack, guiWidth + 130 + 2, guiHeight + 75 + 2, EMPTY_FUEL.textureU(), EMPTY_FUEL.textureV(), EMPTY_FUEL.textureWidth(), EMPTY_FUEL.textureHeight(), EMPTY_FUEL.imageWidth(), EMPTY_FUEL.imageHeight());
                    } else {
                        Minecraft.getInstance().getItemRenderer().renderGuiItem(tritium, guiWidth + 130, guiHeight + 75);
                        Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, tritium, guiWidth + 130, guiHeight + 75);
                    }

                    ITextComponent status = NuclearTextUtils.gui("logisticsnetwork.statusgood").withStyle(TextFormatting.GREEN);

                    if(empty == 4) {
                        status = NuclearTextUtils.gui("logisticsnetwork.statusnofuel").withStyle(TextFormatting.YELLOW);
                    } else if (fissionCore.temperature.getValue() > TileFissionReactorCore.MELTDOWN_TEMPERATURE_ACTUAL) {
                        status = NuclearTextUtils.gui("logisticsnetwork.statusoverheat").withStyle(TextFormatting.RED, TextFormatting.BOLD);
                    }

                    font.draw(poseStack, NuclearTextUtils.gui("logisticsnetwork.status", status), guiWidth + 20, guiHeight + 105, Color.TEXT_GRAY.color());

                    break;
                case MS:

                    if(!(blockEntity instanceof TileMSInterface)) {
                        font.draw(poseStack, NuclearTextUtils.gui("logisticsnetwork.unlinked"), guiWidth + 20, guiHeight + 20, Color.TEXT_GRAY.color());
                        return;
                    }

                    TileMSInterface msInterface = (TileMSInterface) blockEntity;

                    if(msInterface.reactor == null || !msInterface.reactor.valid() || !(msInterface.reactor.getSafe() instanceof TileMSReactorCore)) {
                        font.draw(poseStack, NuclearTextUtils.gui("logisticsnetwork.unlinked"), guiWidth + 20, guiHeight + 20, Color.TEXT_GRAY.color());
                        return;
                    }

                    TileMSReactorCore msCore = msInterface.reactor.getSafe();

                    Minecraft.getInstance().getItemRenderer().renderGuiItem(GenericTileInterface.getItemFromType(type), guiWidth + 80, guiHeight + 20);

                    font.draw(poseStack, NuclearTextUtils.gui("logisticsnetwork.temperature", ChatFormatter.getChatDisplayShort(msCore.temperature.getValue(), DisplayUnits.TEMPERATURE_CELCIUS).withStyle(TextFormatting.GOLD)), guiWidth + 20, guiHeight + 45, Color.TEXT_GRAY.color());

                    font.draw(poseStack, NuclearTextUtils.gui("logisticsnetwork.fuel"), guiWidth + 20, guiHeight + 65, Color.TEXT_GRAY.color());

                    font.draw(poseStack, VoltaicTextUtils.ratio(ChatFormatter.getChatDisplayShort(msCore.currentFuel.getValue() / 1000.0, DisplayUnits.BUCKETS), ChatFormatter.getChatDisplayShort(TileMSReactorCore.FUEL_CAPACITY / 1000.0, DisplayUnits.BUCKETS)), guiWidth + 30, guiHeight + 75, Color.WHITE.color());

                    font.draw(poseStack, NuclearTextUtils.gui("logisticsnetwork.waste"), guiWidth + 20, guiHeight + 90, Color.TEXT_GRAY.color());

                    font.draw(poseStack, VoltaicTextUtils.ratio(ChatFormatter.getChatDisplayShort(msCore.currentWaste.getValue() / 1000.0, DisplayUnits.BUCKETS), ChatFormatter.getChatDisplayShort(TileMSReactorCore.WASTE_CAP / 1000.0, DisplayUnits.BUCKETS)), guiWidth + 30, guiHeight + 100, Color.WHITE.color());

                    status = NuclearTextUtils.gui("logisticsnetwork.statusgood").withStyle(TextFormatting.GREEN);

                    if (!(msCore.clientPlugCache.getSafe() instanceof TileFreezePlug)) {
                        status = NuclearTextUtils.gui("msreactor.status.nofreezeplug").withStyle(TextFormatting.RED);
                    } else if (msCore.clientPlugCache.getSafe() instanceof TileFreezePlug && !((TileFreezePlug) msCore.clientPlugCache.getSafe()).isFrozen()) {
                        status = NuclearTextUtils.gui("msreactor.warning.freezeoff").withStyle(TextFormatting.YELLOW);
                    } else if (msCore.wasteIsFull.getValue()) {
                        status = NuclearTextUtils.gui("msreactor.status.wastefull").withStyle(TextFormatting.YELLOW);
                    } else if (msCore.temperature.getValue() > TileMSReactorCore.MELTDOWN_TEMPERATURE) {
                        status = NuclearTextUtils.gui("logisticsnetwork.statusoverheat").withStyle(TextFormatting.RED, TextFormatting.BOLD);
                    }

                    font.draw(poseStack, NuclearTextUtils.gui("logisticsnetwork.status", status), guiWidth + 20, guiHeight + 115, Color.TEXT_GRAY.color());


                    break;
                case FUSION:

                    if(!(blockEntity instanceof TileFusionInterface)) {
                        font.draw(poseStack, NuclearTextUtils.gui("logisticsnetwork.unlinked"), guiWidth + 20, guiHeight + 20, Color.TEXT_GRAY.color());
                        return;
                    }

                    TileFusionInterface fusionInterface = (TileFusionInterface) blockEntity;

                    if(fusionInterface.reactor == null || !fusionInterface.reactor.valid() || !(fusionInterface.reactor.getSafe() instanceof TileFusionReactorCore)) {
                        font.draw(poseStack, NuclearTextUtils.gui("logisticsnetwork.unlinked"), guiWidth + 20, guiHeight + 20, Color.TEXT_GRAY.color());
                        return;
                    }

                    TileFusionReactorCore fusionCore = fusionInterface.reactor.getSafe();
                    ComponentElectrodynamic electro = fusionCore.getComponent(IComponentType.Electrodynamic);

                    Minecraft.getInstance().getItemRenderer().renderGuiItem(GenericTileInterface.getItemFromType(type), guiWidth + 80, guiHeight + 20);

                    font.draw(poseStack, NuclearTextUtils.gui("logisticsnetwork.deuterium"), guiWidth + 20, guiHeight + 45, Color.TEXT_GRAY.color());

                    font.draw(poseStack, VoltaicTextUtils.ratio(new StringTextComponent(fusionCore.deuterium.getValue() + ""), new StringTextComponent(NuclearConstants.FUSIONREACTOR_MAXSTORAGE + "")), guiWidth + 30, guiHeight + 55, Color.WHITE.color());

                    font.draw(poseStack, NuclearTextUtils.gui("logisticsnetwork.tritium"), guiWidth + 20, guiHeight + 70, Color.TEXT_GRAY.color());

                    font.draw(poseStack, VoltaicTextUtils.ratio(new StringTextComponent(fusionCore.tritium.getValue() + ""), new StringTextComponent(NuclearConstants.FUSIONREACTOR_MAXSTORAGE + "")), guiWidth + 30, guiHeight + 80, Color.WHITE.color());

                    font.draw(poseStack, NuclearTextUtils.gui("logisticsnetwork.power"), guiWidth + 20, guiHeight + 95, Color.TEXT_GRAY.color());

                    font.draw(poseStack, ChatFormatter.getChatDisplayShort(Math.min(1.0, electro.getJoulesStored() / NuclearConstants.FUSIONREACTOR_USAGE_PER_TICK) * 100.0, DisplayUnits.PERCENTAGE), guiWidth + 30, guiHeight + 105, Color.WHITE.color());

                    status = NuclearTextUtils.gui("logisticsnetwork.statusgood").withStyle(TextFormatting.GREEN);

                    if (fusionCore.tritium.getValue() < 1 || fusionCore.deuterium.getValue() < 1) {
                        status = NuclearTextUtils.gui("logisticsnetwork.statusnofuel").withStyle(TextFormatting.RED);
                    } else if (electro.getJoulesStored() < NuclearConstants.FUSIONREACTOR_USAGE_PER_TICK) {
                        status = NuclearTextUtils.gui("logisticsnetwork.statusnopower").withStyle(TextFormatting.YELLOW);
                    }

                    font.draw(poseStack, NuclearTextUtils.gui("logisticsnetwork.status", status), guiWidth + 20, guiHeight + 120, Color.TEXT_GRAY.color());



                    break;
                default:
                    break;
            }

        }));

    }

    @Override
    public void updateNonSelectorVisibility(boolean visible) {
        hidden = !visible;
    }
}
