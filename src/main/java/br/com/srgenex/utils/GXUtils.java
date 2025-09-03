package br.com.srgenex.utils;

import br.com.srgenex.utils.enums.Locale;
import br.com.srgenex.utils.inventory.listener.ToolingHandler;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

@SuppressWarnings("unused")
public class GXUtils {

    @Getter private static Plugin instance;
    @Getter @Setter private static Locale locale;

    public static void load(Plugin plugin){
        Bukkit.getPluginManager().registerEvents(new ToolingHandler(), plugin);
        instance = plugin;
        locale = Locale.PORTUGUESE;
    }

    public static void load(Plugin plugin, Locale locale){
        load(plugin);
        setLocale(locale);
    }

}
