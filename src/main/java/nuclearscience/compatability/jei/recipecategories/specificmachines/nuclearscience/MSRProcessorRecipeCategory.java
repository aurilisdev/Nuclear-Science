package nuclearscience.compatability.jei.recipecategories.specificmachines.nuclearscience;

import java.util.ArrayList;
import java.util.List;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.capability.CapabilityUtils;
import electrodynamics.common.recipe.categories.fluid3items2item.Fluid3Items2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.compatability.jei.recipecategories.ElectrodynamicsRecipeCategory;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import nuclearscience.References;

public class MSRProcessorRecipeCategory extends ElectrodynamicsRecipeCategory<Fluid3Items2ItemRecipe>{
	
	
	public static final int ITEM_INPUT_1 = 0;
	public static final int ITEM_INPUT_2 = 1;
	public static final int ITEM_INPUT_3 = 2;
	public static final int FLUID_BUCKET = 3;
	public static final int FLUID_INPUT = 4;
	public static final int OUTPUT_SLOT = 5;

    private static int[] GUI_BACKGROUND_COORDS = { 0, 0, 132, 64 };
    
    private static int[] INPUT_FLUID_TANK = { 11, 52, 12, 47, 5000 };

    public static int SMELT_TIME = 50;
    private static int TEXT_Y_HEIGHT = 55;

    private static String MOD_ID = References.ID;
    private static String RECIPE_GROUP = "msr_processor";
    private static String GUI_TEXTURE_STRING = "textures/gui/jei/msr_processing_recipe_gui.png";

    public static ItemStack INPUT_MACHINE = new ItemStack(nuclearscience.DeferredRegisters.blockMSRFuelPreProcessor);

    private LoadingCache<Integer, IDrawableAnimated> CACHED_ARROWS;

    public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	public MSRProcessorRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, MOD_ID, RECIPE_GROUP, GUI_TEXTURE_STRING, INPUT_MACHINE, GUI_BACKGROUND_COORDS, Fluid3Items2ItemRecipe.class, TEXT_Y_HEIGHT,
				SMELT_TIME);
		
		CACHED_ARROWS = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<Integer, IDrawableAnimated>() {
		    @Override
		    public IDrawableAnimated load(Integer cookTime) {
		    	return guiHelper.drawableBuilder(getGuiTexture(), 0, 65, 68, 22).buildAnimated(cookTime, StartDirection.LEFT, false);
		    }
		});
	}

	@Override
	public ResourceLocation getUid() {
		return UID;
	}

	@Override
	public void setIngredients(Fluid3Items2ItemRecipe recipe, IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, getItemIngredients(recipe));
		ingredients.setInputs(VanillaTypes.FLUID, getFluids(recipe));
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, Fluid3Items2ItemRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

		guiItemStacks.init(ITEM_INPUT_1, true, 58, 2);
		guiItemStacks.init(ITEM_INPUT_2, true, 58, 23);
		guiItemStacks.init(ITEM_INPUT_3, true, 58, 44);
		guiItemStacks.init(FLUID_BUCKET, true, 27, 36);

		guiItemStacks.init(OUTPUT_SLOT, false, 104, 23);

		int fluidInputAmount = ((FluidIngredient) recipe.getIngredients().get(3)).getFluidStack().getAmount();

		int leftHeightOffset = (int) Math.ceil(fluidInputAmount / (float) INPUT_FLUID_TANK[4] * INPUT_FLUID_TANK[3]);
		int leftStartY = INPUT_FLUID_TANK[1] - leftHeightOffset + 1;

		guiFluidStacks.init(FLUID_INPUT, true, INPUT_FLUID_TANK[0], leftStartY, INPUT_FLUID_TANK[2], leftHeightOffset, fluidInputAmount,
			true, null);

		guiItemStacks.set(ingredients);
		guiFluidStacks.set(ingredients);
	}
	
	@Override
	public void draw(Fluid3Items2ItemRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
		IDrawableAnimated arrow = getArrow(recipe);
		arrow.draw(matrixStack, 32, 17);

		drawSmeltTime(recipe, matrixStack, getYHeight());
	}
	
	protected IDrawableAnimated getArrow(Fluid3Items2ItemRecipe recipe) {
		return CACHED_ARROWS.getUnchecked(getArrowSmeltTime());
	}
	
	protected void drawSmeltTime(Fluid3Items2ItemRecipe recipe, MatrixStack matrixStack, int y) {
		int smeltTimeSeconds = getArrowSmeltTime() / 20;
		TranslationTextComponent timeString = new TranslationTextComponent("gui.jei.category." + getRecipeGroup() + ".info.power", smeltTimeSeconds);
		Minecraft minecraft = Minecraft.getInstance();
		FontRenderer fontRenderer = minecraft.fontRenderer;
		int stringWidth = fontRenderer.getStringPropertyWidth(timeString);
		fontRenderer.func_243248_b(matrixStack, timeString, getBackground().getWidth() - stringWidth, y, 0xFF808080);
	}
	
	public List<List<ItemStack>> getItemIngredients(Fluid3Items2ItemRecipe recipe) {
		List<List<ItemStack>> stacks = new ArrayList<>();
		NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
		stacks.add(((CountableIngredient)recipeIngredients.get(0)).fetchCountedStacks());
		stacks.add(((CountableIngredient)recipeIngredients.get(1)).fetchCountedStacks());
		stacks.add(((CountableIngredient)recipeIngredients.get(2)).fetchCountedStacks());
		FluidStack fluid = ((FluidIngredient) recipeIngredients.get(3)).getFluidStack();
		ItemStack filledBucket = new ItemStack(fluid.getFluid().getFilledBucket());
		CapabilityUtils.fill(filledBucket, fluid);
		List<ItemStack> bucketList = new ArrayList<>();
		bucketList.add(filledBucket);
		stacks.add(bucketList);
		return stacks;
	}
	
	private List<FluidStack> getFluids(Fluid3Items2ItemRecipe recipe) {
		List<FluidStack> list = new ArrayList<>();
		list.add(((FluidIngredient)recipe.getIngredients().get(3)).getFluidStack());
		return list;
	}

}
