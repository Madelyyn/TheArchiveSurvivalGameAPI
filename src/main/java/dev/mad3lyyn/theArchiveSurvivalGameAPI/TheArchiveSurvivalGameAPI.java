package dev.mad3lyyn.theArchiveSurvivalGameAPI;

import dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.BotPacketManager;
import dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.in.InventoryRequestHandler;
import dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.in.MinecraftStatRequestHandler;
import dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.in.PlanStatsRequestHandler;
import dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.in.PlayerListRequestHandler;
import dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.in.UptimeRequestHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class TheArchiveSurvivalGameAPI extends JavaPlugin {

    private BotPacketManager botPacketManager;

    @Override
    public void onEnable() {
        botPacketManager = new BotPacketManager(this);
        botPacketManager.registerChannels();

        botPacketManager.registerHandler("playerlist_request", new PlayerListRequestHandler(botPacketManager));
        botPacketManager.registerHandler("plan_stats_request", new PlanStatsRequestHandler(botPacketManager));
        botPacketManager.registerHandler("minecraft_stat_request", new MinecraftStatRequestHandler(botPacketManager));
        botPacketManager.registerHandler("inventory_request", new InventoryRequestHandler(botPacketManager));
        botPacketManager.registerHandler("uptime_request", new UptimeRequestHandler(botPacketManager));
        
        getLogger().info("The Archive Survival API has been initialized!!");
    }

    @Override
    public void onDisable() {
        if (botPacketManager != null) {
            botPacketManager.unregisterChannels();
        }
    }
}
