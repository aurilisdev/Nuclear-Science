package nuclearscience.compatibility.jei.recipecategories.fluiditem2item.specificmachines;

import electrodynamics.api.References;
import electrodynamics.compatibility.jei.recipecategories.fluiditem2item.FluidItem2ItemRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.arrows.animated.ArrowRightAnimatedWrapper;
import electrodynamics.compatibility.jei.utils.gui.backgroud.BackgroundWrapper;
import electrodynamics.compatibility.jei.utils.gui.fluid.DefaultFluidGaugeWrapper;
import electrodynamics.compatibility.jei.utils.gui.item.BucketSlotWrapper;
import electrodynamics.compatibility.jei.utils.gui.item.DefaultItemSlotWrapper;
import electrodynamics.compatibility.jei.utils.label.PowerLabelWrapper;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.settings.Constants;

public class MSRProcessorRecipeCategory extends FluidItem2ItemRecipeCategory {

	// JEI Window Parameters
	private static BackgroundWrapper BACK_WRAP = new BackgroundWrapper(132, 64);

	private static DefaultItemSlotWrapper INPUT_SLOT_1 = new DefaultItemSlotWrapper(58, 2);
	private static DefaultItemSlotWrapper INPUT_SLOT_2 = new DefaultItemSlotWrapper(58, 23);
	private static DefaultItemSlotWrapper INPUT_SLOT_3 = new DefaultItemSlotWrapper(58, 44);
	private static DefaultItemSlotWrapper OUTPUT_SLOT = new DefaultItemSlotWrapper(107, 23);
	private static BucketSlotWrapper INPUT_BUCKET_SLOT = new BucketSlotWrapper(27, 36);

	private static ArrowRightAnimatedWrapper ANIM_RIGHT_ARROW_1 = new ArrowRightAnimatedWrapper(30, 17);
	private static ArrowRightAnimatedWrapper ANIM_RIGHT_ARROW_2 = new ArrowRightAnimatedWrapper(80, 24);

	private static DefaultFluidGaugeWrapper IN_GAUGE = new DefaultFluidGaugeWrapper(10, 5, 5000);

	private static PowerLabelWrapper POWER_LABEL = new PowerLabelWrapper(0, 55, Constants.MSRFUELPREPROCESSOR_USAGE_PER_TICK, 240);

	private static int ANIM_TIME = 50;

	private static String MOD_ID = References.ID;
	private static String RECIPE_GROUP = "msrfuelpreprocessor";

	public static ItemStack INPUT_MACHINE = new ItemStack(DeferredRegisters.blockMSRFuelPreProcessor);

	public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	public MSRProcessorRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, MOD_ID, RECIPE_GROUP, INPUT_MACHINE, BACK_WRAP, ANIM_TIME);
		setInputSlots(guiHelper, INPUT_SLOT_1, INPUT_SLOT_2, INPUT_SLOT_3, INPUT_BUCKET_SLOT);
		setOutputSlots(guiHelper, OUTPUT_SLOT);
		setFluidInputs(guiHelper, IN_GAUGE);
		setAnimatedArrows(guiHelper, ANIM_RIGHT_ARROW_1, ANIM_RIGHT_ARROW_2);
		setLabels(POWER_LABEL);
	}

	@Override
	public ResourceLocation getUid() {
		return UID;
	}

}
