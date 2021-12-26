package nuclearscience.common.tile;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import nuclearscience.DeferredRegisters;
import nuclearscience.SoundRegister;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.common.inventory.container.ContainerGasCentrifuge;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tags.NuclearScienceTags;

public class TileGasCentrifuge extends GenericTile {
	public static final int TANKCAPACITY = 5000;
	public static final float REQUIRED = 2500;
	private static final double PERCENT_U235 = 0.172;
	private static final double WASTE_MULTIPLIER = 0.1;
	public boolean isRunning = false;
	// public long ticks = 0;
	public int stored235 = 0;
	public int stored238 = 0;
	public int storedWaste = 0;
	public int spinSpeed;

	private static final int RADATION_RADIUS_BLOCKS = 5;
	private static final int RADIATION_STRENGTH = 5000;

	public TileGasCentrifuge(BlockPos pos, BlockState state) {
		super(DeferredRegisters.TILE_GASCENTRIFUGE.get(), pos, state);
		addComponent(new ComponentTickable().tickClient(this::tickClient).tickServer(this::tickServer));
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler().customPacketReader(this::readCustomPacket).customPacketWriter(this::writeCustomPacket));
		addComponent(new ComponentFluidHandlerMulti(this).setManualFluidTags(1, true, TANKCAPACITY, NuclearScienceTags.Fluids.URANIUM_HEXAFLUORIDE)
				.relativeInput(Direction.NORTH));
		addComponent(new ComponentElectrodynamic(this).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2).input(Direction.DOWN)
				.maxJoules(Constants.GASCENTRIFUGE_USAGE_PER_TICK * 10));
		addComponent(new ComponentInventory(this).size(6).faceSlots(Direction.DOWN, 0, 1).relativeFaceSlots(Direction.WEST, 0, 1).outputs(3)
				.upgrades(3).valid(machineValidator()));
		addComponent(new ComponentProcessor(this).usage(Constants.GASCENTRIFUGE_USAGE_PER_TICK)
				.requiredTicks(Constants.GASCENTRIFUGE_REQUIRED_TICKS_PER_PROCESSING).canProcess(this::canProcess).process(this::process));
		addComponent(new ComponentContainerProvider("container.gascentrifuge")
				.createMenu((id, player) -> new ContainerGasCentrifuge(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	public boolean canProcess(ComponentProcessor processor) {
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		ComponentInventory inv = getComponent(ComponentType.Inventory);
		ComponentFluidHandlerMulti tank = getComponent(ComponentType.FluidHandler);
		boolean hasFluid = tank.getInputTanks()[0].getFluidAmount() >= REQUIRED / 60.0;
		boolean val = electro.getJoulesStored() >= processor.getUsage() && hasFluid && inv.getItem(0).getCount() < inv.getItem(0).getMaxStackSize()
				&& inv.getItem(1).getCount() < inv.getItem(1).getMaxStackSize() && inv.getItem(2).getCount() < inv.getItem(2).getMaxStackSize();
		if (!val && spinSpeed > 0) {
			spinSpeed = 0;
			this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
		}

		isRunning = val;

		return val;
	}

	public void process(ComponentProcessor processor) {
		ComponentInventory inv = getComponent(ComponentType.Inventory);
		ComponentFluidHandlerMulti tank = getComponent(ComponentType.FluidHandler);
		spinSpeed = (int) processor.operatingSpeed;
		this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
		int processed = (int) (REQUIRED / 60.0);
		for (Fluid fluid : NuclearScienceTags.Fluids.URANIUM_HEXAFLUORIDE.getValues()) {
			FluidTank fTank = tank.getTankFromFluid(fluid, true);
			if (fTank.getFluidAmount() >= processed) {
				fTank.getFluid().shrink(processed);
				break;
			}
		}

		stored235 += processed * PERCENT_U235;
		stored238 += processed * (1 - PERCENT_U235);
		storedWaste += processed * WASTE_MULTIPLIER;
		if (stored235 > REQUIRED) {
			ItemStack stack = inv.getItem(0);
			if (!stack.isEmpty()) {
				stack.setCount(stack.getCount() + 1);
			} else {
				inv.setItem(0, new ItemStack(DeferredRegisters.ITEM_URANIUM235.get()));
			}
			stored235 -= REQUIRED;
		}
		if (stored238 > REQUIRED) {
			ItemStack stack = inv.getItem(1);
			if (!stack.isEmpty()) {
				stack.setCount(stack.getCount() + 1);
			} else {
				inv.setItem(1, new ItemStack(DeferredRegisters.ITEM_URANIUM238.get()));
			}
			stored238 -= REQUIRED;
		}
		if (storedWaste > REQUIRED) {
			ItemStack stack = inv.getItem(2);
			if (!stack.isEmpty()) {
				stack.grow(1);
			} else {
				inv.setItem(2, new ItemStack(DeferredRegisters.ITEM_FISSILEDUST.get(), 1));
			}
			storedWaste -= REQUIRED;
		}
	}

	protected void tickClient(ComponentTickable tickable) {
		if (spinSpeed > 0 && tickable.getTicks() % 80 == 0) {
			SoundAPI.playSound(SoundRegister.SOUND_GASCENTRIFUGE.get(), SoundSource.BLOCKS, 1, 1, worldPosition);
		}
	}

	protected void tickServer(ComponentTickable tickable) {
		if (level.getLevelData().getGameTime() % 10 == 0 && isRunning) {
			RadiationSystem.emitRadiationFromLocation(level, new Location(worldPosition), RADATION_RADIUS_BLOCKS, RADIATION_STRENGTH);
		}
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		compound.putInt("stored235", stored235);
		compound.putInt("stored238", stored238);
		compound.putInt("storedWaste", storedWaste);
		compound.putBoolean("isRunning", isRunning);
		// compound.putLong("ticks", ticks);
		super.saveAdditional(compound);
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		stored235 = compound.getInt("stored235");
		stored238 = compound.getInt("stored238");
		storedWaste = compound.getInt("storedWaste");
		isRunning = compound.getBoolean("isRunning");
		// ticks = compound.getLong("ticks");
	}

	public void writeCustomPacket(CompoundTag tag) {
		tag.putInt("spinSpeed", spinSpeed);
		tag.putInt("stored235", stored235);
		tag.putInt("stored238", stored238);
		tag.putInt("storedWaste", storedWaste);
		tag.putBoolean("isRunning", isRunning);
		// tag.putLong("ticks", ticks);
	}

	public void readCustomPacket(CompoundTag nbt) {
		spinSpeed = nbt.getInt("spinSpeed");
		stored235 = nbt.getInt("stored235");
		stored238 = nbt.getInt("stored238");
		storedWaste = nbt.getInt("storedWaste");
		isRunning = nbt.getBoolean("isRunning");
		// ticks = nbt.getLong("ticks");
	}

}
