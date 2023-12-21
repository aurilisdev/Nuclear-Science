package nuclearscience.registers;

import net.minecraft.world.inventory.MenuType;
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
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, References.ID);

	public static final RegistryObject<MenuType<ContainerGasCentrifuge>> CONTAINER_GASCENTRIFUGE = MENU_TYPES.register("gascentrifuge", () -> new MenuType<>(ContainerGasCentrifuge::new));
	public static final RegistryObject<MenuType<ContainerNuclearBoiler>> CONTAINER_NUCLEARBOILER = MENU_TYPES.register("nuclearboiler", () -> new MenuType<>(ContainerNuclearBoiler::new));
	public static final RegistryObject<MenuType<ContainerChemicalExtractor>> CONTAINER_CHEMICALEXTRACTOR = MENU_TYPES.register("chemicalextractor", () -> new MenuType<>(ContainerChemicalExtractor::new));
	public static final RegistryObject<MenuType<ContainerRadioisotopeGenerator>> CONTAINER_RADIOISOTOPEGENERATOR = MENU_TYPES.register("radioisotopegenerator", () -> new MenuType<>(ContainerRadioisotopeGenerator::new));
	public static final RegistryObject<MenuType<ContainerFreezePlug>> CONTAINER_FREEZEPLUG = MENU_TYPES.register("freezeplug", () -> new MenuType<>(ContainerFreezePlug::new));
	public static final RegistryObject<MenuType<ContainerReactorCore>> CONTAINER_REACTORCORE = MENU_TYPES.register("reactorcore", () -> new MenuType<>(ContainerReactorCore::new));
	public static final RegistryObject<MenuType<ContainerParticleInjector>> CONTAINER_PARTICLEINJECTOR = MENU_TYPES.register("particleinjetor", () -> new MenuType<>(ContainerParticleInjector::new));
	public static final RegistryObject<MenuType<ContainerQuantumCapacitor>> CONTAINER_QUANTUMCAPACITOR = MENU_TYPES.register("quantumcapacitor", () -> new MenuType<>(ContainerQuantumCapacitor::new));
	public static final RegistryObject<MenuType<ContainerRadioactiveProcessor>> CONTAINER_RADIOACTIVEPROCESSOR = MENU_TYPES.register("radioactiveprocessor", () -> new MenuType<>(ContainerRadioactiveProcessor::new));
	public static final RegistryObject<MenuType<ContainerMSRFuelPreProcessor>> CONTAINER_MSRFUELPREPROCESSOR = MENU_TYPES.register("msrfuelpreprocessor", () -> new MenuType<>(ContainerMSRFuelPreProcessor::new));
	public static final RegistryObject<MenuType<ContainerMSRReactorCore>> CONTAINER_MSRREACTORCORE = MENU_TYPES.register("msrreactorcore", () -> new MenuType<>(ContainerMSRReactorCore::new));
	public static final RegistryObject<MenuType<ContainerMoltenSaltSupplier>> CONTAINER_MOLTENSALTSUPPLIER = MENU_TYPES.register("moltensaltsupplier", () -> new MenuType<>(ContainerMoltenSaltSupplier::new));
	public static final RegistryObject<MenuType<ContainerAtomicAssembler>> CONTAINER_ATOMICASSEMBLER = MENU_TYPES.register("atomicassembler", () -> new MenuType<>(ContainerAtomicAssembler::new));

}
