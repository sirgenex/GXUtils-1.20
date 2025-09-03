package br.com.srgenex.utils.player;

import br.com.srgenex.utils.enums.Lang;
import org.bukkit.Bukkit;

import java.util.Objects;
import java.util.UUID;

public class PlayerUtils {

    public static String getName(UUID uniqueId) {
        if(uniqueId == null) return Lang.DAY.getCapitalized();
        return Bukkit.getPlayer(uniqueId) == null ? Bukkit.getOfflinePlayer(uniqueId).getName() : Objects.requireNonNull(Bukkit.getPlayer(uniqueId)).getName();
    }

}
