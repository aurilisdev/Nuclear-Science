package nuclearscience.common.tile;

import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import nuclearscience.DeferredRegisters;
import nuclearscience.SoundRegister;
import nuclearscience.common.inventory.container.ContainerNuclearBoiler;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.common.settings.Constants;

public class TileNuclearBoiler extends GenericTileTicking {

    public static final int MAX_TANK_CAPACITY = 5000;

    public TileNuclearBoiler(BlockPos pos, BlockState state) {
	super(DeferredRegisters.TILE_CHEMICALBOILER.get(), pos, state);
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentElectrodynamic(this).input(Direction.DOWN).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 2)
		.maxJoules(Constants.CHEMICALBOILER_USAGE_PER_TICK * 10));
	addComponent(new ComponentFluidHandlerMulti(this)
		.setAddFluidsValues(FluidItem2FluidRecipe.class, NuclearScienceRecipeInit.NUCLEAR_BOILER_TYPE, MAX_TANK_CAPACITY, true, true)
		.relativeInput(Direction.EAST));
	addComponent(new ComponentInventory(this).size(6).relativeSlotFaces(0, Direction.EAST, Direction.UP).relativeSlotFaces(1, Direction.DOWN)
		.valid((slot, stack) -> slot < 3 || stack.getItem() instanceof ItemProcessorUpgrade));
	addComponent(new ComponentProcessor(this).upgradeSlots(3, 4, 5)
		.canProcess(component -> component.outputToPipe(component).consumeBucket(1).dispenseBucket(2)
			.canProcessFluidItem2FluidRecipe(component, FluidItem2FluidRecipe.class, NuclearScienceRecipeInit.NUCLEAR_BOILER_TYPE))
		.process(component -> component.processFluidItem2FluidRecipe(component, FluidItem2FluidRecipe.class))
		.usage(Constants.CHEMICALBOILER_USAGE_PER_TICK).type(ComponentProcessorType.ObjectToObject)
		.requiredTicks(Constants.CHEMICALBOILER_REQUIRED_TICKS));
	addComponent(new ComponentContainerProvider("container.nuclearboiler")
		.createMenu((id, player) -> new ContainerNuclearBoiler(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
    }

    @Override
    public AABB getRenderBoundingBox() {
	return super.getRenderBoundingBox().inflate(1);
    }

    protected void tickClient(ComponentTickable tickable) {
	boolean running = this.<ComponentProcessor>getComponent(ComponentType.Processor).operatingTicks > 0;
	if (running && level.random.nextDouble() < 0.15) {
	    level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(),
		    worldPosition.getY() + level.random.nextDouble() * 0.4 + 0.5, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
	}
	if (running && tickable.getTicks() % 100 == 0) {
	    SoundAPI.playSound(SoundRegister.SOUND_NUCLEARBOILER.get(), SoundSource.BLOCKS, 1, 1, worldPosition);
	}
    }

}
