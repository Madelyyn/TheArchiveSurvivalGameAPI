package dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet;

import com.google.common.io.ByteArrayDataOutput;

public interface OutgoingPacket {

    String getName();

    void write(ByteArrayDataOutput out);
}
