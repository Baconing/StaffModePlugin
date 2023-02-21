package tk.baconing.staffmode.events;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import tk.baconing.staffmode.entities.User;

public class StaffModeEnableEvent extends StaffModeEvent {
    public @Nullable String serializedData;
    public @Nullable Boolean isEnabledByOther;

    public StaffModeEnableEvent(Player player, User user, String serializedData, boolean isEnabledByOther) {
        super(player, user);
        this.serializedData = serializedData;
        this.isEnabledByOther = isEnabledByOther;
    }

    public StaffModeEnableEvent(Player player, User user, boolean isEnabledByOther) {
        super(player, user);
        this.isEnabledByOther = isEnabledByOther;
    }

    public @Nullable String getSerializedData() {
        return this.serializedData;
    }

    public void setSerializedData(String serializedData) {
        this.serializedData = serializedData;
    }

    public @Nullable Boolean isEnabledByOther() {
        return isEnabledByOther;
    }

    public void setEnabledByOther(@Nullable boolean enabledByOther) {
        isEnabledByOther = enabledByOther;
    }
}
