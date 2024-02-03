package net.mineland.core.bukkit.modules.nms;

import net.minecraft.server.v1_12_R1.EntityInsentient;
import net.minecraft.server.v1_12_R1.EntityLiving;

public class NMSWrapper {
   public static void moveEntityTo(EntityInsentient entity, double x, double y, double z, double speed) {
      entity.getNavigation().a(x, y, z, speed);
   }

   public static boolean isEntityOnLadder(EntityLiving entity) {
      return entity.m_();
   }

   public static float getEntityMoveStrafing(EntityLiving entity) {
      return entity.be;
   }

   public static float getEntityMoveForward(EntityLiving entity) {
      return entity.bg;
   }

   public static float getEntityHeadYaw(EntityLiving entity) {
      return entity.aP;
   }

   public static void setEntityPersistent(EntityInsentient entity) {
      entity.cW();
   }
}
