package nuclearscience.registers;

import net.minecraft.ChatFormatting;
import net.minecraftforge.eventbus.api.IEventBus;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.common.blockitem.BlockItemDescriptable;
import voltaic.prefab.utilities.VoltaicTextUtils;

public class UnifiedNuclearScienceRegister {
	public static void register(IEventBus bus) {
		NuclearScienceBlocks.BLOCKS.register(bus);
		NuclearScienceItems.ITEMS.register(bus);
		NuclearScienceTiles.BLOCK_ENTITY_TYPES.register(bus);
		NuclearScienceMenuTypes.MENU_TYPES.register(bus);
		NuclearScienceFluids.FLUIDS.register(bus);
		NuclearScienceFluidTypes.FLUID_TYPES.register(bus);
		NuclearScienceEntities.ENTITIES.register(bus);
		NuclearScienceSounds.SOUNDS.register(bus);
		NuclearScienceRecipies.RECIPE_TYPES.register(bus);
		NuclearScienceRecipies.RECIPE_SERIALIZER.register(bus);
		NuclearScienceParticles.PARTICLES.register(bus);
	}

	static {
		// Machines
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.gascentrifuge), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.nuclearboiler), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.chemicalextractor), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.particleinjector), VoltaicTextUtils.voltageTooltip(960));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.teleporter), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fuelreprocessor), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.radioactiveprocessor), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msrfuelpreprocessor), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.moltensaltsupplier), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusionreactorcore), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.freezeplug), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.atomicassembler), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.cloudchamber), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.falloutscrubber), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.logisticscontroller), VoltaicTextUtils.voltageTooltip(120));

		// Generators
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.radioisotopegenerator), VoltaicTextUtils.voltageTooltip(120));

		// Misc
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.steamfunnel), NuclearTextUtils.tooltip("steamfunneluse").withStyle(ChatFormatting.DARK_GRAY));
	}

}
