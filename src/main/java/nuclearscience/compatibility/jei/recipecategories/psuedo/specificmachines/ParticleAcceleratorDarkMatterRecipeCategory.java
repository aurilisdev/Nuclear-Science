package nuclearscience.compatibility.jei.recipecategories.psuedo.specificmachines;

import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.screen.ITexture;
import electrodynamics.compatibility.jei.recipecategories.utils.AbstractRecipeCategory;
import electrodynamics.compatibility.jei.recipecategories.utils.psuedorecipes.types.PsuedoItem2ItemRecipe;
import electrodynamics.compatibility.jei.utils.RecipeType;
import electrodynamics.compatibility.jei.utils.gui.ScreenObject;
import electrodynamics.compatibility.jei.utils.gui.types.ArrowAnimatedObject;
import electrodynamics.compatibility.jei.utils.gui.types.BackgroundObject;
import electrodynamics.compatibility.jei.utils.gui.types.ItemSlotObject;
import electrodynamics.compatibility.jei.utils.label.types.LabelWrapperGeneric;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.SlotType;
import electrodynamics.prefab.utilities.RenderingUtils;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import nuclearscience.References;
import nuclearscience.common.settings.Constants;
import nuclearscience.compatibility.jei.utils.NuclearJeiTextures;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceBlocks;

public class ParticleAcceleratorDarkMatterRecipeCategory extends AbstractRecipeCategory<PsuedoItem2ItemRecipe> {

	public static final BackgroundObject BACK_WRAP = new BackgroundObject(132, 132);

	public static final ScreenObject ATOM_TOP = new ScreenObject(NuclearJeiTextures.PARTICLEACCELERATOR_DMATOM, 60, 16);
	public static final ScreenObject ATOM_BOTTOM = new ScreenObject(NuclearJeiTextures.PARTICLEACCELERATOR_DMATOM, 55, 101);

	public static final ItemSlotObject OUTPUT_SLOT = new ItemSlotObject(SlotType.NORMAL, 57, 57, false);

	public static final ArrowAnimatedObject ANIM_RIGHT_LEFT = new ArrowAnimatedObject(NuclearJeiTextures.PARTICLEACCELERATOR_DMARROWOFF_LEFT, NuclearJeiTextures.PARTICLEACCELERATOR_DMARROWON_LEFT, 25, 22, StartDirection.TOP);
	public static final ArrowAnimatedObject ANIM_RIGHT_RIGHT = new ArrowAnimatedObject(NuclearJeiTextures.PARTICLEACCELERATOR_DMARROWOFF_RIGHT, NuclearJeiTextures.PARTICLEACCELERATOR_DMARROWON_RIGHT, 72, 39, StartDirection.BOTTOM);

	public static final LabelWrapperGeneric POWER_LABEL = new LabelWrapperGeneric(0xFF808080, 124, 2, false, ChatFormatter.getChatDisplayShort(960, DisplayUnit.VOLTAGE).append(" ").append(ChatFormatter.getChatDisplayShort(Constants.PARTICLEINJECTOR_USAGE_PER_PARTICLE, DisplayUnit.JOULES)));

	public static final int ANIM_TIME = 50;

	public static ItemStack INPUT_MACHINE = new ItemStack(NuclearScienceBlocks.blockParticleInjector);

	public static final String RECIPE_GROUP = "particalacceleratordarkmatter";

	public static final RecipeType<PsuedoItem2ItemRecipe> RECIPE_TYPE = RecipeType.create(References.ID, RECIPE_GROUP, PsuedoItem2ItemRecipe.class);

	public ParticleAcceleratorDarkMatterRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, NuclearTextUtils.jeiTranslated(RECIPE_GROUP), INPUT_MACHINE, BACK_WRAP, RECIPE_TYPE, ANIM_TIME);

		setOutputSlots(guiHelper, OUTPUT_SLOT);
		setAnimatedArrows(guiHelper, ANIM_RIGHT_LEFT, ANIM_RIGHT_RIGHT);
		setScreenObjects(guiHelper, ATOM_TOP, ATOM_BOTTOM);
		setLabels(POWER_LABEL);

	}

	@Override
	public List<ItemStack> getItemOutputs(PsuedoItem2ItemRecipe recipe) {
		return Arrays.asList(recipe.OUTPUT);
	}
	
	@Override
	public void setIngredients(PsuedoItem2ItemRecipe recipe, IIngredients ingredients) {
		
		super.setIngredients(recipe, ingredients);
		ingredients.setInputIngredients(recipe.INPUTS);
		
	}
	
	@Override
	public void drawPre(MatrixStack stack, PsuedoItem2ItemRecipe recipe) {

		stack.pushPose();

		ITexture texture = NuclearJeiTextures.PARTICLEACCELERATOR_DMBLACKHOLE;
		
		RenderingUtils.bindTexture(texture.getLocation());
		
		Screen.blit(stack, 28, 28, texture.textureU(), texture.textureV(), texture.textureWidth(), texture.textureHeight(), texture.imageWidth(), texture.imageHeight());

		RenderingUtils.resetShaderColor();

		stack.popPose();

	}

}
