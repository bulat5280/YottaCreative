package net.mineland.core.bukkit.modules.potions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import net.mineland.core.bukkit.module.BukkitModule;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.Lang;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import ua.govnojon.libs.bukkitutil.SimpleItem;
import ua.govnojon.libs.config.Config;
import ua.govnojon.libs.myjava.MathUtil;

public class ModulePotions extends BukkitModule {
   private HashMap<String, SimpleItem> customPotions = new HashMap();

   public ModulePotions(int priority, Plugin plugin) {
      super("potions", priority, plugin, new Config(plugin, "potions.yml"));
   }

   public void onFirstEnable() {
      this.registerListenersThis();
   }

   public void onEnable() {
      this.getConfig().setDescription("bottle-type - тип бутылки (0 - 2 или NORMAL, SPLASH, LINGERING)\nlang - ключ для переводимого имени\neffects - список эффектов в формате тип:время:сила:прозрачность (не все обызательны, тип может быть как число начиная с 1 либо как ENUM)");
      Iterator var1 = this.getConfig().getKeys(false).iterator();

      while(var1.hasNext()) {
         String name = (String)var1.next();

         try {
            PotionBottleType bottleType = PotionBottleType.NORMAL;
            String type = this.getConfig().getString(name + ".bottle-type");
            if (type != null && !type.equals("")) {
               if (MathUtil.isInt(type)) {
                  bottleType = PotionBottleType.values()[Integer.parseInt(type) % PotionBottleType.values().length];
               } else {
                  try {
                     bottleType = PotionBottleType.valueOf(type.toUpperCase());
                  } catch (Exception var7) {
                     continue;
                  }
               }
            }

            String key = this.getConfig().getString(name + ".lang");
            if (key == null || key.equals("")) {
               key = "potion_" + name;
            }

            List<PotionEffect> effects = (List)this.getConfig().getStringList(name + ".effects").stream().map(Config::toPotionEffect).filter(Objects::nonNull).collect(Collectors.toCollection(LinkedList::new));
            this.customPotions.put(name, this.createPotion(bottleType, (PotionEffect[])effects.toArray(new PotionEffect[0])).displayName("lang:" + key));
         } catch (Exception var8) {
            System.out.print("При паринге итема произошла ошибка: " + name);
            var8.printStackTrace();
         }
      }

      this.getLogger().info("Загружено " + this.customPotions.size() + " кастомных зелий");
   }

   public void onDisable() {
   }

   public SimpleItem createPotion(PotionBottleType bottleType, PotionEffect... effects) {
      SimpleItem item = new SimpleItem(bottleType.getMaterial());
      PotionMeta meta = (PotionMeta)item.getItemMeta();
      Arrays.stream(effects).forEach((effect) -> {
         meta.addCustomEffect(effect, true);
      });
      meta.setColor(effects[0].getType().getColor());
      item.setItemMeta(meta);
      return item;
   }

   public SimpleItem getCustomPotion(String name) {
      SimpleItem simpleItem = (SimpleItem)this.customPotions.get(name);
      return simpleItem == null ? null : simpleItem.clone();
   }

   public SimpleItem getCustomPotion(String name, Lang lang) {
      SimpleItem item = this.getCustomPotion(name);
      item.setText(Message.getMessage(lang, item.getDisplayName().replaceFirst("lang:", "").trim()));
      return item;
   }
}
