package be.woutzah.litebansbridge.litebans.listener;

import be.woutzah.litebansbridge.LiteBansBridge;
import be.woutzah.litebansbridge.litebans.domain.ModerationType;
import be.woutzah.litebansbridge.discord.DiscordManager;
import be.woutzah.litebansbridge.litebans.domain.Notification;
import litebans.api.Entry;
import litebans.api.Events;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static java.util.Optional.ofNullable;

public class LiteBansListener {

    private static final String CONSOLE = "console";
    private static final String NOT_FOUND = "Not found";

    private final LiteBansBridge plugin;
    private final DiscordManager discordManager;

    public LiteBansListener(LiteBansBridge plugin) {
        this.plugin = plugin;
        this.discordManager = plugin.getDiscordManager();
    }


    //embeds voorzien i.pv messages voor
    // separaten van funct nodig met of zonder linked account
    public void registerEvent() {
        Events.get().register(new Events.Listener() {
            @Override
            public void entryAdded(Entry entry) {
                boolean userIslinked = plugin.getDiscordManager().userIsLinked(plugin.getDiscordManager().getIdByUUID(UUID.fromString(ofNullable(entry.getUuid()).orElse(""))));


                Notification notification;
                String discordId = plugin.getDiscordManager().getIdByUUID(UUID.fromString(entry.getUuid()));
                User discordUser = plugin.getDiscordManager().getUserById(discordId);
                String playerName = getPlayerName(entry.getUuid());
                String issuerName = getPlayerName(entry.getExecutorUUID());

                switch (entry.getType()) {
                    case "ban":
                        notification = new Notification(entry.getReason(), entry.getDateStart(), entry.getDateEnd(), entry.getExecutorUUID(), ModerationType.BANNED);
                        plugin.getDiscordManager().sendPrivateDiscordMessage(discordUser, notification);
                        if (entry.getDateEnd() == -1) {
                            plugin.getDiscordManager().sendMessageToStaffLog(
                                    plugin.getMessageManager().getStaffWarnPermaBanned(playerName, issuerName, entry.getReason()));
                            plugin.getDiscordManager().getGuild().kick(Objects.requireNonNull(plugin.getDiscordManager().getGuild().getMember(discordUser))).queue();
                        } else {
                            plugin.getDiscordManager().sendMessageToStaffLog(
                                    plugin.getMessageManager().getStaffWarnBanned(playerName, issuerName, entry.getReason(), entry.getDateStart(), entry.getDateEnd(), new Date(entry.getDateEnd()).toString()));
                        }
                        break;
                    case "mute":
                        notification = new Notification(entry.getReason(), entry.getDateStart(), entry.getDateEnd(), entry.getExecutorUUID(), ModerationType.MUTED);
                        plugin.getDiscordManager().sendPrivateDiscordMessage(discordUser, notification);
                        if (entry.getDateEnd() == -1) {
                            plugin.getDiscordManager().sendMessageToStaffLog(
                                    plugin.getMessageManager().getStaffWarnPermaMuted(playerName, issuerName, entry.getReason()));
                        } else {
                            plugin.getDiscordManager().sendMessageToStaffLog(
                                    plugin.getMessageManager().getStaffWarnMuted(playerName, issuerName, entry.getReason(), entry.getDateStart(), entry.getDateEnd(), new Date(entry.getDateEnd()).toString()));
                        }
                        break;
                    case "warn":
                        notification = new Notification(entry.getReason(), entry.getDateStart(), entry.getDateEnd(), entry.getExecutorUUID(), ModerationType.WARNED);
                        plugin.getDiscordManager().sendPrivateDiscordMessage(discordUser, notification);
                        plugin.getDiscordManager().sendMessageToStaffLog(
                                plugin.getMessageManager().getStaffWarnWarned(playerName, issuerName, entry.getReason()));
                        break;
                    case "kick":
                        notification = new Notification(entry.getReason(), entry.getDateStart(), entry.getDateEnd(), entry.getExecutorUUID(), ModerationType.KICKED);
                        plugin.getDiscordManager().sendPrivateDiscordMessage(discordUser, notification);
                        plugin.getDiscordManager().sendMessageToStaffLog(
                                plugin.getMessageManager().getStaffWarnKicked(playerName, issuerName, entry.getReason()));
                        break;
                }
            }
        });
    }

    public String getPlayerName(String uuid) {
        if (CONSOLE.equalsIgnoreCase(uuid)) {
            return CONSOLE;
        }

        Player player = Bukkit.getPlayer(UUID.fromString(uuid));

        return player != null ? player.getName() : Bukkit.getOfflinePlayer((UUID.fromString(uuid))).getName();
    }
}
