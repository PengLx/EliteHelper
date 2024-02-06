package com.chsteam.penglx;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Nearby {

    public static List<Player> getNearPlayer(Location loc, double radius) {
        List<Player> result = new ArrayList<Player>();

        int minX = (int) (loc.getX() - radius) >> 4;
        int maxX = (int) (loc.getX() + radius) >> 4;
        int minZ = (int) (loc.getZ() - radius) >> 4;
        int maxZ = (int) (loc.getZ() + radius) >> 4;

        radius *= radius;

        for (int i = minX; i <= maxX; i++)
            for (int j = minZ; j <= maxZ; j++)
                for (Entity entity : loc.getWorld().getChunkAt(i, j).getEntities())
                    if (entity instanceof Player && entity.getWorld() == loc.getWorld() && entity.getLocation().distanceSquared(loc) < radius)
                        result.add((Player) entity);

        return result;
    }
}