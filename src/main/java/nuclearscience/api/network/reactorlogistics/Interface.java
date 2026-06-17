package nuclearscience.api.network.reactorlogistics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface.InterfaceType;

public record Interface(BlockPos pos, GenericTileInterface.InterfaceType type) {

    private Interface(BlockPos pos, int type) {
	this(pos, GenericTileInterface.InterfaceType.values()[type]);
    }

    public static final Codec<Interface> CODEC = RecordCodecBuilder.create(instance -> instance.group(
	    //
	    BlockPos.CODEC.fieldOf("position").forGetter(Interface::pos),
	    //
	    Codec.INT.fieldOf("type")
		    .xmap(val -> GenericTileInterface.InterfaceType.values()[val], InterfaceType::ordinal)
		    .forGetter(Interface::type)
    //
    ).apply(instance, Interface::new));

    public static final StreamCodec<ByteBuf, Interface> STREAM_CODEC = StreamCodec.composite(
	    //
	    BlockPos.STREAM_CODEC, Interface::pos,
	    //
	    ByteBufCodecs.INT, instance -> instance.type().ordinal(),
	    //
	    Interface::new);

}
