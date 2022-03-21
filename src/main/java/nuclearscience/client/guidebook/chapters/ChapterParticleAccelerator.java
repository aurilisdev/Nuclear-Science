package nuclearscience.client.guidebook.chapters;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.item.ItemUtils;
import electrodynamics.client.guidebook.utils.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.TextWrapperObject;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Page;
import nuclearscience.DeferredRegisters;
import nuclearscience.References;

public class ChapterParticleAccelerator extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(17, 60, 2.0F, ItemUtils.fromBlock(DeferredRegisters.blockParticleInjector));

	@Override
	protected List<Page> genPages() {
		List<Page> pages = new ArrayList<>();

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p1l1"),
				//
				new TextWrapperObject(10, 50, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p1l2"),
				//
				new TextWrapperObject(10, 60, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p1l3"),
				//
				new TextWrapperObject(10, 70, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p1l4"),
				//
				new TextWrapperObject(10, 80, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p1l5"),
				//
				new TextWrapperObject(10, 90, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p1l6"),
				//
				new TextWrapperObject(10, 100, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p1l7"),
				//
				new TextWrapperObject(10, 110, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p1l8"),
				//
				new TextWrapperObject(10, 120, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p1l9"),
				//
				new TextWrapperObject(10, 130, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p1l10"),
				//
				new TextWrapperObject(10, 140, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p1l11"),
				//
				new TextWrapperObject(10, 150, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p1l12"),
				//
				new TextWrapperObject(10, 160, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p1l13"),
				//
				new TextWrapperObject(10, 170, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p1l14"),
				//
				new TextWrapperObject(10, 180, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p1l15"), }));

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p2l1"),
				//
				new TextWrapperObject(10, 50, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p2l2"),
				//
				new TextWrapperObject(10, 60, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p2l3"),
				//
				new TextWrapperObject(10, 70, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p2l4"),
				//
				new TextWrapperObject(10, 80, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p2l5"),
				//
				new TextWrapperObject(10, 90, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p2l6"),
				//
				new TextWrapperObject(10, 100, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p2l7"),
				//
				new TextWrapperObject(10, 110, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p2l8"),
				//
				new TextWrapperObject(10, 120, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p2l9"),
				//
				new TextWrapperObject(10, 130, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p2l10"),
				//
				new TextWrapperObject(10, 140, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p2l11"),
				//
				new TextWrapperObject(10, 150, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p2l12"),
				//
				new TextWrapperObject(10, 160, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p2l13") }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/particleaccelerator1.png"),
				//
				new ImageWrapperObject(12, 117, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/particleaccelerator2.png") }));

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p4l1"),
				//
				new TextWrapperObject(10, 50, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p4l2"),
				//
				new TextWrapperObject(10, 60, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p4l3"),
				//
				new TextWrapperObject(10, 70, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p4l4"),
				//
				new TextWrapperObject(10, 80, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p4l5"),
				//
				new TextWrapperObject(10, 90, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p4l6"),
				//
				new TextWrapperObject(10, 100, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p4l7"),
				//
				new TextWrapperObject(10, 110, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p4l8"),
				//
				new TextWrapperObject(10, 120, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p4l9"),
				//
				new TextWrapperObject(10, 130, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p4l10"),
				//
				new TextWrapperObject(10, 140, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p4l11"),
				//
				new TextWrapperObject(10, 150, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p4l12"),
				//
				new TextWrapperObject(10, 160, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p4l13"),
				//
				new TextWrapperObject(10, 170, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p4l14"),
				//
				new TextWrapperObject(10, 180, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p4l15"), }));

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p5l1"),
				//
				new TextWrapperObject(10, 50, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p5l2"),
				//
				new TextWrapperObject(10, 60, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p5l3"),
				//
				new TextWrapperObject(10, 70, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p5l4"),
				//
				new TextWrapperObject(10, 80, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p5l5"),
				//
				new TextWrapperObject(10, 90, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p5l6"),
				//
				new TextWrapperObject(10, 100, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p5l7"),
				//
				new TextWrapperObject(10, 110, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p5l8"),
				//
				new TextWrapperObject(10, 120, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p5l9"),
				//
				new TextWrapperObject(10, 130, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p5l10"),
				//
				new TextWrapperObject(10, 140, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p5l11"),
				//
				new TextWrapperObject(10, 150, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p5l12"),
				//
				new TextWrapperObject(10, 160, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p5l13"),
				//
				new TextWrapperObject(10, 170, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p5l14"),
				//
				new TextWrapperObject(10, 180, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p5l15"), }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/particleaccelerator3.png"),
				//
				new ImageWrapperObject(12, 117, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/particleaccelerator4.png") }));

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p7l1"),
				//
				new TextWrapperObject(10, 50, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p7l2"),
				//
				new TextWrapperObject(10, 60, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p7l3"),
				//
				new TextWrapperObject(10, 70, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p7l4") }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/particleaccelerator5.png"),
				//
				new ImageWrapperObject(12, 117, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/particleaccelerator6.png") }));

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p9l1"),
				//
				new TextWrapperObject(10, 50, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p9l2"),
				//
				new TextWrapperObject(10, 60, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p9l3"),
				//
				new TextWrapperObject(10, 70, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p9l4"),
				//
				new TextWrapperObject(10, 80, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p9l5"),
				//
				new TextWrapperObject(10, 90, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p9l6"),
				//
				new TextWrapperObject(10, 100, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p9l7"),
				//
				new TextWrapperObject(10, 110, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p9l8"),
				//
				new TextWrapperObject(10, 120, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p9l9"),
				//
				new TextWrapperObject(10, 130, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p9l10"),
				//
				new TextWrapperObject(10, 140, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p9l11"),
				//
				new TextWrapperObject(10, 150, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p9l12") }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/particleaccelerator7.png"),
				//
				new ImageWrapperObject(12, 117, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/particleaccelerator8.png") }));

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p11l1"),
				//
				new TextWrapperObject(10, 50, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p11l2"),
				//
				new TextWrapperObject(10, 60, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p11l3"),
				//
				new TextWrapperObject(10, 70, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p11l4"),
				//
				new TextWrapperObject(10, 80, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p11l5"),
				//
				new TextWrapperObject(10, 90, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p11l6"),
				//
				new TextWrapperObject(10, 100, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p11l7"),
				//
				new TextWrapperObject(10, 110, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p11l8"),
				//
				new TextWrapperObject(10, 120, 4210752, "guidebook.nuclearscience.chapter.particleaccelerator.p11l9") }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/particleaccelerator9.png"),
				//
				new ImageWrapperObject(12, 117, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/particleaccelerator10.png") }));

		return pages;
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public String getTitleKey() {
		return "guidebook.nuclearscience.chapter.particleaccelerator";
	}

}
