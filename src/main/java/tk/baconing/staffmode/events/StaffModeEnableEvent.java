package tk.baconing.staffmode.events;

import org.bukkit.entity.Player;
import tk.baconing.staffmode.entities.User;

public class StaffModeEnableEvent extends StaffModeEvent {
    public String serializedData;
    public boolean isEnabledByOther;

    public StaffModeEnableEvent(Player player, User user, String serializedData, boolean isEnabledByOther) {
        super(player, user);
        this.serializedData = serializedData;
        this.isEnabledByOther = isEnabledByOther;
    }

    public String getSerializedData() {
        return this.serializedData;
    }

    public void setSerializedData(String serializedData) {
        this.serializedData = serializedData;
    }

    public boolean isEnabledByOther() {
        return isEnabledByOther;
    }

    public void setEnabledByOther(boolean enabledByOther) {
        isEnabledByOther = enabledByOther;
    }
}
