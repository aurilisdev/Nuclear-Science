package nuclearscience.common.tile;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasTank;
import electrodynamics.common.block.VoxelShapes;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.sound.SoundBarrierMethods;
import electrodynamics.prefab.sound.utils.ITickableSound;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentGasHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.Location;
import electrodynamics.registers.ElectrodynamicsRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.common.inventory.container.ContainerGasCentrifuge;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceBlockTypes;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceSounds;

public class TileGasCentrifuge extends GenericTile implements ITickableSound {

	public static final double TANKCAPACITY = 5000;
	public static final double REQUIRED = 2500;
	public static final double PERCENT_U235 = 0.172;
	public static final double WASTE_MULTIPLIER = 0.1;
	public Property<Integer> spinSpeed = property(new Property<>(PropertyType.Integer, "spinSpeed", 0));
	public Property<Double> stored235 = property(new Property<>(PropertyType.Double, "stored235", 0.0));
	public Property<Double> stored238 = property(new Property<>(PropertyType.Double, "stored238", 0.0));
	public Property<Double> storedWaste = property(new Property<>(PropertyType.Double, "storedWaste", 0.0));
	public Property<Boolean> isRunning = property(new Property<>(PropertyType.Boolean, "isRunning", false));

	private static final int RADATION_RADIUS_BLOCKS = 5;
	private static final int RADIATION_STRENGTH = 5000;

	private boolean isSoundPlaying = false;

	public TileGasCentrifuge(BlockPos pos, BlockState state) {
		super(NuclearScienceBlockTypes.TILE_GASCENTRIFUGE.get(), pos, state);
		addComponent(new ComponentTickable(this).tickClient(this::tickClient).tickServer(this::tickServer));
		addComponent(new ComponentDirection(this));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentGasHandlerMulti(this).setInputTanks(1, arr(TANKCAPACITY), arr(293.1), arr(1)).setInputGasTags(NuclearScienceTags.Gases.URANIUM_HEXAFLUORIDE).setInputDirections(Direction.NORTH));
		addComponent(new ComponentElectrodynamic(this).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2).input(Direction.DOWN).maxJoules(Constants.GASCENTRIFUGE_USAGE_PER_TICK * 10));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().outputs(3).upgrades(3)).universalSlots(0, 1, 2).validUpgrades(ContainerGasCentrifuge.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentProcessor(this).usage(Constants.GASCENTRIFUGE_USAGE_PER_TICK).requiredTicks(Constants.GASCENTRIFUGE_REQUIRED_TICKS_PER_PROCESSING).canProcess(this::canProcess).process(this::process));
		addComponent(new ComponentContainerProvider("container.gascentrifuge", this).createMenu((id, player) -> new ContainerGasCentrifuge(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	public boolean canProcess(ComponentProcessor processor) {
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		ComponentInventory inv = getComponent(ComponentType.Inventory);
		ComponentGasHandlerMulti gasHandler = getComponent(ComponentType.GasHandler);
		boolean hasGas = gasHandler.getInputTanks()[0].getGasAmount() >= REQUIRED / 60.0;
		boolean val = electro.getJoulesStored() >= processor.getUsage() && hasGas && inv.getItem(0).getCount() < inv.getItem(0).getMaxStackSize() && inv.getItem(1).getCount() < inv.getItem(1).getMaxStackSize() && inv.getItem(2).getCount() < inv.getItem(2).getMaxStackSize();
		if (!val && spinSpeed.get() > 0) {
			spinSpeed.set(0);
		}

		isRunning.set(val);

		return val;
	}

	public void process(ComponentProcessor processor) {
		ComponentInventory inv = getComponent(ComponentType.Inventory);
		ComponentGasHandlerMulti multi = getComponent(ComponentType.GasHandler);
		// ComponentFluidHandlerMulti multi = getComponent(ComponentType.FluidHandler);
		spinSpeed.set(processor.operatingSpeed.get().intValue());
		double processed = REQUIRED / 60.0;
		GasTank tank = multi.getInputTanks()[0];

		if (ElectrodynamicsRegistries.gasRegistry().tags().getTag(NuclearScienceTags.Gases.URANIUM_HEXAFLUORIDE).contains(tank.getGas().getGas()) && tank.getGasAmount() >= processed) {
			tank.drain(processed, GasAction.EXECUTE);
		}

		stored235.set(stored235.get() + processed * PERCENT_U235);
		stored238.set(stored238.get() + processed * (1 - PERCENT_U235 - WASTE_MULTIPLIER));
		storedWaste.set(stored235.get() + processed * WASTE_MULTIPLIER);
		if (stored235.get() > REQUIRED) {
			ItemStack stack = inv.getItem(0);
			if (!stack.isEmpty()) {
				stack.setCount(stack.getCount() + 1);
			} else {
				inv.setItem(0, new ItemStack(NuclearScienceItems.ITEM_URANIUM235.get()));
			}
			stored235.set(stored235.get() - REQUIRED);
		}
		if (stored238.get() > REQUIRED) {
			ItemStack stack = inv.getItem(1);
			if (!stack.isEmpty()) {
				stack.setCount(stack.getCount() + 1);
			} else {
				inv.setItem(1, new ItemStack(NuclearScienceItems.ITEM_URANIUM238.get()));
			}
			stored238.set(stored238.get() - REQUIRED);
		}
		if (storedWaste.get() > REQUIRED) {
			ItemStack stack = inv.getItem(2);
			if (!stack.isEmpty()) {
				stack.grow(1);
			} else {
				inv.setItem(2, new ItemStack(NuclearScienceItems.ITEM_FISSILEDUST.get(), 1));
			}
			storedWaste.set(storedWaste.get() - REQUIRED);
		}
	}

	protected void tickClient(ComponentTickable tickable) {
		if (!isSoundPlaying && shouldPlaySound()) {
			isSoundPlaying = true;
			SoundBarrierMethods.playTileSound(NuclearScienceSounds.SOUND_GASCENTRIFUGE.get(), this, true);
		}
	}

	protected void tickServer(ComponentTickable tickable) {
		if (level.getLevelData().getGameTime() % 10 == 0 && isRunning.get()) {
			RadiationSystem.emitRadiationFromLocation(level, new Location(worldPosition), RADATION_RADIUS_BLOCKS, RADIATION_STRENGTH);
		}
	}

	static {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.5625, 0.921875, 0.26875, 1.0234375, 1.03125, 0.73125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.0625, 0, 1, 0.125, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.25, 0.75, 0.0625, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.0625, 0.0625, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0, 0.9375, 0.0625, 0.0625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.9375, 0.9375, 0.0625, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.9375, 0, 0, 1, 0.0625, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.125, 0.125, 0.875, 0.1875, 0.8715), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.125, 0.28125, 1, 0.8125, 0.71875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.234375, 0.125, 0.25, 0.75, 0.3125, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.390625, 0.875, 0.375, 0.5890625, 0.984375, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.7765625, 0.3125, 0.5625, 0.8390625, 0.34375, 0.671875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.2453125, 0.3125, 0.609375, 0.3078125, 0.34375, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.2453125, 0.3125, 0.25, 0.3078125, 0.34375, 0.390625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.7765625, 0.3125, 0.328125, 0.8390625, 0.34375, 0.4375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.7765625, 0.125, 0.5625, 0.8390625, 0.3125, 0.671875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.7765625, 0.125, 0.328125, 0.8390625, 0.3125, 0.4375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.2453125, 0.125, 0.609375, 0.3078125, 0.3125, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.2453125, 0.125, 0.25, 0.3078125, 0.3125, 0.390625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.3125, 0.5625, 0.4375, 0.4375, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5625, 0.3125, 0.5625, 0.625, 0.4375, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.3125, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5625, 0.3125, 0.375, 0.625, 0.4375, 0.4375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.34375, 0.3125, 0.46875, 0.40625, 0.4375, 0.53125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46875, 0.3125, 0.59375, 0.53125, 0.4375, 0.65625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46875, 0.3125, 0.34375, 0.53125, 0.4375, 0.40625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.3125, 0.375, 0.4375, 0.4375, 0.4375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.59375, 0.3125, 0.46875, 0.65625, 0.4375, 0.53125), BooleanOp.OR);
		VoxelShapes.registerShape(NuclearScienceBlocks.blockGasCentrifuge, shape, Direction.WEST);
	}

	@Override
	public void setNotPlaying() {
		isSoundPlaying = false;
	}

	@Override
	public boolean shouldPlaySound() {
		return spinSpeed.get() > 0;
	}

	@Override
	public int getComparatorSignal() {
		return isRunning.get() ? 15 : 0;
	}
}
