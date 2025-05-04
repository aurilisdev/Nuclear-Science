package nuclearscience.api.quantumtunnel;

import java.util.Objects;
import java.util.UUID;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.entity.player.Player;
import voltaic.api.codec.StreamCodec;
import voltaic.prefab.utilities.CodecUtils;

public class TunnelFrequency {

    public static final UUID PUBLIC_ID = UUID.fromString("111aa11a-11a1-111a-aaaa-a1a11a111111");

    private static final UUID EMPTY_ID = UUID.fromString("111aa11a-11a1-111a-aaaa-a1a11a111112");

    public static final Codec<TunnelFrequency> CODEC = RecordCodecBuilder.create(instance -> instance.group(

            //
    		CodecUtils.UUID_CODEC.fieldOf("channelid").forGetter(TunnelFrequency::getId),
            //
    		CodecUtils.UUID_CODEC.fieldOf("creatorid").forGetter(TunnelFrequency::getCreatorId),
            //
            Codec.STRING.fieldOf("channelname").forGetter(TunnelFrequency::getName),
            //
            Codec.STRING.fieldOf("creatorfallbackname").forGetter(TunnelFrequency::getCreatorFallbackName),
            //
            Codec.INT.fieldOf("channeltype").forGetter(instance0 -> instance0.channelType)

    ).apply(instance, TunnelFrequency::new));

    public static final StreamCodec<ByteBuf, TunnelFrequency> STREAM_CODEC = new StreamCodec<ByteBuf, TunnelFrequency>() {
		
		@Override
		public void encode(ByteBuf buffer, TunnelFrequency value) {
			StreamCodec.UUID.encode(buffer, value.uuid);
			StreamCodec.UUID.encode(buffer, value.creator);
			StreamCodec.STRING.encode(buffer, value.name);
			StreamCodec.STRING.encode(buffer, value.creatorFallbackName);
			StreamCodec.INT.encode(buffer, value.channelType);
		}
		
		@Override
		public TunnelFrequency decode(ByteBuf buffer) {
			return new TunnelFrequency(StreamCodec.UUID.decode(buffer), StreamCodec.UUID.decode(buffer), StreamCodec.STRING.decode(buffer), StreamCodec.STRING.decode(buffer), StreamCodec.INT.decode(buffer));
		}
	};

    public static final TunnelFrequency NO_FREQUENCY = new TunnelFrequency(EMPTY_ID, EMPTY_ID, "", "", 0);

    private final UUID uuid;
    private final UUID creator;
    private String name = "";
    private final String creatorFallbackName;
    private final int channelType;

    private TunnelFrequency(UUID uuid, UUID creator, String name, String creatorFallbackName, int channelType) {
        this.uuid = uuid;
        this.creator = creator;
        this.name = name;
        this.creatorFallbackName = creatorFallbackName;
        this.channelType = channelType;
    }

    public UUID getId() {
        return uuid;
    }

    public UUID getCreatorId() {
        return creator;
    }

    public String getName() {
        return name;
    }

    public String getCreatorFallbackName() {
        return creatorFallbackName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FrequencyType getChannelType() {
        return FrequencyType.values()[channelType];
    }

    public static TunnelFrequency createPrivate(UUID id, Player creator, String name) {
        return new TunnelFrequency(id, creator.getUUID(), name, creator.getName().getString(), FrequencyType.PRIVATE.ordinal());
    }


    public static TunnelFrequency createPublic(UUID id, Player creator, String name) {
        return new TunnelFrequency(id, creator.getUUID(), name, creator.getName().getString(), FrequencyType.PUBLIC.ordinal());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TunnelFrequency other) {
            return uuid.equals(other.uuid) && channelType == other.channelType;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, channelType);
    }
}
