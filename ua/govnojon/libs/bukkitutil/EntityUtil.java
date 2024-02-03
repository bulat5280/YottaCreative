package ua.govnojon.libs.bukkitutil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.mineland.core.bukkit.LibsPlugin;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.nms.NMS;
import net.mineland.core.bukkit.modules.region.ModuleRegion;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;

public class EntityUtil {
   private static final int range = 10000;
   public static int lastId = 0;
   public static int itId = 0;
   private static ModuleRegion moduleRegion = (ModuleRegion)Module.getInstance(ModuleRegion.class);
   private static Field entityCount = load0();

   private static Field load0() {
      try {
         Field field = Class.forName("net.minecraft.server." + NMS.getModuleNMS().VERSION + ".Entity").getDeclaredField("entityCount");
         field.setAccessible(true);
         return field;
      } catch (NoSuchFieldException | ClassNotFoundException var1) {
         var1.printStackTrace();
         return null;
      }
   }

   @Nullable
   public static Entity getDamager(Entity entity) {
      if (entity instanceof Projectile) {
         Projectile projectile = (Projectile)entity;
         return projectile.getShooter() instanceof Entity ? (Entity)projectile.getShooter() : null;
      } else if (entity instanceof AreaEffectCloud) {
         AreaEffectCloud effectCloud = (AreaEffectCloud)entity;
         if (!moduleRegion.hasNegativePotion(effectCloud)) {
            return null;
         } else {
            return effectCloud.getSource() instanceof Entity ? (Entity)effectCloud.getSource() : null;
         }
      } else {
         return entity;
      }
   }

   public static int nextEntityId() {
      checkNewIds(1);
      return itId++;
   }

   public static List<Integer> nextEntityId(int count) {
      checkNewIds(count);
      return (List)IntStream.range(itId, itId += count).boxed().collect(Collectors.toList());
   }

   private static void checkNewIds(int count) {
      if (itId + count > lastId) {
         try {
            Integer entityCount = (Integer)EntityUtil.entityCount.get((Object)null);
            EntityUtil.entityCount.set((Object)null, lastId = entityCount + 10000 + count);
            itId = entityCount;
         } catch (IllegalAccessException var2) {
            var2.printStackTrace();
            throw new RuntimeException();
         }
      }

   }

   public static void setMetadata(Metadatable entity, String key, Object value) {
      getMetadata(entity).put(key, value);
   }

   public static <T> T removeMetadata(Metadatable entity, String key) {
      return getMetadata(entity).remove(key);
   }

   public static <T> T getMetadata(Metadatable entity, String key) {
      return getMetadata(entity).get(key);
   }

   public static boolean hasMetadata(Metadatable entity, String key) {
      return getMetadata(entity).containsKey(key);
   }

   public static Map<String, Object> getMetadata(Metadatable entity) {
      List<MetadataValue> metadata = entity.getMetadata("metadata-kek");
      Object result;
      if (metadata.isEmpty()) {
         entity.setMetadata("metadata-kek", new FixedMetadataValue(LibsPlugin.getInstance(), result = new HashMap()));
      } else {
         result = (Map)((MetadataValue)metadata.get(0)).value();
      }

      return (Map)result;
   }

   public static void fireWorkBall(Location loc) {
      Firework fw = (Firework)loc.getWorld().spawn(loc, Firework.class);
      FireworkMeta fm = fw.getFireworkMeta();
      fm.addEffects(new FireworkEffect[]{FireworkEffect.builder().with(Type.BALL_LARGE).withColor(Color.RED).withColor(Color.AQUA).withColor(Color.ORANGE).withColor(Color.YELLOW).trail(false).flicker(false).build()});
      fm.setPower(0);
      fw.setFireworkMeta(fm);
      detonate(fw);
   }

   private static void detonate(Firework firework) {
      Schedule.later(firework::detonate, 2L);
   }

   public static double getMaxHealth(Attributable attributable) {
      return attributable.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
   }
}
