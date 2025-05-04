package nuclearscience.client.guidebook.chapters;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import nuclearscience.NuclearScience;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceItems;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.client.guidebook.ScreenGuidebook;
import voltaic.client.guidebook.utils.components.Chapter;
import voltaic.client.guidebook.utils.components.Module;
import voltaic.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import voltaic.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import voltaic.client.guidebook.utils.pagedata.text.TextWrapperObject;

public class ChapterOtherMachines extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 32, 32, 32, 2.0F, NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.atomicassembler));

	public ChapterOtherMachines(Module module) {
		super(module);
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return NuclearTextUtils.guidebook("chapter.othermachines");
	}

	@Override
	public void addData() {

		// Teleporter
		pageData.add(new TextWrapperObject(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.teleporter).getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 32, 32, 2.0F, NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.teleporter)));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.othermachines.teleporter1", ChatFormatter.getChatDisplayShort(NuclearConstants.TELEPORTER_USAGE_PER_TELEPORT, DisplayUnits.JOULES))).setIndentions(1).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 150, 150, 150, NuclearScience.rl("textures/screen/guidebook/teleporter1.png")));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.othermachines.teleporter2", NuclearScienceItems.ITEM_FREQUENCYCARD.get().getDescription().copy().withStyle(ChatFormatting.BOLD))).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.othermachines.teleporter3")).setIndentions(1).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 150, 150, 150, NuclearScience.rl("textures/screen/guidebook/teleporter2.png")));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.othermachines.teleporter4")).setSeparateStart());

		// Chunkloader
		pageData.add(new TextWrapperObject(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.chunkloader).getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 32, 32, 2.0F, NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.chunkloader)));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.othermachines.chunkloader1")).setIndentions(1).setSeparateStart());

		// Atomic Assembler
		pageData.add(new TextWrapperObject(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.atomicassembler).getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 32, 32, 2.0F, NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.atomicassembler)));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.othermachines.atomicassembler1")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.othermachines.atomicassembler2")).setIndentions(1).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, NuclearScience.rl("textures/screen/guidebook/atomicassembler1.png")));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.othermachines.atomicassembler3")).setIndentions(1).setSeparateStart());
	}

}
