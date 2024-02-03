package net.mineland.creative.modules.coding.variables;

import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.potions.ModulePotions;
import net.mineland.core.bukkit.modules.potions.PotionBottleType;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ua.govnojon.libs.bukkitutil.SimpleItem;

public class GuiMenuPotionEffects extends GuiMenu {
   private static ModulePotions modulePotions = (ModulePotions)Module.getInstance(ModulePotions.class);

   public GuiMenuPotionEffects(String key, User user) {
      super(key, user, 3, Message.getMessage((IUser)user, "coding.gui.potion_effects.title"));

      for(int i = 1; i < PotionEffectType.values().length; ++i) {
         PotionEffectType effectType = PotionEffectType.values()[i];
         SimpleItem item = modulePotions.createPotion(PotionBottleType.NORMAL, new PotionEffect(effectType, 200, 0)).displayName("potion:" + effectType.getName());
         this.addItem(new GuiMenuPotionEffects.GuiItemPotionEffect(this, i - 1, item));
      }

   }

   private class GuiItemPotionEffect extends GuiItem {
      GuiItemPotionEffect(GuiMenu guiMenu, int pos, ItemStack itemStack) {
         super(guiMenu, pos, itemStack);
         this.getItem().setItemMeta(itemStack.getItemMeta());
      }

      public void click(InventoryClickEvent event) {
         PotionMeta potionMeta = (PotionMeta)event.getCurrentItem().getItemMeta();
         ItemStack itemStack = this.getPlayer().getInventory().getItemInMainHand();
         if (itemStack.getType() == Material.GLASS_BOTTLE) {
            itemStack.setType(Material.POTION);
         }

         potionMeta.removeItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
         itemStack.setItemMeta(potionMeta);
         this.getPlayer().getInventory().setItemInMainHand(itemStack);
      }
   }
}
