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

public class GuiMenuWorldFly extends GuiMenu {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);

   public GuiMenuWorldFly(String key, User user) {
      super(key, user, 6, Message.getMessage((IUser)user, "creative.gui.world_settings.fly.title"));
      this.init();
   }

   private void init() {
      this.clear();
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(this.getUser());
      if (plot != null) {
         List<User> allowed = (List)plot.getOnlinePlayers().stream().filter((u) -> {
            return u.getPlayer().getAllowFlight() && !u.getName().equals(this.getUser().getName());
         }).collect(Collectors.toList());
         List<User> denied = (List)plot.getOnlinePlayers().stream().filter((u) -> {
            return !u.getPlayer().getAllowFlight() && !u.getName().equals(this.getUser().getName());
         }).collect(Collectors.toList());
         int pos = 0;

         Iterator var5;
         User user;
         for(var5 = allowed.iterator(); var5.hasNext(); ++pos) {
            user = (User)var5.next();
            this.addItem(pos, ItemStackUtil.createSkinSkullByUser(user.getName()), Message.getMessage((IUser)this.getUser(), "creative.gui.world_settings.fly.allowed", "{player}", user.getPlayer().getDisplayName()), (event) -> {
               user.getPlayer().setAllowFlight(false);
               user.getPlayer().setFlying(false);
               this.init();
            }).setEnchantEffect(true);
         }

         for(var5 = denied.iterator(); var5.hasNext(); ++pos) {
            user = (User)var5.next();
            this.addItem(pos, ItemStackUtil.createSkinSkullByUser(user.getName()), Message.getMessage((IUser)this.getUser(), "creative.gui.world_settings.fly.denied", "{player}", user.getPlayer().getDisplayName()), (event) -> {
               user.getPlayer().setAllowFlight(true);
               this.init();
            });
         }

         this.addItem(new GuiItemBack(this, GuiMenuWorldSettings.class));
      }
   }
}
