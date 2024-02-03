package net.mineland.creative.modules.coding.variables;

import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiMenuParticleEffects extends GuiMenu {
   public GuiMenuParticleEffects(String key, User user) {
      super(key, user, 6, Message.getMessage((IUser)user, "coding.gui.particle_effects.title"));

      for(int i = 1; i < Particle.values().length; ++i) {
         Particle particle = Particle.values()[i];
         this.addItem(new GuiMenuParticleEffects.GuiItemParticleEffect(this, i - 1, new ItemStack(Material.NETHER_STAR), particle.toString()));
      }

   }

   private class GuiItemParticleEffect extends GuiItem {
      GuiItemParticleEffect(GuiMenu guiMenu, int pos, ItemStack itemStack, String name) {
         super(guiMenu, pos, itemStack, name);
      }

      public void click(InventoryClickEvent event) {
         ItemStack itemStack = event.getCurrentItem();
         this.getPlayer().getInventory().setItemInMainHand(itemStack);
      }
   }
}
