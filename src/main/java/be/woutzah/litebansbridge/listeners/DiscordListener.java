package be.woutzah.litebansbridge.listeners;

import be.woutzah.litebansbridge.LiteBansBridge;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DiscordGuildMessagePostProcessEvent;
import github.scarsz.discordsrv.dependencies.jda.api.entities.User;
import org.bukkit.event.Listener;

import java.util.UUID;

public class DiscordListener implements Listener {

    private LiteBansBridge plugin;

    public DiscordListener(LiteBansBridge plugin) {
        this.plugin = plugin;
    }


    @Subscribe
    public void afterMessageSend(DiscordGuildMessagePostProcessEvent event) {
        User user = event.getAuthor();
        if (plugin.getDiscordManager().userIsLinked(user.getId())) {
            UUID uuidUser = plugin.getDiscordManager().getUUIDById(user.getId());
            if (plugin.getLiteBansManager().getIsPlayerBanned(uuidUser)) {
                event.getChannel().deleteMessageById(event.getMessage().getId()).queue();
                plugin.getDiscordManager().sendPrivateDiscordMessage(user, plugin.getMessageManager().getDiscordWarnBanned());
                event.setCancelled(true);
            } else if (plugin.getLiteBansManager().getIsPlayerMuted(uuidUser)) {
                event.getChannel().deleteMessageById(event.getMessage().getId()).queue();
                plugin.getDiscordManager().sendPrivateDiscordMessage(user, plugin.getMessageManager().getDiscordWarnMuted());
                event.setCancelled(true);
            }
        }
    }
}
