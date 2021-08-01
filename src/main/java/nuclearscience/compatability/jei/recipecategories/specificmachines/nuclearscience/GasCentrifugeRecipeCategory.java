package nuclearscience.compatability.jei.recipecategories.specificmachines.nuclearscience;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.compatability.jei.recipecategories.ElectrodynamicsRecipeCategory;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import nuclearscience.DeferredRegisters;
import nuclearscience.References;
import nuclearscience.compatability.jei.recipecategories.psuedorecipes.PsuedoGasCentrifugeRecipe;

public class GasCentrifugeRecipeCategory extends ElectrodynamicsRecipeCategory<PsuedoGasCentrifugeRecipe> {

    public static final int INPUT_FLUID_STACK_SLOT = 0;
    public static final int OUTPUT_1_ITEM_SLOT = 1;
    public static final int OUTPUT_2_ITEM_SLOT = 2;

    private static int[] GUI_BACKGROUND = { 0, 0, 132, 61 };

    private static int SMELT_TIME = 100;
    private static int TEXT_Y_HEIGHT = 70;

    private static String MOD_ID = References.ID;
    private static String RECIPE_GROUP = "gas_centrifuge";
    private static String GUI_TEXTURE = "textures/gui/jei/gas_centrifuge_gui.png";

    public static ItemStack INPUT_MACHINE = new ItemStack(DeferredRegisters.blockGasCentrifuge);

    private LoadingCache<Integer, ArrayList<IDrawableAnimated>> CACHED_ARROWS;
    private LoadingCache<Integer, ArrayList<IDrawableStatic>> CACHED_FLUID_BARS;

    public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

    public GasCentrifugeRecipeCategory(IGuiHelper guiHelper) {

	super(guiHelper, MOD_ID, RECIPE_GROUP, GUI_TEXTURE, INPUT_MACHINE, GUI_BACKGROUND, PsuedoGasCentrifugeRecipe.class, SMELT_TIME,
		TEXT_Y_HEIGHT);

	CACHED_ARROWS = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<Integer, ArrayList<IDrawableAnimated>>() {

	    @Override
	    public ArrayList<IDrawableAnimated> load(Integer cookTime) {

		IDrawableAnimated distillArrow = guiHelper.drawableBuilder(getGuiTexture(), 0, 70, 27, 47).buildAnimated(cookTime,
			IDrawableAnimated.StartDirection.LEFT, false);

		IDrawableAnimated condArrow = guiHelper.drawableBuilder(getGuiTexture(), 27, 70, 46, 55).buildAnimated(cookTime,
			IDrawableAnimated.StartDirection.LEFT, false);

		IDrawableAnimated uF6FluidBar = guiHelper.drawableBuilder(getGuiTexture(), 180, 0, 16, 50).buildAnimated(cookTime,
			IDrawableAnimated.StartDirection.TOP, false);

		IDrawableAnimated u235FluidBar = guiHelper.drawableBuilder(getGuiTexture(), 148, 0, 16, 22).buildAnimated(cookTime,
			IDrawableAnimated.StartDirection.BOTTOM, false);

		IDrawableAnimated u238FluidBar = guiHelper.drawableBuilder(getGuiTexture(), 164, 0, 16, 22).buildAnimated(cookTime,
			IDrawableAnimated.StartDirection.BOTTOM, false);

		IDrawableAnimated[] arrows = { distillArrow, condArrow, uF6FluidBar, u235FluidBar, u238FluidBar };
		return new ArrayList<>(Arrays.asList(arrows));
	    }
	});

	CACHED_FLUID_BARS = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<Integer, ArrayList<IDrawableStatic>>() {
	    @Override
	    public ArrayList<IDrawableStatic> load(Integer fluidHeight) {

		IDrawableStatic fluidBar = guiHelper.drawableBuilder(getGuiTexture(), 132, 0, 16, 50).build();

		IDrawableStatic[] fluidBars = { fluidBar };

		return new ArrayList<>(Arrays.asList(fluidBars));
	    }
	});

    }

    @Override
    public ResourceLocation getUid() {
	return UID;
    }

    @Override
    public void setIngredients(PsuedoGasCentrifugeRecipe recipe, IIngredients ingredients) {

	ingredients.setInputs(VanillaTypes.FLUID, getFluids(recipe));

	ingredients.setOutputs(VanillaTypes.ITEM, getOutputs(recipe));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, PsuedoGasCentrifugeRecipe recipe, IIngredients ingredients) {

	IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
	IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

	guiItemStacks.init(OUTPUT_1_ITEM_SLOT, false, 107, 11);
	guiItemStacks.init(OUTPUT_2_ITEM_SLOT, false, 107, 32);

	guiFluidStacks.init(INPUT_FLUID_STACK_SLOT, true, 3, 5, 16, 50, 5000, false, null);

	guiItemStacks.set(ingredients);
	guiFluidStacks.set(ingredients);

    }

    @Override
    public void draw(PsuedoGasCentrifugeRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {

	ArrayList<IDrawableStatic> fluidBars = getFluidBars(recipe);
	fluidBars.get(0).draw(matrixStack, 3, 5);

	ArrayList<IDrawableAnimated> arrows = getArrows(recipe);

	arrows.get(0).draw(matrixStack, 22, 7);
	arrows.get(1).draw(matrixStack, 70, 3);
	arrows.get(2).draw(matrixStack, 3, 5);
	arrows.get(3).draw(matrixStack, 51, 4);
	arrows.get(4).draw(matrixStack, 51, 35);

	drawSmeltTime(recipe, matrixStack, getYHeight());
    }

    protected ArrayList<IDrawableAnimated> getArrows(PsuedoGasCentrifugeRecipe recipe) {
	return CACHED_ARROWS.getUnchecked(getArrowSmeltTime());
    }

    protected ArrayList<IDrawableStatic> getFluidBars(PsuedoGasCentrifugeRecipe recipe) {
	return CACHED_FLUID_BARS.getUnchecked(getArrowSmeltTime());
    }

    protected void drawSmeltTime(PsuedoGasCentrifugeRecipe recipe, MatrixStack matrixStack, int y) {

	int smeltTimeSeconds = getArrowSmeltTime() / 20;

	TranslationTextComponent indivU235String = new TranslationTextComponent("gui.jei.category." + getRecipeGroup() + ".info.indiv_u235",
		smeltTimeSeconds);

	TranslationTextComponent indivU238String = new TranslationTextComponent("gui.jei.category." + getRecipeGroup() + ".info.indiv_u238",
		smeltTimeSeconds);

	TranslationTextComponent percentU235String = new TranslationTextComponent("gui.jei.category." + getRecipeGroup() + ".info.percent_u235",
		smeltTimeSeconds);

	TranslationTextComponent percentU238String = new TranslationTextComponent("gui.jei.category." + getRecipeGroup() + ".info.percent_u238",
		smeltTimeSeconds);

	Minecraft minecraft = Minecraft.getInstance();
	FontRenderer fontRenderer = minecraft.fontRenderer;

	int indivU235StringWidth = fontRenderer.getStringPropertyWidth(indivU235String);
	int indivU238StringWidth = fontRenderer.getStringPropertyWidth(indivU238String);
	int percentU238StringWidth = fontRenderer.getStringPropertyWidth(percentU238String);
	int percentU235StringWidth = fontRenderer.getStringPropertyWidth(percentU235String);

	fontRenderer.func_243248_b(matrixStack, indivU238String, getBackground().getWidth() - indivU238StringWidth - 27, y - 27, 0xFF616161);
	fontRenderer.func_243248_b(matrixStack, percentU238String, getBackground().getWidth() - percentU238StringWidth - 27, y - 37, 0xFF616161);
	fontRenderer.func_243248_b(matrixStack, indivU235String, getBackground().getWidth() - indivU235StringWidth - 27, y - 59, 0xFF616161);
	fontRenderer.func_243248_b(matrixStack, percentU235String, getBackground().getWidth() - percentU235StringWidth - 27, y - 49, 0xFF616161);

    }

    public NonNullList<FluidStack> getFluids(PsuedoGasCentrifugeRecipe recipe) {
	NonNullList<FluidStack> fluids = NonNullList.create();
	fluids.add(recipe.INPUT_FLUID_STACK);
	return fluids;
    }

    public List<ItemStack> getOutputs(PsuedoGasCentrifugeRecipe recipe) {
	List<ItemStack> outputs = new ArrayList<>();
	outputs.add(recipe.OUTPUT_1_ITEM);
	outputs.add(recipe.OUTPUT_2_ITEM);
	return outputs;
    }

}
