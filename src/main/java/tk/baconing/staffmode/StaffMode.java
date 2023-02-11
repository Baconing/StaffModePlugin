package tk.baconing.staffmode;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import tk.baconing.staffmode.commands.StaffModeCommand;
import tk.baconing.staffmode.commands.StaffModeTabComplete;
import tk.baconing.staffmode.listeners.ChatListener;
import tk.baconing.staffmode.managers.ContextManager;
import tk.baconing.staffmode.managers.DatabaseManager;

import java.sql.SQLException;

public final class StaffMode extends JavaPlugin {
    private static StaffMode plugin;
    private static String tableName;

    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        tableName = this.getConfig().getString("tablename");
        try {
            DatabaseManager.initialize(getConfig().getString("databaseurl"));
        } catch (SQLException e) {
            Bukkit.getPluginManager().disablePlugin(this);
            throw new RuntimeException(e);
        }
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider == null) {
            this.getLogger().warning("LuckPerms API was not found! Make sure LuckPerms is installed.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        LuckPerms lpApi = LuckPermsProvider.get();
        lpApi.getContextManager().registerCalculator(new ContextManager());

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

    public static String getTableName() {
        return tableName;
    }
}
