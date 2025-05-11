package nuclearscience.prefab;

import java.util.Objects;

import net.minecraft.network.PacketBuffer;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import voltaic.prefab.properties.types.SinglePropertyType;

public class NuclearPropertyTypes {

    public static final SinglePropertyType<TunnelFrequency, PacketBuffer> TUNNEL_FREQUENCY = new SinglePropertyType<>(
            //
            Objects::equals,
            //
            TunnelFrequency.STREAM_CODEC,
            //
            TunnelFrequency.CODEC
            //
    );

}
