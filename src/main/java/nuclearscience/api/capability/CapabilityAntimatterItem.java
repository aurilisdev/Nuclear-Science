package nuclearscience.api.capability;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import nuclearscience.registers.NuclearScienceCapabilities;

public class CapabilityAntimatterItem implements ICapabilityAntimatterItem, ICapabilitySerializable<CompoundTag> {

    private int counter = 0;

    private final LazyOptional<ICapabilityAntimatterItem> lazyOptional = LazyOptional.of(() -> this);

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
	if (cap == NuclearScienceCapabilities.CAPABILITY_ANTIMATTERITEM) {
	    return lazyOptional.cast();
	}
	return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
	CompoundTag tag = new CompoundTag();
	tag.putInt("time", counter);
	return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
	if (NuclearScienceCapabilities.CAPABILITY_ANTIMATTERITEM == null) {
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

}
