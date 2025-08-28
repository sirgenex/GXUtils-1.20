package br.com.srgenex.utils.item;

import br.com.srgenex.utils.GXUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.Nullable;

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

    public static ItemStack setIntegerArray(ItemStack item, String key, int[] value) {
        if (item == null || item.getType() == Material.AIR) return item;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return item;
        meta.getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), PersistentDataType.INTEGER_ARRAY, value);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setLong(ItemStack item, String key, Long value) {
        if (item == null || item.getType() == Material.AIR) return item;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return item;
        meta.getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), PersistentDataType.LONG, value);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setLongArray(ItemStack item, String key, long[] value) {
        if (item == null || item.getType() == Material.AIR) return item;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return item;
        meta.getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), PersistentDataType.LONG_ARRAY, value);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setFloat(ItemStack item, String key, Float value) {
        if (item == null || item.getType() == Material.AIR) return item;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return item;
        meta.getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), PersistentDataType.FLOAT, value);
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

    public static ItemStack setBoolean(ItemStack item, String key, Boolean value) {
        if (item == null || item.getType() == Material.AIR) return item;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return item;
        meta.getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), PersistentDataType.BOOLEAN, value);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setByte(ItemStack item, String key, Byte value) {
        if (item == null || item.getType() == Material.AIR) return item;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return item;
        meta.getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), PersistentDataType.BYTE, value);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setByteArray(ItemStack item, String key, byte[] value) {
        if (item == null || item.getType() == Material.AIR) return item;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return item;
        meta.getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), PersistentDataType.BYTE_ARRAY, value);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setShort(ItemStack item, String key, Short value) {
        if (item == null || item.getType() == Material.AIR) return item;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return item;
        meta.getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), PersistentDataType.SHORT, value);
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

    public static int @Nullable [] getIntegerArray(ItemStack item, String key) {
        if (item == null || item.getType() == Material.AIR) return null;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return null;
        return meta.getPersistentDataContainer().get(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), PersistentDataType.INTEGER_ARRAY);
    }

    public static Long getLong(ItemStack item, String key) {
        if (item == null || item.getType() == Material.AIR) return null;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return null;
        return meta.getPersistentDataContainer().get(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), PersistentDataType.LONG);
    }

    public static long @Nullable [] getLongArray(ItemStack item, String key) {
        if (item == null || item.getType() == Material.AIR) return null;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return null;
        return meta.getPersistentDataContainer().get(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), PersistentDataType.LONG_ARRAY);
    }

    public static Float getFloat(ItemStack item, String key) {
        if (item == null || item.getType() == Material.AIR) return null;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return null;
        return meta.getPersistentDataContainer().get(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), PersistentDataType.FLOAT);
    }

    public static Boolean getBoolean(ItemStack item, String key) {
        if (item == null || item.getType() == Material.AIR) return null;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return null;
        return meta.getPersistentDataContainer().get(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), PersistentDataType.BOOLEAN);
    }

    public static Byte getByte(ItemStack item, String key) {
        if (item == null || item.getType() == Material.AIR) return null;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return null;
        return meta.getPersistentDataContainer().get(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), PersistentDataType.BYTE);
    }

    public static byte[] getByteArray(ItemStack item, String key) {
        if (item == null || item.getType() == Material.AIR) return null;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return null;
        return meta.getPersistentDataContainer().get(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), PersistentDataType.BYTE_ARRAY);
    }

    public static Short getShort(ItemStack item, String key) {
        if (item == null || item.getType() == Material.AIR) return null;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return null;
        return meta.getPersistentDataContainer().get(Objects.requireNonNull(NamespacedKey.fromString(key, GXUtils.getInstance())), PersistentDataType.SHORT);
    }

}
