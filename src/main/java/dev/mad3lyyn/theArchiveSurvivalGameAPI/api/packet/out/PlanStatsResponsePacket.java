package dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.out;

import com.google.common.io.ByteArrayDataOutput;
import dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.OutgoingPacket;

import java.util.UUID;

public record PlanStatsResponsePacket(UUID playerUuid, String playerName, long playtime, long registerDate, int kills, int deaths, int mobKills) implements OutgoingPacket {

    public PlanStatsResponsePacket {
        playerName = playerName != null ? playerName : "Unknown";
    }

    @Override
    public String getName() {
        return "plan_stats_response";
    }

    @Override
    public void write(ByteArrayDataOutput out) {
        out.writeUTF(playerUuid.toString());
        out.writeUTF(playerName);
        out.writeLong(playtime);
        out.writeLong(registerDate);
        out.writeInt(kills);
        out.writeInt(deaths);
        out.writeInt(mobKills);
    }
}
