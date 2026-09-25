package dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.out;

import com.google.common.io.ByteArrayDataOutput;
import dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.OutgoingPacket;
import org.bukkit.entity.Player;

import java.util.Collection;

public record PlayerListResponsePacket(Collection<? extends Player> players) implements OutgoingPacket {

    @Override
    public String getName() {
        return "playerlist_response";
    }

    @Override
    public void write(ByteArrayDataOutput out) {
        out.writeInt(players.size());
        for (Player player : players) {
            out.writeUTF(player.getUniqueId().toString());
            out.writeUTF(player.getName());
            out.writeInt(player.getPing());
        }
    }
}
