package nuclearscience.compatibility.jei.recipecategories.psuedo.specificmachines;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.compatibility.jei.recipecategories.utils.AbstractRecipeCategory;
import electrodynamics.compatibility.jei.utils.RecipeType;
import electrodynamics.compatibility.jei.utils.gui.ScreenObject;
import electrodynamics.compatibility.jei.utils.gui.types.ArrowAnimatedObject;
import electrodynamics.compatibility.jei.utils.gui.types.BackgroundObject;
import electrodynamics.compatibility.jei.utils.gui.types.ItemSlotObject;
import electrodynamics.compatibility.jei.utils.gui.types.fluidgauge.FluidGaugeObject;
import electrodynamics.compatibility.jei.utils.label.types.LabelWrapperGeneric;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.SlotType;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import nuclearscience.References;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tile.TileGasCentrifuge;
import nuclearscience.compatibility.jei.utils.NuclearJeiTextures;
import nuclearscience.compatibility.jei.utils.psuedorecipes.PsuedoGasCentrifugeRecipe;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceBlocks;

public class GasCentrifugeRecipeCategory extends AbstractRecipeCategory<PsuedoGasCentrifugeRecipe> {

	public static final BackgroundObject BACK_WRAP = new BackgroundObject(132, 68);

	public static final ScreenObject GASCENTRIFUGE_ARROW_STATIC = new ScreenObject(NuclearJeiTextures.GASCENTRIFUGE_ARROW_STATIC, 19, 5);

	public static final ItemSlotObject OUTPUT_SLOT_1 = new ItemSlotObject(SlotType.NORMAL, 113, 2, false);
	public static final ItemSlotObject OUTPUT_SLOT_2 = new ItemSlotObject(SlotType.NORMAL, 113, 22, false);
	public static final ItemSlotObject BIPRODUCT_SLOT = new ItemSlotObject(SlotType.NORMAL, 113, 42, false);

	public static final ArrowAnimatedObject ANIM_RIGHT_ARROW = new ArrowAnimatedObject(NuclearJeiTextures.GASCENTRIFUGE_ARROW_OFF, NuclearJeiTextures.GASCENTRIFUGE_ARROW_ON, 64, 4, StartDirection.LEFT);

	public static final FluidGaugeObject IN_GAUGE = new FluidGaugeObject(2, 6, 5000);

	public static final LabelWrapperGeneric U235 = new LabelWrapperGeneric(0xFF616161, 7, 36, false, ChatFormatter.getChatDisplayShort(TileGasCentrifuge.PERCENT_U235 * 100, DisplayUnit.PERCENTAGE));
	public static final LabelWrapperGeneric U238 = new LabelWrapperGeneric(0xFF616161, 28, 36, false, ChatFormatter.getChatDisplayShort((1 - TileGasCentrifuge.PERCENT_U235) * 100, DisplayUnit.PERCENTAGE));
	public static final LabelWrapperGeneric BIPROD = new LabelWrapperGeneric(0xFF616161, 49, 36, false, ChatFormatter.getChatDisplayShort(TileGasCentrifuge.WASTE_MULTIPLIER * 100, DisplayUnit.PERCENTAGE));

	public static final LabelWrapperGeneric POWER_LABEL = new LabelWrapperGeneric(0xFF808080, 58, 2, false, ChatFormatter.getChatDisplayShort(960, DisplayUnit.VOLTAGE).append(" ").append(ChatFormatter.getChatDisplayShort(Constants.PARTICLEINJECTOR_USAGE_PER_PARTICLE, DisplayUnit.JOULES)));

	public static final int ANIM_TIME = 100;

	public static ItemStack INPUT_MACHINE = new ItemStack(NuclearScienceBlocks.blockGasCentrifuge);

	public static final String RECIPE_GROUP = "gascentrifuge";

	public static final RecipeType<PsuedoGasCentrifugeRecipe> RECIPE_TYPE = RecipeType.create(References.ID, RECIPE_GROUP, PsuedoGasCentrifugeRecipe.class);

	public GasCentrifugeRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, NuclearTextUtils.jeiTranslated(RECIPE_GROUP), INPUT_MACHINE, BACK_WRAP, RECIPE_TYPE, ANIM_TIME);

		setOutputSlots(guiHelper, OUTPUT_SLOT_1, OUTPUT_SLOT_2, BIPRODUCT_SLOT);
		setFluidInputs(guiHelper, IN_GAUGE);
		setAnimatedArrows(guiHelper, ANIM_RIGHT_ARROW);
		setScreenObjects(guiHelper, GASCENTRIFUGE_ARROW_STATIC);
		setLabels(U235, U238, BIPROD, POWER_LABEL);

	}

	@Override
	public List<ItemStack> getItemOutputs(PsuedoGasCentrifugeRecipe recipe) {
		List<ItemStack> outputs = new ArrayList<>();
		outputs.add(recipe.output1);
		outputs.add(recipe.output2);
		outputs.add(recipe.biproduct);
		return outputs;
	}

	@Override
	public List<List<FluidStack>> getFluidInputs(PsuedoGasCentrifugeRecipe recipe) {
		List<List<FluidStack>> inputs = new ArrayList<>();
		inputs.add(recipe.inputFluidStack.getMatchingFluids());
		return inputs;
	}

}
