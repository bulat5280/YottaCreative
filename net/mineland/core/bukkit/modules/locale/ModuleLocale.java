package net.mineland.core.bukkit.modules.locale;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import net.mineland.core.bukkit.module.BukkitModule;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.nms.NMS;
import net.mineland.core.bukkit.modules.user.Lang;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import ua.govnojon.libs.bukkitutil.SimpleItem;
import ua.govnojon.libs.config.Config;
import ua.govnojon.libs.myjava.FormatNumerals;
import ua.govnojon.libs.myjava.MyObject;

public class ModuleLocale extends BukkitModule {
   public static final double Y = 50.0D;
   public static Set<Player> players = new HashSet();
   private static ModuleLocale instance;
   private MapInjector injector;
   private Lang current;
   private HashMap<Lang, Map<String, String>> locales = new HashMap();
   private String[] effects;

   public ModuleLocale(int priority, Plugin plugin) {
      super("locale", priority, plugin, (Config)null);
      instance = this;
   }

   /** @deprecated */
   @Deprecated
   public static ModuleLocale getInstance() {
      return instance;
   }

   public void onFirstEnable() {
      this.registerData(new PacketAdapterLocale(this.getPlugin()));
   }

   public void onEnable() {
      this.registerListenersThis();
      this.locales.clear();
      Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d.]*[df]");
      Splitter split = Splitter.on('=').limit(2);
      Lang[] var3 = Lang.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Lang lang = var3[var5];

         try {
            HashMap<String, String> map = new HashMap();
            this.locales.put(lang, map);
            File file = new File(this.getPlugin().getDataFolder() + File.separator + "minecraft-locale" + File.separator + lang.getLang() + ".txt");
            if (file.exists()) {
               FileInputStream stream = new FileInputStream(file);
               byte[] bytes = new byte[stream.available()];
               stream.read(bytes);
               stream.close();
               String[] var11 = (new String(bytes)).replace("\r", "").split("\n");
               int var12 = var11.length;

               for(int var13 = 0; var13 < var12; ++var13) {
                  String line = var11[var13];
                  if (!line.isEmpty() && line.charAt(0) != '#') {
                     String[] data = (String[])Iterables.toArray(split.split(line), String.class);
                     if (data != null && data.length == 2) {
                        String key = data[0];
                        String value = pattern.matcher(data[1]).replaceAll("%$1s");
                        value = value.replace("Яйцо призыва", "ЯП");
                        map.put(key, value);
                     }
                  }
               }
            }
         } catch (IOException var19) {
            this.getLogger().severe("Ошибка при загрузке локализации для языка '" + lang.name() + "'.");
            var19.printStackTrace();
         }
      }

      this.injector = new MapInjector((Map)this.locales.get(this.current = Lang.RU));

      try {
         MyObject.wrap(Class.forName("net.minecraft.server." + NMS.getModuleNMS().VERSION + ".LocaleI18n")).getField("a").setField("d", this.injector);
      } catch (ClassNotFoundException var18) {
         var18.printStackTrace();
      }

      this.effects = new String[28];
      this.effects[1] = "moveSpeed";
      this.effects[2] = "moveSlowdown";
      this.effects[3] = "digSpeed";
      this.effects[4] = "digSlowDown";
      this.effects[5] = "damageBoost";
      this.effects[6] = "heal";
      this.effects[7] = "harm";
      this.effects[8] = "jump";
      this.effects[9] = "confusion";
      this.effects[10] = "regeneration";
      this.effects[11] = "resistance";
      this.effects[12] = "fireResistance";
      this.effects[13] = "waterBreathing";
      this.effects[14] = "invisibility";
      this.effects[15] = "blindness";
      this.effects[16] = "nightVision";
      this.effects[17] = "hunger";
      this.effects[18] = "weakness";
      this.effects[19] = "poison";
      this.effects[20] = "wither";
      this.effects[21] = "healthBoost";
      this.effects[22] = "absorption";
      this.effects[23] = "saturation";
      this.effects[24] = "glowing";
      this.effects[25] = "levitation";
      this.effects[26] = "luck";
      this.effects[27] = "unluck";
   }

   public void onDisable() {
   }

   @EventHandler
   public void onPlayerItemRename(PrepareAnvilEvent e) {
      ItemStack is = e.getResult();
      if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("lang:")) {
         e.setResult((ItemStack)null);
      }

   }

   public String getName(ItemStack item, Lang lang) {
      return this.getName(item, lang, false);
   }

   public String getName(ItemStack item, Lang lang, boolean onlyStandart) {
      if (item == new SimpleItem(Material.GOLDEN_APPLE, 1, 0)) {
         return Message.getMessage(lang, "золотое.яблоко.1");
      } else if (item == new SimpleItem(Material.GOLDEN_APPLE, 1, 1)) {
         return Message.getMessage(lang, "золотое.яблоко.2");
      } else if (item == new SimpleItem(Material.HUGE_MUSHROOM_2, 1, 64)) {
         return Message.getMessage(lang, "блок.мухомора");
      } else if (item == new SimpleItem(Material.HUGE_MUSHROOM_1, 1, 51)) {
         return Message.getMessage(lang, "блок.белого.гриба");
      } else if (item == new SimpleItem(Material.RED_MUSHROOM, 1, 1)) {
         return Message.getMessage(lang, "мухомор");
      } else if (item == new SimpleItem(Material.BROWN_MUSHROOM, 1, 1)) {
         return Message.getMessage(lang, "белый.гриб");
      } else if (item == new ItemStack(Material.MELON)) {
         return Message.getMessage(lang, "долька.арбуза");
      } else {
         this.usingLang(lang);
         if (!onlyStandart) {
            ItemMeta meta = item.getItemMeta();
            String displayName = meta == null ? null : item.getItemMeta().getDisplayName();
            if (displayName != null && displayName.startsWith("lang:")) {
               return Message.getMessage(lang, displayName.substring(5));
            }
         }

         String name = NMS.getManager().getNameItem(item);
         return name == null ? item.getType().name() : name;
      }
   }

   private void usingLang(Lang lang) {
      if (this.current != lang) {
         Map<String, String> map = (Map)this.locales.get(lang);
         if (map != null) {
            this.injector.setCurrent(map);
            this.current = lang;
         }
      }

   }

   public String getNameEnchant(Enchantment enchantment, int level, Lang lang) {
      this.usingLang(lang);
      return NMS.getManager().getNameEnchantment(enchantment) + " " + this.getLevel(level);
   }

   public String getNameEnchant(Enchantment enchantment, Lang lang) {
      this.usingLang(lang);
      return NMS.getManager().getNameEnchantment(enchantment);
   }

   public String getLevel(int level) {
      return FormatNumerals.formatRoman(level);
   }

   public String getName(Material material, Lang lang) {
      return this.getName(new ItemStack(material), lang);
   }

   public String getName(Integer id, Lang lang) {
      return this.getName(new ItemStack(id), lang);
   }

   public String getName(Integer id, short data, Lang lang) {
      return this.getName(new ItemStack(id, 1, data), lang);
   }

   public String getNamePotion(PotionEffectType effectType, Lang lang) {
      this.usingLang(lang);
      String name = NMS.getManager().getLocale("effect." + this.effects[effectType.getId()]);
      return name == null ? "" : name;
   }

   public String getString(String key, Lang lang) {
      this.usingLang(lang);
      return NMS.getManager().getLocale(key);
   }

   public String getNameBiome(Biome biome, Lang lang) {
      return Message.getMessage(lang, "биом_" + biome.name());
   }

   public String getNameHorseColor(Color horseColor, Lang lang) {
      return Message.getMessage(lang, "лошадь_цвет_" + horseColor.name());
   }

   public String getNameHorseStyle(Style horseStyle, Lang lang) {
      return Message.getMessage(lang, "лошадь_стиль_" + horseStyle.name());
   }
}
