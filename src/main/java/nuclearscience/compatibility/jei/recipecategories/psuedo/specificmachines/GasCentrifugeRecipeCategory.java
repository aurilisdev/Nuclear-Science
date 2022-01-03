package nuclearscience.compatibility.jei.recipecategories.psuedo.specificmachines;

import java.util.ArrayList;
import java.util.List;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
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
	public static final int BIPRODUCT_ITEM_SLOT = 3;

	private static int[] GUI_BACKGROUND = { 0, 0, 132, 68 };

	private static int ANIMATION_TIME = 100;

	private static String MOD_ID = References.ID;
	private static String RECIPE_GROUP = "gascentrifuge";
	private static String GUI_TEXTURE = "textures/gui/jei/gascentrifuge.png";

	public static ItemStack INPUT_MACHINE = new ItemStack(DeferredRegisters.blockGasCentrifuge);

	public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	private IDrawable BACKGROUND;
	private IDrawable ICON;

	private LoadingCache<Integer, IDrawableAnimated> cachedArrows;
	private static StartDirection ARROW_START_DIRECTION = IDrawableAnimated.StartDirection.LEFT;

	public GasCentrifugeRecipeCategory(IGuiHelper guiHelper) {

		ICON = guiHelper.createDrawableIngredient(INPUT_MACHINE);
		BACKGROUND = guiHelper.createDrawable(new ResourceLocation(MOD_ID, GUI_TEXTURE), GUI_BACKGROUND[0], GUI_BACKGROUND[1], GUI_BACKGROUND[2],
				GUI_BACKGROUND[3]);

		cachedArrows = CacheBuilder.newBuilder().maximumSize(1).build(new CacheLoader<Integer, IDrawableAnimated>() {
			@Override
			public IDrawableAnimated load(Integer cookTime) {
				return guiHelper.drawableBuilder(new ResourceLocation(MOD_ID, GUI_TEXTURE), 0, 68, 47, 54).buildAnimated(cookTime,
						ARROW_START_DIRECTION, false);
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
		return new TranslatableComponent("jei.gui." + RECIPE_GROUP);
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

		guiItemStacks.init(OUTPUT_1_ITEM_SLOT, false, 113, 2);
		guiItemStacks.init(OUTPUT_2_ITEM_SLOT, false, 113, 22);
		guiItemStacks.init(BIPRODUCT_ITEM_SLOT, false, 113, 42);

		guiFluidStacks.init(INPUT_FLUID_STACK_SLOT, true, 3, 7, 12, 47, 5000, false, null);

		guiItemStacks.set(ingredients);
		guiFluidStacks.set(ingredients);
	}

	@Override
	public void draw(PsuedoGasCentrifugeRecipe recipe, PoseStack matrixStack, double mouseX, double mouseY) {

		IDrawableAnimated arrow = cachedArrows.getUnchecked(ANIMATION_TIME);
		arrow.draw(matrixStack, 64, 4);

		int animTimeSeconds = ANIMATION_TIME / 20;

		TranslatableComponent percentU235String = new TranslatableComponent("jei.gui." + RECIPE_GROUP + ".info.percent_u235", animTimeSeconds);
		TranslatableComponent percentU238String = new TranslatableComponent("jei.gui." + RECIPE_GROUP + ".info.percent_u238", animTimeSeconds);
		TranslatableComponent percentBiproductString = new TranslatableComponent("jei.gui." + RECIPE_GROUP + ".info.percent_biproduct",
				animTimeSeconds);

		Minecraft minecraft = Minecraft.getInstance();
		Font fontRenderer = minecraft.font;

		fontRenderer.draw(matrixStack, percentU235String, 36, 7, 0xFF616161);
		fontRenderer.draw(matrixStack, percentU238String, 36, 28, 0xFF616161);
		fontRenderer.draw(matrixStack, percentBiproductString, 36, 49, 0xFF616161);
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
		outputs.add(recipe.biproduct);
		return outputs;
	}

}
