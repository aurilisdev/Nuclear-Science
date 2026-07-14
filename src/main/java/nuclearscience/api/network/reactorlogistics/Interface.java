package nuclearscience.api.network.reactorlogistics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface.InterfaceType;
import voltaic.api.codec.StreamCodec;

public class Interface {

    private Interface(BlockPos pos, int type) {
        this(pos, GenericTileInterface.InterfaceType.values()[type]);
    }

    public static final Codec<Interface> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            //
            BlockPos.CODEC.fieldOf("position").forGetter(Interface::pos),
            //
            Codec.INT.fieldOf("type").xmap(val -> GenericTileInterface.InterfaceType.values()[val], InterfaceType::ordinal).forGetter(Interface::type)
            //
    ).apply(instance, Interface::new));

    public static final StreamCodec<PacketBuffer, Interface> STREAM_CODEC = new StreamCodec<PacketBuffer, Interface>() {
		
		@Override
		public void encode(PacketBuffer buffer, Interface value) {
			StreamCodec.BLOCK_POS.encode(buffer, value.pos);
			StreamCodec.INT.encode(buffer, value.type.ordinal());
		}
		
		@Override
		public Interface decode(PacketBuffer buffer) {
			return new Interface(StreamCodec.BLOCK_POS.decode(buffer), StreamCodec.INT.decode(buffer));
		}
	};
	
	private final BlockPos pos;
	private final GenericTileInterface.InterfaceType type;
	
	public Interface(BlockPos pos, GenericTileInterface.InterfaceType type) {
		this.pos = pos;
		this.type = type;
	}
	
	public BlockPos pos() {
		return pos;
	}
	
	public GenericTileInterface.InterfaceType type() {
		return type;
	}

}
