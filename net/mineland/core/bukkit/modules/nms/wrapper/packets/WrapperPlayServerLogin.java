package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.Difficulty;
import com.comphenix.protocol.wrappers.EnumWrappers.NativeGameMode;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Entity;

public class WrapperPlayServerLogin extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerLogin() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerLogin(PacketContainer packet) {
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

   public NativeGameMode getGamemode() {
      return (NativeGameMode)this.handle.getGameModes().read(0);
   }

   public void setGamemode(NativeGameMode value) {
      this.handle.getGameModes().write(0, value);
   }

   public int getDimension() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setDimension(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public Difficulty getDifficulty() {
      return (Difficulty)this.handle.getDifficulties().read(0);
   }

   public void setDifficulty(Difficulty value) {
      this.handle.getDifficulties().write(0, value);
   }

   public int getMaxPlayers() {
      return (Integer)this.handle.getIntegers().read(1);
   }

   public void setMaxPlayers(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public WorldType getLevelType() {
      return (WorldType)this.handle.getWorldTypeModifier().read(0);
   }

   public void setLevelType(WorldType value) {
      this.handle.getWorldTypeModifier().write(0, value);
   }

   public boolean getReducedDebugInfo() {
      return (Boolean)this.handle.getBooleans().read(0);
   }

   public void setReducedDebugInfo(boolean value) {
      this.handle.getBooleans().write(0, value);
   }

   static {
      TYPE = Server.LOGIN;
   }
}
