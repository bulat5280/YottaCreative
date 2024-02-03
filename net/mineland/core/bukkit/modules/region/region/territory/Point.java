package net.mineland.core.bukkit.modules.region.region.territory;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

public class Point {
   private World world;
   private int x;
   private int y;
   private int z;

   public Point(World world, int x, int y, int z) {
      this.world = world;
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Point(Location loc) {
      this.world = loc.getWorld();
      this.x = loc.getBlockX();
      this.y = loc.getBlockY();
      this.z = loc.getBlockZ();
   }

   public World getWorld() {
      return this.world;
   }

   public Point setWorld(World world) {
      this.world = world;
      return this;
   }

   public int getX() {
      return this.x;
   }

   public Point setX(int x) {
      this.x = x;
      return this;
   }

   public int getY() {
      return this.y;
   }

   public Point setY(int y) {
      this.y = y;
      return this;
   }

   public int getZ() {
      return this.z;
   }

   public Point setZ(int z) {
      this.z = z;
      return this;
   }

   protected Object clone() {
      return new Point(this.world, this.x, this.y, this.z);
   }

   public Location toLocation() {
      return new Location(this.world, (double)this.x, (double)this.y, (double)this.z);
   }

   public Chunk getChunk() {
      return this.world.getChunkAt(this.toLocation());
   }
}
