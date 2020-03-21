package be.woutzah.litebansbridge.managers;

import be.woutzah.litebansbridge.LiteBansBridge;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class MessageManager {

    private LiteBansBridge plugin;
    private List<String> discordWarnBanned;
    private List<String> discordWarnMuted;
    private List<String> litebansWarnPermaBanned;
    private List<String> litebansWarnBanned;
    private List<String> litebansWarnPermaMuted;
    private List<String> litebansWarnMuted;
    private List<String> litebansWarnWarned;
    private List<String> litebansWarnKicked;
    private List<String> staffWarnPermaBanned;
    private List<String> staffWarnBanned;
    private List<String> staffWarnPermaMuted;
    private List<String> staffWarnMuted;
    private List<String> staffWarnWarned;
    private List<String> staffWarnKicked;

    public MessageManager(LiteBansBridge plugin) {
        this.plugin = plugin;
        FileConfiguration config = plugin.getConfig();
        this.discordWarnBanned = config.getStringList("dicord-chat-warnings.banned");
        this.discordWarnMuted = config.getStringList("dicord-chat-warnings.muted");
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

    public String getDiscordWarnBanned(){
        StringBuilder sb = new StringBuilder();
        for (String line : this.discordWarnBanned) {
            sb.append(line.replace("\\n", "\n"));
        }
        return sb.toString();
    }

    public String getDiscordWarnMuted(){
        StringBuilder sb = new StringBuilder();
        for (String line : this.discordWarnMuted) {
            sb.append(line.replace("\\n", "\n"));
        }
        return sb.toString();
    }


    public String getLitebansWarnPermaBanned(String issuer, String reason){
        StringBuilder sb = new StringBuilder();
        for (String line : this.litebansWarnPermaBanned) {
            sb.append(line.replace("\\n", "\n")
                    .replace("<issuer>",issuer)
                    .replace("<reason>",reason));
        }
        return sb.toString();
    }

    public String getLitebansWarnBanned(String issuer, String reason, String untill){
        StringBuilder sb = new StringBuilder();
        for (String line : this.litebansWarnBanned) {
            sb.append(line.replace("\\n", "\n")
                    .replace("<issuer>",issuer)
                    .replace("<reason>",reason)
                    .replace("<untill>",untill));
        }
        return sb.toString();
    }

    public String getLitebansWarnPermaMuted(String issuer, String reason){
        StringBuilder sb = new StringBuilder();
        for (String line : this.litebansWarnPermaMuted) {
            sb.append(line.replace("\\n", "\n")
                    .replace("<issuer>",issuer)
                    .replace("<reason>",reason));
        }
        return sb.toString();
    }

    public String getLitebansWarnMuted(String issuer, String reason, String untill){
        StringBuilder sb = new StringBuilder();
        for (String line : this.litebansWarnMuted) {
            sb.append(line.replace("\\n", "\n")
                    .replace("<issuer>",issuer)
                    .replace("<reason>",reason)
                    .replace("<untill>",untill));
        }
        return sb.toString();
    }

    public String getLitebansWarnWarned(String issuer, String reason){
        StringBuilder sb = new StringBuilder();
        for (String line : this.litebansWarnWarned) {
            sb.append(line.replace("\\n", "\n")
                    .replace("<issuer>",issuer)
                    .replace("<reason>",reason));
        }
        return sb.toString();
    }

    public String getLitebansWarnKicked(String issuer, String reason){
        StringBuilder sb = new StringBuilder();
        for (String line : this.litebansWarnKicked) {
            sb.append(line.replace("\\n", "\n")
                    .replace("<issuer>",issuer)
                    .replace("<reason>",reason));
        }
        return sb.toString();
    }

    public String getStaffWarnPermaBanned(String name, String issuer, String reason){
        StringBuilder sb = new StringBuilder();
        for (String line : this.staffWarnPermaBanned) {
            sb.append(line.replace("\\n", "\n")
                    .replace("<issuer>",issuer)
                    .replace("<reason>",reason)
                    .replace("<player>", name));
        }
        return sb.toString();
    }

    public String getStaffWarnBanned(String name, String issuer, String reason, String untill){
        StringBuilder sb = new StringBuilder();
        for (String line : this.staffWarnBanned) {
            sb.append(line.replace("\\n", "\n")
                    .replace("<issuer>",issuer)
                    .replace("<reason>",reason)
                    .replace("<player>", name)
                    .replace("<untill>",untill));
        }
        return sb.toString();
    }

    public String getStaffWarnPermaMuted(String name, String issuer, String reason){
        StringBuilder sb = new StringBuilder();
        for (String line : this.staffWarnPermaMuted) {
            sb.append(line.replace("\\n", "\n")
                    .replace("<issuer>",issuer)
                    .replace("<reason>",reason)
                    .replace("<player>", name));
        }
        return sb.toString();
    }

    public String getStaffWarnMuted(String name, String issuer, String reason, String untill){
        StringBuilder sb = new StringBuilder();
        for (String line : this.staffWarnMuted) {
            sb.append(line.replace("\\n", "\n")
                    .replace("<issuer>",issuer)
                    .replace("<reason>",reason)
                    .replace("<player>", name)
                    .replace("<untill>",untill));
        }
        return sb.toString();
    }

    public String getStaffWarnWarned(String name, String issuer, String reason){
        StringBuilder sb = new StringBuilder();
        for (String line : this.staffWarnWarned) {
            sb.append(line.replace("\\n", "\n")
                    .replace("<issuer>",issuer)
                    .replace("<reason>",reason)
                    .replace("<player>", name));
        }
        return sb.toString();
    }

    public String getStaffWarnKicked(String name, String issuer, String reason){
        StringBuilder sb = new StringBuilder();
        for (String line : this.staffWarnKicked) {
            sb.append(line.replace("\\n", "\n")
                    .replace("<issuer>",issuer)
                    .replace("<reason>",reason)
                    .replace("<player>", name));
        }
        return sb.toString();
    }







}
