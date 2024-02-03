package net.mineland.creative.modules.creative.generators.biomes;

import com.sk89q.worldedit.EditSession;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import ua.govnojon.libs.bukkitutil.ItemData;

public class FlatGenerator extends PlotGenerator {
   public FlatGenerator(User user, Location pos) {
      super(user, pos);
   }

   void generate(EditSession editSession) {
      this.setBlocks(editSession, this.getCuboidRegion(3, 3), new ItemData(Material.GRASS));
      this.setBlocks(editSession, this.getCuboidRegion(1, 2), new ItemData(Material.DIRT));
      this.setBlocks(editSession, this.getCuboidRegion(0, 0), new ItemData(Material.BEDROCK));
      this.setBiome(editSession, Biome.PLAINS);
   }
}
