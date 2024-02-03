package net.mineland.core.bukkit.modules.region.region.territory.types;

import java.util.Collection;
import java.util.List;
import net.mineland.core.bukkit.modules.region.region.territory.Point;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public interface TerritoryHepler {
   boolean isContains(Point[] var1, Location var2);

   boolean isContains(Point[] var1, Chunk var2);

   void fix(Point[] var1);

   Collection<Entity> getEntity(Point[] var1);

   void setBiome(Point[] var1, Biome var2);

   List<int[]> getCoordChunks(Point[] var1);

   int getBlocksCount(Point[] var1);

   List<Block> getBlocks(Point[] var1);
}
