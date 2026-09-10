package nuclearscience.registers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import nuclearscience.NuclearScience;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.api.quantumtunnel.TunnelFrequencyBuffer;
import nuclearscience.common.settings.NuclearConfig;

public class NuclearScienceAttachmentTypes {

    private static final String SIZE = "size";
    private static final String ID = "id";
    private static final String SET_SIZE = "setsize";
    private static final String BUFFER = "buffer";

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister
	    .create(NeoForgeRegistries.ATTACHMENT_TYPES, NuclearScience.ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<HashMap<UUID, HashSet<TunnelFrequency>>>> CHANNEL_MAP = ATTACHMENT_TYPES
	    .register("channelmap", () -> AttachmentType
		    .<HashMap<UUID, HashSet<TunnelFrequency>>>builder(NuclearScienceAttachmentTypes::newHashMap)
		    .serialize(new IAttachmentSerializer<CompoundTag, HashMap<UUID, HashSet<TunnelFrequency>>>() {
			@Override
			public HashMap<UUID, HashSet<TunnelFrequency>> read(IAttachmentHolder holder, CompoundTag tag,
				HolderLookup.Provider provider) {
			    HashMap<UUID, HashSet<TunnelFrequency>> data = newHashMap();
			    int size = tag.getInt(SIZE);

			    for (int i = 0; i < size; i++) {
				CompoundTag stored = tag.getCompound(Integer.toString(i));
				UUID id = decode(UUIDUtil.CODEC, getRequired(stored, ID));
				HashSet<TunnelFrequency> frequencies = new HashSet<>();
				int setSize = stored.getInt(SET_SIZE);

				for (int j = 0; j < setSize; j++) {
				    frequencies.add(
					    decode(TunnelFrequency.CODEC, getRequired(stored, Integer.toString(j))));
				}

				data.put(id, frequencies);
			    }

			    return data;
			}

			@Override
			public @Nullable CompoundTag write(HashMap<UUID, HashSet<TunnelFrequency>> attachment,
				HolderLookup.Provider provider) {
			    CompoundTag data = new CompoundTag();
			    data.putInt(SIZE, attachment.size());

			    int i = 0;
			    for (Map.Entry<UUID, HashSet<TunnelFrequency>> entry : attachment.entrySet()) {
				CompoundTag stored = new CompoundTag();
				stored.put(ID, encode(UUIDUtil.CODEC, entry.getKey()));
				stored.putInt(SET_SIZE, entry.getValue().size());

				int j = 0;
				for (TunnelFrequency frequency : entry.getValue()) {
				    stored.put(Integer.toString(j++), encode(TunnelFrequency.CODEC, frequency));
				}

				data.put(Integer.toString(i++), stored);
			    }

			    return data;
			}
		    }).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<HashMap<TunnelFrequency, TunnelFrequencyBuffer>>> TUNNEL_MAP = ATTACHMENT_TYPES
	    .register("tunnelmap", () -> AttachmentType
		    .<HashMap<TunnelFrequency, TunnelFrequencyBuffer>>builder(NuclearScienceAttachmentTypes::newHashMap)
		    .serialize(
			    new IAttachmentSerializer<CompoundTag, HashMap<TunnelFrequency, TunnelFrequencyBuffer>>() {
				@Override
				public HashMap<TunnelFrequency, TunnelFrequencyBuffer> read(IAttachmentHolder holder,
					CompoundTag tag, HolderLookup.Provider provider) {
				    HashMap<TunnelFrequency, TunnelFrequencyBuffer> data = newHashMap();
				    int size = tag.getInt(SIZE);

				    for (int i = 0; i < size; i++) {
					CompoundTag stored = tag.getCompound(Integer.toString(i));
					TunnelFrequency frequency = decode(TunnelFrequency.CODEC,
						getRequired(stored, ID));
					TunnelFrequencyBuffer buffer = decode(TunnelFrequencyBuffer.CODEC,
						getRequired(stored, BUFFER));

					data.put(frequency, buffer);
				    }

				    return data;
				}

				@Override
				public CompoundTag write(HashMap<TunnelFrequency, TunnelFrequencyBuffer> attachment,
					HolderLookup.Provider provider) {
				    CompoundTag data = new CompoundTag();
				    data.putInt(SIZE, attachment.size());

				    int i = 0;
				    for (Map.Entry<TunnelFrequency, TunnelFrequencyBuffer> entry : attachment
					    .entrySet()) {
					CompoundTag stored = new CompoundTag();
					stored.put(ID, encode(TunnelFrequency.CODEC, entry.getKey()));
					stored.put(BUFFER, encode(TunnelFrequencyBuffer.CODEC, entry.getValue()));
					data.put(Integer.toString(i++), stored);
				    }

				    return data;
				}
			    })
		    .build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> ANTIMATTER_TIMEONGROUND = ATTACHMENT_TYPES
	    .register("timeonground",
		    () -> AttachmentType.builder(() -> NuclearConfig.getInstance().ANTIMATTER_TICKS_ON_GROUND.get())
			    .serialize(Codec.INT).build());

    private static <K, V> HashMap<K, V> newHashMap() {
	return new HashMap<>();
    }

    private static Tag getRequired(CompoundTag tag, String key) {
	Tag value = tag.get(key);

	if (value == null)
	    throw new IllegalStateException("Missing attachment field: " + key);

	return value;
    }

    private static <T> T decode(Codec<T> codec, Tag tag) {
	return codec.parse(new Dynamic<>(NbtOps.INSTANCE, tag)).getOrThrow();
    }

    private static <T> Tag encode(Codec<T> codec, T value) {
	return codec.encodeStart(NbtOps.INSTANCE, value).getOrThrow();
    }
}