package dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.HashMap;
import java.util.Map;

public class BotPacketManager implements PluginMessageListener {

    public static final String CHANNEL_NAME = "archive:bot_api";
    
    private final Plugin plugin;
    private final Map<String, PacketHandler> packetHandlers = new HashMap<>();

    public BotPacketManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void registerChannels() {
        Bukkit.getMessenger().registerIncomingPluginChannel(plugin, CHANNEL_NAME, this);
        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, CHANNEL_NAME);
    }

    public void unregisterChannels() {
        Bukkit.getMessenger().unregisterIncomingPluginChannel(plugin, CHANNEL_NAME, this);
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(plugin, CHANNEL_NAME);
    }

    public void registerHandler(String packetName, PacketHandler handler) {
        packetHandlers.put(packetName, handler);
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte @NotNull [] message) {
        if (!channel.equals(CHANNEL_NAME)) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        try {
            String packetName = in.readUTF();
            PacketHandler handler = packetHandlers.get(packetName);
            
            if (handler != null) {
                handler.handle(player, in);
            } else {
                plugin.getLogger().warning("Received unknown bot packet: " + packetName + " from " + player.getName());
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to decode bot packet from " + player.getName() + ": " + e.getMessage());
        }
    }

    public void sendPacket(Player player, OutgoingPacket packet) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(packet.getName());
        packet.write(out);
        player.sendPluginMessage(plugin, CHANNEL_NAME, out.toByteArray());
    }

    public interface PacketHandler {
        void handle(Player sender, ByteArrayDataInput in);
    }
}
