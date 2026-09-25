# Game API for The Archive Survival (survival.thearchive.world)

Packet List:

| Packet                    | Direction    | Description                                                               |
|---------------------------|--------------|---------------------------------------------------------------------------|
| `playerlist_request`      | Bot → Server | Request the list of online players                                        |
| `plan_stats_request`      | Bot → Server | Request Plan stats for a player (UUID)                                    |
| `minecraft_stat_request`  | Bot → Server | Request a vanilla statistic for a player (UUID, statistic, sub-statistic) |
| `inventory_request`       | Bot → Server | Request the inventory contents of a player (UUID)                         |
| `uptime_request`          | Bot → Server | Request server uptime                                                     |
| `playerlist_response`     | Server → Bot | List of online players (UUID, name, ping)                                 |
| `plan_stats_response`     | Server → Bot | Plan stats (name, playtime, last seen, activity index)                    |
| `minecraft_stat_response` | Server → Bot | Vanilla statistic value for a player                                      |
| `inventory_response`      | Server → Bot | Inventory contents (slot, material, amount)                               |
| `uptime_response`         | Server → Bot | Server uptime in milliseconds                                             |

All packets are sent over the plugin messaging channel `archive:bot_api`.


Example Mineflayer API:
https://github.com/Madelyyn/TheArchiveSurvivalGameAPI-Mineflayer-Plugin
