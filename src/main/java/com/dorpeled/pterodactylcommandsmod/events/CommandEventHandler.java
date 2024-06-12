package com.dorpeled.pterodactylcommandsmod.events;

import com.dorpeled.pterodactylcommandsmod.commands.ConfigCommands;
import com.dorpeled.pterodactylcommandsmod.commands.ExecuteScheduleCommand;
import com.dorpeled.pterodactylcommandsmod.commands.HelpCommand;
import com.mojang.logging.LogUtils;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod.EventBusSubscriber
public class CommandEventHandler {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        HelpCommand.register(event.getDispatcher());
        ConfigCommands.register(event.getDispatcher());
        ExecuteScheduleCommand.register(event.getDispatcher());
        LOGGER.info("Registered Command Event Handler");
    }
}
