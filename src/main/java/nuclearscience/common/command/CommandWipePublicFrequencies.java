package nuclearscience.common.command;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import nuclearscience.NuclearScience;
import nuclearscience.api.quantumtunnel.TunnelFrequencyManager;

public class CommandWipePublicFrequencies {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {

        dispatcher.register(Commands.literal(NuclearScience.ID).requires(source -> source.hasPermission(3)).then(Commands.literal("wipepublicfrequencies").executes(source -> {

            TunnelFrequencyManager.wipePublicFrequencies();
            source.getSource().sendSuccess(new StringTextComponent("wiped"), true);
            return 1;
        })));


    }
}
