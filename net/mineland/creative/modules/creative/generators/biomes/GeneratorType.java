package net.mineland.creative.modules.creative.generators.biomes;

import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Location;
import org.bukkit.Material;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;

public enum GeneratorType {
   FLAT(FlatGenerator::new, new ItemData(Material.GRASS)),
   VOID(VoidGenerator::new, new ItemData(Material.GLASS)),
   SAND(SandGenerator::new, new ItemData(Material.SAND));

   private PlotGeneratorCreator creator;
   private ItemData icon;

   private GeneratorType(PlotGeneratorCreator creator, ItemData icon) {
      this.creator = creator;
      this.icon = icon;
   }

   public void generate(User user, Location pos, boolean clear, Runnable done) {
      Schedule.run(() -> {
         this.creator.create(user, pos).create(clear, done);
      });
   }

   public ItemData getIcon() {
      return this.icon;
   }
}
