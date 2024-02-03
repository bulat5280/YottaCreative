package net.mineland.creative.modules.coding;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.dynamicvariables.DynamicVariable;
import net.mineland.creative.modules.coding.values.objects.ValueType;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jooq.tools.StringUtils;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;
import ua.govnojon.libs.bukkitutil.SimpleItem;
import ua.govnojon.libs.bukkitutil.chatcomponent.ChatComponentUtil;
import ua.govnojon.libs.config.Config;

public class CodeUtils {
   public static final Pattern NUMBER = Pattern.compile("-?[0-9]+\\.?[0-9]*");
   public static final Pattern INTEGER = Pattern.compile("-?[0-9]+?[0-9]*");
   public static final Pattern TIME = Pattern.compile("[0-9]+(t|m|s|h)");
   public static final Pattern DURATION = Pattern.compile("([0-9]?[0-9]?[0-9]|2[0-9]):[0-5][0-9]");
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);

   public static String parsePlaceholders(String string, Entity asEntity) {
      string = StringUtils.replace(string, "{name}", asEntity.getCustomName());
      return string;
   }

   public static String parseText(ItemStack itemStack) {
      return parseText(itemStack, "");
   }

   public static String parseText(ItemStack itemStack, String def) {
      return parseText(itemStack, def, true);
   }

   public static String parseText(ItemStack itemStack, String def, boolean checkType) {
      if (!ItemStackUtil.isNullOrAir(itemStack)) {
         if (checkType && itemStack.getType() != Material.BOOK) {
            return def;
         } else {
            String text = (new SimpleItem(itemStack)).getDisplayName();
            if (text != null && text.startsWith("§r")) {
               text = text.substring(2);
            }

            return text != null ? text : def;
         }
      } else {
         return def;
      }
   }

   public static double parseNumber(ItemStack itemStack) {
      return parseNumber(itemStack, 0.0D);
   }

   public static double parseNumber(ItemStack itemStack, double def) {
      return parseNumber(itemStack, def, true);
   }

   public static double parseNumber(ItemStack itemStack, double def, boolean checkType) {
      if (!ItemStackUtil.isNullOrAir(itemStack)) {
         if (checkType && itemStack.getType() != Material.SLIME_BALL) {
            return def;
         } else {
            String text = ChatComponentUtil.removeColors(parseText(itemStack, String.valueOf(def), false));
            return NUMBER.matcher(text).matches() ? Double.parseDouble(text) : def;
         }
      } else {
         return def;
      }
   }

   public static Location parseLocation(ItemStack itemStack, Location def) {
      return parseLocation(itemStack, def, true);
   }

   public static Location parseLocation(ItemStack itemStack, Location def, boolean checkType) {
      if (!ItemStackUtil.isNullOrAir(itemStack)) {
         if (checkType && itemStack.getType() != Material.PAPER) {
            return def;
         } else {
            String text = ChatComponentUtil.removeColors(parseText(itemStack, "", false));
            Location parsedLoc = Config.toLocation(text);
            if (parsedLoc != null) {
               return parsedLoc;
            } else {
               String[] split = text.split(" ");
               double[] numbers = new double[split.length];

               for(int i = 0; i < split.length; ++i) {
                  if (!NUMBER.matcher(split[i]).matches()) {
                     return def;
                  }

                  numbers[i] = new Double(split[i]);
               }

               return new Location(moduleCreative.getPlotWorld(), numbers[0], numbers[1], numbers[2], (float)numbers[3], (float)numbers[4]);
            }
         }
      } else {
         return def;
      }
   }

   public static PotionEffect parsePotion(ItemStack itemStack) {
      if (!ItemStackUtil.isNullOrAir(itemStack)) {
         switch(itemStack.getType()) {
         case POTION:
         case LINGERING_POTION:
         case SPLASH_POTION:
            PotionMeta potionMeta = (PotionMeta)itemStack.getItemMeta();
            PotionEffectType potionEffectType = potionMeta.getBasePotionData().getType().getEffectType();
            int duration = 200;
            int amplifier = 0;
            if (potionEffectType == null) {
               potionEffectType = ((PotionEffect)potionMeta.getCustomEffects().get(0)).getType();
               duration = ((PotionEffect)potionMeta.getCustomEffects().get(0)).getDuration();
               amplifier = ((PotionEffect)potionMeta.getCustomEffects().get(0)).getAmplifier();
            }

            return new PotionEffect(potionEffectType, duration, amplifier);
         default:
            return null;
         }
      } else {
         return null;
      }
   }

   public static EntityType parseEgg(ItemStack itemStack) {
      if (!ItemStackUtil.isNullOrAir(itemStack)) {
         switch(itemStack.getType()) {
         case MONSTER_EGG:
            SpawnEggMeta meta = (SpawnEggMeta)itemStack.getItemMeta();
            return meta.getSpawnedType();
         default:
            return null;
         }
      } else {
         return null;
      }
   }

   public static Location getLastBlock(Location startPosition) {
      Block block = startPosition.getBlock();

      int airBlocks;
      for(airBlocks = 0; airBlocks < 3; block = block.getRelative(BlockFace.WEST)) {
         if (block.getType() == Material.AIR) {
            ++airBlocks;
         } else if (block.getType() == Material.PISTON_BASE && airBlocks != 0) {
            --airBlocks;
         }
      }

      return block.getRelative(BlockFace.EAST, airBlocks).getLocation();
   }

   public static Block getEndPistonBlock(Block startPistonBlock) {
      Plot plot = moduleCreative.getPlotManager().getPlot(startPistonBlock.getLocation());
      if (plot != null) {
         int openPistons = 1;
         Block block = startPistonBlock;

         while(plot.inTerritory(block.getLocation())) {
            block = block.getRelative(BlockFace.WEST, 2);
            if (block.getType() == Material.PISTON_BASE) {
               if (block.getData() == 4) {
                  ++openPistons;
               } else if (block.getData() == 5) {
                  --openPistons;
               }

               if (openPistons == 0) {
                  return block;
               }
            }
         }

         return null;
      } else {
         throw new NullPointerException("Plot == null, Location = " + startPistonBlock.getLocation().toString());
      }
   }

   public static Object parseItem(ItemStack itemStack) {
      if (!ItemStackUtil.isNullOrAir(itemStack)) {
         String localizedName;
         switch(itemStack.getType()) {
         case POTION:
            return parsePotion(itemStack);
         case LINGERING_POTION:
         case SPLASH_POTION:
         default:
            return null;
         case MONSTER_EGG:
            return parseEgg(itemStack);
         case BOOK:
            return parseText(itemStack);
         case SLIME_BALL:
            return parseNumber(itemStack);
         case PAPER:
            return parseLocation(itemStack, (Location)null);
         case NETHER_STAR:
            localizedName = (new SimpleItem(itemStack)).getLocalizedName();
            if (localizedName == null) {
               return null;
            } else {
               try {
                  return Particle.valueOf(localizedName.toUpperCase());
               } catch (IllegalArgumentException var5) {
                  return null;
               }
            }
         case APPLE:
            localizedName = (new SimpleItem(itemStack)).getLocalizedName();
            if (localizedName == null) {
               return null;
            } else {
               try {
                  return ValueType.valueOf(localizedName.toUpperCase()).create();
               } catch (IllegalArgumentException var4) {
                  return null;
               }
            }
         case MAGMA_CREAM:
            localizedName = ChatComponentUtil.removeColors(parseText(itemStack, "dynamicVar", false));
            DynamicVariable dynamicVariable = new DynamicVariable(localizedName);
            String localizedName = (new SimpleItem(itemStack)).getLocalizedName();
            if (localizedName != null && localizedName.equalsIgnoreCase("save")) {
               dynamicVariable.setSave(true);
            }

            return dynamicVariable;
         }
      } else {
         return null;
      }
   }

   public static ItemStack[] sortByType(ItemStack[] itemStacks, Material... material) {
      List<Material> materials = Arrays.asList(material);
      return (ItemStack[])Arrays.stream(itemStacks).filter((itemStack) -> {
         return !ItemStackUtil.isNullOrAir(itemStack) && materials.contains(itemStack.getType());
      }).toArray((x$0) -> {
         return new ItemStack[x$0];
      });
   }

   public static List<ItemData> sortByBlock(ItemStack[] itemStacks) {
      return (List)Arrays.stream(itemStacks).filter((itemStack) -> {
         return !ItemStackUtil.isNullOrAir(itemStack) && hasBlock(itemStack.getType());
      }).map(ItemData::new).collect(Collectors.toList());
   }

   public static boolean hasBlock(Material material) {
      if (material.isBlock()) {
         return true;
      } else {
         return material.equals(Material.SKULL_ITEM);
      }
   }

   public static ItemStack[] sortNotNull(ItemStack[] itemStacks) {
      return (ItemStack[])Arrays.stream(itemStacks).filter((itemStack) -> {
         return !ItemStackUtil.isNullOrAir(itemStack);
      }).toArray((x$0) -> {
         return new ItemStack[x$0];
      });
   }

   public static boolean onPlot(Plot plot, Entity entity) {
      if (entity.getType() == EntityType.PLAYER) {
         User user = User.getUser((CommandSender)entity);
         return plot.equals(moduleCreative.getPlotManager().getCurrentPlot(user));
      } else {
         Location location = entity.getLocation();
         return plot.equals(moduleCreative.getPlotManager().getPlot(location));
      }
   }

   public static String getEntityName(Entity entity) {
      if (entity == null) {
         return "";
      } else if (entity instanceof Player) {
         return entity.getName();
      } else {
         String customName = entity.getCustomName();
         return customName != null ? customName : (entity.getName() != null ? entity.getName() : entity.getType().name());
      }
   }

   public static boolean invertIfBlock(Block block, ItemStack itemStack) {
      if (!ItemStackUtil.isNullOrAir(itemStack) && itemStack.getType() == Material.ARROW && block.getType() == Material.WALL_SIGN) {
         Sign sign = (Sign)block.getState();
         String line = sign.getLine(3);
         if (line.endsWith("coding.sign.not")) {
            sign.setLine(3, "");
         } else {
            sign.setLine(3, "lang:coding.sign.not");
         }

         sign.update();
         return true;
      } else {
         return false;
      }
   }

   public static List<EntityType> getEntityTypes(ItemStack[] itemStacks) {
      return (List)Arrays.stream(itemStacks).map((itemStack) -> {
         switch(itemStack.getType()) {
         case LINGERING_POTION:
            return EntityType.AREA_EFFECT_CLOUD;
         case SPLASH_POTION:
         case BOOK:
         case SLIME_BALL:
         case PAPER:
         case NETHER_STAR:
         case APPLE:
         case MAGMA_CREAM:
         default:
            return null;
         case MONSTER_EGG:
            SpawnEggMeta meta = (SpawnEggMeta)itemStack.getItemMeta();
            return meta.getSpawnedType();
         case ARROW:
            return EntityType.ARROW;
         case TIPPED_ARROW:
            return EntityType.TIPPED_ARROW;
         case SPECTRAL_ARROW:
            return EntityType.SPECTRAL_ARROW;
         case FIREBALL:
            return EntityType.FIREBALL;
         case SNOW_BALL:
            return EntityType.SNOWBALL;
         case EGG:
            return EntityType.EGG;
         case ITEM_FRAME:
            return EntityType.ITEM_FRAME;
         case PAINTING:
            return EntityType.PAINTING;
         case MINECART:
            return EntityType.MINECART;
         case COMMAND_MINECART:
            return EntityType.MINECART_COMMAND;
         case EXPLOSIVE_MINECART:
            return EntityType.MINECART_TNT;
         case HOPPER_MINECART:
            return EntityType.MINECART_HOPPER;
         case POWERED_MINECART:
            return EntityType.MINECART_FURNACE;
         case STORAGE_MINECART:
            return EntityType.MINECART_CHEST;
         case BOAT_ACACIA:
         case BOAT_BIRCH:
         case BOAT_DARK_OAK:
         case BOAT_JUNGLE:
         case BOAT_SPRUCE:
         case BOAT:
            return EntityType.BOAT;
         case EXP_BOTTLE:
            return EntityType.EXPERIENCE_ORB;
         case FIREWORK:
            return EntityType.FIREWORK;
         case ARMOR_STAND:
            return EntityType.ARMOR_STAND;
         case END_CRYSTAL:
            return EntityType.ENDER_CRYSTAL;
         case SKULL_ITEM:
            if (itemStack.getDurability() == 3) {
               return EntityType.PLAYER;
            } else {
               return itemStack.getDurability() == 1 ? EntityType.WITHER : null;
            }
         }
      }).filter(Objects::nonNull).collect(Collectors.toList());
   }
}
