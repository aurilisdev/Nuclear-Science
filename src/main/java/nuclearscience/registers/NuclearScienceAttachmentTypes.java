package nuclearscience.registers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import nuclearscience.NuclearScience;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.api.quantumtunnel.TunnelFrequencyBuffer;
import nuclearscience.common.settings.NuclearConstants;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class NuclearScienceAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, NuclearScience.ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<HashMap<UUID, HashSet<TunnelFrequency>>>> CHANNEL_MAP = ATTACHMENT_TYPES.register("channelmap", () -> AttachmentType.builder(() -> new HashMap<UUID, HashSet<TunnelFrequency>>()).serialize(new IAttachmentSerializer<CompoundTag, HashMap<UUID, HashSet<TunnelFrequency>>>() {
        @Override
        public HashMap<UUID, HashSet<TunnelFrequency>> read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider provider) {
            HashMap<UUID, HashSet<TunnelFrequency>> data = new HashMap<>();

            int size = tag.getInt("size");
            for (int i = 0; i < size; i++) {

                CompoundTag stored = tag.getCompound("" + i);

                UUID id = UUIDUtil.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, stored.get("id"))).result().get();
                HashSet<TunnelFrequency> set = new HashSet<>();

                for(int j = 0; j < stored.getInt("setsize"); j++) {
                    set.add(TunnelFrequency.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, stored.get("" + j))).result().get());
                }

                data.put(id, set);
            }


            return data;
        }

        @Override
        public @Nullable CompoundTag write(HashMap<UUID, HashSet<TunnelFrequency>> attachment, HolderLookup.Provider provider) {
            CompoundTag data = new CompoundTag();
            int size = attachment.size();
            data.putInt("size", size);
            int i = 0;
            for (Map.Entry<UUID, HashSet<TunnelFrequency>> entry : attachment.entrySet()) {
                CompoundTag store = new CompoundTag();
                UUIDUtil.CODEC.encodeStart(NbtOps.INSTANCE, entry.getKey()).ifSuccess(tag -> store.put("id", tag));
                store.putInt("setsize", entry.getValue().size());
                int j = 0;
                for(TunnelFrequency freq : entry.getValue()) {
                    int finalJ = j;
                    TunnelFrequency.CODEC.encodeStart(NbtOps.INSTANCE, freq).ifSuccess(tag -> store.put("" + finalJ, tag));
                    j++;
                }
                data.put(i + "", store);
                i++;
            }
            return data;
        }
    }).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<HashMap<TunnelFrequency, TunnelFrequencyBuffer>>> TUNNEL_MAP = ATTACHMENT_TYPES.register("tunnelmap", () -> AttachmentType.builder(() -> new HashMap<TunnelFrequency, TunnelFrequencyBuffer>()).serialize(new IAttachmentSerializer<CompoundTag, HashMap<TunnelFrequency, TunnelFrequencyBuffer>>() {
        @Override
        public HashMap<TunnelFrequency, TunnelFrequencyBuffer> read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider provider) {
            HashMap<TunnelFrequency, TunnelFrequencyBuffer> data = new HashMap<>();

            int size = tag.getInt("size");
            for (int i = 0; i < size; i++) {

                CompoundTag stored = tag.getCompound("" + i);

                data.put(TunnelFrequency.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, stored.get("id"))).result().get(), TunnelFrequencyBuffer.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, stored.get("buffer"))).result().get());
            }


            return data;
        }

        @Override
        public @Nullable CompoundTag write(HashMap<TunnelFrequency, TunnelFrequencyBuffer> attachment, HolderLookup.Provider provider) {
            CompoundTag data = new CompoundTag();
            int size = attachment.size();
            data.putInt("size", size);
            int i = 0;
            for (Map.Entry<TunnelFrequency, TunnelFrequencyBuffer> entry : attachment.entrySet()) {
                CompoundTag store = new CompoundTag();
                TunnelFrequency.CODEC.encodeStart(NbtOps.INSTANCE, entry.getKey()).ifSuccess(tag -> store.put("id", tag));
                TunnelFrequencyBuffer.CODEC.encodeStart(NbtOps.INSTANCE, entry.getValue()).ifSuccess(tag -> store.put("buffer", tag));
                data.put(i + "", store);
                i++;
            }
            return data;
        }
    }).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> ANTIMATTER_TIMEONGROUND = ATTACHMENT_TYPES.register("timeonground", () -> AttachmentType.builder(() -> NuclearConstants.ANTIMATTER_TICKS_ON_GROUND).serialize(Codec.INT).build());


}
