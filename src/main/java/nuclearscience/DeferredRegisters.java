package nuclearscience;

import com.google.common.base.Supplier;
import com.google.common.collect.Sets;

import electrodynamics.common.blockitem.BlockItemDescriptable;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;
import nuclearscience.common.block.BlockGasCentrifuge;
import nuclearscience.common.tile.TileGasCentrifuge;

public class DeferredRegisters {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.ID);
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, References.ID);
	public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, References.ID);
	public static Block blockGasCentrifuge;

	static {
		BLOCKS.register("gascentrifuge", supplier(blockGasCentrifuge = new BlockGasCentrifuge()));
		ITEMS.register("gascentrifuge", supplier(new BlockItemDescriptable(blockGasCentrifuge, new Item.Properties().group(References.CORETAB))));
	}
	public static final RegistryObject<TileEntityType<TileGasCentrifuge>> TILE_GASCENTRIFUGE = TILES.register("gascentrifuge",
			() -> new TileEntityType<>(TileGasCentrifuge::new, Sets.newHashSet(blockGasCentrifuge), null));

	@SubscribeEvent
	public static void onLoadEvent(FMLLoadCompleteEvent event) {
		// MachineRecipes.registerRecipe(TILE_BLASTCOMPRESSOR.get(), new
		// O2OProcessingRecipe(blockRawBlastproofWalling, blockBlastproofWalling));
	}

	private static <T extends IForgeRegistryEntry<T>> Supplier<? extends T> supplier(T entry) {
		return () -> entry;
	}
}
