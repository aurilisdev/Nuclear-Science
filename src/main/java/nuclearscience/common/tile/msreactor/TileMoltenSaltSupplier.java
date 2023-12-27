package nuclearscience.common.tile.msreactor;

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
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import nuclearscience.common.inventory.container.ContainerMoltenSaltSupplier;
import nuclearscience.common.settings.Constants;
import nuclearscience.registers.NuclearScienceBlockTypes;
import nuclearscience.registers.NuclearScienceItems;

public class TileMoltenSaltSupplier extends GenericTile {

	public static final double AMT_PER_SALT = 250;
	public static final double AMT_PER_WASTE = 300;

	protected CachedTileOutput output;

	public final Property<Double> reactorWaste = property(new Property<>(PropertyType.Double, "reactorwaste", 0.0).setNoSave());

	public TileMoltenSaltSupplier() {

		super(NuclearScienceBlockTypes.TILE_MOLTENSALTSUPPLIER.get());

		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, false, true).voltage(Constants.MOLTENSALTSUPPLIER_VOLTAGE).extractPower((x, y) -> TransferPack.EMPTY).setInputDirections(Direction.DOWN).maxJoules(Constants.MOLTENSALTSUPPLIER_USAGE_PER_TICK * 20));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().inputs(1).outputs(1)).setDirectionsBySlot(0, Direction.NORTH, Direction.UP).setDirectionsBySlot(1, Direction.EAST, Direction.WEST).valid((slot, stack, i) -> stack.getItem() == NuclearScienceItems.ITEM_LIFHT4PUF3.get()));
		addComponent(new ComponentContainerProvider("container.moltensaltsupplier", this).createMenu((id, player) -> new ContainerMoltenSaltSupplier(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));

		reactorWaste.setNoSave();
	}

	public void tickServer(ComponentTickable tickable) {
		Direction dir = getFacing();
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(dir.getOpposite()));
		}
		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);
		boolean enoughPower = electro.getJoulesStored() >= Constants.MOLTENSALTSUPPLIER_USAGE_PER_TICK;
		if (!enoughPower) {
			return;
		}

		if (BlockEntityUtils.isLit(this) ^ enoughPower) {
			BlockEntityUtils.updateLit(this, enoughPower);
		}

		electro.joules(electro.getJoulesStored() - Constants.MOLTENSALTSUPPLIER_USAGE_PER_TICK);

		if (tickable.getTicks() % 40 == 0) {
			output.update(worldPosition.relative(dir.getOpposite()));
		}

		ComponentInventory inv = getComponent(IComponentType.Inventory);

		ItemStack fuel = inv.getItem(0);

		if (!output.valid() || !(output.getSafe() instanceof TileMSReactorCore)) {
			reactorWaste.set(0);
			return;
		}

		TileMSReactorCore core = output.getSafe();
		reactorWaste.set(core.currentWaste.get());

		if (fuel.isEmpty()) {
			return;
		}

		if (core.getFacing() != dir) {
			return;
		}

		if (TileMSReactorCore.FUEL_CAPACITY - core.currentFuel.get() >= AMT_PER_SALT) {
			fuel.shrink(1);
			core.currentFuel.set(core.currentFuel.get() + AMT_PER_SALT);
		}

		if (core.currentWaste.get() < AMT_PER_WASTE) {
			return;
		}

		ItemStack waste = inv.getItem(1);

		if (waste.getCount() >= waste.getMaxStackSize()) {
			return;
		}

		if (waste.isEmpty()) {
			inv.setItem(1, new ItemStack(NuclearScienceItems.ITEM_FISSILE_SALT.get()));
		} else {
			waste.grow(1);
			inv.setItem(1, waste);
		}

		core.currentWaste.set(core.currentWaste.get() - AMT_PER_WASTE);

	}

}