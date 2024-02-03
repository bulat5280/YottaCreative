package net.mineland.creative.modules.creative.gui.worldsettings;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.gui.defaultitems.GuiItemBack;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;

public class GuiMenuWorldAddBlacklist extends GuiMenu {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);

   public GuiMenuWorldAddBlacklist(String key, User user) {
      super(key, user, 6, Message.getMessage((IUser)user, "creative.gui.world_settings.blacklist.add.title"));
      this.init();
   }

   private void init() {
      this.clear();
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(this.getUser());
      if (plot != null) {
         List<String> blacklist = plot.getBlacklist();
         List<User> users = (List)plot.getOnlinePlayers().stream().filter((u) -> {
            return !blacklist.contains(u.getName()) && !u.getName().equals(this.getUser().getName());
         }).collect(Collectors.toList());
         int pos = 0;

         for(Iterator var5 = users.iterator(); var5.hasNext(); ++pos) {
            User user = (User)var5.next();
            this.addItem(pos, ItemStackUtil.createSkinSkullByUser(user.getName()), Message.getMessage((IUser)this.getUser(), "creative.gui.world_settings.blacklist.add", "{player}", user.getPlayer().getDisplayName()), (event) -> {
               user.getPlayer().chat("/s");
               blacklist.add(user.getName());
               this.init();
            });
         }

         this.addItem(new GuiItemBack(this, GuiMenuWorldSettings.class));
      }
   }
}
