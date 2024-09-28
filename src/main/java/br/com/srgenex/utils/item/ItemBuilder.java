package br.com.srgenex.utils.item;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Getter
@Setter
@SuppressWarnings({"unused", "deprecation"})
public class ItemBuilder implements Cloneable {
    public ItemStack item;
    public int slot;

    public ItemBuilder(ItemStack item) {
        this.item = item.clone();
    }

    public ItemBuilder(Material type) {
        this(new ItemStack(type));
    }

    public ItemBuilder(Material type, int quantity, short data) {
        this(new ItemStack(type, quantity, data));
    }

    public ItemBuilder(String name) {

        item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(name);

        item.setItemMeta(meta);
    }

    public ItemBuilder(FileConfiguration c, String path, String... replacements){
        String type = c.getString(path+".item", "BEDROCK");
        Material material = Material.getMaterial(type);
        int data = c.getInt(path+".data", 0);
        assert material != null;
        item = new ItemStack(material, 1, (short)data);
        if(c.getString(path+".color") != null) {
            java.awt.Color color = java.awt.Color.decode(Objects.requireNonNull(c.getString(path + ".color")));
            color(Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue()));
        }
        String name = c.getString(path+".name");
        for (String replacement : replacements) {
            String previous = replacement.split(";")[0];
            String next = replacement.split(";")[1];
            assert name != null;
            name = name.replace(previous, next);
        }
        List<String> lore = null;
        if(c.getString(path+".lore") != null) {
            List<String> list = new ArrayList<>();
            c.getStringList(path + ".lore").forEach(line ->{
                for (String replacement : replacements) {
                    String previous = replacement.split(";")[0];
                    String next = replacement.split(";")[1];
                    line = line.replace(previous, next);
                }
                list.add(line.replace("&", "§"));
            });
            lore = list;
        }
        if(name != null) name(name);
        if(lore != null) setLore(lore);
        if(c.getString(path+".slot") != null)
            setSlot(c.getInt(path+".slot"));
        String skullUrl = c.getString(path+".skull-value");
        String skull = c.getString(path+".skull");
        if(skull != null)
            head(skull);
        if(skullUrl != null)
            texture(skullUrl);
        if(c.getString(path+".enchantments") != null)
            c.getStringList(path+".enchantments").forEach(s -> {
                Enchantment enchantment =Enchantment.getByName(s.split(";")[0].toUpperCase());
                int level = Integer.parseInt(s.split(";")[1]);
                assert enchantment != null;
                item.addUnsafeEnchantment(enchantment, level);
            });
        if(c.getString(path+".flag") != null)
            c.getStringList(path + ".flag").forEach(s -> {
                if (s.equalsIgnoreCase("all")) addFlag(ItemFlag.values());
                else addFlag(ItemFlag.valueOf(s.toUpperCase()));
            });
        if(c.getBoolean(path+".glow")){
            addFlag(ItemFlag.HIDE_ENCHANTS);
            enchant(Enchantment.PROTECTION_PROJECTILE, 1);
        }
        String customModel = c.getString(path + ".custom-model");
        if (customModel != null) customModel(Integer.parseInt(customModel));
    }

    public ItemBuilder(FileConfiguration c, String path, String player, boolean a, String... replacements){
        this(c, path, replacements);
        if(item.getType().equals(Material.PLAYER_HEAD))
            head(player);
    }

    public ItemBuilder addTag(String key, String value){
        item = NBTUtils.setString(item, key, value);
        return this;
    }

    public ItemBuilder addTag(String key, Integer value){
        item = NBTUtils.setInteger(item, key, value);
        return this;
    }

    public ItemBuilder addTag(String key, Double value){
        item = NBTUtils.setDouble(item, key, value);
        return this;
    }

    public ItemBuilder customModel(Integer data) {
        changeItemMeta(meta -> meta.setCustomModelData(data));
        return this;
    }

    public ItemBuilder changeItemMeta(Consumer<ItemMeta> consumer) {
        ItemMeta itemMeta = item.getItemMeta();
        consumer.accept(itemMeta);
        item.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder changeItem(Consumer<ItemStack> consumer) {
        consumer.accept(item);
        return this;
    }

    public ItemBuilder name(String name, String... replacements) {
        if (name == null) System.out.println("Tried to update a name that is null");
        for (String replacement : replacements) {
            try {
                String previous = replacement.split(", ")[0];
                String next = replacement.split(", ")[1];
                assert name != null;
                name = name.replace(previous, next.replace("null", ""));
            } catch (Exception ignored) {
            }
        }
        String finalName = name;
        return changeItemMeta(it -> it.setDisplayName(colored(finalName)));
    }

    public ItemBuilder name(List<String> name, String... replacements){
        AtomicReference<String> s = new AtomicReference<>();
        name.forEach(s::set);
        return name(s.get(), replacements);
    }

    public ItemBuilder color(Color color){
        return changeItem(item -> {
            LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
            meta.setColor(color);
            item.setItemMeta(meta);
        });
    }

    public ItemBuilder setSlot(int slot){
        this.slot = slot;
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        return changeItemMeta(it -> it.setLore(Arrays.asList(colored(lore))));
    }

    public ItemBuilder setAmount(int amount){
        return changeItem(i -> i.setAmount(amount));
    }

    public ItemBuilder setLore(List<String> lore, String... replacements) {
        return changeItemMeta(it -> it.setLore(null)).addLore(lore, replacements);
    }

    public ItemBuilder addLore(List<String> lore, String... replacements) {
        if (lore == null || lore.isEmpty()) return this;
        List<String> list = new ArrayList<>();
        lore.forEach(line -> {
            for (String replacement : replacements) {
                try {
                    String previous = replacement.split(", ")[0];
                    String next = replacement.split(", ")[1];
                    line = line.replace(previous, next.replace("null", ""));
                }catch(Exception ignored){}
            }
            list.add(line);
        });
        return changeItemMeta(meta -> {
            List<String> originalLore = meta.getLore() == null ? Lists.newArrayList() : meta.getLore();
            originalLore.addAll(list);
            meta.setLore(colored(originalLore));
        });
    }

    public void texture(String target) {
        changeItem(item -> {
            item.setType(Material.PLAYER_HEAD);
            item.setDurability((short) 3);
            ItemMeta meta = item.getItemMeta();
            final SkullMeta skullMeta = (SkullMeta) meta;

            final GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);

            gameProfile.getProperties().put("textures", new Property("textures", target));

            try {
                final Field field = skullMeta.getClass().getDeclaredField("profile");
                field.setAccessible(true);
                field.set(skullMeta, gameProfile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            item.setItemMeta(meta);
        });
    }

    public ItemBuilder head(String player) {
        return changeItem(item -> {
            item.setType(Material.PLAYER_HEAD);
            item.setDurability((short)3);
            SkullMeta meta = (SkullMeta)item.getItemMeta();
            meta.setOwner(player);
            item.setItemMeta(meta);
        });
    }

    public void enchant(Enchantment enchantment, int level) {
        changeItemMeta(itemMeta -> itemMeta.addEnchant(enchantment, level, true));
    }

    public ItemBuilder glow() {
        enchant(Enchantment.LUCK, 1);
        return changeItemMeta(itemMeta -> itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS));
    }

    public ItemBuilder glowIf(Boolean condition) {
        return condition ? glow() : this;
    }

    public ItemBuilder unbreakable() {
        return changeItemMeta(itemMeta -> itemMeta.setUnbreakable(true));
    }

    public ItemBuilder addFlag(ItemFlag... itemFlag) {
        return changeItemMeta(itemMeta -> itemMeta.addItemFlags(itemFlag));
    }

    public ItemStack wrap() {
        return item;
    }

    public ItemStack wrap(String... replacement) {
        if(getItem().hasItemMeta()) {
            String name = getItem().getItemMeta().getDisplayName();
            if (name != null) name(name, replacement);
            List<String> lore = getItem().getItemMeta().getLore();
            if (lore != null) setLore(lore, replacement);
        }
        return item;
    }

    private static String colored(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private static String[] colored(String... messages) {
        for (int i = 0; i < messages.length; i++) {
            messages[i] = colored(messages[i]);
        }

        return messages;
    }

    private static List<String> colored(List<String> description) {

        return description.stream()
                .map(ItemBuilder::colored)
                .collect(Collectors.toList());

    }

    @Override
    public ItemBuilder clone() {
        try {
            ItemBuilder clone = (ItemBuilder) super.clone();
            clone.setItem(this.item.clone());
            clone.setSlot(this.slot);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}