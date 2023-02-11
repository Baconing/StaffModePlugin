package tk.baconing.staffmode;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import tk.baconing.staffmode.commands.StaffModeCommand;
import tk.baconing.staffmode.commands.StaffModeTabComplete;
import tk.baconing.staffmode.entities.User;
import tk.baconing.staffmode.managers.ContextManager;
import tk.baconing.staffmode.managers.DatabaseManager;
import tk.baconing.staffmode.managers.PlaceholderManager;
import tk.baconing.staffmode.managers.StaffModeManager;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public final class StaffMode extends JavaPlugin {
    private static StaffMode plugin;
    private static YamlConfiguration messagesConfig;

    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        this.loadMessagesConfig();
        
        try {
            DatabaseManager.initialize(getConfig().getString("databaseurl"));
        } catch (SQLException e) {
            Bukkit.getPluginManager().disablePlugin(this);
            throw new RuntimeException(e);
        }

        // register luckperms
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider == null) {
            this.getLogger().warning("LuckPerms API was not found! Make sure LuckPerms is installed.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        LuckPerms lpApi = LuckPermsProvider.get();
        lpApi.getContextManager().registerCalculator(new ContextManager());

        // register placholderapi
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderManager(this).register();
        }

        // register commands
        Bukkit.getPluginCommand("staffmode").setExecutor(new StaffModeCommand());
        Bukkit.getPluginCommand("staffmode").setTabCompleter(new StaffModeTabComplete());

        // just a really stupid thing
        BukkitRunnable checkperms = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    User u = DatabaseManager.DatabaseQueries.getUser(player);
                    if (!(player.hasPermission("staffmode.toggle")) && u.isStaffMode() && Boolean.FALSE.equals(u.isEnabledByOther())) StaffModeManager.disableStaffMode(player, u);
                });
            }
        };
        checkperms.runTaskTimer(this, 1, 100);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void reload() {
        get().reloadConfig();
        get().loadMessagesConfig();
        Bukkit.getPluginManager().enablePlugin(StaffMode.get());
    }

    public void loadMessagesConfig() {
        File messagesFile = new File(getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            messagesFile.getParentFile().mkdirs();
            saveResource("messages.yml", false);
        }
        messagesConfig = new YamlConfiguration();
        try {
            messagesConfig.load(messagesFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static StaffMode get() {
        return plugin;
    }

    public static YamlConfiguration getMessagesConfig() {
        return messagesConfig;
    }
}
