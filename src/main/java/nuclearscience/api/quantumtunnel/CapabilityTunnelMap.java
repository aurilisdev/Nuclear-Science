package nuclearscience.api.quantumtunnel;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import nuclearscience.registers.NuclearScienceCapabilities;

public class CapabilityTunnelMap implements ICapabilityTunnelMap, ICapabilitySerializable<CompoundTag> {
	
	private final HashMap<TunnelFrequency, TunnelFrequencyBuffer> map = new HashMap<>();
	
	private final LazyOptional<ICapabilityTunnelMap> lazyOptional = LazyOptional.of(() -> this);

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if(cap == NuclearScienceCapabilities.CAPABILITY_TUNNELMAP) {
			return lazyOptional.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag data = new CompoundTag();
        int size = map.size();
        data.putInt("size", size);
        int i = 0;
        for (Map.Entry<TunnelFrequency, TunnelFrequencyBuffer> entry : map.entrySet()) {
            CompoundTag store = new CompoundTag();
            TunnelFrequency.CODEC.encodeStart(NbtOps.INSTANCE, entry.getKey()).result().ifPresent(tag -> store.put("id", tag));
            TunnelFrequencyBuffer.CODEC.encodeStart(NbtOps.INSTANCE, entry.getValue()).result().ifPresent(tag -> store.put("buffer", tag));
            data.put(i + "", store);
            i++;
        }
        return data;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		if(NuclearScienceCapabilities.CAPABILITY_TUNNELMAP == null) {
			return;
		}
		map.clear();

        int size = nbt.getInt("size");
        for (int i = 0; i < size; i++) {

            CompoundTag stored = nbt.getCompound("" + i);
            
            Optional<Pair<TunnelFrequency, Tag>> freq = TunnelFrequency.CODEC.decode(NbtOps.INSTANCE, stored.get("id")).result();
            Optional<Pair<TunnelFrequencyBuffer, Tag>> val = TunnelFrequencyBuffer.CODEC.decode(NbtOps.INSTANCE, stored.getCompound("buffer")).result();

            if(freq.isPresent() && val.isPresent()) {
            	map.put(freq.get().getFirst(), val.get().getFirst());
            }
        }
		
	}

	@Override
	public HashMap<TunnelFrequency, TunnelFrequencyBuffer> getMap() {
		return map;
	}

}
