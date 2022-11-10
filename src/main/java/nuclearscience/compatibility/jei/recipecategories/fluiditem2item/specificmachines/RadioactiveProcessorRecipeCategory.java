package nuclearscience.compatibility.jei.recipecategories.fluiditem2item.specificmachines;

import electrodynamics.api.References;
import electrodynamics.compatibility.jei.recipecategories.fluiditem2item.FluidItem2ItemRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.arrows.animated.ArrowLeftAnimatedWrapper;
import electrodynamics.compatibility.jei.utils.gui.arrows.animated.ArrowRightAnimatedWrapper;
import electrodynamics.compatibility.jei.utils.gui.backgroud.BackgroundWrapper;
import electrodynamics.compatibility.jei.utils.gui.fluid.DefaultFluidGaugeWrapper;
import electrodynamics.compatibility.jei.utils.gui.item.BucketSlotWrapper;
import electrodynamics.compatibility.jei.utils.gui.item.DefaultItemSlotWrapper;
import electrodynamics.compatibility.jei.utils.label.PowerLabelWrapper;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import nuclearscience.common.recipe.categories.fluiditem2item.specificmachines.RadioactiveProcessorRecipe;
import nuclearscience.common.settings.Constants;
import nuclearscience.registers.NuclearScienceBlocks;

public class RadioactiveProcessorRecipeCategory extends FluidItem2ItemRecipeCategory<RadioactiveProcessorRecipe> {

	// JEI Window Parameters
	private static BackgroundWrapper BACK_WRAP = new BackgroundWrapper(132, 64);

	private static DefaultItemSlotWrapper INPUT_SLOT = new DefaultItemSlotWrapper(57, 16);
	private static BucketSlotWrapper INPUT_BUCKET_SLOT = new BucketSlotWrapper(57, 36);
	private static DefaultItemSlotWrapper OUTPUT_SLOT = new DefaultItemSlotWrapper(107, 16);

	private static ArrowRightAnimatedWrapper ANIM_RIGHT_ARROW_1 = new ArrowRightAnimatedWrapper(30, 17);
	private static ArrowRightAnimatedWrapper ANIM_RIGHT_ARROW_2 = new ArrowRightAnimatedWrapper(80, 17);
	private static ArrowLeftAnimatedWrapper ANIM_LEFT_ARROW = new ArrowLeftAnimatedWrapper(30, 37);

	private static DefaultFluidGaugeWrapper IN_GAUGE = new DefaultFluidGaugeWrapper(10, 5, 5000);

	private static PowerLabelWrapper POWER_LABEL = new PowerLabelWrapper(2, 55, Constants.RADIOACTIVEPROCESSOR_USAGE_PER_TICK, 480);

	private static int ANIM_TIME = 50;

	private static String MOD_ID = References.ID;
	private static String RECIPE_GROUP = "radioactiveprocessor";

	public static ItemStack INPUT_MACHINE = new ItemStack(NuclearScienceBlocks.blockRadioactiveProcessor);

	public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);
	public static final RecipeType<RadioactiveProcessorRecipe> RECIPE_TYPE = RecipeType.create(References.ID, RadioactiveProcessorRecipe.RECIPE_GROUP, RadioactiveProcessorRecipe.class);

	public RadioactiveProcessorRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, MOD_ID, RECIPE_GROUP, INPUT_MACHINE, BACK_WRAP, RadioactiveProcessorRecipe.class, ANIM_TIME);
		setInputSlots(guiHelper, INPUT_SLOT, INPUT_BUCKET_SLOT);
		setOutputSlots(guiHelper, OUTPUT_SLOT);
		setFluidInputs(guiHelper, IN_GAUGE);
		setAnimatedArrows(guiHelper, ANIM_LEFT_ARROW, ANIM_RIGHT_ARROW_1, ANIM_RIGHT_ARROW_2);
		setLabels(POWER_LABEL);
	}

	public ResourceLocation getUid() {
		return UID;
	}

	@Override
	public RecipeType<RadioactiveProcessorRecipe> getRecipeType() {
		return RECIPE_TYPE;
	}

}
