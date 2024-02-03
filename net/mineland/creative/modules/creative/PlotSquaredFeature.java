package net.mineland.creative.modules.creative;

import com.boydti.fawe.bukkit.regions.BukkitMaskManager;
import com.boydti.fawe.object.FawePlayer;
import com.boydti.fawe.regions.FaweMask;
import com.boydti.fawe.regions.FaweMaskManager.MaskType;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.entity.Player;

public class PlotSquaredFeature extends BukkitMaskManager {
   private ModuleCreative moduleCreative;

   public PlotSquaredFeature(ModuleCreative moduleCreative) {
      super("creative-plus");
      this.moduleCreative = moduleCreative;
   }

   public FaweMask getMask(FawePlayer<Player> player, MaskType type) {
      User user = User.getUser(player.getName());
      if (user != null) {
         if (!user.getPlayer().getWorld().equals(this.moduleCreative.getPlotWorld())) {
            return null;
         }

         Plot plot = this.moduleCreative.getPlotManager().getCurrentPlot(user);
         if (plot != null && plot.getPlotMode().equals(PlotMode.BUILDING) && plot.getPlotRegion().isMember(user.getPlayer())) {
            return plot.getFaweMask();
         }
      }

      return null;
   }
}
