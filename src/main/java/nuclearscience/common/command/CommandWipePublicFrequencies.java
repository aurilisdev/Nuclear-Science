package nuclearscience.common.command;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import nuclearscience.NuclearScience;
import nuclearscience.api.quantumtunnel.TunnelFrequencyManager;

public class CommandWipePublicFrequencies {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(Commands.literal(NuclearScience.ID).requires(source -> source.hasPermission(3)).then(Commands.literal("wipepublicfrequencies").executes(source -> {

            TunnelFrequencyManager.wipePublicFrequencies();
            source.getSource().sendSuccess(() -> Component.literal("wiped"), true);
            return 1;
        })));


    }
}
