package tk.baconing.staffmode.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.annotation.Nullable;

@DatabaseTable
public class User {
    @DatabaseField(generatedId = true, canBeNull = false)
    private int id;

    @DatabaseField(canBeNull = false)
    private String uuid;

    @DatabaseField(canBeNull = false)
    private String username;

    @DatabaseField()
    private @Nullable String serializedData;

    @DatabaseField(canBeNull = false)
    private boolean isStaffMode;

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
}
