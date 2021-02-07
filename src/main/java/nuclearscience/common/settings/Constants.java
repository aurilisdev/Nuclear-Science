package nuclearscience.common.settings;

import electrodynamics.api.configuration.Configuration;
import electrodynamics.api.configuration.DoubleValue;
import electrodynamics.api.configuration.IntValue;

@Configuration(name = "Nuclear Science")
public class Constants {

	@DoubleValue(def = 120.0)
	public static double RADIOISOTOPEGENERATOR_VOLTAGE = 120.0;
	@DoubleValue(def = 0.2)
	public static double RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER = 0.2f;
	@DoubleValue(def = 500000.0)
	public static double FISSIONREACTOR_MAXENERGYTARGET = 500000.0;
	@DoubleValue(def = 4000000.0)
	public static double FUSIONREACTOR_MAXENERGYTARGET = 4000000.0;
	@DoubleValue(def = 175000.0)
	public static double FUSIONREACTOR_REQUIREDPOWER = 175000.0;
	@DoubleValue(def = 20000000.0)
	public static double PARTICLEINJECTOR_REQUIREDPOWER = 20000000.0;
	@IntValue(def = 1024)
	public static int FUSIONREACTOR_MAXSTORAGE = 1024;
	@IntValue(def = 2500)
	public static int REACTOR_RADIATION_PER_FUEL = 2500;
}
