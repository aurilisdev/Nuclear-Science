package nuclearscience.common.tile;

import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import nuclearscience.DeferredRegisters;
import nuclearscience.SoundRegister;
import nuclearscience.common.inventory.container.ContainerGasCentrifuge;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tags.NuclearScienceTags;

public class TileGasCentrifuge extends GenericTileTicking {
    public static final int TANKCAPACITY = 5000;
    public static final float REQUIRED = 2500;
    public int stored235 = 0;
    public int stored238 = 0;
    public int spinSpeed;

    public TileGasCentrifuge() {
	super(DeferredRegisters.TILE_GASCENTRIFUGE.get());
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler().customPacketReader(this::readCustomPacket).customPacketWriter(this::writeCustomPacket));
	addComponent(new ComponentFluidHandlerMulti(this).addFluidTank(NuclearScienceTags.Fluids.URANIUM_HEXAFLUORIDE, TANKCAPACITY, true)
		.relativeInput(Direction.NORTH));
	addComponent(new ComponentElectrodynamic(this).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 2).input(Direction.DOWN)
		.maxJoules(Constants.GASCENTRIFUGE_USAGE_PER_TICK * 10));
	addComponent(new ComponentInventory(this).size(5).faceSlots(Direction.DOWN, 0, 1).relativeFaceSlots(Direction.WEST, 0, 1)
		.valid((slot, stack) -> slot < 2 || stack.getItem() instanceof ItemProcessorUpgrade));
	addComponent(new ComponentProcessor(this).upgradeSlots(2, 3, 4).usage(Constants.GASCENTRIFUGE_USAGE_PER_TICK)
		.requiredTicks(Constants.GASCENTRIFUGE_REQUIRED_TICKS_PER_PROCESSING).canProcess(this::canProcess).process(this::process));
	addComponent(new ComponentContainerProvider("container.gascentrifuge")
		.createMenu((id, player) -> new ContainerGasCentrifuge(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
    }

    public boolean canProcess(ComponentProcessor processor) {
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	ComponentInventory inv = getComponent(ComponentType.Inventory);
	ComponentFluidHandlerMulti tank = getComponent(ComponentType.FluidHandler);
	boolean hasFluid = false;
	for(Fluid fluid : NuclearScienceTags.Fluids.URANIUM_HEXAFLUORIDE.getAllElements()) {
		FluidTank fTank = tank.getTankFromFluid(fluid, true); 
		if(fTank.getFluidAmount() >= REQUIRED / 60.0) {
			hasFluid = true;
			break;
		}
	}
	boolean val = electro.getJoulesStored() >= processor.getUsage() && hasFluid
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
	ComponentFluidHandlerMulti tank = getComponent(ComponentType.FluidHandler);
	spinSpeed = (int) processor.operatingSpeed;
	this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
	int processed = (int) (REQUIRED / 60.0);
	for(Fluid fluid : NuclearScienceTags.Fluids.URANIUM_HEXAFLUORIDE.getAllElements()) {
		FluidTank fTank = tank.getTankFromFluid(fluid, true); 
		if(fTank.getFluidAmount() >= processed) {
			fTank.getFluid().shrink(processed);
			break;
		}
	}
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

    protected void tickClient(ComponentTickable tickable) {
	if (spinSpeed > 0 && tickable.getTicks() % 80 == 0) {
	    SoundAPI.playSound(SoundRegister.SOUND_GASCENTRIFUGE.get(), SoundCategory.BLOCKS, 1, 1, pos);
	}
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
	compound.putInt("stored235", stored235);
	compound.putInt("stored238", stored238);
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
	tag.putInt("stored235", stored235);
	tag.putInt("stored238", stored238);
    }

    public void readCustomPacket(CompoundNBT nbt) {
	spinSpeed = nbt.getInt("spinSpeed");
	stored235 = nbt.getInt("stored235");
	stored238 = nbt.getInt("stored238");
    }

}
