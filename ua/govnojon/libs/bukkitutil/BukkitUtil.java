package ua.govnojon.libs.bukkitutil;

import java.util.Objects;
import javax.annotation.Nullable;
import net.mineland.core.bukkit.modules.nms.NMS;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;

public class BukkitUtil {
   public static final int MIN_COORD = -29999999;
   public static final int MAX_COORD = 29999999;

   public static void updateChunk(Chunk chunk) {
      NMS.getManagerSend().sendChunk(chunk);
   }

   public static boolean isLoadChunk(Player player, Chunk chunk) {
      return isLoadChunk(player, chunk.getX(), chunk.getZ());
   }

   public static boolean isLoadChunk(Player player, Location location) {
      return isLoadChunk(player, location.getBlockX() >> 4, location.getBlockZ() >> 4);
   }

   public static boolean isLoadChunk(Player player, int x, int z) {
      Location location = player.getLocation();
      int player_x = location.getBlockX() >> 4;
      int player_z = location.getBlockZ() >> 4;
      int vec_x = x - player_x;
      int vec_z = z - player_z;
      int distance = (int)Math.sqrt((double)(vec_x * vec_x + vec_z * vec_z));
      return distance <= Bukkit.getServer().getViewDistance();
   }

   public static boolean equalsIgnoreAmount(ItemStack item1, ItemStack item2) {
      if (!ItemStackUtil.isNullOrAir(item1) && !ItemStackUtil.isNullOrAir(item2)) {
         if (item1.isSimilar(item2)) {
            return true;
         } else {
            return item1.getType() == item2.getType() && item1.getDurability() == item2.getDurability() && Objects.equals(item1.getItemMeta(), item2.getItemMeta());
         }
      } else {
         return false;
      }
   }

   public static Material getMaterial(String nameOrId) {
      try {
         Material material = Material.getMaterial(nameOrId.toUpperCase());
         return material == null ? Material.getMaterial(Integer.parseInt(nameOrId)) : material;
      } catch (Exception var2) {
         return null;
      }
   }

   public static void cancelTask(@Nullable BukkitTask task) {
      if (task != null) {
         try {
            task.cancel();
         } catch (Exception var2) {
         }
      }

   }

   public static void cancelTask(@Nullable BukkitRunnable task) {
      if (task != null) {
         try {
            task.cancel();
         } catch (Exception var2) {
         }
      }

   }

   public static void cancelTask(@Nullable Schedule task) {
      if (task != null) {
         try {
            task.cancel();
         } catch (Exception var2) {
         }
      }

   }

   public static Color valueOfColor(String name) {
      byte var2 = -1;
      switch(name.hashCode()) {
      case -2027972496:
         if (name.equals("MAROON")) {
            var2 = 5;
         }
         break;
      case -1955522002:
         if (name.equals("ORANGE")) {
            var2 = 16;
         }
         break;
      case -1923613764:
         if (name.equals("PURPLE")) {
            var2 = 15;
         }
         break;
      case -1848981747:
         if (name.equals("SILVER")) {
            var2 = 1;
         }
         break;
      case -1680910220:
         if (name.equals("YELLOW")) {
            var2 = 6;
         }
         break;
      case 81009:
         if (name.equals("RED")) {
            var2 = 4;
         }
         break;
      case 2016956:
         if (name.equals("AQUA")) {
            var2 = 10;
         }
         break;
      case 2041946:
         if (name.equals("BLUE")) {
            var2 = 12;
         }
         break;
      case 2196067:
         if (name.equals("GRAY")) {
            var2 = 2;
         }
         break;
      case 2336725:
         if (name.equals("LIME")) {
            var2 = 8;
         }
         break;
      case 2388918:
         if (name.equals("NAVY")) {
            var2 = 13;
         }
         break;
      case 2570844:
         if (name.equals("TEAL")) {
            var2 = 11;
         }
         break;
      case 63281119:
         if (name.equals("BLACK")) {
            var2 = 3;
         }
         break;
      case 68081379:
         if (name.equals("GREEN")) {
            var2 = 9;
         }
         break;
      case 75295163:
         if (name.equals("OLIVE")) {
            var2 = 7;
         }
         break;
      case 82564105:
         if (name.equals("WHITE")) {
            var2 = 0;
         }
         break;
      case 198329015:
         if (name.equals("FUCHSIA")) {
            var2 = 14;
         }
      }

      switch(var2) {
      case 0:
         return Color.WHITE;
      case 1:
         return Color.SILVER;
      case 2:
         return Color.GRAY;
      case 3:
         return Color.BLACK;
      case 4:
         return Color.RED;
      case 5:
         return Color.MAROON;
      case 6:
         return Color.YELLOW;
      case 7:
         return Color.OLIVE;
      case 8:
         return Color.LIME;
      case 9:
         return Color.GREEN;
      case 10:
         return Color.AQUA;
      case 11:
         return Color.TEAL;
      case 12:
         return Color.BLUE;
      case 13:
         return Color.NAVY;
      case 14:
         return Color.FUCHSIA;
      case 15:
         return Color.PURPLE;
      case 16:
         return Color.ORANGE;
      default:
         throw new IllegalArgumentException("No constant " + Color.class.getCanonicalName() + "." + name);
      }
   }
}
