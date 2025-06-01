package nuclearscience.common.tile.reactor.logisticsnetwork;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import nuclearscience.common.inventory.container.ContainerControlRodModule;
import nuclearscience.common.network.ReactorLogisticsNetwork;
import nuclearscience.common.tile.reactor.TileControlRod;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.util.GenericTileInterfaceBound;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.common.block.states.VoltaicBlockStates;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicItems;

public class TileControlRodModule extends GenericTileInterfaceBound {

    private Direction relativeBack;

    public final SingleProperty<Integer> insertion = property(new SingleProperty<>(PropertyTypes.INTEGER, "insertion", 0));
    public final SingleProperty<Integer> redstoneSignal = property(new SingleProperty<>(PropertyTypes.INTEGER, "redstonesignal", 0)).onChange((prop, oldVal) -> {
        if(level == null || level.isClientSide || prop.getValue() == oldVal) {
            return;
        }

        double perc = (double) prop.getValue() / 15.0;

        double tot = perc * TileControlRod.MAX_EXTENSION;

        int mult = (int) (tot / TileControlRod.EXTENSION_PER_CLICK);

        insertion.setValue(mult * TileControlRod.EXTENSION_PER_CLICK);
    });

    public TileControlRodModule() {
        super(NuclearScienceTiles.TILE_CONTROLRODMODULE.get());
        addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
        addComponent(new ComponentContainerProvider("controlrodmodule", this).createMenu((id, player) -> new ContainerControlRodModule(id, player, new Inventory(0), getCoordsArray())));
    }
    
    @Override
    public void tickServer(ComponentTickable tickable) {
    	if(relativeBack == null) {
    		relativeBack = BlockEntityUtils.getRelativeSide(getFacing(), BlockEntityUtils.MachineDirection.BACK.mappedDir);
    	}
    	super.tickServer(tickable);
    }
    
    public void tickClient(ComponentTickable tickable) {
    	if(relativeBack == null) {
    		relativeBack = BlockEntityUtils.getRelativeSide(getFacing(), BlockEntityUtils.MachineDirection.BACK.mappedDir);
    	}
    }

    @Override
    public boolean checkLinkedPosition(GenericTileInterface inter) {
        return inter.controlRodLocation.getValue().equals(getBlockPos());
    }

    @Override
    public Direction getCableLocation() {
        return relativeBack;
    }

    @Override
    public void onBlockStateUpdate(BlockState oldState, BlockState newState) {
        super.onBlockStateUpdate(oldState, newState);
        if (!level.isClientSide() && oldState.hasProperty(VoltaicBlockStates.FACING) && newState.hasProperty(VoltaicBlockStates.FACING) && oldState.getValue(VoltaicBlockStates.FACING) != newState.getValue(VoltaicBlockStates.FACING)) {
            relativeBack = BlockEntityUtils.getRelativeSide(getFacing(), BlockEntityUtils.MachineDirection.BACK.mappedDir);
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        compound.putInt("relativeback", relativeBack.ordinal());
        return super.save(compound);
    }

    @Override
	public void load(BlockState state, CompoundNBT compound) {
        super.load(state, compound);
        relativeBack = Direction.values()[compound.getInt("relativeback")];
    }

    @Override
    public void onNeightborChanged(BlockPos neighbor, boolean blockStateTrigger) {
        super.onNeightborChanged(neighbor, blockStateTrigger);
        if (!level.isClientSide) {
            redstoneSignal.setValue(getLevel().getBestNeighborSignal(getBlockPos()));
        }
    }

    @Override
    public void onBlockDestroyed() {
        super.onBlockDestroyed();
        if(!level.isClientSide()) {

            if (!networkCable.valid() || !(networkCable.getSafe() instanceof TileReactorLogisticsCable)) {
                return;
            }

            TileReactorLogisticsCable cable = networkCable.getSafe();

            if (cable.isRemoved()) {
                return;
            }

            ReactorLogisticsNetwork network = cable.getNetwork();

            GenericTileInterface inter = network.getInterface(interfaceLocation.getValue());

            if (inter == null) {
                return;
            }

            inter.controlRodLocation.setValue(BlockEntityUtils.OUT_OF_REACH);
        }
    }

    @Override
    public GenericTileInterface.InterfaceType[] getValidInterfaces() {
        return CONTROL_RODS;
    }
    
    @Override
    public ActionResultType use(PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
    	if (player.getItemInHand(hand).getItem() == VoltaicItems.ITEM_WRENCH.get()) {
            if (this.hasComponent(IComponentType.ContainerProvider)) {
                if (!this.level.isClientSide) {
                    player.openMenu(this.getComponent(IComponentType.ContainerProvider));
                    player.awardStat(Stats.INTERACT_WITH_FURNACE);
                }

                return ActionResultType.CONSUME;
            }
        }
    	if (level.isClientSide()) {
            return ActionResultType.CONSUME;
        }

        if (player.isShiftKeyDown()) {
            insertion.setValue(insertion.getValue() - TileControlRod.TileFissionControlRod.EXTENSION_PER_CLICK);
            if (insertion.getValue() < 0) {
                insertion.setValue(TileControlRod.TileFissionControlRod.MAX_EXTENSION);
            }
        } else {
            insertion.setValue(insertion.getValue() + TileControlRod.TileFissionControlRod.EXTENSION_PER_CLICK);
            if (insertion.getValue() > TileControlRod.TileFissionControlRod.MAX_EXTENSION) {
                insertion.setValue(0);
            }
        }

        return ActionResultType.CONSUME;
    }

    @Override
    public int getComparatorSignal() {
        return (int) (((double) insertion.getValue() / (double) TileControlRod.MAX_EXTENSION) * 15);
    }

    @Override
    public void onInterfacePropChange(SingleProperty<BlockPos> prop, BlockPos old) {

        super.onInterfacePropChange(prop, old);

        boolean oldInval = old.equals(BlockEntityUtils.OUT_OF_REACH);
        boolean newInval = prop.getValue().equals(BlockEntityUtils.OUT_OF_REACH);

        if(oldInval && newInval) {
            return;
        }

        if (networkCable == null || !networkCable.valid() || !(networkCable.getSafe() instanceof TileReactorLogisticsCable)) {
            return;
        }

        TileReactorLogisticsCable cable = networkCable.getSafe();

        if (cable.isRemoved()) {
            return;
        }

        ReactorLogisticsNetwork network = cable.getNetwork();

        if(oldInval && !newInval) {
            GenericTileInterface inter = network.getInterface(prop.getValue());

            if(inter != null) {
                inter.controlRodLocation.setValue(getBlockPos());
            }
        } else if (!oldInval && newInval) {
            GenericTileInterface inter = network.getInterface(old);

            if(inter != null) {
                inter.controlRodLocation.setValue(BlockEntityUtils.OUT_OF_REACH);
            }
        }

    }
}
