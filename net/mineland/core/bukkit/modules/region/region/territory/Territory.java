package net.mineland.core.bukkit.modules.region.region.territory;

import java.util.Collection;
import java.util.List;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class Territory {
   private TerritoryType type;
   private Point[] points;

   public Territory(TerritoryType type, Point... points) {
      this.type = type;
      this.points = points;
      this.fixPoints();
   }

   public TerritoryType getType() {
      return this.type;
   }

   public Point[] getPoints() {
      return this.points;
   }

   public boolean isContains(Chunk chunk) {
      return this.type.getHepler().isContains(this.points, chunk);
   }

   public void setPoints(Point... points) {
      this.points = points;
      this.fixPoints();
   }

   public boolean isContains(Location location) {
      return this.type.getHepler().isContains(this.points, location);
   }

   public int getBlocksCount() {
      return this.type.getHepler().getBlocksCount(this.points);
   }

   public List<Block> getBlocks() {
      return this.type.getHepler().getBlocks(this.points);
   }

   private void fixPoints() {
      this.type.getHepler().fix(this.points);
   }

   public Collection<Entity> getEntities() {
      return this.type.getHepler().getEntity(this.points);
   }

   public void setBiome(Biome biome) {
      this.type.getHepler().setBiome(this.points, biome);
   }

   public List<int[]> getCoordChunks() {
      return this.type.getHepler().getCoordChunks(this.points);
   }

   public World getWorld() {
      return this.points[0].getWorld();
   }
}
