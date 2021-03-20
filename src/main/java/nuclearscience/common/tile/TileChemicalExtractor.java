package nuclearscience.common.tile;

import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.api.tile.GenericTileTicking;
import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentContainerProvider;
import electrodynamics.api.tile.components.type.ComponentDirection;
import electrodynamics.api.tile.components.type.ComponentElectrodynamic;
import electrodynamics.api.tile.components.type.ComponentFluidHandler;
import electrodynamics.api.tile.components.type.ComponentInventory;
import electrodynamics.api.tile.components.type.ComponentPacketHandler;
import electrodynamics.api.tile.components.type.ComponentProcessor;
import electrodynamics.api.tile.components.type.ComponentTickable;
import electrodynamics.common.block.subtype.SubtypeOre;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.inventory.container.ContainerChemicalExtractor;
import nuclearscience.common.settings.Constants;

public class TileChemicalExtractor extends GenericTileTicking {
    public static final int TANKCAPACITY = 5000;
    public static final int REQUIRED_WATER_CAP = 4800;

    public TileChemicalExtractor() {
	super(DeferredRegisters.TILE_CHEMICALEXTRACTOR.get());
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentElectrodynamic(this).enableUniversalInput().voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 2)
		.maxJoules(Constants.CHEMICALEXTRACTOR_USAGE_PER_TICK * 10));
	addComponent(new ComponentFluidHandler(this).fluidTank(Fluids.WATER, TANKCAPACITY).universalInput());
	addComponent(new ComponentInventory(this).size(6).faceSlots(Direction.UP, 0).faceSlots(Direction.DOWN, 1).slotFaces(2, Direction.SOUTH,
		Direction.NORTH, Direction.EAST, Direction.WEST));
	addComponent(new ComponentProcessor(this).usage(Constants.CHEMICALEXTRACTOR_USAGE_PER_TICK)
		.requiredTicks(Constants.CHEMICALEXTRACTOR_REQUIRED_TICKS).canProcess(this::canProcess).upgradeSlots(3, 4, 5).process(this::process));
	addComponent(new ComponentContainerProvider("container.chemicalextractor")
		.createMenu((id, player) -> new ContainerChemicalExtractor(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
    }

    protected void tickClient(ComponentTickable tickable) {
	if (this.<ComponentProcessor>getComponent(ComponentType.Processor).operatingTicks > 0 && world.rand.nextDouble() < 0.15) {
	    world.addParticle(ParticleTypes.SMOKE, pos.getX() + world.rand.nextDouble(), pos.getY() + world.rand.nextDouble() * 0.8 + 0.5,
		    pos.getZ() + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
	}
    }

    protected boolean canProcess(ComponentProcessor processor) {
	ComponentInventory inv = getComponent(ComponentType.Inventory);
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	ComponentFluidHandler tank = getComponent(ComponentType.FluidHandler);
	ItemStack bucketStack = inv.getStackInSlot(2);
	if (!bucketStack.isEmpty() && bucketStack.getCount() > 0 && bucketStack.getItem() == Items.WATER_BUCKET
		&& tank.getStackFromFluid(Fluids.WATER).getAmount() <= TANKCAPACITY - 1000) {
	    inv.setInventorySlotContents(2, new ItemStack(Items.BUCKET));
	    tank.getStackFromFluid(Fluids.WATER).setAmount(Math.min(tank.getStackFromFluid(Fluids.WATER).getAmount() + 1000, TANKCAPACITY));
	}
	if (this.<ComponentTickable>getComponent(ComponentType.Tickable).getTicks() % 10 == 0) {
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	}
	int requiredWater = getRequiredWater(processor);
	return electro.getJoulesStored() >= processor.getUsage() && !inv.getStackInSlot(0).isEmpty() && inv.getStackInSlot(0).getCount() > 0
		&& tank.getStackFromFluid(Fluids.WATER).getAmount() >= requiredWater && requiredWater > 0;
    }

    protected int getRequiredWater(ComponentProcessor processor) {
	ItemStack input = processor.getInput();
	ItemStack output = processor.getOutput();
	Item item = input.getItem();
	int requiredWater = -1;
	if (output.getCount() < output.getMaxStackSize() || output.isEmpty()) {
	    if (item == DeferredRegisters.ITEM_CELLEMPTY.get()) {
		if (output.getItem() == DeferredRegisters.ITEM_CELLHEAVYWATER.get() || output.isEmpty()) {
		    requiredWater = REQUIRED_WATER_CAP;
		}
	    } else if (item == DeferredRegisters.ITEM_CELLHEAVYWATER.get()) {
		if (output.getItem() == DeferredRegisters.ITEM_CELLDEUTERIUM.get() || output.isEmpty()) {
		    requiredWater = REQUIRED_WATER_CAP;
		}
	    } else if (item == electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeOre.uraninite)
		    && (output.getItem() == DeferredRegisters.ITEM_YELLOWCAKE.get() || output.isEmpty())) {
		requiredWater = REQUIRED_WATER_CAP / 3;
	    }
	}
	return requiredWater;
    }

    protected void process(ComponentProcessor processor) {
	ComponentInventory inv = getComponent(ComponentType.Inventory);
	int requiredWater = getRequiredWater(processor);
	ItemStack stack = processor.getInput();
	Item item = stack.getItem();
	ItemStack output = processor.getOutput();
	if (item == DeferredRegisters.ITEM_CELLEMPTY.get()) {
	    if (output.isEmpty()) {
		inv.setInventorySlotContents(1, new ItemStack(DeferredRegisters.ITEM_CELLHEAVYWATER.get()));
	    } else {
		output.setCount(output.getCount() + 1);
	    }
	} else if (item == DeferredRegisters.ITEM_CELLHEAVYWATER.get()) {
	    if (output.isEmpty()) {
		inv.setInventorySlotContents(1, new ItemStack(DeferredRegisters.ITEM_CELLDEUTERIUM.get()));
	    } else {
		output.setCount(output.getCount() + 1);
	    }
	} else if (item == electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeOre.uraninite)) {
	    if (output.isEmpty()) {
		inv.setInventorySlotContents(1, new ItemStack(DeferredRegisters.ITEM_YELLOWCAKE.get()));
	    } else {
		output.setCount(output.getCount() + 1);
	    }
	}
	stack.setCount(stack.getCount() - 1);
	ComponentFluidHandler tank = getComponent(ComponentType.FluidHandler);
	tank.getStackFromFluid(Fluids.WATER).shrink(requiredWater);
    }
}
