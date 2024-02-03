package net.mineland.creative.modules.creative;

import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.EnumWrappers.NativeGameMode;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerInfoAction;
import com.comphenix.protocol.wrappers.EnumWrappers.WorldBorderAction;
import io.netty.util.internal.ConcurrentSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.gui.ModuleGui;
import net.mineland.core.bukkit.modules.mainitem.MainBar;
import net.mineland.core.bukkit.modules.mainitem.ModuleMainItem;
import net.mineland.core.bukkit.modules.mainitem.defaultmainitems.MainItemCommand;
import net.mineland.core.bukkit.modules.mainitem.defaultmainitems.MainItemOpenGui;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.mysql.SQL;
import net.mineland.core.bukkit.modules.nms.wrapper.packets.WrapperPlayServerPlayerInfo;
import net.mineland.core.bukkit.modules.nms.wrapper.packets.WrapperPlayServerWorldBorder;
import net.mineland.core.bukkit.modules.user.Lang;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.BlockParser;
import net.mineland.creative.modules.coding.CodeHandler;
import net.mineland.creative.modules.coding.Param;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.creative.generators.biomes.GeneratorType;
import net.mineland.creative.modules.creative.generators.biomes.PlotGenerator;
import net.mineland.creative.modules.creative.gui.GuiMenuMyWorlds;
import net.mineland.creative.modules.creative.gui.GuiMenuPlotsOnline;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jooq.Record;
import org.jooq.Result;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.PlayerUtil;
import ua.govnojon.libs.bukkitutil.chatcomponent.ChatComponentUtil;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;
import ua.govnojon.libs.myjava.Try;

public class PlotManager {
   private static ModuleMainItem moduleMainItem = (ModuleMainItem)Module.getInstance(ModuleMainItem.class);
   private static ModuleGui moduleGui = (ModuleGui)Module.getInstance(ModuleGui.class);
   private ModuleCreative moduleCreative;
   private Set<Plot> plots;

   PlotManager(ModuleCreative moduleCreative) {
      this.plots = new PlotSet(new ConcurrentSet(), moduleGui);
      this.moduleCreative = moduleCreative;
   }

   public Set<Plot> getPlots() {
      return this.plots;
   }

   public void createPlot(GeneratorType generatorType, User user, Consumer<Plot> consumer) {
      Consumer<PlotManager.PlotData> newLocConsumer = (plotData) -> {
         if (plotData.location == null) {
            Schedule.run(() -> {
               consumer.accept((Object)null);
            });
         } else {
            SQL.async((create) -> {
               create.execute("REPLACE INTO `" + this.moduleCreative.tablePlots + "`(`owner_id`, `location_x`, `location_z`, `size`, `id`, `node`, `last_activity`) VALUES (?,?,?,?,?,?,CURRENT_TIMESTAMP)", user.getName(), plotData.location.getBlockX(), plotData.location.getBlockZ(), PlotGenerator.getPlotSizeByUser(user), plotData.id, 0);
               create.execute("REPLACE INTO `" + this.moduleCreative.tablePlotsSettings + "`(`plot_id`) VALUES (?)", plotData.id);
               this.loadPlot0(plotData.id, consumer);
            });
         }

      };
      SQL.async((create) -> {
         Record record = create.fetchOne("SELECT `id`, `location_x`, `location_z`, `size`, `owner_id`\nFROM `" + this.moduleCreative.tablePlots + "` \nWHERE `is_removed`=TRUE \nORDER BY `last_activity` ASC \nLIMIT 1");
         Location location;
         PlotManager.PlotData plotData;
         if (record != null) {
            int newId = (Integer)record.get("id", Integer.TYPE);
            location = new Location(this.moduleCreative.getPlotWorld(), (Double)record.get("location_x", Double.TYPE), 255.0D, (Double)record.get("location_z", Double.TYPE));
            plotData = new PlotManager.PlotData(newId, location);
            generatorType.generate(user, location, true, () -> {
               newLocConsumer.accept(plotData);
            });
         } else {
            Record maxResult = create.fetchOne("SELECT `id`, `location_x`, `location_z`, `size` FROM `" + this.moduleCreative.tablePlots + "` WHERE `id`=(SELECT MAX(id) FROM `" + this.moduleCreative.tablePlots + "`)");
            if (maxResult != null) {
               location = this.getNewLocationSync(new Location(this.moduleCreative.getPlotWorld(), (double)(Integer)maxResult.get("location_x", Integer.TYPE), 255.0D, (double)(Integer)maxResult.get("location_z", Integer.TYPE)));
               if (location == null) {
                  Bukkit.broadcastMessage("CRITICAL ERROR: НЕ НАЙДЕНА ПОЗИЦИЯ ОСТРОВА! Позвать цианида срочно! Telegram: @XjCyan1de");
                  throw new NullPointerException("CRITICAL ERROR: НЕ НАЙДЕНА ПОЗИЦИЯ ОСТРОВА! Позвать цианида срочно! Telegram: @XjCyan1de");
               }

               plotData = new PlotManager.PlotData((Integer)maxResult.get("id", Integer.TYPE) + 1, location);
               generatorType.generate(user, location, false, () -> {
                  newLocConsumer.accept(plotData);
               });
            } else {
               location = new Location(this.moduleCreative.getPlotWorld(), 0.0D, 255.0D, 0.0D);
               plotData = new PlotManager.PlotData(0, location);
               generatorType.generate(user, location, false, () -> {
                  newLocConsumer.accept(plotData);
               });
            }
         }

      });
   }

   public void loadPlot(int id, Consumer<Plot> consumer) {
      SQL.async((create) -> {
         Consumer<PlotManager.PlotData> newLocConsumer = (plotData) -> {
            if (plotData.location == null) {
               Schedule.run(() -> {
                  consumer.accept((Object)null);
               });
            } else {
               this.loadPlot0(plotData.id, consumer);
            }

         };
         Record plot = create.fetchOne("SELECT `id`, `location_x`, `location_z`, `size`, `owner_id`FROM `" + this.moduleCreative.tablePlots + "` WHERE `id`=? LIMIT 1", id);
         if (plot != null) {
            int newId = (Integer)plot.get("id", Integer.TYPE);
            Location newPos = new Location(this.moduleCreative.getPlotWorld(), (Double)plot.get("location_x", Double.TYPE), 255.0D, (Double)plot.get("location_z", Double.TYPE));
            newLocConsumer.accept(new PlotManager.PlotData(newId, newPos));
         }

      });
   }

   private void loadPlot0(int id, Consumer<Plot> consumer) {
      SQL.async((c) -> {
         Plot plot = this.loadPlotSync(id);
         Schedule.run(() -> {
            this.parseCoding(plot);
            consumer.accept(plot);
         });
      });
   }

   private Plot loadPlotSync(int plotId) {
      Plot plot = (Plot)this.plots.stream().filter((p) -> {
         return p.getId() == plotId;
      }).findAny().orElse((Object)null);
      if (plot != null) {
         this.moduleCreative.getLogger().info("Остров уже загружен - " + plotId);
         return plot;
      } else {
         plot = new Plot(plotId);
         this.plots.add(plot);
         return plot;
      }
   }

   private Location getNewLocationSync(Location last) {
      ModuleCreative var10000 = this.moduleCreative;
      int distance = 400 * 2;
      Result<Record> records = SQL.getCreate().fetch(("SELECT `node`, `location_x`, `location_z`FROM `" + this.moduleCreative.tablePlots + "` WHERE `node` = " + 0 + " AND `location_x`>=%x%-%distance% AND `location_x`<=%x%+%distance% AND `location_z`>=%z%-%distance% AND `location_z`<=%z%+%distance%").replace("%x%", String.valueOf(last.getBlockX())).replace("%z%", String.valueOf(last.getBlockZ())).replace("%distance%", String.valueOf(distance)));
      int x = last.getBlockX();
      int z = last.getBlockZ();
      List<int[]> islands = new LinkedList();
      Iterator var7 = records.iterator();

      while(var7.hasNext()) {
         Record record = (Record)var7.next();
         islands.add(new int[]{(Integer)record.get("location_x", Integer.TYPE), (Integer)record.get("location_z", Integer.TYPE)});
      }

      if (this.containsCoord(islands, x, z + distance) && !this.containsCoord(islands, x + distance, z)) {
         x += distance;
      } else if (this.containsCoord(islands, x - distance, z) && !this.containsCoord(islands, x, z + distance)) {
         z += distance;
      } else if (this.containsCoord(islands, x, z - distance) && !this.containsCoord(islands, x - distance, z)) {
         x -= distance;
      } else if (this.containsCoord(islands, x + distance, z) && !this.containsCoord(islands, x, z - distance)) {
         z -= distance;
      } else {
         boolean xyContains = this.containsCoord(islands, x, z);
         if (xyContains && !this.containsCoord(islands, x + distance, z)) {
            x += distance;
         } else if (xyContains) {
            this.moduleCreative.getLogger().severe("НЕ СМОГЛИ НАЙТИ ПОЗИЦИЮ ДЛЯ НОВОГО ОСТРОВА");
            return null;
         }
      }

      last.setX((double)x);
      last.setZ((double)z);
      return last;
   }

   private boolean containsCoord(List<int[]> coords, int x, int z) {
      Iterator var4 = coords.iterator();

      int[] coord;
      do {
         if (!var4.hasNext()) {
            return false;
         }

         coord = (int[])var4.next();
      } while(coord[0] != x || coord[1] != z);

      return true;
   }

   @Nullable
   public Plot getPlot(Location location) {
      return (Plot)this.getPlots().stream().filter((p) -> {
         return p.inTerritory(location);
      }).findAny().orElse((Object)null);
   }

   @Nullable
   public Plot getPlot(int id) {
      return (Plot)this.getPlots().stream().filter((p) -> {
         return p.getId() == id;
      }).findFirst().orElse((Object)null);
   }

   @Nullable
   public Plot getCurrentPlot(User user) {
      return (Plot)user.getMetadata("current-plot");
   }

   public void teleportToLobby(User user, boolean clearInv) {
      Plot currentPlot = this.moduleCreative.getPlotManager().getCurrentPlot(user);
      if (currentPlot != null) {
         currentPlot.removePlayer(user);
      }

      Player player = user.getPlayer();
      PlayerUtil.setDefaultParameters(player, GameMode.ADVENTURE, clearInv);
      Try.ignore(() -> {
         user.removeMetadata("creative.plots");
         moduleGui.reloadGui(user, GuiMenuMyWorlds.class);
      });
      PlayerUtil.setDefaultParameters(player, GameMode.ADVENTURE);
      player.setAllowFlight(false);
      player.setAllowFlight(user.hasPermission("mineland.creative.lobby_fly"));
      player.sendTitle("", "", 1, 1, 1);
      player.sendActionBar("");
      player.teleport(this.moduleCreative.getLobbyLocation());
      player.setGliding(false);
      Schedule.later(() -> {
         PlayerUtil.setDefaultParameters(player, GameMode.ADVENTURE);
      }, 40L);
      MainBar mainBar = moduleMainItem.getMainBar(user);
      mainBar.clear();
      mainBar.addItem(new MainItemOpenGui(mainBar, new ItemData(Material.COMPASS), "creative.game_menu", 4, GuiMenuPlotsOnline.class)).setTranslate(true);
      mainBar.addItem(new MainItemCommand(mainBar, Material.DIAMOND, "§cДОНАТ", 0, "/donate"));
      mainBar.addItem(new MainItemCommand(mainBar, Material.BOOK, "§6МЕНЮ", 8, "/help"));
      mainBar.addItem(Material.PAPER, "creative.my_worlds", 5, (event) -> {
         event.setCancelled(true);
         moduleGui.openGui(user, GuiMenuMyWorlds.class);
      }).setTranslate(true);
      this.updateTab((Plot)null, user);
   }

   public void updateTab(Plot plot, User user) {
      if (plot != null) {
         User.getUsers().forEach((u) -> {
            if (!u.equals(user)) {
               if (plot.getOnlinePlayers().contains(u)) {
                  this.getTabPacket(user, NativeGameMode.SURVIVAL).sendPacket(u.getPlayer());
                  this.getTabPacket(u, NativeGameMode.SURVIVAL).sendPacket(user.getPlayer());
               } else {
                  this.getTabPacket(user, NativeGameMode.SPECTATOR).sendPacket(u.getPlayer());
                  this.getTabPacket(u, NativeGameMode.SPECTATOR).sendPacket(user.getPlayer());
               }
            }

         });
      } else {
         User.getUsers().forEach((u) -> {
            if (!u.equals(user)) {
               if (u.getPlayer().getWorld() == this.moduleCreative.getLobbyLocation().getWorld()) {
                  this.getTabPacket(user, NativeGameMode.SURVIVAL).sendPacket(u.getPlayer());
                  this.getTabPacket(u, NativeGameMode.SURVIVAL).sendPacket(user.getPlayer());
               } else {
                  this.getTabPacket(user, NativeGameMode.SPECTATOR).sendPacket(u.getPlayer());
                  this.getTabPacket(u, NativeGameMode.SPECTATOR).sendPacket(user.getPlayer());
               }
            }

         });
      }

   }

   private WrapperPlayServerPlayerInfo getTabPacket(User user, NativeGameMode gameMode) {
      PlayerInfoData playerInfoData = new PlayerInfoData(WrappedGameProfile.fromPlayer(user.getPlayer()), -1, gameMode, (WrappedChatComponent)null);
      WrapperPlayServerPlayerInfo packet = new WrapperPlayServerPlayerInfo();
      packet.setAction(PlayerInfoAction.UPDATE_GAME_MODE);
      packet.setData(Collections.singletonList(playerInfoData));
      return packet;
   }

   public void parseCoding(@NotNull Plot plot) {
      List<Location> positions = new LinkedList();
      Iterator var3 = plot.getChunksWithCode().iterator();

      while(var3.hasNext()) {
         Chunk chunk = (Chunk)var3.next();
         BlockState[] var5 = chunk.getTileEntities();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            BlockState blockState = var5[var7];
            Block block = blockState.getBlock();
            if (block.getType() == Material.WALL_SIGN) {
               switch(block.getRelative(BlockFace.SOUTH).getType()) {
               case EMERALD_BLOCK:
               case LAPIS_BLOCK:
               case DIAMOND_BLOCK:
                  Sign sign = (Sign)blockState;
                  String line = ChatComponentUtil.removeColors(sign.getLine(0));
                  if (line.startsWith("lang:coding.sign.")) {
                     positions.add(block.getLocation());
                  }
               }
            }
         }
      }

      List<Activator> activators = (new BlockParser(plot, positions)).parseActivators();
      if (plot.getCodeHandler() != null) {
         plot.getCodeHandler().cancelSchedulers();
         plot.getCodeHandler().setActivators(activators);
      } else {
         CodeHandler codeHandler = new CodeHandler(activators, plot);
         plot.registerCodeHandler(codeHandler);
      }

   }

   public void sendWorldBorderPacker(User user, Location center, int size) {
      WrapperPlayServerWorldBorder packet = new WrapperPlayServerWorldBorder();
      packet.setAction(WorldBorderAction.INITIALIZE);
      packet.setCenterX((double)center.getBlockX());
      packet.setCenterZ((double)center.getBlockZ());
      packet.setWarningTime(0);
      packet.setRadius((double)(size * 32));
      packet.setOldRadius((double)(size * 32));
      packet.setPortalTeleportBoundary(29999984);
      packet.sendPacket(user.getPlayer());
   }

   public String syncGetPlotDescription(int plotId, Param param, User user, Lang lang) {
      List<String> votes = new ArrayList();
      boolean isWorldsMenu = param != null;
      String playerName;
      if (isWorldsMenu) {
         Plot plot = (Plot)this.getPlots().stream().filter((p) -> {
            return p.getId() == plotId;
         }).findAny().orElse((Object)null);
         if (plot != null) {
            votes = plot.getVotes();
         }
      } else {
         Record settingsRecord = SQL.getCreate().fetchOne("SELECT * FROM `" + this.moduleCreative.tablePlotsSettings + "` WHERE `plot_id`=?", plotId);
         if (settingsRecord != null) {
            playerName = (String)settingsRecord.get("settings", String.class);
            if (playerName != null) {
               try {
                  param = new Param(playerName);
               } catch (Exception var11) {
                  this.moduleCreative.getLogger().severe("Ошибка парсинга настроек острова " + plotId + ", обнуляем их. Настройки: " + playerName);
                  var11.printStackTrace();
                  param = new Param();
               }
            } else {
               param = new Param();
            }

            votes = this.getVotesSync(plotId);
         } else {
            SQL.async((create) -> {
               create.execute("REPLACE INTO `" + this.moduleCreative.tablePlotsSettings + "`(`plot_id`) VALUES (?)", plotId);
            });
            param = new Param();
            votes = new ArrayList();
         }
      }

      String name = param.get("name");

      try {
         playerName = user.getPlayer().getDisplayName();
      } catch (NullPointerException var10) {
         playerName = "Unknown";
      }

      StringBuilder sb = new StringBuilder();
      sb.append(name != null && !name.isEmpty() ? name : Message.getMessage(lang, "creative.gui.plots.world", "{player}", playerName)).append("\n");
      if (isWorldsMenu) {
         sb.append(Message.getMessage(lang, "creative.gui.plots.author", "{author}", playerName)).append("\n");
      }

      sb.append(Message.getMessage(lang, "creative.gui.plots.votes", "{votes}", String.valueOf(((List)votes).size()))).append("\n");
      sb.append(Message.getMessage(lang, "creative.gui.plots.id", "{id}", String.valueOf(plotId)));
      return sb.toString();
   }

   public void getVotes(int plotId, Consumer<List<String>> consumer) {
      SQL.async((create) -> {
         Result<Record> records = create.fetch("SELECT `player_id` FROM `" + this.moduleCreative.tableVotes + "` WHERE `plot_id`=? AND `last_vote` > NOW() - INTERVAL 7 DAY", plotId);
         consumer.accept(records.map((record) -> {
            return (String)record.get("player_id", String.class);
         }));
      });
   }

   public List<String> getVotesSync(int plotId) {
      Result<Record> records = SQL.getCreate().fetch("SELECT `player_id` FROM `" + this.moduleCreative.tableVotes + "` WHERE `plot_id`=? AND `last_vote` > NOW() - INTERVAL 7 DAY", plotId);
      return records.map((record) -> {
         return (String)record.get("player_id", String.class);
      });
   }

   private class PlotData {
      private int id;
      private Location location;

      private PlotData(int id, Location location) {
         this.id = id;
         this.location = location;
      }

      // $FF: synthetic method
      PlotData(int x1, Location x2, Object x3) {
         this(x1, x2);
      }
   }
}
