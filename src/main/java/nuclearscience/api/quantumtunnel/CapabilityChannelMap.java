package nuclearscience.api.quantumtunnel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
import voltaic.prefab.utilities.CodecUtils;

public class CapabilityChannelMap implements ICapabilityChannelMap, ICapabilitySerializable<CompoundNBT> {
	
	private final HashMap<UUID, HashSet<TunnelFrequency>> map = new HashMap<>();
	
	private final LazyOptional<ICapabilityChannelMap> lazyOptional = LazyOptional.of(() -> this);
	
	public CapabilityChannelMap() {
		
	}

	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if(cap == NuclearScienceCapabilities.CAPABILITY_CHANNELMAP) {
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
        for (Map.Entry<UUID, HashSet<TunnelFrequency>> entry : map.entrySet()) {
            CompoundNBT store = new CompoundNBT();
            CodecUtils.UUID_CODEC.encodeStart(NBTDynamicOps.INSTANCE, entry.getKey()).result().ifPresent(tag -> store.put("id", tag));
            store.putInt("setsize", entry.getValue().size());
            int j = 0;
            for(TunnelFrequency freq : entry.getValue()) {
                int finalJ = j;
                TunnelFrequency.CODEC.encodeStart(NBTDynamicOps.INSTANCE, freq).result().ifPresent(tag -> store.put("" + finalJ, tag));
                j++;
            }
            data.put(i + "", store);
            i++;
        }
        return data;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		if(NuclearScienceCapabilities.CAPABILITY_CHANNELMAP == null) {
			return;
		}
		map.clear();
		
		int size = nbt.getInt("size");
        for (int i = 0; i < size; i++) {

            CompoundNBT stored = nbt.getCompound("" + i);
            
            Optional<Pair<UUID, INBT>> id = CodecUtils.UUID_CODEC.decode(NBTDynamicOps.INSTANCE, stored.get("id")).result();
            if(id.isPresent()) {
            	HashSet<TunnelFrequency> set = new HashSet<>();

                for(int j = 0; j < stored.getInt("setsize"); j++) {
                	
                	TunnelFrequency.CODEC.decode(NBTDynamicOps.INSTANCE, stored.get("" + j)).result().ifPresent(pair -> set.add(pair.getFirst()));

                }

                map.put(id.get().getFirst(), set);
            }
            
        }
		
	}

	@Override
	public HashMap<UUID, HashSet<TunnelFrequency>> getMap() {
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
