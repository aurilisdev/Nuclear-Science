package nuclearscience.client.guidebook.chapters;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.item.ItemUtils;
import electrodynamics.client.guidebook.utils.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.TextWrapperObject;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Page;
import nuclearscience.References;
import nuclearscience.registers.NuclearScienceBlocks;

public class ChapterFusionReactor extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(17, 60, 2.0F, ItemUtils.fromBlock(NuclearScienceBlocks.blockFusionReactorCore));

	@Override
	protected List<Page> genPages() {
		List<Page> pages = new ArrayList<>();

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p1l1"),
				//
				new TextWrapperObject(10, 50, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p1l2"),
				//
				new TextWrapperObject(10, 60, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p1l3"),
				//
				new TextWrapperObject(10, 70, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p1l4"),
				//
				new TextWrapperObject(10, 80, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p1l5"),
				//
				new TextWrapperObject(10, 90, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p1l6"),
				//
				new TextWrapperObject(10, 100, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p1l7"),
				//
				new TextWrapperObject(10, 110, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p1l8"),
				//
				new TextWrapperObject(10, 120, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p1l9"),
				//
				new TextWrapperObject(10, 130, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p1l10"),
				//
				new TextWrapperObject(10, 140, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p1l11"),
				//
				new TextWrapperObject(10, 150, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p1l12"),
				//
				new TextWrapperObject(10, 160, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p1l13"),
				//
				new TextWrapperObject(10, 170, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p1l14"),
				//
				new TextWrapperObject(10, 180, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p1l15"), }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/fusionreactor1.png"),
				//
				new ImageWrapperObject(12, 117, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/fusionreactor2.png") }));

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p3l1"),
				//
				new TextWrapperObject(10, 50, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p3l2"),
				//
				new TextWrapperObject(10, 60, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p3l3") }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/fusionreactor3.png"),
				//
				new ImageWrapperObject(12, 117, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/fusionreactor4.png") }));

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p5l1"),
				//
				new TextWrapperObject(10, 50, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p5l2"),
				//
				new TextWrapperObject(10, 60, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p5l3"),
				//
				new TextWrapperObject(10, 70, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p5l4"),
				//
				new TextWrapperObject(10, 80, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p5l5"),
				//
				new TextWrapperObject(10, 90, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p5l6") }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/fusionreactor5.png"),
				//
				new ImageWrapperObject(12, 117, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/fusionreactor6.png") }));

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p7l1"),
				//
				new TextWrapperObject(10, 50, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p7l2"),
				//
				new TextWrapperObject(10, 60, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p7l3"),
				//
				new TextWrapperObject(10, 70, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p7l4"),
				//
				new TextWrapperObject(10, 80, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p7l5"),
				//
				new TextWrapperObject(10, 90, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p7l6"),
				//
				new TextWrapperObject(10, 100, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p7l7"),
				//
				new TextWrapperObject(10, 110, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p7l8"),
				//
				new TextWrapperObject(10, 120, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p7l9"),
				//
				new TextWrapperObject(10, 130, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p7l10"),
				//
				new TextWrapperObject(10, 140, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p7l11"),
				//
				new TextWrapperObject(10, 150, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p7l12"),
				//
				new TextWrapperObject(10, 160, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p7l13"), }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/fusionreactor7.png"),
				//
				new ImageWrapperObject(12, 117, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/fusionreactor8.png") }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/fusionreactor9.png"),
				//
				new ImageWrapperObject(12, 117, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/fusionreactor10.png") }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/fusionreactor11.png") }));

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p11l1"),
				//
				new TextWrapperObject(10, 50, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p11l2"),
				//
				new TextWrapperObject(10, 60, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p11l3"),
				//
				new TextWrapperObject(10, 70, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p11l4"),
				//
				new TextWrapperObject(10, 80, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p11l5"),
				//
				new TextWrapperObject(10, 90, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p11l6"),
				//
				new TextWrapperObject(10, 100, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p11l7"),
				//
				new TextWrapperObject(10, 110, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p11l8"),
				//
				new TextWrapperObject(10, 120, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p11l9"),
				//
				new TextWrapperObject(10, 130, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p11l10"),
				//
				new TextWrapperObject(10, 140, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p11l11"),
				//
				new TextWrapperObject(10, 150, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p11l12"),
				//
				new TextWrapperObject(10, 160, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p11l13"),
				//
				new TextWrapperObject(10, 170, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p11l14"),
				//
				new TextWrapperObject(10, 180, 4210752, "guidebook.nuclearscience.chapter.fusionreactor.p11l15"), }));

		return pages;
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public String getTitleKey() {
		return "guidebook.nuclearscience.chapter.fusionreactor";
	}

}
