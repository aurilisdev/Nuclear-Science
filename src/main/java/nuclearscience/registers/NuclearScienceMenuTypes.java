package nuclearscience.registers;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import nuclearscience.NuclearScience;
import nuclearscience.common.inventory.container.ContainerAtomicAssembler;
import nuclearscience.common.inventory.container.ContainerChemicalExtractor;
import nuclearscience.common.inventory.container.ContainerCloudChamber;
import nuclearscience.common.inventory.container.ContainerControlRodModule;
import nuclearscience.common.inventory.container.ContainerElectromagneticGateway;
import nuclearscience.common.inventory.container.ContainerFalloutScrubber;
import nuclearscience.common.inventory.container.ContainerFissionReactorCore;
import nuclearscience.common.inventory.container.ContainerFreezePlug;
import nuclearscience.common.inventory.container.ContainerGasCentrifuge;
import nuclearscience.common.inventory.container.ContainerMSRFuelPreProcessor;
import nuclearscience.common.inventory.container.ContainerMSReactorCore;
import nuclearscience.common.inventory.container.ContainerMoltenSaltSupplier;
import nuclearscience.common.inventory.container.ContainerMonitorModule;
import nuclearscience.common.inventory.container.ContainerNuclearBoiler;
import nuclearscience.common.inventory.container.ContainerParticleInjector;
import nuclearscience.common.inventory.container.ContainerQuantumTunnel;
import nuclearscience.common.inventory.container.ContainerRadioactiveProcessor;
import nuclearscience.common.inventory.container.ContainerRadioisotopeGenerator;
import nuclearscience.common.inventory.container.ContainerSupplyModule;
import nuclearscience.common.inventory.container.ContainerTeleporter;
import nuclearscience.common.inventory.container.ContainerThermometerModule;

public class NuclearScienceMenuTypes {
	public static final DeferredRegister<ContainerType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, NuclearScience.ID);

	public static final RegistryObject<ContainerType<ContainerGasCentrifuge>> CONTAINER_GASCENTRIFUGE = register("gascentrifuge", ContainerGasCentrifuge::new);
	public static final RegistryObject<ContainerType<ContainerNuclearBoiler>> CONTAINER_NUCLEARBOILER = register("nuclearboiler", ContainerNuclearBoiler::new);
	public static final RegistryObject<ContainerType<ContainerChemicalExtractor>> CONTAINER_CHEMICALEXTRACTOR = register("chemicalextractor", ContainerChemicalExtractor::new);
	public static final RegistryObject<ContainerType<ContainerRadioisotopeGenerator>> CONTAINER_RADIOISOTOPEGENERATOR = register("radioisotopegenerator", ContainerRadioisotopeGenerator::new);
	public static final RegistryObject<ContainerType<ContainerFreezePlug>> CONTAINER_FREEZEPLUG = register("freezeplug", ContainerFreezePlug::new);
	public static final RegistryObject<ContainerType<ContainerFissionReactorCore>> CONTAINER_REACTORCORE = register("reactorcore", ContainerFissionReactorCore::new);
	public static final RegistryObject<ContainerType<ContainerParticleInjector>> CONTAINER_PARTICLEINJECTOR = register("particleinjetor", ContainerParticleInjector::new);
	public static final RegistryObject<ContainerType<ContainerQuantumTunnel>> CONTAINER_QUANTUMTUNNEL = register("quantumcapacitor", ContainerQuantumTunnel::new);
	public static final RegistryObject<ContainerType<ContainerRadioactiveProcessor>> CONTAINER_RADIOACTIVEPROCESSOR = register("radioactiveprocessor", ContainerRadioactiveProcessor::new);
	public static final RegistryObject<ContainerType<ContainerMSRFuelPreProcessor>> CONTAINER_MSRFUELPREPROCESSOR = register("msrfuelpreprocessor", ContainerMSRFuelPreProcessor::new);
	public static final RegistryObject<ContainerType<ContainerMSReactorCore>> CONTAINER_MSRREACTORCORE = register("msrreactorcore", ContainerMSReactorCore::new);
	public static final RegistryObject<ContainerType<ContainerMoltenSaltSupplier>> CONTAINER_MOLTENSALTSUPPLIER = register("moltensaltsupplier", ContainerMoltenSaltSupplier::new);
	public static final RegistryObject<ContainerType<ContainerAtomicAssembler>> CONTAINER_ATOMICASSEMBLER = register("atomicassembler", ContainerAtomicAssembler::new);
	public static final RegistryObject<ContainerType<ContainerTeleporter>> CONTAINER_TELEPORTER = register("teleporter", ContainerTeleporter::new);
	public static final RegistryObject<ContainerType<ContainerCloudChamber>> CONTAINER_CLOUDCHAMBER = register("cloudchamber", ContainerCloudChamber::new);
	public static final RegistryObject<ContainerType<ContainerFalloutScrubber>> CONTAINER_FALLOUTSCRUBBER = register("falloutscrubber", ContainerFalloutScrubber::new);
	public static final RegistryObject<ContainerType<ContainerSupplyModule>> CONTAINER_SUPPLYMODULE = register("supplymodule", ContainerSupplyModule::new);
	public static final RegistryObject<ContainerType<ContainerControlRodModule>> CONTAINER_CONTROLRODMODULE = register("controlrodmodule", ContainerControlRodModule::new);
	public static final RegistryObject<ContainerType<ContainerMonitorModule>> CONTAINER_MONITORMODULE = register("monitormodule", ContainerMonitorModule::new);
	public static final RegistryObject<ContainerType<ContainerThermometerModule>> CONTAINER_THERMOMETERMODULE = register("thermometermodule", ContainerThermometerModule::new);
	public static final RegistryObject<ContainerType<ContainerElectromagneticGateway>> CONTAINER_ELECTROMAGNETICGATEWAY = register("electromagneticgateway", ContainerElectromagneticGateway::new);

	private static <T extends Container> RegistryObject<ContainerType<T>> register(String id, ContainerType.IFactory<T> supplier) {
		return MENU_TYPES.register(id, () -> new ContainerType<>(supplier));
	}

}
