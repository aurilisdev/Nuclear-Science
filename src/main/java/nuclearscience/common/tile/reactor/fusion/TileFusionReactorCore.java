package nuclearscience.common.tile.reactor.fusion;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileFusionReactorCore extends GenericTile {

    public final SingleProperty<Integer> deuterium = property(new SingleProperty<>(PropertyTypes.INTEGER, "deuterium", 0));
    public final SingleProperty<Integer> tritium = property(new SingleProperty<>(PropertyTypes.INTEGER, "tritium", 0));
    public final SingleProperty<Integer> timeLeft = property(new SingleProperty<>(PropertyTypes.INTEGER, "timeleft", 0));

    public TileFusionReactorCore() {
        super(NuclearScienceTiles.TILE_FUSIONREACTORCORE.get());

        addComponent(new ComponentTickable(this).tickServer(this::tickServer));
        addComponent(new ComponentPacketHandler(this));
        addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(BlockEntityUtils.MachineDirection.TOP, BlockEntityUtils.MachineDirection.BOTTOM).maxJoules(NuclearConstants.FUSIONREACTOR_USAGE_PER_TICK * 20.0).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 4));
    }

    public void tickServer(ComponentTickable tick) {
        ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

        if (tritium.getValue() > 0 && deuterium.getValue() > 0 && timeLeft.getValue() <= 0 && electro.getJoulesStored() > NuclearConstants.FUSIONREACTOR_USAGE_PER_TICK) {
            deuterium.setValue(deuterium.getValue() - 1);
            tritium.setValue(tritium.getValue() - 1);
            timeLeft.setValue(15 * 20);
        }

        if (timeLeft.getValue() <= 0) {
            if (BlockEntityUtils.isLit(this)) {
                BlockEntityUtils.updateLit(this, false);
            }
            return;
        }
        if (!BlockEntityUtils.isLit(this)) {
            BlockEntityUtils.updateLit(this, true);
        }
        timeLeft.setValue(timeLeft.getValue() - 1);

        if (electro.getJoulesStored() < NuclearConstants.FUSIONREACTOR_USAGE_PER_TICK) {
            return;
        }

        BlockPos offset;
        BlockState offsetState;

        for (Direction dir : Direction.Plane.HORIZONTAL) {
            offset = worldPosition.relative(dir);
            offsetState = level.getBlockState(offset);
            if (offsetState.getBlock() == NuclearScienceBlocks.BLOCK_PLASMA.get()) {
                TileEntity tile = level.getBlockEntity(offset);
                if (tile instanceof TilePlasma && ((TilePlasma) tile).ticksExisted.getValue() > 30) {
                    ((TilePlasma) tile).ticksExisted.setValue(0);
                }
            } else if (offsetState.isAir(level, offset)) {
                level.setBlockAndUpdate(offset, NuclearScienceBlocks.BLOCK_PLASMA.get().defaultBlockState());
            }
        }
        electro.joules(electro.getJoulesStored() - NuclearConstants.FUSIONREACTOR_USAGE_PER_TICK);
    }
    
    @Override
    public ActionResultType use(PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
    	ItemStack inHand = player.getItemInHand(hand);

        int accepted = 0;

        if (inHand.getItem().is(NuclearScienceTags.Items.CELL_DEUTERIUM)) {
            accepted = addDeuteriumCells(inHand.getCount());
        } else if (inHand.getItem().is(NuclearScienceTags.Items.CELL_TRITIUM)) {
            accepted = addTritiumCells(inHand.getCount());
        }

        if(accepted > 0) {

            if(!level.isClientSide()) {
                inHand.setCount(inHand.getCount() - accepted);
            }

            return ActionResultType.CONSUME;
        }
    	return super.use(player, hand, hit);
    }

    public int addDeuteriumCells(int count) {
        return addCell(deuterium, count);
    }

    public int addTritiumCells(int count) {
        return addCell(tritium, count);
    }

    private int addCell(SingleProperty<Integer> property, int count) {

        if (property.getValue() >= NuclearConstants.FUSIONREACTOR_MAXSTORAGE) {
            return 0;
        }

        int added = Math.min(count, NuclearConstants.FUSIONREACTOR_MAXSTORAGE - property.getValue());

        if (!level.isClientSide()) {
            property.setValue(property.getValue() + added);
        }

        return added;

    }

    public boolean isDeuteriumFull() {
        return deuterium.getValue() >= NuclearConstants.FUSIONREACTOR_MAXSTORAGE;
    }

    public boolean isTritiumFull() {
        return tritium.getValue() >= NuclearConstants.FUSIONREACTOR_MAXSTORAGE;
    }

}
