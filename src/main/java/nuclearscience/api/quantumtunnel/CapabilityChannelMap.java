package nuclearscience.api.quantumtunnel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.Direction;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import nuclearscience.registers.NuclearScienceCapabilities;

public class CapabilityChannelMap implements ICapabilityChannelMap, ICapabilitySerializable<CompoundTag> {
	
	private final HashMap<UUID, HashSet<TunnelFrequency>> map = new HashMap<>();
	
	private final LazyOptional<ICapabilityChannelMap> lazyOptional = LazyOptional.of(() -> this);
	
	public CapabilityChannelMap() {
		
	}

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if(cap == NuclearScienceCapabilities.CAPABILITY_CHANNELMAP) {
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
        for (Map.Entry<UUID, HashSet<TunnelFrequency>> entry : map.entrySet()) {
            CompoundTag store = new CompoundTag();
            UUIDUtil.CODEC.encodeStart(NbtOps.INSTANCE, entry.getKey()).result().ifPresent(tag -> store.put("id", tag));
            store.putInt("setsize", entry.getValue().size());
            int j = 0;
            for(TunnelFrequency freq : entry.getValue()) {
                int finalJ = j;
                TunnelFrequency.CODEC.encodeStart(NbtOps.INSTANCE, freq).result().ifPresent(tag -> store.put("" + finalJ, tag));
                j++;
            }
            data.put(i + "", store);
            i++;
        }
        return data;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		if(NuclearScienceCapabilities.CAPABILITY_CHANNELMAP == null) {
			return;
		}
		map.clear();
		
		int size = nbt.getInt("size");
        for (int i = 0; i < size; i++) {

            CompoundTag stored = nbt.getCompound("" + i);
            
            Optional<Pair<UUID, Tag>> id = UUIDUtil.CODEC.decode(NbtOps.INSTANCE, stored.get("id")).result();
            if(id.isPresent()) {
            	HashSet<TunnelFrequency> set = new HashSet<>();

                for(int j = 0; j < stored.getInt("setsize"); j++) {
                	
                	TunnelFrequency.CODEC.decode(NbtOps.INSTANCE, stored.get("" + j)).result().ifPresent(pair -> set.add(pair.getFirst()));

                }

                map.put(id.get().getFirst(), set);
            }
            
        }
		
	}

	@Override
	public HashMap<UUID, HashSet<TunnelFrequency>> getMap() {
		return map;
	}

}
