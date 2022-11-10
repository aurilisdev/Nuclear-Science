package nuclearscience.common.block;

import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tile.TileFusionReactorCore;
import nuclearscience.registers.NuclearScienceItems;

public class BlockFusionReactorCore extends GenericMachineBlock {

	public BlockFusionReactorCore() {
		super(TileFusionReactorCore::new);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if (!worldIn.isClientSide) {
			ItemStack inHand = player.getItemBySlot(handIn == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
			Item itemInHand = inHand.getItem();
			if (itemInHand == NuclearScienceItems.ITEM_CELLDEUTERIUM.get() || itemInHand == NuclearScienceItems.ITEM_CELLTRITIUM.get()) {
				BlockEntity tile = worldIn.getBlockEntity(pos);
				if (tile instanceof TileFusionReactorCore core) {
					boolean tritium = itemInHand == NuclearScienceItems.ITEM_CELLTRITIUM.get();
					int type = tritium ? core.tritium : core.deuterium;
					int added = Math.min(inHand.getCount(), Constants.FUSIONREACTOR_MAXSTORAGE - type);
					inHand.setCount(inHand.getCount() - added);
					if (tritium) {
						core.tritium += added;
					} else {
						core.deuterium += added;
					}
				}
				return InteractionResult.CONSUME;
			}
		}
		return super.use(state, worldIn, pos, player, handIn, hit);
	}
}
