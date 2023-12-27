package nuclearscience.common.block;

import java.util.Arrays;
import java.util.List;

import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic;
import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext.Builder;
import net.minecraft.loot.LootParameters;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nuclearscience.common.tile.TileQuantumCapacitor;

public class BlockQuantumCapacitor extends GenericMachineBlock {

	public BlockQuantumCapacitor() {
		super(world -> new TileQuantumCapacitor());
	}
	
	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {
		ItemStack addstack = new ItemStack(this);
		TileEntity tile = builder.getOptionalParameter(LootParameters.BLOCK_ENTITY);
		if (tile instanceof ICapabilityElectrodynamic) {
			ICapabilityElectrodynamic electro = (ICapabilityElectrodynamic) tile;
			double joules = electro.getJoulesStored();
			if (joules > 0) {
				addstack.getOrCreateTag().putDouble("joules", joules);
			}
		}
		if (tile instanceof TileQuantumCapacitor) {
			TileQuantumCapacitor cap = (TileQuantumCapacitor) tile;
			addstack.getOrCreateTag().putInt("frequency", cap.frequency.get());
			addstack.getOrCreateTag().putUUID("uuid", cap.uuid.get());
		}
		return Arrays.asList(addstack);
	}

	@Override
	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		TileEntity tile = worldIn.getBlockEntity(pos);
		if (tile instanceof TileQuantumCapacitor) {
			TileQuantumCapacitor cap = (TileQuantumCapacitor) tile;
			cap.frequency.set(stack.getOrCreateTag().getInt("frequency"));
			if (stack.getOrCreateTag().contains("uuid")) {
				cap.uuid.set(stack.getOrCreateTag().getUUID("uuid"));
			} else if (placer instanceof PlayerEntity) {
				PlayerEntity pl = (PlayerEntity) placer;
				cap.uuid.set(pl.getGameProfile().getId());
			}
		} else {
			super.setPlacedBy(worldIn, pos, state, placer, stack);
		}
	}
}