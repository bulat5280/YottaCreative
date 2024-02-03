package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.CombatEventType;

public class WrapperPlayServerCombatEvent extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerCombatEvent() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerCombatEvent(PacketContainer packet) {
      super(packet, TYPE);
   }

   public CombatEventType getEvent() {
      return (CombatEventType)this.handle.getCombatEvents().read(0);
   }

   public void setEvent(CombatEventType value) {
      this.handle.getCombatEvents().write(0, value);
   }

   public int getDuration() {
      if (this.getEvent() != CombatEventType.END_COMBAT) {
         throw new IllegalStateException("Duration only exists for END_COMBAT");
      } else {
         return (Integer)this.handle.getIntegers().read(0);
      }
   }

   public void setDuration(int value) {
      if (this.getEvent() != CombatEventType.END_COMBAT) {
         throw new IllegalStateException("Duration only exists for END_COMBAT");
      } else {
         this.handle.getIntegers().write(0, value);
      }
   }

   public int getPlayerID() {
      if (this.getEvent() != CombatEventType.ENTITY_DIED) {
         throw new IllegalStateException("Player ID only exists for ENTITY_DEAD");
      } else {
         return (Integer)this.handle.getIntegers().read(0);
      }
   }

   public void setPlayerId(int value) {
      if (this.getEvent() != CombatEventType.ENTITY_DIED) {
         throw new IllegalStateException("Player ID only exists for ENTITY_DEAD");
      } else {
         this.handle.getIntegers().write(0, value);
      }
   }

   public int getEntityID() {
      CombatEventType event = this.getEvent();
      switch(event) {
      case END_COMBAT:
      case ENTITY_DIED:
         return (Integer)this.handle.getIntegers().read(1);
      default:
         throw new IllegalStateException("Entity ID does not exist for " + event);
      }
   }

   public void setEntityId(int value) {
      CombatEventType event = this.getEvent();
      switch(event) {
      case END_COMBAT:
      case ENTITY_DIED:
         this.handle.getIntegers().write(1, value);
      default:
         throw new IllegalStateException("Entity ID does not exist for " + event);
      }
   }

   public String getMessage() {
      if (this.getEvent() != CombatEventType.ENTITY_DIED) {
         throw new IllegalStateException("Message only exists for ENTITY_DEAD");
      } else {
         return (String)this.handle.getStrings().read(0);
      }
   }

   public void setMessage(String value) {
      if (this.getEvent() != CombatEventType.ENTITY_DIED) {
         throw new IllegalStateException("Message only exists for ENTITY_DEAD");
      } else {
         this.handle.getStrings().write(0, value);
      }
   }

   static {
      TYPE = Server.COMBAT_EVENT;
   }
}
