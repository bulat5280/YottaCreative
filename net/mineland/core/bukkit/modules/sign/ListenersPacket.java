package net.mineland.core.bukkit.modules.sign;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import java.util.List;
import java.util.Objects;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import ua.govnojon.libs.bukkitutil.chatcomponent.ChatComponentUtil;

public class ListenersPacket extends PacketAdapter {
   private final String[] emptyLines = new String[]{"", "", "", ""};
   private PluginManager manager = Bukkit.getPluginManager();
   private ModuleSign moduleSign;

   public ListenersPacket(Plugin plugin, ModuleSign moduleSign) {
      super(plugin, ListenerPriority.NORMAL, new PacketType[]{Server.TILE_ENTITY_DATA, Server.MAP_CHUNK});
      this.moduleSign = moduleSign;
   }

   public void onPacketSending(PacketEvent event) {
      PacketContainer packet = event.getPacket();
      Player player = event.getPlayer();
      User user = User.getUser(player);
      StructureModifier modifier;
      if (packet.getType().equals(Server.TILE_ENTITY_DATA)) {
         modifier = packet.getNbtModifier();
         NbtCompound compound = (NbtCompound)modifier.read(0);
         if (Objects.equals(compound.getString("id"), "minecraft:sign")) {
            BlockPosition position = (BlockPosition)packet.getBlockPositionModifier().read(0);
            if (this.moduleSign.hasView(user.getPlayer().getLocation(), position.getX(), position.getY(), position.getZ())) {
               String[] lines = this.getLines(compound);
               this.send(lines, player);
               this.setNewText(lines, compound);
            } else {
               this.setNewText(this.emptyLines, compound);
            }

            modifier.write(0, compound);
         }
      } else if (packet.getType().equals(Server.MAP_CHUNK)) {
         modifier = packet.getSpecificModifier(List.class);
         List tiles = (List)modifier.read(0);
         StructureModifier<Integer> integers = packet.getIntegers();
         Location loc = user.getPlayer().getLocation();
         int i;
         Object next;
         NbtCompound compound;
         if (this.moduleSign.hasView(loc, (Integer)integers.read(0), (Integer)integers.read(1))) {
            for(i = 0; i < tiles.size(); ++i) {
               next = tiles.get(i);
               compound = NbtFactory.fromNMSCompound(next);
               if (Objects.equals(compound.getString("id"), "minecraft:sign")) {
                  if (this.moduleSign.hasView(loc, compound.getInteger("x"), compound.getInteger("y"), compound.getInteger("z"))) {
                     String[] lines = this.getLines(compound);
                     this.send(lines, player);
                     this.setNewText(lines, compound);
                     tiles.set(i, ((NbtWrapper)compound).getHandle());
                  } else {
                     this.setNewText(this.emptyLines, compound);
                     tiles.set(i, ((NbtWrapper)compound).getHandle());
                  }
               }
            }
         } else {
            for(i = 0; i < tiles.size(); ++i) {
               next = tiles.get(i);
               compound = NbtFactory.fromNMSCompound(next);
               if (Objects.equals(compound.getString("id"), "minecraft:sign")) {
                  this.setNewText(this.emptyLines, compound);
                  tiles.set(i, ((NbtWrapper)compound).getHandle());
               }
            }
         }

         modifier.write(0, tiles);
      }

   }

   private void setNewText(String[] lines, NbtCompound compound) {
      for(int i = 0; i < lines.length; ++i) {
         compound.put("Text" + (i + 1), ChatComponentUtil.textToJson(lines[i]));
      }

   }

   private String[] getLines(NbtCompound compound) {
      String[] lines = new String[]{compound.getString("Text1"), compound.getString("Text2"), compound.getString("Text3"), compound.getString("Text4")};

      for(int i = 0; i < 4; ++i) {
         lines[i] = ChatComponentUtil.jsonToText(lines[i]);
      }

      return lines;
   }

   private void send(String[] lines, Player player) {
      SignSendEvent event = new SignSendEvent(User.getUser(player), lines);
      this.manager.callEvent(event);
   }
}
