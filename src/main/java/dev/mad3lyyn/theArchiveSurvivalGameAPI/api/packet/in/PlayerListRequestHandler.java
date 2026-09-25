package dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.in;

import com.google.common.io.ByteArrayDataInput;
import dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.BotPacketManager;
import dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.out.PlayerListResponsePacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerListRequestHandler implements BotPacketManager.PacketHandler {

    private final BotPacketManager packetManager;

    public PlayerListRequestHandler(BotPacketManager packetManager) {
        this.packetManager = packetManager;
    }

    @Override
    public void handle(@NotNull Player sender, @NotNull ByteArrayDataInput in) {
        PlayerListResponsePacket response = new PlayerListResponsePacket(Bukkit.getOnlinePlayers());
        packetManager.sendPacket(sender, response);
    }
}
