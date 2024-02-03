package net.mineland.creative.modules.creative.generators.biomes;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import ua.govnojon.libs.bukkitutil.ItemData;

public class VoidGenerator extends PlotGenerator {
   public VoidGenerator(User user, Location location) {
      super(user, location);
   }

   void generate(EditSession editSession) {
      Vector centerPosition = new Vector(this.getCenterPosition().getX(), 4.0D, this.getCenterPosition().getZ());
      CuboidRegion region = new CuboidRegion(centerPosition, centerPosition);
      region.expand(new Vector[]{new Vector(8, 0, 8), new Vector(-8, 0, -8)});
      this.setBlocks(editSession, region, new ItemData(Material.STONE));
      editSession.flushQueue();
      this.setBiome(editSession, Biome.PLAINS);
   }
}
