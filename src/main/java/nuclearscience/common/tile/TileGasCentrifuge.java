package nuclearscience.common.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistries;
import nuclearscience.common.inventory.container.ContainerGasCentrifuge;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceSounds;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.sound.ITickableSound;
import voltaic.prefab.sound.SoundBarrierMethods;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.*;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.RadiationUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileGasCentrifuge extends GenericTile implements ITickableSound {

	public static final int TANKCAPACITY = 5000;
	public static final double REQUIRED = 2500;
	public static final double PERCENT_U235 = 0.172;
	public static final double WASTE_MULTIPLIER = 0.1;
	public SingleProperty<Integer> spinSpeed = property(new SingleProperty<>(PropertyTypes.INTEGER, "spinSpeed", 0));
	public SingleProperty<Double> stored235 = property(new SingleProperty<>(PropertyTypes.DOUBLE, "stored235", 0.0));
	public SingleProperty<Double> stored238 = property(new SingleProperty<>(PropertyTypes.DOUBLE, "stored238", 0.0));
	public SingleProperty<Double> storedWaste = property(new SingleProperty<>(PropertyTypes.DOUBLE, "storedWaste", 0.0));
	public SingleProperty<Boolean> isRunning = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "isRunning", false));

	private boolean isSoundPlaying = false;

	public TileGasCentrifuge(BlockPos pos, BlockState state) {
		super(NuclearScienceTiles.TILE_GASCENTRIFUGE.get(), pos, state);
		addComponent(new ComponentTickable(this).tickClient(this::tickClient));

		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentFluidHandlerMulti(this).setInputTanks(1, arr(TANKCAPACITY)).setInputFluidTags(NuclearScienceTags.Fluids.URANIUM_HEXAFLUORIDE).setInputDirections(BlockEntityUtils.MachineDirection.BACK));
		addComponent(new ComponentElectrodynamic(this, false, true).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 2).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM).maxJoules(NuclearConstants.GASCENTRIFUGE_USAGE_PER_TICK * 10));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().outputs(3).upgrades(3)).setSlotsByDirection(BlockEntityUtils.MachineDirection.TOP, 0, 1, 2).setSlotsByDirection(BlockEntityUtils.MachineDirection.RIGHT, 0, 1, 2).setSlotsByDirection(BlockEntityUtils.MachineDirection.LEFT, 0, 1, 2)
				//
				.setSlotsByDirection(BlockEntityUtils.MachineDirection.BACK, 0, 1, 2).validUpgrades(ContainerGasCentrifuge.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentProcessor(this).usage(NuclearConstants.GASCENTRIFUGE_USAGE_PER_TICK, 0).requiredTicks(NuclearConstants.GASCENTRIFUGE_REQUIRED_TICKS_PER_PROCESSING, 0).canProcess(this::canProcess).process(this::process));
		addComponent(new ComponentContainerProvider("gascentrifuge", this).createMenu((id, player) -> new ContainerGasCentrifuge(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	public boolean canProcess(ComponentProcessor processor, int procNumber) {
		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);
		ComponentInventory inv = getComponent(IComponentType.Inventory);
		ComponentFluidHandlerMulti fluidHandler = getComponent(IComponentType.FluidHandler);

		RadiationUtils.handleRadioactiveFluids(this, fluidHandler, NuclearConstants.GAS_CENTRIFUGE_RADIATION_RADIUS, true, 0, false);
		RadiationUtils.handleRadioactiveItems(this, inv, NuclearConstants.GAS_CENTRIFUGE_RADIATION_RADIUS, true, 0, false);

		boolean hasGas = fluidHandler.getInputTanks()[0].getFluidAmount() >= REQUIRED / 60.0;
		boolean val = electro.getJoulesStored() >= processor.getUsage(0) && hasGas && inv.getItem(0).getCount() < inv.getItem(0).getMaxStackSize() && inv.getItem(1).getCount() < inv.getItem(1).getMaxStackSize() && inv.getItem(2).getCount() < inv.getItem(2).getMaxStackSize();
		if (!val && spinSpeed.getValue() > 0) {
			spinSpeed.setValue(0);
		}

		isRunning.setValue(val);

		return val;
	}

	public void process(ComponentProcessor processor, int procNumber) {
		ComponentInventory inv = getComponent(IComponentType.Inventory);
		ComponentFluidHandlerMulti multi = getComponent(IComponentType.FluidHandler);
		spinSpeed.setValue(processor.operatingSpeed.getValue().intValue());
		int processed = (int) (REQUIRED / 60.0);
		FluidTank tank = multi.getInputTanks()[0];

		if (ForgeRegistries.FLUIDS.tags().getTag(NuclearScienceTags.Fluids.URANIUM_HEXAFLUORIDE).contains(tank.getFluid().getFluid()) && tank.getFluidAmount() >= processed) {
			tank.drain(processed, FluidAction.EXECUTE);
		}

		stored235.setValue(stored235.getValue() + processed * PERCENT_U235);
		stored238.setValue(stored238.getValue() + processed * (1 - PERCENT_U235 - WASTE_MULTIPLIER));
		storedWaste.setValue(stored235.getValue() + processed * WASTE_MULTIPLIER);
		if (stored235.getValue() > REQUIRED) {
			ItemStack stack = inv.getItem(0);
			if (!stack.isEmpty()) {
				stack.setCount(stack.getCount() + 1);
			} else {
				inv.setItem(0, new ItemStack(NuclearScienceItems.ITEM_URANIUM235.get()));
			}
			stored235.setValue(stored235.getValue() - REQUIRED);
		}
		if (stored238.getValue() > REQUIRED) {
			ItemStack stack = inv.getItem(1);
			if (!stack.isEmpty()) {
				stack.setCount(stack.getCount() + 1);
			} else {
				inv.setItem(1, new ItemStack(NuclearScienceItems.ITEM_URANIUM238.get()));
			}
			stored238.setValue(stored238.getValue() - REQUIRED);
		}
		if (storedWaste.getValue() > REQUIRED) {
			ItemStack stack = inv.getItem(2);
			if (!stack.isEmpty()) {
				stack.grow(1);
			} else {
				inv.setItem(2, new ItemStack(NuclearScienceItems.ITEM_FISSILEDUST.get(), 1));
			}
			storedWaste.setValue(storedWaste.getValue() - REQUIRED);
		}
	}

	protected void tickClient(ComponentTickable tickable) {
		if (!isSoundPlaying && shouldPlaySound()) {
			isSoundPlaying = true;
			SoundBarrierMethods.playTileSound(NuclearScienceSounds.SOUND_GASCENTRIFUGE.get(), this, true);
		}
	}

	@Override
	public void setNotPlaying() {
		isSoundPlaying = false;
	}

	@Override
	public boolean shouldPlaySound() {
		return spinSpeed.getValue() > 0;
	}

	@Override
	public int getComparatorSignal() {
		return isRunning.getValue() ? 15 : 0;
	}
}
