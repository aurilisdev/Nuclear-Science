package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.inventory.container.ContainerMoltenSaltSupplier;
import nuclearscience.common.settings.Constants;

public class TileMoltenSaltSupplier extends GenericTile {

	protected CachedTileOutput output;

	public TileMoltenSaltSupplier(BlockPos pos, BlockState state) {
		super(DeferredRegisters.TILE_MOLTENSALTSUPPLIER.get(), pos, state);
		addComponent(new ComponentDirection());
		addComponent(new ComponentTickable().tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentElectrodynamic(this).voltage(Constants.MOLTENSALTSUPPLIER_VOLTAGE).extractPower((x, y) -> TransferPack.EMPTY).input(Direction.UP).input(Direction.DOWN).maxJoules(Constants.MOLTENSALTSUPPLIER_USAGE_PER_TICK * 20));
		addComponent(new ComponentInventory(this).size(1).slotFaces(0, Direction.values()).valid((slot, stack, i) -> stack.getItem() == DeferredRegisters.ITEM_LIFHT4PUF3.get()));
		addComponent(new ComponentContainerProvider("container.moltensaltsupplier").createMenu((id, player) -> new ContainerMoltenSaltSupplier(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	public void tickServer(ComponentTickable tickable) {
		Direction dir = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(dir.getOpposite()));
		}
		if (tickable.getTicks() % 50 == 0) {
			this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
		}
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		if (electro.getJoulesStored() > Constants.MOLTENSALTSUPPLIER_USAGE_PER_TICK) {
			electro.extractPower(TransferPack.joulesVoltage(Constants.MOLTENSALTSUPPLIER_USAGE_PER_TICK, Constants.MOLTENSALTSUPPLIER_VOLTAGE), false);
			if (tickable.getTicks() % 40 == 0) {
				output.update();
				ItemStack in = this.<ComponentInventory>getComponent(ComponentType.Inventory).getItem(0);
				if (in.getCount() > 0 && output.valid() && output.getSafe() instanceof TileMSRReactorCore core) {
					if (core.<ComponentDirection>getComponent(ComponentType.Direction).getDirection() == dir) {
						if (TileMSRReactorCore.FUEL_CAPACITY - core.currentFuel >= 250) {
							in.shrink(1);
							core.currentFuel += 250;
						}
					}
				}
			}
		}
	}
}
