package tk.baconing.staffmode.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;
import tk.baconing.staffmode.StaffMode;
import tk.baconing.staffmode.serializers.PlayerSerializer;

import java.io.IOException;
// temporary testing class
public class ChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        StaffMode.get().getLogger().info("test");
        String data = PlayerSerializer.serialize(e.getPlayer());
        e.getPlayer().getInventory().clear();
        e.getPlayer().setExp(0);
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                PlayerSerializer.deSerialize(data, e.getPlayer());
            }
        };
        task.runTaskLater(StaffMode.get(), 300);
    }
}
