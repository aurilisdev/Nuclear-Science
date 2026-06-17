package nuclearscience.client.guidebook.chapters;

import electrodynamics.common.item.subtype.SubtypePlate;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import nuclearscience.NuclearScience;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.item.ItemGeigerCounter;
import nuclearscience.common.tile.TileCloudChamber;
import nuclearscience.common.tile.TileFalloutScrubber;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceItems;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.client.guidebook.utils.components.Chapter;
import voltaic.client.guidebook.utils.components.Module;
import voltaic.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import voltaic.client.guidebook.utils.pagedata.text.TextWrapperObject;
import voltaic.common.item.ItemIodineTablet;
import voltaic.common.settings.VoltaicConfig;

public class ChapterRadiation extends Chapter {

    private static final ImageWrapperObject LOGO = new ImageWrapperObject(0, 0, 0, 0, 32, 32, 32, 32,
	    NuclearScience.rl("textures/item/uranium235.png"));

    public ChapterRadiation(Module module) {
	super(module);
    }

    @Override
    public ImageWrapperObject getLogo() {
	return LOGO;
    }

    @Override
    public MutableComponent getTitle() {
	return NuclearTextUtils.guidebook("chapter.radiation");
    }

    @Override
    public void addData() {
	pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.l1.1")).setIndentions(1)
		.setSeparateStart());
	pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75,
		NuclearScience.rl("textures/screen/guidebook/radiationradius1.png")));
	pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.l1.2")));
	pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75,
		NuclearScience.rl("textures/screen/guidebook/radiationradius2.png")));
	pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.l1.3")));
	pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.l2.1")).setIndentions(1)
		.setSeparateStart());
	pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75,
		NuclearScience.rl("textures/screen/guidebook/radiationtooltip.png")));
	pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.l2.2",
		NuclearScienceItems.ITEM_GEIGERCOUNTER.get().getDescription().copy().withStyle(ChatFormatting.BOLD),
		ChatFormatter.roundDecimals(ItemGeigerCounter.POWER_USAGE, 2))));
	blankLine();
	pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.l3")).setIndentions(1)
		.setSeparateStart());
	pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.l4",
		//
		NuclearScienceItems.ITEM_IODINETABLET.get().getDescription().copy().withStyle(ChatFormatting.BOLD),
		//
		Component.literal("" + VoltaicConfig.INSTANCE.IODINE_RESISTANCE_THRESHOLD.get())
			.withStyle(ChatFormatting.BOLD),
		//
		Component.literal("" + ItemIodineTablet.TIME_MINUTES).withStyle(ChatFormatting.BOLD),
		//
		ChatFormatter.getChatDisplayShort((1.0 - VoltaicConfig.INSTANCE.IODINE_RAD_REDUCTION.get()) * 100,
			DisplayUnits.PERCENTAGE).withStyle(ChatFormatting.BOLD)))
		.setIndentions(1).setSeparateStart());
	pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.l5",
		//
		NuclearTextUtils.guidebook("chapter.radiation.hazmatsuit").withStyle(ChatFormatting.BOLD),
		//
		ElectrodynamicsItems.ITEMS_PLATE.getValue(SubtypePlate.lead).getDescription().copy()
			.withStyle(ChatFormatting.BOLD)))
		.setIndentions(1).setSeparateStart());
	pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.l6.1")).setIndentions(1)
		.setSeparateStart());
	pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75,
		NuclearScience.rl("textures/screen/guidebook/radiationshieldingtooltip.png")));
	pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.l6.2")).setSeparateStart());
	pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75,
		NuclearScience.rl("textures/screen/guidebook/radiationshielding1.png")));
	pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.l6.3")).setSeparateStart());
	pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75,
		NuclearScience.rl("textures/screen/guidebook/radiationshielding2.png")));
	blankLine();
	pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.l7",
		NuclearScienceItems.ITEM_ANTIDOTE.get().getDescription().copy().withStyle(ChatFormatting.BOLD))));
	pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.l8",
		NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.cloudchamber).getDescription()
			.copy().withStyle(ChatFormatting.BOLD),
		TileCloudChamber.HORR_RADIUS)).setSeparateStart().setIndentions(1));
	pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.l9",
		NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.falloutscrubber)
			.getDescription().copy().withStyle(ChatFormatting.BOLD),
		VoltaicConfig.INSTANCE.BACKGROUND_RADIATION_DISSIPATION.get(), TileFalloutScrubber.RANGE,
		TileFalloutScrubber.DISIPATION)).setSeparateStart().setIndentions(1));

    }

}
