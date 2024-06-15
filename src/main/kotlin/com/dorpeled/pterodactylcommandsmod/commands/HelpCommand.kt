package com.dorpeled.pterodactylcommandsmod.commands

import com.dorpeled.pterodactylcommandsmod.util.Constants
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component

object HelpCommand {
    fun register(dispatcher: CommandDispatcher<CommandSourceStack?>?) {
        dispatcher?.register(
            Commands.literal("pterodactyl")
                .then(
                    Commands.literal("help")
                        .executes(HelpCommand::executeHelp)
                )
        )
    }

    private fun executeHelp(context: CommandContext<CommandSourceStack?>?): Int {
        context?.source?.sendSuccess({ Component.literal(Constants.HELP_MESSAGE) }, false)
        return 1
    }
}
