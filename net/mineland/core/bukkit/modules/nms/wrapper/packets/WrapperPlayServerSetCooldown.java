package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.EquivalentConverter;
import com.comphenix.protocol.reflect.accessors.Accessors;
import com.comphenix.protocol.reflect.accessors.MethodAccessor;
import com.comphenix.protocol.utility.MinecraftReflection;
import org.bukkit.Material;

public class WrapperPlayServerSetCooldown extends AbstractPacket {
   public static final PacketType TYPE;
   private static final Class<?> ITEM_CLASS;

   public WrapperPlayServerSetCooldown() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerSetCooldown(PacketContainer packet) {
      super(packet, TYPE);
   }

   public Material getItem() {
      return (Material)this.handle.getModifier().withType(ITEM_CLASS, new WrapperPlayServerSetCooldown.ItemConverter()).read(0);
   }

   public void setItem(Material value) {
      this.handle.getModifier().withType(ITEM_CLASS, new WrapperPlayServerSetCooldown.ItemConverter()).write(0, value);
   }

   public int getTicks() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setTicks(int value) {
      this.handle.getIntegers().write(0, value);
   }

   static {
      TYPE = Server.SET_COOLDOWN;
      ITEM_CLASS = MinecraftReflection.getMinecraftClass("Item");
   }

   private static class ItemConverter implements EquivalentConverter<Material> {
      private static MethodAccessor getMaterial = null;
      private static MethodAccessor getItem = null;

      private ItemConverter() {
      }

      public Material getSpecific(Object generic) {
         if (getMaterial == null) {
            getMaterial = Accessors.getMethodAccessor(MinecraftReflection.getCraftBukkitClass("util.CraftMagicNumbers"), "getMaterial", new Class[]{WrapperPlayServerSetCooldown.ITEM_CLASS});
         }

         return (Material)getMaterial.invoke((Object)null, new Object[]{generic});
      }

      public Object getGeneric(Material specific) {
         if (getItem == null) {
            getItem = Accessors.getMethodAccessor(MinecraftReflection.getCraftBukkitClass("util.CraftMagicNumbers"), "getItem", new Class[]{Material.class});
         }

         return getItem.invoke((Object)null, new Object[]{specific});
      }

      public Class<Material> getSpecificType() {
         return Material.class;
      }

      // $FF: synthetic method
      ItemConverter(Object x0) {
         this();
      }
   }
}
