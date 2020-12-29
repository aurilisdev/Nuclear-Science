package nuclearscience.common.tile;

import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.api.tile.electric.IElectricTile;
import electrodynamics.api.tile.electric.IPowerProvider;
import electrodynamics.api.tile.electric.IPowerReceiver;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.tile.generic.GenericTileInventory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import nuclearscience.DeferredRegisters;
import nuclearscience.api.radiation.IRadioactiveObject;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.common.inventory.container.ContainerRadioisotopeGenerator;
import nuclearscience.common.settings.Constants;

public class TileRadioisotopeGenerator extends GenericTileInventory implements ITickableTileBase, IPowerProvider, IElectricTile {
	public static final int[] SLOTS_INPUT = new int[] { 0 };

	protected CachedTileOutput output1;
	protected CachedTileOutput output2;

	public TileRadioisotopeGenerator() {
		super(DeferredRegisters.TILE_RADIOISOTOPEGENERATOR.get());
	}

	@Override
	public double getVoltage(Direction arg0) {
		return Constants.RADIOISOTOPEGENERATOR_VOLTAGE;
	}

	@Override
	public void tickServer() {
		if (output1 == null) {
			output1 = new CachedTileOutput(world, new BlockPos(pos).offset(Direction.UP));
		}
		if (output2 == null) {
			output2 = new CachedTileOutput(world, new BlockPos(pos).offset(Direction.DOWN));
		}
		ItemStack in = getStackInSlot(0);
		IRadioactiveObject rad = RadiationRegister.get(in.getItem());
		double currentOutput = in.getCount() * Constants.RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER * rad.getRadiationStrength();
		if (currentOutput > 0) {
			TransferPack transfer = TransferPack.ampsVoltage(currentOutput / getVoltage(Direction.UP), getVoltage(Direction.UP));
			if (output1.get() instanceof IPowerReceiver && output2.get() instanceof IPowerReceiver) {
				transfer = TransferPack.ampsVoltage(transfer.getAmps() / 2.0, transfer.getVoltage());
			}
			if (output1.get() instanceof IPowerReceiver) {
				output1.<IPowerReceiver>get().receivePower(transfer, getFacing(), false);
			}
			if (output2.get() instanceof IPowerReceiver) {
				output2.<IPowerReceiver>get().receivePower(transfer, getFacing(), false);
			}
		}
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return side == Direction.UP ? SLOTS_INPUT : SLOTS_EMPTY;
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new ContainerRadioisotopeGenerator(id, player, this, inventorydata);
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

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.radioisotopegenerator");
	}

	@Override
	public TransferPack extractPower(TransferPack transfer, Direction from, boolean debug) {
		return TransferPack.EMPTY;
	}

	@Override
	public boolean canConnectElectrically(Direction direction) {
		return direction == Direction.UP || direction == Direction.DOWN;
	}

}
