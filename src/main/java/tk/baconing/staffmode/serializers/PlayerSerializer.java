package tk.baconing.staffmode.serializers;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.json.JSONObject;

import java.util.Collection;

public class PlayerSerializer{
    public static String serialize(Player player) {
        String inventory = InventorySerializer.serialize(player.getInventory().getContents(), player.getInventory().getArmorContents());
        Location loc = player.getLocation();

        JSONObject obj = new JSONObject();

        obj.put("inventory", inventory);

        obj.put("totExp", player.getTotalExperience());
        obj.put("exp", player.getExp());
        obj.put("level", player.getLevel());

        obj.put("health", player.getHealth());
        obj.put("maxhealth", player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        obj.put("foodLevel", player.getFoodLevel());
        obj.put("air", player.getRemainingAir());
        obj.put("potEffects", PotSerializer.serialize(player.getActivePotionEffects()));

        obj.put("gamemode", player.getGameMode().name());

        obj.put("world", player.getWorld().getName());
        obj.put("locationY", loc.getY());
        obj.put("locationX", loc.getX());
        obj.put("locationZ", loc.getZ());
        obj.put("locationYaw", loc.getYaw());
        obj.put("locationPitch", loc.getPitch());

        return obj.toString();
    }

    public static void deSerialize(String data, Player player) {
        JSONObject obj = new JSONObject(data);

        ItemStack[][] inventoryArray = InventorySerializer.deserialize(obj.getString("inventory"));
        ItemStack[] inventory = inventoryArray[0];
        ItemStack[] armor = inventoryArray[1];

        int totExp = obj.getInt("totExp");
        float exp = obj.getFloat("exp");
        int level = obj.getInt("level");
        double health = obj.getDouble("health");
        double maxHealth = obj.getDouble("maxhealth");
        int foodLevel = obj.getInt("foodLevel");
        int air = obj.getInt("air");
        Collection<PotionEffect> potEffects = PotSerializer.deserialize(obj.getString("potEffects"));

        GameMode gameMode = GameMode.valueOf(obj.getString("gamemode"));

        World world = Bukkit.getServer().getWorld(obj.getString("world"));
        Location loc = new Location(world, obj.getDouble("locationX"), obj.getDouble("locationY"), obj.getDouble("locationZ"), obj.getFloat("locationYaw"), obj.getFloat("locationPitch"));

        player.getInventory().setContents(inventory);
        player.getInventory().setArmorContents(armor);

        player.setTotalExperience(totExp);
        player.setExp(exp);
        player.setLevel(level);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        player.setHealth(health);
        player.setFoodLevel(foodLevel);
        player.setRemainingAir(air);
        player.setGameMode(gameMode);
        player.addPotionEffects(potEffects);

        player.teleport(loc);
    }
}
