package nuclearscience.common.block;

import java.util.Arrays;
import java.util.List;

import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic;
import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import nuclearscience.common.tile.TileQuantumCapacitor;

public class BlockQuantumCapacitor extends GenericMachineBlock {

	public BlockQuantumCapacitor() {
		super(TileQuantumCapacitor::new);
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {
		ItemStack addstack = new ItemStack(this);
		BlockEntity tile = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
		if (tile instanceof ICapabilityElectrodynamic electro) {
			double joules = electro.getJoulesStored();
			if (joules > 0) {
				addstack.getOrCreateTag().putDouble("joules", joules);
			}
		}
		if (tile instanceof TileQuantumCapacitor cap) {
			addstack.getOrCreateTag().putInt("frequency", cap.frequency.get());
			addstack.getOrCreateTag().putUUID("uuid", cap.uuid.get());
		}
		return Arrays.asList(addstack);
	}

	@Override
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		BlockEntity tile = worldIn.getBlockEntity(pos);
		if (tile instanceof TileQuantumCapacitor cap) {
			cap.frequency.set(stack.getOrCreateTag().getInt("frequency"));
			if (stack.getOrCreateTag().contains("uuid")) {
				cap.uuid.set(stack.getOrCreateTag().getUUID("uuid"));
			} else if (placer instanceof Player pl) {
				cap.uuid.set(pl.getGameProfile().getId());
			}
		} else {
			super.setPlacedBy(worldIn, pos, state, placer, stack);
		}
	}
}
