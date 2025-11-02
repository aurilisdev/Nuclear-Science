package nuclearscience.compatibility.jei.recipecategories.psuedo.specificmachines;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.ItemStack;
import nuclearscience.NuclearScience;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.settings.NuclearConfig;
import nuclearscience.common.tile.TileGasCentrifuge;
import nuclearscience.compatibility.jei.utils.NuclearJeiTextures;
import nuclearscience.compatibility.jei.utils.psuedorecipes.PsuedoGasCentrifugeRecipe;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceBlocks;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.gas.GasStack;
import voltaic.compatibility.jei.recipecategories.AbstractRecipeCategory;
import voltaic.compatibility.jei.utils.gui.ScreenObject;
import voltaic.compatibility.jei.utils.gui.types.ArrowAnimatedObject;
import voltaic.compatibility.jei.utils.gui.types.BackgroundObject;
import voltaic.compatibility.jei.utils.gui.types.ItemSlotObject;
import voltaic.compatibility.jei.utils.gui.types.gasgauge.GasGaugeObject;
import voltaic.compatibility.jei.utils.label.types.LabelWrapperGeneric;
import voltaic.prefab.screen.component.types.ScreenComponentSlot;
import voltaic.prefab.utilities.math.Color;

public class GasCentrifugeRecipeCategory extends AbstractRecipeCategory<PsuedoGasCentrifugeRecipe> {

	public static final Color LABEL_COLOR = new Color(97, 97, 97, 255);

	public static final BackgroundObject BACK_WRAP = new BackgroundObject(132, 68);

	public static final ScreenObject GASCENTRIFUGE_ARROW_STATIC = new ScreenObject(NuclearJeiTextures.GASCENTRIFUGE_ARROW_STATIC, 19, 5);

	public static final ItemSlotObject OUTPUT_SLOT_1 = new ItemSlotObject(ScreenComponentSlot.SlotType.NORMAL, 113, 2, RecipeIngredientRole.OUTPUT);
	public static final ItemSlotObject OUTPUT_SLOT_2 = new ItemSlotObject(ScreenComponentSlot.SlotType.NORMAL, 113, 22, RecipeIngredientRole.OUTPUT);
	public static final ItemSlotObject BIPRODUCT_SLOT = new ItemSlotObject(ScreenComponentSlot.SlotType.NORMAL, 113, 42, RecipeIngredientRole.OUTPUT);

	public static final ArrowAnimatedObject ANIM_RIGHT_ARROW = new ArrowAnimatedObject(NuclearJeiTextures.GASCENTRIFUGE_ARROW_OFF, NuclearJeiTextures.GASCENTRIFUGE_ARROW_ON, 64, 4, StartDirection.LEFT);

	public static final GasGaugeObject IN_GAUGE = new GasGaugeObject(2, 6);

	public static final LabelWrapperGeneric U235 = new LabelWrapperGeneric(LABEL_COLOR, 7, 36, false, ChatFormatter.getChatDisplayShort(TileGasCentrifuge.PERCENT_U235 * 100, DisplayUnits.PERCENTAGE));
	public static final LabelWrapperGeneric U238 = new LabelWrapperGeneric(LABEL_COLOR, 28, 36, false, ChatFormatter.getChatDisplayShort((1 - TileGasCentrifuge.PERCENT_U235) * 100, DisplayUnits.PERCENTAGE));
	public static final LabelWrapperGeneric BIPROD = new LabelWrapperGeneric(LABEL_COLOR, 49, 36, false, ChatFormatter.getChatDisplayShort(TileGasCentrifuge.WASTE_MULTIPLIER * 100, DisplayUnits.PERCENTAGE));

	public static final LabelWrapperGeneric POWER_LABEL = new LabelWrapperGeneric(LABEL_COLOR, 58, 2, false, ChatFormatter.getChatDisplayShort(960, DisplayUnits.VOLTAGE).append(" ").append(ChatFormatter.getChatDisplayShort(NuclearConfig.INSTANCE.PARTICLEINJECTOR_USAGE_PER_PARTICLE.get(), DisplayUnits.JOULES)));

	public static final int ANIM_TIME = 100;

	public static ItemStack INPUT_MACHINE = new ItemStack(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.gascentrifuge));

	public static final String RECIPE_GROUP = "gascentrifuge";

	public static final RecipeType<PsuedoGasCentrifugeRecipe> RECIPE_TYPE = RecipeType.create(NuclearScience.ID, RECIPE_GROUP, PsuedoGasCentrifugeRecipe.class);

	public GasCentrifugeRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, NuclearTextUtils.jeiTranslated(RECIPE_GROUP), INPUT_MACHINE, BACK_WRAP, RECIPE_TYPE, ANIM_TIME);

		setOutputSlots(guiHelper, OUTPUT_SLOT_1, OUTPUT_SLOT_2, BIPRODUCT_SLOT);
		setGasInputs(guiHelper, IN_GAUGE);
		setAnimatedArrows(guiHelper, ANIM_RIGHT_ARROW);
		setScreenObjects(guiHelper, GASCENTRIFUGE_ARROW_STATIC);
		setLabels(U235, U238, BIPROD, POWER_LABEL);

	}

	@Override
	public List<ItemStack> getItemOutputs(PsuedoGasCentrifugeRecipe recipe) {
		List<ItemStack> outputs = new ArrayList<>();
		outputs.add(recipe.output1);
		outputs.add(recipe.output2);
		outputs.add(recipe.biproduct);
		return outputs;
	}

	@Override
	public List<List<GasStack>> getGasInputs(PsuedoGasCentrifugeRecipe recipe) {
		List<List<GasStack>> inputs = new ArrayList<>();
		inputs.add(recipe.inputGasStack.getMatchingGases());
		return inputs;
	}

}
