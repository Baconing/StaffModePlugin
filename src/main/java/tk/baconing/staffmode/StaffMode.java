package tk.baconing.staffmode;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tk.baconing.staffmode.commands.StaffModeCommand;
import tk.baconing.staffmode.commands.StaffModeTabComplete;
import tk.baconing.staffmode.listeners.ChatListener;
import tk.baconing.staffmode.managers.DatabaseManager;

import java.sql.SQLException;

public final class StaffMode extends JavaPlugin {
    private static StaffMode plugin;

    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        try {
            DatabaseManager.initialize(getConfig().getString("databaseurl"));
        } catch (SQLException e) {
            Bukkit.getPluginManager().disablePlugin(this);
            throw new RuntimeException(e);
        }
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginCommand("staffmode").setExecutor(new StaffModeCommand());
        Bukkit.getPluginCommand("staffmode").setTabCompleter(new StaffModeTabComplete());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static StaffMode get() {
        return plugin;
    }
}
