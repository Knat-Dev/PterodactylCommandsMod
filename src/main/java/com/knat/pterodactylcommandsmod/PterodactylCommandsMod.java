package com.knat.pterodactylcommandsmod;

import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(PterodactylCommandsMod.MODID)
public class PterodactylCommandsMod {
    public static final String MODID = "pterodactylcommandsmod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public PterodactylCommandsMod() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.config);
        MinecraftForge.EVENT_BUS.register(this);
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Server starting event triggered");
        LOGGER.info("Config setup completed. API Key: {}, Base URL: {}, Server ID: {}", Config.API_KEY.get(), Config.BASE_URL.get(), Config.SERVER_ID.get());

    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!Config.isConfigValid()) {
            String message = "Configuration is not set or invalid in the configuration file. Can not communicate with the Pterodactyl API.";
            LOGGER.error(message);
            event.getEntity().displayClientMessage(Component.literal(message), false);
        }
    }
}
