package nuclearscience.common.tile;

import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.common.tile.generic.GenericTileInventory;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.inventory.container.ContainerReactorCore;

public class TileReactorCore extends GenericTileInventory implements ITickableTileBase {

	public static final int[] SLOTS_UP = new int[] { 0, 1, 2, 3, 4 };
	public static final int[] SLOTS_DOWN = new int[] { 5 };
	public static final int MELTDOWN_TEMPERATURE_ACTUAL = 5611;
	public static final int MELTDOWN_TEMPERATURE_CALC = 4407;
	public static final int WATER_TEMPERATURE = 10;
	public static final int AIR_TEMPERATURE = 15;
	public static final int MAX_FUEL_COUNT = 3 * 4;
	public double temperature = AIR_TEMPERATURE; // Actual real temperature is calculated by temp / 4 + 15 in the gui
	public boolean hasDeuterium = false;
	public int ticksOverheating = 0;
	public int fuelCount = 0;
	public int ticks = 0;

	public TileReactorCore() {
		super(DeferredRegisters.TILE_REACTORCORE.get());
	}

	@Override
	public void tickServer() {
		if (world.getWorldInfo().getDayTime() % 10 == 0) {
			sendUpdatePacket();
		}
		ticks = ticks > Integer.MAX_VALUE - 2 ? 0 : ticks + 1;
		fuelCount = 0;
		for (int i = 0; i < 4; i++) {
			ItemStack stack = getStackInSlot(i);
			fuelCount += stack.getItem() == DeferredRegisters.ITEM_FUELLEUO2.get() ? 2 : stack.getItem() == DeferredRegisters.ITEM_FUELHEUO2.get() ? 3 : 0;
		}
		hasDeuterium = !getStackInSlot(4).isEmpty();

		double decrease = (temperature - AIR_TEMPERATURE) / 3000.0;
		if (fuelCount == 0) {
			decrease *= 25;
		}
		boolean hasWater = !getBlockState().getFluidState().isEmpty();
		if (hasWater) {
			decrease += (temperature - WATER_TEMPERATURE) / 5000.0;
		}
		if (decrease != 0) {
			temperature -= decrease < 0.001 && decrease > 0 ? 0.001 : decrease > -0.001 && decrease < 0 ? -0.001 : decrease;
		}
		if (fuelCount > 0 && ticks > 50) {
			double insertDecimal = /* was "(100 - insertion) / 100.0" before */ 1.0;
			if (world.rand.nextFloat() < insertDecimal) {
				for (int slot = 0; slot < 4; slot++) {
					ItemStack fuelRod = getStackInSlot(slot);
					if (fuelRod.getDamage() >= fuelRod.getMaxDamage()) {
						setInventorySlotContents(slot, ItemStack.EMPTY);
					}
					fuelRod.setDamage((int) (fuelRod.getDamage() + 1 + Math.round(temperature) / (MELTDOWN_TEMPERATURE_CALC / 2.0)));
				}
			}
			temperature += (MELTDOWN_TEMPERATURE_CALC * insertDecimal * (0.25 * (fuelCount / 2.0) + world.rand.nextDouble() / 5.0) - temperature) / (200 + 20 * (hasWater ? 4 : 1));
			if (temperature > MELTDOWN_TEMPERATURE_ACTUAL + world.rand.nextInt(50) && fuelCount > 0) {
				ticksOverheating++;
				// Implement some alarm sounds at this time
				// Implement a warning in the gui at this time
				if (ticksOverheating > 15 * 20) {
					// Explode here!!!
				}
			} else {
				ticksOverheating--;
			}
		}
		temperature = Math.max(AIR_TEMPERATURE, temperature);
	}

	@Override
	public void tickCommon() {
		produceSteam();
	}

	public static final int STEAM_GEN_DIAMETER = 5;
	public static final int STEAM_GEN_HEIGHT = 2;
	private TileTurbine[][][] cachedTurbines = new TileTurbine[STEAM_GEN_DIAMETER][STEAM_GEN_HEIGHT][STEAM_GEN_DIAMETER];

	private void produceSteam() {
		if (temperature <= 100) {
			return;
		}
		int totalSteam = 0;
		for (int i = 0; i < STEAM_GEN_DIAMETER; i++) {
			for (int j = 0; j < STEAM_GEN_HEIGHT; j++) {
				for (int k = 0; k < STEAM_GEN_DIAMETER; k++) {
					boolean isReactor2d = i - STEAM_GEN_DIAMETER / 2 == 0 && k - STEAM_GEN_DIAMETER / 2 == 0;
					if (isReactor2d && j == 0) {
						continue;
					}
					int offsetX = pos.getX() + i - STEAM_GEN_DIAMETER / 2;
					int offsetY = pos.getY() + j;
					int offsetZ = pos.getZ() + k - STEAM_GEN_DIAMETER / 2;
					Block offset = world.getBlockState(new BlockPos(offsetX, offsetY, offsetZ)).getBlock();
					if (offset == Blocks.WATER) {
						boolean isFaceWater = world.getBlockState(new BlockPos(offsetX, pos.getY(), pos.getZ())).getBlock() == Blocks.WATER
								|| world.getBlockState(new BlockPos(pos.getX(), pos.getY(), offsetZ)).getBlock() == Blocks.WATER || isReactor2d;
						if (isFaceWater) {
							if (!world.isRemote) {
								TileTurbine turbine = cachedTurbines[i][j][k];
								if (turbine == null || world.loadedTileEntityList.contains(turbine)) {
									TileEntity above = world.getTileEntity(new BlockPos(offsetX, offsetY + 1, offsetZ));
									if (above instanceof TileTurbine) {
										cachedTurbines[i][j][k] = (TileTurbine) above;
										turbine = (TileTurbine) above;
									} else {
										cachedTurbines[i][j][k] = null;
										turbine = null;
									}
								}
								if (turbine != null) {
									totalSteam += ((temperature - 100) / 10 * 0.65f) * 20 * 20;
									turbine.addSteam((int) ((temperature - 100) / 10 * 0.075f) * 20 * 20);
								}
							} else if (world.isRemote) {
								if (world.rand.nextFloat() < temperature / (MELTDOWN_TEMPERATURE_ACTUAL * 3)) {
									if (world.rand.nextInt(80) == 0) {
										// world.playSoundEffect(offsetX + 0.5D, offsetY + 0.5D, offsetZ + 0.5D,
										// "liquid.lava", 0.5F, 2.1F + (world.rand.nextFloat() - world.rand.nextFloat())
										// * 0.85F);
									}
									if (world.rand.nextInt(40) == 0) {
										// world.playSoundEffect(offsetX + 0.5D, offsetY + 0.5D, offsetZ + 0.5D,
										// "liquid.lavapop", 0.5F, 2.6F + (world.rand.nextFloat() -
										// world.rand.nextFloat()) * 0.8F);
									}
									double offsetFX = offsetX + world.rand.nextDouble() / 2.0 * (world.rand.nextBoolean() ? -1 : 1);
									double offsetFY = offsetY + world.rand.nextDouble() / 2.0 * (world.rand.nextBoolean() ? -1 : 1);
									double offsetFZ = offsetZ + world.rand.nextDouble() / 2.0 * (world.rand.nextBoolean() ? -1 : 1);
									world.addParticle(ParticleTypes.BUBBLE, offsetFX + 0.5D, offsetFY + 0.20000000298023224D, offsetFZ + 0.5D, 0.0D, 0.0D, 0.0D);
									if (world.rand.nextInt(3) == 0) {
										world.addParticle(ParticleTypes.SMOKE, offsetFX + 0.5D, offsetFY + 0.5D, offsetFZ + 0.5D, 0.0D, 0.0D, 0.0D);
									}
								}
							}
						}
					}
				}
			}
		}
		System.out.println(totalSteam);
	}

	@Override
	public CompoundNBT createUpdateTag() {
		CompoundNBT tag = super.createUpdateTag();
		tag.putBoolean("hasDeuterium", hasDeuterium);
		tag.putDouble("temperature", temperature);
		tag.putInt("fuelCount", count(DeferredRegisters.ITEM_FUELHEUO2.get()) + count(DeferredRegisters.ITEM_FUELLEUO2.get()));
		return tag;
	}

	@Override
	public void handleUpdatePacket(CompoundNBT nbt) {
		super.handleUpdatePacket(nbt);
		hasDeuterium = nbt.getBoolean("hasDeuterium");
		temperature = nbt.getDouble("temperature");
		fuelCount = nbt.getInt("fuelCount");
	}

	@Override
	public int getSizeInventory() {
		return 6;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return side == Direction.UP ? SLOTS_UP : side == Direction.DOWN ? SLOTS_DOWN : SLOTS_EMPTY;
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new ContainerReactorCore(id, player, this, inventorydata);
	}

	@Override
	public ITextComponent getName() {
		return new TranslationTextComponent("container.reactorcore");
	}

	protected final IIntArray inventorydata = new IIntArray() {
		@Override
		public int get(int index) {
			switch (index) {
			case 0:
				return (int) temperature;
			default:
				return 0;
			}
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
			case 0:
				temperature = value;
				break;
			}
		}

		@Override
		public int size() {
			return 1;
		}
	};

}
