package nuclearscience.common.tile.msreactor;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.block.VoxelShapes;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import nuclearscience.common.inventory.container.ContainerFreezePlug;
import nuclearscience.common.settings.Constants;
import nuclearscience.registers.NuclearScienceBlockTypes;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceItems;

public class TileFreezePlug extends GenericTile {

	public final Property<Boolean> isFrozen = property(new Property<>(PropertyType.Boolean, "isfrozen", false));
	public final Property<Double> saltBonus = property(new Property<>(PropertyType.Double, "saltbonus", 1.0));

	public TileFreezePlug(BlockPos pos, BlockState state) {
		super(NuclearScienceBlockTypes.TILE_FREEZEPLUG.get(), pos, state);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, false, true).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE).extractPower((x, y) -> TransferPack.EMPTY).setInputDirections(Direction.DOWN).maxJoules(Constants.FREEZEPLUG_USAGE_PER_TICK * 20));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().inputs(1)).valid((slot, stack, i) -> stack.getItem() == NuclearScienceItems.ITEM_FLINAK.get()));
		addComponent(new ComponentContainerProvider("container.freezeplug", this).createMenu((id, player) -> new ContainerFreezePlug(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	public void tickServer(ComponentTickable tickable) {
		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);
		ComponentInventory inv = getComponent(IComponentType.Inventory);

		ItemStack stack = inv.getItem(0);

		if (stack.isEmpty()) {
			isFrozen.set(false);
			saltBonus.set(0);
			return;
		}

		if (electro.getJoulesStored() < Constants.FREEZEPLUG_USAGE_PER_TICK) {
			isFrozen.set(false);
			saltBonus.set(0);
			return;
		}

		electro.joules(electro.getJoulesStored() - Constants.FREEZEPLUG_USAGE_PER_TICK);

		isFrozen.set(true);

		double bonus = 1.0 + ((stack.getCount() - 1) / 63.0);

		saltBonus.set(bonus);

	}

	public boolean isFrozen() {
		return isFrozen.get();
	}

	public double getSaltBonus() {
		return saltBonus.get();
	}

	static {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.25, 0.15625, 0.25, 0.75, 0.28125, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.25, 0.3125, 0.6875, 0.8125, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.9375, 0.8125, 0.875, 1, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.9375, 0.125, 0.875, 1, 0.1875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.875, 0.75, 0.8125, 0.9375, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.8125, 0.25, 0.75, 0.875, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.875, 0.1875, 0.8125, 0.9375, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.875, 0.25, 0.25, 0.9375, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.875, 0.25, 0.8125, 0.9375, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.8125, 0.9375, 0.1875, 0.875, 1, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.9375, 0.1875, 0.1875, 1, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.296875, 0.625, 0.296875, 0.703125, 0.6875, 0.703125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.28125, 0.5, 0.28125, 0.71875, 0.5625, 0.71875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.296875, 0.375, 0.296875, 0.703125, 0.4375, 0.703125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.078125, 0, 0.296875, 0.15625, 0.3125, 0.390625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.078125, 0, 0.453125, 0.15625, 0.3125, 0.546875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.078125, 0, 0.609375, 0.15625, 0.3125, 0.703125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.296875, 0, 0.84375, 0.390625, 0.3125, 0.921875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.453125, 0, 0.84375, 0.546875, 0.3125, 0.921875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.609375, 0, 0.84375, 0.703125, 0.3125, 0.921875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.84375, 0, 0.609375, 0.921875, 0.3125, 0.703125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.84375, 0, 0.453125, 0.921875, 0.3125, 0.546875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.84375, 0, 0.296875, 0.921875, 0.3125, 0.390625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.609375, 0, 0.078125, 0.703125, 0.3125, 0.15625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.453125, 0, 0.078125, 0.546875, 0.3125, 0.15625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.296875, 0, 0.078125, 0.390625, 0.3125, 0.15625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.625, 0.1875, 0.15625, 0.6875, 0.25, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46875, 0.1875, 0.15625, 0.53125, 0.25, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.1875, 0.15625, 0.375, 0.25, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.15625, 0.1875, 0.3125, 0.25, 0.25, 0.375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.15625, 0.1875, 0.46875, 0.25, 0.25, 0.53125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.15625, 0.1875, 0.625, 0.25, 0.25, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.1875, 0.75, 0.375, 0.25, 0.84375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46875, 0.1875, 0.75, 0.53125, 0.25, 0.84375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.625, 0.1875, 0.75, 0.6875, 0.25, 0.84375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.1875, 0.625, 0.84375, 0.25, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.1875, 0.46875, 0.84375, 0.25, 0.53125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.1875, 0.3125, 0.84375, 0.25, 0.375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0, 0.3125, 0.6875, 0.1875, 0.6875), BooleanOp.OR);

		VoxelShapes.registerShape(NuclearScienceBlocks.blockFreezePlug, shape, Direction.EAST);
	}
}
