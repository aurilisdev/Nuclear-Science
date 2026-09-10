package nuclearscience.common.tile.reactor.logisticsnetwork.interfaces;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.tile.reactor.logisticsnetwork.util.GenericTileLogisticsMember;
import nuclearscience.registers.NuclearScienceItems;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SetProperty;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;

public abstract class GenericTileInterface extends GenericTileLogisticsMember {

    public final SetProperty<Integer> queuedAnimations = property(
	    new SetProperty<>(getPropertyManager(), PropertyTypes.INTEGER_SET, "queuedanimations", new HashSet<>()));

    public final SingleProperty<BlockPos> controlRodLocation = property(new SingleProperty<>(getPropertyManager(),
	    PropertyTypes.BLOCK_POS, "controlrodlocation", BlockEntityUtils.OUT_OF_REACH));

    public final SingleProperty<BlockPos> supplyModuleLocation = property(new SingleProperty<>(getPropertyManager(),
	    PropertyTypes.BLOCK_POS, "supplymodulelocation", BlockEntityUtils.OUT_OF_REACH));

    public final HashMap<InterfaceAnimation, Long> clientAnimations = new HashMap<>();

    // Tracks server activity without rendering.
    protected final HashMap<InterfaceAnimation, Long> serverAnimations = new HashMap<>();

    public GenericTileInterface(BlockEntityType<?> tileEntityTypeIn, BlockPos worldPos, BlockState blockState) {
	super(tileEntityTypeIn, worldPos, blockState);
	addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
    }

    @Override
    public void tickServer(Level level, ComponentTickable tickable) {
	super.tickServer(level, tickable);
	queuedAnimations.wipeSet();
    }

    private void tickClient(Level level, ComponentTickable tickable) {
	updateAnimations(clientAnimations, tickable.getTicks());
    }

    public Optional<BlockEntity> getReactor() {
	Level level = getLevel();
	if (level == null) {
	    return Optional.empty();
	}

	BlockPos reactorPos = getBlockPos().relative(getReactorDirection());

	return Optional.ofNullable(level.getBlockEntity(reactorPos)).filter(reactor -> !reactor.isRemoved());
    }

    public <T extends BlockEntity> Optional<T> getReactor(Class<T> reactorClass) {
	return getReactor().filter(reactorClass::isInstance).map(reactorClass::cast);
    }

    public abstract Direction getReactorDirection();

    public abstract InterfaceType getInterfaceType();

    protected void handleServerAnimations(ComponentTickable tickable) {
	updateAnimations(serverAnimations, tickable.getTicks());
    }

    private void updateAnimations(Map<InterfaceAnimation, Long> animations, long currTime) {
	InterfaceAnimation[] values = InterfaceAnimation.values();

	for (int index : queuedAnimations.getValue()) {
	    animations.putIfAbsent(values[index], currTime);
	}

	animations.entrySet().removeIf(entry -> currTime - entry.getValue() > entry.getKey().animationTime);
    }

    public enum InterfaceType {
	NONE,
	FISSION,
	MS,
	FUSION;
    }

    public enum InterfaceAnimation {
	FISSION_WASTE_1(80),
	FISSION_WASTE_2(80),
	FISSION_WASTE_3(80),
	FISSION_WASTE_4(80),
	FISSION_TRITIUM_EXTRACT(80),
	FISSION_FUEL_1(80),
	FISSION_FUEL_2(80),
	FISSION_FUEL_3(80),
	FISSION_FUEL_4(80),
	FISSION_DEUTERIUM_INSERT(80),
	FUSION_DEUTERIUM_INSERT(80),
	FUSION_TRITIUM_INSERT(80);

	public final int animationTime;

	private InterfaceAnimation(int timeTicks) {
	    animationTime = timeTicks;
	}
    }

    public static ItemStack getItemFromType(InterfaceType type) {
	return switch (type) {
	case NONE -> ItemStack.EMPTY;
	case FISSION ->
	    new ItemStack(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissionreactorcore));
	case MS ->
	    new ItemStack(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msreactorcore));
	case FUSION ->
	    new ItemStack(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusionreactorcore));
	};
    }
}