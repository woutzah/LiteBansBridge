package be.woutzah.litebansbridge;

import be.woutzah.litebansbridge.discord.listener.DiscordListener;
import be.woutzah.litebansbridge.litebans.listener.LiteBansListener;
import be.woutzah.litebansbridge.discord.DiscordManager;
import be.woutzah.litebansbridge.message.MessageManager;
import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class LiteBansBridge extends JavaPlugin {

    private DiscordListener discordListener;
    private DiscordManager discordManager;
    private LiteBansListener liteBansListener;
    private MessageManager messageManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        init();
        printConsoleMessage();
        DiscordSRV.api.subscribe(this.discordListener);
        liteBansListener.registerEvent();
    }

    @Override
    public void onDisable() {
        DiscordSRV.api.subscribe(this.discordListener);
    }

    public void init(){
        this.discordListener = new DiscordListener(this);
        this.discordManager = new DiscordManager(this);
        this.liteBansListener = new LiteBansListener(this);
        this.messageManager = new MessageManager(this);
    }

    public void printConsoleMessage() {
        Bukkit.getConsoleSender()
                .sendMessage(parseColorCodes("&3&l>&7&m----------&3&l[ &b&oLiteBansBridge &3&l]&7&m--------&3&l<"));
        Bukkit.getConsoleSender()
                .sendMessage(parseColorCodes("&3&l>                                    &3&l<"));
        Bukkit.getConsoleSender()
                .sendMessage(parseColorCodes("&3&l>         &bLiteBansBridge V" + this
                        .getDescription().getVersion() + "        &3&l<"));

        Bukkit.getConsoleSender()
                .sendMessage(parseColorCodes("&3&l>              &9&oBy Woutzah            &3&l<"));
        Bukkit.getConsoleSender()
                .sendMessage(parseColorCodes("&3&l>                                    &3&l<"));
        Bukkit.getConsoleSender()
                .sendMessage(parseColorCodes("&3&l>              &aEnabling ...          &3&l<"));
        Bukkit.getConsoleSender()
                .sendMessage(parseColorCodes("&3&l>                                    &3&l<"));
        Bukkit.getConsoleSender()
                .sendMessage(parseColorCodes("&3&l>&7&m------------------------------------&3&l<"));
    }

    private String parseColorCodes(String text) { return ChatColor.translateAlternateColorCodes('&', text); }

    public DiscordManager getDiscordManager() {
        return discordManager;
    }

    public MessageManager getMessageManager(){return messageManager;}
}
