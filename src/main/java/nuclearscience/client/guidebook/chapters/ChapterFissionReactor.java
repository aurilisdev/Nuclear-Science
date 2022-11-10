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

public class ChapterFissionReactor extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(17, 60, 2.0F, ItemUtils.fromBlock(NuclearScienceBlocks.blockReactorCore));

	@Override
	protected List<Page> genPages() {
		List<Page> pages = new ArrayList<>();
		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p1l1"),
				//
				new TextWrapperObject(10, 50, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p1l2"),
				//
				new TextWrapperObject(10, 60, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p1l3"),
				//
				new TextWrapperObject(10, 70, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p1l4"),
				//
				new TextWrapperObject(10, 80, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p1l5"),
				//
				new TextWrapperObject(10, 90, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p1l6"),
				//
				new TextWrapperObject(10, 100, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p1l7"),
				//
				new TextWrapperObject(10, 110, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p1l8"),
				//
				new TextWrapperObject(10, 120, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p1l9"),
				//
				new TextWrapperObject(10, 130, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p1l10"),
				//
				new TextWrapperObject(10, 140, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p1l11"),
				//
				new TextWrapperObject(10, 150, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p1l12") }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/fissionreactor1.png"),
				//
				new ImageWrapperObject(12, 117, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/fissionreactor2.png") }));

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p3l1"),
				//
				new TextWrapperObject(10, 50, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p3l2"),
				//
				new TextWrapperObject(10, 60, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p3l3"),
				//
				new TextWrapperObject(10, 70, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p3l4"),
				//
				new TextWrapperObject(10, 80, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p3l5"),
				//
				new TextWrapperObject(10, 90, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p3l6"),
				//
				new TextWrapperObject(10, 100, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p3l7"),
				//
				new TextWrapperObject(10, 110, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p3l8"),
				//
				new TextWrapperObject(10, 120, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p3l9") }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/fissionreactor3.png"),
				//
				new ImageWrapperObject(12, 117, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/fissionreactor4.png") }));

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p5l1"),
				//
				new TextWrapperObject(10, 50, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p5l2"),
				//
				new TextWrapperObject(10, 60, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p5l3"),
				//
				new TextWrapperObject(10, 70, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p5l4"),
				//
				new TextWrapperObject(10, 80, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p5l5"),
				//
				new TextWrapperObject(10, 90, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p5l6"),
				//
				new TextWrapperObject(10, 100, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p5l7"),
				//
				new TextWrapperObject(10, 110, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p5l8"),
				//
				new TextWrapperObject(10, 120, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p5l9"),
				//
				new TextWrapperObject(10, 130, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p5l10"),
				//
				new TextWrapperObject(10, 140, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p5l11"),
				//
				new TextWrapperObject(10, 150, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p5l12"),
				//
				new TextWrapperObject(10, 160, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p5l13"),
				//
				new TextWrapperObject(10, 170, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p5l14"),
				//
				new TextWrapperObject(10, 180, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p5l15"), }));

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p6l1"),
				//
				new TextWrapperObject(10, 50, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p6l2"),
				//
				new TextWrapperObject(10, 60, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p6l3"),
				//
				new TextWrapperObject(10, 70, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p6l4"),
				//
				new TextWrapperObject(10, 80, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p6l5"),
				//
				new TextWrapperObject(10, 90, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p6l6"),
				//
				new TextWrapperObject(10, 100, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p6l7"),
				//
				new TextWrapperObject(10, 110, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p6l8"),
				//
				new TextWrapperObject(10, 120, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p6l9"),
				//
				new TextWrapperObject(10, 130, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p6l10"),
				//
				new TextWrapperObject(10, 140, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p6l11"),
				//
				new TextWrapperObject(10, 150, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p6l12"),
				//
				new TextWrapperObject(10, 160, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p6l13"),
				//
				new TextWrapperObject(10, 170, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p6l14"),
				//
				new TextWrapperObject(10, 180, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p6l15"), }));

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p7l1"),
				//
				new TextWrapperObject(10, 50, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p7l2"),
				//
				new TextWrapperObject(10, 60, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p7l3"),
				//
				new TextWrapperObject(10, 70, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p7l4"),
				//
				new TextWrapperObject(10, 80, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p7l5"),
				//
				new TextWrapperObject(10, 90, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p7l6"),
				//
				new TextWrapperObject(10, 100, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p7l7"),
				//
				new TextWrapperObject(10, 110, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p7l8"),
				//
				new TextWrapperObject(10, 120, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p7l9"),
				//
				new TextWrapperObject(10, 130, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p7l10"),
				//
				new TextWrapperObject(10, 140, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p7l11"),
				//
				new TextWrapperObject(10, 150, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p7l12"),
				//
				new TextWrapperObject(10, 160, 4210752, "guidebook.nuclearscience.chapter.fissionreactor.p7l13") }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/fissionreactor5.png"),
				//
				new ImageWrapperObject(12, 117, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/fissionreactor6.png") }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/fissionreactor7.png") }));

		return pages;
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public String getTitleKey() {
		return "guidebook.nuclearscience.chapter.fissionreactor";
	}

}
