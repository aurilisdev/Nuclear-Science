package nuclearscience.client.guidebook.chapters;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.client.guidebook.utils.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.TextWrapperObject;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Page;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import nuclearscience.References;
import nuclearscience.prefab.utils.TextUtils;

public class ChapterRadiation extends Chapter {

	private static final ImageWrapperObject LOGO = new ImageWrapperObject(10, 50, 0, 0, 32, 32, 32, 32, References.ID + ":textures/item/uranium235.png");

	@Override
	protected List<Page> genPages() {
		List<Page> pages = new ArrayList<>();
		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, TextUtils.guidebook("chapter.radiation.p1l1")),
				//
				new TextWrapperObject(10, 50, 4210752, TextUtils.guidebook("chapter.radiation.p1l2")),
				//
				new TextWrapperObject(10, 60, 4210752, TextUtils.guidebook("chapter.radiation.p1l3")),
				//
				new TextWrapperObject(10, 70, 4210752, TextUtils.guidebook("chapter.radiation.p1l4")),
				//
				new TextWrapperObject(10, 80, 4210752, TextUtils.guidebook("chapter.radiation.p1l5")),
				//
				new TextWrapperObject(10, 90, 4210752, TextUtils.guidebook("chapter.radiation.p1l6")),
				//
				new TextWrapperObject(10, 100, 4210752, TextUtils.guidebook("chapter.radiation.p1l7")),
				//
				new TextWrapperObject(10, 110, 4210752, TextUtils.guidebook("chapter.radiation.p1l8")),
				//
				new TextWrapperObject(10, 120, 4210752, TextUtils.guidebook("chapter.radiation.p1l9")),
				//
				new TextWrapperObject(10, 130, 4210752, TextUtils.guidebook("chapter.radiation.p1l10")),
				//
				new TextWrapperObject(10, 140, 4210752, TextUtils.guidebook("chapter.radiation.p1l11")),
				//
				new TextWrapperObject(10, 150, 4210752, TextUtils.guidebook("chapter.radiation.p1l12")),
				//
				new TextWrapperObject(10, 160, 4210752, TextUtils.guidebook("chapter.radiation.p1l13")),
				//
				new TextWrapperObject(10, 170, 4210752, TextUtils.guidebook("chapter.radiation.p1l14")),
				//
				new TextWrapperObject(10, 180, 4210752, TextUtils.guidebook("chapter.radiation.p1l15")), }));

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, TextUtils.guidebook("chapter.radiation.p2l1")),
				//
				new TextWrapperObject(10, 50, 4210752, TextUtils.guidebook("chapter.radiation.p2l2")),
				//
				new TextWrapperObject(10, 60, 4210752, TextUtils.guidebook("chapter.radiation.p2l3")),
				//
				new TextWrapperObject(10, 70, 4210752, TextUtils.guidebook("chapter.radiation.p2l4")),
				//
				new TextWrapperObject(10, 80, 4210752, TextUtils.guidebook("chapter.radiation.p2l5-1")),
				//
				new TextWrapperObject(69, 80, 4210752, TextUtils.guidebook("chapter.radiation.p2l5-2").withStyle(ChatFormatting.BOLD)),
				//
				new TextWrapperObject(10, 90, 4210752, TextUtils.guidebook("chapter.radiation.p2l6")),
				//
				new TextWrapperObject(10, 100, 4210752, TextUtils.guidebook("chapter.radiation.p2l7")),
				//
				new TextWrapperObject(10, 110, 4210752, TextUtils.guidebook("chapter.radiation.p2l8")),
				//
				new TextWrapperObject(10, 120, 4210752, TextUtils.guidebook("chapter.radiation.p2l9")),
				//
				new TextWrapperObject(10, 130, 4210752, TextUtils.guidebook("chapter.radiation.p2l10")),
				//
				new TextWrapperObject(10, 140, 4210752, TextUtils.guidebook("chapter.radiation.p2l11")),
				//
				new TextWrapperObject(10, 150, 4210752, TextUtils.guidebook("chapter.radiation.p2l12")),
				//
				new TextWrapperObject(10, 160, 4210752, TextUtils.guidebook("chapter.radiation.p2l13")),
				//
				new TextWrapperObject(10, 170, 4210752, TextUtils.guidebook("chapter.radiation.p2l14-1")),
				//
				new TextWrapperObject(62, 170, 4210752, TextUtils.guidebook("chapter.radiation.p2l14-2").withStyle(ChatFormatting.BOLD)),
				//
				new TextWrapperObject(113, 170, 4210752, TextUtils.guidebook("chapter.radiation.p2l14-3")),
				//
				new TextWrapperObject(10, 180, 4210752, TextUtils.guidebook("chapter.radiation.p2l15")), }));
		return pages;
	}

	@Override
	public ImageWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return TextUtils.guidebook("chapter.radiation");
	}

}
