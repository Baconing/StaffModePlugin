package tk.baconing.staffmode.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tk.baconing.staffmode.StaffMode;
import tk.baconing.staffmode.entities.User;
import tk.baconing.staffmode.managers.DatabaseManager;
import tk.baconing.staffmode.managers.ParseManager;
import tk.baconing.staffmode.managers.StaffModeManager;

public class StaffModeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        boolean toggledByOther = false;
        Player p = null;
        if (args.length > 0) {
            if (args[0].equals("reload")) {
                if (sender.hasPermission("staffmode.reload")) {
                    StaffMode.reload();
                    sender.sendMessage(ChatColor.GREEN + "Plugin reloaded, check console for any errors.");
                    return true;
                }
            }
            if (sender.hasPermission("staffmode.toggle.others")) {
                toggledByOther = true;
                p = Bukkit.getPlayer(args[0]);
                if (p == null) {
                    String s = ParseManager.parseColors(StaffMode.getMessagesConfig().getString("playerNotFound"));
                    s = s.replace("%player%", args[0]);
                    sender.sendMessage(s);
                    return true;
                }
                if (p.getName().equals(sender.getName())) toggledByOther = false;
            }
        } else if (sender.hasPermission("staffmode.toggle") && sender instanceof Player) {
            p = (Player) sender;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to run this command.");
            return true;
        }

        assert p != null;
        User u = DatabaseManager.DatabaseQueries.getUser(p);

        if (u.isStaffMode() && !toggledByOther) {
            StaffModeManager.disableStaffMode(p, u);
        } else if (u.isStaffMode() && toggledByOther) {
            StaffModeManager.disableStaffMode(p, (Player) sender, u);
        } else if (toggledByOther) {
            StaffModeManager.enableStaffMode(p, (Player) sender, u);
        } else {
            StaffModeManager.enableStaffMode(p, u);
        }

        return true;
    }
}
