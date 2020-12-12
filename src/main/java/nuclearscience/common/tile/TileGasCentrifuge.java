package nuclearscience.common.tile;

import electrodynamics.api.tile.processing.IO2OProcessor;
import electrodynamics.common.tile.generic.GenericTileProcessor;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.inventory.container.ContainerGasCentrifuge;

public class TileGasCentrifuge extends GenericTileProcessor implements IO2OProcessor {
	public static final double REQUIRED_JOULES_PER_TICK = 2250;
	public static final int REQUIRED_TICKS = 2400;

	public static final int[] SLOTS_UP = new int[] { 0 };
	public static final int[] SLOTS_DOWN = new int[] { 1 };

	private int liquidLevelPercentage = 0;

	public TileGasCentrifuge() {
		super(DeferredRegisters.TILE_GASCENTRIFUGE.get());
		addUpgradeSlots(2, 3, 4);
	}

	@Override
	public double getJoulesPerTick() {
		return REQUIRED_JOULES_PER_TICK * currentSpeedMultiplier;
	}

	@Override
	public double getVoltage() {
		return DEFAULT_BASIC_MACHINE_VOLTAGE * 2;
	}

	@Override
	public boolean canProcess() {
		liquidLevelPercentage = 100;
		return getJoulesStored() >= getJoulesPerTick() && liquidLevelPercentage >= 50 && getStackInSlot(0).getCount() < getStackInSlot(0).getMaxStackSize()
				&& getStackInSlot(1).getCount() < getStackInSlot(1).getMaxStackSize();
	}

	@Override
	public void process() {
		liquidLevelPercentage -= 50;
		int index = world.rand.nextFloat() <= 0.172 ? 0 : 1;
		ItemStack stack = getStackInSlot(index);
		if (stack.isEmpty()) {
			setInventorySlotContents(index, new ItemStack(index == 0 ? DeferredRegisters.ITEM_URANIUM235.get() : DeferredRegisters.ITEM_URANIUM238.get()));
		} else {
			stack.setCount(stack.getCount() + 1);
		}
	}

	@Override
	public int getRequiredTicks() {
		return REQUIRED_TICKS;
	}

	@Override
	public int getSizeInventory() {
		return 5;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return side == Direction.UP ? SLOTS_UP : side == Direction.DOWN ? SLOTS_DOWN : SLOTS_EMPTY;
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new ContainerGasCentrifuge(id, player, this, inventorydata);
	}

	@Override
	public boolean canConnectElectrically(Direction direction) {
		return direction == Direction.DOWN;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.gascentrifuge");
	}

	@Override
	public ItemStack getInput() {
		return getStackInSlot(0);
	}

	protected final IIntArray inventorydata = new IIntArray() {
		@Override
		public int get(int index) {
			switch (index) {
			case 0:
				return (int) currentOperatingTick;
			case 1:
				return (int) getVoltage();
			case 2:
				return (int) Math.ceil(getJoulesPerTick());
			case 3:
				return getRequiredTicks() == 0 ? 1 : getRequiredTicks();
			case 4:
				return liquidLevelPercentage;
			default:
				return 0;
			}
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
			case 0:
				currentOperatingTick = value;
				break;
			default:
				break;
			}

		}

		@Override
		public int size() {
			return 5;
		}
	};

	@Override
	public ItemStack getOutput() {
		return getStackInSlot(1);
	}

	@Override
	public void setOutput(ItemStack stack) {
		setInventorySlotContents(1, stack);
	}
}
