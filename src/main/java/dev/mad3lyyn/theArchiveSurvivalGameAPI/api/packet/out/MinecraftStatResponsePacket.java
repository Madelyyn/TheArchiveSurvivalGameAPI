package dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.out;

import com.google.common.io.ByteArrayDataOutput;
import dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.OutgoingPacket;

import java.util.UUID;

public record MinecraftStatResponsePacket(UUID playerUuid, String statistic, String subStatistic, int value) implements OutgoingPacket {

    public MinecraftStatResponsePacket {
        subStatistic = subStatistic != null ? subStatistic : "";
    }

    @Override
    public String getName() {
        return "minecraft_stat_response";
    }

    @Override
    public void write(ByteArrayDataOutput out) {
        out.writeUTF(playerUuid.toString());
        out.writeUTF(statistic);
        out.writeUTF(subStatistic);
        out.writeInt(value);
    }
}
