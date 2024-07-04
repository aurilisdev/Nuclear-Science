package nuclearscience.datagen.client;

import electrodynamics.datagen.client.ElectrodynamicsBlockStateProvider;
import electrodynamics.prefab.block.GenericEntityBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import nuclearscience.References;
import nuclearscience.common.block.BlockElectromagneticBooster;
import nuclearscience.common.block.facing.FacingDirection;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.registers.NuclearScienceBlocks;

public class NuclearScienceBlockStateProvider extends ElectrodynamicsBlockStateProvider {

	public NuclearScienceBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, exFileHelper, References.ID);
	}

	@Override
	protected void registerStatesAndModels() {

		simpleBlock(NuclearScienceBlocks.blocklead, blockLoc("blocklead"), true);
		simpleColumnBlock(NuclearScienceBlocks.blockElectromagnet, blockLoc("electromagnet"), blockLoc("electromagnettop"), true);
		glassBlock(NuclearScienceBlocks.blockElectromagneticGlass, blockLoc("electromagneticglass"), true);
		simpleBlock(NuclearScienceBlocks.blockFreezePlug, existingBlock(NuclearScienceBlocks.blockFreezePlug), true);
		simpleBlockCustomRenderType(NuclearScienceBlocks.blockPlasma, blockLoc("plasma"), new ResourceLocation("translucent"), true);
		airBlock(NuclearScienceBlocks.blockRadioactiveAir, "block/plasma", true);
		snowyBlock(NuclearScienceBlocks.blockRadioactiveSoil, cubeAll(NuclearScienceBlocks.blockRadioactiveSoil), existingBlock(new ResourceLocation("block/grass_block_snow")), true);
		simpleBlock(NuclearScienceBlocks.blockMeltedReactor, existingBlock(NuclearScienceBlocks.blockMeltedReactor), true);
		simpleBlock(NuclearScienceBlocks.blockRadioisotopeGenerator, existingBlock(NuclearScienceBlocks.blockRadioisotopeGenerator), true);
		simpleBlock(NuclearScienceBlocks.blockSiren, blockLoc("siren"), true);
		simpleBlock(NuclearScienceBlocks.blockTurbine, existingBlock(blockLoc("turbinecasing")), false);
		simpleColumnBlock(NuclearScienceBlocks.blockSteamFunnel, blockLoc("steamfunnelside"), blockLoc("steamfunneltop"), true);
		simpleBlock(NuclearScienceBlocks.blockElectromagneticSwitch, existingBlock(NuclearScienceBlocks.blockElectromagneticSwitch), true);

		horrRotatedBlock(NuclearScienceBlocks.blockAtomicAssembler, existingBlock(NuclearScienceBlocks.blockAtomicAssembler), false);
		horrRotatedBlock(NuclearScienceBlocks.blockChemicalExtractor, existingBlock(NuclearScienceBlocks.blockChemicalExtractor), true);
		horrRotatedBlock(NuclearScienceBlocks.blockControlRodAssembly, existingBlock(NuclearScienceBlocks.blockControlRodAssembly), 180, 0, false);
		rotatedLeftRightBlock(NuclearScienceBlocks.blockElectromagneticBooster, existingBlock(blockLoc("electromagneticbooster")), existingBlock(blockLoc("electromagneticboosterleft")), existingBlock(blockLoc("electromagneticboosterright")), 90, true);
		horrRotatedLitBlock(NuclearScienceBlocks.blockFuelReprocessor, existingBlock(NuclearScienceBlocks.blockFuelReprocessor), existingBlock(blockLoc("fuelreprocessoron")), true);
		horrRotatedBlock(NuclearScienceBlocks.blockFusionReactorCore, existingBlock(NuclearScienceBlocks.blockFusionReactorCore), true);
		horrRotatedBlock(NuclearScienceBlocks.blockGasCentrifuge, existingBlock(blockLoc("gascentrifugeoutline")), 180, 0, false);
		horrRotatedBlock(NuclearScienceBlocks.blockHeatExchanger, existingBlock(NuclearScienceBlocks.blockHeatExchanger), true);
		horrRotatedLitBlock(NuclearScienceBlocks.blockMoltenSaltSupplier, existingBlock(NuclearScienceBlocks.blockMoltenSaltSupplier), existingBlock(blockLoc("moltensaltsupplieron")), true);
		horrRotatedBlock(NuclearScienceBlocks.blockMSRFuelPreProcessor, existingBlock(NuclearScienceBlocks.blockMSRFuelPreProcessor), true);
		horrRotatedBlock(NuclearScienceBlocks.blockMSReactorCore, existingBlock(NuclearScienceBlocks.blockMSReactorCore), 180, 0, true);
		horrRotatedBlock(NuclearScienceBlocks.blockNuclearBoiler, existingBlock(NuclearScienceBlocks.blockNuclearBoiler), 180, 0, true);
		horrRotatedBlock(NuclearScienceBlocks.blockParticleInjector, existingBlock(NuclearScienceBlocks.blockParticleInjector), 180, 0, false);
		horrRotatedBlock(NuclearScienceBlocks.blockQuantumCapacitor, existingBlock(NuclearScienceBlocks.blockQuantumCapacitor), true);
		horrRotatedLitBlock(NuclearScienceBlocks.blockRadioactiveProcessor, existingBlock(NuclearScienceBlocks.blockRadioactiveProcessor), existingBlock(blockLoc("radioactiveprocessoron")), true);
		horrRotatedBlock(NuclearScienceBlocks.blockFissionReactorCore, existingBlock(NuclearScienceBlocks.blockFissionReactorCore), true);
		horrRotatedLitBlock(NuclearScienceBlocks.blockTeleporter, existingBlock(NuclearScienceBlocks.blockTeleporter), existingBlock(blockLoc("teleporteron")), true);

		genPipes();

	}

	private void genPipes() {

		String parent = "parent/";
		String name = "block/pipe/";
		String texture = "pipe/";

		for (SubtypeMoltenSaltPipe pipe : SubtypeMoltenSaltPipe.values()) {
			wire(NuclearScienceBlocks.getBlock(pipe), models().withExistingParent(name + pipe.tag() + "_none", modLoc(parent + "pipe_none")).texture("texture", blockLoc(texture + pipe.tag())).texture("particle", "#texture").renderType("cutout"), models().withExistingParent(name + pipe.tag() + "_side", modLoc(parent + "pipe_side")).texture("texture", blockLoc(texture + pipe.tag())).texture("particle", "#texture").renderType("cutout"), false);
		}

	}

	public ItemModelBuilder rotatedLeftRightBlock(Block block, ModelFile none, ModelFile left, ModelFile right, boolean registerItem) {
		return rotatedLeftRightBlock(block, none, left, right, 0, registerItem);
	}

	public ItemModelBuilder rotatedLeftRightBlock(Block block, ModelFile none, ModelFile left, ModelFile right, int rotationOffset, boolean registerItem) {
		getVariantBuilder(block).partialState().with(GenericEntityBlock.FACING, Direction.NORTH).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.NONE).modelForState().modelFile(none).rotationY((270 + rotationOffset) % 360).addModel().partialState().with(GenericEntityBlock.FACING, Direction.EAST).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.NONE).modelForState().modelFile(none).rotationY((0 + rotationOffset) % 360).addModel().partialState().with(GenericEntityBlock.FACING, Direction.SOUTH).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.NONE).modelForState().modelFile(none).rotationY((90 + rotationOffset) % 360).addModel().partialState().with(GenericEntityBlock.FACING, Direction.WEST).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.NONE).modelForState().modelFile(none).rotationY((180 + rotationOffset) % 360).addModel().partialState().with(GenericEntityBlock.FACING, Direction.NORTH).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.LEFT).modelForState().modelFile(left).rotationY((270 + rotationOffset) % 360).addModel().partialState().with(GenericEntityBlock.FACING, Direction.EAST).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.LEFT).modelForState().modelFile(left).rotationY((0 + rotationOffset) % 360).addModel().partialState().with(GenericEntityBlock.FACING, Direction.SOUTH).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.LEFT).modelForState().modelFile(left).rotationY((90 + rotationOffset) % 360).addModel().partialState().with(GenericEntityBlock.FACING, Direction.WEST).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.LEFT).modelForState().modelFile(left).rotationY((180 + rotationOffset) % 360).addModel().partialState().with(GenericEntityBlock.FACING, Direction.NORTH).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.RIGHT).modelForState().modelFile(right).rotationY((270 + rotationOffset) % 360).addModel().partialState().with(GenericEntityBlock.FACING, Direction.EAST).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.RIGHT).modelForState().modelFile(right).rotationY((0 + rotationOffset) % 360).addModel().partialState().with(GenericEntityBlock.FACING, Direction.SOUTH).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.RIGHT).modelForState().modelFile(right).rotationY((90 + rotationOffset) % 360).addModel().partialState().with(GenericEntityBlock.FACING, Direction.WEST).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.RIGHT).modelForState().modelFile(right).rotationY((180 + rotationOffset) % 360).addModel();

		if (registerItem) {
			return blockItem(block, none);
		}
		return null;

	}

}
