package nuclearscience;

import com.google.common.base.Supplier;
import com.google.common.collect.Sets;

import electrodynamics.common.blockitem.BlockItemDescriptable;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;
import nuclearscience.common.block.BlockChemicalBoiler;
import nuclearscience.common.block.BlockChemicalExtractor;
import nuclearscience.common.block.BlockGasCentrifuge;
import nuclearscience.common.block.BlockRadioisotopeGenerator;
import nuclearscience.common.fluid.FluidUraniumHexafluoride;
import nuclearscience.common.inventory.container.ContainerChemicalBoiler;
import nuclearscience.common.inventory.container.ContainerChemicalExtractor;
import nuclearscience.common.inventory.container.ContainerGasCentrifuge;
import nuclearscience.common.inventory.container.ContainerRadioisotopeGenerator;
import nuclearscience.common.item.ItemRadioactive;
import nuclearscience.common.tile.TileChemicalBoiler;
import nuclearscience.common.tile.TileChemicalExtractor;
import nuclearscience.common.tile.TileGasCentrifuge;
import nuclearscience.common.tile.TileRadioisotopeGenerator;

public class DeferredRegisters {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.ID);
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, References.ID);
	public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, References.ID);
	public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, References.ID);
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, References.ID);
	public static FluidUraniumHexafluoride fluidUraniumHexafluoride;
	public static BlockGasCentrifuge blockGasCentrifuge;
	public static BlockChemicalBoiler blockChemicalBoiler;
	public static BlockChemicalExtractor blockChemicalExtractor;
	public static BlockRadioisotopeGenerator blockRadioisotopeGenerator;

	static {
		BLOCKS.register("gascentrifuge", supplier(blockGasCentrifuge = new BlockGasCentrifuge()));
		BLOCKS.register("chemicalboiler", supplier(blockChemicalBoiler = new BlockChemicalBoiler()));
		BLOCKS.register("chemicalextractor", supplier(blockChemicalExtractor = new BlockChemicalExtractor()));
		BLOCKS.register("radioisotopegenerator", supplier(blockRadioisotopeGenerator = new BlockRadioisotopeGenerator()));
		ITEMS.register("gascentrifuge", supplier(new BlockItemDescriptable(blockGasCentrifuge, new Item.Properties().group(References.CORETAB))));
		ITEMS.register("chemicalboiler", supplier(new BlockItemDescriptable(blockChemicalBoiler, new Item.Properties().group(References.CORETAB))));
		ITEMS.register("chemicalextractor", supplier(new BlockItemDescriptable(blockChemicalExtractor, new Item.Properties().group(References.CORETAB))));
		ITEMS.register("radioisotopegenerator", supplier(new BlockItemDescriptable(blockRadioisotopeGenerator, new Item.Properties().group(References.CORETAB))));
		FLUIDS.register("fluiduraniumhexafluoride", supplier(fluidUraniumHexafluoride = new FluidUraniumHexafluoride()));
	}
	public static final RegistryObject<Item> ITEM_URANIUM235 = ITEMS.register("uranium235", supplier(new ItemRadioactive(new Item.Properties().group(References.CORETAB))));
	public static final RegistryObject<Item> ITEM_URANIUM238 = ITEMS.register("uranium238", supplier(new ItemRadioactive(new Item.Properties().group(References.CORETAB))));
	public static final RegistryObject<Item> ITEM_YELLOWCAKE = ITEMS.register("yellowcake", supplier(new ItemRadioactive(new Item.Properties().group(References.CORETAB))));
	public static final RegistryObject<Item> ITEM_CELLEMPTY = ITEMS.register("cellempty", supplier(new Item(new Item.Properties().group(References.CORETAB))));
	public static final RegistryObject<Item> ITEM_CELLDEUTERIUM = ITEMS.register("celldeuterium", supplier(new Item(new Item.Properties().group(References.CORETAB))));
	public static final RegistryObject<Item> ITEM_CELLTRITIUM = ITEMS.register("celltritium", supplier(new Item(new Item.Properties().group(References.CORETAB))));
	public static final RegistryObject<Item> ITEM_CELLHEAVYWATER = ITEMS.register("cellheavywater", supplier(new Item(new Item.Properties().group(References.CORETAB))));
	public static final RegistryObject<Item> ITEM_FUELHEUO2 = ITEMS.register("fuelheuo2", supplier(new ItemRadioactive(new Item.Properties().group(References.CORETAB).maxStackSize(1).defaultMaxDamage(24000))));
	public static final RegistryObject<Item> ITEM_FUELLEUO2 = ITEMS.register("fuelleuo2", supplier(new ItemRadioactive(new Item.Properties().group(References.CORETAB).maxStackSize(1).defaultMaxDamage(26000))));

	public static final RegistryObject<TileEntityType<TileGasCentrifuge>> TILE_GASCENTRIFUGE = TILES.register("gascentrifuge",
			() -> new TileEntityType<>(TileGasCentrifuge::new, Sets.newHashSet(blockGasCentrifuge), null));
	public static final RegistryObject<TileEntityType<TileChemicalBoiler>> TILE_CHEMICALBOILER = TILES.register("chemicalboiler",
			() -> new TileEntityType<>(TileChemicalBoiler::new, Sets.newHashSet(blockChemicalBoiler), null));
	public static final RegistryObject<TileEntityType<TileChemicalExtractor>> TILE_CHEMICALEXTRACTOR = TILES.register("chemicalextractor",
			() -> new TileEntityType<>(TileChemicalExtractor::new, Sets.newHashSet(blockChemicalExtractor), null));
	public static final RegistryObject<TileEntityType<TileRadioisotopeGenerator>> TILE_RADIOISOTOPEGENERATOR = TILES.register("radioisotopegenerator",
			() -> new TileEntityType<>(TileRadioisotopeGenerator::new, Sets.newHashSet(blockRadioisotopeGenerator), null));
	public static final RegistryObject<ContainerType<ContainerGasCentrifuge>> CONTAINER_GASCENTRIFUGE = CONTAINERS.register("gascentrifuge", () -> new ContainerType<>(ContainerGasCentrifuge::new));
	public static final RegistryObject<ContainerType<ContainerChemicalBoiler>> CONTAINER_CHEMICALBOILER = CONTAINERS.register("chemicalboiler", () -> new ContainerType<>(ContainerChemicalBoiler::new));
	public static final RegistryObject<ContainerType<ContainerChemicalExtractor>> CONTAINER_CHEMICALEXTRACTOR = CONTAINERS.register("chemicalextractor", () -> new ContainerType<>(ContainerChemicalExtractor::new));
	public static final RegistryObject<ContainerType<ContainerRadioisotopeGenerator>> CONTAINER_RADIOISOTOPEGENERATOR = CONTAINERS.register("radioisotopegenerator",
			() -> new ContainerType<>(ContainerRadioisotopeGenerator::new));

	@SubscribeEvent
	public static void onLoadEvent(FMLLoadCompleteEvent event) {
	}

	private static <T extends IForgeRegistryEntry<T>> Supplier<? extends T> supplier(T entry) {
		return () -> entry;
	}
}
