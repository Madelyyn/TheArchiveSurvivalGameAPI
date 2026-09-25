package dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.in;

import com.google.common.io.ByteArrayDataInput;
import dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.BotPacketManager;
import dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.out.InventoryResponsePacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InventoryRequestHandler implements BotPacketManager.PacketHandler {

    private final BotPacketManager packetManager;

    public InventoryRequestHandler(BotPacketManager packetManager) {
        this.packetManager = packetManager;
    }

    @Override
    public void handle(@NotNull Player sender, @NotNull ByteArrayDataInput in) {
        String targetUuidStr = in.readUTF();
        UUID targetUuid;
        try {
            targetUuid = UUID.fromString(targetUuidStr);
        } catch (IllegalArgumentException e) {
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(targetUuid);
        List<InventoryResponsePacket.InventoryItem> items = collectItems(targetPlayer);

        InventoryResponsePacket response = new InventoryResponsePacket(targetUuid, items);
        packetManager.sendPacket(sender, response);
    }

    private List<InventoryResponsePacket.InventoryItem> collectItems(Player targetPlayer) {
        List<InventoryResponsePacket.InventoryItem> items = new ArrayList<>();

        if (targetPlayer == null || !targetPlayer.isOnline()) {
            return items;
        }

        ItemStack[] contents = targetPlayer.getInventory().getContents();
        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item != null && !item.getType().isAir()) {
                items.add(new InventoryResponsePacket.InventoryItem(i, item.getType().name(), item.getAmount()));
            }
        }
        return items;
    }
}
