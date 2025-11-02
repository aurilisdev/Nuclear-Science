package nuclearscience.common.settings;

import net.neoforged.neoforge.common.ModConfigSpec;

public class NuclearConfig {
    public static NuclearConfig INSTANCE;

    public ModConfigSpec SPEC;

    public ModConfigSpec.DoubleValue TRANSFORMER_EFFICIENCY;

    public ModConfigSpec.DoubleValue TELEPORTER_USAGE_PER_TELEPORT;
    public ModConfigSpec.DoubleValue RADIOISOTOPEGENERATOR_VOLTAGE;
    public ModConfigSpec.DoubleValue RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER;
    public ModConfigSpec.DoubleValue FISSIONREACTOR_MAXENERGYTARGET;
    public ModConfigSpec.DoubleValue MSRREACTOR_MAXENERGYTARGET;
    public ModConfigSpec.DoubleValue FUSIONREACTOR_MAXENERGYTARGET;
    public ModConfigSpec.DoubleValue FUSIONREACTOR_USAGE_PER_TICK;
    public ModConfigSpec.DoubleValue PARTICLEINJECTOR_USAGE_PER_PARTICLE;
    public ModConfigSpec.IntValue FUSIONREACTOR_MAXSTORAGE;
    public ModConfigSpec.DoubleValue GASCENTRIFUGE_USAGE_PER_TICK;
    public ModConfigSpec.IntValue GASCENTRIFUGE_REQUIRED_TICKS_PER_PROCESSING;
    public ModConfigSpec.DoubleValue FREEZEPLUG_USAGE_PER_TICK;
    public ModConfigSpec.DoubleValue MOLTENSALTSUPPLIER_VOLTAGE;
    public ModConfigSpec.DoubleValue MOLTENSALTSUPPLIER_USAGE_PER_TICK;
    public ModConfigSpec.DoubleValue ATOMICASSEMBLER_USAGE_PER_TICK;
    public ModConfigSpec.DoubleValue ATOMICASSEMBLER_VOLTAGE;
    public ModConfigSpec.IntValue ATOMICASSEMBLER_REQUIRED_TICKS;
    public ModConfigSpec.IntValue QUANTUM_TUNNEL_FREQUENCY_CAP_PER_PLAYER;
    public ModConfigSpec.IntValue ANTIMATTER_TICKS_ON_GROUND;
    public ModConfigSpec.DoubleValue CLOUD_CHAMBER_ENERGY_USAGE_PER_TICK;
    public ModConfigSpec.IntValue CLOUD_CHAMBER_FLUID_USAGE_PER_TICK;
    public ModConfigSpec.DoubleValue FALLOUT_SCRUBBER_USAGE_PER_TICK;
    public ModConfigSpec.IntValue ATOMIC_ASSEMBLER_RADIATION_RADIUS;
    public ModConfigSpec.IntValue CHEMICAL_EXTRACTOR_RADIATION_RADIUS;
    public ModConfigSpec.IntValue FUEL_REPROCESSOR_RADIATION_RADIUS;
    public ModConfigSpec.IntValue GAS_CENTRIFUGE_RADIATION_RADIUS;
    public ModConfigSpec.IntValue NUCLEAR_BOILER_RADIATION_RADIUS;
    public ModConfigSpec.IntValue PARTICLE_INJECTOR_RADIATION_RADIUS;
    public ModConfigSpec.IntValue RADIOACTIVE_PROCESSOR_RADIATION_RADIUS;
    public ModConfigSpec.IntValue RADIO_GENATOR_RADIATION_RADIUS;
    public ModConfigSpec.IntValue MOLTEN_SAL_SUPPLIER_RADIATION_RADIUS;
    public ModConfigSpec.IntValue DEFAULT_PARTICLE_COOLDOWN_TICKS;
    public ModConfigSpec.IntValue PARTICLE_SURVIVAL_TICKS;

    public NuclearConfig() {
	var builder = new ModConfigSpec.Builder();
	builder.push("common");
	TELEPORTER_USAGE_PER_TELEPORT = builder.defineInRange("teleporter_usage", 1000000.0, 0, Double.MAX_VALUE);
	RADIOISOTOPEGENERATOR_VOLTAGE = builder.defineInRange("radioisotopegenerator_voltage", 120.0, 0, Double.MAX_VALUE);
	RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER = builder.defineInRange("radioisotopegenerator_output_multiplier", 0.35f, 0, Double.MAX_VALUE);
	FISSIONREACTOR_MAXENERGYTARGET = builder.defineInRange("fissionreactor_max_energy_target", 350000.0, 0, Double.MAX_VALUE);
	MSRREACTOR_MAXENERGYTARGET = builder.defineInRange("msrreactor_max_energy_target", 850000.0, 0, Double.MAX_VALUE);
	FUSIONREACTOR_MAXENERGYTARGET = builder.defineInRange("fusionreactor_max_energy_target", 6000000.0, 0, Double.MAX_VALUE);
	FUSIONREACTOR_USAGE_PER_TICK = builder.defineInRange("fusionreactor_usage_per_tick", 50000.0, 0, Double.MAX_VALUE);
	FUSIONREACTOR_MAXSTORAGE = builder.defineInRange("fusionreactor_max_storage", 1024, 0, Integer.MAX_VALUE);
	PARTICLEINJECTOR_USAGE_PER_PARTICLE = builder.defineInRange("particle_injector_usage_per_particle", 200000000.0, 0, Double.MAX_VALUE);
	GASCENTRIFUGE_USAGE_PER_TICK = builder.defineInRange("gascentrifuge_usage_per_tick", 1500.0, 0, Double.MAX_VALUE);
	GASCENTRIFUGE_REQUIRED_TICKS_PER_PROCESSING = builder.defineInRange("gascentrifuge_required_ticks", 20, 0, Integer.MAX_VALUE);
	FREEZEPLUG_USAGE_PER_TICK = builder.defineInRange("freezeplug_usage_per_tick", 200.0, 0, Double.MAX_VALUE);
	MOLTENSALTSUPPLIER_VOLTAGE = builder.defineInRange("moltensaltsupplier_voltage", 120.0, 0, Double.MAX_VALUE);
	MOLTENSALTSUPPLIER_USAGE_PER_TICK = builder.defineInRange("moltensaltsupplier_usage_per_tick", 200.0, 0, Double.MAX_VALUE);
	ATOMICASSEMBLER_USAGE_PER_TICK = builder.defineInRange("atomicassembler_usage_per_tick", 6000.0, 0, Double.MAX_VALUE);
	ATOMICASSEMBLER_VOLTAGE = builder.defineInRange("atomicassembler_voltage", 480.0, 0, Double.MAX_VALUE);
	ATOMICASSEMBLER_REQUIRED_TICKS = builder.defineInRange("atomicassembler_required_ticks", 12000, 0, Integer.MAX_VALUE);
	QUANTUM_TUNNEL_FREQUENCY_CAP_PER_PLAYER = builder.defineInRange("quantum_tunnel_frequency_cap_per_player", 40, 0, Integer.MAX_VALUE);
	ANTIMATTER_TICKS_ON_GROUND = builder.defineInRange("antimatter_ticks_on_ground", 40, 0, Integer.MAX_VALUE);
	CLOUD_CHAMBER_ENERGY_USAGE_PER_TICK = builder.defineInRange("cloud_chamber_energy_usage_per_tick", 100, 0, Double.MAX_VALUE);
	CLOUD_CHAMBER_FLUID_USAGE_PER_TICK = builder.defineInRange("cloud_chamber_fluid_usage_per_tick", 1, 0, Integer.MAX_VALUE);
	FALLOUT_SCRUBBER_USAGE_PER_TICK = builder.defineInRange("fallout_scrubber_usage_per_tick", 100.0, 0, Double.MAX_VALUE);
	ATOMIC_ASSEMBLER_RADIATION_RADIUS = builder.defineInRange("atomic_assembler_radiation_radius", 5, 0, Integer.MAX_VALUE);
	CHEMICAL_EXTRACTOR_RADIATION_RADIUS = builder.defineInRange("chemical_extractor_radiation_radius", 3, 0, Integer.MAX_VALUE);
	FUEL_REPROCESSOR_RADIATION_RADIUS = builder.defineInRange("fuel_reprocessor_radiation_radius", 10, 0, Integer.MAX_VALUE);
	GAS_CENTRIFUGE_RADIATION_RADIUS = builder.defineInRange("gas_centrifuge_radiation_radius", 5, 0, Integer.MAX_VALUE);
	NUCLEAR_BOILER_RADIATION_RADIUS = builder.defineInRange("nuclear_boiler_radiation_radius", 5, 0, Integer.MAX_VALUE);
	PARTICLE_INJECTOR_RADIATION_RADIUS = builder.defineInRange("particle_injector_radiation_radius", 3, 0, Integer.MAX_VALUE);
	RADIOACTIVE_PROCESSOR_RADIATION_RADIUS = builder.defineInRange("radioactive_processor_radiation_radius", 5, 0, Integer.MAX_VALUE);
	RADIO_GENATOR_RADIATION_RADIUS = builder.defineInRange("radioisotopegenerator_radiation_radius", 10, 0, Integer.MAX_VALUE);
	MOLTEN_SAL_SUPPLIER_RADIATION_RADIUS = builder.defineInRange("molten_salt_supplier_radiation_radius", 5, 0, Integer.MAX_VALUE);
	DEFAULT_PARTICLE_COOLDOWN_TICKS = builder.defineInRange("particle_cooldown_tick", 50, 0, Integer.MAX_VALUE);
	PARTICLE_SURVIVAL_TICKS = builder.defineInRange("particle_survival_ticks", 800, 0, Integer.MAX_VALUE);
	builder.pop();
	SPEC = builder.build();
    }
}
