package net.mineland.creative.modules.creative.gui.worldsettings;

import java.util.Iterator;
import java.util.List;
import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.gui.defaultitems.GuiItemBack;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;
import ua.govnojon.libs.bukkitutil.PlayerUtil;

public class GuiMenuWorldRemoveBlacklist extends GuiMenu {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);

   public GuiMenuWorldRemoveBlacklist(String key, User user) {
      super(key, user, 6, Message.getMessage((IUser)user, "creative.gui.world_settings.blacklist.remove.title"));
      this.init();
   }

   private void init() {
      this.clear();
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(this.getUser());
      if (plot != null) {
         List<String> blacklist = plot.getBlacklist();
         PlayerUtil.getDisplayNamesByIds(blacklist, (names) -> {
            int pos = 0;

            for(Iterator var4 = blacklist.iterator(); var4.hasNext(); ++pos) {
               String id = (String)var4.next();
               this.addItem(pos, ItemStackUtil.createSkinSkullByUser("Steve"), Message.getMessage((IUser)this.getUser(), "creative.gui.world_settings.blacklist.remove", "{player}", (String)names.get(pos)), (event) -> {
                  blacklist.remove(id);
                  this.init();
               });
            }

         });
         this.addItem(new GuiItemBack(this, GuiMenuWorldSettings.class));
      }
   }
}
