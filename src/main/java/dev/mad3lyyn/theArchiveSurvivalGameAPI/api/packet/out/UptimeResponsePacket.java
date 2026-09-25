package dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.out;

import com.google.common.io.ByteArrayDataOutput;
import dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.OutgoingPacket;

public record UptimeResponsePacket(long uptimeMs) implements OutgoingPacket {

    @Override
    public String getName() {
        return "uptime_response";
    }

    @Override
    public void write(ByteArrayDataOutput out) {
        out.writeLong(uptimeMs);
    }
}
