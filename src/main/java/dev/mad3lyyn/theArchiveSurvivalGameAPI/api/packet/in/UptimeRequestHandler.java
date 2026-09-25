package dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.in;

import com.google.common.io.ByteArrayDataInput;
import dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.BotPacketManager;
import dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.out.UptimeResponsePacket;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.management.ManagementFactory;

public class UptimeRequestHandler implements BotPacketManager.PacketHandler {

    private final BotPacketManager packetManager;

    public UptimeRequestHandler(BotPacketManager packetManager) {
        this.packetManager = packetManager;
    }

    @Override
    public void handle(@NotNull Player sender, @NotNull ByteArrayDataInput in) {
        long uptime = ManagementFactory.getRuntimeMXBean().getUptime();
        packetManager.sendPacket(sender, new UptimeResponsePacket(uptime));
    }
}
