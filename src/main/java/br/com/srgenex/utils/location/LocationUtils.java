package br.com.srgenex.utils.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class LocationUtils {

    public static String toString(Location loc) {
        String world = Objects.requireNonNull(loc.getWorld()).getName();
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        float yaw = loc.getYaw();
        float pitch = loc.getPitch();
        return "world="+world + ";x=" + x + ";y=" + y + ";z=" + z + ";yaw=" + (int)yaw + ";pitch=" + (int)pitch;
    }

    public static String toBeatifulString(Location loc, boolean w){
        String world = Objects.requireNonNull(loc.getWorld()).getName();
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        StringBuilder sb = new StringBuilder();
        sb.append("X: ").append(x).append(" ");
        sb.append("Y: ").append(y).append(" ");
        sb.append("Z: ").append(z).append(" ");
        if(w) sb.append("(").append(world).append(")");
        return sb.toString().trim();
    }

    public static String toBeatifulString(Location loc){
        return toBeatifulString(loc, false);
    }


    public static Location valueOf(String loc) {
        if(loc == null) return Bukkit.getWorlds().getFirst().getSpawnLocation();
        if(loc.split(";").length < 6) return Bukkit.getWorlds().getFirst().getSpawnLocation();
        World world = Bukkit.getWorld(loc.split(";")[0].split("=")[1]);
        int x = Integer.parseInt(loc.split(";")[1].split("=")[1]);
        int y = Integer.parseInt(loc.split(";")[2].split("=")[1]);
        int z = Integer.parseInt(loc.split(";")[3].split("=")[1]);
        float yaw = Float.parseFloat(loc.split(";")[4].split("=")[1]);
        float pitch = Float.parseFloat(loc.split(";")[5].split("=")[1]);
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static List<Location> getRadius(Location l, int radius) {
        List<Location> list = new ArrayList<>();
        for (int x = l.getBlockX() - radius; x <= l.getBlockX() + radius; x++)
            for (int y = l.getBlockY() - radius; y <= l.getBlockY() + radius; y++)
                for (int z = l.getBlockZ() - radius; z <= l.getBlockZ() + radius; z++)
                    list.add(new Location(l.getWorld(), x, y, z));
        return list;
    }

    public static List<Location> find(Location start, Location end) {
        List<Location> locations = new ArrayList<>();
        int topBlockX = Math.max(start.getBlockX(), end.getBlockX());
        int bottomBlockX = Math.min(start.getBlockX(), end.getBlockX());
        int topBlockY = Math.max(start.getBlockY(), end.getBlockY());
        int bottomBlockY = Math.min(start.getBlockY(), end.getBlockY());
        int topBlockZ = Math.max(start.getBlockZ(), end.getBlockZ());
        int bottomBlockZ = Math.min(start.getBlockZ(), end.getBlockZ());
        for(int x = bottomBlockX; x <= topBlockX; x++)
            for(int z = bottomBlockZ; z <= topBlockZ; z++)
                for(int y = bottomBlockY; y <= topBlockY; y++)
                    locations.add(start.getWorld().getBlockAt(x, y, z).getLocation());
        return locations;
    }

    public static List<Location> getCube(Location center, int radius){
        return find(center.clone().add(radius, radius, radius), center.clone().subtract(radius, radius, radius));
    }

    public Location getBlocksAway(Location location, int amount) {
        Vector dir = location.getDirection().normalize().multiply(amount);
        return location.add(dir);
    }

    public static Location getCenter(Location loc) {
        return new Location(loc.getWorld(),
                getRelativeCoordinate(loc.getBlockX()),
                getRelativeCoordinate(loc.getBlockY()),
                getRelativeCoordinate(loc.getBlockZ()));
    }

    private static double getRelativeCoordinate(int i) {
        double d = i;
        d = d < 0 ? d - .5 : d + .5;
        return d;
    }

}
