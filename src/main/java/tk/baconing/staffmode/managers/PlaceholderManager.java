package tk.baconing.staffmode.managers;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.baconing.staffmode.StaffMode;

public class PlaceholderManager extends PlaceholderExpansion {
    private StaffMode plugin;

    public PlaceholderManager(StaffMode plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "staffmode";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Baconing";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if (!(params.equalsIgnoreCase("status"))) return "";

        return DatabaseManager.DatabaseQueries.getUser((Player) player).isStaffMode() ? StaffMode.getMessagesConfig().getString("placeholderAPI.staffEnabled") : StaffMode.getMessagesConfig().getString("placeholderAPI.staffDisabled");
    }
}
