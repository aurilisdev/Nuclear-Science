package nuclearscience.common.settings;

import electrodynamics.api.configuration.Configuration;
import electrodynamics.api.configuration.DoubleValue;
import electrodynamics.api.configuration.IntValue;

@Configuration(name = "Nuclear Science")
public class Constants {

	@DoubleValue(def = 120.0)
	public static double RADIOISOTOPEGENERATOR_VOLTAGE = 120.0;
	@DoubleValue(def = 0.15)
	public static double RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER = 0.15f;
	@DoubleValue(def = 400000.0)
	public static double FISSIONREACTOR_MAXENERGYTARGET = 400000.0;
	@DoubleValue(def = 750000.0)
	public static double MSRREACTOR_MAXENERGYTARGET = 750000.0;
	@DoubleValue(def = 4000000.0)
	public static double FUSIONREACTOR_MAXENERGYTARGET = 4000000.0;
	@DoubleValue(def = 50000.0)
	public static double FUSIONREACTOR_USAGE_PER_TICK = 50000.0;
	@DoubleValue(def = 20000000.0)
	public static double PARTICLEINJECTOR_USAGE_PER_PARTICLE = 20000000.0;
	@IntValue(def = 1024)
	public static int FUSIONREACTOR_MAXSTORAGE = 1024;
	@IntValue(def = 2500)
	public static int REACTOR_RADIATION_PER_FUEL = 2500;
	@DoubleValue(def = 750)
	public static double CHEMICALBOILER_USAGE_PER_TICK = 750.0;
	@IntValue(def = 800)
	public static int CHEMICALBOILER_REQUIRED_TICKS = 800;
	@DoubleValue(def = 750)
	public static double CHEMICALEXTRACTOR_USAGE_PER_TICK = 750.0;
	@IntValue(def = 400)
	public static int CHEMICALEXTRACTOR_REQUIRED_TICKS = 400;
	@DoubleValue(def = 1500)
	public static double GASCENTRIFUGE_USAGE_PER_TICK = 1500.0;
	@IntValue(def = 20)
	public static int GASCENTRIFUGE_REQUIRED_TICKS_PER_PROCESSING = 20;
	@DoubleValue(def = 1200.0)
	public static double MSRFUELPREPROCESSOR_USAGE_PER_TICK = 1200.0;
	@IntValue(def = 200)
	public static int MSRFUELPREPROCESSOR_REQUIRED_TICKS = 200;
	@DoubleValue(def = 1200.0)
	public static double FREEZEPLUG_USAGE_PER_TICK = 200.0;
	@IntValue(def = 480)
	public static int FUELREPROCESSOR_USAGE_PER_TICK = 480;
	@DoubleValue(def = 200.0)
	public static double FUELREPROCESSOR_REQUIRED_TICKS = 200.0;
	@IntValue(def = 480)
	public static int RADIOACTIVEPROCESSOR_USAGE_PER_TICK = 480;
	@DoubleValue(def = 300.0)
	public static double RADIOACTIVEPROCESSOR_REQUIRED_TICKS = 300.0;
	@DoubleValue(def = 120.0)
	public static double MOLTENSALTSUPPLIER_VOLTAGE = 120.0;
	@DoubleValue(def = 200.0)
	public static double MOLTENSALTSUPPLIER_USAGE_PER_TICK = 200.0;
	@DoubleValue(def = 1250.0)
	public static double ATOMICASSEMBLER_USAGE_PER_TICK = 3000.0;
	@DoubleValue(def = 480.0)
	public static double ATOMICASSEMBLER_VOLTAGE = 480.0;
	@IntValue(def = 1200)
	public static int ATOMICASSEMBLER_REQUIRED_TICKS = 3600;

}
