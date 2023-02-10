package tk.baconing.staffmode.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StaffModeTabComplete implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("staffmode.toggle.others")) {
            List<String> list = new ArrayList<String>();
            Bukkit.getOnlinePlayers().forEach(player -> {
                list.add(player.getName());
            });
            return list;
        } else {
            List<String> list = new ArrayList<String>();
            list.add(sender.getName());
            return list;
        }
    }
}
