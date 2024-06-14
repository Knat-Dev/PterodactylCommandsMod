package com.dorpeled.pterodactylcommandsmod.events

import com.dorpeled.pterodactylcommandsmod.config.PterodactylCommandsConfig
import com.mojang.logging.LogUtils
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import org.slf4j.Logger

@Mod.EventBusSubscriber
object OpAdminLoginHandler {
    private val LOGGER: Logger? = LogUtils.getLogger()

    @SubscribeEvent
    fun onPlayerLogin(event: PlayerEvent.PlayerLoggedInEvent?) {
        val player: Player? = event?.entity
        if (player?.hasPermissions(4) == true) {
            LOGGER?.info("An op admin has logged in: {}", player.name.string)
            if (!PterodactylCommandsConfig.validate()) {
                val message = "Configuration is not set or invalid. /help for more info."
                LOGGER?.error(message)
                player.sendSystemMessage(Component.literal(message))
            }
        }
    }
}