package net.mineland.core.bukkit.modules.nms;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import java.util.HashMap;
import net.mineland.core.bukkit.module.BukkitModule;
import net.mineland.core.bukkit.modules.nms.myentity.MyEntity;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;
import ua.govnojon.libs.config.Config;

public class ModuleNMS extends BukkitModule {
   private static ModuleNMS instance;
   private static NMS nms;
   private static NMSItemMap nmsItemMap;
   private static NMSSend nmsSend;
   private static NMSSingle nmsSingle;
   public final String VERSION = Bukkit.getServer().getClass().getName().split("\\.")[3];
   private HashMap<Class<? extends MyEntity>, Short> idTypeMap = new HashMap();

   public ModuleNMS(int priority, Plugin plugin) {
      super("nms", priority, plugin, (Config)null);
      instance = this;
   }

   public static ModuleNMS getInstance() {
      return instance;
   }

   public static NMSSend getNmsSend() {
      return nmsSend;
   }

   public static NMSItemMap getNmsItemMap() {
      return nmsItemMap;
   }

   public static NMS getNmsManager() {
      return nms;
   }

   public static NMSSingle getNmsSingle() {
      return nmsSingle;
   }

   public void onFirstEnable() {
      this.registerData(new PacketAdapter(this.getPlugin(), new PacketType[]{Server.SPAWN_ENTITY_LIVING}) {
         public void onPacketSending(PacketEvent event) {
            Entity entity = ((WrappedDataWatcher)event.getPacket().getDataWatcherModifier().read(0)).getEntity();
            if (entity != null) {
               Object hundleEntity = NMS.getManager().getHandle(entity);
               if (hundleEntity instanceof MyEntity) {
                  Short id = (Short)ModuleNMS.getInstance().idTypeMap.get(((MyEntity)hundleEntity).getClass());
                  if (id != null) {
                     event.getPacket().getIntegers().write(1, Integer.valueOf(id));
                  }
               }
            }

         }
      });
   }

   public void onEnable() {
      nms = new NMS();
      nmsItemMap = new NMSItemMap();
      nmsSend = new NMSSend();
      nmsSingle = new NMSSingle();
      this.getLogger().info("Версия пакетов '" + this.VERSION + "'.");
   }

   public void onDisable() {
   }

   public void onReload() {
   }

   public void registerEntity(Class<? extends MyEntity> entityClass, EntityType type) {
      this.idTypeMap.put(entityClass, type.getTypeId());
   }
}
