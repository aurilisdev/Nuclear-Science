package nuclearscience.registers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import nuclearscience.NuclearScience;
import nuclearscience.common.block.BlockElectromagneticBooster;
import nuclearscience.common.block.BlockElectromagneticDiode;
import nuclearscience.common.block.BlockElectromagneticGateway;
import nuclearscience.common.block.BlockElectromagneticSwitch;
import nuclearscience.common.block.BlockIrradiated;
import nuclearscience.common.block.BlockMeltedReactor;
import nuclearscience.common.block.BlockPlasma;
import nuclearscience.common.block.BlockRadioactiveAir;
import nuclearscience.common.block.BlockTurbine;
import nuclearscience.common.block.connect.BlockMoltenSaltPipe;
import nuclearscience.common.block.connect.BlockReactorLogisticsCable;
import nuclearscience.common.block.subtype.SubtypeElectromagent;
import nuclearscience.common.block.subtype.SubtypeIrradiatedBlock;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.block.subtype.SubtypeRadiationShielding;
import nuclearscience.common.block.subtype.SubtypeReactorLogisticsCable;
import nuclearscience.common.tile.TileQuantumTunnel;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;
import voltaic.api.registration.BulkDeferredHolder;
import voltaic.common.block.BlockCustomGlass;
import voltaic.common.block.BlockMachine;
import voltaic.prefab.utilities.math.Color;

public class NuclearScienceBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, NuclearScience.ID);

    public static final BulkDeferredHolder<Block, Block, SubtypeRadiationShielding> BLOCKS_RADIATION_SHIELDING = new BulkDeferredHolder<>(SubtypeRadiationShielding.values(), subtype -> BLOCKS.register(subtype.tag(), () -> {
        if (subtype == SubtypeRadiationShielding.door) {
            return new DoorBlock(BlockSetType.IRON, subtype.properties);
        } else if (subtype == SubtypeRadiationShielding.trapdoor) {
            return new TrapDoorBlock(BlockSetType.IRON, subtype.properties);
        } else if (subtype == SubtypeRadiationShielding.glass) {
            return new BlockCustomGlass(5.0f, 3.0f);
        } else {
            return new Block(subtype.properties);
        }
    }));

    public static final DeferredHolder<Block, BlockTurbine> BLOCK_TURBINE = BLOCKS.register("turbine", BlockTurbine::new);

    public static final BulkDeferredHolder<Block, BlockMachine, SubtypeNuclearMachine> BLOCKS_NUCLEARMACHINE = new BulkDeferredHolder<>(SubtypeNuclearMachine.values(), subtype -> BLOCKS.register(subtype.tag(), () -> {

        if (subtype == SubtypeNuclearMachine.chunkloader) {
            return new BlockMachine(subtype) {
                @Override
                public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
                    return 15;
                }
            };
        } else if (subtype == SubtypeNuclearMachine.fissionreactorcore) {
            return new BlockMachine(subtype) {
                @Override
                public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
                    if (world.getBlockEntity(pos) instanceof TileFissionReactorCore core) {
                        return (int) Math.max(0, Math.min(core.temperature.getValue() / TileFissionReactorCore.MELTDOWN_TEMPERATURE_ACTUAL * 15, 15));
                    }
                    return super.getLightEmission(state, world, pos);
                }
            };
        } else {
            return new BlockMachine(subtype);
        }

    })

    );

    public static final BulkDeferredHolder<Block, Block, SubtypeElectromagent> BLOCKS_ELECTROMAGENT = new BulkDeferredHolder<>(SubtypeElectromagent.values(), subtype -> BLOCKS.register(subtype.tag(), () -> {

        if(subtype == SubtypeElectromagent.electromagneticglass) {
            return new BlockCustomGlass(3.5F, 20);
        }
	return new Block(Blocks.IRON_BLOCK.properties().strength(3.5F, 20).requiresCorrectToolForDrops());

    }));

    //public static final DeferredHolder<Block, BlockElectromagnet> BLOCK_ELECTROMAGNET = BLOCKS.register("electromagnet", () -> new BlockElectromagnet(Blocks.IRON_BLOCK.properties(), false));
    //public static final DeferredHolder<Block, BlockElectromagnet> BLOCK_ELECTROMAGNETICGLASS = BLOCKS.register("electromagneticglass", () -> new BlockElectromagnet(Blocks.GLASS.properties(), true));
    public static final DeferredHolder<Block, BlockElectromagneticBooster> BLOCK_ELECTORMAGNETICBOOSTER = BLOCKS.register("electromagneticbooster", BlockElectromagneticBooster::new);
    public static final DeferredHolder<Block, BlockElectromagneticSwitch> BLOCK_ELECTROMAGNETICSWITCH = BLOCKS.register("electromagneticswitch", BlockElectromagneticSwitch::new);
    public static final DeferredHolder<Block, BlockElectromagneticGateway> BLOCK_ELECTROMAGNETICGATEWAY = BLOCKS.register("electromagneticgateway", BlockElectromagneticGateway::new);
    public static final DeferredHolder<Block, BlockElectromagneticDiode> BLOCK_ELECTROMAGNETICDIODE = BLOCKS.register("electromagneticdiode", BlockElectromagneticDiode::new);
    public static final DeferredHolder<Block, BlockPlasma> BLOCK_PLASMA = BLOCKS.register("plasma", BlockPlasma::new);
    public static final BulkDeferredHolder<Block, BlockMoltenSaltPipe, SubtypeMoltenSaltPipe> BLOCKS_MOLTENSALTPIPE = new BulkDeferredHolder<>(SubtypeMoltenSaltPipe.values(), subtype -> BLOCKS.register(subtype.tag(), () -> new BlockMoltenSaltPipe(subtype)));
    public static final BulkDeferredHolder<Block, BlockReactorLogisticsCable, SubtypeReactorLogisticsCable> BLOCKS_REACTORLOGISTICSCABLE = new BulkDeferredHolder<>(SubtypeReactorLogisticsCable.values(), subtype -> BLOCKS.register(subtype.tag(), () -> new BlockReactorLogisticsCable(subtype)));
    public static final DeferredHolder<Block, BlockMeltedReactor> BLOCK_MELTEDREACTOR = BLOCKS.register("meltedreactor", BlockMeltedReactor::new);
    public static final DeferredHolder<Block, BlockRadioactiveAir> BLOCK_RADIOACTIVEAIR = BLOCKS.register("radioactiveair", BlockRadioactiveAir::new);
    public static final BulkDeferredHolder<Block, BlockIrradiated, SubtypeIrradiatedBlock> BLOCKS_IRRADIATED = new BulkDeferredHolder<>(SubtypeIrradiatedBlock.values(), subtype -> BLOCKS.register(subtype.tag(), () -> new BlockIrradiated(subtype)));




    @EventBusSubscriber(value = Dist.CLIENT, modid = NuclearScience.ID, bus = EventBusSubscriber.Bus.MOD)
    private static class ColorHandlerInternal {

        private static final Color NONE = new Color(114, 114, 114, 255);
        private static final Color INPUT = new Color(167, 223, 248, 255);
        private static final Color OUTPUT = new Color(255, 120, 46, 255);

        @SubscribeEvent
        public static void registerColoredBlocks(RegisterColorHandlersEvent.Block event) {
            Block block = BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.quantumcapacitor);

            event.register((state, level, pos, tintIndex) -> {
                if (tintIndex >= 1) {

                    BlockEntity tile = level.getBlockEntity(pos);

                    if(tile instanceof TileQuantumTunnel tunnel) {

                        Direction dir = getDirFromIndex(tintIndex);

                        if(tunnel.readInputDirections().contains(dir)) {
                            return INPUT.color();
                        } else if (tunnel.readOutputDirections().contains(dir)) {
                            return OUTPUT.color();
                        } else {
                            return NONE.color();
                        }

                    }
		    return NONE.color();
                }
                return Color.WHITE.color();
            }, block);
        }
    }

    private static Direction getDirFromIndex(int index) {
        if(index == 1) {
            return Direction.SOUTH.getCounterClockWise();
        } else if (index == 2) {
            return Direction.NORTH.getCounterClockWise();
        } else if (index == 3) {
            return Direction.EAST.getCounterClockWise();
        } else if (index == 4) {
            return Direction.WEST.getCounterClockWise();
        } else if (index == 5) {
            return Direction.UP;
        } else if (index == 6) {
            return Direction.DOWN;
        }
        return Direction.UP;
    }

}
