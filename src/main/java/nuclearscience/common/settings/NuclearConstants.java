package nuclearscience.common.settings;

import voltaic.api.configuration.Configuration;
import voltaic.api.configuration.DoubleValue;
import voltaic.api.configuration.IntValue;

@Configuration(name = "Nuclear Science")
public class NuclearConstants {

	@DoubleValue(def = 1000000.0)
	public static double TELEPORTER_USAGE_PER_TELEPORT = 1000000.0;
	@DoubleValue(def = 120.0)
	public static double RADIOISOTOPEGENERATOR_VOLTAGE = 120.0;
	@DoubleValue(def = 0.35)
	public static double RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER = 0.35f;
	@DoubleValue(def = 350000.0)
	public static double FISSIONREACTOR_MAXENERGYTARGET = 350000.0;
	@DoubleValue(def = 850000.0)
	public static double MSRREACTOR_MAXENERGYTARGET = 850000.0;
	@DoubleValue(def = 6000000.0)
	public static double FUSIONREACTOR_MAXENERGYTARGET = 6000000.0;
	@DoubleValue(def = 50000.0)
	public static double FUSIONREACTOR_USAGE_PER_TICK = 50000.0;
	@DoubleValue(def = 200000000.0)
	public static double PARTICLEINJECTOR_USAGE_PER_PARTICLE = 200000000.0;
	@IntValue(def = 1024)
	public static int FUSIONREACTOR_MAXSTORAGE = 1024;
	@DoubleValue(def = 1500)
	public static double GASCENTRIFUGE_USAGE_PER_TICK = 1500.0;
	@IntValue(def = 20)
	public static int GASCENTRIFUGE_REQUIRED_TICKS_PER_PROCESSING = 20;
	@DoubleValue(def = 1200.0)
	public static double FREEZEPLUG_USAGE_PER_TICK = 200.0;
	@DoubleValue(def = 120.0)
	public static double MOLTENSALTSUPPLIER_VOLTAGE = 120.0;
	@DoubleValue(def = 200.0)
	public static double MOLTENSALTSUPPLIER_USAGE_PER_TICK = 200.0;
	@DoubleValue(def = 1250.0)
	public static double ATOMICASSEMBLER_USAGE_PER_TICK = 6000.0;
	@DoubleValue(def = 480.0)
	public static double ATOMICASSEMBLER_VOLTAGE = 480.0;
	@IntValue(def = 6000)
	public static int ATOMICASSEMBLER_REQUIRED_TICKS = 6000;
	@IntValue(def = 40)
	public static int QUANTUM_TUNNEL_FREQUENCY_CAP_PER_PLAYER = 40;
	@IntValue(def = 40)
	public static int ANTIMATTER_TICKS_ON_GROUND = 40;
	@DoubleValue(def = 100)
	public static double CLOUD_CHAMBER_ENERGY_USAGE_PER_TICK = 100;
	@IntValue(def = 1)
	public static int CLOUD_CHAMBER_FLUID_USAGE_PER_TICK = 1;
	@DoubleValue(def = 200)
	public static double FALLOUT_SCRUBBER_USAGE_PER_TICK = 100.0;
	@IntValue(def = 5)
	public static int ATOMIC_ASSEMBLER_RADIATION_RADIUS = 5;
	@IntValue(def = 3)
	public static int CHEMICAL_EXTRACTOR_RADIATION_RADIUS = 3;
	@IntValue(def = 10)
	public static int FUEL_REPROCESSOR_RADIATION_RADIUS = 10;
	@IntValue(def = 5)
	public static int GAS_CENTRIFUGE_RADIATION_RADIUS = 5;
	@IntValue(def = 5)
	public static int NUCLEAR_BOILER_RADIATION_RADIUS = 5;
	@IntValue(def = 3)
	public static int PARTICLE_INJECTOR_RADIATION_RADIUS = 3;
	@IntValue(def = 5)
	public static int RADIOACTIVE_PROCESSOR_RADIATION_RADIUS = 5;
	@IntValue(def = 10)
	public static int RADIO_GENATOR_RADIATION_RADIUS = 10;
	@IntValue(def = 5)
	public static int MOLTEN_SAL_SUPPLIER_RADIATION_RADIUS = 5;
	@IntValue(def = 50)
	public static int DEFAULT_PARTICLE_COOLDOWN_TICKS = 50;
	@IntValue(def = 800)
	public static int PARTICLE_SURVIVAL_TICKS = 800;
	@DoubleValue(def = 5.0)
	public static double FISSION_REACTOR_MELTDOWN_RADIATION_DURATION_REAL_DAYS = 5.0;

}
