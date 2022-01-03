package nuclearscience.compatibility.jei.recipecategories.psuedo.specificmachines;

import java.util.ArrayList;
import java.util.List;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.compatibility.jei.recipecategories.psuedo.PsuedoItem2ItemRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import nuclearscience.References;

public class ParticleAcceleratorAntiMatterRecipeCategory implements IRecipeCategory<PsuedoItem2ItemRecipe> {

	// JEI Window Parameters
	private static final int INPUT_SLOT = 0;
	private static final int OUTPUT_SLOT = 1;
	private static final int ANY_ITEM_INPUT_SLOT = 2;

	private static int[] GUI_BACKGROUND = { 0, 0, 132, 66 };
	private static int[] PROCESSING_ARROW_COORDS = { 0, 67, 82, 47 };

	private static int[] INPUT_OFFSET = { 12, 39 };
	private static int[] OUTPUT_OFFSET = { 101, 20 };
	private static int[] PROCESSING_ARROW_OFFSET = { 17, 6 };

	private static int ANIM_TIME = 50;
	private static int DESC_Y_HEIGHT = 58;

	private static String MOD_ID = References.ID;
	private static String RECIPE_GROUP = "particalaccelerator.antimatter";
	private static String GUI_TEXTURE = "textures/gui/jei/particle_accelerator_antimatter_gui.png";

	public static ItemStack INPUT_MACHINE = new ItemStack(nuclearscience.DeferredRegisters.blockParticleInjector);
	private LoadingCache<Integer, IDrawableAnimated> cachedArrows;
	private static StartDirection ARROW_START_DIRECTION = IDrawableAnimated.StartDirection.LEFT;

	public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	private IDrawable BACKGROUND;
	private IDrawable ICON;

	public ParticleAcceleratorAntiMatterRecipeCategory(IGuiHelper guiHelper) {

		ICON = guiHelper.createDrawableIngredient(INPUT_MACHINE);
		BACKGROUND = guiHelper.createDrawable(new ResourceLocation(MOD_ID, GUI_TEXTURE), GUI_BACKGROUND[0], GUI_BACKGROUND[1], GUI_BACKGROUND[2],
				GUI_BACKGROUND[3]);

		cachedArrows = CacheBuilder.newBuilder().maximumSize(1).build(new CacheLoader<Integer, IDrawableAnimated>() {
			@Override
			public IDrawableAnimated load(Integer cookTime) {
				return guiHelper.drawableBuilder(new ResourceLocation(MOD_ID, GUI_TEXTURE), PROCESSING_ARROW_COORDS[0], PROCESSING_ARROW_COORDS[1],
						PROCESSING_ARROW_COORDS[2], PROCESSING_ARROW_COORDS[3]).buildAnimated(cookTime, ARROW_START_DIRECTION, false);
			}
		});
	}

	@Override
	public ResourceLocation getUid() {
		return UID;
	}

	@Override
	public Class<? extends PsuedoItem2ItemRecipe> getRecipeClass() {
		return PsuedoItem2ItemRecipe.class;
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
	public void setIngredients(PsuedoItem2ItemRecipe recipe, IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, recipeInput(recipe));
		ingredients.setOutput(VanillaTypes.ITEM, recipe.OUTPUT);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, PsuedoItem2ItemRecipe recipe, IIngredients ingredients) {

		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(INPUT_SLOT, true, INPUT_OFFSET[0], INPUT_OFFSET[1]);
		guiItemStacks.init(ANY_ITEM_INPUT_SLOT, true, 12, 2);
		guiItemStacks.init(OUTPUT_SLOT, false, OUTPUT_OFFSET[0], OUTPUT_OFFSET[1]);

		guiItemStacks.set(ingredients);

	}

	@Override
	public void draw(PsuedoItem2ItemRecipe recipe, PoseStack matrixStack, double mouseX, double mouseY) {
		IDrawableAnimated arrow = cachedArrows.getUnchecked(ANIM_TIME);
		arrow.draw(matrixStack, PROCESSING_ARROW_OFFSET[0], PROCESSING_ARROW_OFFSET[1]);

		int animTimeSeconds = ANIM_TIME / 20;
		TranslatableComponent timeString = new TranslatableComponent("jei.gui." + RECIPE_GROUP + ".info.power", animTimeSeconds);
		Minecraft minecraft = Minecraft.getInstance();
		Font fontRenderer = minecraft.font;
		float stringWidth = fontRenderer.width(timeString);
		fontRenderer.draw(matrixStack, timeString, getBackground().getWidth() - stringWidth, DESC_Y_HEIGHT, 0xFF808080);
	}

	private static List<List<ItemStack>> recipeInput(PsuedoItem2ItemRecipe recipe) {

		List<ItemStack> emagCell = new ArrayList<>();
		emagCell.add(recipe.INPUTS.get(0).getItems()[0]);

		/* Gets a list of all Vanilla items */

		int i = 0;
		List<Item> allItems = new ArrayList<>(ForgeRegistries.ITEMS.getValues());
		List<Item> vanillaItems = new ArrayList<>();
		CreativeModeTab[] vanillaItemGroups = { CreativeModeTab.TAB_BREWING, CreativeModeTab.TAB_BUILDING_BLOCKS, CreativeModeTab.TAB_COMBAT,
				CreativeModeTab.TAB_DECORATIONS, CreativeModeTab.TAB_FOOD, CreativeModeTab.TAB_MISC, CreativeModeTab.TAB_REDSTONE,
				CreativeModeTab.TAB_TOOLS, CreativeModeTab.TAB_TRANSPORTATION };
		for (Item item : allItems) {
			for (i = 0; i < vanillaItemGroups.length; i++) {
				if (item.getItemCategory() == vanillaItemGroups[i]) {
					vanillaItems.add(item);
					break;
				}
			}
		}
		// Filters out Air item; breaks JEI category if left!
		for (Item item : vanillaItems) {
			if (item instanceof AirItem) {
				vanillaItems.remove(item);
			}
		}

		List<ItemStack> vanillaItemStacks = new ArrayList<>();
		for (Item item : vanillaItems) {
			vanillaItemStacks.add(new ItemStack(item));
		}

		List<List<ItemStack>> inputSlots = new ArrayList<>();
		inputSlots.add(emagCell);
		inputSlots.add(vanillaItemStacks);

		return inputSlots;

	}

}
