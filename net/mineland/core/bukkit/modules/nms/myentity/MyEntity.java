package net.mineland.core.bukkit.modules.nms.myentity;

import net.mineland.core.bukkit.modules.nms.NMS;
import org.bukkit.entity.Entity;

public interface MyEntity {
   default Entity spawn() {
      Entity bukkitEntity = this.getBukkitEntity();
      NMS.getManager().addEntityToWorld(bukkitEntity);
      return bukkitEntity;
   }

   Entity getBukkitEntity();
}
