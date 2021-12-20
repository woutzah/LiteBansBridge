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

    //private final DiscordManager discordManager;
    //private final LiteBansManager liteBansManager;
    //private final MessageManager messageManager;
    private final LiteBansBridge plugin;
    private final boolean discordPunishEnabled;
    public DiscordListener(LiteBansBridge plugin) {
        this.plugin = plugin;
        this.discordPunishEnabled = plugin.getDiscordPunishEnabled();

        // Probably can delete later:
        //this.discordManager = plugin.discordManager;
        //this.liteBansManager = plugin.liteBansManager;
        //this.messageManager = plugin.messageManager;
    }


    @Subscribe
    public void afterMessageSend(DiscordGuildMessagePostProcessEvent event) {
        if (discordPunishEnabled) {
            User user = event.getAuthor();
            if (this.plugin.getDiscordManager().userIsLinked(user.getId())) {
                UUID uuidUser = this.plugin.getDiscordManager().getUUIDById(user.getId());
                if (this.plugin.getLiteBansManager().getIsPlayerBanned(uuidUser)) {
                    event.getChannel().deleteMessageById(event.getMessage().getId()).queue();
                    this.plugin.getDiscordManager().sendPrivateDiscordMessage(user, this.plugin.getMessageManager().getDiscordWarnBanned());
                    event.setCancelled(true);
                } else if (this.plugin.getLiteBansManager().getIsPlayerMuted(uuidUser)) {
                    event.getChannel().deleteMessageById(event.getMessage().getId()).queue();
                    this.plugin.getDiscordManager().sendPrivateDiscordMessage(user, this.plugin.getMessageManager().getDiscordWarnMuted());
                    event.setCancelled(true);
                }
            }
        }
    }
}
