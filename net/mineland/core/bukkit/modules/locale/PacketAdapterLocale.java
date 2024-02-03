package net.mineland.core.bukkit.modules.locale;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.nms.NMS;
import net.mineland.core.bukkit.modules.nms.wrapper.packets.WrapperPlayServerScoreboardTeam;
import net.mineland.core.bukkit.modules.user.Lang;
import net.mineland.core.bukkit.modules.user.User;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;
import ua.govnojon.libs.bukkitutil.chatcomponent.ChatComponentUtil;

public class PacketAdapterLocale extends PacketAdapter {
   private static ModuleLocale moduleLocale = (ModuleLocale)Module.getInstance(ModuleLocale.class);

   public PacketAdapterLocale(Plugin plugin) {
      super(plugin, ListenerPriority.HIGH, new PacketType[]{Server.SET_SLOT, Server.WINDOW_ITEMS, Server.OPEN_WINDOW, Server.SPAWN_ENTITY_LIVING, Server.ENTITY_METADATA, Server.PLAYER_INFO, Server.CHAT, Server.BOSS, Server.SCOREBOARD_TEAM});
   }

   public static String localizeString(Lang lang, String string) {
      if (string.startsWith("lang:")) {
         String[] split = string.substring(5).split(";");
         if (split.length > 2) {
            String[] replace = string.substring(5).replace(split[0] + ";", "").split(";");
            return Message.getMessage(lang, split[0], replace);
         } else {
            return Message.getMessage(lang, split[0]);
         }
      } else {
         return string;
      }
   }

   private PlayerInfoData checkTranslate(PlayerInfoData infoData, Lang lang) {
      WrappedGameProfile profile = infoData.getProfile();
      String name = profile.getName();
      if (name.startsWith("lang:")) {
         String message = localizeString(lang, name);
         message = StringUtils.left(message, 16);
         WrappedGameProfile gameProfile = profile.withName(message);
         gameProfile.getProperties().putAll(profile.getProperties());
         return new PlayerInfoData(gameProfile, infoData.getLatency(), infoData.getGameMode(), infoData.getDisplayName());
      } else {
         return null;
      }
   }

   public void onPacketSending(PacketEvent event) {
      PacketContainer packet = event.getPacket();
      PacketType type = event.getPacketType();
      User user = User.getUser(event.getPlayer());
      Lang lang = user != null ? user.getLang() : Lang.RU;
      StructureModifier chatComponents;
      if (type.equals(Server.SET_SLOT)) {
         chatComponents = packet.getItemModifier();
         ItemStack item = (ItemStack)chatComponents.read(0);
         if (!ItemStackUtil.isNullOrAir(item)) {
            ItemStack newItem = this.checkTranslate(item, lang);
            if (newItem != null) {
               chatComponents.write(0, newItem);
            }
         }
      } else {
         int i;
         List infoDatas;
         boolean update;
         if (type.equals(Server.WINDOW_ITEMS)) {
            chatComponents = packet.getItemListModifier();
            infoDatas = (List)chatComponents.read(0);
            update = false;

            for(i = 0; i < infoDatas.size(); ++i) {
               ItemStack item = (ItemStack)infoDatas.get(i);
               if (!ItemStackUtil.isNullOrAir(item)) {
                  ItemStack newItem = this.checkTranslate(item, lang);
                  if (newItem != null) {
                     infoDatas.set(i, newItem);
                     update = true;
                  }
               }
            }

            if (update) {
               chatComponents.write(0, infoDatas);
            }
         } else if (type.equals(Server.SPAWN_ENTITY_LIVING)) {
            chatComponents = packet.getDataWatcherModifier();
            WrappedDataWatcher dataWatcher = (WrappedDataWatcher)chatComponents.read(0);
            List<WrappedWatchableObject> objects = dataWatcher.getWatchableObjects();
            if (this.checkTranslate(objects, lang)) {
               chatComponents.write(0, new WrappedDataWatcher(objects));
            }
         } else if (type.equals(Server.ENTITY_METADATA)) {
            chatComponents = packet.getWatchableCollectionModifier();
            infoDatas = (List)chatComponents.read(0);
            if (this.checkTranslate(infoDatas, lang)) {
               chatComponents.write(0, infoDatas);
            }
         } else if (type.equals(Server.PLAYER_INFO)) {
            chatComponents = packet.getPlayerInfoDataLists();
            infoDatas = (List)chatComponents.read(0);
            update = false;

            for(i = 0; i < infoDatas.size(); ++i) {
               PlayerInfoData data = (PlayerInfoData)infoDatas.get(i);
               PlayerInfoData result = this.checkTranslate(data, lang);
               if (result != null) {
                  update = true;
                  infoDatas.set(i, result);
               }
            }

            if (update) {
               chatComponents.write(0, infoDatas);
            }
         } else {
            WrappedChatComponent chatComponent;
            String json;
            if (type.equals(Server.CHAT)) {
               chatComponents = packet.getChatComponents();
               chatComponent = (WrappedChatComponent)chatComponents.read(0);
               if (chatComponent != null) {
                  json = chatComponent.getJson();
                  if (json.startsWith("{\"extra\":[{\"text\":\"lang:")) {
                     chatComponent.setJson(ChatComponentUtil.textToJson(localizeString(lang, json.substring(19, json.length() - 14))));
                     chatComponents.write(0, chatComponent);
                  }
               }
            } else if (type.equals(Server.OPEN_WINDOW)) {
               chatComponents = packet.getChatComponents();
               chatComponent = (WrappedChatComponent)chatComponents.read(0);
               if (chatComponent != null) {
                  json = chatComponent.getJson();
                  if (json.startsWith("{\"text\":\"lang:")) {
                     chatComponent.setJson(ChatComponentUtil.textToJson(localizeString(lang, json.substring(9, json.length() - 2))));
                     chatComponents.write(0, chatComponent);
                  }
               }
            } else if (type.equals(Server.BOSS)) {
               chatComponents = packet.getChatComponents();
               chatComponent = (WrappedChatComponent)chatComponents.read(0);
               json = ChatComponentUtil.jsonToText(chatComponent.getJson());
               if (json.startsWith("lang:")) {
                  chatComponent.setJson(ChatComponentUtil.textToJson(localizeString(lang, json)));
                  chatComponents.write(0, chatComponent);
               }
            } else if (type.equals(Server.SCOREBOARD_TEAM)) {
               WrapperPlayServerScoreboardTeam team = new WrapperPlayServerScoreboardTeam(packet);
               List<String> players = new ArrayList(team.getPlayers());
               ListIterator<String> iterator = players.listIterator();
               boolean change = false;

               while(iterator.hasNext()) {
                  String next = (String)iterator.next();
                  if (next.startsWith("lang:")) {
                     iterator.set(localizeString(lang, next));
                     change = true;
                  }
               }

               if (change) {
                  team.setPlayers((List)players);
               }

               if (team.getDisplayName() != null && team.getDisplayName().startsWith("lang:")) {
                  team.setDisplayName(localizeString(lang, team.getDisplayName()));
               }

               if (team.getPrefix() != null && team.getPrefix().startsWith("lang:")) {
                  team.setPrefix(localizeString(lang, team.getPrefix()));
               }

               if (team.getSuffix() != null && team.getSuffix().startsWith("lang:")) {
                  team.setSuffix(localizeString(lang, team.getSuffix()));
               }
            }
         }
      }

   }

   private ItemStack checkTranslate(ItemStack item, Lang lang) {
      ItemMeta meta = item.getItemMeta();
      if (meta != null) {
         String name = meta.getDisplayName();
         if (name != null) {
            if (name.startsWith("lang:")) {
               item = item.clone();
               ItemStackUtil.setText(item, localizeString(lang, name));
            }

            if (name.startsWith("potion:")) {
               PotionEffectType effectType = PotionEffectType.getByName(name.substring(7));
               if (effectType != null) {
                  item = item.clone();
                  ItemStackUtil.setText(item, "§r" + moduleLocale.getNamePotion(effectType, lang));
               }
            }
         }

         List<String> lore = meta.getLore();
         if (lore != null && !lore.isEmpty()) {
            List<String> newLore = new LinkedList();
            lore.forEach((line) -> {
               newLore.add(localizeString(lang, line));
            });
            ItemMeta newMeta = item.getItemMeta();
            newMeta.setLore(newLore);
            item.setItemMeta(newMeta);
         }

         return item;
      } else {
         Material type = item.getType();
         switch(type) {
         case BLACK_SHULKER_BOX:
         case BLUE_SHULKER_BOX:
         case BROWN_SHULKER_BOX:
         case CYAN_SHULKER_BOX:
         case GRAY_SHULKER_BOX:
         case GREEN_SHULKER_BOX:
         case LIGHT_BLUE_SHULKER_BOX:
         case PINK_SHULKER_BOX:
         case MAGENTA_SHULKER_BOX:
         case LIME_SHULKER_BOX:
         case ORANGE_SHULKER_BOX:
         case PURPLE_SHULKER_BOX:
         case RED_SHULKER_BOX:
         case SILVER_SHULKER_BOX:
         case WHITE_SHULKER_BOX:
         case YELLOW_SHULKER_BOX:
            return NMS.getManagerSingle().checkTranslateBox(item, lang);
         default:
            return null;
         }
      }
   }

   private boolean checkTranslate(List<WrappedWatchableObject> objects, Lang lang) {
      boolean change = false;
      ListIterator iterator = objects.listIterator();

      while(iterator.hasNext()) {
         WrappedWatchableObject object = (WrappedWatchableObject)iterator.next();
         Object value = object.getValue();
         if (value instanceof String) {
            String name = (String)value;
            if (name.startsWith("lang:")) {
               String message = localizeString(lang, name);
               iterator.set(new WrappedWatchableObject(object.getWatcherObject(), message));
               change = true;
            }
         } else if (value instanceof ItemStack) {
            ItemStack stack = (ItemStack)value;
            ItemStack newItem = this.checkTranslate(stack, lang);
            if (newItem != null) {
               iterator.set(new WrappedWatchableObject(object.getWatcherObject(), NMS.getManager().asNMSCopy(newItem)));
               change = true;
            }
         }
      }

      return change;
   }
}
