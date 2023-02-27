package nuclearscience.client.guidebook.chapters;

import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.TextWrapperObject;
import net.minecraft.network.chat.MutableComponent;
import nuclearscience.prefab.utils.TextUtils;
import nuclearscience.registers.NuclearScienceBlocks;

public class ChapterTurbines extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 2.0F, 32, 32, NuclearScienceBlocks.blockTurbine.asItem());
	
	public ChapterTurbines(Module module) {
		super(module);
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return TextUtils.guidebook("chapter.turbines");
	}
	
	@Override
	public void addData() {
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.turbines.l1")).setIndentions(1));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.turbines.tempvoltage", 1090, 120)).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.turbines.tempvoltage", 1515, 240)).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.turbines.tempvoltage", ">1515", 480)).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.turbines.l2")).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.turbines.l3")).setIndentions(1).setSeparateStart());
		
	}

}
