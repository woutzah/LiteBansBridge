package be.woutzah.litebansbridge.discord;

import be.woutzah.litebansbridge.LiteBansBridge;
import github.scarsz.discordsrv.DiscordSRV;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

import java.util.UUID;

import static java.util.Optional.ofNullable;

public class DiscordManager {

    private final String stafflogChannelId;
    private final boolean staffLoggingEnabled;

    public DiscordManager(LiteBansBridge plugin) {
        this.stafflogChannelId = plugin.getConfig().getString("stafflog-textchannel-id");
        this.staffLoggingEnabled = plugin.getConfig().getBoolean("enable-stafflog");
    }

    public boolean userIsLinked(String id) {
        return DiscordSRV.getPlugin().getAccountLinkManager().getUuid(id) != null;
    }

    public UUID getUUIDById(String id){
        return DiscordSRV.getPlugin().getAccountLinkManager().getUuid(id);
    }

    public String getIdByUUID(UUID uuid){
        return DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(uuid);
    }

    public User getUserById(String id){
        return DiscordSRV.getPlugin().getJda().getUserById(id);
    }

    public void sendPrivateDiscordMessage(User user, String message){
        user.openPrivateChannel().queue(channel -> channel.sendMessage(message).queue());
    }

    public void sendMessageToStaffLog(String message){
        if (!staffLoggingEnabled){
            return;
        }

       ofNullable(getGuild().getTextChannelById(stafflogChannelId))
               .ifPresent(channel -> channel.sendMessage(message).queue());
    }

    public Guild getGuild() {
        return DiscordSRV.getPlugin().getJda().getGuilds().stream().findFirst().orElse(null);
    }
}