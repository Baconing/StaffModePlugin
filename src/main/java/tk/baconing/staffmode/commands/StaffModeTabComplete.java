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
        List<String> list = new ArrayList<String>();
        if (sender.hasPermission("staffmode.toggle.others")) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                list.add(player.getName());
            });
        }
        if (sender.hasPermission("staffmode.reload")) {
            list.add("reload");
            list.add(sender.getName());
        }
        list.add(sender.getName());
        return list;
    }
}
