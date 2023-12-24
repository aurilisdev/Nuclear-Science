package nuclearscience.registers;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
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
	public static final DeferredRegister<ContainerType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, References.ID);

	public static final RegistryObject<ContainerType<ContainerGasCentrifuge>> CONTAINER_GASCENTRIFUGE = MENU_TYPES.register("gascentrifuge", () -> new ContainerType<>(ContainerGasCentrifuge::new));
	public static final RegistryObject<ContainerType<ContainerNuclearBoiler>> CONTAINER_NUCLEARBOILER = MENU_TYPES.register("nuclearboiler", () -> new ContainerType<>(ContainerNuclearBoiler::new));
	public static final RegistryObject<ContainerType<ContainerChemicalExtractor>> CONTAINER_CHEMICALEXTRACTOR = MENU_TYPES.register("chemicalextractor", () -> new ContainerType<>(ContainerChemicalExtractor::new));
	public static final RegistryObject<ContainerType<ContainerRadioisotopeGenerator>> CONTAINER_RADIOISOTOPEGENERATOR = MENU_TYPES.register("radioisotopegenerator", () -> new ContainerType<>(ContainerRadioisotopeGenerator::new));
	public static final RegistryObject<ContainerType<ContainerFreezePlug>> CONTAINER_FREEZEPLUG = MENU_TYPES.register("freezeplug", () -> new ContainerType<>(ContainerFreezePlug::new));
	public static final RegistryObject<ContainerType<ContainerReactorCore>> CONTAINER_REACTORCORE = MENU_TYPES.register("reactorcore", () -> new ContainerType<>(ContainerReactorCore::new));
	public static final RegistryObject<ContainerType<ContainerParticleInjector>> CONTAINER_PARTICLEINJECTOR = MENU_TYPES.register("particleinjetor", () -> new ContainerType<>(ContainerParticleInjector::new));
	public static final RegistryObject<ContainerType<ContainerQuantumCapacitor>> CONTAINER_QUANTUMCAPACITOR = MENU_TYPES.register("quantumcapacitor", () -> new ContainerType<>(ContainerQuantumCapacitor::new));
	public static final RegistryObject<ContainerType<ContainerRadioactiveProcessor>> CONTAINER_RADIOACTIVEPROCESSOR = MENU_TYPES.register("radioactiveprocessor", () -> new ContainerType<>(ContainerRadioactiveProcessor::new));
	public static final RegistryObject<ContainerType<ContainerMSRFuelPreProcessor>> CONTAINER_MSRFUELPREPROCESSOR = MENU_TYPES.register("msrfuelpreprocessor", () -> new ContainerType<>(ContainerMSRFuelPreProcessor::new));
	public static final RegistryObject<ContainerType<ContainerMSRReactorCore>> CONTAINER_MSRREACTORCORE = MENU_TYPES.register("msrreactorcore", () -> new ContainerType<>(ContainerMSRReactorCore::new));
	public static final RegistryObject<ContainerType<ContainerMoltenSaltSupplier>> CONTAINER_MOLTENSALTSUPPLIER = MENU_TYPES.register("moltensaltsupplier", () -> new ContainerType<>(ContainerMoltenSaltSupplier::new));
	public static final RegistryObject<ContainerType<ContainerAtomicAssembler>> CONTAINER_ATOMICASSEMBLER = MENU_TYPES.register("atomicassembler", () -> new ContainerType<>(ContainerAtomicAssembler::new));

}
