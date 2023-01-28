package nuclearscience.client.guidebook.chapters;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.client.guidebook.utils.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.TextWrapperObject;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Page;
import net.minecraft.network.chat.MutableComponent;
import nuclearscience.References;
import nuclearscience.prefab.utils.TextUtils;

public class ChapterMisc extends Chapter {

	private static final ImageWrapperObject LOGO = new ImageWrapperObject(10, 50, 0, 0, 32, 32, 32, 32, References.ID + ":textures/item/cellempty.png");

	@Override
	protected List<Page> genPages() {
		List<Page> pages = new ArrayList<>();

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, TextUtils.guidebook("chapter.misc.p1l1")), }));

		return pages;
	}

	@Override
	public ImageWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return TextUtils.guidebook("chapter.misc");
	}

}
