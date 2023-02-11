package tk.baconing.staffmode.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tk.baconing.staffmode.entities.User;
import tk.baconing.staffmode.managers.DatabaseManager;
import tk.baconing.staffmode.managers.StaffModeManager;

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

        User u = DatabaseManager.DatabaseQueries.getUser(p);
        if (u.isStaffMode()) {
            StaffModeManager.disableStaffMode(p, u);
        } else {
            StaffModeManager.enableStaffMode(p, u);
        }

        return true;
    }
}
