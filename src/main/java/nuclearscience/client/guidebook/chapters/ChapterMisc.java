package nuclearscience.client.guidebook.chapters;

import net.minecraft.network.chat.MutableComponent;
import nuclearscience.NuclearScience;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.client.guidebook.utils.components.Chapter;
import voltaic.client.guidebook.utils.components.Module;
import voltaic.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import voltaic.client.guidebook.utils.pagedata.text.TextWrapperObject;

public class ChapterMisc extends Chapter {

    private static final ImageWrapperObject LOGO = new ImageWrapperObject(0, 0, 0, 0, 32, 32, 32, 32,
	    NuclearScience.rl("textures/item/cellempty.png"));

    public ChapterMisc(Module module) {
	super(module);
    }

    @Override
    public ImageWrapperObject getLogo() {
	return LOGO;
    }

    @Override
    public MutableComponent getTitle() {
	return NuclearTextUtils.guidebook("chapter.misc");
    }

    @Override
    public void addData() {
	pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.misc.l1")));

    }

}
