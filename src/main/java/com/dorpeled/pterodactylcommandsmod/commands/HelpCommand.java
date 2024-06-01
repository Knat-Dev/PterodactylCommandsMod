package com.dorpeled.pterodactylcommandsmod.commands;

import com.dorpeled.pterodactylcommandsmod.util.Constants;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class HelpCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("pterodactyl")
                .then(Commands.literal("help")
                        .executes(HelpCommand::executeHelp))
        );
    }

    private static int executeHelp(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        source.sendSuccess(() -> Component.literal(Constants.HELP_MESSAGE), false);
        return 1;
    }
}
