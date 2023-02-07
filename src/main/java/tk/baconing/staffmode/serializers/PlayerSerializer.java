package tk.baconing.staffmode.serializers;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class PlayerSerializer{
    public static String serialize(Player player) {
        String inventory = InventorySerializer.serialize(player.getInventory().getContents(), player.getInventory().getArmorContents());
        Location loc = player.getLocation();

        JSONObject obj = new JSONObject();

        obj.put("inventory", inventory);

        obj.put("exp", player.getTotalExperience());
        obj.put("health", player.getHealth());
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

    public static void deSerialize(String data, Player player) throws IOException {
        JSONObject obj = new JSONObject(data);

        ItemStack[][] inventoryArray = InventorySerializer.deserialize(obj.getString("inventory"));
        ItemStack[] inventory = inventoryArray[0];
        ItemStack[] armor = inventoryArray[1];

        int exp = obj.getInt("exp");
        double health = obj.getDouble("health");
        int foodLevel = obj.getInt("foodLevel");
        int air = obj.getInt("air");
        Collection<PotionEffect> potEffects = PotSerializer.deserialize(obj.getString("potEffects"));

        GameMode gameMode = GameMode.valueOf(obj.getString("gamemode"));

        World world = Bukkit.getServer().getWorld(obj.getString("world"));
        Location loc = new Location(world, obj.getDouble("locationX"), obj.getDouble("locationY"), obj.getDouble("locationZ"), obj.getFloat("locationYaw"), obj.getFloat("locationPitch"));

        player.getInventory().setContents(inventory);
        player.getInventory().setArmorContents(armor);

        player.setTotalExperience(exp);
        player.setHealth(health);
        player.setFoodLevel(foodLevel);
        player.setRemainingAir(air);
        player.setGameMode(gameMode);
        player.addPotionEffects(potEffects);

        player.teleport(loc);
    }
}
