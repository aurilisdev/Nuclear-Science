package nuclearscience.common.command;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import nuclearscience.NuclearScience;
import nuclearscience.api.quantumtunnel.TunnelFrequencyManager;

public class CommandWipeAllFrequencies {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {

        dispatcher.register(Commands.literal(NuclearScience.ID).requires(source -> source.hasPermission(4)).then(Commands.literal("wipeallfrequencies").executes(source -> {

            TunnelFrequencyManager.wipeAllFrequencies();
            source.getSource().sendSuccess(new StringTextComponent("wiped"), true);
            return 1;
        })));


    }


}
