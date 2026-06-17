package nuclearscience.datagen.client;

import net.minecraft.data.PackOutput;
import nuclearscience.NuclearScience;
import nuclearscience.common.block.subtype.SubtypeElectromagent;
import nuclearscience.common.block.subtype.SubtypeIrradiatedBlock;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.block.subtype.SubtypeRadiationShielding;
import nuclearscience.common.block.subtype.SubtypeReactorLogisticsCable;
import nuclearscience.common.recipe.categories.fluiditem2gas.NuclearBoilerRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.ChemicalExtractorRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.MSRFuelPreProcessorRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.RadioactiveProcessorRecipe;
import nuclearscience.common.recipe.categories.item2item.FissionReactorRecipe;
import nuclearscience.common.recipe.categories.item2item.FuelReprocessorRecipe;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceFluids;
import nuclearscience.registers.NuclearScienceGases;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceSounds;
import voltaic.datagen.utils.client.BaseLangKeyProvider;

public class NuclearScienceLangKeyProvider extends BaseLangKeyProvider {

    public NuclearScienceLangKeyProvider(PackOutput output, Locale locale) {
	super(output, locale, NuclearScience.ID);
    }

    @Override
    protected void addTranslations() {

	switch (locale) {
	case EN_US:
	default:

	    addCreativeTab("main", "Nuclear Science");

	    addItem(NuclearScienceItems.ITEM_URANIUM235, "Uranium-235");
	    addItem(NuclearScienceItems.ITEM_URANIUM238, "Uranium-238");
	    addItem(NuclearScienceItems.ITEM_PLUTONIUM239, "Plutonium-239");
	    addItem(NuclearScienceItems.ITEM_POLONIUM210, "Polonium-210");
	    addItem(NuclearScienceItems.ITEM_POLONIUM210_CHUNK, "Polonium-210 Chunk");
	    addItem(NuclearScienceItems.ITEM_ACTINIUM225, "Actinium-225");
	    addItem(NuclearScienceItems.ITEM_ACTINIUMOXIDE, "Actinium Trioxide");
	    addItem(NuclearScienceItems.ITEM_LIFHT4PUF3, "LiF-ThF4-UF4 Salt");
	    addItem(NuclearScienceItems.ITEM_FLINAK, "FLiNaK Salt");
	    addItem(NuclearScienceItems.ITEM_YELLOWCAKE, "Yellowcake");
	    addItem(NuclearScienceItems.ITEM_FISSILEDUST, "Fissile Dust");
	    addItem(NuclearScienceItems.ITEM_PLUTONIUMOXIDE, "Plutonium Tetroxide");
	    addItem(NuclearScienceItems.ITEM_THORIANITEDUST, "Thorianite Dust");
	    addItem(NuclearScienceItems.ITEM_FISSILE_SALT, "Fissile Salt");

	    addItem(NuclearScienceItems.ITEM_CELLEMPTY, "Empty Cell");
	    addItem(NuclearScienceItems.ITEM_CELLDEUTERIUM, "Deuterium Cell");
	    addItem(NuclearScienceItems.ITEM_CELLTRITIUM, "Tritium Cell");
	    addItem(NuclearScienceItems.ITEM_CELLHEAVYWATER, "Heavy Water Cell");
	    addItem(NuclearScienceItems.ITEM_CELLELECTROMAGNETIC, "Electromagnetic Cell");
	    addItem(NuclearScienceItems.ITEM_CELLANTIMATTERSMALL, "Anti-Matter Cell (500 mg)");
	    addItem(NuclearScienceItems.ITEM_CELLANTIMATTERLARGE, "Anti-Matter Cell (4 g)");
	    addItem(NuclearScienceItems.ITEM_CELLANTIMATTERVERYLARGE, "Anti-Matter Cell (12 g)");
	    addItem(NuclearScienceItems.ITEM_CELLDARKMATTER, "Dark Matter Cell");
	    addItem(NuclearScienceItems.ITEM_FUELHEUO2, "Highly Enriched Fuel Rod");
	    addItem(NuclearScienceItems.ITEM_FUELLEUO2, "Enriched Fuel Rod");
	    addItem(NuclearScienceItems.ITEM_FUELSPENT, "Spent Fuel Rod");
	    addItem(NuclearScienceItems.ITEM_FUELPLUTONIUM, "Plutonium Fuel Rod");

	    addItem(NuclearScienceItems.ITEM_GEIGERCOUNTER, "Geiger Counter");

	    addItem(NuclearScienceItems.ITEM_HAZMATHELMET, "Hazmat Hood");
	    addItem(NuclearScienceItems.ITEM_HAZMATPLATE, "Hazmat Shroud");
	    addItem(NuclearScienceItems.ITEM_HAZMATLEGS, "Hazmat Leggings");
	    addItem(NuclearScienceItems.ITEM_HAZMATBOOTS, "Hazmat Boots");

	    addItem(NuclearScienceItems.ITEM_REINFORCEDHAZMATHELMET, "Reinforced Hazmat Hood");
	    addItem(NuclearScienceItems.ITEM_REINFORCEDHAZMATPLATE, "Reinforced Hazmat Shroud");
	    addItem(NuclearScienceItems.ITEM_REINFORCEDHAZMATLEGS, "Reinforced Hazmat Leggings");
	    addItem(NuclearScienceItems.ITEM_REINFORCEDHAZMATBOOTS, "Reinforced Hazmat Boots");

	    addItem(NuclearScienceItems.ITEM_ANTIDOTE, "Antidote");
	    addItem(NuclearScienceItems.ITEM_IODINETABLET, "Iodine Tablet");
	    addItem(NuclearScienceItems.ITEM_FREQUENCYCARD, "Frequency Card");
	    addItem(NuclearScienceItems.ITEM_CANISTERLEAD, "Lead-Lined Canister");

	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.gascentrifuge),
		    "Gas Centrifuge");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.nuclearboiler),
		    "Nuclear Boiler");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.chemicalextractor),
		    "Chemical Extractor");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.radioisotopegenerator),
		    "Radioisotope Generator");
	    addBlock(NuclearScienceBlocks.BLOCK_TURBINE, "Steam Turbine");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.steamfunnel),
		    "Steam Funnel");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissionreactorcore),
		    "Fission Reactor Core");
	    addBlock(NuclearScienceBlocks.BLOCKS_ELECTROMAGENT.getValue(SubtypeElectromagent.electromagnet),
		    "Electromagnet");
	    addBlock(NuclearScienceBlocks.BLOCKS_ELECTROMAGENT.getValue(SubtypeElectromagent.electromagneticglass),
		    "Electromagnetic Glass");
	    addBlock(NuclearScienceBlocks.BLOCK_ELECTORMAGNETICBOOSTER, "Electromagnetic Booster");
	    addBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH, "Electromagnetic Switch");
	    addBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICGATEWAY, "Electromagnetic Gateway");
	    addBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICDIODE, "Electromagnetic Diode");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusionreactorcore),
		    "Fusion Reactor Core");
	    addBlock(NuclearScienceBlocks.BLOCK_PLASMA, "Plasma");
	    addBlock(NuclearScienceBlocks.BLOCK_MELTEDREACTOR, "Melted Reactor Core");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.particleinjector),
		    "Particle Injector");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.quantumcapacitor),
		    "Quantum Tunnel");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.teleporter),
		    "Teleporter");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioncontrolrod),
		    "Fission Control Rod");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fuelreprocessor),
		    "Fuel Reprocessor");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.radioactiveprocessor),
		    "Radioactive Processor");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msrfuelpreprocessor),
		    "MSR Fuel Pre-Processor");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.freezeplug),
		    "MSR Freeze Plug");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msreactorcore),
		    "MS Reactor Core");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.mscontrolrod),
		    "MS Control Rod");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.heatexchanger),
		    "Heat Exchanger");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.siren), "Siren");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.atomicassembler),
		    "Atomic Assembler");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.moltensaltsupplier),
		    "Molten Salt Supplier");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.logisticscontroller),
		    "RL Controller");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioninterface),
		    "Fission Interface");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msinterface),
		    "MS Interface");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusioninterface),
		    "Fusion Interface");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.controlrodmodule),
		    "Control Rod Module");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.supplymodule),
		    "Supply Module");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.monitormodule),
		    "Monitor Module");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.thermometermodule),
		    "Thermometer Module");
	    addBlock(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.soil), "Irradiated Soil");
	    addBlock(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.grass), "Irradiated Grass");
	    addBlock(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.petrifiedwood),
		    "Petrified Wood");
	    addBlock(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.base),
		    "Lead-lined Block");
	    addBlock(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.door),
		    "Lead-lined Door");
	    addBlock(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.glass),
		    "Lead-lined Glass");
	    addBlock(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.trapdoor),
		    "Lead-lined Trapdoor");

	    addBlock(NuclearScienceBlocks.BLOCKS_MOLTENSALTPIPE.getValue(SubtypeMoltenSaltPipe.vanadiumsteelceramic),
		    "VS-Ceramic Pipe");

	    addBlock(NuclearScienceBlocks.BLOCKS_REACTORLOGISTICSCABLE.getValue(SubtypeReactorLogisticsCable.base),
		    "RL Cable");

	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.chunkloader),
		    "Chunkloader");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.cloudchamber),
		    "Cloud Chamber");
	    addBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.falloutscrubber),
		    "Fallout Scrubber");

	    addFluid(NuclearScienceFluids.FLUID_IODINESOLUTION, "Iodine Solution");
	    addFluid(NuclearScienceFluids.FLUID_METHANOL, "Methanol");
	    addFluid(NuclearScienceFluids.FLUID_DECONTAMINATIONFOAM, "Decontamination Foam");
	    addFluid(NuclearScienceFluids.FLUID_URANIUMHEXAFLUORIDE, "Liquid Uranium Hexafluoride");

	    addGas(NuclearScienceGases.URANIUM_HEXAFLUORIDE, "Uranium Hexafluoride");

	    addContainer("gascentrifuge", "Gas Centrifuge");
	    addContainer("nuclearboiler", "Nuclear Boiler");
	    addContainer("chemicalextractor", "Chemical Extractor");
	    addContainer("radioisotopegenerator", "Radioisotope Generator");
	    addContainer("reactorcore", "Fission Reactor Core");
	    addContainer("particleinjector", "Particle Injector");
	    addContainer("quantumcapacitor", "Quantum Tunnel");
	    addContainer("fuelreprocessor", "Fuel Reprocessor");
	    addContainer("radioactiveprocessor", "Radioactive Processor");
	    addContainer("msrfuelpreprocessor", "MS Fuel Pre-Processor");
	    addContainer("freezeplug", "MSR Freeze Plug");
	    addContainer("msrreactorcore", "MSR Reactor Core");
	    addContainer("moltensaltsupplier", "Molten Salt Supplier");
	    addContainer("atomicassembler", "Atomic Assembler");
	    addContainer("teleporter", "Teleporter");
	    addContainer("cloudchamber", "Cloud Chamber");
	    addContainer("falloutscrubber", "Fallout Scrubber");
	    addContainer("supplymodule", "Supply Module");
	    addContainer("monitormodule", "Monitor Module");
	    addContainer("controlrodmodule", "Control Rod Module");
	    addContainer("thermometermodule", "Thermometer Module");
	    addContainer("electromagneticgateway", "Electromagnetic Gateway");

	    addGuiLabel("machine.usage", "Usage: %s");
	    addGuiLabel("machine.voltage", "Voltage: %s");
	    addGuiLabel("machine.output", "Output: %s");
	    addGuiLabel("machine.current", "Current: %s");
	    addGuiLabel("machine.transfer", "Output: %s");
	    addGuiLabel("machine.stored", "Stored: %s");
	    addGuiLabel("particleinjector.charge", "Charge: %s");
	    addGuiLabel("particleinjector.matter", "Matter");
	    addGuiLabel("particleinjector.cells", "Cells");
	    addGuiLabel("fissionreactor.deuterium", "Deuterium");
	    addGuiLabel("quantumcapacitor.joulesinput", "Joules");
	    addGuiLabel("quantumcapacitor.frequency", "Frequency");
	    addGuiLabel("msreactor.status", "Status:");
	    addGuiLabel("msreactor.status.good", "Good");
	    addGuiLabel("msreactor.status.nofreezeplug", "Freeze Plug Missing");
	    addGuiLabel("msreactor.status.wastefull", "Waste Full");
	    addGuiLabel("msreactor.warning", "Warning:");
	    addGuiLabel("msreactor.warning.none", "None");
	    addGuiLabel("msreactor.warning.freezeoff", "Freeze Plug Off");
	    addGuiLabel("msreactor.warning.overheat", "OVERHEATING");
	    addGuiLabel("saltsupplier.waste", "Waste");
	    addGuiLabel("saltsupplier.wastecont", "Waste: %s");

	    addGuiLabel("displayunit.speedoflightsymbol", "C");
	    addGuiLabel("displayunit.speedoflightname", "C");
	    addGuiLabel("displayunit.speedoflightnameplural", "C");

	    addGuiLabel("freezeplug.status", "Status: %s");
	    addGuiLabel("freezeplug.frozen", "FROZEN");
	    addGuiLabel("freezeplug.off", "OFF");
	    addGuiLabel("freezeplug.saltbonus", "Thermal Bonus: %s");

	    addGuiLabel("teleporter.import", "Import");
	    addGuiLabel("teleporter.x", "X:");
	    addGuiLabel("teleporter.y", "Y:");
	    addGuiLabel("teleporter.z", "Z:");
	    addGuiLabel("teleporter.reset", "Reset");

	    addGuiLabel("quantumtunnel.public", "Public");
	    addGuiLabel("quantumtunnel.private", "Private");
	    addGuiLabel("quantumtunnel.delete", "Delete");
	    addGuiLabel("quantumtunnel.edit", "Edit");
	    addGuiLabel("quantumtunnel.enable", "Enable");
	    addGuiLabel("quantumtunnel.disable", "Disable");
	    addGuiLabel("quantumtunnel.none", "None");
	    addGuiLabel("quantumtunnel.newfrequency", "New Frequency");
	    addGuiLabel("quantumtunnel.name", "Name");
	    addGuiLabel("quantumtunnel.create", "Create");
	    addGuiLabel("quantumtunnel.cancel", "Cancel");
	    addGuiLabel("quantumtunnel.save", "Save");
	    addGuiLabel("quantumtunnel.frequencytype", "Frequency Type");
	    addGuiLabel("quantumtunnel.editfrequency", "Edit Frequency");

	    addGuiLabel("cloudchamber.status", "Status: %s");
	    addGuiLabel("cloudchamber.offline", "Offline");
	    addGuiLabel("cloudchamber.active", "Active");
	    addGuiLabel("cloudchamber.detected", "Detected: %s");

	    addGuiLabel("logisticsnetwork.network", "Network");
	    addGuiLabel("logisticsnetwork.unlinked", "Unlinked...");
	    addGuiLabel("logisticsnetwork.temperature", "Temperature: %s");
	    addGuiLabel("logisticsnetwork.fuel", "Fuel");
	    addGuiLabel("logisticsnetwork.other", "Other");
	    addGuiLabel("logisticsnetwork.status", "Status: %s");
	    addGuiLabel("logisticsnetwork.statusgood", "GOOD");
	    addGuiLabel("logisticsnetwork.statusnofuel", "NO FUEL");
	    addGuiLabel("logisticsnetwork.statusoverheat", "OVERHEATING");
	    addGuiLabel("logisticsnetwork.statusnopower", "NO POWER");
	    addGuiLabel("logisticsnetwork.waste", "Waste");
	    addGuiLabel("logisticsnetwork.deuterium", "Deuterium");
	    addGuiLabel("logisticsnetwork.tritium", "Tritium");
	    addGuiLabel("logisticsnetwork.power", "Energy Satisfaction");
	    addGuiLabel("logisticsnetwork.outputmode", "Output Mode");
	    addGuiLabel("logisticsnetwork.signalmode", "Signal Mode");
	    addGuiLabel("logisticsnetwork.targettemp", "Target Temperature");
	    addGuiLabel("logisticsnetwork.signalstrength", "Signal Strength: %s");
	    addGuiLabel("logisticsnetwork.signalnormal", "Normal");
	    addGuiLabel("logisticsnetwork.signalinverted", "Inverted");
	    addGuiLabel("logisticsnetwork.modeconstant", "Constant");
	    addGuiLabel("logisticsnetwork.modebuildup", "Build-Up");

	    addGuiLabel("electromagneticswitch.targetspeed", "Target Speed");

	    addDamageSource("radiation", "%s just did a speedrun of evolution!");
	    addDamageSource("plasma", "%s was ionized!");
	    add("effect.nuclearscience.radiation", "Radiation");
	    add("effect.nuclearscience.radiationresistance", "Radiation Resistance");

	    addChatMessage("geigercounter.nopower", "No Power");

	    addTooltip("frequencycard.linked", "Linked to %s");
	    addTooltip("frequencycard.notag", "No Link");
	    addTooltip("deuteriumlevel", "Deuterium: %s");
	    addTooltip("tritiumlevel", "Tritium: %s");
	    addTooltip("steamfunneluse", "Collects and emits steam");
	    addTooltip("fissionreactor.maxtemp", "Maximum: %s");
	    addTooltip("fissionreactor.currtemp", "Current: %s");
	    addTooltip("fissionreactor.warning", "OVERHEATING!");

	    addTooltip("quantumtunnel.ioconfig", "I/O Config");
	    addTooltip("quantumtunnel.input", "Input");
	    addTooltip("quantumtunnel.output", "Output");
	    addTooltip("quantumtunnel.none", "None");
	    addTooltip("quantumtunnel.createnew", "New Frequency");
	    addTooltip("quantumtunnel.buffer", "Buffer");

	    addTooltip("logisticsnetwork.linkinterface", "Link Interface");

	    addTooltip("particleinjector.charge", "Charge: %1$s / %2$s");
	    addTooltip("particleinjector.gatewaymode", "Toggle Gateway Mode");
	    addTooltip("particleinjector.gatewayenabled", "Enabled");
	    addTooltip("particleinjector.gatewaydisabled", "Disabled");
	    addTooltip("particleinjector.particle1speed", "Particle 1: %s");
	    addTooltip("particleinjector.particle2speed", "Particle 2: %s");

	    addSubtitle(NuclearScienceSounds.SOUND_GASCENTRIFUGE, "Gas Centrifuge spins");
	    addSubtitle(NuclearScienceSounds.SOUND_NUCLEARBOILER, "Nuclear Boiler boils");
	    addSubtitle(NuclearScienceSounds.SOUND_TURBINE, "Steam Turbine spins");
	    addSubtitle(NuclearScienceSounds.SOUND_SIREN, "Siren blares!");
	    addSubtitle(NuclearScienceSounds.SOUND_GEIGERCOUNTER_1, "Geiger Counter ticks!");
	    addSubtitle(NuclearScienceSounds.SOUND_GEIGERCOUNTER_2, "Geiger Counter ticks!");
	    addSubtitle(NuclearScienceSounds.SOUND_GEIGERCOUNTER_3, "Geiger Counter ticks!");
	    addSubtitle(NuclearScienceSounds.SOUND_GEIGERCOUNTER_4, "Geiger Counter ticks!");
	    addSubtitle(NuclearScienceSounds.SOUND_GEIGERCOUNTER_5, "Geiger Counter ticks!");
	    addSubtitle(NuclearScienceSounds.SOUND_GEIGERCOUNTER_6, "Geiger Counter ticks!");
	    addSubtitle(NuclearScienceSounds.SOUND_LOGISTICSCONTROLLER, "Reactor Logistics Controller runs");
	    addSubtitle(NuclearScienceSounds.SOUND_PARTICLE, "Particle Accelerates");

	    addJei(NuclearBoilerRecipe.RECIPE_GROUP, "Nuclear Boiler");
	    addJei(ChemicalExtractorRecipe.RECIPE_GROUP, "Chemical Extractor");
	    addJei(MSRFuelPreProcessorRecipe.RECIPE_GROUP, "MSR Fuel Pre-Processor");
	    addJei(RadioactiveProcessorRecipe.RECIPE_GROUP, "Radioactive Processor");
	    addJei(FissionReactorRecipe.RECIPE_GROUP, "Fission Reactor");
	    addJei(FuelReprocessorRecipe.RECIPE_GROUP, "Fuel Reprocessor");
	    addJei("gascentrifuge", "Gas Centrifuge");
	    addJei("particalacceleratorantimatter", "Particle Collision");
	    addJei("particalacceleratordarkmatter", "Particle Collision");

	    addGuidebook(NuclearScience.ID, "Nuclear Science");

	    addGuidebook("chapter.radiation", "Radiation");
	    addGuidebook("chapter.radiation.l1.1",
		    "Radiation is one of the key mechanics of Nuclear Science. Radiation is measured in rads and is ray casting-based. Each radiation source will be cast out to entities within a certain radius from the source position. If you are outside the radius, you will not be affected by that particular source. Below, the following"
			    + " radiation source has a value of 300 Rads and affects entities in a 3 block radius. By standing outside that 3 block radius, you are not afflicted by it:");

	    addGuidebook("chapter.radiation.l1.2",
		    "It is important to understand that the strength of a radiation source does not diminish while you are inside its area of affect. Further, radiation application takes into account how tall an entity is. For example, the below source is only 300 Rads strong, but because the player is two blocks tall, the total amount absorbed is "
			    + "600 rads:");
	    addGuidebook("chapter.radiation.l1.3",
		    "It is worth noting before continuing however that the ray-casting system isn't perfect, and it will struggle in some edge cases!");
	    addGuidebook("chapter.radiation.l2.1",
		    "But you may be asking \"what emits radiation?\" Radiation is emitted by various items, blocks, and machines. To see how radioactive an item is, hover over it in your inventory and hold the Control key:");
	    addGuidebook("chapter.radiation.l2.2",
		    "It is left up to the player to discover what other sources of radiation there are! A good rule of thumb though is that if it works with radioactive materials, there is a good chance it emits radiation. A useful tool to craft early on is the %1$s. The Geiger Counter is an invaluable tool that will allow you to view the radiation "
			    + "exposure at a current location. When in your inventory, it will tick if exposed to radiation, making it a useful warning device. Note that Geiger Counter will consume %2$s Joules for every tick it detects radiation.");

	    addGuidebook("chapter.radiation.l3",
		    "Up until now, we have discussed how radiation works and what emits it, but there has been no mention of if it is possible to mitigate it. Radiation is lethal if exposed to it for long enough. Fortunately, there are various methods offered to deal with this.");
	    addGuidebook("chapter.radiation.l4",
		    "The first option is to consume an %1$s. Iodine Tablets offer limited radiation resistance, and are capable of protecting you from up to %2$s Rads for up to %3$s Minutes per pill. Even if you are exposed to radiation greater than what the Iodine is rated for, the tablet will still reduce received rads by %4$s. Keep in mind however "
			    + "that the effect is limited, so you will need to keep eating Iodine Tablets!");
	    addGuidebook("chapter.radiation.l5",
		    "The next option is to wear a %1$s. The Hazmat Suit is far more robust than an Iodine Tablet, and will protect you from radiation at the expense of durability. You can craft a reinforced variant with more durability, but ultimately it too can break. Fortunately, Hazmat Suits can be repaired using a %2$s in an Anvil.");
	    addGuidebook("chapter.radiation.hazmatsuit", "Hazmat Suit");
	    addGuidebook("chapter.radiation.l6.1",
		    "But all these options seem temporary you might be saying. \"Surely there has to be a more permanent method.\" Well, you're in luck! The third option for radiation mitigation is to use blocks that provide radiation shielding. You can view how much shielding a block provides by hovering over it in your inventory and holding the "
			    + "Shift key:");
	    addGuidebook("chapter.radiation.l6.2",
		    "Shielding functions as an impediment, and will reduce radiation by the value displayed. When placing radiation shielding, it is important to remember that the radiation system takes height into account as discussed earlier. Remember the 300 Rad source we previously used? If you only place one block of shielding, it will only "
			    + "shield you from half the radiation:");
	    addGuidebook("chapter.radiation.l6.3",
		    "If you wanted to fully shield yourself using blocks, you would need to use two due to the player being two blocks tall:");

	    addGuidebook("chapter.radiation.l7",
		    "But what to do if all of this fails? What happens if you get exposed to radiation even after all of these precautions. To remove the radiation effect, you can consume %s, which will remove the radiation effect completely. Antidote is extracted from fish for reference.");
	    addGuidebook("chapter.radiation.l8",
		    "Nuclear Science also offers machines that can help mitigate radiation exposure. The first of these is the %1$s. The Cloud Chamber, if powered and supplied with liquid Methanol, will show all radiation sources around it in a %2$s block radius. Sources will appear as a highlighted outline. The Chamber can be toggled using a redstone "
			    + "signal, and will identify how many sources it has detected via its GUI.");

	    addGuidebook("chapter.radiation.l9",
		    """
		    	The second machine that is offered is the %1$s. To understand the Fallout Scrubber, you must first understand one of the underlying mechanics for radiation. Nuclear has three types of radiation sources: permanent, temporary, and fading. Permanent sources will never dissipate and are permanent scars on the \
		    	environment. Temporary sources are similar to permanent sources, however they will eventually dissipate over time. The final source is a fading radiation source. Fading radiation sources will slowly be whittled down by their environment. The default dissipation rate is %2$s Rad/tick, and can be altered in the settings. The Fallout Scrubber will increase the rate\
		    	of dissipation in a %3$s block radius around it by %4$s Rad/tick. This effect stacks with other scrubbers, but will only affect fading radiation sources. The Scrubber needs Water and Decontamination Foam to work.""");

	    addGuidebook("chapter.turbines", "Steam Turbines");
	    addGuidebook("chapter.turbines.l1",
		    "Steam Turbines are what actually produce power in Nuclear Science. Turbines will produce at different voltages depending on what temperature of steam is venting through them. Turbines produce the following voltages under the following temperatures: ");
	    addGuidebook("chapter.turbines.tempvoltage", "%1$s C : %2$s V");
	    addGuidebook("chapter.turbines.l2",
		    "This relationship is important, as Turbines produce more power the faster they spin. However, the only way to spin a turbine faster is to vent more steam through it. This means that as a turbine spins faster, it will produce more power, but also at an ever-increasing voltage. This can become a "
			    + "major problem, especially in situations like a melting-down reactor, as not only will the reactor explode, but also any machinery that is connected downstream!");
	    addGuidebook("chapter.turbines.l3",
		    "To get power out of a turbine, connect a wire to the top of one. A 3x3 group of turbines can be upgraded into a large turbine by whacking the center one with a wrench.");

	    addGuidebook("chapter.steamfunnel", "Steam Funnel");
	    addGuidebook("chapter.steamfunnel.l1",
		    "The steam funnel provides you a way to transport the steam that is produced by the various reactors in Nuclear Science. To collect steam, place the funnel where a turbine would traditionally go. You can then connect a gas pipe to the top. To release the steam into a turbine,"
			    + "place a turbine on top of a funnel, and connect a gas pipe into the bottom. Note the steam inside of the gas pipe will not cool, however it is incredibly hot, making it very difficult to store.");

	    addGuidebook("chapter.centrifuge", "Gas Centrifuge");
	    addGuidebook("chapter.centrifuge.l1",
		    "The Gas Centrifuge splits Uranium Hexafluoride into its isotopes of U235 and U238. There is a split of 17.5% to 82.5% respectively for each mB of Hexafluoride processed. The Centrifuge runs continuously as long as it has at least 42 mB of Uranium Hexafluoride in its input tank. 10% of each cycle "
			    + "is also converted into waste. Note this is not subtracted from the aforementioned split, and is added on top of it.");
	    addGuidebook("chapter.centrifuge.l2",
		    """
		    	Once the Centrifuge has collected 2500 mB of a material, it will produce an item of the respective material. This corresponds to the percentage counter for a category in the Centrifuge's GUI reaching 100%. While this is not 100% realistic, it is a legacy feature to pay tribute to \
		    	the respective block from Atomic Science. The waste material generated from each cycle will produce Fissile Dust, which can then be processed into other useful materials. Uranium Hexaflouride can either be piped into the back of the Gas Centrifuge, or the Nuclear Boiler can output \
		    	directly into it.""");

	    addGuidebook("chapter.fissionreactor", "Fission Reactor");
	    addGuidebook("chapter.fissionreactor.l1.1",
		    "The Fission Reactor is the first atomic power source that you will be able to access as you progress in Nuclear Science. While crude and simple, it is fairly cheap to make. To construct the Fission Reactor, you will first need to craft a %s. The core needs to be surrounded in a 5x5x2 area of water in order to be cooled:");
	    addGuidebook("chapter.fissionreactor.l1.2",
		    "Water placed below the core will not affect its cooling. Another item to note is that while a 5x5x2 area is the minimum required, a 7x7x2 volume is advised due to how block updates work, as the reactor will delete water sources in the cooling volume. ");

	    addGuidebook("chapter.fissionreactor.l2",
		    "Next, cover the top of the 5x5x2 water volume with Turbines. You can either have single turbines, or turn a group of 9 into a large 3x3 turbine using a wrench. Connect the turbines to a wire once ready:");

	    addGuidebook("chapter.fissionreactor.l3",
		    "Now that you have the Reactor constructed, you will need a source of heat to make the steam. This is where the Fission part of the name comes into play. The following fuel rods can be used to heat the reactor:");
	    addGuidebook("chapter.fissionreactor.maxtemp", "Max Temp: %s C");
	    addGuidebook("chapter.fissionreactor.cycles", "Cycles: %s");
	    addGuidebook("chapter.fissionreactor.l4",
		    "The hotter the core gets, the more steam and thus electricity it will produce. The temperature associated with each fuel type is the temperature the reactor core will reach when four rods of that type are inserted. The Core has a temperature limit of 1417 C, and going above this temperature places it at risk of melting "
			    + "down! The Core will not meltdown immediately if it is overheated, but the longer it runs in an overheated state, the greater the chance is of a meltdown occurring. It is also important to note that the hotter a reactor runs, the quicker the fuel source degrades! When a fuel rod is expended, it will leave behind a Spent Fuel Rod which can be processed into other valuable materials.");
	    addGuidebook("chapter.fissionreactor.l5",
		    "If you were paying attention, you may have noticed that a full set of some fuel rods can actually cause the reactor to meltdown. There are two ways to deal with this issue. This first is to mix and match certain fuel types. However, this method is mostly a trial-by-error approach, and does not leave a large amount of room for "
			    + "error.");
	    addGuidebook("chapter.fissionreactor.l6",
		    "The second and more reliable approach is to use a %s to decrease the rate of the Fission reaction. To use one, first place it under the core. Use Right-Click to extend it and Shift + Right-Click to retract the control rod. The more extended the rod is, the slower the reaction and thus cooler the reactor will run. A full extension will result in the fission"
			    + " reaction halting completely.");
	    addGuidebook("chapter.fissionreactor.l7",
		    "One final item of note on the Fission Reactor is that Tritium can be created by placing a Deuterium Cell in the reactor core while it is active. The Deuterium cell has a random chance to be transformed into a Tritium cell at any temperature, but the hotter the core, the greater the chance is!");

	    addGuidebook("chapter.radiogen", "Radio. Generator");
	    addGuidebook("chapter.radiogen.l1",
		    "The Radioisotropic Generator presents an alternative to a fission reaction to produce electricity. It instead uses the natural heat produced from the radioactive decay of items to directly generate electricity. Simply place a radioactive item in it, and it will begin "
			    + "to generate power. The more radioactive an item is, and the more of said item there is, the more power will be produced!");

	    addGuidebook("chapter.msreactor", "MS Reactor");
	    addGuidebook("chapter.msreactor.l1",
		    "The Molten Salt Reactor is a far more refined version of the crude Fission Reactor, but is also far more expensive. Instead of directly heating the water using fission, the reactor uses a molten fissile material that in turns heats a conductive salt. This molten conductive salt is then "
			    + "passed through a heat exchanger which will in turn boil water to produce steam. This has the added benefit of not needing to have the reactor core submersed directly in water, allowing you to be flexible with turbine placement.");
	    addGuidebook("chapter.msreactor.l2.1",
		    "To build a Molten Salt Reactor, you will need to craft a %1$s, %2$s, and %3$s. First, place the Freeze Plug, and then place a the Reactor Core on top of it:");
	    addGuidebook("chapter.msreactor.l2.2",
		    "Facing the green port on the core, place a Molten Salt Supplier so that its green port faces the Core's:");
	    addGuidebook("chapter.msreactor.l2.3",
		    "Both the Freeze Plug and Supplier require a small amount of energy to work, so you will need to power them:");

	    addGuidebook("chapter.msreactor.l3",
		    "As mentioned, the MS Reactor isn't cooled by water and uses a specialized conductive salt known as FLiNaK. This salt is supplied by the Freeze Plug. Simply make the salt and place the pellets in the Plug. The salt is not consumed by the reactor meaning you won't have to replenish it, but the more salt you add, the more heat it will be able to transfer.");
	    addGuidebook("chapter.msreactor.l4",
		    "The fissile salt that the Molten Salt Reactor uses is a specially prepared compound known as LiF-ThF4-UF4. However, as the name of the reactor implies, this salt must be molten in order to be useful. Place the fissile salt pellet in the Molten Salt Supplier to melt it. Each salt pellet melts to %s mB, and the core has an internal capacity of 1000 mB. "
			    + "The salt is slowly consumed over time and produces waste which is also collected by the supplier. Just like the Fission Reactor, the hotter the reactor, the faster the fuel is consumed.");

	    addGuidebook("chapter.msreactor.l5",
		    "Now that the reactor has melted the conductive salt, you need a way to move it and disperse the heat. To do this, you will need to craft some %1$s and a %2$s. The VS Pipe is connected to the top of the Reactor Core and fed into the bottom of the Heat Exchanger. The Heat Exchanger is incredibly efficient, and a single one can be "
			    + "sufficient. It must be placed in a 5x5x2 pool of water like the Fission Reactor Core. It should be noted that while the exchanger doesn't have to be directly above the reactor core by placing a longer VS Pipe, the longer the VS Pipe is, the more heat it will radiate away before it reaches the Exchanger!");

	    addGuidebook("chapter.msreactor.l6",
		    "Another perk of the MS Reactor is that is cannot melt down, making it significantly safer. However, like its predecessor, the hotter the reactor gets, the faster it burns fuel. To decrease the fuel usage, a %s can be used. To use it, attach it to the side of the MS Reactor Core, and control it as with the Fission Reactor's variant.");

	    addGuidebook("chapter.fusionreactor", "Fusion Reactor");
	    addGuidebook("chapter.fusionreactor.l1.1",
		    """
		    	The Fusion Reactor is the ultimate source of power that Nuclear Science has to offer, and is able to produce over 6 MW of energy! However, harvesting this energy is very expensive. The Fusion Reactor's ability to produce power is best understood by understanding its principles rather than understanding a rigid design like with the \
		    	fission-based reactors. The core produces plasma by fusing Deuterium and Tritium together. This fusion process takes energy however, so the reactor must produce more energy than it uses to be beneficial. The plasma that is produced can only be contained by Electromagnets. The plasma on its own will do nothing, however if the plasma is underneath an electromagnet that has a water source block \
		    	on top of it, the water will be boiled from the immense heat, producing steam that can spin a turbine. There is no one set design on how to achieve the full potential of the reactor, so you will have to play around with designs. The rest of this guide will cover the construction of a fairly efficient Fusion reactor. It should be noted that this design does not achieve the maximum \
		    	possible energy output!""");
	    addGuidebook("chapter.fusionreactor.l1.2",
		    "First, you will to construct 13x13 diamond of Electromagnets. They can be either glass or opaque. We will be using both. Place the Fusion Reactor Core in the center of the diamond and remove the block below it.");
	    addGuidebook("chapter.fusionreactor.l2",
		    "Next, surround the side of the 13x13 diamond with a ring of Electromagnets.");
	    addGuidebook("chapter.fusionreactor.l3",
		    "Next, build another 13x13 diamond to act as the roof. Leave a hole in the middle for the Reactor Core like before.");
	    addGuidebook("chapter.fusionreactor.l4",
		    "Next, you will need to cover the top of the Electromagnets with water. The plasma of the reactor will heat the water generating steam, which can in turn be used for spinning turbines. Note the turbines will operate at 480V.");
	    addGuidebook("chapter.fusionreactor.l5",
		    "The core requires 50 kJ/t at 480V to operate. While the initial energy must come from somewhere else, you can use the output of the turbines to feed back into the reaction. The wire can be connected to the top or the bottom of the reactor. In this case, we will be using the top.");
	    addGuidebook("chapter.fusionreactor.l6",
		    "The Reactor uses Deuterium and Tritium Cells to fuel the reaction. To add fuel to the core, right-click a cell on it. This can be done through the convenient hole left in the bottom.");

	    addGuidebook("chapter.reactorlogistics", "Nuclear Control");

	    addGuidebook("chapter.reactorlogistics.l1",
		    "By now, you have probably noticed that interacting manually with reactors and their various supporting equipment is incredibly tedious. On top of that, you have to expose yourself to deadly radiation in order to refuel the Fission and MS reactors. Surly "
			    + "there has to be some way to automate things to relieve the burden you might be saying. Well say no more, because that's exactly what the %s is for! The RL Network will allow you to accomplish all of these things and more. The following pages will cover the components of the network.");
	    addGuidebook("chapter.reactorlogistics.network", "Reactor Logistics Network");

	    addGuidebook("chapter.reactorlogistics.logisticscable",
		    "The Reactor Logistics Cable is used to connect the various components of the network together. A radiation-hardened fiber optic cable, it can travel great distances without taking energy!");
	    addGuidebook("chapter.reactorlogistics.logisticscontroller",
		    "The RL Controller is the brain and the router of the network. Only one is allowed per network, and the device takes %1$s at %2$s to run. It is responsible for allowing the various components to communicate to each other. Individual components "
			    + "will not lose what they are linked to if the controller loses power, however they will not be able to communicate with each-other!");
	    addGuidebook("chapter.reactorlogistics.fissioninterface",
		    "The Fission Interface takes the place of the Fission Control Rod under the Fission Reactor. It is what allows the network to interact with the Fission Reactor Core.");
	    addGuidebook("chapter.reactorlogistics.msinterface",
		    "The MS Interface takes the place of the MS Control rod on the MS Reactor Core. It allows the network to interact with the MS Reactor Core.");
	    addGuidebook("chapter.reactorlogistics.fusioninterface",
		    "The Fusion Interface is placed underneath the Fusion Reactor Core akin to a Fission Control Rod. It allows the network to interact with the Fusion Reactor Core.");

	    addGuidebook("chapter.reactorlogistics.linkinggui", "Link GUI");
	    addGuidebook("chapter.reactorlogistics.linkinggui.l1",
		    "The Link GUI is used to link various modules to an interface. The GUI will either be the only option displayed in a Network module's GUI, or can be accessed via the link tab:");
	    addGuidebook("chapter.reactorlogistics.linkinggui.l2",
		    "The GUI will display the different interfaces on the network that the module can be linked to. Note only reactors compatible with the module will be shown:");
	    addGuidebook("chapter.reactorlogistics.linkinggui.l3",
		    "To link the module, select it and press the \"enable\" button:");
	    addGuidebook("chapter.reactorlogistics.linkinggui.l4",
		    "The de-link a module, press the \"disable\" button:");

	    addGuidebook("chapter.reactorlogistics.controlrodmodule",
		    "The Control Rod Module acts as a remote control rod to the reactor it is linked to. The insertion can be controlled as with a normal control rod. If the block receives a redstone signal, the insertion will be interpreted as the strength of the signal, "
			    + "with a signal of 15 resulting in 100% insertion. To access the linking GUI, right-click the block while holding a wrench. Multiple Control Rod Modules can be connected to a network, but only one can be linked to a single reactor at a time.");

	    addGuidebook("chapter.reactorlogistics.supplymodule",
		    "The Supply Module allows the Network to refuel and extract waste from reactors. Items placed in the top 9 slots will be inserted into the linked reactor, and the network will attempt to deposit waste into the bottom 9 slots. The module can also be used to automate "
			    + "Tritium production. Multiple Supply Modules can be connected to a network, but only one can be linked to a single reactor at a time.");

	    addGuidebook("chapter.reactorlogistics.monitormodule",
		    "The Monitor Module can be used to view information about its linked reactor remotely via its GUI. Multiple can be connected to a network at a time, and multiple can be linked to the same reactor.");

	    addGuidebook("chapter.reactorlogistics.thermometermodule",
		    """
		    	The Thermometer Module can be used to monitor the temperature of the reactor it is linked to. When read with a Redstone Comparator, it will produce a signal with respect to the target temperature it is programmed with. The Module has two signal modes: Constant and Build-up. \
		    	In Constant mode, the module will read a redstone signal of 15 once the reactor's temperature has reached or surpasses the target temperature and a signal of 0 otherwise. If inverted, it will do the opposite, and emit a signal of 15 while the reactor's temperature is below the target temperature and a signal of 0 otherwise. In Build-up mode, \
		    	the module will read a signal that increases linearly from 0 to 15 based upon the ratio of the reactor's temperature to the target temperature, with a maximum of 15 possible once the target temperature has been reached or exceeded. If inverted, The module will read a signal that decreases linearly with the aforementioned ration, with a signal of \
		    	0 being read once the target temperature has been reached or exceeded. Multiple Thermometer Modules be connected to a network at a time, and multiple can be linked to the same reactor.""");

	    addGuidebook("chapter.particleaccelerator", "Particle Accelerator");
	    addGuidebook("chapter.particleaccelerator.l1",
		    "The Particle Accelerator is used to make Anti-Matter and Dark Matter. Both are generated when two Particles collide at semi-luminal speeds. The result of a particle collision is determined by the following formula:");
	    addGuidebook("chapter.particleaccelerator.formula", "((s1 + s2) / 4) ^ 2");
	    addGuidebook("chapter.particleaccelerator.l2",
		    "where s1 and s2 are the speeds of the two particles. Dark Matter is created when the resulting value is greater than 0.999, and has a 100% chance of being created. To achieve this, both particles must be traveling at least 99.99% the Speed of Light. Otherwise, Anti-Matter is created from the collision. "
			    + "Unlike Dark Matter, the chance of Anti-Matter being generated is not guaranteed, and its chance of being created increases the closer the resulting value gets to 0.999. Note nothing will be created if both particles are not traveling at least 50% the speed of light.");

	    addGuidebook("chapter.particleaccelerator.l3",
		    "In order to collide particles, you will first need to create them. For this, you will need a %1$s. The Injector uses matter to make a particle, and any block or item can be used to supply the matter. Place the matter in its respective slot in the Injector. While this will create the particle, to actually catch the "
			    + "result of the collision, you will need to craft an %2$s and insert it as well. The Injector uses %3$s per particle at 960 V. This means you will actually need %4$s for each collision. Note the injector can be disabled using redstone.");

	    addGuidebook("chapter.particleaccelerator.l4",
		    """
		    	While the Injector creates particles, it only spawns them at very low speeds. In order to achieve the speeds needed for a collision, the particle will need to be passed through an %1$s. Particles while passing through boosters follow two simple principles. If the Particle and Booster are facing the same direction, the \
		    	Booster will increase the Particle's speed by %2$s for every tick the Particle is inside the Booster. If the Booster is a corner causing the particle to turn, the Particle's speed will receive a %3$s penalty for each tick it is inside the corner. This means that corners should be kept to a minimum when creating the accelerator ring. It is worth noting now that moving \
		    	particles emit radiation.""");

	    addGuidebook("chapter.particleaccelerator.l5",
		    "But how do we get two particles to actually collide? This is where regular Electromagnets come in. You will need to construct a ring of Electromagnets to allow the Particles to circle and collide. It is incredibly important to note that particles will expire after %s ticks, so it is desirable to make the Electromagnet "
			    + "ring as small as possible. In this example case, we will be using a 3x3 ring.");
	    addGuidebook("chapter.particleaccelerator.l6",
		    "Start by laying out the bottom of the ring at the end of the Booster chain like so:");

	    addGuidebook("chapter.particleaccelerator.l7",
		    "To ensure the particles actually collide, you will need to switch the direction of one, which can be accomplished using a %s. The Switch flips the direction of every other particle that crosses it. Place the switch at the entrance to the collider ring like so:");
	    addGuidebook("chapter.particleaccelerator.l8",
		    "Now, fill in the sides and top of the ring to create an enclosed structure for the Particles. Note either Electromagnets or Electromagnetic Glass can be used. Note the turn penalty is no longer in affect once the particle passes through the switch for the first time.");

	    addGuidebook("chapter.particleaccelerator.l9",
		    "You may have noticed that up until now, there has been little discussion of how to construct the accelerator ring. This is because there are two methods to construct it. The first method is to create a line of accelerators long enough to achieve the particle's needed speed. This method is the simplest solution, "
			    + "however it is rather bulky, and attempts to compact it will result in many turns being introduced as can be demonstrated below:");
	    addGuidebook("chapter.particleaccelerator.l10",
		    "While bulky, it is the fastest method to achieve collisions.");

	    addGuidebook("chapter.particleaccelerator.l11",
		    "The second option is to make use of a Cyclotron ring. This method is more complex and slower at producing collisions, however it gains the ability to be more space efficient. To make use of a Cyclotron ring, you will additionally need an %1$s and %2$s. To construct the Cyclotron, build a ring of Boosters. Note one corner "
			    + "will need to be a junction made of out Electromagnets to give the Particle Injector a spot.");
	    addGuidebook("chapter.particleaccelerator.l12",
		    "In another corner, place another Electromagnet junction to give the Electromagnetic Gateway access to the ring. The most efficient spot to place the Gateway is in the same Junction as the Particle Injector.");
	    addGuidebook("chapter.particleaccelerator.l13",
		    "The Gateway will only allow particles to pass through if they have a specific percentage speed of light equal or greater to the value programmed. The value can be programmed using the Gateway's GUI.");
	    addGuidebook("chapter.particleaccelerator.l14",
		    "After the Gateway, place the Electromagnetic Diode. The arrow must be pointing in the direction of travel of the particle. Place the Electromagnetic Switch after the Diode.");
	    addGuidebook("chapter.particleaccelerator.l15",
		    "Finally, surround everything with Electromagnets and create the collision loop.");
	    addGuidebook("chapter.particleaccelerator.l16",
		    "An important item to note about the two methods described above is that the Injector must be programmed to use the respective method. This can be accomplished via the tab button in its GUI. You can also view the speed of the two particles by hovering over the Speed tab.");

	    addGuidebook("chapter.quantumtunnel", "Quantum Tunnel");
	    addGuidebook("chapter.quantumtunnel.l1",
		    "The Quantum Tunnel is a highly useful block for moving things long distances. The block acts like those that have come before it including the infamous Tesseract, allowing various quantities to be transported between two points instantly and effortlessly. This chapter will discuss "
			    + "how to use the block and what it can do.");
	    addGuidebook("chapter.quantumtunnel.l2.1",
		    "First, what can the block transfer. The block can transfer the following quantities:");
	    addGuidebook("chapter.quantumtunnel.items", "Items");
	    addGuidebook("chapter.quantumtunnel.fluids", "Fluids");
	    addGuidebook("chapter.quantumtunnel.gases", "Gases");
	    addGuidebook("chapter.quantumtunnel.electricity", "Electricity");
	    addGuidebook("chapter.quantumtunnel.l2.2",
		    """
		    	The block accomplishes this by being bound to a frequency. Each frequency can transfer all quantity types at the same time, meaning a frequency could send both electricity and gas at the same time. However you can only send one subtype at a time. For example, \
		    	you could not send water and lava at the same time over the same frequency. It is important to note especially in the case of electricity, as electricity is treated as a packet, meaning 100J at 120V could not be sent at the same time as 500J at 960V. It is also important to note that the two conduits on either side of \
		    	the tunnel frequency will not take into account each other's characteristics. For example, the resistance of the sending wire will not impact the resistance of the receiving wire.""");
	    addGuidebook("chapter.quantumtunnel.l3.1",
		    "We now know how the block functions, but what exactly is a frequency? Frequencies are what link quantum tunnels together. Any number of quantum tunnels can be linked to a single frequency. Opening the GUI of the quantum tunnel, you immediately will observe that there are "
			    + "two types of frequencies: Private and Public");
	    addGuidebook("chapter.quantumtunnel.l3.2",
		    "Private frequencies are only visible to you. Public frequencies can be used by all players. However, only the player that created it may edit, add, and remove it. As this may lead to abuse cases, a command is made available to the server owner that wipes all public frequencies. "
			    + "Note the name of the player who created the frequency is also visible.");
	    addGuidebook("chapter.quantumtunnel.l4.1",
		    "To create a new frequency, select the \"New Frequency\" button:");
	    addGuidebook("chapter.quantumtunnel.l4.2", "Doing so will open the frequency creation menu:");
	    addGuidebook("chapter.quantumtunnel.l4.3",
		    "With the menu, you can name the frequency as well as select what type it will be. Press the \"Create\" button to finalize the creation or \"Cancel\" button to abort. Note that multiple frequencies can have the same name!");
	    addGuidebook("chapter.quantumtunnel.l5.1",
		    "Now that we have created a frequency, how do we enable it? To do this, select the frequency from the menu by clicking on it:");
	    addGuidebook("chapter.quantumtunnel.l5.2",
		    "This imports the frequency into the current frequency field. Press the checkmark to bind the frequency to the tunnel. Press the x to disable the frequency.");
	    addGuidebook("chapter.quantumtunnel.l6.1",
		    "You can delete a frequency pressing the delete button (nothing will happen if you are not the creator):");
	    addGuidebook("chapter.quantumtunnel.l6.2",
		    "You can also edit a frequency using the edit button (note nothing will happen if you are not the creator of the frequency:");
	    addGuidebook("chapter.quantumtunnel.l6.3",
		    "Once you have made the desired changes, press the \"Save\" button to save the changes, or the \"Cancel\" button to abort the changes.");
	    addGuidebook("chapter.quantumtunnel.l7.1",
		    "Now that the quantum tunnel is bound to a frequency of our liking, we now need to configure its IO. To do this, select the \"IO Config\" Button:");
	    addGuidebook("chapter.quantumtunnel.l7.2", "Click the squares to decide the IO of each face:");
	    addGuidebook("chapter.quantumtunnel.l7.3", "Press the IO Config button again to hide the selector.");
	    addGuidebook("chapter.quantumtunnel.l8.1",
		    "To see the quantities being actively transported by a frequency, you can hover over the buffer view tab:");
	    addGuidebook("chapter.quantumtunnel.l8.2",
		    "It is important to note that a Quantum Tunnel must be loaded in order for it to work!");

	    addGuidebook("chapter.othermachines", "Other Machines");
	    addGuidebook("chapter.othermachines.quantumcapacitor1",
		    " the It has an unlimited energy storage capacity and the storage is shared across capacitors. The GUI has two programmable fields. The first is for the joules/tick output of the capacitor. Note, the capacitor outputs at 1.92 kV! "
			    + "The second is for the frequency. The frequency of two capacitors must match for them to link. Every capacitor network is tied to a specific player, so don't worry about other players stealing your energy!");

	    addGuidebook("chapter.othermachines.teleporter1",
		    "The Teleporter requires %s to teleport an entity and is a one-way trip. This means two teleporters are needed: one to send the player, and one to get them back. There is a 4 second cooldown after every teleport. To select a teleporter's destination, you can input coordinates via its GUI.");
	    addGuidebook("chapter.othermachines.teleporter2",
		    "If you don't like entering coordinates manually, you can craft a %s, and import coordiantes from it to the Teleporter via the \"Import\" button. To store coordinates on a Frequency Card, right-click the card on a block. You can clear the card's stored coordinates by Shift + Right-Clicking it.");
	    addGuidebook("chapter.othermachines.teleporter3",
		    "By now, you have probably noticed the dimension field in the GUI:");
	    addGuidebook("chapter.othermachines.teleporter4",
		    "This field defaults to the dimension the Teleporter is placed in initially, and can only be programmed via a Frequency Card.");

	    addGuidebook("chapter.othermachines.chunkloader1",
		    "The Chunkloader loads a 3x3 chunk area centered around the chunk it is placed in and requires nothing to function.");

	    addGuidebook("chapter.othermachines.atomicassembler1",
		    "The Atomic Assembler makes use of the strange properties of Dark Matter, and is able to duplicate items (we have made sure you can't dupe items with inventories, so no dupe bugs this time (: ). It requires 72 kW at 480 V to run. If it loses power, all progress is lost!");
	    addGuidebook("chapter.othermachines.atomicassembler2",
		    "To use it, take an item of your desire and place it in the Assembler. Surround the item with Dark Matter Cells like shown:");
	    addGuidebook("chapter.othermachines.atomicassembler3",
		    "Each duplication takes 6000 ticks, and will take one use of the Dark Matter Cells. This is equivalent to 5 minutes. Cells have 12 uses in total.");

	    addGuidebook("chapter.misc", "Misc");
	    addGuidebook("chapter.misc.l1", "Futurum Usui.");

	    addJei("gui.reactorcore.info.temp", "Range: 0 C to 1400 C");

	    addJei("gascentrifuge.info.power_usage", "240V 30kW");
	    addJei("particalaccelerator.antimatter.collision", "Charge:100% Speed<100%");
	    addJei("particalaccelerator.darkmatter.collision", "Charge:100% Speed:100%");

	}

    }

}
