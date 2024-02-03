package ua.govnojon.libs.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.IllegalFormatCodePointException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.nms.NMS;
import net.mineland.core.bukkit.modules.potions.Potions;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.LocationUtil;
import ua.govnojon.libs.bukkitutil.SimpleItem;
import ua.govnojon.libs.bukkitutil.WorldUtil;
import ua.govnojon.libs.myjava.MathUtil;
import ua.govnojon.libs.myjava.StringUtil;

public class Config extends YamlConfiguration {
   public static final String SEP;
   protected String description;
   private File file;
   private boolean first;

   public Config(Module module, String path) {
      this(module.getBukkitPlugin(), path);
   }

   public Config(String data) {
      this.description = "";
      this.first = false;

      try {
         this.loadFromString(data);
      } catch (InvalidConfigurationException var3) {
         var3.printStackTrace();
      }

   }

   public Config(File file, boolean load) {
      this.description = "";
      this.first = false;
      this.file = file;
      if (load) {
         this.reload();
      }

   }

   public Config(File file) {
      this(file, true);
   }

   public Config(Plugin plugin, String path) {
      this.description = "";
      this.first = false;
      this.file = new File(plugin.getDataFolder() + SEP + path);
      this.reload();
   }

   public static String toString(ItemStack item) {
      ItemMeta meta = item.getItemMeta();
      StringBuilder code = new StringBuilder(item.getType().name().toLowerCase() + ":" + item.getDurability() + ":" + item.getAmount() + ":" + (meta != null ? (meta.getDisplayName() == null ? "none" : meta.getDisplayName().replace(":", "<<>>")) : "none") + ":");
      boolean first = true;
      Iterator var4;
      String lore;
      if (meta != null && meta.getLore() != null) {
         for(var4 = meta.getLore().iterator(); var4.hasNext(); code.append(lore.replace(":", "<<>>"))) {
            lore = (String)var4.next();
            if (first) {
               first = false;
            } else {
               code.append("\\n");
            }
         }
      } else {
         code.append("none");
      }

      code.append(":");
      first = true;
      Enchantment enchantment;
      if (meta != null && meta.getEnchants().size() != 0) {
         for(var4 = meta.getEnchants().keySet().iterator(); var4.hasNext(); code.append(enchantment.getName().toLowerCase()).append("*").append(meta.getEnchantLevel(enchantment))) {
            enchantment = (Enchantment)var4.next();
            if (first) {
               first = false;
            } else {
               code.append(",");
            }
         }
      } else {
         code.append("none");
      }

      return code.toString();
   }

   public static ItemData toItemData(String code) {
      if (code != null && code.length() != 0 && !code.equals("null")) {
         String[] data = code.split(":");

         try {
            Material mat = toMaterial(data[0]);
            if (mat == null) {
               return null;
            } else {
               int meta = 0;
               if (data.length > 1) {
                  meta = Integer.parseInt(data[1]);
               }

               return new ItemData(mat, meta);
            }
         } catch (Exception var4) {
            return null;
         }
      } else {
         return null;
      }
   }

   public static ItemStack toItemStack(String code) {
      if (code != null && code.length() != 0 && !code.equals("null")) {
         String[] data = code.split(":");
         if (Potions.getModule() != null && data.length > 0) {
            ItemStack item = Potions.getModule().getCustomPotion(data[0]);
            if (item != null) {
               if (data.length > 1 && MathUtil.isInt(data[1])) {
                  item.setAmount(Integer.parseInt(data[1]));
               }

               return item.clone();
            }
         }

         if (data.length == 0) {
            throw new IllegalFormatCodePointException(0);
         } else if (data.length == 1) {
            return new ItemStack(toMaterial(data[0]));
         } else if (data.length == 2) {
            return new ItemStack(toMaterial(data[0]), Integer.parseInt(data[1]), (short)0);
         } else {
            ItemStack item = new ItemStack(toMaterial(data[0]), Integer.parseInt(data[2]), Short.parseShort(data[1]));
            if (data.length > 3) {
               ItemMeta meta = item.getItemMeta();
               if (meta != null) {
                  if (!data[3].equals("none")) {
                     ((ItemMeta)meta).setDisplayName(data[3].replace("<<>>", ":"));
                  }

                  if (data.length > 4) {
                     if (!data[4].equals("none")) {
                        ((ItemMeta)meta).setLore(Arrays.asList(data[4].split("\\n")));
                     }

                     if (data.length > 5 && !data[5].equals("none")) {
                        String[] var4 = data[5].split(",");
                        int var5 = var4.length;

                        for(int var6 = 0; var6 < var5; ++var6) {
                           String enchant = var4[var6];
                           String[] enchData = enchant.split("\\*");
                           Enchantment enchantment = null;

                           try {
                              enchantment = Enchantment.getById(Integer.parseInt(enchData[0]));
                           } catch (Exception var13) {
                              enchantment = Enchantment.getByName(enchData[0].toUpperCase());
                           }

                           ((ItemMeta)meta).addEnchant(enchantment, Integer.parseInt(enchData[1]), true);
                        }
                     }

                     if (data.length > 6 && !data[6].equals("none")) {
                        PotionMeta metaP = (PotionMeta)meta;
                        String potion = data[6];
                        String[] potionData = potion.split("\\*");

                        PotionType type;
                        try {
                           type = PotionType.getByDamageValue(Integer.parseInt(potionData[0]));
                        } catch (Exception var12) {
                           type = PotionType.valueOf(potionData[0].toUpperCase());
                        }

                        try {
                           String var19 = potionData[1];
                           byte var20 = -1;
                           switch(var19.hashCode()) {
                           case -1820889799:
                              if (var19.equals("extended")) {
                                 var20 = 0;
                              }
                              break;
                           case 1423616456:
                              if (var19.equals("upgraded")) {
                                 var20 = 1;
                              }
                           }

                           switch(var20) {
                           case 0:
                              metaP.setBasePotionData(new PotionData(type, true, false));
                              break;
                           case 1:
                              metaP.setBasePotionData(new PotionData(type, false, true));
                              break;
                           default:
                              metaP.setBasePotionData(new PotionData(type, false, false));
                           }
                        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException var11) {
                           metaP.setBasePotionData(new PotionData(type, false, false));
                        }

                        meta = metaP;
                     }
                  }
               }

               item.setItemMeta((ItemMeta)meta);
            }

            return item;
         }
      } else {
         return null;
      }
   }

   public static String toString(Location loc) {
      return loc.getWorld().getName() + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getYaw() + ":" + loc.getPitch();
   }

   public static String toString(String worldName, Location loc) {
      return toString(worldName, loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
   }

   public static String toString(String worldName, double x, double y, double z, float yaw, float pitch) {
      return worldName + ":" + x + ":" + y + ":" + z + ":" + yaw + ":" + pitch;
   }

   public static Location toLocation(String code) {
      if (code != null && !code.equals("")) {
         String[] loc = code.split(":");
         if (loc.length == 3) {
            return LocationUtil.fixNan(new Location(WorldUtil.getDefaultWorld(), Double.parseDouble(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2])));
         } else if (loc.length == 4) {
            return LocationUtil.fixNan(new Location(Bukkit.getWorld(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]), Double.parseDouble(loc[3])));
         } else {
            return loc.length == 6 ? LocationUtil.fixNan(new Location(Bukkit.getWorld(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]), Double.parseDouble(loc[3]), Float.parseFloat(loc[4]), Float.parseFloat(loc[5]))) : null;
         }
      } else {
         return null;
      }
   }

   public static Location toBlockLocation(String code) {
      if (code != null && !code.equals("")) {
         String[] loc = code.split(":");
         return loc.length != 4 ? null : new Location(Bukkit.getWorld(loc[0]), (double)Integer.parseInt(loc[1]), (double)Integer.parseInt(loc[2]), (double)Integer.parseInt(loc[3]));
      } else {
         return null;
      }
   }

   public void setBlockLocation(String path, Location loc) {
      this.set(path, blockLocationToString(loc));
   }

   public static String blockLocationToString(Location loc) {
      return loc.getWorld().getName() + ":" + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ();
   }

   public static Vector toVector(String code) {
      if (code != null && !code.equals("")) {
         String[] cords = code.split(":");
         return cords.length != 3 ? null : new Vector(Integer.parseInt(cords[0]), Integer.parseInt(cords[1]), Integer.parseInt(cords[2]));
      } else {
         return null;
      }
   }

   public static PotionEffect toPotionEffect(String code) {
      if (code != null && !code.equals("")) {
         String[] args = code.split(":");
         PotionEffectType type = PotionEffectType.INVISIBILITY;
         int duration = 1;
         int amplifier = 1;
         boolean ambient = false;
         if (MathUtil.isInt(args[0])) {
            type = PotionEffectType.getById(Integer.parseInt(args[0]));
         } else {
            try {
               type = PotionEffectType.getByName(args[0].toUpperCase());
            } catch (Exception var7) {
               return null;
            }
         }

         if (args.length > 1) {
            if (!MathUtil.isInt(args[1])) {
               return null;
            }

            duration = Integer.parseInt(args[1]);
         }

         if (args.length > 2) {
            if (!MathUtil.isInt(args[2])) {
               return null;
            }

            amplifier = Integer.parseInt(args[2]);
         }

         if (args.length > 3) {
            ambient = Boolean.parseBoolean(args[3]);
         }

         return new PotionEffect(type, duration, amplifier, ambient);
      } else {
         return null;
      }
   }

   public static Material toMaterial(String code) {
      if (code == null) {
         return null;
      } else {
         try {
            Material material = Material.getMaterial(code.toUpperCase());
            return material == null ? Material.getMaterial(Integer.parseInt(code)) : material;
         } catch (Exception var2) {
            var2.printStackTrace();
            return null;
         }
      }
   }

   public void save() {
      if (this.file != null) {
         try {
            FileOutputStream stream = new FileOutputStream(this.file);
            stream.write(this.saveDataToString().getBytes());
            stream.close();
         } catch (IOException var2) {
            var2.printStackTrace();
         }
      }

   }

   protected String saveDataToString() {
      StringBuilder builder = new StringBuilder();
      if (!this.description.isEmpty()) {
         builder.append('#').append(this.description.replace("\n", "\n#")).append("\n");
      }

      builder.append(this.saveToString());
      return builder.toString();
   }

   public void reload() {
      if (this.file == null) {
         throw new NullPointerException("конфиг не был загружен с файла, сохранить его нельзя");
      } else {
         this.createFile();
         this.loadFromFile();
      }
   }

   protected void loadFromFile() {
      try {
         String content = this.loadDataFromFile();
         StringBuilder dataBuild = new StringBuilder();
         StringBuilder descBuild = new StringBuilder();
         String[] var4 = content.split("\n");
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String line = var4[var6];
            if (!line.isEmpty()) {
               if (line.charAt(0) == '#') {
                  descBuild.append(line.substring(1)).append("\n");
               } else {
                  dataBuild.append(line).append("\n");
               }
            }
         }

         this.description = StringUtil.removeLast(descBuild.toString(), 1);
         this.loadFromString(dataBuild.toString());
      } catch (InvalidConfigurationException | IOException var8) {
         var8.printStackTrace();
      }

   }

   protected String loadDataFromFile() throws IOException {
      FileInputStream stream = new FileInputStream(this.file);
      byte[] bytes = new byte[stream.available()];
      stream.read(bytes);
      stream.close();
      return new String(bytes);
   }

   protected void createFile() {
      if (!this.file.exists()) {
         try {
            this.first = true;
            this.file.getParentFile().mkdirs();
            this.file.createNewFile();
         } catch (IOException var2) {
            var2.printStackTrace();
         }
      } else {
         this.first = false;
      }

   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      if (!this.description.equals(description)) {
         this.description = description;
         this.save();
      }

   }

   public <T> T getOrSet(String path, T def) {
      if (!this.contains(path)) {
         this.setAndSave(path, def);
         return def;
      } else {
         return this.get(path);
      }
   }

   public Number getOrSetNumber(String path, Number def) {
      if (!this.contains(path)) {
         this.setAndSave(path, def);
         return def;
      } else {
         return (Number)this.get(path);
      }
   }

   public void setIfNotExist(String path, Object value) {
      if (!this.contains(path)) {
         this.setAndSave(path, value);
      }

   }

   public void setDefault(String path, Object value) {
      if (this.first) {
         this.setAndSave(path, value);
      }

   }

   public void setAndSave(String path, Object value) {
      this.set(path, value);
      this.save();
   }

   public void set(String path, Object value) {
      super.set(path, this.fixObject(value));
   }

   private Object fixObject(Object value) {
      if (value instanceof Location) {
         return toString((Location)value);
      } else if (value instanceof ItemStack) {
         ItemStack item = (ItemStack)value;
         return NMS.getManager().hasTags(item) ? item : toString(item);
      } else if (value instanceof Material) {
         return ((Material)value).name();
      } else if (value instanceof Collection) {
         Collection<?> collectValue = (Collection)Collection.class.cast(value);
         return collectValue.stream().map(this::fixObject).collect(Collectors.toList());
      } else {
         return value;
      }
   }

   public String getStringColor(String path) {
      String line = this.getString(path);
      if (line == null) {
         return null;
      } else {
         line = StringUtils.replace(line, "&", "§");
         line = StringUtils.replace(line, "\\n", "\n");
         return line;
      }
   }

   public List<String> getStringListColor(String path) {
      List<String> list = this.getStringList(path);
      if (list == null) {
         return null;
      } else {
         List<String> listNew = new ArrayList(list.size());
         Iterator var4 = list.iterator();

         while(var4.hasNext()) {
            String line = (String)var4.next();
            line = StringUtils.replace(line, "&", "§");
            line = StringUtils.replace(line, "\\n", "\n");
            listNew.add(line);
         }

         return listNew;
      }
   }

   public Location getLocation(String path) {
      String code = this.getString(path);
      return toLocation(code);
   }

   public Location getBlockLocation(String path) {
      String code = this.getString(path);
      return toBlockLocation(code);
   }

   public Vector getVector(String path) {
      String code = this.getString(path);
      return toVector(code);
   }

   public PotionEffect getPotionEffect(String path) {
      String code = this.getString(path);
      return toPotionEffect(code);
   }

   public ItemStack getItemStack(String path) {
      Object defLoad = this.get(path);
      if (defLoad instanceof ItemStack) {
         return (ItemStack)defLoad;
      } else {
         String code = (String)defLoad;
         return toItemStack(code);
      }
   }

   public ItemData getItemData(String path) {
      return toItemData(this.getString(path));
   }

   public void setItemData(String path, ItemData itemData) {
      if (itemData == null) {
         this.set(path, (Object)null);
      } else {
         this.set(path, itemData.toString());
      }

   }

   public void createSectionIfNotExist(String path) {
      if (!this.contains(path)) {
         this.setAndSave(path + ".lkjsdafknhsdjklhfalsjhfwui3324ij2i3j4", 10);
         this.setAndSave(path + ".lkjsdafknhsdjklhfalsjhfwui3324ij2i3j4", (Object)null);
      }

   }

   public Material getMaterial(String path) {
      String mat = this.getString(path);
      return toMaterial(mat);
   }

   public List<ItemStack> getItemStackList(String path) {
      List<ItemStack> list = new LinkedList();
      if (path == null) {
         SimpleItem dirt = new SimpleItem(Material.DIRT);
         dirt.setLore(Collections.singletonList("getItemStackList path==null"));
         list.add(dirt);
         return list;
      } else {
         Iterator var3 = this.getList(path).iterator();

         while(var3.hasNext()) {
            Object o = var3.next();
            if (o instanceof String) {
               list.add(toItemStack((String)o));
            } else {
               list.add((ItemStack)o);
            }
         }

         return list;
      }
   }

   public List<Location> getLocationList(String path) {
      List<Location> items = new LinkedList();
      Iterator var3 = this.getStringList(path).iterator();

      while(var3.hasNext()) {
         String string = (String)var3.next();
         items.add(toLocation(string));
      }

      return items;
   }

   public List<Material> getMaterialList(String path) {
      List<Material> list = new LinkedList();
      Iterator var3 = this.getStringList(path).iterator();

      while(var3.hasNext()) {
         String string = (String)var3.next();
         list.add(toMaterial(string));
      }

      return list;
   }

   /** @deprecated */
   @Deprecated
   public ConfigurationSection getConfigurationSection(String path) {
      return super.getConfigurationSection(path);
   }

   public int generateNumberPath(String path) {
      int i;
      for(i = 0; this.contains(path + "." + i); ++i) {
      }

      return i;
   }

   public float getFloat(String s) {
      return (float)this.getDouble(s);
   }

   public Set<String> getKeys(String section) {
      this.createSectionIfNotExist(section);
      return this.getConfigurationSection(section).getKeys(false);
   }

   public File getFile() {
      return this.file;
   }

   public void setFile(File file) {
      this.file = file;
   }

   public boolean isFirst() {
      return this.first;
   }

   static {
      SEP = File.separator;
   }
}
