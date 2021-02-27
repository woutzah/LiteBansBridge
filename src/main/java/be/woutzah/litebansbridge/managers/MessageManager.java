package be.woutzah.litebansbridge.managers;

import be.woutzah.litebansbridge.LiteBansBridge;
import be.woutzah.litebansbridge.util.DateTime;
import be.woutzah.litebansbridge.util.TimeUtil;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class MessageManager {

    private LiteBansBridge plugin;
    private final List<String> discordWarnBanned;
    private final List<String> discordWarnMuted;
    private final List<String> litebansWarnPermaBanned;
    private final List<String> litebansWarnBanned;
    private final List<String> litebansWarnPermaMuted;
    private final List<String> litebansWarnMuted;
    private final List<String> litebansWarnWarned;
    private final List<String> litebansWarnKicked;
    private final List<String> staffWarnPermaBanned;
    private final List<String> staffWarnBanned;
    private final List<String> staffWarnPermaMuted;
    private final List<String> staffWarnMuted;
    private final List<String> staffWarnWarned;
    private final List<String> staffWarnKicked;

    public MessageManager(LiteBansBridge plugin) {
        this.plugin = plugin;
        FileConfiguration config = plugin.getConfig();
        this.discordWarnBanned = config.getStringList("discord-chat-warnings.banned");
        this.discordWarnMuted = config.getStringList("discord-chat-warnings.muted");
        this.litebansWarnPermaBanned = config.getStringList("litebans-entry-warnings.permabanned");
        this.litebansWarnBanned = config.getStringList("litebans-entry-warnings.banned");
        this.litebansWarnPermaMuted = config.getStringList("litebans-entry-warnings.permamuted");
        this.litebansWarnMuted = config.getStringList("litebans-entry-warnings.muted");
        this.litebansWarnWarned = config.getStringList("litebans-entry-warnings.warned");
        this.litebansWarnKicked = config.getStringList("litebans-entry-warnings.kicked");
        this.staffWarnPermaBanned = config.getStringList("stafflog-entry-warnings.permabanned");
        this.staffWarnBanned = config.getStringList("stafflog-entry-warnings.banned");
        this.staffWarnPermaMuted = config.getStringList("stafflog-entry-warnings.permamuted");
        this.staffWarnMuted = config.getStringList("stafflog-entry-warnings.muted");
        this.staffWarnWarned = config.getStringList("stafflog-entry-warnings.warned");
        this.staffWarnKicked = config.getStringList("stafflog-entry-warnings.kicked");
    }

    public String getDiscordWarnBanned() {
        StringBuilder sb = new StringBuilder();
        for (String line : this.discordWarnBanned) {
            sb.append(line.replace("\\n", "\n"));
        }
        return sb.toString();
    }

    public String getDiscordWarnMuted() {
        StringBuilder sb = new StringBuilder();
        for (String line : this.discordWarnMuted) {
            sb.append(line.replace("\\n", "\n"));
        }
        return sb.toString();
    }


    public String getLitebansWarnPermaBanned(String issuer, String reason) {
        StringBuilder sb = new StringBuilder();
        for (String line : this.litebansWarnPermaBanned) {
            sb.append(line.replace("\\n", "\n")
                    .replace("<issuer>", issuer)
                    .replace("<reason>", reason));
        }
        return sb.toString();
    }

    public String getLitebansWarnBanned(String issuer, String reason, long start, long end, String until) {
        StringBuilder sb = new StringBuilder();
        DateTime dateTime = TimeUtil.diffTimeToString(start, end);
        for (String line : this.litebansWarnBanned) {
            replaceLiteBansWarnStrings(issuer, reason, start, end, until, sb, dateTime, line);
        }
        return sb.toString();
    }

    public String getLitebansWarnPermaMuted(String issuer, String reason) {
        StringBuilder sb = new StringBuilder();
        for (String line : this.litebansWarnPermaMuted) {
            sb.append(line.replace("\\n", "\n")
                    .replace("<issuer>", issuer)
                    .replace("<reason>", reason));
        }
        return sb.toString();
    }

    public String getLitebansWarnMuted(String issuer, String reason, long start, long end, String until) {
        StringBuilder sb = new StringBuilder();
        DateTime dateTime = TimeUtil.diffTimeToString(start, end);
        for (String line : this.litebansWarnMuted) {
            replaceLiteBansWarnStrings(issuer, reason, start, end, until, sb, dateTime, line);
        }
        return sb.toString();
    }

    public String getLitebansWarnWarned(String issuer, String reason) {
        StringBuilder sb = new StringBuilder();
        for (String line : this.litebansWarnWarned) {
            sb.append(line.replace("\\n", "\n")
                    .replace("<issuer>", issuer)
                    .replace("<reason>", reason));
        }
        return sb.toString();
    }

    public String getLitebansWarnKicked(String issuer, String reason) {
        StringBuilder sb = new StringBuilder();
        for (String line : this.litebansWarnKicked) {
            sb.append(line.replace("\\n", "\n")
                    .replace("<issuer>", issuer)
                    .replace("<reason>", reason));
        }
        return sb.toString();
    }

    public String getStaffWarnPermaBanned(String name, String issuer, String reason) {
        StringBuilder sb = new StringBuilder();
        for (String line : this.staffWarnPermaBanned) {
            sb.append(line.replace("\\n", "\n")
                    .replace("<issuer>", issuer)
                    .replace("<reason>", reason)
                    .replace("<player>", name));
        }
        return sb.toString();
    }

    public String getStaffWarnBanned(String name, String issuer, String reason, long start, long end, String until) {
        StringBuilder sb = new StringBuilder();
        DateTime dateTime = TimeUtil.diffTimeToString(start, end);
        for (String line : this.staffWarnBanned) {
            replaceStaffWarnStrings(name, issuer, reason, start, end, until, sb, dateTime, line);
        }
        return sb.toString();
    }

    public String getStaffWarnPermaMuted(String name, String issuer, String reason) {
        StringBuilder sb = new StringBuilder();
        for (String line : this.staffWarnPermaMuted) {
            sb.append(line.replace("\\n", "\n")
                    .replace("<issuer>", issuer)
                    .replace("<reason>", reason)
                    .replace("<player>", name));
        }
        return sb.toString();
    }

    public String getStaffWarnMuted(String name, String issuer, String reason, long start, long end, String until) {
        StringBuilder sb = new StringBuilder();
        DateTime dateTime = TimeUtil.diffTimeToString(start, end);
        for (String line : this.staffWarnMuted) {
            replaceStaffWarnStrings(name, issuer, reason, start, end, until, sb, dateTime, line);
        }
        return sb.toString();
    }

    public String getStaffWarnWarned(String name, String issuer, String reason) {
        StringBuilder sb = new StringBuilder();
        for (String line : this.staffWarnWarned) {
            sb.append(line.replace("\\n", "\n")
                    .replace("<issuer>", issuer)
                    .replace("<reason>", reason)
                    .replace("<player>", name));
        }
        return sb.toString();
    }

    public String getStaffWarnKicked(String name, String issuer, String reason) {
        StringBuilder sb = new StringBuilder();
        for (String line : this.staffWarnKicked) {
            sb.append(line.replace("\\n", "\n")
                    .replace("<issuer>", issuer)
                    .replace("<reason>", reason)
                    .replace("<player>", name));
        }
        return sb.toString();
    }

    private void replaceLiteBansWarnStrings(String issuer, String reason, long start, long end, String until, StringBuilder sb, DateTime dateTime, String line) {
        sb.append(line.replace("\\n", "\n")
                .replace("<issuer>", issuer)
                .replace("<reason>", reason)
                .replace("<days>", String.valueOf(dateTime.days))
                .replace("<hours>", String.valueOf(dateTime.hours))
                .replace("<minutes>", String.valueOf(dateTime.minutes))
                .replace("<seconds>", String.valueOf(dateTime.seconds))
                .replace("<start>", start + "")
                .replace("<end>", end + "")
                .replace("<until>", until + ""));
    }

    private void replaceStaffWarnStrings(String name, String issuer, String reason, long start, long end, String until, StringBuilder sb, DateTime dateTime, String line) {
        sb.append(line.replace("\\n", "\n")
                .replace("<issuer>", issuer)
                .replace("<reason>", reason)
                .replace("<player>", name)
                .replace("<days>", String.valueOf(dateTime.days))
                .replace("<hours>", String.valueOf(dateTime.hours))
                .replace("<minutes>", String.valueOf(dateTime.minutes))
                .replace("<seconds>", String.valueOf(dateTime.seconds))
                .replace("<start>", start + "")
                .replace("<end>", end + "")
                .replace("<until>", until + ""));
    }
}
