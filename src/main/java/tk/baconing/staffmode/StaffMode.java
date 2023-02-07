package tk.baconing.staffmode;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tk.baconing.staffmode.listeners.ChatListener;

public final class StaffMode extends JavaPlugin {
    private static StaffMode plugin;

    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static StaffMode get() {
        return plugin;
    }
}
