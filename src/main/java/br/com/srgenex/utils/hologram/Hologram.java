package br.com.srgenex.utils.hologram;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Hologram {

    private final List<Object> destroyCache;
    private final List<Object> spawnCache;
    private final List<UUID> players;
    private static final double ABS = 0.25D;
    private static final String version;
    private static Class<?> nmsEntity;
    private static Class<?> craftWorld;
    private static Class<?> packetClass;
    private static Class<?> entityLivingClass;
    private static Constructor<?> armorStandConstructor;
    private static Constructor<?> destroyPacketConstructor;
    private static Class<?> nmsPacket;

    static {
        String path = Bukkit.getServer().getClass().getPackage().getName();
        version = path.substring(path.lastIndexOf(".") + 1);
        try {
            Class<?> armorStand = Class.forName("net.minecraft.server." + version + ".EntityArmorStand");
            Class<?> worldClass = Class.forName("net.minecraft.server." + version + ".World");
            nmsEntity = Class.forName("net.minecraft.server." + version + ".Entity");
            craftWorld = Class.forName("org.bukkit.craftbukkit." + version + ".CraftWorld");
            packetClass = Class.forName("net.minecraft.server." + version + ".PacketPlayOutSpawnEntityLiving");
            entityLivingClass = Class.forName("net.minecraft.server." + version + ".EntityLiving");
            armorStandConstructor = armorStand.getConstructor(worldClass);
            Class<?> destroyPacketClass = Class.forName("net.minecraft.server." + version + ".PacketPlayOutEntityDestroy");
            destroyPacketConstructor = destroyPacketClass.getConstructor(int[].class);
            nmsPacket = Class.forName("net.minecraft.server." + version + ".Packet");
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException ex) {
            System.err.println("Error - Classes not initialized!");
            ex.printStackTrace();
        }
    }

    public Hologram(Location loc, ItemStack item, String... lines) {
        this(loc, Arrays.asList(lines), item);
    }

    public Hologram(Location loc, List<String> lines, ItemStack item) {
        this.players = new ArrayList<>();
        this.spawnCache = new ArrayList<>();
        this.destroyCache = new ArrayList<>();
        Location displayLoc = loc.clone().add(0, (ABS * lines.size()) - 1.97D, 0);
        for (String line : lines) {
            Object packet = this.getPacket(loc.getWorld(), displayLoc.getX()+0.5, displayLoc.getY(), displayLoc.getZ()+0.5, line.replace("&", "§"), item);
            if (!line.equals("")) this.spawnCache.add(packet);
            try {
                Field field = packetClass.getDeclaredField("a");
                field.setAccessible(true);
                this.destroyCache.add(this.getDestroyPacket((int) field.get(packet)));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            displayLoc.add(0, ABS * (-1), 0);
        }
    }

    public void display(Player p) {
        for (Object o : this.spawnCache) this.sendPacket(p, o);
        this.players.add(p.getUniqueId());
    }

    public void displayAll(){
        Bukkit.getOnlinePlayers().forEach(this::display);
    }

    public void destroy(Player p) {
        if (this.players.contains(p.getUniqueId())) {
            for (Object o : this.destroyCache) this.sendPacket(p, o);
            this.players.remove(p.getUniqueId());
        }
    }

    public void destroyAll() {
        Bukkit.getOnlinePlayers().forEach(this::destroy);
    }

    private Object getPacket(World w, double x, double y, double z, String text, ItemStack item) {
        try {
            Object craftWorldObj = craftWorld.cast(w);
            Method getHandleMethod = craftWorldObj.getClass().getMethod("getHandle");
            Object entityObject = armorStandConstructor.newInstance(getHandleMethod.invoke(craftWorldObj));
            Method setCustomName = entityObject.getClass().getMethod("setCustomName", String.class);
            setCustomName.invoke(entityObject, text);
            Method setItemInHand = entityObject.getClass().getMethod("setItemInHand", ItemStack.class);
            setItemInHand.invoke(entityObject, item);
            Method setGravity = entityObject.getClass().getMethod("setGravity", boolean.class);
            setGravity.invoke(entityObject, false);
            Method setCustomNameVisible = nmsEntity.getMethod("setCustomNameVisible", boolean.class);
            setCustomNameVisible.invoke(entityObject, true);
            Method setLocation = entityObject.getClass().getMethod("setLocation", double.class, double.class, double.class, float.class, float.class);
            setLocation.invoke(entityObject, x, y, z, 0.0F, 0.0F);
            Method setInvisible = entityObject.getClass().getMethod("setInvisible", boolean.class);
            setInvisible.invoke(entityObject, true);
            Constructor<?> cw = packetClass.getConstructor(entityLivingClass);
            return cw.newInstance(entityObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object getDestroyPacket(int... id) {
        try {
            return destroyPacketConstructor.newInstance(id);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendPacket(Player p, Object packet) {
        try {
            Method getHandle = p.getClass().getMethod("getHandle");
            Object entityPlayer = getHandle.invoke(p);
            Object pConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
            Method sendMethod = pConnection.getClass().getMethod("sendPacket", nmsPacket);
            sendMethod.invoke(pConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
