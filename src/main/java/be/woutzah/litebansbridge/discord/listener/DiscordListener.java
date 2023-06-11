package be.woutzah.litebansbridge.discord.listener;

import be.woutzah.litebansbridge.LiteBansBridge;
import be.woutzah.litebansbridge.discord.DiscordManager;
import be.woutzah.litebansbridge.util.LiteBansUtil;
import be.woutzah.litebansbridge.message.MessageManager;
import github.scarsz.discordsrv.api.Subscribe;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bukkit.event.Listener;

import java.util.UUID;

public class DiscordListener implements Listener {

    private final DiscordManager discordManager;
    private final MessageManager messageManager;
    private final boolean discordPunishEnabled;

    public DiscordListener(LiteBansBridge plugin) {
        this.discordManager = plugin.getDiscordManager();
        this.messageManager = plugin.getMessageManager();
        this.discordPunishEnabled = plugin.getConfig().getBoolean("enable-discord-punishments");
    }


    @Subscribe
    public void afterMessageSend(MessageReceivedEvent event) {
        if (!discordPunishEnabled) {
            return;
        }

        User user = event.getAuthor();
        if (discordManager.userIsLinked(user.getId())) {
            UUID uuidUser = discordManager.getUUIDById(user.getId());

            if (LiteBansUtil.getIsPlayerBanned(uuidUser)) {
                event.getChannel().deleteMessageById(event.getMessageId()).queue();
                discordManager.sendPrivateDiscordMessage(user, messageManager.getDiscordWarnBanned());
            } else if (LiteBansUtil.getIsPlayerMuted(uuidUser)) {
                event.getChannel().deleteMessageById(event.getMessage().getId()).queue();
                discordManager.sendPrivateDiscordMessage(user, messageManager.getDiscordWarnMuted());
            }
        }
    }
}
