package nuclearscience.common.tile.fusionreactor;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import nuclearscience.common.settings.Constants;
import nuclearscience.registers.NuclearScienceBlockTypes;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceItems;

public class TileFusionReactorCore extends GenericTile {

	public final Property<Integer> deuterium = property(new Property<>(PropertyType.Integer, "deuterium", 0));
	public final Property<Integer> tritium = property(new Property<>(PropertyType.Integer, "tritium", 0));
	public final Property<Integer> timeLeft = property(new Property<>(PropertyType.Integer, "timeleft", 0));

	public TileFusionReactorCore() {
		super(NuclearScienceBlockTypes.TILE_FUSIONREACTORCORE.get());

		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(Direction.DOWN, Direction.UP).maxJoules(Constants.FUSIONREACTOR_USAGE_PER_TICK * 20.0).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 4));
	}

	public void tickServer(ComponentTickable tick) {
		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

		if (tritium.get() > 0 && deuterium.get() > 0 && timeLeft.get() <= 0 && electro.getJoulesStored() > Constants.FUSIONREACTOR_USAGE_PER_TICK) {
			deuterium.set(deuterium.get() - 1);
			tritium.set(tritium.get() - 1);
			timeLeft.set(15 * 20);
		}

		if (timeLeft.get() <= 0) {
			return;
		}

		timeLeft.set(timeLeft.get() - 1);

		if (electro.getJoulesStored() < Constants.FUSIONREACTOR_USAGE_PER_TICK) {
			return;
		}

		for (Direction dir : Direction.Plane.HORIZONTAL) {
			BlockPos offset = worldPosition.relative(dir);
			BlockState state = level.getBlockState(offset);
			if (state.getBlock() == NuclearScienceBlocks.blockPlasma) {
				TileEntity tile = level.getBlockEntity(offset);
				if (tile instanceof TilePlasma) {
					TilePlasma plasma = (TilePlasma) tile;
					
					if(plasma.ticksExisted.get() > 30) {
						plasma.ticksExisted.set(0);
					}
					
					
				}
			} else if (state.isAir(level, offset)) {
				level.setBlockAndUpdate(offset, NuclearScienceBlocks.blockPlasma.defaultBlockState());
			}
		}
		electro.joules(electro.getJoulesStored() - Constants.FUSIONREACTOR_USAGE_PER_TICK);
	}

	@Override
	public ActionResultType use(PlayerEntity player, Hand hand, BlockRayTraceResult result) {

		ItemStack inHand = player.getItemInHand(hand);

		Item itemInHand = inHand.getItem();

		if (itemInHand == NuclearScienceItems.ITEM_CELLDEUTERIUM.get() || itemInHand == NuclearScienceItems.ITEM_CELLTRITIUM.get()) {

			boolean isTritium = itemInHand == NuclearScienceItems.ITEM_CELLTRITIUM.get();

			int count = isTritium ? tritium.get() : deuterium.get();

			int added = Math.min(inHand.getCount(), Constants.FUSIONREACTOR_MAXSTORAGE - count);

			if (added > 0) {
				if (!level.isClientSide()) {
					inHand.setCount(inHand.getCount() - added);

					if (isTritium) {
						tritium.set(tritium.get() + added);
					} else {
						deuterium.set(deuterium.get() + added);
					}
				}

				return ActionResultType.CONSUME;
			}

		}

		return ActionResultType.PASS;
	}

}