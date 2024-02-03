package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class WrapperPlayServerMount extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerMount() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerMount(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getEntityID() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setEntityID(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public Entity getEntity(World world) {
      return (Entity)this.handle.getEntityModifier(world).read(0);
   }

   public Entity getEntity(PacketEvent event) {
      return this.getEntity(event.getPlayer().getWorld());
   }

   public int[] getPassengerIds() {
      return (int[])this.handle.getIntegerArrays().read(0);
   }

   public void setPassengerIds(int[] value) {
      this.handle.getIntegerArrays().write(0, value);
   }

   public List<Entity> getPassengers(PacketEvent event) {
      return this.getPassengers(event.getPlayer().getWorld());
   }

   public List<Entity> getPassengers(World world) {
      int[] ids = this.getPassengerIds();
      List<Entity> passengers = new ArrayList();
      ProtocolManager manager = ProtocolLibrary.getProtocolManager();
      int[] var5 = ids;
      int var6 = ids.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         int id = var5[var7];
         Entity entity = manager.getEntityFromID(world, id);
         if (entity != null) {
            passengers.add(entity);
         }
      }

      return passengers;
   }

   public void setPassengers(List<Entity> value) {
      int[] array = new int[value.size()];

      for(int i = 0; i < value.size(); ++i) {
         array[i] = ((Entity)value.get(i)).getEntityId();
      }

      this.setPassengerIds(array);
   }

   static {
      TYPE = Server.MOUNT;
   }
}
