package tk.baconing.staffmode.managers;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import tk.baconing.staffmode.StaffMode;
import tk.baconing.staffmode.entities.User;
import tk.baconing.staffmode.events.StaffModeDisableEvent;
import tk.baconing.staffmode.events.StaffModeEnableEvent;
import tk.baconing.staffmode.serializers.PlayerSerializer;

public class StaffModeManager {
    public static void enableStaffMode(Player player, User user) {
        StaffModeEnableEvent e = new StaffModeEnableEvent(player, user, false);
        String data = null;
        if (StaffMode.get().getConfig().getBoolean("serializePlayer")) {
            data = PlayerSerializer.serialize(player);
        }
        e.setSerializedData(data);
        Bukkit.getPluginManager().callEvent(e);
        if (!(e.isCancelled())) {
            if (StaffMode.get().getConfig().getBoolean("serializePlayer")) {
                user.setSerializedData(data);
            }
            player.getInventory().clear();
            player.setExp(0);
            player.setTotalExperience(0);
            player.setLevel(0);
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
            if (!(StaffMode.get().getConfig().getString("staffmodegamemode").equalsIgnoreCase("NOCHANGE"))) {
                player.setGameMode(GameMode.valueOf(StaffMode.get().getConfig().getString("staffmodegamemode")));
            }
            player.sendMessage(ParseManager.parse(StaffMode.getMessagesConfig().getString("staffmodeEnabled"), player));
            user.setStaffMode(true);
            user.setEnabledByOther(false);
            DatabaseManager.DatabaseQueries.updateUser(user);
            Bukkit.getServer().getOnlinePlayers().forEach(p -> {
                if (p.hasPermission("staffmode.alert") && !(p.equals(player))) {
                    p.sendMessage(ParseManager.parse(StaffMode.getMessagesConfig().getString("staffmodeBroadcastEnabled"), player));
                }
            });
        }
    }

    public static void disableStaffMode(Player player, User user) {
        StaffModeDisableEvent e = new StaffModeDisableEvent(player, user);
        Bukkit.getPluginManager().callEvent(e);
        if (!(e.isCancelled())) {
            try {
                if (StaffMode.get().getConfig().getBoolean("serializePlayer") && user.getSerializedData() != null && !(user.getSerializedData().equalsIgnoreCase(""))) {
                    PlayerSerializer.deSerialize(user.getSerializedData(), player);
                }
            } catch (Exception ex) {
                return;
            }
            player.sendMessage(ParseManager.parse(StaffMode.getMessagesConfig().getString("staffmodeDisabled"), player));
            user.setStaffMode(false);
            user.setEnabledByOther(null);
            user.setSerializedData(null);
            DatabaseManager.DatabaseQueries.updateUser(user);
            Bukkit.getServer().getOnlinePlayers().forEach(p -> {
                if (p.hasPermission("staffmode.alert") && !(p.equals(player))) {
                    p.sendMessage(ParseManager.parse(StaffMode.getMessagesConfig().getString("staffmodeBroadcastDisabled"), player));
                }
            });
        }
    }

    public static void enableStaffMode(Player player, Player sender, User user) {
        StaffModeEnableEvent e = new StaffModeEnableEvent(player, user, false);
        String data = null;
        if (StaffMode.get().getConfig().getBoolean("serializePlayer")) {
            data = PlayerSerializer.serialize(player);
        }
        e.setSerializedData(data);
        Bukkit.getPluginManager().callEvent(e);
        if (!(e.isCancelled())) {
            user.setSerializedData(data);
            player.getInventory().clear();
            player.setExp(0);
            player.setTotalExperience(0);
            player.setLevel(0);
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
            player.setGameMode(GameMode.valueOf(StaffMode.get().getConfig().getString("staffmodegamemode")));
            player.sendMessage(ParseManager.parse(StaffMode.getMessagesConfig().getString("staffmodeEnabled"), player));
            sender.sendMessage(ParseManager.parse(StaffMode.getMessagesConfig().getString("staffmodeEnabledByOther"), player));
            user.setStaffMode(true);
            user.setEnabledByOther(true);
            DatabaseManager.DatabaseQueries.updateUser(user);
            Bukkit.getServer().getOnlinePlayers().forEach(p -> {
                if (p.hasPermission("staffmode.alert") && !(p.equals(player)) && !(p.equals(sender))) {
                    p.sendMessage(ParseManager.parse(StaffMode.getMessagesConfig().getString("staffmodeBroadcastEnabled"), player));
                }
            });
        }
    }

    public static void disableStaffMode(Player player, Player sender, User user) {
        StaffModeDisableEvent e = new StaffModeDisableEvent(player, user);
        Bukkit.getPluginManager().callEvent(e);
        if (!(e.isCancelled())) {
            try {
                if (StaffMode.get().getConfig().getBoolean("serializePlayer") && user.getSerializedData() != null && !(user.getSerializedData().equalsIgnoreCase(""))) {
                    PlayerSerializer.deSerialize(user.getSerializedData(), player);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
            player.sendMessage(ParseManager.parse(StaffMode.getMessagesConfig().getString("staffmodeDisabled"), player));
            sender.sendMessage(ParseManager.parse(StaffMode.getMessagesConfig().getString("staffmodeDisabledByOther"), player));
            user.setStaffMode(false);
            user.setEnabledByOther(null);
            user.setSerializedData(null);
            DatabaseManager.DatabaseQueries.updateUser(user);
            Bukkit.getServer().getOnlinePlayers().forEach(p -> {
                if (p.hasPermission("staffmode.alert") && !(p.equals(player)) && !(p.equals(sender))) {
                    p.sendMessage(ParseManager.parse(StaffMode.getMessagesConfig().getString("staffmodeBroadcastDisabled"), player));
                }
            });
        }
    }
}
