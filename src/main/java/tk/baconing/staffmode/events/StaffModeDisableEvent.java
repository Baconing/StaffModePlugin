package tk.baconing.staffmode.events;

import org.bukkit.entity.Player;
import tk.baconing.staffmode.entities.User;

public class StaffModeDisableEvent extends StaffModeEvent {
    public StaffModeDisableEvent(Player player, User user) {
        super(player, user);
    }
}
