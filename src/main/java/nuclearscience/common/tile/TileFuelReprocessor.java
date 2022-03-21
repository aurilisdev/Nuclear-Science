package nuclearscience.common.tile;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.block.VoxelShapes;
import electrodynamics.common.inventory.container.tile.ContainerO2OProcessor;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.common.settings.Constants;

public class TileFuelReprocessor extends GenericTile {

	public TileFuelReprocessor(BlockPos pos, BlockState state) {
		super(DeferredRegisters.TILE_FUELREPROCESSOR.get(), pos, state);
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 4).relativeInput(Direction.NORTH));
		addComponent(new ComponentInventory(this).size(6).faceSlots(Direction.UP, 0).faceSlots(Direction.DOWN, 1).relativeFaceSlots(Direction.EAST, 1).relativeFaceSlots(Direction.WEST, 2).inputs(1).outputs(1).upgrades(3).processors(1).processorInputs(1).biproducts(1).validUpgrades(ContainerO2OProcessor.VALID_UPGRADES).valid(machineValidator()));
		addProcessor(new ComponentProcessor(this).setProcessorNumber(0).canProcess(component -> component.canProcessItem2ItemRecipe(component, NuclearScienceRecipeInit.FUEL_REPROCESSOR_TYPE)).process(component -> component.processItem2ItemRecipe(component)).requiredTicks((long) Constants.FUELREPROCESSOR_REQUIRED_TICKS).usage(Constants.FUELREPROCESSOR_USAGE_PER_TICK));
		addComponent(new ComponentContainerProvider("container.fuelreprocessor").createMenu((id, player) -> new ContainerO2OProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	protected void tickServer(ComponentTickable tick) {
		InventoryUtils.handleExperienceUpgrade(this);
	}

	public void tickClient(ComponentTickable tickable) {
		boolean running = getProcessor(0).operatingTicks > 0;
		if (running && tickable.getTicks() % 100 == 0) {
			SoundAPI.playSound(electrodynamics.SoundRegister.SOUND_MINERALCRUSHER.get(), SoundSource.BLOCKS, 1, 1, worldPosition);
		}
	}

	static {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.875, 0, 0.9375, 1, 1, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.875, 0, 0, 1, 1, 0.0625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.125, 1, 0.0625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0, 0.9375, 0.125, 1, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0, 0.875, 0.0625, 1, 0.9375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0, 0.0625, 0.0625, 1, 0.125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.9375, 0, 0.0625, 1, 1, 0.125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.9375, 0, 0.875, 1, 1, 0.9375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.0625, 0.4375, 0.8125, 0.1875, 0.5625, 0.9375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.4375, 0.75, 0.25, 0.5625, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.4375, 0.6875, 0.25, 0.5625, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.4375, 0.75, 0.3125, 0.5625, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.0625, 0.4375, 0.0625, 0.1875, 0.5625, 0.1875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.4375, 0.125, 0.25, 0.5625, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.4375, 0.1875, 0.3125, 0.5625, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.4375, 0.25, 0.25, 0.5625, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.8125, 0.4375, 0.8125, 0.9375, 0.5625, 0.9375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.4375, 0.75, 0.875, 0.5625, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.4375, 0.6875, 0.8125, 0.5625, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.6875, 0.4375, 0.75, 0.75, 0.5625, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.8125, 0.4375, 0.0625, 0.9375, 0.5625, 0.1875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.4375, 0.125, 0.875, 0.5625, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.6875, 0.4375, 0.1875, 0.75, 0.5625, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.4375, 0.25, 0.8125, 0.5625, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.125, 0.3125, 0.8125, 0.875, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.125, 0.6875, 0.3125, 0.875, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.125, 0.25, 0.3125, 0.875, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.6875, 0.125, 0.25, 0.75, 0.875, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.6875, 0.125, 0.6875, 0.75, 0.875, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.125, 0.6875, 0.6875, 0.875, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.125, 0.1875, 0.6875, 0.875, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.9375, 0.25, 0.75, 1, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.875, 0.3125, 0.6875, 0.9375, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.9375, 0.25, 0.25, 1, 0.75, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.8125, 0.3125, 0.3125, 0.9375, 0.6875, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.1875, 0.125, 0.6875, 0.25, 0.1875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.8125, 0.1875, 0.3125, 0.875, 0.25, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.1875, 0.6875, 0.8125, 0.25, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.6875, 0.1875, 0.75, 0.75, 0.25, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.6875, 0.1875, 0.1875, 0.75, 0.25, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.1875, 0.25, 0.8125, 0.25, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.1875, 0.25, 0.25, 0.25, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.1875, 0.1875, 0.3125, 0.25, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.1875, 0.75, 0.3125, 0.25, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.1875, 0.6875, 0.25, 0.25, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.1875, 0.3125, 0.1875, 0.25, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.1875, 0.8125, 0.6875, 0.25, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.75, 0.125, 0.6875, 0.8125, 0.1875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.8125, 0.75, 0.3125, 0.875, 0.8125, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.75, 0.6875, 0.8125, 0.8125, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.6875, 0.75, 0.75, 0.75, 0.8125, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.6875, 0.75, 0.1875, 0.75, 0.8125, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.75, 0.25, 0.8125, 0.8125, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.75, 0.25, 0.25, 0.8125, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.75, 0.1875, 0.3125, 0.8125, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.75, 0.75, 0.3125, 0.8125, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.75, 0.6875, 0.25, 0.8125, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.75, 0.3125, 0.1875, 0.8125, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.75, 0.8125, 0.6875, 0.8125, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.25, 0.375, 0.1875, 0.75, 0.4375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5625, 0.25, 0.125, 0.625, 0.75, 0.1875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.25, 0.125, 0.4375, 0.75, 0.1875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.25, 0.8125, 0.4375, 0.75, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5625, 0.25, 0.8125, 0.625, 0.75, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.25, 0.5625, 0.1875, 0.75, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.8125, 0.6875, 0.5625, 0.875, 0.75, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.8125, 0.6875, 0.375, 0.875, 0.75, 0.4375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.8125, 0.25, 0.375, 0.875, 0.3125, 0.4375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.8125, 0.25, 0.5625, 0.875, 0.3125, 0.625), BooleanOp.OR);
		VoxelShapes.registerShape(DeferredRegisters.blockFuelReprocessor, shape, Direction.WEST);
	}
}
