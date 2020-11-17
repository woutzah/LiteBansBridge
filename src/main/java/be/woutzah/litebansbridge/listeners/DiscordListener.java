package be.woutzah.litebansbridge.listeners;

import be.woutzah.litebansbridge.LiteBansBridge;
import be.woutzah.litebansbridge.managers.DiscordManager;
import be.woutzah.litebansbridge.managers.LiteBansManager;
import be.woutzah.litebansbridge.managers.MessageManager;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DiscordGuildMessagePostProcessEvent;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Message;
import github.scarsz.discordsrv.dependencies.jda.api.entities.User;
import org.bukkit.event.Listener;

import java.util.UUID;

public class DiscordListener implements Listener {

    private final DiscordManager discordManager;
    private final LiteBansManager liteBansManager;
    private final MessageManager messageManager;
    private final boolean discordPunishEnabled;
    public DiscordListener(LiteBansBridge plugin) {
        this.discordManager = plugin.getDiscordManager();
        this.liteBansManager = plugin.getLiteBansManager();
        this.messageManager = plugin.getMessageManager();
        this.discordPunishEnabled = plugin.getDiscordPunishEnabled();
    }


    @Subscribe
    public void afterMessageSend(DiscordGuildMessagePostProcessEvent event) {
        if (discordPunishEnabled) {
            User user = event.getAuthor();
            if (discordManager.userIsLinked(user.getId())) {
                UUID uuidUser = discordManager.getUUIDById(user.getId());
                if (liteBansManager.getIsPlayerBanned(uuidUser)) {
                    event.getChannel().deleteMessageById(event.getMessage().getId()).queue();
                    discordManager.sendPrivateDiscordMessage(user, messageManager.getDiscordWarnBanned());
                    event.setCancelled(true);
                } else if (liteBansManager.getIsPlayerMuted(uuidUser)) {
                    event.getChannel().deleteMessageById(event.getMessage().getId()).queue();
                    discordManager.sendPrivateDiscordMessage(user, messageManager.getDiscordWarnMuted());
                    event.setCancelled(true);
                }
            }
        }
    }
}
