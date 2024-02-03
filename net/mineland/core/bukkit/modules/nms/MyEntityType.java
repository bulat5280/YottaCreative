package net.mineland.core.bukkit.modules.nms;

import net.mineland.core.bukkit.modules.nms.myentity.MyEntity;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public enum MyEntityType {
   private MyEntityType.CreateEntity createEntity;

   private MyEntityType(MyEntityType.CreateEntity createEntity, Class<? extends MyEntity> c, EntityType type) {
      this.createEntity = createEntity;
      ModuleNMS.getInstance().registerEntity(c, type);
   }

   public MyEntity createNewEntity(Location location) {
      return this.createEntity.create(location);
   }

   public interface CreateEntity {
      MyEntity create(Location var1);
   }
}
