package nuclearscience.api.network.reactorlogistics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;
import voltaic.api.codec.StreamCodec;

public record Interface(BlockPos pos, GenericTileInterface.InterfaceType type) {

    private Interface(BlockPos pos, int type) {
        this(pos, GenericTileInterface.InterfaceType.values()[type]);
    }

    public static final Codec<Interface> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            //
            BlockPos.CODEC.fieldOf("position").forGetter(Interface::pos),
            //
            Codec.INT.fieldOf("type").xmap(val -> GenericTileInterface.InterfaceType.values()[val], val -> val.ordinal()).forGetter(Interface::type)
            //
    ).apply(instance, Interface::new));

    public static final StreamCodec<ByteBuf, Interface> STREAM_CODEC = new StreamCodec<ByteBuf, Interface>() {
		
		@Override
		public void encode(ByteBuf buffer, Interface value) {
			StreamCodec.BLOCK_POS.encode(buffer, value.pos);
			StreamCodec.INT.encode(buffer, value.type.ordinal());
		}
		
		@Override
		public Interface decode(ByteBuf buffer) {
			return new Interface(StreamCodec.BLOCK_POS.decode(buffer), StreamCodec.INT.decode(buffer));
		}
	};

}
