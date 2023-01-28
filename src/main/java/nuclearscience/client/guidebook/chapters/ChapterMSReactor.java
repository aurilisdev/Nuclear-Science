package nuclearscience.client.guidebook.chapters;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.client.guidebook.utils.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.TextWrapperObject;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Page;
import electrodynamics.prefab.utilities.ItemUtils;
import net.minecraft.network.chat.MutableComponent;
import nuclearscience.References;
import nuclearscience.prefab.utils.TextUtils;
import nuclearscience.registers.NuclearScienceBlocks;

public class ChapterMSReactor extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(17, 60, 2.0F, ItemUtils.fromBlock(NuclearScienceBlocks.blockMsrReactorCore));

	@Override
	protected List<Page> genPages() {
		List<Page> pages = new ArrayList<>();
		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, TextUtils.guidebook("chapter.msreactor.p1l1")),
				//
				new TextWrapperObject(10, 50, 4210752, TextUtils.guidebook("chapter.msreactor.p1l2")),
				//
				new TextWrapperObject(10, 60, 4210752, TextUtils.guidebook("chapter.msreactor.p1l3")),
				//
				new TextWrapperObject(10, 70, 4210752, TextUtils.guidebook("chapter.msreactor.p1l4")),
				//
				new TextWrapperObject(10, 80, 4210752, TextUtils.guidebook("chapter.msreactor.p1l5")),
				//
				new TextWrapperObject(10, 90, 4210752, TextUtils.guidebook("chapter.msreactor.p1l6")),
				//
				new TextWrapperObject(10, 100, 4210752, TextUtils.guidebook("chapter.msreactor.p1l7")),
				//
				new TextWrapperObject(10, 110, 4210752, TextUtils.guidebook("chapter.msreactor.p1l8")),
				//
				new TextWrapperObject(10, 120, 4210752, TextUtils.guidebook("chapter.msreactor.p1l9")),
				//
				new TextWrapperObject(10, 130, 4210752, TextUtils.guidebook("chapter.msreactor.p1l10")),
				//
				new TextWrapperObject(10, 140, 4210752, TextUtils.guidebook("chapter.msreactor.p1l11")),
				//
				new TextWrapperObject(10, 150, 4210752, TextUtils.guidebook("chapter.msreactor.p1l12")),
				//
				new TextWrapperObject(10, 160, 4210752, TextUtils.guidebook("chapter.msreactor.p1l13")),
				//
				new TextWrapperObject(10, 170, 4210752, TextUtils.guidebook("chapter.msreactor.p1l14")),
				//
				new TextWrapperObject(10, 180, 4210752, TextUtils.guidebook("chapter.msreactor.p1l15")), }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/msreactor1.png"),
				//
				new ImageWrapperObject(12, 117, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/msreactor2.png") }));

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, TextUtils.guidebook("chapter.msreactor.p3l1")),
				//
				new TextWrapperObject(10, 50, 4210752, TextUtils.guidebook("chapter.msreactor.p3l2")),
				//
				new TextWrapperObject(10, 60, 4210752, TextUtils.guidebook("chapter.msreactor.p3l3")),
				//
				new TextWrapperObject(10, 70, 4210752, TextUtils.guidebook("chapter.msreactor.p3l4")),
				//
				new TextWrapperObject(10, 80, 4210752, TextUtils.guidebook("chapter.msreactor.p3l5")),
				//
				new TextWrapperObject(10, 90, 4210752, TextUtils.guidebook("chapter.msreactor.p3l6")),
				//
				new TextWrapperObject(10, 100, 4210752, TextUtils.guidebook("chapter.msreactor.p3l7")),
				//
				new TextWrapperObject(10, 110, 4210752, TextUtils.guidebook("chapter.msreactor.p3l8")),
				//
				new TextWrapperObject(10, 120, 4210752, TextUtils.guidebook("chapter.msreactor.p3l9")),
				//
				new TextWrapperObject(10, 130, 4210752, TextUtils.guidebook("chapter.msreactor.p3l10")),
				//
				new TextWrapperObject(10, 140, 4210752, TextUtils.guidebook("chapter.msreactor.p3l11")),
				//
				new TextWrapperObject(10, 150, 4210752, TextUtils.guidebook("chapter.msreactor.p3l12")),
				//
				new TextWrapperObject(10, 160, 4210752, TextUtils.guidebook("chapter.msreactor.p3l13")),
				//
				new TextWrapperObject(10, 170, 4210752, TextUtils.guidebook("chapter.msreactor.p3l14")),
				//
				new TextWrapperObject(10, 180, 4210752, TextUtils.guidebook("chapter.msreactor.p3l15")), }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/msreactor3.png"),
				//
				new ImageWrapperObject(12, 117, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/msreactor4.png") }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/msreactor5.png"), }));

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, TextUtils.guidebook("chapter.msreactor.p6l1")),
				//
				new TextWrapperObject(10, 50, 4210752, TextUtils.guidebook("chapter.msreactor.p6l2")),
				//
				new TextWrapperObject(10, 60, 4210752, TextUtils.guidebook("chapter.msreactor.p6l3")),
				//
				new TextWrapperObject(10, 70, 4210752, TextUtils.guidebook("chapter.msreactor.p6l4")),
				//
				new TextWrapperObject(10, 80, 4210752, TextUtils.guidebook("chapter.msreactor.p6l5")),
				//
				new TextWrapperObject(10, 90, 4210752, TextUtils.guidebook("chapter.msreactor.p6l6")),
				//
				new TextWrapperObject(10, 100, 4210752, TextUtils.guidebook("chapter.msreactor.p6l7")),
				//
				new TextWrapperObject(10, 110, 4210752, TextUtils.guidebook("chapter.msreactor.p6l8")),
				//
				new TextWrapperObject(10, 120, 4210752, TextUtils.guidebook("chapter.msreactor.p6l9")),
				//
				new TextWrapperObject(10, 130, 4210752, TextUtils.guidebook("chapter.msreactor.p6l10")),
				//
				new TextWrapperObject(10, 140, 4210752, TextUtils.guidebook("chapter.msreactor.p6l11")),
				//
				new TextWrapperObject(10, 150, 4210752, TextUtils.guidebook("chapter.msreactor.p6l12")),
				//
				new TextWrapperObject(10, 160, 4210752, TextUtils.guidebook("chapter.msreactor.p6l13")),
				//
				new TextWrapperObject(10, 170, 4210752, TextUtils.guidebook("chapter.msreactor.p6l14")) }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/msreactor6.png"),
				//
				new ImageWrapperObject(12, 117, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/msreactor7.png") }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/msreactor8.png"),
				//
				new ImageWrapperObject(12, 117, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/msreactor9.png") }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/msreactor10.png"),
				//
				new ImageWrapperObject(12, 117, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/msreactor11.png") }));

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, TextUtils.guidebook("chapter.msreactor.p10l1")),
				//
				new TextWrapperObject(10, 50, 4210752, TextUtils.guidebook("chapter.msreactor.p10l2")),
				//
				new TextWrapperObject(10, 60, 4210752, TextUtils.guidebook("chapter.msreactor.p10l3")),
				//
				new TextWrapperObject(10, 70, 4210752, TextUtils.guidebook("chapter.msreactor.p10l4")),
				//
				new TextWrapperObject(10, 80, 4210752, TextUtils.guidebook("chapter.msreactor.p10l5")),
				//
				new TextWrapperObject(10, 90, 4210752, TextUtils.guidebook("chapter.msreactor.p10l6")),
				//
				new TextWrapperObject(10, 100, 4210752, TextUtils.guidebook("chapter.msreactor.p10l7")),
				//
				new TextWrapperObject(10, 110, 4210752, TextUtils.guidebook("chapter.msreactor.p10l8")),
				//
				new TextWrapperObject(10, 120, 4210752, TextUtils.guidebook("chapter.msreactor.p10l9")),
				//
				new TextWrapperObject(10, 130, 4210752, TextUtils.guidebook("chapter.msreactor.p10l10")), }));

		pages.add(new Page(new ImageWrapperObject[] {
				//
				new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/msreactor12.png"), }));

		return pages;
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return TextUtils.guidebook("chapter.msreactor");
	}

}
