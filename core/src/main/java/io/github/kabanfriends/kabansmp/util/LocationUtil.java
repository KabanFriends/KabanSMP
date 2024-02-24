package io.github.kabanfriends.kabansmp.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Logic from: https://github.com/EssentialsX/Essentials/blob/2.x/Essentials/src/main/java/com/earth2me/essentials/utils/LocationUtil.java
public class LocationUtil {

    public static int RADIUS = 3;
    public static Vector3D[] VOLUME;
    private static Set<Material> BEDS = Set.of(
            Material.WHITE_BED,
            Material.ORANGE_BED,
            Material.MAGENTA_BED,
            Material.LIGHT_BLUE_BED,
            Material.YELLOW_BED,
            Material.LIME_BED,
            Material.PINK_BED,
            Material.GRAY_BED,
            Material.LIGHT_GRAY_BED,
            Material.CYAN_BED,
            Material.PURPLE_BED,
            Material.BLUE_BED,
            Material.BROWN_BED,
            Material.GREEN_BED,
            Material.RED_BED,
            Material.BLACK_BED
    );
    // Water types used for TRANSPARENT_MATERIALS and is-water-safe config option
    private static Set<Material> WATER_TYPES = Set.of(
            Material.WATER
    );
    // Types checked by isBlockDamaging
    private static Set<Material> DAMAGING_TYPES = Set.of(
            Material.CACTUS,
            Material.CAMPFIRE,
            Material.FIRE,
            Material.MAGMA_BLOCK,
            Material.SOUL_CAMPFIRE,
            Material.SOUL_FIRE,
            Material.SWEET_BERRIES,
            Material.WITHER_ROSE
    );
    private static Set<Material> LAVA_TYPES = Set.of(
            Material.LAVA
    );
    private static Material PORTAL = Material.NETHER_PORTAL;
    private static Material LIGHT = Material.LIGHT;

    private static Material PATH = Material.DIRT_PATH;

    private static Material FARMLAND = Material.FARMLAND;

    // The player can stand inside these materials
    private static Set<Material> HOLLOW_MATERIALS = new HashSet<>();
    private static Set<Material> TRANSPARENT_MATERIALS = new HashSet<>();

    static {
        // Materials from Material.isTransparent()
        for (Material mat : Material.values()) {
            if (mat.isTransparent()) {
                HOLLOW_MATERIALS.add(mat);
            }
        }

        TRANSPARENT_MATERIALS.addAll(HOLLOW_MATERIALS);
        TRANSPARENT_MATERIALS.addAll(WATER_TYPES);

        // Barrier is transparent, but solid
        HOLLOW_MATERIALS.remove(Material.BARRIER);

        // Path and farmland are transparent, but solid
        HOLLOW_MATERIALS.remove(PATH);
        HOLLOW_MATERIALS.remove(FARMLAND);

        // Light blocks can be passed through and are not considered transparent for some reason
        HOLLOW_MATERIALS.add(LIGHT);
    }

    static {
        List<Vector3D> pos = new ArrayList<>();
        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int y = -RADIUS; y <= RADIUS; y++) {
                for (int z = -RADIUS; z <= RADIUS; z++) {
                    pos.add(new Vector3D(x, y, z));
                }
            }
        }
        pos.sort(Comparator.comparingInt(a -> a.x * a.x + a.y * a.y + a.z * a.z));
        VOLUME = pos.toArray(new Vector3D[0]);
    }


    public static @Nullable Location getSafeDestination(Location loc) {
        World world = loc.getWorld();
        int worldMinY = world.getMinHeight();
        int worldLogicalY = world.getLogicalHeight();
        int worldMaxY = loc.getBlockY() < worldLogicalY ? worldLogicalY : world.getMaxHeight();
        int x = loc.getBlockX();
        int y = (int) Math.round(loc.getY());
        int z = loc.getBlockZ();
        if (isBlockOutsideWorldBorder(world, x, z)) {
            x = getXInsideWorldBorder(world, x);
            z = getZInsideWorldBorder(world, z);
        }
        int origX = x;
        int origY = y;
        int origZ = z;
        while (isBlockAboveAir(world, x, y, z)) {
            y -= 1;
            if (y < 0) {
                y = origY;
                break;
            }
        }
        if (isBlockUnsafe(world, x, y, z)) {
            x = Math.round(loc.getX()) == origX ? x - 1 : x + 1;
            z = Math.round(loc.getZ()) == origZ ? z - 1 : z + 1;
        }
        int i = 0;
        while (isBlockUnsafe(world, x, y, z)) {
            i++;
            if (i >= VOLUME.length) {
                x = origX;
                y = constrainToRange(origY + RADIUS, worldMinY, worldMaxY);
                z = origZ;
                break;
            }
            x = origX + VOLUME[i].x;
            y = constrainToRange(origY + VOLUME[i].y, worldMinY, worldMaxY);
            z = origZ + VOLUME[i].z;
        }
        while (isBlockUnsafe(world, x, y, z)) {
            y += 1;
            if (y >= worldMaxY) {
                x += 1;
                break;
            }
        }
        while (isBlockUnsafe(world, x, y, z)) {
            y -= 1;
            if (y <= worldMinY + 1) {
                x += 1;
                // Allow spawning at the top of the world, but not above the nether roof
                y = Math.min(world.getHighestBlockYAt(x, z) + 1, worldMaxY);
                if (x - 48 > loc.getBlockX()) {
                    return null;
                }
            }
        }
        return new Location(world, x + 0.5, y, z + 0.5, loc.getYaw(), loc.getPitch());
    }

    private static boolean isBlockOutsideWorldBorder(World world, int x, int z) {
        Location center = world.getWorldBorder().getCenter();
        int radius = (int) world.getWorldBorder().getSize() / 2;
        int x1 = center.getBlockX() - radius, x2 = center.getBlockX() + radius;
        int z1 = center.getBlockZ() - radius, z2 = center.getBlockZ() + radius;
        return x < x1 || x > x2 || z < z1 || z > z2;
    }

    private static int getXInsideWorldBorder(World world, int x) {
        Location center = world.getWorldBorder().getCenter();
        int radius = (int) world.getWorldBorder().getSize() / 2;
        int x1 = center.getBlockX() - radius, x2 = center.getBlockX() + radius;
        if (x < x1) {
            return x1;
        } else if (x > x2) {
            return x2;
        }
        return x;
    }

    private static int getZInsideWorldBorder(World world, int z) {
        Location center = world.getWorldBorder().getCenter();
        int radius = (int) world.getWorldBorder().getSize() / 2;
        int z1 = center.getBlockZ() - radius, z2 = center.getBlockZ() + radius;
        if (z < z1) {
            return z1;
        } else if (z > z2) {
            return z2;
        }
        return z;
    }

    private static boolean isBlockUnsafe(World world, int x, int y, int z) {
        return isBlockDamaging(world, x, y, z) || isBlockAboveAir(world, x, y, z);
    }

    private static boolean isBlockDamaging(World world, int x, int y, int z) {
        Material block = world.getBlockAt(x, y, z).getType();
        Material below = world.getBlockAt(x, y - 1, z).getType();
        Material above = world.getBlockAt(x, y + 1, z).getType();

        if (DAMAGING_TYPES.contains(below) || LAVA_TYPES.contains(below) || BEDS.contains(below)) {
            return true;
        }

        if (block == PORTAL) {
            return true;
        }

        return !HOLLOW_MATERIALS.contains(block) || !HOLLOW_MATERIALS.contains(above);
    }

    private static boolean isBlockAboveAir(World world, int x, int y, int z) {
        return y > world.getMaxHeight() || HOLLOW_MATERIALS.contains(world.getBlockAt(x, y - 1, z).getType());
    }

    private static int constrainToRange(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

    private static class Vector3D {
        public int x;
        public int y;
        public int z;

        Vector3D(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
