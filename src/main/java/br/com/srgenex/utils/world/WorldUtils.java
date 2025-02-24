package br.com.srgenex.utils.world;

import org.bukkit.World;

public class WorldUtils {

    public static boolean isDay(World world) {
        return world.getTime() < 12300 || world.getTime() > 23850;
    }

}
