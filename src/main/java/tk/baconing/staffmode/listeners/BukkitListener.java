package tk.baconing.staffmode.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tk.baconing.staffmode.managers.DatabaseManager;

public class BukkitListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        DatabaseManager.getCache().put(e.getPlayer(), DatabaseManager.DatabaseQueries.getUser(e.getPlayer()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        DatabaseManager.getCache().remove(e.getPlayer());
    }
}
