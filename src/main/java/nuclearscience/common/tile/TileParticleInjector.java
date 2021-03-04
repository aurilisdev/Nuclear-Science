package nuclearscience.common.tile;

import electrodynamics.api.math.Location;
import electrodynamics.common.tile.generic.GenericTileProcessor;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.entity.EntityParticle;
import nuclearscience.common.inventory.container.ContainerParticleInjector;
import nuclearscience.common.settings.Constants;

public class TileParticleInjector extends GenericTileProcessor {
    private EntityParticle[] particles = new EntityParticle[2];
    private long timeSinceSpawn = 0;

    public TileParticleInjector() {
	super(DeferredRegisters.TILE_PARTICLEINJECTOR.get());
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
	return index == 1 ? stack.getItem() == DeferredRegisters.ITEM_CELLELECTROMAGNETIC.get()
		: super.isItemValidForSlot(index, stack);
    }

    @Override
    public boolean canProcess() {
	trackInteger(0, (int) getVoltage());
	trackInteger(1, (int) Math.ceil(getJoulesPerTick()));
	trackInteger(2, (int) (getJoulesStored() / getJoulesPerTick() * 100.0));
	if (particles[0] != null && !particles[0].isAlive()) {
	    particles[0] = null;
	}
	if (particles[1] != null && !particles[1].isAlive()) {
	    particles[1] = null;
	}
	ItemStack resultStack = getStackInSlot(2);
	timeSinceSpawn--;
	return timeSinceSpawn < 0 && super.canProcess() && (particles[0] == null || particles[1] == null)
		&& getStackInSlot(0).getCount() > 0 && resultStack.getCount() < resultStack.getMaxStackSize();
    }

    public void checkCollide() {
	ItemStack resultStack = getStackInSlot(2);
	ItemStack cellStack = getStackInSlot(0);
	if (resultStack.getCount() < resultStack.getMaxStackSize() && cellStack.getCount() > 0 && particles[0] != null
		&& particles[1] != null) {
	    EntityParticle one = particles[0];
	    EntityParticle two = particles[1];
	    if (one.getDistance(two) < 1) {
		double speedOfMax = Math.pow((one.speed + two.speed) / 4.0, 2);
		one.remove();
		two.remove();
		particles[0] = particles[1] = null;
		cellStack.shrink(1);
		double mod = world.rand.nextDouble();
		if (speedOfMax > 0.999) {
		    if (resultStack.getItem() == DeferredRegisters.ITEM_CELLDARKMATTER.get()) {
			resultStack.setCount(resultStack.getCount() + 1);
		    } else if (resultStack.isEmpty()) {
			setInventorySlotContents(2, new ItemStack(DeferredRegisters.ITEM_CELLDARKMATTER.get()));
		    }
		} else if (speedOfMax > mod) {
		    if (resultStack.getItem() == DeferredRegisters.ITEM_CELLANTIMATTERSMALL.get()) {
			resultStack.setCount(resultStack.getCount() + 1);
		    } else if (resultStack.isEmpty()) {
			setInventorySlotContents(2, new ItemStack(DeferredRegisters.ITEM_CELLANTIMATTERSMALL.get()));
		    }
		}
	    }
	}
    }

    @Override
    public void process() {
	timeSinceSpawn = 100;
	Direction dir = getFacing();
	ItemStack stack = getStackInSlot(0);
	stack.setCount(stack.getCount() - 1);
	EntityParticle particle = new EntityParticle(getFacing(), world,
		new Location(pos.getX() + 0.5f + dir.getXOffset() * 1.5f, pos.getY() + 0.5f + dir.getYOffset() * 1.5f,
			pos.getZ() + 0.5f + dir.getZOffset() * 1.5f));
	addParticle(particle);
	world.addEntity(particle);
    }

    public void addParticle(EntityParticle particle) {
	if (particles[0] != particle && particles[1] != particle) {
	    particle.source = getPos();
	    if (particles[0] == null) {
		particles[0] = particle;
	    } else if (particles[1] == null) {
		particles[1] = particle;
	    }
	}
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
	return INFINITE_EXTENT_AABB;
    }

    @Override
    public int getSizeInventory() {
	return 3;
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
    protected Container createMenu(int id, PlayerInventory player) {
	return new ContainerParticleInjector(id, player, this, getInventoryData());
    }

    @Override
    public ITextComponent getDisplayName() {
	return new TranslationTextComponent("container.particleinjector");
    }

    @Override
    public double getVoltage() {
	return super.getVoltage() * 8;
    }

    @Override
    public double getJoulesPerTick() {
	return Constants.PARTICLEINJECTOR_USAGE_PER_PARTICLE;
    }

}
