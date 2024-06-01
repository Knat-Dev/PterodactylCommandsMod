package com.dorpeled.pterodactylcommandsmod.events;

import com.dorpeled.pterodactylcommandsmod.config.PterodactylCommandsConfig;
import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod.EventBusSubscriber
public class OpAdminLoginHandler {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (player.hasPermissions(4)) {
            LOGGER.info("An op admin has logged in: {}", player.getName().getString());

            if (!PterodactylCommandsConfig.validate()) {
                String message = "Configuration is not set or invalid. /help for more info.";
                LOGGER.error(message);
                player.sendSystemMessage(Component.literal(message));
            }
        }
    }
}