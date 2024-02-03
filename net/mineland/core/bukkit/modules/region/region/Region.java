package net.mineland.core.bukkit.modules.region.region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import net.mineland.core.bukkit.modules.region.ModuleRegion;
import net.mineland.core.bukkit.modules.region.region.flag.FlagType;
import net.mineland.core.bukkit.modules.region.region.territory.Point;
import net.mineland.core.bukkit.modules.region.region.territory.Territory;
import net.mineland.core.bukkit.modules.region.region.territory.TerritoryType;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import ua.govnojon.libs.config.Config;

public class Region {
   private static ModuleRegion moduleRegion = ModuleRegion.getInstance();
   public Config config;
   public boolean save = false;
   private String name;
   private int priority = 0;
   private Territory territory;
   private Map<FlagType, String> flags = new HashMap();
   private List<String> members = new LinkedList();
   private HashMap<String, Object> metadata = new HashMap();

   public Region(String name, Territory territory) {
      this.territory = territory;
      this.name = name;
   }

   public Region(String name, Territory territory, List<String> members) {
      this.name = name;
      this.territory = territory;
      this.members.addAll(members);
   }

   public Region(Config config, String name) {
      this.config = config;
      this.name = name;
      String path = "regions." + name + ".";
      TerritoryType type = TerritoryType.forName(config.getString(path + "type"));
      ArrayList<String> pointDatas = (ArrayList)config.getStringList(path + "points");
      Point[] points = new Point[pointDatas.size()];
      int i = 0;

      Iterator var8;
      String flag;
      String[] data;
      for(var8 = pointDatas.iterator(); var8.hasNext(); points[i++] = new Point(Bukkit.getWorld(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]))) {
         flag = (String)var8.next();
         data = flag.split(":");
      }

      this.territory = new Territory(type, points);
      var8 = config.getStringList(path + "flags").iterator();

      while(var8.hasNext()) {
         flag = (String)var8.next();

         try {
            data = flag.split(":");
            if (!data[1].equalsIgnoreCase("NONE")) {
               FlagType flagType = FlagType.forName(data[0]);
               if (flagType == null) {
                  moduleRegion.getLogger().severe("У региона '" + this.name + "' флаг '" + data[0] + "' не существует.");
               } else {
                  this.setFlag(flagType, data[1]);
               }
            } else {
               moduleRegion.getLogger().warning("У региона '" + this.name + "' флаг '" + data[0] + "' не имеет значения - 'NONE'.");
            }
         } catch (Exception var12) {
            ModuleRegion.getInstance().getLogger().severe("Ошибка при загрузке флага '" + flag + "' региона '" + this.name + "'.");
            var12.printStackTrace();
         }
      }

      this.members.addAll(config.getStringList(path + "members"));
      this.priority = config.getInt(path + "priority");
   }

   public Region(Config config, String name, String customName, World customWorld) {
      this.config = config;
      this.name = customName;
      String path = "regions." + name + ".";
      TerritoryType type = TerritoryType.forName(config.getString(path + "type"));
      ArrayList<String> pointDatas = (ArrayList)config.getStringList(path + "points");
      Point[] points = new Point[pointDatas.size()];
      int i = 0;

      Iterator var10;
      String flag;
      String[] data;
      for(var10 = pointDatas.iterator(); var10.hasNext(); points[i++] = new Point(customWorld, Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]))) {
         flag = (String)var10.next();
         data = flag.split(":");
      }

      this.territory = new Territory(type, points);
      var10 = config.getStringList(path + "flags").iterator();

      while(var10.hasNext()) {
         flag = (String)var10.next();

         try {
            data = flag.split(":");
            if (!data[1].equalsIgnoreCase("NONE")) {
               FlagType flagType = FlagType.forName(data[0]);
               if (flagType == null) {
                  moduleRegion.getLogger().severe("У региона 'world' флаг '" + data[0] + "' не существует.");
               } else {
                  this.setFlag(flagType, data[1]);
               }
            } else {
               moduleRegion.getLogger().warning("У региона 'world' флаг '" + data[0] + "' не имеет значения - 'NONE'.");
            }
         } catch (Exception var14) {
            ModuleRegion.getInstance().getLogger().severe("Ошибка при загрузке флага '" + flag + "' региона 'world'.");
            var14.printStackTrace();
         }
      }

      this.members.addAll(config.getStringList(path + "members"));
      this.priority = config.getInt(path + "priority");
   }

   /** @deprecated */
   @Deprecated
   public static void save(Region region, Config config) {
      region.save = true;
      region.config = config;
      region.updateMembers();
      region.updateFlags();
      region.config.set("regions." + region.name + ".type", region.territory.getType().name());
      List<String> points = new LinkedList();
      Point[] var3 = region.territory.getPoints();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Point point = var3[var5];
         points.add(point.getWorld().getName() + ":" + point.getX() + ":" + point.getY() + ":" + point.getZ());
      }

      region.config.set("regions." + region.name + ".points", points);
      region.config.set("regions." + region.name + ".priority", region.priority);
      region.config.save();
   }

   public boolean setFlag(FlagType type, String value) {
      return this.setFlag(type, value, true);
   }

   public boolean setFlag(FlagType type, String value, boolean save) {
      if (!type.isEmptyValues() && !type.isContainsValue(value)) {
         ModuleRegion.getInstance().getLogger().severe("Значение '" + value + "' флага '" + type.name() + "' не найдено.");
         return false;
      } else if (Objects.equals(value, this.flags.get(type))) {
         return false;
      } else {
         if (type.equals(FlagType.DENY_SPAWN)) {
            value = value.toUpperCase();
         }

         this.flags.put(type, value);
         Iterator var4;
         Entity entity;
         if (type.equals(FlagType.TIME)) {
            var4 = this.getEntities().iterator();

            while(var4.hasNext()) {
               entity = (Entity)var4.next();
               if (entity.getType().equals(EntityType.PLAYER)) {
                  moduleRegion.updatePlayerTime((Player)entity, moduleRegion.getFlagValue(entity.getLocation(), FlagType.TIME));
               }
            }
         } else if (type.equals(FlagType.WEATHER)) {
            var4 = this.getEntities().iterator();

            while(var4.hasNext()) {
               entity = (Entity)var4.next();
               if (entity.getType().equals(EntityType.PLAYER)) {
                  moduleRegion.updatePlayerWeather((Player)entity, moduleRegion.getFlagValue(entity.getLocation(), FlagType.WEATHER));
               }
            }
         }

         if (save) {
            this.updateFlags();
         }

         return true;
      }
   }

   public void removeFlag(FlagType type) {
      this.flags.remove(type);
      this.updateFlags();
   }

   public Territory getTerritory() {
      return this.territory;
   }

   public Map<FlagType, String> getFlags() {
      return this.flags;
   }

   public String getFlagValue(FlagType type) {
      return (String)this.flags.get(type);
   }

   public String getName() {
      return this.name;
   }

   public int getPriority() {
      return this.priority;
   }

   public void setPriority(int priority) {
      this.priority = priority;
      if (this.save) {
         this.config.setAndSave("regions." + this.name + ".priority", priority);
      }

   }

   public boolean isMember(Player player) {
      return this.isMember(User.getUser(player));
   }

   public boolean isMember(User user) {
      return user == null ? false : this.isMember(user.getName());
   }

   private boolean isMember(String idPlayer) {
      return this.members.contains(idPlayer);
   }

   public List<String> getMembers() {
      return this.members;
   }

   public void addPlayer(User user) {
      this.addPlayer(user.getName());
   }

   public void addPlayer(String idPlayer) {
      if (!this.isMember(idPlayer)) {
         this.members.add(idPlayer);
         this.updateMembers();
      }

   }

   public void removePlayer(User user) {
      this.removePlayer(user.getName());
   }

   public void removePlayer(String idPlayer) {
      this.members.remove(idPlayer);
      this.updateMembers();
   }

   public void updateFlags() {
      if (this.save) {
         List<String> flags = new LinkedList();
         Iterator var2 = this.flags.entrySet().iterator();

         while(var2.hasNext()) {
            Entry<FlagType, String> flag = (Entry)var2.next();
            flags.add(((FlagType)flag.getKey()).name() + ":" + (String)flag.getValue());
         }

         this.config.setAndSave("regions." + this.name + ".flags", flags);
      }

   }

   public boolean isSave() {
      return this.save;
   }

   public void setSave(boolean save) {
      this.save = save;
   }

   public void updateMembers() {
      if (this.save) {
         List<String> members = new LinkedList(this.members);
         this.config.setAndSave("regions." + this.name + ".members", members);
      }

   }

   public HashMap<String, Object> getMetadata() {
      return this.metadata;
   }

   public Config getConfig() {
      return this.config;
   }

   public Collection<Entity> getEntities() {
      return this.territory.getEntities();
   }

   public void setBiome(Biome biome) {
      this.territory.setBiome(biome);
   }

   public List<int[]> getCoordChunks() {
      return this.territory.getCoordChunks();
   }

   public List<Chunk> getChunks() {
      List<Chunk> chunks = new LinkedList();
      World world = this.territory.getWorld();
      Iterator var3 = this.getCoordChunks().iterator();

      while(var3.hasNext()) {
         int[] coord = (int[])var3.next();
         chunks.add(world.getChunkAt(coord[0], coord[1]));
      }

      return chunks;
   }

   public World getWorld() {
      return this.territory.getWorld();
   }

   public void registerRegion() {
      moduleRegion.registerRegion(this);
   }

   public void unregisterRegion() {
      moduleRegion.unregisterRegion(this);
   }
}
