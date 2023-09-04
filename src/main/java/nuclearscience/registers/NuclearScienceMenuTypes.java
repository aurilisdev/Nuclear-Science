package nuclearscience.registers;

import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.MenuType.MenuSupplier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import nuclearscience.References;
import nuclearscience.common.inventory.container.ContainerAtomicAssembler;
import nuclearscience.common.inventory.container.ContainerChemicalExtractor;
import nuclearscience.common.inventory.container.ContainerFreezePlug;
import nuclearscience.common.inventory.container.ContainerGasCentrifuge;
import nuclearscience.common.inventory.container.ContainerMSRFuelPreProcessor;
import nuclearscience.common.inventory.container.ContainerMSRReactorCore;
import nuclearscience.common.inventory.container.ContainerMoltenSaltSupplier;
import nuclearscience.common.inventory.container.ContainerNuclearBoiler;
import nuclearscience.common.inventory.container.ContainerParticleInjector;
import nuclearscience.common.inventory.container.ContainerQuantumCapacitor;
import nuclearscience.common.inventory.container.ContainerRadioactiveProcessor;
import nuclearscience.common.inventory.container.ContainerRadioisotopeGenerator;
import nuclearscience.common.inventory.container.ContainerReactorCore;

public class NuclearScienceMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, References.ID);

	public static final RegistryObject<MenuType<ContainerGasCentrifuge>> CONTAINER_GASCENTRIFUGE = register("gascentrifuge", ContainerGasCentrifuge::new);
	public static final RegistryObject<MenuType<ContainerNuclearBoiler>> CONTAINER_NUCLEARBOILER = register("nuclearboiler", ContainerNuclearBoiler::new);
	public static final RegistryObject<MenuType<ContainerChemicalExtractor>> CONTAINER_CHEMICALEXTRACTOR = register("chemicalextractor", ContainerChemicalExtractor::new);
	public static final RegistryObject<MenuType<ContainerRadioisotopeGenerator>> CONTAINER_RADIOISOTOPEGENERATOR = register("radioisotopegenerator", ContainerRadioisotopeGenerator::new);
	public static final RegistryObject<MenuType<ContainerFreezePlug>> CONTAINER_FREEZEPLUG = register("freezeplug", ContainerFreezePlug::new);
	public static final RegistryObject<MenuType<ContainerReactorCore>> CONTAINER_REACTORCORE = register("reactorcore", ContainerReactorCore::new);
	public static final RegistryObject<MenuType<ContainerParticleInjector>> CONTAINER_PARTICLEINJECTOR = register("particleinjetor", ContainerParticleInjector::new);
	public static final RegistryObject<MenuType<ContainerQuantumCapacitor>> CONTAINER_QUANTUMCAPACITOR = register("quantumcapacitor", ContainerQuantumCapacitor::new);
	public static final RegistryObject<MenuType<ContainerRadioactiveProcessor>> CONTAINER_RADIOACTIVEPROCESSOR = register("radioactiveprocessor", ContainerRadioactiveProcessor::new);
	public static final RegistryObject<MenuType<ContainerMSRFuelPreProcessor>> CONTAINER_MSRFUELPREPROCESSOR = register("msrfuelpreprocessor", ContainerMSRFuelPreProcessor::new);
	public static final RegistryObject<MenuType<ContainerMSRReactorCore>> CONTAINER_MSRREACTORCORE = register("msrreactorcore", ContainerMSRReactorCore::new);
	public static final RegistryObject<MenuType<ContainerMoltenSaltSupplier>> CONTAINER_MOLTENSALTSUPPLIER = register("moltensaltsupplier", ContainerMoltenSaltSupplier::new);
	public static final RegistryObject<MenuType<ContainerAtomicAssembler>> CONTAINER_ATOMICASSEMBLER = register("atomicassembler", ContainerAtomicAssembler::new);
	
	private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String id, MenuSupplier<T> supplier) {
		return MENU_TYPES.register(id, () -> new MenuType<T>(supplier, FeatureFlags.VANILLA_SET));
	}

}
