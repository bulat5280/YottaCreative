package net.mineland.core.bukkit.modules.region.region.territory.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import net.mineland.core.bukkit.modules.nms.NMS;
import net.mineland.core.bukkit.modules.region.region.territory.Point;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.NumberConversions;

public class TerritoryHeplerCube implements TerritoryHepler {
   public boolean isContains(Point[] points, Location pos) {
      try {
         if (points != null && points.length == 2 && pos != null) {
            Point min = points[0];
            Point max = points[1];
            return min.getWorld().equals(pos.getWorld()) && pos.getBlockX() >= min.getX() && pos.getBlockY() >= min.getY() && pos.getBlockZ() >= min.getZ() && pos.getBlockX() <= max.getX() && pos.getBlockY() <= max.getY() && pos.getBlockZ() <= max.getZ();
         } else {
            return false;
         }
      } catch (NullPointerException var5) {
         return false;
      }
   }

   public boolean isContains(Point[] points, Chunk chunk) {
      try {
         if (points != null && points.length == 2 && chunk != null) {
            Point min = points[0];
            Point max = points[1];
            return min.getWorld().equals(chunk.getWorld()) && chunk.getX() >= NumberConversions.floor((double)(min.getX() >> 4)) && chunk.getZ() >= NumberConversions.floor((double)(min.getZ() >> 4)) && chunk.getX() <= NumberConversions.floor((double)(max.getX() >> 4)) && chunk.getZ() <= NumberConversions.floor((double)(max.getZ() >> 4));
         } else {
            return false;
         }
      } catch (NullPointerException var5) {
         return false;
      }
   }

   public void fix(Point[] points) {
      Point min = points[0];
      Point max = points[1];
      int x1 = min.getX();
      int x2 = max.getX();
      int y1 = min.getY();
      int y2 = max.getY();
      int z1 = min.getZ();
      int z2 = max.getZ();
      min.setX(Math.min(x1, x2));
      min.setY(Math.min(y1, y2));
      min.setZ(Math.min(z1, z2));
      max.setX(Math.max(x1, x2));
      max.setY(Math.max(y1, y2));
      max.setZ(Math.max(z1, z2));
   }

   public Collection<Entity> getEntity(Point[] points) {
      Point min = points[0];
      Point max = points[1];
      return NMS.getManagerSingle().getNearbyEntities(min.toLocation(), max.toLocation());
   }

   public void setBiome(Point[] points, Biome biome) {
      Point min = points[0];
      Point max = points[1];
      int minX = min.getX();
      int minZ = min.getZ();
      int maxX = max.getX();
      int maxZ = max.getZ();
      World world = min.getWorld();

      for(int x = minX; x <= maxX; ++x) {
         for(int z = minZ; z <= maxZ; ++z) {
            world.setBiome(x, z, biome);
         }
      }

   }

   public List<int[]> getCoordChunks(Point[] points) {
      List<int[]> list = new LinkedList();
      Point min = points[0];
      Point max = points[1];
      int minX = min.getX();
      int minZ = min.getZ();
      int maxX = max.getX();
      int maxZ = max.getZ();
      int maxChunkX = maxX >> 4;
      int maxChunkZ = maxZ >> 4;

      for(int chunkX = minX >> 4; chunkX <= maxChunkX; ++chunkX) {
         for(int chunkZ = minZ >> 4; chunkZ <= maxChunkZ; ++chunkZ) {
            list.add(new int[]{chunkX, chunkZ});
         }
      }

      return list;
   }

   public int getBlocksCount(Point[] points) {
      Point centerTop = points[0];
      Point leftDown = points[1];
      int x = centerTop.getX() - leftDown.getX();
      int y = centerTop.getY() - leftDown.getY();
      int z = centerTop.getZ() - leftDown.getZ();
      return x * y * z;
   }

   public List<Block> getBlocks(Point[] points) {
      List<Block> list = new ArrayList(this.getBlocksCount(points));
      Point min = points[0];
      Point max = points[1];
      World world = points[0].getWorld();

      for(int y = min.getX(); y <= max.getX(); ++y) {
         for(int x = min.getX(); x <= max.getX(); ++x) {
            for(int z = min.getZ(); z <= max.getZ(); ++z) {
               list.add(world.getBlockAt(x, y, z));
            }
         }
      }

      return list;
   }
}
