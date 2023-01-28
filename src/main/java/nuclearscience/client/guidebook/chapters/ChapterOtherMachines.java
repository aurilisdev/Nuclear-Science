package nuclearscience.client.guidebook.chapters;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.client.guidebook.utils.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.TextWrapperObject;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Page;
import electrodynamics.prefab.utilities.ItemUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import nuclearscience.References;
import nuclearscience.prefab.utils.TextUtils;
import nuclearscience.registers.NuclearScienceBlocks;

public class ChapterOtherMachines extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(17, 60, 2.0F, ItemUtils.fromBlock(NuclearScienceBlocks.blockAtomicAssembler));

	@Override
	protected List<Page> genPages() {
		List<Page> pages = new ArrayList<>();

		pages.add(new Page(new TextWrapperObject[] {
				//
				new TextWrapperObject(45, 53, 4210752, TextUtils.guidebook("chapter.othermachines.radiogentitle").withStyle(ChatFormatting.UNDERLINE)),
				//
				new TextWrapperObject(10, 80, 4210752, TextUtils.guidebook("chapter.othermachines.p1l1")),
				//
				new TextWrapperObject(10, 90, 4210752, TextUtils.guidebook("chapter.othermachines.p1l2")),
				//
				new TextWrapperObject(10, 100, 4210752, TextUtils.guidebook("chapter.othermachines.p1l3")),
				//
				new TextWrapperObject(10, 110, 4210752, TextUtils.guidebook("chapter.othermachines.p1l4")),
				//
				new TextWrapperObject(10, 120, 4210752, TextUtils.guidebook("chapter.othermachines.p1l5")),
				//
				new TextWrapperObject(10, 130, 4210752, TextUtils.guidebook("chapter.othermachines.p1l6")),
				//
				new TextWrapperObject(10, 140, 4210752, TextUtils.guidebook("chapter.othermachines.p1l7")),
				//
				new TextWrapperObject(10, 150, 4210752, TextUtils.guidebook("chapter.othermachines.p1l8")),
				//
				new TextWrapperObject(10, 160, 4210752, TextUtils.guidebook("chapter.othermachines.p1l9")),
				//
				new TextWrapperObject(10, 170, 4210752, TextUtils.guidebook("chapter.othermachines.p1l10")),
				//
				new TextWrapperObject(10, 180, 4210752, TextUtils.guidebook("chapter.othermachines.p1l11")) },
				new ItemWrapperObject[] {
						//
						new ItemWrapperObject(17, 50, 2.0F, ItemUtils.fromBlock(NuclearScienceBlocks.blockRadioisotopeGenerator)) }));

		pages.add(new Page(new TextWrapperObject[] {
				//
				new TextWrapperObject(45, 53, 4210752, TextUtils.guidebook("chapter.othermachines.gascenttitle").withStyle(ChatFormatting.UNDERLINE)),
				//
				new TextWrapperObject(10, 80, 4210752, TextUtils.guidebook("chapter.othermachines.p2l1")),
				//
				new TextWrapperObject(10, 90, 4210752, TextUtils.guidebook("chapter.othermachines.p2l2")),
				//
				new TextWrapperObject(10, 100, 4210752, TextUtils.guidebook("chapter.othermachines.p2l3")),
				//
				new TextWrapperObject(10, 110, 4210752, TextUtils.guidebook("chapter.othermachines.p2l4")),
				//
				new TextWrapperObject(10, 120, 4210752, TextUtils.guidebook("chapter.othermachines.p2l5")),
				//
				new TextWrapperObject(10, 130, 4210752, TextUtils.guidebook("chapter.othermachines.p2l6")),
				//
				new TextWrapperObject(10, 140, 4210752, TextUtils.guidebook("chapter.othermachines.p2l7")),
				//
				new TextWrapperObject(10, 150, 4210752, TextUtils.guidebook("chapter.othermachines.p2l8")),
				//
				new TextWrapperObject(10, 160, 4210752, TextUtils.guidebook("chapter.othermachines.p2l9")),
				//
				new TextWrapperObject(10, 170, 4210752, TextUtils.guidebook("chapter.othermachines.p2l10")),
				//
				new TextWrapperObject(10, 180, 4210752, TextUtils.guidebook("chapter.othermachines.p2l11")) },
				new ItemWrapperObject[] {
						//
						new ItemWrapperObject(17, 50, 2.0F, ItemUtils.fromBlock(NuclearScienceBlocks.blockGasCentrifuge)) }));

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, TextUtils.guidebook("chapter.othermachines.p3l1")),
				//
				new TextWrapperObject(10, 50, 4210752, TextUtils.guidebook("chapter.othermachines.p3l2")),
				//
				new TextWrapperObject(10, 60, 4210752, TextUtils.guidebook("chapter.othermachines.p3l3")),
				//
				new TextWrapperObject(10, 70, 4210752, TextUtils.guidebook("chapter.othermachines.p3l4")),
				//
				new TextWrapperObject(10, 80, 4210752, TextUtils.guidebook("chapter.othermachines.p3l5")),
				//
				new TextWrapperObject(10, 90, 4210752, TextUtils.guidebook("chapter.othermachines.p3l6")),
				//
				new TextWrapperObject(10, 100, 4210752, TextUtils.guidebook("chapter.othermachines.p3l7")),
				//
				new TextWrapperObject(10, 110, 4210752, TextUtils.guidebook("chapter.othermachines.p3l8")),
				//
				new TextWrapperObject(10, 120, 4210752, TextUtils.guidebook("chapter.othermachines.p3l9")),
				//
				new TextWrapperObject(10, 130, 4210752, TextUtils.guidebook("chapter.othermachines.p3l10")),
				//
				new TextWrapperObject(10, 140, 4210752, TextUtils.guidebook("chapter.othermachines.p3l11")),
				//
				new TextWrapperObject(10, 150, 4210752, TextUtils.guidebook("chapter.othermachines.p3l12")),
				//
				new TextWrapperObject(10, 160, 4210752, TextUtils.guidebook("chapter.othermachines.p3l13")),
				//
				new TextWrapperObject(10, 170, 4210752, TextUtils.guidebook("chapter.othermachines.p3l14")),
				//
				new TextWrapperObject(10, 180, 4210752, TextUtils.guidebook("chapter.othermachines.p3l15")), }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/gascentrifuge1.png"),
				//
				new ImageWrapperObject(12, 117, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/gascentrifuge2.png") }));

		pages.add(new Page(new TextWrapperObject[] {
				//
				new TextWrapperObject(45, 53, 4210752, TextUtils.guidebook("chapter.othermachines.quantumcaptitle").withStyle(ChatFormatting.UNDERLINE)),
				//
				new TextWrapperObject(10, 80, 4210752, TextUtils.guidebook("chapter.othermachines.p5l1")),
				//
				new TextWrapperObject(10, 90, 4210752, TextUtils.guidebook("chapter.othermachines.p5l2")),
				//
				new TextWrapperObject(10, 100, 4210752, TextUtils.guidebook("chapter.othermachines.p5l3")),
				//
				new TextWrapperObject(10, 110, 4210752, TextUtils.guidebook("chapter.othermachines.p5l4")),
				//
				new TextWrapperObject(10, 120, 4210752, TextUtils.guidebook("chapter.othermachines.p5l5")),
				//
				new TextWrapperObject(10, 130, 4210752, TextUtils.guidebook("chapter.othermachines.p5l6")),
				//
				new TextWrapperObject(10, 140, 4210752, TextUtils.guidebook("chapter.othermachines.p5l7")),
				//
				new TextWrapperObject(10, 150, 4210752, TextUtils.guidebook("chapter.othermachines.p5l8")),
				//
				new TextWrapperObject(10, 160, 4210752, TextUtils.guidebook("chapter.othermachines.p5l9")),
				//
				new TextWrapperObject(10, 170, 4210752, TextUtils.guidebook("chapter.othermachines.p5l10")),
				//
				new TextWrapperObject(10, 180, 4210752, TextUtils.guidebook("chapter.othermachines.p5l11")) },
				new ItemWrapperObject[] {
						//
						new ItemWrapperObject(17, 50, 2.0F, ItemUtils.fromBlock(NuclearScienceBlocks.blockQuantumCapacitor)) }));

		pages.add(new Page(new TextWrapperObject[] {
				//
				new TextWrapperObject(45, 53, 4210752, TextUtils.guidebook("chapter.othermachines.teleportertitle").withStyle(ChatFormatting.UNDERLINE)),
				//
				new TextWrapperObject(10, 80, 4210752, TextUtils.guidebook("chapter.othermachines.p6l1")),
				//
				new TextWrapperObject(10, 90, 4210752, TextUtils.guidebook("chapter.othermachines.p6l2")),
				//
				new TextWrapperObject(10, 100, 4210752, TextUtils.guidebook("chapter.othermachines.p6l3")),
				//
				new TextWrapperObject(10, 110, 4210752, TextUtils.guidebook("chapter.othermachines.p6l4")),
				//
				new TextWrapperObject(10, 120, 4210752, TextUtils.guidebook("chapter.othermachines.p6l5")),
				//
				new TextWrapperObject(10, 130, 4210752, TextUtils.guidebook("chapter.othermachines.p6l6")),
				//
				new TextWrapperObject(10, 140, 4210752, TextUtils.guidebook("chapter.othermachines.p6l7")),
				//
				new TextWrapperObject(10, 150, 4210752, TextUtils.guidebook("chapter.othermachines.p6l8")),
				//
				new TextWrapperObject(10, 160, 4210752, TextUtils.guidebook("chapter.othermachines.p6l9")),
				//
				new TextWrapperObject(10, 170, 4210752, TextUtils.guidebook("chapter.othermachines.p6l10")),
				//
				new TextWrapperObject(10, 180, 4210752, TextUtils.guidebook("chapter.othermachines.p6l11")) },
				new ItemWrapperObject[] {
						//
						new ItemWrapperObject(17, 50, 2.0F, ItemUtils.fromBlock(NuclearScienceBlocks.blockTeleporter)) }));

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, TextUtils.guidebook("chapter.othermachines.p7l1")),
				//
				new TextWrapperObject(10, 50, 4210752, TextUtils.guidebook("chapter.othermachines.p7l2")),
				//
				new TextWrapperObject(10, 60, 4210752, TextUtils.guidebook("chapter.othermachines.p7l3")),
				//
				new TextWrapperObject(10, 70, 4210752, TextUtils.guidebook("chapter.othermachines.p7l4")),
				//
				new TextWrapperObject(10, 80, 4210752, TextUtils.guidebook("chapter.othermachines.p7l5")),
				//
				new TextWrapperObject(10, 90, 4210752, TextUtils.guidebook("chapter.othermachines.p7l6")),
				//
				new TextWrapperObject(10, 100, 4210752, TextUtils.guidebook("chapter.othermachines.p7l7")) }));

		pages.add(new Page(new TextWrapperObject[] {
				//
				new TextWrapperObject(45, 53, 4210752, TextUtils.guidebook("chapter.othermachines.atomicasstitle").withStyle(ChatFormatting.UNDERLINE)),
				//
				new TextWrapperObject(10, 80, 4210752, TextUtils.guidebook("chapter.othermachines.p8l1")),
				//
				new TextWrapperObject(10, 90, 4210752, TextUtils.guidebook("chapter.othermachines.p8l2")),
				//
				new TextWrapperObject(10, 100, 4210752, TextUtils.guidebook("chapter.othermachines.p8l3")),
				//
				new TextWrapperObject(10, 110, 4210752, TextUtils.guidebook("chapter.othermachines.p8l4")),
				//
				new TextWrapperObject(10, 120, 4210752, TextUtils.guidebook("chapter.othermachines.p8l5")),
				//
				new TextWrapperObject(10, 130, 4210752, TextUtils.guidebook("chapter.othermachines.p8l6")),
				//
				new TextWrapperObject(10, 140, 4210752, TextUtils.guidebook("chapter.othermachines.p8l7")),
				//
				new TextWrapperObject(10, 150, 4210752, TextUtils.guidebook("chapter.othermachines.p8l8")),
				//
				new TextWrapperObject(10, 160, 4210752, TextUtils.guidebook("chapter.othermachines.p8l9")),
				//
				new TextWrapperObject(10, 170, 4210752, TextUtils.guidebook("chapter.othermachines.p8l10")),
				//
				new TextWrapperObject(10, 180, 4210752, TextUtils.guidebook("chapter.othermachines.p8l11")) },
				new ItemWrapperObject[] {
						//
						new ItemWrapperObject(17, 50, 2.0F, ItemUtils.fromBlock(NuclearScienceBlocks.blockAtomicAssembler)) }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 115, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/atomicassembler1.png") },
				new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, TextUtils.guidebook("chapter.othermachines.p9l1")),
						//
						new TextWrapperObject(10, 50, 4210752, TextUtils.guidebook("chapter.othermachines.p9l2")),
						//
						new TextWrapperObject(10, 60, 4210752, TextUtils.guidebook("chapter.othermachines.p9l3")),
						//
						new TextWrapperObject(10, 70, 4210752, TextUtils.guidebook("chapter.othermachines.p9l4")),
						//
						new TextWrapperObject(10, 80, 4210752, TextUtils.guidebook("chapter.othermachines.p9l5")),
						//
						new TextWrapperObject(10, 90, 4210752, TextUtils.guidebook("chapter.othermachines.p9l6")),
						//
						new TextWrapperObject(10, 100, 4210752, TextUtils.guidebook("chapter.othermachines.p9l7")), }));

		return pages;
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return TextUtils.guidebook("chapter.othermachines");
	}

}
