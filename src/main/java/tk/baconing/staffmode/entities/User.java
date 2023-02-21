package tk.baconing.staffmode.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.jetbrains.annotations.Nullable;

@DatabaseTable(tableName = "staffmode_users")
public class User {
    @DatabaseField(generatedId = true, canBeNull = false)
    private int id;

    @DatabaseField(canBeNull = false)
    private String uuid;

    @DatabaseField(canBeNull = false)
    private String username;

    @DatabaseField(dataType = DataType.LONG_STRING)
    private @Nullable String serializedData;

    @DatabaseField(canBeNull = false)
    private boolean isStaffMode;

    @DatabaseField()
    private @Nullable Boolean isEnabledByOther;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Nullable
    public String getSerializedData() {
        return serializedData;
    }

    public void setSerializedData(@Nullable String serializedData) {
        this.serializedData = serializedData;
    }

    public boolean isStaffMode() {
        return isStaffMode;
    }

    public void setStaffMode(boolean staffMode) {
        isStaffMode = staffMode;
    }

    @Nullable
    public Boolean isEnabledByOther() {
        return isEnabledByOther;
    }

    public void setEnabledByOther(@Nullable Boolean enabledByOther) {
        isEnabledByOther = enabledByOther;
    }
}
