package nuclearscience.registers;

import net.minecraft.ChatFormatting;
import net.neoforged.bus.api.IEventBus;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.common.blockitem.BlockItemDescriptable;
import voltaic.prefab.utilities.VoltaicTextUtils;

public class UnifiedNuclearScienceRegister {

	public static void register(IEventBus bus) {
		NuclearScienceArmorMaterials.ARMOR_MATERIALS.register(bus);
		NuclearScienceAttachmentTypes.ATTACHMENT_TYPES.register(bus);
		NuclearScienceBlocks.BLOCKS.register(bus);
		NuclearScienceItems.ITEMS.register(bus);
		NuclearScienceTiles.BLOCK_ENTITY_TYPES.register(bus);
		NuclearScienceMenuTypes.MENU_TYPES.register(bus);
		NuclearScienceFluids.FLUIDS.register(bus);
		NuclearScienceFluidTypes.FLUID_TYPES.register(bus);
		NuclearScienceEntities.ENTITIES.register(bus);
		NuclearScienceSounds.SOUNDS.register(bus);
		NuclearScienceGases.GASES.register(bus);
		NuclearScienceCreativeTabs.CREATIVE_TABS.register(bus);
		NuclearScienceRecipies.RECIPE_TYPES.register(bus);
		NuclearScienceRecipies.RECIPE_SERIALIZER.register(bus);
		NuclearScienceParticles.PARTICLES.register(bus);
	}

	static {
		// Machines
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.gascentrifuge), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.nuclearboiler), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.chemicalextractor), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.particleinjector), VoltaicTextUtils.voltageTooltip(960));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.teleporter), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.fuelreprocessor), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.radioactiveprocessor), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.msrfuelpreprocessor), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.moltensaltsupplier), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.fusionreactorcore), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.freezeplug), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.atomicassembler), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.cloudchamber), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.falloutscrubber), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.logisticscontroller), VoltaicTextUtils.voltageTooltip(120));

		// Generators
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.radioisotopegenerator), VoltaicTextUtils.voltageTooltip(120));

		// Misc
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.steamfunnel), NuclearTextUtils.tooltip("steamfunneluse").withStyle(ChatFormatting.DARK_GRAY));
	}

}
