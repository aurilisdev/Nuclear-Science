package nuclearscience.common.tile;

import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.common.tile.generic.GenericTileInventory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.inventory.container.ContainerReactorCore;

public class TileReactorCore extends GenericTileInventory implements ITickableTileBase {

	public static final int[] SLOTS_UP = new int[] { 0, 1, 2, 3, 4, 5 };
	public static final int[] SLOTS_DOWN = new int[] { 6 };
	public boolean hasDeuterium = false;
	public int fuelCount = 0;

	public TileReactorCore() {
		super(DeferredRegisters.TILE_REACTORCORE.get());
	}

	@Override
	public void tickServer() {
		fuelCount = count(DeferredRegisters.ITEM_FUELHEUO2.get()) + count(DeferredRegisters.ITEM_FUELLEUO2.get());
		hasDeuterium = !getStackInSlot(4).isEmpty();
		if (world.getWorldInfo().getDayTime() % 10 == 0) {
			sendUpdatePacket();
		}
	}

	@Override
	public CompoundNBT createUpdateTag() {
		CompoundNBT tag = super.createUpdateTag();
		tag.putBoolean("hasDeuterium", hasDeuterium);
		tag.putInt("fuelCount", fuelCount);
		return tag;
	}

	@Override
	public void handleUpdatePacket(CompoundNBT nbt) {
		super.handleUpdatePacket(nbt);
		this.hasDeuterium = nbt.getBoolean("hasDeuterium");
		this.fuelCount = nbt.getInt("fuelCount");
	}

	@Override
	public int getSizeInventory() {
		return 6;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return side == Direction.UP ? SLOTS_UP : side == Direction.DOWN ? SLOTS_DOWN : SLOTS_EMPTY;
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new ContainerReactorCore(id, player, this, inventorydata);
	}

	@Override
	public ITextComponent getName() {
		return new TranslationTextComponent("container.reactorcore");
	}

	protected final IIntArray inventorydata = new IIntArray() {
		@Override
		public int get(int index) {
			switch (index) {
			default:
				return 0;
			}
		}

		@Override
		public void set(int index, int value) {
		}

		@Override
		public int size() {
			return 0;
		}
	};

}
