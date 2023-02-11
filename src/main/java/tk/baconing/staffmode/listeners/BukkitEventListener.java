package tk.baconing.staffmode.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tk.baconing.staffmode.managers.DatabaseManager;

public class BukkitEventListener implements Listener {
    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        // create user if not exists
        if (p.hasPermission("staffmode.toggle")) DatabaseManager.DatabaseQueries.getUser(p);
    }
}
