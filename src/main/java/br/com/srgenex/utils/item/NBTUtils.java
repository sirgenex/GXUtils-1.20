package br.com.srgenex.utils.item;

import br.com.srgenex.utils.GXUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

@SuppressWarnings("unused")
public class NBTUtils {

    public static boolean exists(ItemStack item, String key, PersistentDataType<?, ?> type) {
        if (item == null || item.getType() == Material.AIR) return false;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return false;
        return meta.getPersistentDataContainer().has(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), type);
    }

    public static ItemStack setString(ItemStack item, String key, String value) {
        if (item == null || item.getType() == Material.AIR) return item;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return item;
        meta.getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), PersistentDataType.STRING, value);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setInteger(ItemStack item, String key, Integer value) {
        if (item == null || item.getType() == Material.AIR) return item;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return item;
        meta.getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setDouble(ItemStack item, String key, Double value) {
        if (item == null || item.getType() == Material.AIR) return item;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return item;
        meta.getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), PersistentDataType.DOUBLE, value);
        item.setItemMeta(meta);
        return item;
    }

    public static String getString(ItemStack item, String key) {
        if (item == null || item.getType() == Material.AIR) return null;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return null;
        return meta.getPersistentDataContainer().get(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), PersistentDataType.STRING);
    }

    public static Integer getInteger(ItemStack item, String key) {
        if (item == null || item.getType() == Material.AIR) return null;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return null;
        return meta.getPersistentDataContainer().get(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), PersistentDataType.INTEGER);
    }

    public static Double getDouble(ItemStack item, String key) {
        if (item == null || item.getType() == Material.AIR) return null;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return null;
        return meta.getPersistentDataContainer().get(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), PersistentDataType.DOUBLE);
    }

}
