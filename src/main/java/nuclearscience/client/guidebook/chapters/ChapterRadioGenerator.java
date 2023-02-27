package nuclearscience.client.guidebook.chapters;

import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.TextWrapperObject;
import net.minecraft.network.chat.MutableComponent;
import nuclearscience.prefab.utils.TextUtils;
import nuclearscience.registers.NuclearScienceBlocks;

public class ChapterRadioGenerator extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 2.0F, 32, 32, NuclearScienceBlocks.blockRadioisotopeGenerator.asItem());
	
	public ChapterRadioGenerator(Module module) {
		super(module);
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return TextUtils.guidebook("chapter.radiogen");
	}
	
	@Override
	public void addData() {
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.radiogen.l1")).setIndentions(1));
	}

}
