package nuclearscience.api.quantumtunnel;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.datafixers.util.Pair;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import nuclearscience.registers.NuclearScienceCapabilities;

public class CapabilityTunnelMap implements ICapabilityTunnelMap, ICapabilitySerializable<CompoundNBT> {
	
	private final HashMap<TunnelFrequency, TunnelFrequencyBuffer> map = new HashMap<>();
	
	private final LazyOptional<ICapabilityTunnelMap> lazyOptional = LazyOptional.of(() -> this);

	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if(cap == NuclearScienceCapabilities.CAPABILITY_TUNNELMAP) {
			return lazyOptional.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT data = new CompoundNBT();
        int size = map.size();
        data.putInt("size", size);
        int i = 0;
        for (Map.Entry<TunnelFrequency, TunnelFrequencyBuffer> entry : map.entrySet()) {
            CompoundNBT store = new CompoundNBT();
            TunnelFrequency.CODEC.encodeStart(NBTDynamicOps.INSTANCE, entry.getKey()).result().ifPresent(tag -> store.put("id", tag));
            TunnelFrequencyBuffer.CODEC.encodeStart(NBTDynamicOps.INSTANCE, entry.getValue()).result().ifPresent(tag -> store.put("buffer", tag));
            data.put(i + "", store);
            i++;
        }
        return data;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		if(NuclearScienceCapabilities.CAPABILITY_TUNNELMAP == null) {
			return;
		}
		map.clear();

        int size = nbt.getInt("size");
        for (int i = 0; i < size; i++) {

            CompoundNBT stored = nbt.getCompound("" + i);
            
            Optional<Pair<TunnelFrequency, INBT>> freq = TunnelFrequency.CODEC.decode(NBTDynamicOps.INSTANCE, stored.get("id")).result();
            Optional<Pair<TunnelFrequencyBuffer, INBT>> val = TunnelFrequencyBuffer.CODEC.decode(NBTDynamicOps.INSTANCE, stored.getCompound("buffer")).result();

            if(freq.isPresent() && val.isPresent()) {
            	map.put(freq.get().getFirst(), val.get().getFirst());
            }
        }
		
	}

	@Override
	public HashMap<TunnelFrequency, TunnelFrequencyBuffer> getMap() {
		return map;
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
