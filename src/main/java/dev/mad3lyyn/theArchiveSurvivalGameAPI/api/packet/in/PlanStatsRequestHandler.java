package dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.in;

import com.djrapitops.plan.query.QueryService;
import com.djrapitops.plan.query.CommonQueries;
import com.google.common.io.ByteArrayDataInput;
import dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.BotPacketManager;
import dev.mad3lyyn.theArchiveSurvivalGameAPI.api.packet.out.PlanStatsResponsePacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlanStatsRequestHandler implements BotPacketManager.PacketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlanStatsRequestHandler.class);

    private final BotPacketManager packetManager;

    public PlanStatsRequestHandler(BotPacketManager packetManager) {
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

        QueryService qs = QueryService.getInstance();
        if (qs == null) return;
        CommonQueries queries = qs.getCommonQueries();
        
        UUID serverUuid = qs.getServerUUID().orElse(null);

        CompletableFuture.supplyAsync(() -> {
            try {
                String name = queries.fetchNameOf(targetUuid).orElse("Unknown");
                
                long now = System.currentTimeMillis();
                long playtime = 0;
                try {
                    playtime = queries.fetchPlaytime(targetUuid, serverUuid, 0L, now);
                } catch (Exception ignored) {}
                
                long lastSeen = 0;
                try {
                    lastSeen = queries.fetchLastSeen(targetUuid, serverUuid);
                } catch (Exception ignored) {}
                
                long currentSession = 0;
                try {
                    currentSession = queries.fetchCurrentSessionPlaytime(targetUuid);
                } catch (Exception ignored) {}
                
                double activityIndex = 0;
                try {
                    activityIndex = queries.fetchActivityIndexOf(targetUuid, now);
                } catch (Exception ignored) {}
                
                return new PlanStatsResponsePacket(targetUuid, name, playtime + currentSession, lastSeen, (int) (activityIndex * 100), 0, 0);
            } catch (Exception e) {
                LOGGER.error("Failed to get Plan stats for {} :c", targetUuid, e);
                return null;
            }
        }).thenAccept(responsePacket -> {
            if (responsePacket != null && sender.isOnline()) {
                packetManager.sendPacket(sender, responsePacket);
            }
        });
    }
}
