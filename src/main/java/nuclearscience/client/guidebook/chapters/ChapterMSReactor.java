package nuclearscience.client.guidebook.chapters;

import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.TextWrapperObject;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import nuclearscience.References;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.prefab.utils.TextUtils;
import nuclearscience.registers.NuclearScienceBlocks;

public class ChapterMSReactor extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 2.0F, 32, 32, NuclearScienceBlocks.blockMsrReactorCore.asItem());

	public ChapterMSReactor(Module module) {
		super(module);
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return TextUtils.guidebook("chapter.msreactor");
	}

	@Override
	public void addData() {
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.msreactor.l1")).setIndentions(1));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.msreactor.l2")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearScienceBlocks.blockMsrReactorCore.asItem().getDescription()).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearScienceBlocks.blockFreezePlug.asItem().getDescription()).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearScienceBlocks.blockMoltenSaltSupplier.asItem().getDescription()).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.msreactor.l3")).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/msreactor1.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/msreactor2.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.msreactor.l4")).setIndentions(1).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/msreactor4.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.msreactor.l5")).setIndentions(1).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/msreactor5.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.msreactor.l6")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearScienceBlocks.getBlock(SubtypeMoltenSaltPipe.vanadiumsteelceramic).asItem().getDescription()).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearScienceBlocks.blockHeatExchanger.asItem().getDescription()).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.msreactor.l7")).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/msreactor6.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/msreactor7.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/msreactor8.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/msreactor9.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/msreactor10.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/msreactor11.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.msreactor.l8")).setIndentions(1).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/msreactor12.png")));

	}

}
