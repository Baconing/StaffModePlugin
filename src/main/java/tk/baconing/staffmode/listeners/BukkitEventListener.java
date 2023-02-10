package tk.baconing.staffmode.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tk.baconing.staffmode.entities.User;
import tk.baconing.staffmode.managers.DatabaseManager;

public class BukkitEventListener implements Listener {
    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if ( p.hasPermission("staffmode.toggle") && DatabaseManager.DatabaseQueries.getUserByName(p.getName()) != null  || DatabaseManager.DatabaseQueries.getUserByUUID(p.getUniqueId().toString()) != null) {
            User u = DatabaseManager.DatabaseQueries.getUserByName(p.getName()) != null ? DatabaseManager.DatabaseQueries.getUserByName(p.getName()) : DatabaseManager.DatabaseQueries.getUserByUUID(p.getUniqueId().toString());
            assert u != null;
            if (u.isStaffMode()) {
                //todo all the luckperms contexts stuff
            }
            return;
        } else if (p.hasPermission("staffmode.toggle")) {
            User u = new User();
            u.setUsername(p.getName());
            u.setUuid(p.getUniqueId().toString());
            u.setStaffMode(false);
            DatabaseManager.DatabaseQueries.createUser(u);
        }
        return;
    }
}
