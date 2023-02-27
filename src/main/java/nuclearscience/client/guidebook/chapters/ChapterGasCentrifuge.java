package nuclearscience.client.guidebook.chapters;

import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.TextWrapperObject;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import nuclearscience.References;
import nuclearscience.prefab.utils.TextUtils;
import nuclearscience.registers.NuclearScienceBlocks;

public class ChapterGasCentrifuge extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 2.0F, 32, 32, NuclearScienceBlocks.blockGasCentrifuge.asItem());
	
	public ChapterGasCentrifuge(Module module) {
		super(module);
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return TextUtils.guidebook("chapter.centrifuge");
	}
	
	@Override
	public void addData() {
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.centrifuge.l1")).setIndentions(1));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.centrifuge.l2")).setSeparateStart().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/gascentrifuge1.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/gascentrifuge2.png")));
	}

}
