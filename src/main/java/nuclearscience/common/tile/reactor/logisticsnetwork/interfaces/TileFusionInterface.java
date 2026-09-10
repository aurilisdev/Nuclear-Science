package nuclearscience.common.tile.reactor.logisticsnetwork.interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.network.ReactorLogisticsNetwork;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.common.tile.reactor.fusion.TileFusionReactorCore;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileReactorLogisticsCable;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileSupplyModule;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentTickable;

public class TileFusionInterface extends GenericTileInterface {

    public TileFusionInterface(BlockPos worldPos, BlockState blockState) {
	super(NuclearScienceTiles.TILE_FUSIONINTERFACE.get(), worldPos, blockState);
    }

    @Override
    public void tickServer(Level level, ComponentTickable tickable) {
	super.tickServer(level, tickable);
	ReactorLogisticsNetwork network = getNetworkCable(TileReactorLogisticsCable.class)
		.map(TileReactorLogisticsCable::getNetwork).filter(ReactorLogisticsNetwork::isControllerActive)
		.orElse(null);
	if (network == null)
	    return;

	TileSupplyModule supplyModule = network.getSupplyModule(supplyModuleLocation.getValue());
	TileFusionReactorCore core = getReactor(TileFusionReactorCore.class).orElse(null);
	if (supplyModule == null || core == null)
	    return;

	ComponentInventory supplyInv = supplyModule.requireComponent(IComponentType.Inventory);

	boolean isInsertingTritium = serverAnimations.containsKey(InterfaceAnimation.FUSION_TRITIUM_INSERT);
	boolean isInsertingDeuterium = serverAnimations.containsKey(InterfaceAnimation.FUSION_DEUTERIUM_INSERT);

	boolean checkDeuterium = !core.isDeuteriumFull();
	boolean checkTritium = !core.isTritiumFull();

	if (!checkDeuterium && !checkTritium) {
	    handleServerAnimations(tickable);
	    return;
	}

	ItemStack stack;

	if (!isInsertingTritium && checkDeuterium) {

	    boolean inserted = false;

	    for (int i = 0; i < 9; i++) {
		stack = supplyInv.getItem(i);

		if (stack.isEmpty() || !stack.is(NuclearScienceTags.Items.CELL_DEUTERIUM)) {
		    continue;
		}

		int accepted = core.addDeuteriumCells(stack.getCount());

		if (accepted > 0) {
		    supplyInv.removeItem(i, accepted);
		    inserted = true;
		    isInsertingDeuterium = true;
		}

		if (core.isDeuteriumFull()) {
		    break;
		}
	    }

	    if (inserted) {
		queuedAnimations.addValue(InterfaceAnimation.FUSION_DEUTERIUM_INSERT.ordinal());
	    }
	}

	if (!isInsertingDeuterium && checkTritium) {

	    boolean inserted = false;

	    for (int i = 0; i < 9; i++) {
		stack = supplyInv.getItem(i);

		if (stack.isEmpty() || !stack.is(NuclearScienceTags.Items.CELL_TRITIUM)) {
		    continue;
		}

		int accepted = core.addTritiumCells(stack.getCount());

		if (accepted > 0) {
		    supplyInv.removeItem(i, accepted);
		    inserted = true;
		}

		if (core.isTritiumFull()) {
		    break;
		}
	    }

	    if (inserted) {
		queuedAnimations.addValue(InterfaceAnimation.FUSION_TRITIUM_INSERT.ordinal());
	    }
	}

	handleServerAnimations(tickable);

    }

    @Override
    public Direction getReactorDirection() {
	return Direction.UP;
    }

    @Override
    public InterfaceType getInterfaceType() {
	return InterfaceType.FUSION;
    }

    @Override
    public Direction getCableLocation() {
	return Direction.DOWN;
    }
}
