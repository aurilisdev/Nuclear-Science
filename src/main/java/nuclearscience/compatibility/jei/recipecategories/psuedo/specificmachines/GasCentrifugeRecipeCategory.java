package nuclearscience.compatibility.jei.recipecategories.psuedo.specificmachines;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
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

	public static final RecipeType<PsuedoGasCentrifugeRecipe> RECIPE_TYPE = RecipeType.create(References.ID, "gas_centrifuge", PsuedoGasCentrifugeRecipe.class);

	public GasCentrifugeRecipeCategory(IGuiHelper guiHelper) {

		ICON = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, INPUT_MACHINE);
		BACKGROUND = guiHelper.createDrawable(new ResourceLocation(MOD_ID, GUI_TEXTURE), GUI_BACKGROUND[0], GUI_BACKGROUND[1], GUI_BACKGROUND[2], GUI_BACKGROUND[3]);

		cachedArrows = CacheBuilder.newBuilder().maximumSize(1).build(new CacheLoader<Integer, IDrawableAnimated>() {
			@Override
			public IDrawableAnimated load(Integer cookTime) {
				return guiHelper.drawableBuilder(new ResourceLocation(MOD_ID, GUI_TEXTURE), 0, 68, 47, 54).buildAnimated(cookTime, ARROW_START_DIRECTION, false);
			}
		});
	}

	public ResourceLocation getUid() {
		return UID;
	}

	public Class<? extends PsuedoGasCentrifugeRecipe> getRecipeClass() {
		return PsuedoGasCentrifugeRecipe.class;
	}

	@Override
	public Component getTitle() {
		return Component.translatable("jei.gui." + RECIPE_GROUP);
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
	public RecipeType<PsuedoGasCentrifugeRecipe> getRecipeType() {
		return RECIPE_TYPE;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, PsuedoGasCentrifugeRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 3, 7).addIngredient(ForgeTypes.FLUID_STACK, recipe.inputFluidStack).setFluidRenderer(5000, false, 12, 47);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 114, 3).addItemStack(recipe.output1);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 114, 23).addItemStack(recipe.output2);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 114, 43).addItemStack(recipe.biproduct);
	}

	@Override
	public void draw(PsuedoGasCentrifugeRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {

		IDrawableAnimated arrow = cachedArrows.getUnchecked(ANIMATION_TIME);
		arrow.draw(matrixStack, 64, 4);

		int animTimeSeconds = ANIMATION_TIME / 20;

		Component percentU235String = Component.translatable("jei.gui." + RECIPE_GROUP + ".info.percent_u235", animTimeSeconds);
		Component percentU238String = Component.translatable("jei.gui." + RECIPE_GROUP + ".info.percent_u238", animTimeSeconds);
		Component percentBiproductString =Component.translatable("jei.gui." + RECIPE_GROUP + ".info.percent_biproduct", animTimeSeconds);

		Minecraft minecraft = Minecraft.getInstance();
		Font fontRenderer = minecraft.font;

		fontRenderer.draw(matrixStack, percentU235String, 36, 7, 0xFF616161);
		fontRenderer.draw(matrixStack, percentU238String, 36, 28, 0xFF616161);
		fontRenderer.draw(matrixStack, percentBiproductString, 36, 49, 0xFF616161);
	}

}
