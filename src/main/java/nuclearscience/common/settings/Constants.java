package nuclearscience.common.settings;

import electrodynamics.api.configuration.Configuration;
import electrodynamics.api.configuration.DoubleValue;
import electrodynamics.api.configuration.IntValue;

@Configuration(name = "Nuclear Science")
public class Constants {

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
	@IntValue(def = 2500)
	public static int REACTOR_RADIATION_PER_FUEL = 2500;
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
	@IntValue(def = 1200)
	public static int ATOMICASSEMBLER_REQUIRED_TICKS = 12000;

}