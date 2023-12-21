package nuclearscience.compatibility.jei.recipecategories.psuedo.specificmachines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.compatibility.jei.recipecategories.utils.AbstractRecipeCategory;
import electrodynamics.compatibility.jei.recipecategories.utils.psuedorecipes.types.PsuedoItem2ItemRecipe;
import electrodynamics.compatibility.jei.utils.gui.types.ArrowAnimatedObject;
import electrodynamics.compatibility.jei.utils.gui.types.BackgroundObject;
import electrodynamics.compatibility.jei.utils.gui.types.ItemSlotObject;
import electrodynamics.compatibility.jei.utils.label.types.LabelWrapperGeneric;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.SlotType;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import nuclearscience.References;
import nuclearscience.common.settings.Constants;
import nuclearscience.compatibility.jei.utils.NuclearJeiTextures;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceBlocks;

public class ParticleAcceleratorAntiMatterRecipeCategory extends AbstractRecipeCategory<PsuedoItem2ItemRecipe> {

	public static final BackgroundObject BACK_WRAP = new BackgroundObject(132, 66);

	public static final ItemSlotObject MATTER_SLOT = new ItemSlotObject(SlotType.NORMAL, 12, 2, RecipeIngredientRole.INPUT);
	public static final ItemSlotObject CELL_SLOT = new ItemSlotObject(SlotType.NORMAL, 12, 39, RecipeIngredientRole.INPUT);
	public static final ItemSlotObject OUTPUT_SLOT = new ItemSlotObject(SlotType.NORMAL, 101, 20, RecipeIngredientRole.OUTPUT);

	public static final ArrowAnimatedObject ANIM_RIGHT_ARROW = new ArrowAnimatedObject(NuclearJeiTextures.PARTICLEACCELERATOR_AMARROW_OFF, NuclearJeiTextures.PARTICLEACCELERATOR_AMARROW_ON, 17, 6, StartDirection.LEFT);

	public static final LabelWrapperGeneric POWER_LABEL = new LabelWrapperGeneric(0xFF808080, 58, 2, false, ChatFormatter.getChatDisplayShort(960, DisplayUnit.VOLTAGE).append(" ").append(ChatFormatter.getChatDisplayShort(Constants.PARTICLEINJECTOR_USAGE_PER_PARTICLE, DisplayUnit.JOULES)));
	// public static final LabelWrapperGeneric COLLISION_LABEL = new LabelWrapperGeneric(0xFF808080, 58, 132, true, NuclearTextUtils.jeiTranslated("particalaccelerator.antimatter.collision"));

	public static final int ANIM_TIME = 50;

	public static ItemStack INPUT_MACHINE = new ItemStack(NuclearScienceBlocks.blockParticleInjector);

	public static final String RECIPE_GROUP = "particalacceleratorantimatter";

	public static final RecipeType<PsuedoItem2ItemRecipe> RECIPE_TYPE = RecipeType.create(References.ID, RECIPE_GROUP, PsuedoItem2ItemRecipe.class);

	public ParticleAcceleratorAntiMatterRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, NuclearTextUtils.jeiTranslated(RECIPE_GROUP), INPUT_MACHINE, BACK_WRAP, RECIPE_TYPE, ANIM_TIME);

		setInputSlots(guiHelper, MATTER_SLOT, CELL_SLOT);
		setOutputSlots(guiHelper, OUTPUT_SLOT);
		setAnimatedArrows(guiHelper, ANIM_RIGHT_ARROW);
		setLabels(POWER_LABEL);
	}

	@Override
	public List<List<ItemStack>> getItemInputs(PsuedoItem2ItemRecipe recipe) {
		List<List<ItemStack>> inputs = new ArrayList<>();

		inputs.add(getMatterItems());
		inputs.add(Arrays.asList(recipe.INPUTS.get(0).getItems()));

		return inputs;
	}

	public static List<ItemStack> getMatterItems() {
		List<Item> items = new ArrayList<>(ForgeRegistries.ITEMS.getValues());
		List<ItemStack> matter = new ArrayList<>();

		items.forEach(item -> {
			if (!(item instanceof AirItem)) {
				matter.add(new ItemStack(item));
			}
		});

		return matter;
	}

	@Override
	public List<ItemStack> getItemOutputs(PsuedoItem2ItemRecipe recipe) {
		return Arrays.asList(recipe.OUTPUT);
	}

}
