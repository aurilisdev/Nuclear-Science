package nuclearscience.client.guidebook.chapters;

import net.minecraft.util.text.IFormattableTextComponent;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceItems;
import voltaic.client.guidebook.utils.components.Chapter;
import voltaic.client.guidebook.utils.components.Module;
import voltaic.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import voltaic.client.guidebook.utils.pagedata.text.TextWrapperObject;

public class ChapterTurbines extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 32, 32, 32, 2.0F, NuclearScienceItems.ITEM_TURBINE.get());

	public ChapterTurbines(Module module) {
		super(module);
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public IFormattableTextComponent getTitle() {
		return NuclearTextUtils.guidebook("chapter.turbines");
	}

	@Override
	public void addData() {
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.turbines.l1")).setIndentions(1));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.turbines.tempvoltage", 1090, 120)).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.turbines.tempvoltage", 1515, 240)).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.turbines.tempvoltage", ">1515", 480)).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.turbines.l2")).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.turbines.l3")).setIndentions(1).setSeparateStart());

	}

}
