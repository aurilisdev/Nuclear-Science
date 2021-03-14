package nuclearscience.common.tile;

import electrodynamics.api.tile.electric.CapabilityElectrodynamic;
import electrodynamics.common.tile.generic.GenericTileTicking;
import electrodynamics.common.tile.generic.component.ComponentType;
import electrodynamics.common.tile.generic.component.type.ComponentContainerProvider;
import electrodynamics.common.tile.generic.component.type.ComponentDirection;
import electrodynamics.common.tile.generic.component.type.ComponentElectrodynamic;
import electrodynamics.common.tile.generic.component.type.ComponentFluidHandler;
import electrodynamics.common.tile.generic.component.type.ComponentInventory;
import electrodynamics.common.tile.generic.component.type.ComponentPacketHandler;
import electrodynamics.common.tile.generic.component.type.ComponentProcessor;
import electrodynamics.common.tile.generic.component.type.ComponentTickable;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.inventory.container.ContainerGasCentrifuge;
import nuclearscience.common.settings.Constants;

public class TileGasCentrifuge extends GenericTileTicking {
    public static final int TANKCAPACITY = 5000;
    public static final float REQUIRED = 2500;
    public int stored235 = 0;
    public int stored238 = 0;
    public int spinSpeed;

    public TileGasCentrifuge() {
	super(DeferredRegisters.TILE_GASCENTRIFUGE.get());
	addComponent(new ComponentTickable());
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler().addCustomPacketReader(this::readCustomPacket)
		.addCustomPacketWriter(this::writeCustomPacket));
	addComponent(
		new ComponentFluidHandler(this).addFluidTank(DeferredRegisters.fluidUraniumHexafluoride, TANKCAPACITY));
	addComponent(new ComponentElectrodynamic(this).setVoltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 2)
		.addInputDirection(Direction.DOWN));
	addComponent(new ComponentInventory().setInventorySize(5).addSlotOnFace(Direction.DOWN, 0)
		.addSlotOnFace(Direction.DOWN, 1));
	addComponent(new ComponentProcessor(this).addUpgradeSlots(2, 3, 4)
		.setJoulesPerTick(Constants.GASCENTRIFUGE_USAGE_PER_TICK)
		.setRequiredTicks(Constants.GASCENTRIFUGE_REQUIRED_TICKS_PER_PROCESSING).setCanProcess(this::canProcess)
		.setProcess(this::process));
	addComponent(new ComponentContainerProvider("container.gascentrifuge")
		.setCreateMenuFunction((id, player) -> new ContainerGasCentrifuge(id, player,
			getComponent(ComponentType.Inventory), getCoordsArray())));
    }

    public boolean canProcess(ComponentProcessor processor) {
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	ComponentInventory inv = getComponent(ComponentType.Inventory);
	ComponentFluidHandler tank = getComponent(ComponentType.FluidHandler);
	boolean val = electro.getJoulesStored() >= processor.getJoulesPerTick()
		&& tank.getStackFromFluid(Fluids.WATER).getAmount() >= REQUIRED / 60.0
		&& inv.getStackInSlot(0).getCount() < inv.getStackInSlot(0).getMaxStackSize()
		&& inv.getStackInSlot(1).getCount() < inv.getStackInSlot(1).getMaxStackSize();
	if (!val && spinSpeed > 0) {
	    spinSpeed = 0;
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
	}
	return val;
    }

    public void process(ComponentProcessor processor) {
	ComponentInventory inv = getComponent(ComponentType.Inventory);
	ComponentFluidHandler tank = getComponent(ComponentType.FluidHandler);
	spinSpeed = (int) processor.operatingSpeed;
	this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
	int processed = (int) (REQUIRED / 60.0);
	tank.getStackFromFluid(Fluids.WATER).shrink(processed);
	stored235 += processed * 0.172;
	stored238 += processed * (1 - 0.172);
	if (stored235 > REQUIRED) {
	    ItemStack stack = inv.getStackInSlot(0);
	    if (!stack.isEmpty()) {
		stack.setCount(stack.getCount() + 1);
	    } else {
		inv.setInventorySlotContents(0, new ItemStack(DeferredRegisters.ITEM_URANIUM235.get()));
	    }
	    stored235 -= 2500;
	}
	if (stored238 > REQUIRED) {
	    ItemStack stack = inv.getStackInSlot(1);
	    if (!stack.isEmpty()) {
		stack.setCount(stack.getCount() + 1);
	    } else {
		inv.setInventorySlotContents(1, new ItemStack(DeferredRegisters.ITEM_URANIUM238.get()));
	    }
	    stored238 -= 2500;
	}
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
	compound.putFloat("stored235", stored235);
	compound.putFloat("stored238", stored238);
	return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
	super.read(state, compound);
	stored235 = compound.getInt("stored235");
	stored238 = compound.getInt("stored238");
    }

    public void writeCustomPacket(CompoundNBT tag) {
	tag.putInt("spinSpeed", spinSpeed);
    }

    public void readCustomPacket(CompoundNBT nbt) {
	spinSpeed = nbt.getInt("spinSpeed");
    }

}
