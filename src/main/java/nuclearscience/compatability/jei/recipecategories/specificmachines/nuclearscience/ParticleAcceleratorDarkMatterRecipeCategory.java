package nuclearscience.compatability.jei.recipecategories.specificmachines.nuclearscience;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.compatability.jei.recipecategories.ElectrodynamicsRecipeCategory;
import electrodynamics.compatability.jei.recipecategories.psuedorecipes.PsuedoO2ORecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
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
import nuclearscience.References;

public class ParticleAcceleratorDarkMatterRecipeCategory extends ElectrodynamicsRecipeCategory<PsuedoO2ORecipe> {

    public static final int OUTPUT_SLOT = 0;

    private static int[] GUI_BACKGROUND_COORDS = { 0, 0, 132, 132 };
    private static int[] OUTPUT_OFFSET = { 57, 57 };

    public static int SMELT_TIME = 50;
    private static int TEXT_Y_HEIGHT = 122;

    private static String MOD_ID = References.ID;
    private static String RECIPE_GROUP = "partical_accelerator_darkmatter";
    private static String GUI_TEXTURE_STRING = "textures/gui/jei/particle_accelerator_dark_matter_gui.png";

    public static ItemStack INPUT_MACHINE = new ItemStack(nuclearscience.DeferredRegisters.blockParticleInjector);

    private LoadingCache<Integer, ArrayList<IDrawableAnimated>> CACHED_ARROWS;

    public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

    public ParticleAcceleratorDarkMatterRecipeCategory(IGuiHelper guiHelper) {

	super(guiHelper, MOD_ID, RECIPE_GROUP, GUI_TEXTURE_STRING, INPUT_MACHINE, GUI_BACKGROUND_COORDS, PsuedoO2ORecipe.class, TEXT_Y_HEIGHT,
		SMELT_TIME);

	CACHED_ARROWS = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<Integer, ArrayList<IDrawableAnimated>>() {
	    @Override
	    public ArrayList<IDrawableAnimated> load(Integer cookTime) {

		IDrawableAnimated majorArrowBottom = guiHelper.drawableBuilder(getGuiTexture(), 179, 17, 37, 75).buildAnimated(cookTime,
			IDrawableAnimated.StartDirection.BOTTOM, false);

		IDrawableAnimated majorArrowTop = guiHelper.drawableBuilder(getGuiTexture(), 132, 0, 37, 75).buildAnimated(cookTime,
			IDrawableAnimated.StartDirection.TOP, false);

		IDrawableAnimated[] arrows = { majorArrowBottom, majorArrowTop };
		return new ArrayList<>(Arrays.asList(arrows));
	    }
	});

    }

    @Override
    public ResourceLocation getUid() {
	return UID;
    }

    @Override
    public void setIngredients(PsuedoO2ORecipe recipe, IIngredients ingredients) {
	NonNullList<Ingredient> inputs = NonNullList.create();
	inputs.addAll(getIngredients(recipe));

	ingredients.setInputIngredients(inputs);
	ingredients.setOutput(VanillaTypes.ITEM, recipe.OUTPUT);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, PsuedoO2ORecipe recipe, IIngredients ingredients) {

	IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

	guiItemStacks.init(OUTPUT_SLOT, false, OUTPUT_OFFSET[0], OUTPUT_OFFSET[1]);
	guiItemStacks.set(ingredients);

    }

    @Override
    public void draw(PsuedoO2ORecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
	ArrayList<IDrawableAnimated> arrow = getArrow(recipe);
	arrow.get(0).draw(matrixStack, 70, 38);
	arrow.get(1).draw(matrixStack, 23, 21);

	drawSmeltTime(recipe, matrixStack, getYHeight());
    }

    protected ArrayList<IDrawableAnimated> getArrow(PsuedoO2ORecipe recipe) {
	return CACHED_ARROWS.getUnchecked(getArrowSmeltTime());
    }

    protected void drawSmeltTime(PsuedoO2ORecipe recipe, MatrixStack matrixStack, int y) {
	int smeltTimeSeconds = getArrowSmeltTime() / 20;
	TranslationTextComponent timeString = new TranslationTextComponent("gui.jei.category." + getRecipeGroup() + ".info.power", smeltTimeSeconds);
	Minecraft minecraft = Minecraft.getInstance();
	FontRenderer fontRenderer = minecraft.fontRenderer;
	int stringWidth = fontRenderer.getStringPropertyWidth(timeString);
	fontRenderer.func_243248_b(matrixStack, timeString, getBackground().getWidth() - stringWidth, y, 0xFF808080);
    }

    public NonNullList<Ingredient> getIngredients(PsuedoO2ORecipe recipe) {
	Ingredient ingredient1 = recipe.INPUT;
	NonNullList<Ingredient> ingredients = NonNullList.create();
	ingredients.add(ingredient1);
	return ingredients;
    }

}
