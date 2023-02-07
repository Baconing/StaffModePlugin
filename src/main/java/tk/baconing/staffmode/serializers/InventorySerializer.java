package tk.baconing.staffmode.serializers;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class InventorySerializer {
    public static String serialize(ItemStack[] contents, ItemStack[] armor) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BukkitObjectOutputStream boos = null;

        try {
            boos = new BukkitObjectOutputStream(baos);
            boos.writeObject(contents);
            boos.writeObject(armor);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                boos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    public static ItemStack[][] deserialize(String data) {
        ItemStack[] contents = null;
        ItemStack[] armor = null;

        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getDecoder().decode(data));
            BukkitObjectInputStream bois = new BukkitObjectInputStream(bais);
            contents = (ItemStack[]) bois.readObject();
            armor = (ItemStack[]) bois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ItemStack[][]{contents, armor};
    }
}
