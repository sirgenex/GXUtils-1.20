package br.com.srgenex.utils.inventory.container;

import br.com.srgenex.utils.inventory.container.holder.ContainerHolder;
import br.com.srgenex.utils.inventory.container.icon.Icon;
import br.com.srgenex.utils.inventory.container.size.Size;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

@SuppressWarnings("unused")
@Getter
public abstract class Container {

    private final ContainerHolder holder;

    private final String name;
    private final Size size;

    private final Icon[] icons;
    private Consumer<InventoryClickEvent> consumer = null;
    @Setter private Consumer<InventoryCloseEvent> closeConsumer = null;
    @Setter private boolean closeable = true;
    @Setter private Container nextContainer;

    public Container(String name, Size size) {
        this.name = name;
        this.size = size;
        holder = new ContainerHolder(this);
        this.icons = new Icon[size.getAmount()];
    }

    public void handle(Consumer<InventoryClickEvent> consumer){
        this.consumer = consumer;
    }

    public void open(Player player) {
        player.openInventory(holder.getInventory());
    }

    public void update() {
        for (Icon icon : icons) if(icon != null) icon.update(holder.getInventory());
    }

    public void put(int slot, Icon icon) {
        if(slot < icons.length) icons[slot] = icon;
    }

    public Icon with(int slot) {
        return slot < icons.length ? icons[slot] : null;
    }

    public void decorate(ItemStack item){
        for(int slot = 0 ; slot < size.getAmount() ; slot++)
            holder.getInventory().setItem(slot, item);
    }

}
