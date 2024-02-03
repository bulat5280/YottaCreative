package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.AutoWrapper;
import com.comphenix.protocol.wrappers.BukkitConverters;
import com.comphenix.protocol.wrappers.Converters;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.MinecraftKey;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.bukkit.advancement.Advancement;
import org.bukkit.inventory.ItemStack;

public class WrapperPlayServerAdvancements extends AbstractPacket {
   public static final PacketType TYPE;
   private static final AutoWrapper<WrapperPlayServerAdvancements.AdvancementDisplay> DISPLAY;
   private static final AutoWrapper<WrapperPlayServerAdvancements.SerializedAdvancement> WRAPPER;
   private static final AutoWrapper<WrapperPlayServerAdvancements.CriterionProgress> CRITERION;
   private static final AutoWrapper<WrapperPlayServerAdvancements.AdvancementProgress> PROGRESS;

   public WrapperPlayServerAdvancements() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerAdvancements(PacketContainer packet) {
      super(packet, TYPE);
   }

   public boolean isReset() {
      return (Boolean)this.handle.getBooleans().read(0);
   }

   public void setReset(boolean value) {
      this.handle.getBooleans().write(0, value);
   }

   public Optional<Map<MinecraftKey, WrapperPlayServerAdvancements.SerializedAdvancement>> getAdvancements() {
      return this.handle.getMaps(MinecraftKey.getConverter(), WRAPPER).optionRead(0);
   }

   public void setAdvancements(Map<MinecraftKey, WrapperPlayServerAdvancements.SerializedAdvancement> value) {
      this.handle.getMaps(MinecraftKey.getConverter(), WRAPPER).writeSafely(0, value);
   }

   public Optional<Set<MinecraftKey>> getKeys() {
      return this.handle.getSets(MinecraftKey.getConverter()).optionRead(0);
   }

   public void setKeys(Set<MinecraftKey> value) {
      this.handle.getSets(MinecraftKey.getConverter()).writeSafely(0, value);
   }

   public Optional<Map<MinecraftKey, WrapperPlayServerAdvancements.AdvancementProgress>> getProgress() {
      return this.handle.getMaps(MinecraftKey.getConverter(), PROGRESS).optionRead(1);
   }

   static {
      TYPE = Server.ADVANCEMENTS;
      DISPLAY = AutoWrapper.wrap(WrapperPlayServerAdvancements.AdvancementDisplay.class, "AdvancementDisplay").field(0, BukkitConverters.getWrappedChatComponentConverter()).field(1, BukkitConverters.getWrappedChatComponentConverter()).field(2, BukkitConverters.getItemStackConverter()).field(3, MinecraftKey.getConverter()).field(4, EnumWrappers.getGenericConverter(MinecraftReflection.getMinecraftClass("AdvancementFrameType"), WrapperPlayServerAdvancements.FrameType.class));
      WRAPPER = AutoWrapper.wrap(WrapperPlayServerAdvancements.SerializedAdvancement.class, "Advancement$SerializedAdvancement").field(0, MinecraftKey.getConverter()).field(1, BukkitConverters.getAdvancementConverter()).field(2, DISPLAY);
      CRITERION = AutoWrapper.wrap(WrapperPlayServerAdvancements.CriterionProgress.class, "CriterionProgress");
      PROGRESS = AutoWrapper.wrap(WrapperPlayServerAdvancements.AdvancementProgress.class, "AdvancementProgress").field(0, BukkitConverters.getMapConverter(Converters.passthrough(String.class), CRITERION));
      CRITERION.field(0, PROGRESS);
   }

   public static class CriterionProgress {
      public WrapperPlayServerAdvancements.AdvancementProgress progress;
      public Date date;
   }

   public static class AdvancementProgress {
      public Map<String, WrapperPlayServerAdvancements.CriterionProgress> progress;
      public String[][] array2d;
   }

   public static class AdvancementDisplay {
      public WrappedChatComponent title;
      public WrappedChatComponent description;
      public ItemStack icon;
      public MinecraftKey background;
      public WrapperPlayServerAdvancements.FrameType frame;
      public boolean showToast;
      public boolean announceToChat;
      public boolean hidden;
      public float xCoord;
      public float yCoord;
   }

   public static class SerializedAdvancement {
      public MinecraftKey key;
      public Advancement advancement;
      public WrapperPlayServerAdvancements.AdvancementDisplay display;
      public Object rewards;
      public Map<String, Object> criteria;
      public String[][] requirements;
   }

   public static enum FrameType {
      TASK,
      CHALLENGE,
      GOAL;
   }
}
