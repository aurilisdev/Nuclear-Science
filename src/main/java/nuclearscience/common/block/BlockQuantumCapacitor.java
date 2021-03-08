package nuclearscience.common.block;

import java.util.Arrays;
import java.util.List;

import electrodynamics.api.tile.electric.IElectrodynamic;
import electrodynamics.common.block.BlockGenericMachine;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext.Builder;
import net.minecraft.loot.LootParameters;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import nuclearscience.common.tile.TileQuantumCapacitor;

public class BlockQuantumCapacitor extends BlockGenericMachine {
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
	return new TileQuantumCapacitor();
    }

    @Override
    @Deprecated
    public List<ItemStack> getDrops(BlockState state, Builder builder) {
	ItemStack addstack = new ItemStack(this);
	TileEntity tile = builder.get(LootParameters.BLOCK_ENTITY);
	if (tile instanceof IElectrodynamic) {
	    double joules = ((IElectrodynamic) tile).getJoulesStored();
	    if (joules > 0) {
		addstack.getOrCreateTag().putDouble("joules", joules);
	    }
	}
	if (tile instanceof TileQuantumCapacitor) {
	    addstack.getOrCreateTag().putInt("frequency", ((TileQuantumCapacitor) tile).frequency);
	    addstack.getOrCreateTag().putUniqueId("uuid", ((TileQuantumCapacitor) tile).uuid);
	}
	return Arrays.asList(addstack);
    }

    @Override
    @Deprecated
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
	TileEntity tile = worldIn.getTileEntity(pos);
	if (tile instanceof TileQuantumCapacitor) {
	    ((TileQuantumCapacitor) tile).frequency = stack.getOrCreateTag().getInt("frequency");
	    if (stack.getOrCreateTag().contains("uuid")) {
		((TileQuantumCapacitor) tile).uuid = stack.getOrCreateTag().getUniqueId("uuid");
	    } else if (placer instanceof PlayerEntity) {
		((TileQuantumCapacitor) tile).uuid = ((PlayerEntity) placer).getGameProfile().getId();
	    }
	} else {
	    super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
    }
}
