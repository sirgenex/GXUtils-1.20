package br.com.srgenex.utils.player;

import org.bukkit.Bukkit;

import java.util.Objects;
import java.util.UUID;

public class PlayerUtils {

    public static String getName(UUID uniqueId) {
        return Bukkit.getPlayer(uniqueId) == null ? Bukkit.getOfflinePlayer(uniqueId).getName() : Objects.requireNonNull(Bukkit.getPlayer(uniqueId)).getName();
    }

}
