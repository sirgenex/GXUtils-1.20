package br.com.srgenex.utils.inventory;

import br.com.srgenex.utils.serializer.InventorySerializer;
import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("unused")
@Data
public class ConfigurationInventory {

    private final Inventory inventory;
    private final ItemStack helmet, chestplate, leggings, boots;

    public ConfigurationInventory(Inventory inventory, ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots){
        this.inventory = inventory;
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }

    public ConfigurationInventory(FileConfiguration c, String path){
        if(c.getString(path+".contents") == null) {
            this.inventory = null;
            this.helmet = null;
            this.chestplate = null;
            this.leggings = null;
            this.boots = null;
            return;
        }
        this.inventory = InventorySerializer.deserialize(c.getString(path+".contents"));
        this.helmet = c.getItemStack(path+".helmet");
        this.chestplate = c.getItemStack(path+".chestplate");
        this.leggings = c.getItemStack(path+".leggings");
        this.boots = c.getItemStack(path+".boots");
    }

    public ConfigurationInventory(){
        this.inventory = null;
        this.helmet = null;
        this.chestplate = null;
        this.leggings = null;
        this.boots = null;
    }

    public void set(Player p){
        if(inventory == null) return;
        int slot = 0;
        for (ItemStack content : inventory.getContents()) {
            if(p.getInventory().getSize() >= slot) p.getInventory().setItem(slot, content);
            slot++;
        }
        p.getInventory().setHelmet(helmet);
        p.getInventory().setChestplate(chestplate);
        p.getInventory().setLeggings(leggings);
        p.getInventory().setBoots(boots);
        p.getInventory().forEach(item -> {
            if(item != null){
                ItemMeta meta = item.getItemMeta();
                if(meta != null) {
                    meta.setUnbreakable(true);
                    meta.addItemFlags(ItemFlag.values());
                    item.setItemMeta(meta);
                }
            }
        });
        p.updateInventory();
    }

    public static ConfigurationInventory create(String path, Player p, FileConfiguration c){
        ItemStack helmet = p.getInventory().getHelmet();
        ItemStack chestplate = p.getInventory().getChestplate();
        ItemStack leggings = p.getInventory().getLeggings();
        ItemStack boots = p.getInventory().getBoots();
        c.set(path+".contents", InventorySerializer.serialize(p.getInventory()));
        c.set(path+".helmet", helmet);
        c.set(path+".chestplate", chestplate);
        c.set(path+".leggings", leggings);
        c.set(path+".boots", boots);
        return new ConfigurationInventory(p.getInventory(), helmet, chestplate, leggings, boots);
    }

}
