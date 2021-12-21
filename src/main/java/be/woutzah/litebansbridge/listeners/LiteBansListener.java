package be.woutzah.litebansbridge.listeners;

import be.woutzah.litebansbridge.LiteBansBridge;
import be.woutzah.litebansbridge.enums.ModerationType;
import be.woutzah.litebansbridge.notifications.Notification;
import github.scarsz.discordsrv.dependencies.jda.api.entities.User;
import litebans.api.Entry;
import litebans.api.Events;
import org.bukkit.Bukkit;

import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class LiteBansListener {

    private final LiteBansBridge plugin;

    public LiteBansListener(LiteBansBridge plugin) {
        this.plugin = plugin;
    }

    public void registerEvent() {
        Events.get().register(new Events.Listener() {
            @Override
            public void entryAdded(Entry entry) {
                if (plugin.getDiscordManager().userIsLinked(plugin.getDiscordManager().getIdByUUID(UUID.fromString(Objects.requireNonNull(entry.getUuid()))))) {
                    Notification notification;
                    String discordId = plugin.getDiscordManager().getIdByUUID(UUID.fromString(entry.getUuid()));

                    // If the banned player has not yet linked their account, we do nothing.
                    if (discordId == null) return;

                    User user = plugin.getDiscordManager().getUserById(discordId);
                    String playerName = getPlayerName(entry.getUuid());
                    String issuerName = getPlayerName(Objects.requireNonNull(entry.getExecutorUUID()));
                    Timer kicktimer = new Timer(true);

                        switch (entry.getType()) {
                            case "ban":
                                notification = new Notification(entry.getReason(), entry.getDateStart(), entry.getDateEnd(), entry.getExecutorUUID(), ModerationType.banned);
                                plugin.getDiscordManager().sendPrivateDiscordMessage(user, notification);
                                if (entry.getDateEnd() == -1) {
                                    plugin.getDiscordManager().sendMessageToStaffLog(
                                            plugin.getMessageManager().getStaffWarnPermaBanned(playerName, issuerName, entry.getReason()));

                                    // Creating a 1-second timer to delay the kicking,
                                    // this is to avoid the user from being kicked before they can be messaged.
                                    // As discord's api can be delayed sometimes.
                                    kicktimer.schedule(new TimerTask()
                                    {
                                        @Override
                                        public void run()
                                        {

                                            // Do nothing if the user can't be found.
                                            if (user == null) return;

                                            plugin.getDiscordManager().getGuild().kick(Objects.requireNonNull(plugin.getDiscordManager().getGuild().getMember(user))).queue();
                                        }
                                    }, 1000);

                                } else {
                                    plugin.getDiscordManager().sendMessageToStaffLog(
                                            plugin.getMessageManager().getStaffWarnBanned(playerName, issuerName, entry.getReason(), entry.getDateStart(), entry.getDateEnd(), new Date(entry.getDateEnd()).toString()));
                                }
                                break;
                            case "mute":
                                notification = new Notification(entry.getReason(), entry.getDateStart(), entry.getDateEnd(), entry.getExecutorUUID(), ModerationType.muted);
                                plugin.getDiscordManager().sendPrivateDiscordMessage(user, notification);
                                if (entry.getDateEnd() == -1) {
                                    plugin.getDiscordManager().sendMessageToStaffLog(
                                            plugin.getMessageManager().getStaffWarnPermaMuted(playerName, issuerName, entry.getReason()));
                                }else {
                                    plugin.getDiscordManager().sendMessageToStaffLog(
                                            plugin.getMessageManager().getStaffWarnMuted(playerName, issuerName, entry.getReason(), entry.getDateStart(), entry.getDateEnd(), new Date(entry.getDateEnd()).toString()));
                                }
                                break;
                            case "warn":
                                notification = new Notification(entry.getReason(), entry.getDateStart(), entry.getDateEnd(), entry.getExecutorUUID(), ModerationType.warned);
                                plugin.getDiscordManager().sendPrivateDiscordMessage(user, notification);
                                plugin.getDiscordManager().sendMessageToStaffLog(
                                        plugin.getMessageManager().getStaffWarnWarned(playerName, issuerName, entry.getReason()));
                                break;
                            case "kick":
                                notification = new Notification(entry.getReason(), entry.getDateStart(), entry.getDateEnd(), entry.getExecutorUUID(), ModerationType.kicked);
                                plugin.getDiscordManager().sendPrivateDiscordMessage(user, notification);
                                plugin.getDiscordManager().sendMessageToStaffLog(
                                        plugin.getMessageManager().getStaffWarnKicked(playerName, issuerName, entry.getReason()));
                                break;
                        }
                }
            }
        });
    }

    public String getPlayerName(String uuid) {
        if(uuid.equalsIgnoreCase("console")){
            return "console";
        }
        return Bukkit.getPlayer(UUID.fromString(uuid)) != null ?
                Objects.requireNonNull(Bukkit.getPlayer(UUID.fromString(uuid))).getName()
                : Bukkit.getOfflinePlayer((UUID.fromString(uuid))).getName();
    }

}
