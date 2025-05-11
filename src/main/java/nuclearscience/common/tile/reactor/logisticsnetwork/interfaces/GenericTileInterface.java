package nuclearscience.common.tile.reactor.logisticsnetwork.interfaces;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.tile.reactor.logisticsnetwork.util.GenericTileLogisticsMember;
import nuclearscience.registers.NuclearScienceItems;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SetProperty;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.object.CachedTileOutput;

public abstract class GenericTileInterface extends GenericTileLogisticsMember {

	public CachedTileOutput reactor;

	public final SetProperty<Integer> queuedAnimations = property(new SetProperty<>(PropertyTypes.INTEGER_SET, "queuedanimations", new HashSet<>()));

	public final SingleProperty<BlockPos> controlRodLocation = property(new SingleProperty<>(PropertyTypes.BLOCK_POS, "controlrodlocation", BlockEntityUtils.OUT_OF_REACH));
	public final SingleProperty<BlockPos> supplyModuleLocation = property(new SingleProperty<>(PropertyTypes.BLOCK_POS, "supplymodulelocation", BlockEntityUtils.OUT_OF_REACH));

	public final HashMap<InterfaceAnimation, Long> clientAnimations = new HashMap<>();
	// Nothing is rendered with this map; it is used to keep track of what the interface is doing only
	protected final HashMap<InterfaceAnimation, Long> serverAnimations = new HashMap<>();

	public GenericTileInterface(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
	}

	@Override
	public void tickServer(ComponentTickable tickable) {

		super.tickServer(tickable);

		queuedAnimations.wipeSet();

		if (reactor == null) {
			reactor = new CachedTileOutput(getLevel(), getBlockPos().relative(getReactorDirection()));
		}

		if (tickable.getTicks() % 20 == 0 && !reactor.valid()) {

			reactor.update(getBlockPos().relative(getReactorDirection()));

		}
	}

	private void tickClient(ComponentTickable tickable) {

		if (reactor == null) {
			reactor = new CachedTileOutput(getLevel(), getBlockPos().relative(getReactorDirection()));
		}

		if (tickable.getTicks() % 20 == 0 && !reactor.valid()) {

			reactor.update(getBlockPos().relative(getReactorDirection()));

		}

		long currTime = tickable.getTicks();

		queuedAnimations.getValue().forEach(val -> {

			InterfaceAnimation animation = InterfaceAnimation.values()[val];

			if (!clientAnimations.containsKey(animation)) {
				clientAnimations.put(animation, currTime);
			}

		});

		Iterator<Map.Entry<InterfaceAnimation, Long>> it = clientAnimations.entrySet().iterator();

		Map.Entry<InterfaceAnimation, Long> entry;

		while (it.hasNext()) {
			entry = it.next();

			if (currTime - entry.getValue() > entry.getKey().animationTime) {
				it.remove();
			}

		}

	}

	public abstract Direction getReactorDirection();

	public abstract InterfaceType getInterfaceType();

	protected void handleServerAnimations(ComponentTickable tickable) {
		long currTime = tickable.getTicks();

		queuedAnimations.getValue().forEach(val -> {

			InterfaceAnimation animation = InterfaceAnimation.values()[val];

			if (!serverAnimations.containsKey(animation)) {
				serverAnimations.put(animation, currTime);
			}

		});

		Iterator<Map.Entry<InterfaceAnimation, Long>> it = serverAnimations.entrySet().iterator();

		Map.Entry<InterfaceAnimation, Long> entry;

		while (it.hasNext()) {
			entry = it.next();

			if (currTime - entry.getValue() > entry.getKey().animationTime) {
				it.remove();
			}

		}
	}

	public static enum InterfaceType {
		NONE, FISSION, MS, FUSION;
	}

	public static enum InterfaceAnimation {

		FISSION_WASTE_1(80), //
		FISSION_WASTE_2(80), //
		FISSION_WASTE_3(80), //
		FISSION_WASTE_4(80), //
		FISSION_TRITIUM_EXTRACT(80), //
		FISSION_FUEL_1(80), //
		FISSION_FUEL_2(80), //
		FISSION_FUEL_3(80), //
		FISSION_FUEL_4(80), //
		FISSION_DEUTERIUM_INSERT(80), //
		FUSION_DEUTERIUM_INSERT(80), //
		FUSION_TRITIUM_INSERT(80)//
		;

		public final int animationTime;

		private InterfaceAnimation(int timeTicks) {
			animationTime = timeTicks;
		}

	}

	public static ItemStack getItemFromType(InterfaceType type) {
		switch (type) {
		case NONE:
			return ItemStack.EMPTY;
		case FISSION:
			return new ItemStack(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissionreactorcore));
		case MS:
			return new ItemStack(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msreactorcore));
		case FUSION:
			return new ItemStack(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusionreactorcore));
		}
		return ItemStack.EMPTY;
	}

}
