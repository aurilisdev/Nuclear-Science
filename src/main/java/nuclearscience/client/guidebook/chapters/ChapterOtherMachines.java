package nuclearscience.client.guidebook.chapters;

import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import nuclearscience.References;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceBlocks;

public class ChapterOtherMachines extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 32, 32, 32, 2.0F, NuclearScienceBlocks.blockAtomicAssembler.asItem());

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

		// QUantum Capacitor
		pageData.add(new TextWrapperObject(NuclearScienceBlocks.blockQuantumCapacitor.asItem().getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setSeparateStart());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 32, 32, 2.0F, NuclearScienceBlocks.blockQuantumCapacitor.asItem()));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.othermachines.quantumcapacitor1")).setIndentions(1).setSeparateStart());

		// Teleporter
		pageData.add(new TextWrapperObject(NuclearScienceBlocks.blockTeleporter.asItem().getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 32, 32, 2.0F, NuclearScienceBlocks.blockTeleporter.asItem()));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.othermachines.teleporter1")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.othermachines.teleporter2")).setIndentions(1).setSeparateStart());

		// Atomic Assembler
		pageData.add(new TextWrapperObject(NuclearScienceBlocks.blockAtomicAssembler.asItem().getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 32, 32, 2.0F, NuclearScienceBlocks.blockAtomicAssembler.asItem()));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.othermachines.atomicassembler1")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.othermachines.atomicassembler2")).setIndentions(1).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, new ResourceLocation(References.ID, "textures/screen/guidebook/atomicassembler1.png")));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.othermachines.atomicassembler3")).setSeparateStart());
	}

}
