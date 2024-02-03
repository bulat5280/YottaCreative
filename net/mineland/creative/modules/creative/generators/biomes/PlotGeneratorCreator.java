package net.mineland.creative.modules.creative.generators.biomes;

import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Location;

public interface PlotGeneratorCreator {
   PlotGenerator create(User var1, Location var2);
}
