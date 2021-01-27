package nuclearscience.common.tile;

import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.api.tile.electric.IElectricTile;
import electrodynamics.api.tile.electric.IPowerReceiver;
import electrodynamics.common.tile.generic.GenericTileProcessor;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3f;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.entity.EntityParticle;
import nuclearscience.common.settings.Constants;

public class TileParticleInjector extends GenericTileProcessor implements ITickableTileBase, IPowerReceiver, IElectricTile {
	private double joules;
	private long timeSinceSpawn = 0;

	public TileParticleInjector() {
		super(DeferredRegisters.TILE_PARTICLEINJECTOR.get());
	}

	@Override
	public boolean canProcess() {
		timeSinceSpawn--;
		return super.canProcess() && timeSinceSpawn < 100;
	}

	@Override
	public void process() {
		Direction dir = getFacing();
		EntityParticle particle = new EntityParticle(getFacing(), world,
				new Vector3f(pos.getX() + 0.5f + dir.getXOffset() * 1.5f, pos.getY() + 0.5f + dir.getYOffset() * 1.5f, pos.getZ() + 0.5f + dir.getZOffset() * 1.5f));
		world.addEntity(particle);
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.putDouble(JOULES_STORED_NBT, joules);
		return super.write(compound);
	}

	@Override
	public void read(BlockState state, CompoundNBT compound) {
		super.read(state, compound);
		joules = compound.getDouble(JOULES_STORED_NBT);
	}

	@Override
	public CompoundNBT createUpdateTag() {
		CompoundNBT nbt = super.createUpdateTag();
		return nbt;
	}

	@Override
	public void handleUpdatePacket(CompoundNBT nbt) {
		super.handleUpdatePacket(nbt);
	}

	@Override
	public boolean canConnectElectrically(Direction direction) {
		return direction == getFacing().getOpposite();
	}

	@Override
	public int getSizeInventory() {
		return 0;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return SLOTS_EMPTY;
	}

	@Override
	public int getRequiredTicks() {
		return 0;
	}

	@Override
	protected Container createMenu(int arg0, PlayerInventory arg1) {
		return null;
	}

	@Override
	public double getVoltage() {
		return DEFAULT_BASIC_MACHINE_VOLTAGE * 8;
	}

	@Override
	public double getJoulesPerTick() {
		return Constants.PARTICLEINJECTOR_REQUIREDPOWER;
	}

}
