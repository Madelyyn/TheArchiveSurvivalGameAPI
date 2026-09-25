package dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.in;

import com.google.common.io.ByteArrayDataInput;
import dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.BotPacketManager;
import dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.out.MinecraftStatResponsePacket;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MinecraftStatRequestHandler implements BotPacketManager.PacketHandler {

    private final BotPacketManager packetManager;

    public MinecraftStatRequestHandler(BotPacketManager packetManager) {
        this.packetManager = packetManager;
    }

    @Override
    public void handle(@NotNull Player sender, @NotNull ByteArrayDataInput in) {
        String targetUuidStr = in.readUTF();
        String statName = in.readUTF();
        String subStatName = in.readUTF();

        UUID targetUuid;
        try {
            targetUuid = UUID.fromString(targetUuidStr);
        } catch (IllegalArgumentException e) {
            return;
        }

        CompletableFuture.supplyAsync(() -> {
            try {
                OfflinePlayer op = Bukkit.getOfflinePlayer(targetUuid);
                if (!op.hasPlayedBefore() && !op.isOnline()) {
                    return new MinecraftStatResponsePacket(targetUuid, statName, subStatName, 0);
                }

                Statistic stat = Statistic.valueOf(statName.toUpperCase());
                int value = 0;

                if (stat.getType() == Statistic.Type.UNTYPED) {
                    value = op.getStatistic(stat);
                } else if (stat.getType() == Statistic.Type.BLOCK || stat.getType() == Statistic.Type.ITEM) {
                    Material mat = Material.matchMaterial(subStatName);
                    if (mat != null) {
                        value = op.getStatistic(stat, mat);
                    }
                } else if (stat.getType() == Statistic.Type.ENTITY) {
                    EntityType ent = EntityType.valueOf(subStatName.toUpperCase());
                    value = op.getStatistic(stat, ent);
                }

                return new MinecraftStatResponsePacket(targetUuid, statName, subStatName, value);
            } catch (Exception e) {
                return new MinecraftStatResponsePacket(targetUuid, statName, subStatName, 0);
            }
        }).thenAccept(responsePacket -> {
            if (responsePacket != null && sender.isOnline()) {
                packetManager.sendPacket(sender, responsePacket);
            }
        });
    }
}
