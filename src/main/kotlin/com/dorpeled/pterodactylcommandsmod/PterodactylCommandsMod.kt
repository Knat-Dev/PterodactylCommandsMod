package com.dorpeled.pterodactylcommandsmod

import com.dorpeled.pterodactylcommandsmod.config.PterodactylCommandsConfig
import com.dorpeled.pterodactylcommandsmod.events.CommandEventHandler
import com.dorpeled.pterodactylcommandsmod.events.ConfigEventHandler
import com.dorpeled.pterodactylcommandsmod.events.OpAdminLoginHandler
import com.mojang.logging.LogUtils
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.slf4j.Logger

@Mod(PterodactylCommandsMod.MODID)
class PterodactylCommandsMod {
    init {
        LOGGER.info("Starting Pterodactyl Commands")
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, PterodactylCommandsConfig.config)
        MinecraftForge.EVENT_BUS.register(CommandEventHandler)
        MinecraftForge.EVENT_BUS.register(OpAdminLoginHandler)
        val modEventBus: IEventBus = FMLJavaModLoadingContext.get().modEventBus
        modEventBus.register(ConfigEventHandler())
    }

    companion object {
        const val MODID = "pterodactylcommandsmod"
        private val LOGGER: Logger = LogUtils.getLogger()
    }
}
