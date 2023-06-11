package be.woutzah.litebansbridge.litebans.domain;

import be.woutzah.litebansbridge.litebans.domain.ModerationType;
import be.woutzah.litebansbridge.util.DateTime;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

public class Notification {

    private final String reason;
    private final DateTime dateStart;
    private final DateTime dateEnd;
    private final DateTime untilReleased;
    private final Player issuer;
    private final ModerationType moderationType;

    public Notification(String reason, DateTime dateStart, DateTime dateEnd, String issuer, ModerationType moderationType) {
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
            case BANNED:
                if (untilReleased == null) {
                    return plugin.getMessageManager().getLitebansWarnPermaBanned(getIssuerName(issuer), reason);
                } else {
                    return plugin.getMessageManager().getLitebansWarnBanned(getIssuerName(issuer), reason, dateStart, dateEnd, untilReleased.toString());
                }
            case MUTED:
                if (untilReleased == null) {
                    return plugin.getMessageManager().getLitebansWarnPermaMuted(getIssuerName(issuer), reason);
                }else{
                    return plugin.getMessageManager().getLitebansWarnMuted(getIssuerName(issuer), reason, dateStart, dateEnd, untilReleased.toString());
                }
            case WARNED:
                return plugin.getMessageManager().getLitebansWarnWarned(getIssuerName(issuer), reason);
            case KICKED:
                return plugin.getMessageManager().getLitebansWarnKicked(getIssuerName(issuer), reason);
            default:
                return "";
        }
    }

    private String getIssuerName(Player issuer) {
        return issuer == null ? "console" : issuer.getName();
    }
}
