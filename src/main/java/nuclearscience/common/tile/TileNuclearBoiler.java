package nuclearscience.common.tile;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import nuclearscience.common.inventory.container.ContainerNuclearBoiler;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.registers.NuclearScienceBlockTypes;
import nuclearscience.registers.NuclearScienceSounds;

public class TileNuclearBoiler extends GenericTile {

	public static final int MAX_TANK_CAPACITY = 5000;

	public TileNuclearBoiler(BlockPos pos, BlockState state) {
		super(NuclearScienceBlockTypes.TILE_CHEMICALBOILER.get(), pos, state);
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentElectrodynamic(this).input(Direction.DOWN).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2));
		addComponent(new ComponentFluidHandlerMulti(this).setTanks(1, 1, MAX_TANK_CAPACITY).setInputDirections(Direction.EAST).setOutputDirections(Direction.WEST).setRecipeType(NuclearScienceRecipeInit.NUCLEAR_BOILER_TYPE.get()));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().processors(1, 1, 0, 0).bucketInputs(1).bucketInputs(1).upgrades(3)).relativeSlotFaces(0, Direction.EAST, Direction.UP).relativeSlotFaces(1, Direction.DOWN).validUpgrades(ContainerNuclearBoiler.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentProcessor(this).canProcess(component -> component.outputToPipe().consumeBucket().dispenseBucket().canProcessFluidItem2FluidRecipe(component, NuclearScienceRecipeInit.NUCLEAR_BOILER_TYPE.get())).process(component -> component.processFluidItem2FluidRecipe(component)));
		addComponent(new ComponentContainerProvider("container.nuclearboiler").createMenu((id, player) -> new ContainerNuclearBoiler(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	@Override
	public AABB getRenderBoundingBox() {
		return super.getRenderBoundingBox().inflate(1);
	}

	protected void tickServer(ComponentTickable tickable) {
		Level world = getLevel();
		ComponentDirection boilerComponentDir = getComponent(ComponentType.Direction);
		Direction centrifugeDir = boilerComponentDir.getDirection().getCounterClockWise();
		BlockEntity tile = world.getBlockEntity(getBlockPos().relative(centrifugeDir));
		if (tile != null && tile instanceof TileGasCentrifuge centrifuge) {
			ComponentFluidHandlerMulti centrifugeHandler = centrifuge.getComponent(ComponentType.FluidHandler);
			if (centrifugeHandler != null) {
				ComponentDirection centrifugeComponentDir = centrifuge.getComponent(ComponentType.Direction);
				if (centrifugeComponentDir.getDirection() == centrifugeDir) {
					ComponentFluidHandlerMulti boilerHandler = getComponent(ComponentType.FluidHandler);
					FluidTank boilerTank = boilerHandler.getOutputTanks()[0];
					FluidTank centrifugeTank = centrifugeHandler.getInputTanks()[0];
					int accepted = centrifugeTank.fill(boilerTank.getFluid(), FluidAction.SIMULATE);
					centrifugeTank.fill(new FluidStack(boilerTank.getFluid().getFluid(), accepted), FluidAction.EXECUTE);
					boilerTank.drain(accepted, FluidAction.EXECUTE);
				}
			}
		}
	}

	protected void tickClient(ComponentTickable tickable) {
		boolean running = this.<ComponentProcessor>getComponent(ComponentType.Processor).operatingTicks.get() > 0;
		if (running && level.random.nextDouble() < 0.15) {
			level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble() * 0.4 + 0.5, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
		if (running && tickable.getTicks() % 100 == 0) {
			SoundAPI.playSound(NuclearScienceSounds.SOUND_NUCLEARBOILER.get(), SoundSource.BLOCKS, 1, 1, worldPosition);
		}
	}

}
