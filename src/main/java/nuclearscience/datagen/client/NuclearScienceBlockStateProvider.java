package nuclearscience.datagen.client;

import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import nuclearscience.NuclearScience;
import nuclearscience.common.block.states.NuclearScienceBlockStates;
import nuclearscience.common.block.states.facing.FacingDirection;
import nuclearscience.common.block.subtype.SubtypeElectromagent;
import nuclearscience.common.block.subtype.SubtypeIrradiatedBlock;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.block.subtype.SubtypeRadiationShielding;
import nuclearscience.common.block.subtype.SubtypeReactorLogisticsCable;
import nuclearscience.registers.NuclearScienceBlocks;
import voltaic.Voltaic;
import voltaic.common.block.states.VoltaicBlockStates;
import voltaic.datagen.utils.client.BaseBlockstateProvider;

public class NuclearScienceBlockStateProvider extends BaseBlockstateProvider {
	
	private static final ResourceLocation STEEL_CASING = Voltaic.rl("block/steelcasing");

	public NuclearScienceBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, exFileHelper, NuclearScience.ID);
	}

	@Override
	protected void registerStatesAndModels() {

		simpleBlock(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.base), blockLoc("leadlinedblock"), true);
        doorBlockWithRenderType((DoorBlock) NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.door), blockLoc("leadlineddoor_bottom"), blockLoc("leadlineddoor_top"), Voltaic.vanillarl("cutout"));
        glassBlock(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.glass), blockLoc("leadlinedglass"), true);
        trapdoorBlockWithRenderType((TrapDoorBlock) NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.trapdoor), blockLoc("leadlinedtrapdoor"), true, Voltaic.vanillarl("cutout"));

        simpleColumnBlock(NuclearScienceBlocks.BLOCKS_ELECTROMAGENT.getValue(SubtypeElectromagent.electromagnet), blockLoc("electromagnet"), blockLoc("electromagnettop"), true);
        glassBlock(NuclearScienceBlocks.BLOCKS_ELECTROMAGENT.getValue(SubtypeElectromagent.electromagneticglass), blockLoc("electromagneticglass"), true);
        simpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.freezeplug), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.freezeplug)), true);
        simpleBlockCustomRenderType(NuclearScienceBlocks.BLOCK_PLASMA, blockLoc("plasma"), Voltaic.vanillarl("translucent"), true);
        airBlock(NuclearScienceBlocks.BLOCK_RADIOACTIVEAIR, "block/plasma", true);
        simpleBlock(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.soil), blockLoc("irradiatedblocksoil"), true);
        simpleColumnBlock(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.petrifiedwood), modLoc("block/irradiatedblockpetrifiedwood"), modLoc("block/irradiatedblockpetrifiedwoodtop"), true);

        Block block = NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.grass);
        BlockModelBuilder builder = models().cube(
                //
                name(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.grass)),
                //
                modLoc("block/irradiatedblocksoil"),
                //
                modLoc("block/irradiatedblockgrass"),
                //
                modLoc("block/irradiatedblockgrassside"),
                //
                modLoc("block/irradiatedblockgrassside"),
                //
                modLoc("block/irradiatedblockgrassside"),
                //
                modLoc("block/irradiatedblockgrassside")
                //
        ).texture("particle", modLoc("block/irradiatedblocksoil"));
        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(builder));
        blockItem(block, builder);

        simpleBlock(NuclearScienceBlocks.BLOCK_MELTEDREACTOR, existingBlock(NuclearScienceBlocks.BLOCK_MELTEDREACTOR), true);
        simpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.radioisotopegenerator), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.radioisotopegenerator)), true);
        simpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.siren), blockLoc("siren"), true);
        simpleBlock(NuclearScienceBlocks.BLOCK_TURBINE, existingBlock(blockLoc("turbinecasing")), false);
        simpleColumnBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.steamfunnel), blockLoc("steamfunnelside"), blockLoc("steamfunneltop"), true);
        simpleBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH, existingBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH), true);

        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.atomicassembler), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.atomicassembler)), false);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.chemicalextractor), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.chemicalextractor)), true);
        simpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioncontrolrod), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioncontrolrod)), false);
        rotatedLeftRightBlock(NuclearScienceBlocks.BLOCK_ELECTORMAGNETICBOOSTER.get(), existingBlock(blockLoc("electromagneticbooster")), existingBlock(blockLoc("electromagneticboosterleft")), existingBlock(blockLoc("electromagneticboosterright")), 90, true);
        horrRotatedLitBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fuelreprocessor), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fuelreprocessor)), existingBlock(blockLoc("fuelreprocessoron")), true);
        horrRotatedLitBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusionreactorcore), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusionreactorcore)), existingBlock(blockLoc("fusionreactorcoreon")), true);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.gascentrifuge), existingBlock(blockLoc("gascentrifugeoutline")), 180, 0, false);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.heatexchanger), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.heatexchanger)), true);
        horrRotatedLitBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.moltensaltsupplier), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.moltensaltsupplier)), existingBlock(blockLoc("moltensaltsupplieron")), true);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msrfuelpreprocessor), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msrfuelpreprocessor)), true);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msreactorcore), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msreactorcore)), 180, 0, true);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.nuclearboiler), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.nuclearboiler)), 180, 0, true);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.particleinjector), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.particleinjector)), 180, 0, false);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.quantumcapacitor), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.quantumcapacitor)), true);
        horrRotatedLitBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.radioactiveprocessor), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.radioactiveprocessor)), existingBlock(blockLoc("radioactiveprocessoron")), true);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissionreactorcore), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissionreactorcore)), true);
        horrRotatedLitBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.teleporter), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.teleporter)), existingBlock(blockLoc("teleporteron")), true);

        simpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.chunkloader), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.chunkloader)), true);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.mscontrolrod), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.mscontrolrod)), 180, 0, false);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.cloudchamber), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.cloudchamber)), 0, 0, true);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.falloutscrubber), existingBlock(blockLoc("falloutscrubberframe")), false);
        horrRotatedLitBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.logisticscontroller), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.logisticscontroller)), existingBlock(blockLoc("logisticscontrolleron")), true);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.supplymodule), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.supplymodule)), true);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioninterface), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioninterface)), false);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msinterface), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msinterface)), 180, 0, false);
        simpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusioninterface), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusioninterface)), true);

        horrRotatedLitBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.controlrodmodule), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.controlrodmodule)), existingBlock(blockLoc("controlrodmoduleon")), false);
        horrRotatedLitBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.monitormodule), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.monitormodule)), existingBlock(blockLoc("monitormoduleon")), true);
        horrRotatedLitBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.thermometermodule), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.thermometermodule)), existingBlock(blockLoc("thermometermoduleon")), true);
        simpleBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICGATEWAY, existingBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICGATEWAY), true);
        horrRotatedBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICDIODE, existingBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICDIODE), true);



        genPipes();
        genLogisticsCables();

    }

    private void genPipes() {

        String parent = "parent/";
        String name = "block/pipe/";
        String texture = "pipe/";

        for (SubtypeMoltenSaltPipe pipe : SubtypeMoltenSaltPipe.values()) {
            wire(
                    //
                    NuclearScienceBlocks.BLOCKS_MOLTENSALTPIPE.getValue(pipe),
                    //
                    models().withExistingParent(name + pipe.tag() + "_none", modLoc(parent + "pipe_none")).texture("texture", blockLoc(texture + pipe.tag())).texture("particle", "#texture").renderType("cutout"),
                    //
                    models().withExistingParent(name + pipe.tag() + "_side", modLoc(parent + "pipe_side")).texture("texture", blockLoc(texture + pipe.tag())).texture("particle", "#texture").renderType("cutout"),
                    //
                    false);
        }

    }

    private void genLogisticsCables() {

        String parent = "parent/";
        String name = "block/pipe/";
        String texture = "pipe/";

        for (SubtypeReactorLogisticsCable cable : SubtypeReactorLogisticsCable.values()) {
            wire(
                    //
                    NuclearScienceBlocks.BLOCKS_REACTORLOGISTICSCABLE.getValue(cable),
                    //
                    models().withExistingParent(name + cable.tag() + "_none", modLoc(parent + "logisticscable_none")).texture("texture", blockLoc(texture + cable.tag() + "_none")).texture("particle", STEEL_CASING).renderType("cutout"),
                    //
                    models().withExistingParent(name + cable.tag() + "_side", modLoc(parent + "logisticscable_side")).texture("texture", blockLoc(texture + cable.tag() + "_side")).texture("particle", STEEL_CASING).renderType("cutout"),
                    //
                    false);
        }

    }

    public ItemModelBuilder rotatedLeftRightBlock(Block block, ModelFile none, ModelFile left, ModelFile right, boolean registerItem) {
        return rotatedLeftRightBlock(block, none, left, right, 0, registerItem);
    }

    public ItemModelBuilder rotatedLeftRightBlock(Block block, ModelFile none, ModelFile left, ModelFile right, int rotationOffset, boolean registerItem) {
        getVariantBuilder(block)
                //
                .partialState().with(VoltaicBlockStates.FACING, Direction.NORTH).with(NuclearScienceBlockStates.FACINGDIRECTION, FacingDirection.NONE).modelForState().modelFile(none).rotationY((270 + rotationOffset) % 360).addModel()
                //
                .partialState().with(VoltaicBlockStates.FACING, Direction.EAST).with(NuclearScienceBlockStates.FACINGDIRECTION, FacingDirection.NONE).modelForState().modelFile(none).rotationY((0 + rotationOffset) % 360).addModel()
                //
                .partialState().with(VoltaicBlockStates.FACING, Direction.SOUTH).with(NuclearScienceBlockStates.FACINGDIRECTION, FacingDirection.NONE).modelForState().modelFile(none).rotationY((90 + rotationOffset) % 360).addModel()
                //
                .partialState().with(VoltaicBlockStates.FACING, Direction.WEST).with(NuclearScienceBlockStates.FACINGDIRECTION, FacingDirection.NONE).modelForState().modelFile(none).rotationY((180 + rotationOffset) % 360).addModel()
                //
                .partialState().with(VoltaicBlockStates.FACING, Direction.NORTH).with(NuclearScienceBlockStates.FACINGDIRECTION, FacingDirection.LEFT).modelForState().modelFile(left).rotationY((270 + rotationOffset) % 360).addModel()
                //
                .partialState().with(VoltaicBlockStates.FACING, Direction.EAST).with(NuclearScienceBlockStates.FACINGDIRECTION, FacingDirection.LEFT).modelForState().modelFile(left).rotationY((0 + rotationOffset) % 360).addModel()
                //
                .partialState().with(VoltaicBlockStates.FACING, Direction.SOUTH).with(NuclearScienceBlockStates.FACINGDIRECTION, FacingDirection.LEFT).modelForState().modelFile(left).rotationY((90 + rotationOffset) % 360).addModel()
                //
                .partialState().with(VoltaicBlockStates.FACING, Direction.WEST).with(NuclearScienceBlockStates.FACINGDIRECTION, FacingDirection.LEFT).modelForState().modelFile(left).rotationY((180 + rotationOffset) % 360).addModel()
                //
                .partialState().with(VoltaicBlockStates.FACING, Direction.NORTH).with(NuclearScienceBlockStates.FACINGDIRECTION, FacingDirection.RIGHT).modelForState().modelFile(right).rotationY((270 + rotationOffset) % 360).addModel()
                //
                .partialState().with(VoltaicBlockStates.FACING, Direction.EAST).with(NuclearScienceBlockStates.FACINGDIRECTION, FacingDirection.RIGHT).modelForState().modelFile(right).rotationY((0 + rotationOffset) % 360).addModel()
                //
                .partialState().with(VoltaicBlockStates.FACING, Direction.SOUTH).with(NuclearScienceBlockStates.FACINGDIRECTION, FacingDirection.RIGHT).modelForState().modelFile(right).rotationY((90 + rotationOffset) % 360).addModel()
                //
                .partialState().with(VoltaicBlockStates.FACING, Direction.WEST).with(NuclearScienceBlockStates.FACINGDIRECTION, FacingDirection.RIGHT).modelForState().modelFile(right).rotationY((180 + rotationOffset) % 360).addModel();

        if (registerItem) {
            return blockItem(block, none);
        }
        return null;

    }

}
