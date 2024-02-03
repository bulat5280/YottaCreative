package ua.govnojon.libs.bukkitutil;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public class LocationUtil {
   private static Random random = new Random();

   public static Location fixNan(Location loc) {
      if (Double.isNaN(loc.getX())) {
         loc.setX(0.0D);
      }

      if (Double.isNaN(loc.getY())) {
         loc.setY(0.0D);
      }

      if (Double.isNaN(loc.getZ())) {
         loc.setZ(0.0D);
      }

      if (Float.isNaN(loc.getYaw())) {
         loc.setYaw(0.0F);
      }

      if (Float.isNaN(loc.getPitch())) {
         loc.setPitch(0.0F);
      }

      return loc;
   }

   public static <T> T min(Location to, Collection<T> list, Function<T, Location> function) {
      return list.stream().filter((o) -> {
         return ((Location)function.apply(o)).getWorld().equals(to.getWorld());
      }).min(Comparator.comparingDouble((o) -> {
         return to.distanceSquared((Location)function.apply(o));
      })).orElse((Object)null);
   }

   public static <T> T max(Location to, List<T> list, Function<T, Location> function) {
      return list.stream().filter((o) -> {
         return ((Location)function.apply(o)).getWorld().equals(to.getWorld());
      }).max(Comparator.comparingDouble((o) -> {
         return to.distanceSquared((Location)function.apply(o));
      })).orElse((Object)null);
   }

   public static boolean isInChunk(Location loc, Chunk chunk) {
      return chunk.getX() == loc.getBlockX() >> 4 && chunk.getZ() == loc.getBlockZ() >> 4;
   }

   public static Location getDefaultLocation(World world) {
      return new Location(world, 0.0D, 100.0D, 0.0D);
   }

   public static Location getDefaultLocation() {
      return getDefaultLocation((World)Bukkit.getWorlds().get(0));
   }

   public static Location addRandomOffset(Location location) {
      return location.clone().add((double)(random.nextInt(3000) - 1500) / 1000.0D, 0.0D, (double)(random.nextInt(3000) - 1500) / 1000.0D);
   }

   public static BlockFace getDirectionFace(float yaw) {
      yaw = fixAngle(yaw);
      if (yaw >= 225.0F && yaw <= 315.0F) {
         return BlockFace.EAST;
      } else if (yaw >= 315.0F && yaw <= 360.0F || yaw >= 0.0F && yaw <= 45.0F) {
         return BlockFace.SOUTH;
      } else if (yaw >= 45.0F && yaw <= 135.0F) {
         return BlockFace.WEST;
      } else if (yaw >= 135.0F && yaw <= 225.0F) {
         return BlockFace.NORTH;
      } else {
         throw new AssertionError();
      }
   }

   private static float fixAngle(float angle) {
      while(angle < 0.0F) {
         angle += 360.0F;
      }

      while(angle >= 360.0F) {
         angle -= 360.0F;
      }

      return angle;
   }

   public static Location addRandomOffset(int radius, Location location) {
      return location.clone().add((double)(random.nextInt(radius * 1000) - radius * 1000 / 2) / 1000.0D, 0.0D, (double)((random.nextInt(radius * 1000) - radius * 1000 / 2) / 1000));
   }

   public static float getPitchForByteStandingSign(byte bytes) {
      switch(bytes) {
      case 0:
         return 0.0F;
      case 1:
         return 22.5F;
      case 2:
         return 45.0F;
      case 3:
         return 67.5F;
      case 4:
         return 90.0F;
      case 5:
         return 112.5F;
      case 6:
         return 135.0F;
      case 7:
         return 157.5F;
      case 8:
         return 180.0F;
      case 9:
         return -157.5F;
      case 10:
         return -135.0F;
      case 11:
         return -112.5F;
      case 12:
         return -90.0F;
      case 13:
         return -67.5F;
      case 14:
         return -45.0F;
      case 15:
         return -22.5F;
      default:
         return 0.0F;
      }
   }

   public static float getPitchForByteWallSign(byte bytes) {
      switch(bytes) {
      case 2:
         return 180.0F;
      case 3:
         return 0.0F;
      case 4:
         return 90.0F;
      case 5:
         return -90.0F;
      default:
         return 0.0F;
      }
   }

   public static void setLocation(Location position, Location location) {
      position.setWorld(location.getWorld());
      position.setX(location.getX());
      position.setY(location.getY());
      position.setZ(location.getZ());
      position.setYaw(location.getYaw());
      position.setPitch(location.getPitch());
   }

   public static Location floorBlock(Location loc) {
      loc.setX((double)loc.getBlockX());
      loc.setY((double)loc.getBlockY());
      loc.setZ((double)loc.getBlockZ());
      loc.setPitch(0.0F);
      loc.setYaw(0.0F);
      return loc;
   }

   public static Location floorCenterBlock(Location loc) {
      return floorCenterBlock(loc, true);
   }

   public static Location floorCenterBlock(Location loc, boolean resetLook) {
      loc.setX((double)loc.getBlockX() + 0.5D);
      loc.setY((double)loc.getBlockY());
      loc.setZ((double)loc.getBlockZ() + 0.5D);
      if (resetLook) {
         loc.setPitch(0.0F);
         loc.setYaw(0.0F);
      }

      return loc;
   }

   public static boolean isInOneChunk(Location loc1, Location loc2) {
      return loc1.getBlockX() >> 4 == loc2.getBlockX() >> 4 && loc1.getBlockZ() >> 4 == loc2.getBlockZ() >> 4;
   }

   public static String toString(Location loc) {
      return loc.getWorld().getName() + " " + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
   }

   public static boolean isInOneBlock(Location loc1, Location loc2) {
      return loc1.getWorld() == loc2.getWorld() && loc1.getBlockX() == loc2.getBlockX() && loc1.getBlockY() == loc2.getBlockY() && loc1.getBlockZ() == loc2.getBlockZ();
   }

   public static boolean hasDistance(Location to, Location from, double distance) {
      return !to.getWorld().equals(from.getWorld()) || Math.abs(to.getX() - from.getX()) >= distance || Math.abs(to.getY() - from.getY()) >= distance || Math.abs(to.getZ() - from.getZ()) >= distance;
   }

   public static boolean hasDistance(Location to, double x, double y, double z, double distance) {
      return Math.abs(to.getX() - x) >= distance || Math.abs(to.getY() - y) >= distance || Math.abs(to.getZ() - z) >= distance;
   }

   public static boolean hasDistance2D(double x1, double z1, double x2, double z2, double distance) {
      return Math.abs(x1 - x2) >= distance || Math.abs(z1 - z2) >= distance;
   }

   public static Vector fixVelocityVector(Vector vector) {
      double max = Math.max(Math.abs(vector.getX()), Math.max(Math.abs(vector.getY()), Math.abs(vector.getZ())));
      if (max > 4.0D) {
         vector.multiply(4.0D / max);
      }

      return vector;
   }

   public static double getDistance2D(Location loc, double x, double z) {
      double xD = loc.getX() - x;
      double zD = loc.getZ() - z;
      return Math.sqrt(xD * xD + zD * zD);
   }

   public static double getDistance2D(double x1, double z1, double x2, double z2) {
      double xD = x1 - x2;
      double zD = z1 - z2;
      return Math.sqrt(xD * xD + zD * zD);
   }

   public static boolean hasDistance(double x1, double y1, double z1, double x2, double y2, double z2, double distance) {
      return Math.abs(x1 - x2) >= distance || Math.abs(y1 - y2) >= distance || Math.abs(z1 - z2) >= distance;
   }
}
