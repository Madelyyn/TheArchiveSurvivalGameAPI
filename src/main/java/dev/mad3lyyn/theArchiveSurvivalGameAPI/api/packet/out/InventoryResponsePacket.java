package dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.out;

import com.google.common.io.ByteArrayDataOutput;
import dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.OutgoingPacket;

import java.util.List;
import java.util.UUID;

public record InventoryResponsePacket(UUID playerUuid, List<InventoryItem> items) implements OutgoingPacket {

    @Override
    public String getName() {
        return "inventory_response";
    }

    @Override
    public void write(ByteArrayDataOutput out) {
        out.writeUTF(playerUuid.toString());
        out.writeInt(items.size());
        for (InventoryItem item : items) {
            out.writeInt(item.slot());
            out.writeUTF(item.material());
            out.writeInt(item.amount());
        }
    }

    public record InventoryItem(int slot, String material, int amount) {
    }
}
