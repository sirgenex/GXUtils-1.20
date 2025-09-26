package br.com.srgenex.utils.block;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BlockUtils {

    public static Set<Block> getConnectedBlocks(Location startLocation, int radius, Material material) {
        Set<Block> visited = new HashSet<>();
        Queue<Block> queue = new LinkedList<>();
        queue.add(startLocation.getBlock());

        while (!queue.isEmpty() && visited.size() < radius) {
            Block current = queue.poll();
            if (!visited.add(current)) continue;

            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (Math.abs(dx) + Math.abs(dz) != 1) continue;
                    Block neighbor = current.getWorld().getBlockAt(current.getX() + dx, current.getY(), current.getZ() + dz);
                    if (neighbor.getType() == material && !visited.contains(neighbor)) {
                        queue.add(neighbor);
                    }
                }
            }
        }
        return visited;
    }

}
