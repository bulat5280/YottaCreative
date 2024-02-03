package net.mineland.core.bukkit.modules.region.region.territory.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.mineland.core.bukkit.modules.region.region.territory.Point;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class TerritoryHeplerCyl implements TerritoryHepler {
   public boolean isContains(Point[] points, Location pos) {
      return this.isContains(points, pos.getWorld(), pos.getBlockX(), pos.getBlockY(), pos.getBlockZ());
   }

   public boolean isContains(Point[] points, Chunk chunk) {
      throw new UnsupportedOperationException();
   }

   private boolean isContains(Point[] points, World world, int x, int y, int z) {
      Point centerTop = points[0];
      Point leftDown = points[1];
      int radius = centerTop.getX() - leftDown.getX();
      if (world.equals(centerTop.getWorld()) && x >= centerTop.getX() - radius && x <= centerTop.getX() + radius && z >= centerTop.getZ() - radius && z <= centerTop.getZ() + radius && y >= leftDown.getY() && y <= centerTop.getY()) {
         int vecX = centerTop.getX() - x;
         int vecZ = centerTop.getZ() - z;
         return Math.sqrt((double)(vecX * vecX + vecZ * vecZ)) <= (double)radius;
      } else {
         return false;
      }
   }

   public void fix(Point[] points) {
      Point centerTop = points[0];
      Point leftDown = points[1];
      if (centerTop.getY() < leftDown.getY()) {
         Point temp = centerTop;
         centerTop = points[0] = leftDown;
         leftDown = points[1] = temp;
      }

      int x1 = centerTop.getX();
      int x2 = leftDown.getX();
      int z1 = centerTop.getZ();
      int z2 = leftDown.getZ();
      int vecX = x1 - x2;
      int vecZ = z1 - z2;
      int distance = (int)Math.sqrt((double)(vecX * vecX + vecZ * vecZ));
      leftDown.setX(centerTop.getX() - distance);
      leftDown.setZ(centerTop.getZ());
   }

   public Collection<Entity> getEntity(Point[] points) {
      Point centerTop = points[0];
      Point leftDown = points[1];
      int radiusHorizontally = centerTop.getX() - leftDown.getX();
      int radiusVertical = centerTop.getY() - leftDown.getY();
      int x = centerTop.getX();
      int y = leftDown.getY() + radiusVertical;
      int z = centerTop.getZ();
      List<Entity> result = new LinkedList();
      Iterator var10 = centerTop.getWorld().getNearbyEntities(new Location(centerTop.getWorld(), (double)x, (double)y, (double)z), (double)(radiusHorizontally + 1), (double)radiusVertical, (double)radiusHorizontally).iterator();

      while(var10.hasNext()) {
         Entity entity = (Entity)var10.next();
         Location pos = entity.getLocation();
         int vecX = centerTop.getX() - pos.getBlockX();
         int vecZ = centerTop.getZ() - pos.getBlockZ();
         if (Math.sqrt((double)(vecX * vecX + vecZ * vecZ)) <= (double)radiusHorizontally) {
            result.add(entity);
         }
      }

      return result;
   }

   public void setBiome(Point[] points, Biome biome) {
      Point centerTop = points[0];
      Point leftDown = points[1];
      int radius = centerTop.getX() - leftDown.getX();
      int minX = centerTop.getX() - radius;
      int minZ = centerTop.getZ() - radius;
      int maxX = centerTop.getX() + radius;
      int maxZ = centerTop.getZ() + radius;
      World world = centerTop.getWorld();
      int defY = centerTop.getY();

      for(int x = minX; x <= maxX; ++x) {
         for(int z = minZ; z <= maxZ; ++z) {
            if (this.isContains(points, world, x, defY, z)) {
               world.setBiome(x, z, biome);
            }
         }
      }

   }

   public List<int[]> getCoordChunks(Point[] points) {
      throw new RuntimeException("не реализовано");
   }

   public int getBlocksCount(Point[] points) {
      Point centerTop = points[0];
      Point leftDown = points[1];
      int radiusX = centerTop.getX() - leftDown.getX();
      int radiusY = centerTop.getY() - leftDown.getY();
      int radiusZ = centerTop.getZ() - leftDown.getZ();
      return (int)(1.3333333333333333D * (double)(radiusX * radiusY * radiusZ) * 3.141592653589793D);
   }

   public List<Block> getBlocks(Point[] points) {
      List<Block> list = new ArrayList(this.getBlocksCount(points));
      Point min = points[0];
      Point max = points[1];
      World world = points[0].getWorld();

      for(int y = min.getY(); y < max.getY(); ++y) {
         for(int x = min.getX(); x < max.getX(); ++x) {
            for(int z = min.getZ(); z < max.getZ(); ++z) {
               if (this.isContains(points, world, x, y, z)) {
                  list.add(world.getBlockAt(x, y, z));
               }
            }
         }
      }

      return list;
   }
}
