package nuclearscience.common.block;

import java.util.Arrays;
import java.util.List;

import electrodynamics.api.electricity.IElectrodynamic;
import electrodynamics.common.block.BlockGenericMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import nuclearscience.common.tile.TileQuantumCapacitor;

public class BlockQuantumCapacitor extends BlockGenericMachine {
    @Override
    public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
	return new TileQuantumCapacitor();
    }

    @Override
    @Deprecated
    public List<ItemStack> getDrops(BlockState state, Builder builder) {
	ItemStack addstack = new ItemStack(this);
	BlockEntity tile = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
	if (tile instanceof IElectrodynamic) {
	    double joules = ((IElectrodynamic) tile).getJoulesStored();
	    if (joules > 0) {
		addstack.getOrCreateTag().putDouble("joules", joules);
	    }
	}
	if (tile instanceof TileQuantumCapacitor) {
	    addstack.getOrCreateTag().putInt("frequency", ((TileQuantumCapacitor) tile).frequency);
	    addstack.getOrCreateTag().putUUID("uuid", ((TileQuantumCapacitor) tile).uuid);
	}
	return Arrays.asList(addstack);
    }

    @Override
    @Deprecated
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
	BlockEntity tile = worldIn.getBlockEntity(pos);
	if (tile instanceof TileQuantumCapacitor) {
	    ((TileQuantumCapacitor) tile).frequency = stack.getOrCreateTag().getInt("frequency");
	    if (stack.getOrCreateTag().contains("uuid")) {
		((TileQuantumCapacitor) tile).uuid = stack.getOrCreateTag().getUUID("uuid");
	    } else if (placer instanceof Player) {
		((TileQuantumCapacitor) tile).uuid = ((Player) placer).getGameProfile().getId();
	    }
	} else {
	    super.setPlacedBy(worldIn, pos, state, placer, stack);
	}
    }
}
