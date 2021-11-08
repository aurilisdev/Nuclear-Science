package nuclearscience.compatability.jei.recipecategories.specificmachines.nuclearscience;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;

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
import net.minecraft.client.gui.Font;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
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

    private LoadingCache<Integer, ArrayList<IDrawableAnimated>> arrowCache;
    private LoadingCache<Integer, ArrayList<IDrawableStatic>> fluidBarCache;

    public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

    public GasCentrifugeRecipeCategory(IGuiHelper guiHelper) {

	super(guiHelper, MOD_ID, RECIPE_GROUP, GUI_TEXTURE, INPUT_MACHINE, GUI_BACKGROUND, PsuedoGasCentrifugeRecipe.class, SMELT_TIME,
		TEXT_Y_HEIGHT);

	arrowCache = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<Integer, ArrayList<IDrawableAnimated>>() {

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

	fluidBarCache = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<Integer, ArrayList<IDrawableStatic>>() {
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
    public void draw(PsuedoGasCentrifugeRecipe recipe, PoseStack matrixStack, double mouseX, double mouseY) {

	ArrayList<IDrawableStatic> fluidBars = getFluidBars();
	fluidBars.get(0).draw(matrixStack, 3, 5);

	ArrayList<IDrawableAnimated> arrows = getArrows();

	arrows.get(0).draw(matrixStack, 22, 7);
	arrows.get(1).draw(matrixStack, 70, 3);
	arrows.get(2).draw(matrixStack, 3, 5);
	arrows.get(3).draw(matrixStack, 51, 4);
	arrows.get(4).draw(matrixStack, 51, 35);

	drawSmeltTime(matrixStack, getYHeight());
    }

    protected ArrayList<IDrawableAnimated> getArrows() {
	return arrowCache.getUnchecked(getArrowSmeltTime());
    }

    protected ArrayList<IDrawableStatic> getFluidBars() {
	return fluidBarCache.getUnchecked(getArrowSmeltTime());
    }

    @SuppressWarnings("java:S2184")
    protected void drawSmeltTime(PoseStack matrixStack, int y) {

	int smeltTimeSeconds = getArrowSmeltTime() / 20;

	TranslatableComponent indivU235String = new TranslatableComponent("gui.jei.category." + getRecipeGroup() + ".info.indiv_u235",
		smeltTimeSeconds);

	TranslatableComponent indivU238String = new TranslatableComponent("gui.jei.category." + getRecipeGroup() + ".info.indiv_u238",
		smeltTimeSeconds);

	TranslatableComponent percentU235String = new TranslatableComponent("gui.jei.category." + getRecipeGroup() + ".info.percent_u235",
		smeltTimeSeconds);

	TranslatableComponent percentU238String = new TranslatableComponent("gui.jei.category." + getRecipeGroup() + ".info.percent_u238",
		smeltTimeSeconds);

	Minecraft minecraft = Minecraft.getInstance();
	Font fontRenderer = minecraft.font;

	int indivU235StringWidth = fontRenderer.width(indivU235String);
	int indivU238StringWidth = fontRenderer.width(indivU238String);
	int percentU238StringWidth = fontRenderer.width(percentU238String);
	int percentU235StringWidth = fontRenderer.width(percentU235String);

	fontRenderer.draw(matrixStack, indivU238String, getBackground().getWidth() - indivU238StringWidth - 27, y - 27 - 30, 0xFF616161);
	fontRenderer.draw(matrixStack, percentU238String, getBackground().getWidth() - percentU238StringWidth - 27, y - 37 - 30, 0xFF616161);
	fontRenderer.draw(matrixStack, indivU235String, getBackground().getWidth() - indivU235StringWidth - 27, y - 59 - 30, 0xFF616161);
	fontRenderer.draw(matrixStack, percentU235String, getBackground().getWidth() - percentU235StringWidth - 27, y - 49 - 30, 0xFF616161);

    }

    public NonNullList<FluidStack> getFluids(PsuedoGasCentrifugeRecipe recipe) {
	NonNullList<FluidStack> fluids = NonNullList.create();
	fluids.add(recipe.inputFluidStack);
	return fluids;
    }

    public List<ItemStack> getOutputs(PsuedoGasCentrifugeRecipe recipe) {
	List<ItemStack> outputs = new ArrayList<>();
	outputs.add(recipe.output1);
	outputs.add(recipe.output2);
	return outputs;
    }

}
