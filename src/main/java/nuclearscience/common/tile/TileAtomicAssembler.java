package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.CapabilityItemHandler;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.inventory.container.ContainerAtomicAssembler;
import nuclearscience.common.settings.Constants;

public class TileAtomicAssembler extends GenericTile {
	public int progress = 0;
	public ItemStack current = ItemStack.EMPTY;

	public TileAtomicAssembler(BlockPos pos, BlockState state) {
		super(DeferredRegisters.TILE_ATOMICASSEMBLER.get(), pos, state);
		addComponent(new ComponentDirection());
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickCommon(this::tickCommon));
		addComponent(new ComponentPacketHandler().guiPacketWriter(this::writeGuiPacket).guiPacketReader(this::readGuiPacket));
		addComponent(new ComponentElectrodynamic(this).voltage(Constants.QUANTUMASSEMBLER_VOLTAGE).input(Direction.DOWN));
		addComponent(new ComponentInventory(this).size(8).slotFaces(0, Direction.values())
				.valid((slot, stack, i) -> slot == 6 || slot < 6 && stack.is(DeferredRegisters.ITEM_CELLDARKMATTER.get())).shouldSendInfo());
		addComponent(new ComponentContainerProvider("container.atomicassembler")
				.createMenu((id, player) -> new ContainerAtomicAssembler(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	private void tickCommon(ComponentTickable tickable) {
		ComponentInventory inv = getComponent(ComponentType.Inventory);
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		ItemStack input = inv.getItem(6);
		ItemStack output = inv.getItem(7);
		boolean canProcess = electro.getJoulesStored() < Constants.ATOMICASSEMBLER_USAGE_PER_TICK
				&& !input.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent()
				&& (ItemStack.isSame(input, output) && output.getCount() + 1 <= output.getMaxStackSize() || output.isEmpty()) && !input.isEmpty();
		if (canProcess) {
			for (int index = 0; index < 6; index++) {
				ItemStack dmSlot = inv.getItem(index);
				if (dmSlot.is(DeferredRegisters.ITEM_CELLDARKMATTER.get())) {
					if (dmSlot.getDamageValue() >= dmSlot.getMaxDamage()) {
						inv.setItem(index, ItemStack.EMPTY);
					}
				} else {
					canProcess = false;
				}
			}
		} else {
			progress = 0;
		}

		boolean canProduce = false;
		if (canProcess) {
			if (progress++ >= Constants.QUANTUMASSEMBLER_REQUIRED_TICKS) {
				canProduce = true;
			}
			electro.extractPower(TransferPack.joulesVoltage(Constants.ATOMICASSEMBLER_USAGE_PER_TICK, electro.getVoltage()), false);
		}
		if (canProduce) {
			progress = 0;
			for (int index = 0; index < 6; index++) {
				ItemStack dmSlot = inv.getItem(index);
				if (dmSlot.is(DeferredRegisters.ITEM_CELLDARKMATTER.get())) {
					if (dmSlot.getDamageValue() >= dmSlot.getMaxDamage()) {
						inv.setItem(index, ItemStack.EMPTY);
					}
					dmSlot.setDamageValue(dmSlot.getDamageValue() + 1);
				}
			}
			if (output.isEmpty()) {
				inv.setItem(7, input.copy());
				inv.getItem(7).setCount(1);
			} else {
				output.setCount(output.getCount() + 1);
			}
		}
	}

	private void tickServer(ComponentTickable tickable) {
		if (tickable.getTicks() % 20 == 0) {
			this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
		}
		ComponentInventory inv = getComponent(ComponentType.Inventory);
		current = inv.getItem(6);
	}

	private void writeGuiPacket(CompoundTag compound) {
		compound.putInt("progress", progress);
		current.save(compound);
	}

	private void readGuiPacket(CompoundTag compound) {
		progress = compound.getInt("progress");
		current = ItemStack.of(compound);
	}
}
