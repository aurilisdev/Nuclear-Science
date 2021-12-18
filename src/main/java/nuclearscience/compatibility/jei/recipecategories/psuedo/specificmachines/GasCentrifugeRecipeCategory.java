package nuclearscience.compatibility.jei.recipecategories.psuedo.specificmachines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import nuclearscience.DeferredRegisters;
import nuclearscience.References;
import nuclearscience.compatibility.jei.utils.psuedorecipes.PsuedoGasCentrifugeRecipe;

public class GasCentrifugeRecipeCategory implements IRecipeCategory<PsuedoGasCentrifugeRecipe> {

	public static final int INPUT_FLUID_STACK_SLOT = 0;
	public static final int OUTPUT_1_ITEM_SLOT = 1;
	public static final int OUTPUT_2_ITEM_SLOT = 2;

	private static int[] GUI_BACKGROUND = { 0, 0, 132, 61 };

	private static int ANIMATION_TIME = 100;
	private static int DESC_Y_HEIGHT = 70;

	private static String MOD_ID = References.ID;
	private static String RECIPE_GROUP = "gas_centrifuge";
	private static String GUI_TEXTURE = "textures/gui/jei/gas_centrifuge_gui.png";

	public static ItemStack INPUT_MACHINE = new ItemStack(DeferredRegisters.blockGasCentrifuge);

	private LoadingCache<Integer, ArrayList<IDrawableAnimated>> arrowCache;
	private LoadingCache<Integer, ArrayList<IDrawableStatic>> fluidBarCache;

	public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	private IDrawable BACKGROUND;
	private IDrawable ICON;

	public GasCentrifugeRecipeCategory(IGuiHelper guiHelper) {

		ICON = guiHelper.createDrawableIngredient(INPUT_MACHINE);
		BACKGROUND = guiHelper.createDrawable(new ResourceLocation(MOD_ID, GUI_TEXTURE), GUI_BACKGROUND[0], GUI_BACKGROUND[1], GUI_BACKGROUND[2],
				GUI_BACKGROUND[3]);

		ResourceLocation guiTexture = new ResourceLocation(MOD_ID, GUI_TEXTURE);

		arrowCache = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<Integer, ArrayList<IDrawableAnimated>>() {

			@Override
			public ArrayList<IDrawableAnimated> load(Integer cookTime) {

				IDrawableAnimated distillArrow = guiHelper.drawableBuilder(guiTexture, 0, 70, 27, 47).buildAnimated(cookTime,
						IDrawableAnimated.StartDirection.LEFT, false);

				IDrawableAnimated condArrow = guiHelper.drawableBuilder(guiTexture, 27, 70, 46, 55).buildAnimated(cookTime,
						IDrawableAnimated.StartDirection.LEFT, false);

				IDrawableAnimated uF6FluidBar = guiHelper.drawableBuilder(guiTexture, 180, 0, 16, 50).buildAnimated(cookTime,
						IDrawableAnimated.StartDirection.TOP, false);

				IDrawableAnimated u235FluidBar = guiHelper.drawableBuilder(guiTexture, 148, 0, 16, 22).buildAnimated(cookTime,
						IDrawableAnimated.StartDirection.BOTTOM, false);

				IDrawableAnimated u238FluidBar = guiHelper.drawableBuilder(guiTexture, 164, 0, 16, 22).buildAnimated(cookTime,
						IDrawableAnimated.StartDirection.BOTTOM, false);

				IDrawableAnimated[] arrows = { distillArrow, condArrow, uF6FluidBar, u235FluidBar, u238FluidBar };
				return new ArrayList<>(Arrays.asList(arrows));
			}
		});

		fluidBarCache = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<Integer, ArrayList<IDrawableStatic>>() {
			@Override
			public ArrayList<IDrawableStatic> load(Integer fluidHeight) {

				IDrawableStatic fluidBar = guiHelper.drawableBuilder(guiTexture, 132, 0, 16, 50).build();

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
	public Class<? extends PsuedoGasCentrifugeRecipe> getRecipeClass() {
		return PsuedoGasCentrifugeRecipe.class;
	}

	@Override
	public Component getTitle() {
		return new TranslatableComponent("gui.jei.category." + RECIPE_GROUP);
	}

	@Override
	public IDrawable getBackground() {
		return BACKGROUND;
	}

	@Override
	public IDrawable getIcon() {
		return ICON;
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

		ArrayList<IDrawableStatic> fluidBars = fluidBarCache.getUnchecked(ANIMATION_TIME);
		fluidBars.get(0).draw(matrixStack, 3, 5);

		ArrayList<IDrawableAnimated> arrows = arrowCache.getUnchecked(ANIMATION_TIME);

		arrows.get(0).draw(matrixStack, 22, 7);
		arrows.get(1).draw(matrixStack, 70, 3);
		arrows.get(2).draw(matrixStack, 3, 5);
		arrows.get(3).draw(matrixStack, 51, 4);
		arrows.get(4).draw(matrixStack, 51, 35);

		int animTimeSeconds = ANIMATION_TIME / 20;

		TranslatableComponent indivU235String = new TranslatableComponent("gui.jei.category." + RECIPE_GROUP + ".info.indiv_u235", animTimeSeconds);

		TranslatableComponent indivU238String = new TranslatableComponent("gui.jei.category." + RECIPE_GROUP + ".info.indiv_u238", animTimeSeconds);

		TranslatableComponent percentU235String = new TranslatableComponent("gui.jei.category." + RECIPE_GROUP + ".info.percent_u235",
				animTimeSeconds);

		TranslatableComponent percentU238String = new TranslatableComponent("gui.jei.category." + RECIPE_GROUP + ".info.percent_u238",
				animTimeSeconds);

		Minecraft minecraft = Minecraft.getInstance();
		Font fontRenderer = minecraft.font;

		int indivU235StringWidth = fontRenderer.width(indivU235String);
		int indivU238StringWidth = fontRenderer.width(indivU238String);
		int percentU238StringWidth = fontRenderer.width(percentU238String);
		int percentU235StringWidth = fontRenderer.width(percentU235String);

		fontRenderer.draw(matrixStack, indivU238String, getBackground().getWidth() - indivU238StringWidth - 27, DESC_Y_HEIGHT - 27, 0xFF616161);
		fontRenderer.draw(matrixStack, percentU238String, getBackground().getWidth() - percentU238StringWidth - 27, DESC_Y_HEIGHT - 37, 0xFF616161);
		fontRenderer.draw(matrixStack, indivU235String, getBackground().getWidth() - indivU235StringWidth - 27, DESC_Y_HEIGHT - 59, 0xFF616161);
		fontRenderer.draw(matrixStack, percentU235String, getBackground().getWidth() - percentU235StringWidth - 27, DESC_Y_HEIGHT - 49, 0xFF616161);
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
