package be.woutzah.litebansbridge.notifications;

import be.woutzah.litebansbridge.LiteBansBridge;
import be.woutzah.litebansbridge.enums.ModerationType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

public class Notification {

    public static LiteBansBridge plugin;
    private final String reason;
    private final long dateStart;
    private final long dateEnd;
    private final Date untilReleased;
    private final Player issuer;
    private final ModerationType moderationType;

    public Notification(String reason, long dateStart, long dateEnd, String issuer, ModerationType moderationType) {
        this.reason = reason;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.untilReleased = dateEnd == -1 ? null : new Date(dateEnd);
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
                    return plugin.getMessageManager().getLitebansWarnBanned(checkIfConsole(issuer), reason, dateStart, dateEnd, untilReleased.toString());
                }
            case muted:
                if (untilReleased == null) {
                    return plugin.getMessageManager().getLitebansWarnPermaMuted(checkIfConsole(issuer), reason);
                }else{
                    return plugin.getMessageManager().getLitebansWarnMuted(checkIfConsole(issuer), reason, dateStart, dateEnd, untilReleased.toString());
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
