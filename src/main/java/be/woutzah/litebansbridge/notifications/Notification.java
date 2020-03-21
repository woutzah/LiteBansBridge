package be.woutzah.litebansbridge.notifications;

import be.woutzah.litebansbridge.LiteBansBridge;
import be.woutzah.litebansbridge.enums.ModerationType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

public class Notification {

    public static LiteBansBridge plugin;
    private String reason;
    private Date untilReleased;
    private Player issuer;
    private ModerationType moderationType;

    public Notification(String reason, Long untilReleased, String issuer, ModerationType moderationType) {
        this.reason = reason;
        this.untilReleased = untilReleased == -1? null : new Date(untilReleased);
        this.issuer = issuer.equalsIgnoreCase("console") ? null : Bukkit.getPlayer(UUID.fromString(issuer));
        this.moderationType = moderationType;
    }

    @Override
    public String toString() {
        switch (moderationType) {
            case banned:
                if (untilReleased == null) {
                    return plugin.getMessageManager().getLitebansWarnPermaBanned(checkIfConsole(issuer), reason);
                } else {
                    return plugin.getMessageManager().getLitebansWarnBanned(checkIfConsole(issuer), reason, untilReleased.toString());
                }
            case muted:
                if (untilReleased == null) {
                    return plugin.getMessageManager().getLitebansWarnPermaMuted(checkIfConsole(issuer), reason);
                }else{
                    return plugin.getMessageManager().getLitebansWarnMuted(checkIfConsole(issuer), reason, untilReleased.toString());
                }
            case warned:
                return plugin.getMessageManager().getLitebansWarnWarned(checkIfConsole(issuer), reason);
            case kicked:
                return plugin.getMessageManager().getLitebansWarnKicked(checkIfConsole(issuer), reason);
        }
        return "";
    }

    private String checkIfConsole(Player issuer) {
        return issuer == null ? "console" : issuer.getName();
    }
}
