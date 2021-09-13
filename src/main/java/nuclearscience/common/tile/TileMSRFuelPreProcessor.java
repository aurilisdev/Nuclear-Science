package nuclearscience.common.tile;

import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.common.inventory.container.ContainerChemicalMixer;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentProcessorType;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraftforge.fluids.FluidStack;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.common.recipe.categories.fluid3items2item.specificmachines.MSRFuelPreProcessorRecipe;
import nuclearscience.common.settings.Constants;

public class TileMSRFuelPreProcessor extends GenericTileTicking {
    public static final int MAX_TANK_CAPACITY = 5000;
    public long clientTicks = 0;

    public TileMSRFuelPreProcessor() {
	super(DeferredRegisters.TILE_MSRFUELPREPROCESSOR.get());
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 2)
		.maxJoules(Constants.MSRFUELPREPROCESSOR_USAGE_PER_TICK * 10));
	addComponent(((ComponentFluidHandlerMulti) new ComponentFluidHandlerMulti(this).relativeInput(Direction.EAST).relativeOutput(Direction.WEST))
		.setAddFluidsValues(MSRFuelPreProcessorRecipe.class, NuclearScienceRecipeInit.MSR_FUEL_PREPROCESSOR_TYPE, MAX_TANK_CAPACITY, true,
			true));
	addComponent(new ComponentInventory(this).size(8).relativeFaceSlots(Direction.EAST, 0, 1, 2).relativeFaceSlots(Direction.UP, 0, 1, 2)
		.relativeSlotFaces(4, Direction.DOWN).valid((slot, stack) -> slot < 5 || stack.getItem() instanceof ItemProcessorUpgrade));
	addComponent(new ComponentProcessor(this).upgradeSlots(5, 6, 7)
		.canProcess(component -> component.outputToPipe(component).consumeBucket(1).canProcessFluidItem2FluidRecipe(component,
			FluidItem2FluidRecipe.class, NuclearScienceRecipeInit.MSR_FUEL_PREPROCESSOR_TYPE))
		.process(component -> processFluid3Items2ItemRecipe(component, MSRFuelPreProcessorRecipe.class))
		.usage(Constants.MSRFUELPREPROCESSOR_USAGE_PER_TICK).type(ComponentProcessorType.ObjectToObject)
		.requiredTicks(Constants.MSRFUELPREPROCESSOR_REQUIRED_TICKS));
	addComponent(new ComponentContainerProvider("container.msrfuelpreprocessor")
		.createMenu((id, player) -> new ContainerChemicalMixer(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));

    }

    // TODO: Why the fuck did i do this here...
    public void processFluid3Items2ItemRecipe(ComponentProcessor pr, Class<MSRFuelPreProcessorRecipe> recipeClass) {
	if (pr.getRecipe() != null) {
	    MSRFuelPreProcessorRecipe locRecipe = recipeClass.cast(pr.getRecipe());
	    AbstractFluidHandler<?> fluid = pr.getHolder().getComponent(ComponentType.FluidHandler);
	    FluidStack inputFluid = ((FluidIngredient) locRecipe.getIngredients().get(1)).getFluidStack();

	    if (pr.getOutputCap() >= pr.getOutput().getCount() + locRecipe.getRecipeOutput().getCount()) {
		if (pr.getOutput().isEmpty()) {
		    pr.output(locRecipe.getRecipeOutput().copy());
		} else {
		    pr.getOutput().setCount(pr.getOutput().getCount() + locRecipe.getRecipeOutput().getCount());
		}
		pr.getInput().setCount(pr.getInput().getCount() - ((CountableIngredient) locRecipe.getIngredients().get(0)).getStackSize());
		pr.getSecondInput()
			.setCount(pr.getSecondInput().getCount() - ((CountableIngredient) locRecipe.getIngredients().get(1)).getStackSize());
		pr.getThirdInput().setCount(pr.getThirdInput().getCount() - ((CountableIngredient) locRecipe.getIngredients().get(2)).getStackSize());
		fluid.getStackFromFluid(inputFluid.getFluid(), true).shrink(inputFluid.getAmount());
		this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	    }
	}
    }

    protected void tickClient(ComponentTickable tickable) {
	boolean running = this.<ComponentProcessor>getComponent(ComponentType.Processor).operatingTicks > 0;
	if (running) {
	    if (world.rand.nextDouble() < 0.15) {
		world.addParticle(ParticleTypes.SMOKE, pos.getX() + world.rand.nextDouble(), pos.getY() + world.rand.nextDouble() * 0.4 + 0.5,
			pos.getZ() + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
	    }
	    clientTicks++;
	}
    }

}
