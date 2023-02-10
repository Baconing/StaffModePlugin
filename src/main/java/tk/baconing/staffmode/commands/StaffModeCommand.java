package tk.baconing.staffmode.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tk.baconing.staffmode.StaffMode;
import tk.baconing.staffmode.entities.User;
import tk.baconing.staffmode.managers.DatabaseManager;
import tk.baconing.staffmode.serializers.PlayerSerializer;

import java.io.IOException;

public class StaffModeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p;
        if (args.length != 0 && sender.hasPermission("staffmode.toggle.others")) {
            p = Bukkit.getPlayer(args[0]);
            if (p == null) {
                sender.sendMessage(ChatColor.RED + "Player not found.");
                return true;
            }
        } else if (args.length == 0 && sender.hasPermission("staffmode.toggle")) {
            p = (Player) sender;
        } else {
            return true;
        }

        if (DatabaseManager.DatabaseQueries.getUserByName(p.getName()) == null && DatabaseManager.DatabaseQueries.getUserByUUID(p.getUniqueId().toString()) == null) {
            StaffMode.get().getLogger().warning("User " + p.getName() + "does not exist in database.");
            p.sendMessage(ChatColor.RED + "Your User object was not found in the database, try rejoining the server.");
            return false;
        }

        User u = DatabaseManager.DatabaseQueries.getUserByName(p.getName()) != null ? DatabaseManager.DatabaseQueries.getUserByName(p.getName()) : DatabaseManager.DatabaseQueries.getUserByUUID(p.getUniqueId().toString());
        assert u != null;

        if (u.isStaffMode()) {
            PlayerSerializer.deSerialize(u.getSerializedData(), p);
            p.sendMessage(ChatColor.GREEN + "Staff Mode disabled.");
            u.setStaffMode(false);
            Bukkit.getServer().getOnlinePlayers().forEach(player -> {
                if (player.hasPermission("staffmode.alert")) {
                    player.sendMessage(ChatColor.BLUE + "[STAFF] " + ChatColor.AQUA + p.getName() + " disabled staff mode.");
                }
            });
        } else {
            String data = PlayerSerializer.serialize(p);
            u.setSerializedData(data);
            p.getInventory().clear();
            p.setExp(0);
            p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
            p.setGameMode(GameMode.SPECTATOR);
            p.sendMessage(ChatColor.GREEN + "Staff Mode enabled.");
            u.setStaffMode(true);
            Bukkit.getServer().getOnlinePlayers().forEach(player -> {
                if (player.hasPermission("staffmode.alert")) {
                    player.sendMessage(ChatColor.BLUE + "[STAFF] " + ChatColor.AQUA + p.getName() + " enabled staff mode.");
                }
            });
        }

        return true;
    }
}
