package nuclearscience.client.guidebook.chapters;

import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import nuclearscience.References;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceItems;

public class ChapterFissionReactor extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 32, 32, 32, 2.0F, NuclearScienceBlocks.blockFissionReactorCore.asItem());
	
	public ChapterFissionReactor(Module module) {
		super(module);
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return NuclearTextUtils.guidebook("chapter.fissionreactor");
	}

	@Override
	public void addData() {
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.fissionreactor.l1")).setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/fissionreactor1.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/fissionreactor2.png")));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.fissionreactor.l2")).setIndentions(1).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/fissionreactor3.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/fissionreactor4.png")));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.fissionreactor.l3")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearScienceItems.ITEM_FUELLEUO2.get().getDescription()).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.fissionreactor.maxtemp", 1075)).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.fissionreactor.cycles", new ItemStack(NuclearScienceItems.ITEM_FUELLEUO2.get()).getMaxDamage())).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearScienceItems.ITEM_FUELHEUO2.get().getDescription()).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.fissionreactor.maxtemp", 1417)).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.fissionreactor.cycles", new ItemStack(NuclearScienceItems.ITEM_FUELHEUO2.get()).getMaxDamage())).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearScienceItems.ITEM_FUELPLUTONIUM.get().getDescription()).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.fissionreactor.maxtemp", 1075)).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.fissionreactor.cycles", new ItemStack(NuclearScienceItems.ITEM_FUELPLUTONIUM.get()).getMaxDamage())).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.fissionreactor.l4")).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.fissionreactor.l5")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.fissionreactor.l6")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.fissionreactor.controlrod").withStyle(ChatFormatting.BOLD)));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.fissionreactor.l7")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/fissionreactor5.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/fissionreactor6.png")));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.fissionreactor.l8")).setIndentions(1).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, new ResourceLocation(References.ID, "textures/screen/guidebook/fissionreactor7.png")));
		
	}

}
