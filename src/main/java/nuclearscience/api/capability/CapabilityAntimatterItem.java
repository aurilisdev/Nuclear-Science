package nuclearscience.api.capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import nuclearscience.registers.NuclearScienceCapabilities;

public class CapabilityAntimatterItem implements ICapabilityAntimatterItem, ICapabilitySerializable<CompoundNBT> {
	
	private int counter = 0;
	
	private final LazyOptional<ICapabilityAntimatterItem> lazyOptional = LazyOptional.of(() -> this);

	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if(cap == NuclearScienceCapabilities.CAPABILITY_ANTIMATTERITEM) {
			return lazyOptional.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT tag = new CompoundNBT();
		tag.putInt("time", counter);
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		if(NuclearScienceCapabilities.CAPABILITY_ANTIMATTERITEM == null) {
			return;
		}
		counter = nbt.getInt("time");
	}

	@Override
	public int getTime() {
		return counter;
	}

	@Override
	public void incrementTime() {
		counter++;
	}

	@Override
	public CompoundNBT toTag() {
		return serializeNBT();
	}

	@Override
	public void fromTag(CompoundNBT tag) {
		deserializeNBT(tag);
	}

}
