package com.dorpeled.pterodactylcommandsmod.events

import com.dorpeled.pterodactylcommandsmod.commands.ConfigCommands
import com.dorpeled.pterodactylcommandsmod.commands.ExecuteScheduleCommand
import com.dorpeled.pterodactylcommandsmod.commands.HelpCommand
import com.mojang.logging.LogUtils
import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import org.slf4j.Logger

@Mod.EventBusSubscriber
object CommandEventHandler {
    private val LOGGER: Logger? = LogUtils.getLogger()
    @SubscribeEvent
    fun onRegisterCommands(event: RegisterCommandsEvent?) {
        LOGGER?.info("Registering Command Event Handler")
        HelpCommand.register(event?.dispatcher)
        ConfigCommands.register(event?.dispatcher)
        ExecuteScheduleCommand.register(event?.dispatcher)
        LOGGER?.info("Registered Command Event Handler")
    }
}
