package nuclearscience.common.tile.reactor.moltensalt;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.inventory.container.ContainerMoltenSaltSupplier;
import nuclearscience.common.settings.NuclearConfig;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.RadiationUtils;
import voltaic.prefab.utilities.object.CachedTileOutput;
import voltaic.prefab.utilities.object.TransferPack;

public class TileMoltenSaltSupplier extends GenericTile {

    public static final double AMT_PER_SALT = 250;
    public static final double AMT_PER_WASTE = 300;

    protected CachedTileOutput output;

    public final SingleProperty<Double> reactorWaste = property(
	    new SingleProperty<>(PropertyTypes.DOUBLE, "reactorwaste", 0.0).setNoSave());

    public TileMoltenSaltSupplier(BlockPos pos, BlockState state) {

	super(NuclearScienceTiles.TILE_MOLTENSALTSUPPLIER.get(), pos, state);

	addComponent(new ComponentTickable(this).tickServer(this::tickServer));
	addComponent(new ComponentPacketHandler(this));
	addComponent(new ComponentElectrodynamic(this, false, true)
		.voltage(NuclearConfig.INSTANCE.MOLTENSALTSUPPLIER_VOLTAGE.get())
		.extractPower((x, y) -> TransferPack.EMPTY).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM)
		.maxJoules(NuclearConfig.INSTANCE.MOLTENSALTSUPPLIER_USAGE_PER_TICK.get() * 20));
	addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().inputs(1).outputs(1))
		.setDirectionsBySlot(0, BlockEntityUtils.MachineDirection.FRONT, BlockEntityUtils.MachineDirection.TOP)
		.setDirectionsBySlot(1, BlockEntityUtils.MachineDirection.LEFT, BlockEntityUtils.MachineDirection.RIGHT)
		.valid((slot, stack, i) -> stack.getItem() == NuclearScienceItems.ITEM_LIFHT4PUF3.get()));
	addComponent(new ComponentContainerProvider("moltensaltsupplier", this)
		.createMenu((id, player) -> new ContainerMoltenSaltSupplier(id, player,
			getComponent(IComponentType.Inventory), getCoordsArray())));
    }

    public void tickServer(ComponentTickable tickable) {
	Direction dir = getFacing();
	if (output == null) {
	    output = new CachedTileOutput(level, worldPosition.relative(dir.getOpposite()));
	}

	ComponentInventory inv = getComponent(IComponentType.Inventory);

	RadiationUtils.handleRadioactiveItems(this, inv,
		NuclearConfig.INSTANCE.MOLTEN_SAL_SUPPLIER_RADIATION_RADIUS.get(), true, 30, true, false);

	ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);
	boolean enoughPower = electro.getJoulesStored() >= NuclearConfig.INSTANCE.MOLTENSALTSUPPLIER_USAGE_PER_TICK
		.get();
	if (!enoughPower) {
	    return;
	}

	if (BlockEntityUtils.isLit(this) ^ enoughPower) {
	    BlockEntityUtils.updateLit(this, enoughPower);
	}

	electro.joules(electro.getJoulesStored() - NuclearConfig.INSTANCE.MOLTENSALTSUPPLIER_USAGE_PER_TICK.get());

	if (tickable.getTicks() % 40 == 0) {
	    output.update(worldPosition.relative(dir.getOpposite()));
	}

	ItemStack fuel = inv.getItem(0);

	if (!output.valid() || !(output.getSafe() instanceof TileMSReactorCore)) {
	    reactorWaste.setValue(0.0);
	    return;
	}

	TileMSReactorCore core = output.getSafe();
	reactorWaste.setValue(core.currentWaste.getValue());

	if (fuel.isEmpty() || (core.getFacing() != dir)) {
	    return;
	}

	if (TileMSReactorCore.FUEL_CAPACITY - core.currentFuel.getValue() >= AMT_PER_SALT) {
	    fuel.shrink(1);
	    core.currentFuel.setValue(core.currentFuel.getValue() + AMT_PER_SALT);
	}

	if (core.currentWaste.getValue() < AMT_PER_WASTE) {
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

	core.currentWaste.setValue(core.currentWaste.getValue() - AMT_PER_WASTE);

    }

}
