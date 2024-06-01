package com.dorpeled.pterodactylcommandsmod;

import com.dorpeled.pterodactylcommandsmod.config.PterodactylCommandsConfig;
import com.dorpeled.pterodactylcommandsmod.events.CommandEventHandler;
import com.dorpeled.pterodactylcommandsmod.events.ConfigEventHandler;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(PterodactylCommandsMod.MODID)
public class PterodactylCommandsMod {
    public static final String MODID = "pterodactylcommandsmod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public PterodactylCommandsMod() {
        LOGGER.info("Starting Pterodactyl Commands");

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, PterodactylCommandsConfig.config);

        MinecraftForge.EVENT_BUS.register(CommandEventHandler.class);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(new ConfigEventHandler());
    }
}
