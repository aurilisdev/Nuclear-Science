package nuclearscience.datagen.client;

import electrodynamics.datagen.client.ElectrodynamicsLangKeyProvider;
import net.minecraft.data.DataGenerator;
import nuclearscience.References;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceFluids;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceSounds;

public class NuclearScienceLangKeyProvider extends ElectrodynamicsLangKeyProvider {

	public NuclearScienceLangKeyProvider(DataGenerator gen, Locale locale) {
		super(gen, locale, References.ID);
	}

	@Override
	protected void addTranslations() {

		switch (locale) {
		case EN_US:
		default:

			add("itemGroup.itemgroup" + References.ID, "Nuclear Science");

			addItem(NuclearScienceItems.ITEM_URANIUM235, "Uranium-235");
			addItem(NuclearScienceItems.ITEM_URANIUM238, "Uranium-238");
			addItem(NuclearScienceItems.ITEM_PLUTONIUM239, "Plutonium-239");
			addItem(NuclearScienceItems.ITEM_POLONIUM210, "Polonium-210");
			addItem(NuclearScienceItems.ITEM_POLONIUM210_CHUNK, "Polonium-210 Chunk");
			addItem(NuclearScienceItems.ITEM_LIFHT4PUF3, "LiF-ThF4-UF4 Salt");
			addItem(NuclearScienceItems.ITEM_FLINAK, "FLiNaK Salt");
			addItem(NuclearScienceItems.ITEM_YELLOWCAKE, "Yellowcake");
			addItem(NuclearScienceItems.ITEM_FISSILEDUST, "Fissile Dust");
			addItem(NuclearScienceItems.ITEM_PLUTONIUMOXIDE, "Plutonium Tetroxide");
			addItem(NuclearScienceItems.ITEM_THORIANITEDUST, "Thorianite Dust");

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
			addItem(NuclearScienceItems.ITEM_FREQUENCYCARD, "Frequency Card");
			addItem(NuclearScienceItems.ITEM_CANISTERLEAD, "Lead-Lined Canister");

			addBlock(NuclearScienceBlocks.blockGasCentrifuge, "Gas Centrifuge");
			addBlock(NuclearScienceBlocks.blockNuclearBoiler, "Nuclear Boiler");
			addBlock(NuclearScienceBlocks.blockChemicalExtractor, "Chemical Extractor");
			addBlock(NuclearScienceBlocks.blockRadioisotopeGenerator, "Radioisotope Generator");
			addBlock(NuclearScienceBlocks.blockTurbine, "Steam Turbine");
			addBlock(NuclearScienceBlocks.blockReactorCore, "Fission Reactor Core");
			addBlock(NuclearScienceBlocks.blockElectromagnet, "Electromagnet");
			addBlock(NuclearScienceBlocks.blockElectromagneticGlass, "Electromagnetic Glass");
			addBlock(NuclearScienceBlocks.blockElectromagneticBooster, "Electromagnetic Booster");
			addBlock(NuclearScienceBlocks.blockElectromagneticSwitch, "Electromagnetic Switch");
			addBlock(NuclearScienceBlocks.blockFusionReactorCore, "Fusion Reactor Core");
			addBlock(NuclearScienceBlocks.blockPlasma, "Plasma");
			addBlock(NuclearScienceBlocks.blockMeltedReactor, "Melted Reactor Core");
			addBlock(NuclearScienceBlocks.blockParticleInjector, "Particle Injector");
			addBlock(NuclearScienceBlocks.blockQuantumCapacitor, "Quantum Capacitor");
			addBlock(NuclearScienceBlocks.blockTeleporter, "Teleporter");
			addBlock(NuclearScienceBlocks.blockControlRodAssembly, "Control Rod");
			addBlock(NuclearScienceBlocks.blockFuelReprocessor, "Fuel Reprocessor");
			addBlock(NuclearScienceBlocks.blockRadioactiveProcessor, "Radioactive Processor");
			addBlock(NuclearScienceBlocks.blockMSRFuelPreProcessor, "MSR Fuel Pre-Processor");
			addBlock(NuclearScienceBlocks.blockFreezePlug, "MSR Freeze Plug");
			addBlock(NuclearScienceBlocks.blockMsrReactorCore, "MSR Reactor Core");
			addBlock(NuclearScienceBlocks.blockHeatExchanger, "Heat Exchanger");
			addBlock(NuclearScienceBlocks.blockSiren, "Siren");
			addBlock(NuclearScienceBlocks.blockAtomicAssembler, "Atomic Assembler");
			addBlock(NuclearScienceBlocks.blockMoltenSaltSupplier, "Molten Salt Supplier");
			addBlock(NuclearScienceBlocks.blockRadioactiveSoil, "Radioactive Soil");
			addBlock(NuclearScienceBlocks.blockRadioactiveAir, "Radioactive Air");
			addBlock(NuclearScienceBlocks.blocklead, "Radiation Shielding");

			addBlock(NuclearScienceBlocks.getBlock(SubtypeMoltenSaltPipe.vanadiumsteelceramic), "VS-Ceramic Pipe");

			addFluid(NuclearScienceFluids.fluidUraniumHexafluoride, "Uranium Hexafluoride");
			addFluid(NuclearScienceFluids.fluidAmmonia, "Ammonia");

			addContainer("gascentrifuge", "Gas Centrifuge");
			addContainer("nuclearboiler", "Nuclear Boiler");
			addContainer("chemicalextractor", "Chemical Extractor");
			addContainer("radioisotopegenerator", "Radioisotope Generator");
			addContainer("reactorcore", "Fission Reactor Core");
			addContainer("particleinjector", "Particle Injector");
			addContainer("quantumcapacitor", "Quantum Capacitor");
			addContainer("fuelreprocessor", "Fuel Reprocessor");
			addContainer("radioactiveprocessor", "Radioactive Processor");
			addContainer("msrfuelpreprocessor", "MSR Fuel Pre-Processor");
			addContainer("freezeplug", "MSR Freeze Plug");
			addContainer("msrreactorcore", "MSR Reactor Core");
			addContainer("moltensaltsupplier", "Molten Salt Supplier");
			addContainer("atomicassembler", "Atomic Assembler");

			addGuiLabel("machine.usage", "Usage: %s");
			addGuiLabel("machine.voltage", "Voltage: %s");
			addGuiLabel("machine.output", "Output: %s");
			addGuiLabel("machine.current", "Current: %s");
			addGuiLabel("machine.transfer", "Output: %s");
			addGuiLabel("machine.stored", "Stored: %s");
			addGuiLabel("particleinjector.charge", "Charge: %s");
			addGuiLabel("particleinjector.matter", "Matter");
			addGuiLabel("particleinjector.cells", "Cells");
			addGuiLabel("reactorcore.deuterium", "Deuterium");
			addGuiLabel("reactorcore.temperature", "Temp: %s");
			addGuiLabel("reactorcore.warning", "OVERHEATING!");
			addGuiLabel("msrreactorcore.fuel", "Fuel: %s mB");
			addGuiLabel("msrreactorcore.nofreezeplug", "Freeze Plug Missing");
			addGuiLabel("quantumcapacitor.joulesinput", "Joules");
			addGuiLabel("quantumcapacitor.frequency", "Frequency");

			addChatMessage("geigercounter.text", "%s Rads/hour");

			add("death.attack.radiation", "%s just did a speedrun of evolution!");
			add("death.attack.plasma", "%s was ionized!");
			add("effect.nuclearscience.radiation", "Radiation");

			addTooltip("voltage.120", "Voltage: 120 Volts");
			addTooltip("voltage.240", "Voltage: 240 Volts");
			addTooltip("voltage.480", "Voltage: 480 Volts");
			addTooltip("voltage.960", "Voltage: 960 Volts");
			addTooltip("voltage.1920", "Voltage: 1.92 kV");
			addTooltip("frequencycard.linked", "Linked to %s");
			addTooltip("frequencycard.notag", "No Link");
			addTooltip("deuteriumlevel", "Deuterium: %s");
			addTooltip("tritiumlevel", "Tritium: %s");

			addSubtitle(NuclearScienceSounds.SOUND_GASCENTRIFUGE, "Gas Centrifuge spins");
			addSubtitle(NuclearScienceSounds.SOUND_NUCLEARBOILER, "Nuclear Boiler boils");
			addSubtitle(NuclearScienceSounds.SOUND_TURBINE, "Steam Turbine spins");
			addSubtitle(NuclearScienceSounds.SOUND_SIREN, "Siren blares!");
			addSubtitle(NuclearScienceSounds.SOUND_GEIGER, "Geiger Counter ticks!");

			addGuidebook(References.ID, "Nuclear Science");

			addGuidebook("chapter.radiation", "Radiation");
			addGuidebook("chapter.radiation.p1l1", "    Radiation is one of the    ");
			addGuidebook("chapter.radiation.p1l2", "key mechanics of Nuclear       ");
			addGuidebook("chapter.radiation.p1l3", "Science. Many items are        ");
			addGuidebook("chapter.radiation.p1l4", "radioactive and will harm you  ");
			addGuidebook("chapter.radiation.p1l5", "if not handled with the proper ");
			addGuidebook("chapter.radiation.p1l6", "equipment. These include:      ");
			addGuidebook("chapter.radiation.p1l7", "     Uranium 235               ");
			addGuidebook("chapter.radiation.p1l8", "     Uranium 238               ");
			addGuidebook("chapter.radiation.p1l9", "     Yellowcake                ");
			addGuidebook("chapter.radiation.p1l10", "     Highly Enriched Fuel Rod  ");
			addGuidebook("chapter.radiation.p1l11", "     Low Enriched Fuel Rod     ");
			addGuidebook("chapter.radiation.p1l12", "     Fissile Dust              ");
			addGuidebook("chapter.radiation.p1l13", "     Plutonium Tetroxide       ");
			addGuidebook("chapter.radiation.p1l14", "     Plutonium 239             ");
			addGuidebook("chapter.radiation.p1l15", "     Thorianite Dust           ");

			addGuidebook("chapter.radiation.p2l1", "     Polonium 210              ");
			addGuidebook("chapter.radiation.p2l2", "     Polonium 210 Chunk        ");
			addGuidebook("chapter.radiation.p2l3", "To avoid getting radiation     ");
			addGuidebook("chapter.radiation.p2l4", "poisoning, you will need to    ");
			addGuidebook("chapter.radiation.p2l5-1", "wear a full");
			addGuidebook("chapter.radiation.p2l5-2", "Hazmat Suit.");
			addGuidebook("chapter.radiation.p2l6", "The suit will take damage when ");
			addGuidebook("chapter.radiation.p2l7", "protecting you, so make sure   ");
			addGuidebook("chapter.radiation.p2l8", "to use it sparingly.           ");
			addGuidebook("chapter.radiation.p2l9", "    However, what if you forget ");
			addGuidebook("chapter.radiation.p2l10", "your hazmat suit? No fear, for ");
			addGuidebook("chapter.radiation.p2l11", "there is a solution to that    ");
			addGuidebook("chapter.radiation.p2l12", "too! If you are suffering from ");
			addGuidebook("chapter.radiation.p2l13", "radiation poisoning, you can   ");
			addGuidebook("chapter.radiation.p2l14-1", "take some");
			addGuidebook("chapter.radiation.p2l14-2", "Antidote");
			addGuidebook("chapter.radiation.p2l14-3", "and it");
			addGuidebook("chapter.radiation.p2l15", "will remove the effect.        ");

			addGuidebook("chapter.fissionreactor", "Fission Reactor");
			addGuidebook("chapter.fissionreactor.p1l1", "    The Fission Reactor is the ");
			addGuidebook("chapter.fissionreactor.p1l2", "first nuclear power source     ");
			addGuidebook("chapter.fissionreactor.p1l3", "you will have available. Crude ");
			addGuidebook("chapter.fissionreactor.p1l4", "and simple, the block heats    ");
			addGuidebook("chapter.fissionreactor.p1l5", "water and converts it to steam,");
			addGuidebook("chapter.fissionreactor.p1l6", "which is used to spin turbines.");
			addGuidebook("chapter.fissionreactor.p1l7", "    To construct the Fission   ");
			addGuidebook("chapter.fissionreactor.p1l8", "Reactor, you will first need   ");
			addGuidebook("chapter.fissionreactor.p1l9", "to craft a Fission Reactor     ");
			addGuidebook("chapter.fissionreactor.p1l10", "Core. Place the Core in the    ");
			addGuidebook("chapter.fissionreactor.p1l11", "center of a 5x5 cube filled    ");
			addGuidebook("chapter.fissionreactor.p1l12", "with water like so:            ");

			addGuidebook("chapter.fissionreactor.p3l1", "    Next, cover the top of the ");
			addGuidebook("chapter.fissionreactor.p3l2", "area with Turbines. You can    ");
			addGuidebook("chapter.fissionreactor.p3l3", "either have single turbines, or");
			addGuidebook("chapter.fissionreactor.p3l4", "turn a group of 9 into a large ");
			addGuidebook("chapter.fissionreactor.p3l5", "3x3 turbine using a wrench.    ");
			addGuidebook("chapter.fissionreactor.p3l6", "The area around the Reactor    ");
			addGuidebook("chapter.fissionreactor.p3l7", "can be larger than 5x5, but    ");
			addGuidebook("chapter.fissionreactor.p3l8", "only the 5x5 section will make ");
			addGuidebook("chapter.fissionreactor.p3l9", "power.                          ");

			addGuidebook("chapter.fissionreactor.p5l1", "    To make steam, you will    ");
			addGuidebook("chapter.fissionreactor.p5l2", "need a source of heat. There   ");
			addGuidebook("chapter.fissionreactor.p5l3", "are 3 fuel types available to  ");
			addGuidebook("chapter.fissionreactor.p5l4", "you:                           ");
			addGuidebook("chapter.fissionreactor.p5l5", "1. Highly Enriched Fuel Rod    ");
			addGuidebook("chapter.fissionreactor.p5l6", "    Max Temp: 1417 C           ");
			addGuidebook("chapter.fissionreactor.p5l7", "    Uses: 96000                ");
			addGuidebook("chapter.fissionreactor.p5l8", "2. Low Enriched Fuel Rod       ");
			addGuidebook("chapter.fissionreactor.p5l9", "    Max Temp: 1075 C           ");
			addGuidebook("chapter.fissionreactor.p5l10", "    Uses: 24000                ");
			addGuidebook("chapter.fissionreactor.p5l11", "3. Plutonium Fuel Rod          ");
			addGuidebook("chapter.fissionreactor.p5l12", "    Max Temp: 1075 C           ");
			addGuidebook("chapter.fissionreactor.p5l13", "    Uses: 120000               ");
			addGuidebook("chapter.fissionreactor.p5l14", "The higher the temperature,    ");
			addGuidebook("chapter.fissionreactor.p5l15", "the faster a fuel will be will ");

			addGuidebook("chapter.fissionreactor.p6l1", "degrade. Turbines produce at   ");
			addGuidebook("chapter.fissionreactor.p6l2", "120V for temperatures under    ");
			addGuidebook("chapter.fissionreactor.p6l3", "1117 C, 240V for temperatures  ");
			addGuidebook("chapter.fissionreactor.p6l4", "under 1515 C, and at 480V for  ");
			addGuidebook("chapter.fissionreactor.p6l5", "higher temperatures. A full    ");
			addGuidebook("chapter.fissionreactor.p6l6", "sized reactor will produce up  ");
			addGuidebook("chapter.fissionreactor.p6l7", "to 22 kJ/t or 444 kW!          ");
			addGuidebook("chapter.fissionreactor.p6l8", "    Note that the temerature   ");
			addGuidebook("chapter.fissionreactor.p6l9", "of the reactor is key. Above   ");
			addGuidebook("chapter.fissionreactor.p6l10", "1400 C, it is in danger of     ");
			addGuidebook("chapter.fissionreactor.p6l11", "melting down. This is where the");
			addGuidebook("chapter.fissionreactor.p6l12", "Control Rod Assembly comes     ");
			addGuidebook("chapter.fissionreactor.p6l13", "in. The Assembly can be used   ");
			addGuidebook("chapter.fissionreactor.p6l14", "to reduce the rate at which    ");
			addGuidebook("chapter.fissionreactor.p6l15", "the reactor uses fuel. Place   ");

			addGuidebook("chapter.fissionreactor.p7l1", "one under the Core, and right  ");
			addGuidebook("chapter.fissionreactor.p7l2", "click it. This will extend the ");
			addGuidebook("chapter.fissionreactor.p7l3", "rods into the Core. The higher ");
			addGuidebook("chapter.fissionreactor.p7l4", "the rods, the lower the fuel   ");
			addGuidebook("chapter.fissionreactor.p7l5", "usage and resulting heat, but  ");
			addGuidebook("chapter.fissionreactor.p7l6", "also the lower the energy      ");
			addGuidebook("chapter.fissionreactor.p7l7", "output!                        ");
			addGuidebook("chapter.fissionreactor.p7l8", "    One final mention is that  ");
			addGuidebook("chapter.fissionreactor.p7l9", "Tritium is produced in the Core ");
			addGuidebook("chapter.fissionreactor.p7l10", "by placing Dueterium cells in  ");
			addGuidebook("chapter.fissionreactor.p7l11", "it while running. They will be ");
			addGuidebook("chapter.fissionreactor.p7l12", "converted randomly above       ");
			addGuidebook("chapter.fissionreactor.p7l13", "800 C.                         ");

			addGuidebook("chapter.msreactor", "MS Reactor");
			addGuidebook("chapter.msreactor.p1l1", "    The Molton Salt Reactor is ");
			addGuidebook("chapter.msreactor.p1l2", "a far more refined version of  ");
			addGuidebook("chapter.msreactor.p1l3", "the crude Fission Reactor, but ");
			addGuidebook("chapter.msreactor.p1l4", "is also far more expensive. To ");
			addGuidebook("chapter.msreactor.p1l5", "start off, you will need 3     ");
			addGuidebook("chapter.msreactor.p1l6", "components:                    ");
			addGuidebook("chapter.msreactor.p1l7", "     MSR Reactor Core          ");
			addGuidebook("chapter.msreactor.p1l8", "     Freeze Plug               ");
			addGuidebook("chapter.msreactor.p1l9", "     Molten Salt Supplier      ");
			addGuidebook("chapter.msreactor.p1l10", "Place a Freeze Plug, and then  ");
			addGuidebook("chapter.msreactor.p1l11", "place a Reactor Core on top    ");
			addGuidebook("chapter.msreactor.p1l12", "of it. Facing the green port on");
			addGuidebook("chapter.msreactor.p1l13", "the core, place a Molten Salt  ");
			addGuidebook("chapter.msreactor.p1l14", "Supplier so that its green port");
			addGuidebook("chapter.msreactor.p1l15", "faces the Core's.              ");

			addGuidebook("chapter.msreactor.p3l1", "    The MS Reactor isn't cooled");
			addGuidebook("chapter.msreactor.p3l2", "by water and is instead cooled ");
			addGuidebook("chapter.msreactor.p3l3", "by FLiNaK salt, which the      ");
			addGuidebook("chapter.msreactor.p3l4", "Freeze Plug supplies. Simply   ");
			addGuidebook("chapter.msreactor.p3l5", "make the salt and place it in  ");
			addGuidebook("chapter.msreactor.p3l6", "the plug. The salt is not used ");
			addGuidebook("chapter.msreactor.p3l7", "up by the reactor, but the     ");
			addGuidebook("chapter.msreactor.p3l8", "more salt you add, the more    ");
			addGuidebook("chapter.msreactor.p3l9", "heat it will be able to remove.");
			addGuidebook("chapter.msreactor.p3l10", "    The actual fuel of the MS  ");
			addGuidebook("chapter.msreactor.p3l11", "Reactor is LiF-ThF4-UF4 Salt,  ");
			addGuidebook("chapter.msreactor.p3l12", "and is provided by the Molten  ");
			addGuidebook("chapter.msreactor.p3l13", "Salt Supplier. This fuel is    ");
			addGuidebook("chapter.msreactor.p3l14", "consumed over time unlike the  ");
			addGuidebook("chapter.msreactor.p3l15", "coolant salt.                  ");

			addGuidebook("chapter.msreactor.p6l1", "    To disperse the heat from  ");
			addGuidebook("chapter.msreactor.p6l2", "the coolant, you will need:    ");
			addGuidebook("chapter.msreactor.p6l3", "     VS-Ceramic Pipe           ");
			addGuidebook("chapter.msreactor.p6l4", "     Heat Exchanger            ");
			addGuidebook("chapter.msreactor.p6l5", "The VS Pipe is connected to    ");
			addGuidebook("chapter.msreactor.p6l6", "the top of the Reactor Core    ");
			addGuidebook("chapter.msreactor.p6l7", "and fed into the bottom of the ");
			addGuidebook("chapter.msreactor.p6l8", "Exchanger. The Exchanger       ");
			addGuidebook("chapter.msreactor.p6l9", "must be placed in a 5x5x2 pool ");
			addGuidebook("chapter.msreactor.p6l10", "of water, and must be          ");
			addGuidebook("chapter.msreactor.p6l11", "water-logged to function. The  ");
			addGuidebook("chapter.msreactor.p6l12", "easiest way to do this is to fill");
			addGuidebook("chapter.msreactor.p6l13", "the pool and then place the    ");
			addGuidebook("chapter.msreactor.p6l14", "Exchanger.                     ");

			addGuidebook("chapter.msreactor.p10l1", "    The Control Rod can also   ");
			addGuidebook("chapter.msreactor.p10l2", "be used to slow the fuel use   ");
			addGuidebook("chapter.msreactor.p10l3", "of the Core. Attach it to the  ");
			addGuidebook("chapter.msreactor.p10l4", "side of the Core, and control  ");
			addGuidebook("chapter.msreactor.p10l5", "it as with the Fission Reactor.");
			addGuidebook("chapter.msreactor.p10l6", "It is worth noting that the MS ");
			addGuidebook("chapter.msreactor.p10l7", "reactor does not melt down     ");
			addGuidebook("chapter.msreactor.p10l8", "like the Fission Reactor, but  ");
			addGuidebook("chapter.msreactor.p10l9", "above 900 C, the fuel will be  ");
			addGuidebook("chapter.msreactor.p10l10", "used inefficiently!            ");

			addGuidebook("chapter.fusionreactor", "Fusion Reactor");
			addGuidebook("chapter.fusionreactor.p1l1", "    The Fusion Reactor is the  ");
			addGuidebook("chapter.fusionreactor.p1l2", "ultimate source of power that  ");
			addGuidebook("chapter.fusionreactor.p1l3", "Nuclear Science has to offer,  ");
			addGuidebook("chapter.fusionreactor.p1l4", "and is able to produce over    ");
			addGuidebook("chapter.fusionreactor.p1l5", "80 MW of energy. However,      ");
			addGuidebook("chapter.fusionreactor.p1l6", "havesting this energy is very  ");
			addGuidebook("chapter.fusionreactor.p1l7", "expensive. First, you will     ");
			addGuidebook("chapter.fusionreactor.p1l8", "to construct a Fusion Reactor  ");
			addGuidebook("chapter.fusionreactor.p1l9", "Core. Leave a hole above and   ");
			addGuidebook("chapter.fusionreactor.p1l10", "below the Core and build a    ");
			addGuidebook("chapter.fusionreactor.p1l11", "diamond with a radius of 6 out ");
			addGuidebook("chapter.fusionreactor.p1l12", "of Electromagnets like so.     ");
			addGuidebook("chapter.fusionreactor.p1l13", "Electromagnetic Glass may also ");
			addGuidebook("chapter.fusionreactor.p1l14", "be used. We are using Glass    ");
			addGuidebook("chapter.fusionreactor.p1l15", "in this case.                  ");

			addGuidebook("chapter.fusionreactor.p3l1", "    Next, surround the sides   ");
			addGuidebook("chapter.fusionreactor.p3l2", "of the diamond with more       ");
			addGuidebook("chapter.fusionreactor.p3l3", "Electromagnets like so:        ");

			addGuidebook("chapter.fusionreactor.p5l1", "    Now, cover the top of the  ");
			addGuidebook("chapter.fusionreactor.p5l2", "with Electromagnets. The       ");
			addGuidebook("chapter.fusionreactor.p5l3", "Reactor itself does not make   ");
			addGuidebook("chapter.fusionreactor.p5l4", "power and instead generates    ");
			addGuidebook("chapter.fusionreactor.p5l5", "plasma, which the magnets will ");
			addGuidebook("chapter.fusionreactor.p5l6", "be containing.                 ");

			addGuidebook("chapter.fusionreactor.p7l1", "    To generate power with the ");
			addGuidebook("chapter.fusionreactor.p7l2", "reactor, you will need to      ");
			addGuidebook("chapter.fusionreactor.p7l3", "cover the top of the magnets   ");
			addGuidebook("chapter.fusionreactor.p7l4", "with water. The magnets will   ");
			addGuidebook("chapter.fusionreactor.p7l5", "heat the water and generate    ");
			addGuidebook("chapter.fusionreactor.p7l6", "steam. To get the start the    ");
			addGuidebook("chapter.fusionreactor.p7l7", "Fusion reaction, you will also ");
			addGuidebook("chapter.fusionreactor.p7l8", "need to power the reactor. A   ");
			addGuidebook("chapter.fusionreactor.p7l9", "wire can be connected to the   ");
			addGuidebook("chapter.fusionreactor.p7l10", "top or the bottom of the Core. ");
			addGuidebook("chapter.fusionreactor.p7l11", "Once the area has been filled  ");
			addGuidebook("chapter.fusionreactor.p7l12", "with water, place Turbines     ");
			addGuidebook("chapter.fusionreactor.p7l13", "above the water's surface.     ");

			addGuidebook("chapter.fusionreactor.p11l1", "    The Fusion Reactor Core    ");
			addGuidebook("chapter.fusionreactor.p11l2", "requires 50 kJ/t or 1 MW at    ");
			addGuidebook("chapter.fusionreactor.p11l3", "480 V to keep the fusion       ");
			addGuidebook("chapter.fusionreactor.p11l4", "reaction running. While you    ");
			addGuidebook("chapter.fusionreactor.p11l5", "will need to supply the initial");
			addGuidebook("chapter.fusionreactor.p11l6", "1 MW, you can feed the output  ");
			addGuidebook("chapter.fusionreactor.p11l7", "of the Turbines back into the  ");
			addGuidebook("chapter.fusionreactor.p11l8", "reactor keeping it going.      ");
			addGuidebook("chapter.fusionreactor.p11l9", "    To fuel the Reactor, you   ");
			addGuidebook("chapter.fusionreactor.p11l10", "will need to supply it with    ");
			addGuidebook("chapter.fusionreactor.p11l11", "Dueterium and Tritium. This is ");
			addGuidebook("chapter.fusionreactor.p11l12", "currently done by right Cells  ");
			addGuidebook("chapter.fusionreactor.p11l13", "on the Reactor Core. This is   ");
			addGuidebook("chapter.fusionreactor.p11l14", "planned to be changed in the   ");
			addGuidebook("chapter.fusionreactor.p11l15", "future however.                ");

			addGuidebook("chapter.particleaccelerator", "Particle Accelerator");
			addGuidebook("chapter.particleaccelerator.p1l1", "    The Particle Accelerator   ");
			addGuidebook("chapter.particleaccelerator.p1l2", "is used to make Anti-Matter    ");
			addGuidebook("chapter.particleaccelerator.p1l3", "and Dark Matter. Both are      ");
			addGuidebook("chapter.particleaccelerator.p1l4", "generated when two Particles   ");
			addGuidebook("chapter.particleaccelerator.p1l5", "collide at great speeds. Anti- ");
			addGuidebook("chapter.particleaccelerator.p1l6", "Matter is created when the two ");
			addGuidebook("chapter.particleaccelerator.p1l7", "particles collide at less than ");
			addGuidebook("chapter.particleaccelerator.p1l8", "100%, however the closer they  ");
			addGuidebook("chapter.particleaccelerator.p1l9", "are to 100%, the more likely   ");
			addGuidebook("chapter.particleaccelerator.p1l10", "Anti-Matter will be generated. ");
			addGuidebook("chapter.particleaccelerator.p1l11", "Dark Matter is created when    ");
			addGuidebook("chapter.particleaccelerator.p1l12", "the collision occurs at 100%   ");
			addGuidebook("chapter.particleaccelerator.p1l13", "speed, and has a guarenteed    ");
			addGuidebook("chapter.particleaccelerator.p1l14", "chance to be created.          ");
			addGuidebook("chapter.particleaccelerator.p1l15", "    To create particles, you   ");

			addGuidebook("chapter.particleaccelerator.p2l1", "will need a Particle Injector. ");
			addGuidebook("chapter.particleaccelerator.p2l2", "The Injector uses matter to    ");
			addGuidebook("chapter.particleaccelerator.p2l3", "make a particle. Any block or  ");
			addGuidebook("chapter.particleaccelerator.p2l4", "item can be used to supply the ");
			addGuidebook("chapter.particleaccelerator.p2l5", "matter. To catch the result of ");
			addGuidebook("chapter.particleaccelerator.p2l6", "the collision, you will need   ");
			addGuidebook("chapter.particleaccelerator.p2l7", "Electromagnetic Cells, which   ");
			addGuidebook("chapter.particleaccelerator.p2l8", "are placed in the slot below   ");
			addGuidebook("chapter.particleaccelerator.p2l9", "the matter slot. The Injector  ");
			addGuidebook("chapter.particleaccelerator.p2l10", "runs off of 960 V and uses     ");
			addGuidebook("chapter.particleaccelerator.p2l11", "20 MJ per particle. This means ");
			addGuidebook("chapter.particleaccelerator.p2l12", "you will need 40 MJ for each   ");
			addGuidebook("chapter.particleaccelerator.p2l13", "collision.                     ");

			addGuidebook("chapter.particleaccelerator.p4l1", "    While the Injector makes   ");
			addGuidebook("chapter.particleaccelerator.p4l2", "particles, it does not affect  ");
			addGuidebook("chapter.particleaccelerator.p4l3", "their speed. This is handled by");
			addGuidebook("chapter.particleaccelerator.p4l4", "the Electromagnetic Booster.   ");
			addGuidebook("chapter.particleaccelerator.p4l5", "If the particle and Booster    ");
			addGuidebook("chapter.particleaccelerator.p4l6", "are facing the same direction, ");
			addGuidebook("chapter.particleaccelerator.p4l7", "the Booster will add 0.33% to  ");
			addGuidebook("chapter.particleaccelerator.p4l8", "the particle's speed. If the   ");
			addGuidebook("chapter.particleaccelerator.p4l9", "Booster is a corner, it will   ");
			addGuidebook("chapter.particleaccelerator.p4l10", "add 0.17% to the particle's    ");
			addGuidebook("chapter.particleaccelerator.p4l11", "speed. This is done for every  ");
			addGuidebook("chapter.particleaccelerator.p4l12", "tick the particle is in a      ");
			addGuidebook("chapter.particleaccelerator.p4l13", "Booster. To reach 100% speed,  ");
			addGuidebook("chapter.particleaccelerator.p4l14", "it will take 200 Boosters in a ");
			addGuidebook("chapter.particleaccelerator.p4l15", "straight line. However, this   ");

			addGuidebook("chapter.particleaccelerator.p5l1", "requires a large amount of     ");
			addGuidebook("chapter.particleaccelerator.p5l2", "room. Boosters can be set up   ");
			addGuidebook("chapter.particleaccelerator.p5l3", "in a snake pattern to help     ");
			addGuidebook("chapter.particleaccelerator.p5l4", "compact them. To make one      ");
			addGuidebook("chapter.particleaccelerator.p5l5", "a corner variant, stand on top ");
			addGuidebook("chapter.particleaccelerator.p5l6", "of Booster segment you wish to ");
			addGuidebook("chapter.particleaccelerator.p5l7", "turn, and place a Booster to   ");
			addGuidebook("chapter.particleaccelerator.p5l8", "start the next segment after   ");
			addGuidebook("chapter.particleaccelerator.p5l9", "the corner. Looking at the      ");
			addGuidebook("chapter.particleaccelerator.p5l10", "outer bottom corner of the     ");
			addGuidebook("chapter.particleaccelerator.p5l11", "next segment's Booster, place  ");
			addGuidebook("chapter.particleaccelerator.p5l12", "the corner Booster. If you did ");
			addGuidebook("chapter.particleaccelerator.p5l13", "it properly, you will not be   ");
			addGuidebook("chapter.particleaccelerator.p5l14", "able to directly see the inside");
			addGuidebook("chapter.particleaccelerator.p5l15", "of Booster.                    ");

			addGuidebook("chapter.particleaccelerator.p7l1", "    A set of 60 Boosters as    ");
			addGuidebook("chapter.particleaccelerator.p7l2", "pictured here has a roughly 1  ");
			addGuidebook("chapter.particleaccelerator.p7l3", "5 chance of creating Anti      ");
			addGuidebook("chapter.particleaccelerator.p7l4", "Matter from a collision:       ");

			addGuidebook("chapter.particleaccelerator.p9l1", "    But how do we get two      ");
			addGuidebook("chapter.particleaccelerator.p9l2", "particles to collide? This is  ");
			addGuidebook("chapter.particleaccelerator.p9l3", "where regular Electromagnets   ");
			addGuidebook("chapter.particleaccelerator.p9l4", "come in. You will need to      ");
			addGuidebook("chapter.particleaccelerator.p9l5", "build a ring allowing them to  ");
			addGuidebook("chapter.particleaccelerator.p9l6", "collide. The ring itself can   ");
			addGuidebook("chapter.particleaccelerator.p9l7", "be as small as 3x3. Size has   ");
			addGuidebook("chapter.particleaccelerator.p9l8", "no impact other than being     ");
			addGuidebook("chapter.particleaccelerator.p9l9", "cheaper to make.               ");
			addGuidebook("chapter.particleaccelerator.p9l10", "    Start by laying out the    ");
			addGuidebook("chapter.particleaccelerator.p9l11", "bottom of the ring at the end  ");
			addGuidebook("chapter.particleaccelerator.p9l12", "of the Booster chain like so:  ");

			addGuidebook("chapter.particleaccelerator.p11l1", "    Place an Electromagnetic   ");
			addGuidebook("chapter.particleaccelerator.p11l2", "switch in front of the output  ");
			addGuidebook("chapter.particleaccelerator.p11l3", "of the Booster chain. It is    ");
			addGuidebook("chapter.particleaccelerator.p11l4", "vital you place one of these   ");
			addGuidebook("chapter.particleaccelerator.p11l5", "here! Fill in the sides and    ");
			addGuidebook("chapter.particleaccelerator.p11l6", "top of the ring as pictured.   ");
			addGuidebook("chapter.particleaccelerator.p11l7", "Note either Electromagnets or  ");
			addGuidebook("chapter.particleaccelerator.p11l8", "Electromagnetic Glass can be   ");
			addGuidebook("chapter.particleaccelerator.p11l9", "used.                          ");

			addGuidebook("chapter.othermachines", "Other Machines");
			addGuidebook("chapter.othermachines.radiogentitle", "Radioisotropic Gen");
			addGuidebook("chapter.othermachines.p1l1", "    The Radioisotropic         ");
			addGuidebook("chapter.othermachines.p1l2", "Generator uses the natural     ");
			addGuidebook("chapter.othermachines.p1l3", "heat produced from the         ");
			addGuidebook("chapter.othermachines.p1l4", "radioactive decay of items.    ");
			addGuidebook("chapter.othermachines.p1l5", "Simply place a radioactive item");
			addGuidebook("chapter.othermachines.p1l6", "in it, and it will begin to make");
			addGuidebook("chapter.othermachines.p1l7", "power. See the chapter on      ");
			addGuidebook("chapter.othermachines.p1l8", "Radiation to see which items   ");
			addGuidebook("chapter.othermachines.p1l9", "are valid. The more radioactive");
			addGuidebook("chapter.othermachines.p1l10", "an item is, the more power the ");
			addGuidebook("chapter.othermachines.p1l11", "generator will produce!        ");

			addGuidebook("chapter.othermachines.gascenttitle", "Gas Centrifuge");
			addGuidebook("chapter.othermachines.p2l1", "    The Gas Centrifuge splits  ");
			addGuidebook("chapter.othermachines.p2l2", "Uranium Hexafluoride into its  ");
			addGuidebook("chapter.othermachines.p2l3", "isotopes of U235 and U238.     ");
			addGuidebook("chapter.othermachines.p2l4", "There is a split of 17.5% to   ");
			addGuidebook("chapter.othermachines.p2l5", "82.5% respectively for each    ");
			addGuidebook("chapter.othermachines.p2l6", "mB of Hexafluoride. The        ");
			addGuidebook("chapter.othermachines.p2l7", "Centrifuge runs continuously   ");
			addGuidebook("chapter.othermachines.p2l8", "as long as it has at least 42  ");
			addGuidebook("chapter.othermachines.p2l9", "mB per run. 10% of each cycle  ");
			addGuidebook("chapter.othermachines.p2l10", "is also converted into waste.  ");
			addGuidebook("chapter.othermachines.p2l11", "Note this is not subtracted    ");

			addGuidebook("chapter.othermachines.p3l1", "from the aforementioned split, ");
			addGuidebook("chapter.othermachines.p3l2", "and is added on top of it.     ");
			addGuidebook("chapter.othermachines.p3l3", "    Once the Centrifuge has    ");
			addGuidebook("chapter.othermachines.p3l4", "collected 2500 mB of a material,");
			addGuidebook("chapter.othermachines.p3l5", "it will produce an item of the ");
			addGuidebook("chapter.othermachines.p3l6", "respective material. While this");
			addGuidebook("chapter.othermachines.p3l7", "is not 100% realistic, it is a ");
			addGuidebook("chapter.othermachines.p3l8", "legacy feature to pay tribute  ");
			addGuidebook("chapter.othermachines.p3l9", "to Atomic Science. The waste   ");
			addGuidebook("chapter.othermachines.p3l10", "material will produce Fissile  ");
			addGuidebook("chapter.othermachines.p3l11", "Dust. Uranium Hexaflouride can ");
			addGuidebook("chapter.othermachines.p3l12", "either be piped into the back  ");
			addGuidebook("chapter.othermachines.p3l13", "of the Centrifuge, or the      ");
			addGuidebook("chapter.othermachines.p3l14", "Nuclear Boiler can output      ");
			addGuidebook("chapter.othermachines.p3l15", "directly into it.              ");

			addGuidebook("chapter.othermachines.quantumcaptitle", "Quantum Capacitor");
			addGuidebook("chapter.othermachines.p5l1", "    The Quantum Capacitor is   ");
			addGuidebook("chapter.othermachines.p5l2", "the ultimate energy storage    ");
			addGuidebook("chapter.othermachines.p5l3", "device. It has an unlimited    ");
			addGuidebook("chapter.othermachines.p5l4", "energy storage capacity, and   ");
			addGuidebook("chapter.othermachines.p5l5", "has a programmable voltage     ");
			addGuidebook("chapter.othermachines.p5l6", "and current. Furthermore,      ");
			addGuidebook("chapter.othermachines.p5l7", "each Capacitor is linked to    ");
			addGuidebook("chapter.othermachines.p5l8", "your player UUID. This means   ");
			addGuidebook("chapter.othermachines.p5l9", "no matter where you place one  ");
			addGuidebook("chapter.othermachines.p5l10", "in the world, all will share   ");
			addGuidebook("chapter.othermachines.p5l11", "the same charge!               ");

			addGuidebook("chapter.othermachines.teleportertitle", "Teleporter");
			addGuidebook("chapter.othermachines.p6l1", "    The Teleporter requires    ");
			addGuidebook("chapter.othermachines.p6l2", "5 MJ to teleport a player. Two ");
			addGuidebook("chapter.othermachines.p6l3", "teleporters are needed: one    ");
			addGuidebook("chapter.othermachines.p6l4", "to send the player, and one to ");
			addGuidebook("chapter.othermachines.p6l5", "recieve them. Only the sending ");
			addGuidebook("chapter.othermachines.p6l6", "end needs to be powered. To    ");
			addGuidebook("chapter.othermachines.p6l7", "link two teleporters, right-   ");
			addGuidebook("chapter.othermachines.p6l8", "click the recieving end with a ");
			addGuidebook("chapter.othermachines.p6l9", "Frequency Card. Then right-    ");
			addGuidebook("chapter.othermachines.p6l10", "click the sending end with the");
			addGuidebook("chapter.othermachines.p6l11", "same card to link them.        ");

			addGuidebook("chapter.othermachines.p7l1", "    After each teleport, there ");
			addGuidebook("chapter.othermachines.p7l2", "is 4 second cooldown applied   ");
			addGuidebook("chapter.othermachines.p7l3", "to the sending end. Note, it is");
			addGuidebook("chapter.othermachines.p7l4", "possible to link a teleporter to");
			addGuidebook("chapter.othermachines.p7l5", "itself, so be careful! To wipe ");
			addGuidebook("chapter.othermachines.p7l6", "the frequency from a Card,     ");
			addGuidebook("chapter.othermachines.p7l7", "craft it in your inventory.    ");

			addGuidebook("chapter.othermachines.atomicasstitle", "Atomic Assembler");
			addGuidebook("chapter.othermachines.p8l1", "    The Atomic Assembler makes ");
			addGuidebook("chapter.othermachines.p8l2", "use of the strange properties  ");
			addGuidebook("chapter.othermachines.p8l3", "of Dark Matter, and is able to ");
			addGuidebook("chapter.othermachines.p8l4", "duplicate items (we have made  ");
			addGuidebook("chapter.othermachines.p8l5", "sure you can't dupe items with ");
			addGuidebook("chapter.othermachines.p8l6", "inventories, so no dupe bugs   ");
			addGuidebook("chapter.othermachines.p8l7", "this time (: ). It requires    ");
			addGuidebook("chapter.othermachines.p8l8", "72 kW at 480 V to run. If it   ");
			addGuidebook("chapter.othermachines.p8l9", "loses power, all progress is   ");
			addGuidebook("chapter.othermachines.p8l10", "lost!                          ");
			addGuidebook("chapter.othermachines.p8l11", "    To use it, take an item of ");

			addGuidebook("chapter.othermachines.p9l1", "your desire and place it in    ");
			addGuidebook("chapter.othermachines.p9l2", "the Assembler. Surround the    ");
			addGuidebook("chapter.othermachines.p9l3", "item with Dark Matter Cells    ");
			addGuidebook("chapter.othermachines.p9l4", "like shown. Each duplication   ");
			addGuidebook("chapter.othermachines.p9l5", "3600 ticks, and will take one  ");
			addGuidebook("chapter.othermachines.p9l6", "use of the Dark Matter Cells.  ");
			addGuidebook("chapter.othermachines.p9l7", "Cells have 64 uses in total.   ");

			addGuidebook("chapter.misc", "Misc");
			addGuidebook("chapter.misc.p1l1", "Futurum Usui.");

			addJei("gui.reactorcore.info.temp", "Range: 0 C to 1400 C");
			addJei("gui.gascentrifuge", "Gas Centrifuge");

			addJei("gui.gascentrifuge.info.power_usage", "240V 30kW");
			addJei("gui.gascentrifuge.info.percent_u235", "17.2%");
			addJei("gui.gascentrifuge.info.percent_u238", "72.8%");
			addJei("gui.gascentrifuge.info.percent_biproduct", "10.0%");
			addJei("gui.particalaccelerator.antimatter", "Particle Collision");
			addJei("gui.particalaccelerator.antimatter.info.power", "Charge:100% Speed<100%");
			addJei("gui.particalaccelerator.darkmatter", "Particle Collision");
			addJei("gui.particalaccelerator.darkmatter.info.power", "Charge:100% Speed:100%");

		}

	}

}
