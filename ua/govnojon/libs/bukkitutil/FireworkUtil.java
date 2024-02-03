package ua.govnojon.libs.bukkitutil;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftFirework;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

public class FireworkUtil {
   public static Firework LaunchRandomFirework(Location location) {
      Builder builder = FireworkEffect.builder();
      if (RandomUtils.nextInt(3) == 0) {
         builder.withTrail();
      } else if (RandomUtils.nextInt(2) == 0) {
         builder.withFlicker();
      }

      builder.with(Type.values()[RandomUtils.nextInt(Type.values().length)]);
      int colorCount = 17;
      builder.withColor(Color.fromRGB(RandomUtils.nextInt(255), RandomUtils.nextInt(255), RandomUtils.nextInt(255)));

      while(RandomUtils.nextInt(colorCount) != 0) {
         builder.withColor(Color.fromRGB(RandomUtils.nextInt(255), RandomUtils.nextInt(255), RandomUtils.nextInt(255)));
         --colorCount;
      }

      Firework firework = (Firework)location.getWorld().spawn(location, Firework.class);
      FireworkMeta data = firework.getFireworkMeta();
      data.addEffects(new FireworkEffect[]{builder.build()});
      data.setPower(RandomUtils.nextInt(3));
      firework.setFireworkMeta(data);
      return firework;
   }

   private static void playFirework(Location loc, FireworkEffect fe) {
      Firework firework = (Firework)loc.getWorld().spawn(loc, Firework.class);
      FireworkMeta data = firework.getFireworkMeta();
      data.clearEffects();
      data.setPower(1);
      data.addEffect(fe);
      firework.setFireworkMeta(data);
      ((CraftFirework)firework).getHandle().expectedLifespan = 1;
   }

   public static Firework launchFirework(Location loc, FireworkEffect fe, Vector dir, int power) {
      try {
         Firework fw = (Firework)loc.getWorld().spawn(loc, Firework.class);
         FireworkMeta data = fw.getFireworkMeta();
         data.clearEffects();
         data.setPower(power);
         data.addEffect(fe);
         fw.setFireworkMeta(data);
         if (dir != null) {
            fw.setVelocity(dir);
         }

         return fw;
      } catch (Exception var6) {
         var6.printStackTrace();
         return null;
      }
   }

   public static Firework launchFirework(Location loc, Type type, Color color, boolean flicker, boolean trail, Vector dir, int power) {
      return launchFirework(loc, FireworkEffect.builder().flicker(flicker).withColor(color).with(type).trail(trail).build(), dir, power);
   }

   public static void playFirework(Location loc, Type type, Color color, boolean flicker, boolean trail) {
      playFirework(loc, FireworkEffect.builder().flicker(flicker).withColor(color).with(type).trail(trail).build());
   }

   public void detonateFirework(Firework firework) {
      ((CraftWorld)firework.getWorld()).getHandle().broadcastEntityEffect(((CraftEntity)firework).getHandle(), (byte)17);
      firework.remove();
   }
}
