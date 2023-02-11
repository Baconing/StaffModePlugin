package tk.baconing.staffmode.managers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tk.baconing.staffmode.StaffMode;
import tk.baconing.staffmode.entities.User;

public class ParseManager {
    public static String parse(String string, Player player) {
        User user = DatabaseManager.DatabaseQueries.getUser(player);
        string = string.replace("%playerDisplayName%", player.getDisplayName());
        string = string.replace("%playerName%", player.getName());
        string = string.replace("%playerUUID%", player.getUniqueId().toString());
        string = string.replace("%staffStatus%", user.isStaffMode() ? StaffMode.getMessagesConfig().getString("staffStatus.enabled") : StaffMode.getMessagesConfig().getString("staffStatus.disabled"));
        string = parseColors(string);
        return string;
    }

    public static String parseColors(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
