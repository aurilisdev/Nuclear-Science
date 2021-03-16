package nuclearscience.common.tile;

import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.api.tile.GenericTileTicking;
import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentContainerProvider;
import electrodynamics.api.tile.components.type.ComponentDirection;
import electrodynamics.api.tile.components.type.ComponentElectrodynamic;
import electrodynamics.api.tile.components.type.ComponentInventory;
import electrodynamics.api.tile.components.type.ComponentPacketHandler;
import electrodynamics.api.tile.components.type.ComponentProcessor;
import electrodynamics.api.tile.components.type.ComponentTickable;
import electrodynamics.api.utilities.object.Location;
import electrodynamics.common.recipe.MachineRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.entity.EntityParticle;
import nuclearscience.common.inventory.container.ContainerParticleInjector;
import nuclearscience.common.settings.Constants;

public class TileParticleInjector extends GenericTileTicking {
    private EntityParticle[] particles = new EntityParticle[2];
    private long timeSinceSpawn = 0;

    public TileParticleInjector() {
	super(DeferredRegisters.TILE_PARTICLEINJECTOR.get());
	addComponent(new ComponentTickable());
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentInventory().setInventorySize(3)
		.setItemValidPredicate((index, stack) -> index != 1 || stack.getItem() == DeferredRegisters.ITEM_CELLELECTROMAGNETIC.get()));
	addComponent(new ComponentElectrodynamic(this).setVoltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 8)
		.addRelativeInputDirection(Direction.NORTH).setMaxJoules(Constants.PARTICLEINJECTOR_USAGE_PER_PARTICLE * 10));
	addComponent(new ComponentProcessor(this).setCanProcess(this::canProcess).setJoulesPerTick(Constants.PARTICLEINJECTOR_USAGE_PER_PARTICLE)
		.setProcess(this::process));
	addComponent(new ComponentContainerProvider("container.particleinjector").setCreateMenuFunction(
		(id, player) -> new ContainerParticleInjector(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
    }

    protected boolean canProcess(ComponentProcessor processor) {
	if (particles[0] != null && !particles[0].isAlive()) {
	    particles[0] = null;
	}
	if (particles[1] != null && !particles[1].isAlive()) {
	    particles[1] = null;
	}
	ComponentInventory inv = getComponent(ComponentType.Inventory);
	ItemStack resultStack = inv.getStackInSlot(2);
	timeSinceSpawn--;
	return timeSinceSpawn < 0 && MachineRecipes.canProcess(this) && (particles[0] == null || particles[1] == null)
		&& inv.getStackInSlot(0).getCount() > 0 && resultStack.getCount() < resultStack.getMaxStackSize();
    }

    public void checkCollision() {
	ComponentInventory inv = getComponent(ComponentType.Inventory);
	ItemStack resultStack = inv.getStackInSlot(2);
	ItemStack cellStack = inv.getStackInSlot(0);
	if (resultStack.getCount() < resultStack.getMaxStackSize() && cellStack.getCount() > 0 && particles[0] != null && particles[1] != null) {
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
			inv.setInventorySlotContents(2, new ItemStack(DeferredRegisters.ITEM_CELLDARKMATTER.get()));
		    }
		} else if (speedOfMax > mod) {
		    if (resultStack.getItem() == DeferredRegisters.ITEM_CELLANTIMATTERSMALL.get()) {
			resultStack.setCount(resultStack.getCount() + 1);
		    } else if (resultStack.isEmpty()) {
			inv.setInventorySlotContents(2, new ItemStack(DeferredRegisters.ITEM_CELLANTIMATTERSMALL.get()));
		    }
		}
	    }
	}
    }

    public void process(ComponentProcessor processor) {
	timeSinceSpawn = 100;
	Direction dir = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	ItemStack stack = processor.getInput();
	stack.setCount(stack.getCount() - 1);
	EntityParticle particle = new EntityParticle(dir, world, new Location(pos.getX() + 0.5f + dir.getXOffset() * 1.5f,
		pos.getY() + 0.5f + dir.getYOffset() * 1.5f, pos.getZ() + 0.5f + dir.getZOffset() * 1.5f));
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
}
