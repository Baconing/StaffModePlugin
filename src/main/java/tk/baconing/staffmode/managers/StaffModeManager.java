package tk.baconing.staffmode.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import tk.baconing.staffmode.StaffMode;
import tk.baconing.staffmode.entities.User;
import tk.baconing.staffmode.events.StaffModeDisableEvent;
import tk.baconing.staffmode.events.StaffModeEnableEvent;
import tk.baconing.staffmode.serializers.PlayerSerializer;

public class StaffModeManager {
    public static void enableStaffMode(Player p, User u) {
        String data = PlayerSerializer.serialize(p);
        StaffModeEnableEvent e = new StaffModeEnableEvent(p, u, data);
        Bukkit.getPluginManager().callEvent(e);
        if (!(e.isCancelled())) {
            u.setSerializedData(data);
            p.getInventory().clear();
            p.setExp(0);
            p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
            p.setGameMode(GameMode.valueOf(StaffMode.get().getConfig().getString("staffmodegamemode")));
            p.sendMessage(ChatColor.GREEN + "Staff Mode enabled.");
            u.setStaffMode(true);
            DatabaseManager.DatabaseQueries.updateUser(u);
            Bukkit.getServer().getOnlinePlayers().forEach(player -> {
                if (player.hasPermission("staffmode.alert") && !(player.equals(p))) {
                    player.sendMessage(ChatColor.BLUE + "[STAFF] " + ChatColor.AQUA + p.getName() + " enabled staff mode.");
                }
            });
        }
    }

    public static void disableStaffMode(Player p, User u) {
        StaffModeDisableEvent e = new StaffModeDisableEvent(p, u);
        Bukkit.getPluginManager().callEvent(e);
        if (!(e.isCancelled())) {
            PlayerSerializer.deSerialize(u.getSerializedData(), p);
            p.sendMessage(ChatColor.GREEN + "Staff Mode disabled.");
            u.setStaffMode(false);
            DatabaseManager.DatabaseQueries.updateUser(u);
            Bukkit.getServer().getOnlinePlayers().forEach(player -> {
                if (player.hasPermission("staffmode.alert") && !(player.equals(p))) {
                    player.sendMessage(ChatColor.BLUE + "[STAFF] " + ChatColor.AQUA + p.getName() + " disabled staff mode.");
                }
            });
        }
    }
}
