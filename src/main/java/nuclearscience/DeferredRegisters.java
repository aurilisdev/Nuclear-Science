package nuclearscience;

import com.google.common.base.Supplier;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class DeferredRegisters {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.ID);
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, References.ID);
	public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, References.ID);

	static {
	}

	private static <T extends IForgeRegistryEntry<T>> Supplier<? extends T> supplier(T entry) {
		return () -> entry;
	}
}
