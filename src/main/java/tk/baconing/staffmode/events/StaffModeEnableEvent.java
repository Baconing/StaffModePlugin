package tk.baconing.staffmode.events;

import org.bukkit.entity.Player;
import tk.baconing.staffmode.entities.User;

public class StaffModeEnableEvent extends StaffModeEvent {
    public String serializedData;

    public StaffModeEnableEvent(Player player, User user, String serializedData) {
        super(player, user);
        this.serializedData = serializedData;
    }

    public String getSerializedData() {
        return this.serializedData;
    }

    public void setSerializedData(String serializedData) {
        this.serializedData = serializedData;
    }
}
