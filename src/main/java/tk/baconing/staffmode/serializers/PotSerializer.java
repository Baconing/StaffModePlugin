package tk.baconing.staffmode.serializers;

import org.bukkit.potion.PotionEffect;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

public class PotSerializer {
    public static String serialize(Collection<PotionEffect> effects) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BukkitObjectOutputStream boos = null;

        try {
            boos = new BukkitObjectOutputStream(baos);
            boos.writeObject(effects);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                boos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    public static Collection<PotionEffect> deserialize(String data) {
        Collection<PotionEffect> effects = null;

        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getDecoder().decode(data));
            BukkitObjectInputStream bois = new BukkitObjectInputStream(bais);
            effects = (List<PotionEffect>) bois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return effects;
    }
}
