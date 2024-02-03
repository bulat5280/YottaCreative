package ua.govnojon.libs.bukkitutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import ua.govnojon.libs.config.Config;

public class SimpleItem extends ItemStack {
   public SimpleItem(ItemStack item) {
      super(item);
   }

   public SimpleItem(int id) {
      super(new SimpleItem(id(id), 1));
   }

   public SimpleItem(Material material) {
      super(new SimpleItem(material, 1));
   }

   public SimpleItem(int id, int amount) {
      super(new SimpleItem(id(id), amount, 0));
   }

   public SimpleItem(Material material, int amount) {
      super(new SimpleItem(material, amount, 0));
   }

   public SimpleItem(int id, int amount, int data) {
      super(new SimpleItem(id(id), amount, data, (String)null));
   }

   public SimpleItem(Material material, int amount, int data) {
      super(new SimpleItem(material, amount, data, (String)null));
   }

   public SimpleItem(int id, int amount, int data, String name) {
      super(id(id), amount, (short)data);
      if (name != null) {
         this.displayName(name);
      }

   }

   public SimpleItem(Material material, int amount, int data, String name) {
      super(material, amount, (short)data);
      if (name != null) {
         this.displayName(name);
      }

   }

   public SimpleItem(int id, int amount, int data, String name, List<String> lore) {
      super(id(id), amount, (short)data);
      if (name != null) {
         this.displayName(name);
      }

      if (lore != null) {
         this.lore(lore);
      }

   }

   public SimpleItem(Material material, int amount, int data, String name, List<String> lore) {
      super(material, amount, (short)data);
      if (name != null) {
         this.displayName(name);
      }

      if (lore != null) {
         this.lore(lore);
      }

   }

   private static Material id(int id) {
      return Material.getMaterial(id);
   }

   public SimpleItem setMaterial(Material material) {
      this.setType(material);
      return this;
   }

   public SimpleItem setData(int data) {
      this.setDurability((short)data);
      return this;
   }

   public SimpleItem amount(int amount) {
      this.setAmount(amount);
      return this;
   }

   public SimpleItem setText(String text) {
      String[] data = text.split("(::|\n|\r)");
      ItemMeta meta = this.getItemMeta();
      meta.setDisplayName(data[0]);
      meta.setLore(data.length > 1 ? Arrays.asList(data).subList(1, data.length) : null);
      this.setItemMeta(meta);
      return this;
   }

   public SimpleItem addLoreLine(String loreLine) {
      ItemMeta meta = this.getItemMeta();
      List<String> newLore = meta.getLore();
      if (newLore == null) {
         newLore = new ArrayList();
      }

      ((List)newLore).add(loreLine);
      meta.setLore((List)newLore);
      this.setItemMeta(meta);
      return this;
   }

   public SimpleItem addFlags(ItemFlag... flags) {
      ItemMeta meta = this.getItemMeta();
      meta.addItemFlags(flags);
      this.setItemMeta(meta);
      return this;
   }

   public SimpleItem color(int color) {
      return this.color(Color.fromRGB(color));
   }

   public SimpleItem color(@NotNull DyeColor color) {
      return this.color(color.getColor());
   }

   public SimpleItem color(@NotNull Color color) {
      if (this.getItemMeta() instanceof LeatherArmorMeta) {
         LeatherArmorMeta meta = (LeatherArmorMeta)this.getItemMeta();
         meta.setColor(color);
         this.setItemMeta(meta);
      } else {
         switch(this.getType()) {
         case WOOL:
         case STAINED_GLASS:
         case STAINED_GLASS_PANE:
         case STAINED_CLAY:
         case BED:
         case CONCRETE:
         case CONCRETE_POWDER:
         case INK_SACK:
            if (!DyeColor.BLACK.getColor().equals(color) && Color.BLACK != color) {
               if (!DyeColor.RED.getColor().equals(color) && Color.RED != color) {
                  if (!DyeColor.GREEN.getColor().equals(color) && Color.GREEN != color) {
                     if (DyeColor.BROWN.getColor().equals(color)) {
                        this.setDurability((short)3);
                     } else if (!DyeColor.BLUE.getColor().equals(color) && Color.BLUE != color) {
                        if (!DyeColor.PURPLE.getColor().equals(color) && Color.PURPLE != color) {
                           if (!DyeColor.CYAN.getColor().equals(color) && Color.TEAL != color) {
                              if (!DyeColor.SILVER.getColor().equals(color) && Color.SILVER != color) {
                                 if (!DyeColor.GRAY.getColor().equals(color) && Color.GRAY != color) {
                                    if (DyeColor.PINK.getColor().equals(color)) {
                                       this.setDurability((short)9);
                                    } else if (!DyeColor.LIME.getColor().equals(color) && Color.OLIVE != color) {
                                       if (!DyeColor.YELLOW.getColor().equals(color) && Color.YELLOW != color) {
                                          if (!DyeColor.LIGHT_BLUE.getColor().equals(color) && Color.AQUA != color) {
                                             if (!DyeColor.MAGENTA.getColor().equals(color) && Color.FUCHSIA != color) {
                                                if (!DyeColor.ORANGE.getColor().equals(color) && Color.ORANGE != color) {
                                                   if (DyeColor.WHITE.getColor().equals(color) || Color.WHITE == color) {
                                                      this.setDurability((short)15);
                                                   }
                                                } else {
                                                   this.setDurability((short)14);
                                                }
                                             } else {
                                                this.setDurability((short)13);
                                             }
                                          } else {
                                             this.setDurability((short)12);
                                          }
                                       } else {
                                          this.setDurability((short)11);
                                       }
                                    } else {
                                       this.setDurability((short)10);
                                    }
                                 } else {
                                    this.setDurability((short)8);
                                 }
                              } else {
                                 this.setDurability((short)7);
                              }
                           } else {
                              this.setDurability((short)6);
                           }
                        } else {
                           this.setDurability((short)5);
                        }
                     } else {
                        this.setDurability((short)4);
                     }
                  } else {
                     this.setDurability((short)2);
                  }
               } else {
                  this.setDurability((short)1);
               }
            } else {
               this.setDurability((short)0);
            }
         }
      }

      return this;
   }

   public SimpleItem fireworkEffect(FireworkEffect fireworkEffect) {
      if (this.getType() != Material.FIREWORK_CHARGE) {
         return this;
      } else {
         FireworkEffectMeta effectMeta = (FireworkEffectMeta)this.getItemMeta();
         effectMeta.setEffect(fireworkEffect);
         this.setItemMeta(effectMeta);
         return this;
      }
   }

   /** @deprecated */
   @Deprecated
   public SimpleItem addNewEnchantment(Enchantment ench, int level) {
      this.addEnchantment(ench, level);
      return this;
   }

   public SimpleItem enchant(Enchantment enchantment, int level) {
      this.addUnsafeEnchantment(enchantment, level);
      return this;
   }

   public String getDisplayName() {
      return this.getItemMeta().getDisplayName();
   }

   public SimpleItem displayName(String name) {
      ItemMeta meta = this.getItemMeta();
      meta.setDisplayName(name);
      this.setItemMeta(meta);
      return this;
   }

   public String getLocalizedName() {
      return this.getItemMeta().getLocalizedName();
   }

   public SimpleItem localizedName(String name) {
      ItemMeta meta = this.getItemMeta();
      meta.setLocalizedName(name);
      this.setItemMeta(meta);
      return this;
   }

   public String getCustomNameWithoutChatColors() {
      return ChatColor.stripColor(this.getItemMeta().getDisplayName());
   }

   public List<String> getLore() {
      List<String> lore = this.getItemMeta().getLore();
      return (List)(lore != null ? lore : new ArrayList());
   }

   public SimpleItem lore(List<String> lore) {
      ItemMeta meta = this.getItemMeta();
      meta.setLore(lore);
      this.setItemMeta(meta);
      return this;
   }

   public SimpleItem setLore(List<String> lore) {
      ItemMeta meta = this.getItemMeta();
      meta.setLore(lore);
      this.setItemMeta(meta);
      return this;
   }

   public SimpleItem lore(String... lines) {
      ItemMeta meta = this.getItemMeta();
      List<String> lore = meta.getLore();
      if (lore == null) {
         lore = Arrays.asList(lines);
      } else {
         lore.addAll(Arrays.asList(lines));
      }

      meta.setLore(lore);
      this.setItemMeta(meta);
      return this;
   }

   public SimpleItem addCustomEffect(PotionEffect potionEffect, boolean replace) {
      PotionMeta potionMeta = (PotionMeta)this.getItemMeta();
      potionMeta.addCustomEffect(potionEffect, replace);
      this.setItemMeta(potionMeta);
      return this;
   }

   public SimpleItem clone() {
      return new SimpleItem(super.clone());
   }

   public boolean isUnbreakable() {
      return this.hasItemMeta() && this.getItemMeta().isUnbreakable();
   }

   public SimpleItem setUnbreakable(boolean b) {
      ItemMeta meta = this.getItemMeta();
      meta.setUnbreakable(b);
      this.setItemMeta(meta);
      return this;
   }

   public SimpleItem setSkullOwner(String name) {
      SkullMeta meta = (SkullMeta)this.getItemMeta();
      meta.setOwner(name);
      this.setItemMeta(meta);
      return this;
   }

   public SimpleItem hideEnchants() {
      this.addFlags(ItemFlag.HIDE_ENCHANTS);
      return this;
   }

   public SimpleItem hideAttributes() {
      this.addFlags(ItemFlag.HIDE_ATTRIBUTES);
      return this;
   }

   public SimpleItem hideUnbreakable() {
      this.addFlags(ItemFlag.HIDE_UNBREAKABLE);
      return this;
   }

   public SimpleItem hideDestroys() {
      this.addFlags(ItemFlag.HIDE_DESTROYS);
      return this;
   }

   public SimpleItem hidePlacedOn() {
      this.addFlags(ItemFlag.HIDE_PLACED_ON);
      return this;
   }

   public SimpleItem hidePotionEffects() {
      this.addFlags(ItemFlag.HIDE_POTION_EFFECTS);
      return this;
   }

   public String toString(ItemStack itemStack) {
      Config config = new Config("");
      config.set("i", this);
      return config.saveToString();
   }
}
