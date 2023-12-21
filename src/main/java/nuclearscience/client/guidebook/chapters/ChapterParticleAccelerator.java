package nuclearscience.client.guidebook.chapters;

import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import nuclearscience.References;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceBlocks;

public class ChapterParticleAccelerator extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 32, 32, 32, 2.0F, NuclearScienceBlocks.blockParticleInjector.asItem());

	public ChapterParticleAccelerator(Module module) {
		super(module);
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return NuclearTextUtils.guidebook("chapter.particleaccelerator");
	}

	@Override
	public void addData() {
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l1")).setIndentions(1));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.formula")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l2")).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l3")).setIndentions(1).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/particleaccelerator1.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/particleaccelerator2.png")));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l4")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l5")).setIndentions(1).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/particleaccelerator3.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/particleaccelerator4.png")));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l6")).setIndentions(1).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/particleaccelerator5.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/particleaccelerator6.png")));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l7")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l8")).setIndentions(1).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/particleaccelerator7.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/particleaccelerator8.png")));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l9")).setIndentions(1).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/particleaccelerator9.png")));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l10")).setIndentions(1).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/particleaccelerator10.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/particleaccelerator11.png")));

	}

}
