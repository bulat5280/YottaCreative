package net.mineland.core.bukkit.modules.sign;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import java.util.stream.Stream;
import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.module.BukkitModule;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.nms.NMS;
import net.mineland.core.bukkit.modules.tracker.ModuleTracker;
import net.mineland.core.bukkit.modules.tracker.TrackerPlayer;
import net.mineland.core.bukkit.modules.user.PlayerChangeLangMyEvent;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import ua.govnojon.libs.bukkitutil.LocationUtil;
import ua.govnojon.libs.bukkitutil.chatcomponent.ChatComponent;
import ua.govnojon.libs.bukkitutil.chatcomponent.Click;
import ua.govnojon.libs.bukkitutil.chatcomponent.Hover;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;
import ua.govnojon.libs.config.Config;

public class ModuleSign extends BukkitModule {
   static final String prefix = "[lang]";
   private ModuleTracker moduleTracker = (ModuleTracker)Module.getInstance(ModuleTracker.class);
   private Gson gson = new Gson();
   private TypeAdapter<ResetSign> resetSignAdapter;
   private TypeAdapter<SetTranslate> setTranslateAdapter;
   private int viewDistance;
   private int distanceUpdate;
   private TrackerPlayer trackerPlayer;
   private String setTranslateCommand;
   private String resetSignCommand;

   public ModuleSign(int priority, Plugin plugin) {
      super("sign", priority, plugin, new Config(plugin, "sign.yml"));
      this.resetSignAdapter = this.gson.getAdapter(ResetSign.class);
      this.setTranslateAdapter = this.gson.getAdapter(SetTranslate.class);
      this.setTranslateCommand = "set_translate:";
      this.resetSignCommand = "reset_sign:";
   }

   public void onFirstEnable() {
      this.registerListenersThis();
      this.registerData(new ListenersPacket(this.getPlugin(), this));
   }

   public void onEnable() {
      this.getConfig().setDescription("view-distance-chunks - стандартная дальность отрисовки объектов\ndistance-handlers - количетсво блоков, через которое будет обновлять Sign'ы");
      Config config = this.getConfig();
      int def = 16;
      if (config.contains("view-distance-chunks")) {
         def = config.getInt("view-distance-chunks") * 16;
         config.setAndSave("view-distance-chunks", (Object)null);
      }

      this.viewDistance = config.getOrSetNumber("view-distance-blocks", def).intValue();
      this.distanceUpdate = config.getOrSetNumber("distance-handlers", 4).intValue();
      if (this.trackerPlayer != null) {
         this.moduleTracker.unregisterTracker(this.trackerPlayer);
      }

      this.trackerPlayer = this.moduleTracker.registerTracker(this.distanceUpdate, (user, from, to) -> {
         if (to.getWorld().equals(from.getWorld())) {
            this.move(user, to, from);
         }

      });
   }

   public void onDisable() {
   }

   public boolean hasView(Location loc, int chunkX, int chunkZ) {
      return !LocationUtil.hasDistance2D((double)chunkX, (double)chunkZ, (double)(loc.getBlockX() >> 4), (double)(loc.getBlockZ() >> 4), (double)((this.viewDistance >> 4) + 1));
   }

   public boolean hasView(Location loc, int x, int y, int z) {
      return !LocationUtil.hasDistance(loc, (double)x, (double)y, (double)z, (double)this.viewDistance);
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void on(PlayerChangeLangMyEvent event) {
      Player player = event.getUser().getPlayer();
      NMS.getManagerSingle().sendSpawnAllSign(player, player.getLocation(), this.distanceUpdate);
   }

   @EventHandler(
      priority = EventPriority.LOW
   )
   public void on(PlayerInteractEvent event) {
      if (event.getHand() == null || event.getHand().equals(EquipmentSlot.HAND) && event.hasItem() && event.hasBlock() && event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getItem().getType().equals(Material.FEATHER) && event.getPlayer().hasPermission("mineland.libs.sign.lang")) {
         Block block = event.getClickedBlock();
         if (block.getType().equals(Material.WALL_SIGN) || block.getType().equals(Material.SIGN_POST)) {
            Sign sign = (Sign)block.getState();
            User user = User.getUser(event.getPlayer());
            if (sign.getLine(0).equals("[lang]")) {
               user.sendMessage("табличка.уже.переведена");
            } else {
               event.setCancelled(true);
               SetTranslate setTranslate = new SetTranslate(block);
               String data = this.setTranslateAdapter.toJson(setTranslate);
               ChatComponent component = new ChatComponent();
               String line = (String)Stream.of(sign.getLines()).filter((s) -> {
                  return !s.isEmpty();
               }).findFirst().orElse("");
               component.addText(Message.getMessage((IUser)user, "сделать.табличку.переводимой", "{line}", line), Hover.show_text, Message.getMessage((IUser)user, "сделать.табличку.переводимой.подсказка"), Click.run_command, this.setTranslateCommand + data);
               component.send(event.getPlayer());
            }
         }
      }

   }

   private void move(User user, Location toLoc, Location fromLoc) {
      NMS.getManagerSingle().sendSigns(user, toLoc, fromLoc, this.viewDistance);
   }

   @EventHandler(
      priority = EventPriority.MONITOR,
      ignoreCancelled = true
   )
   public void onSignChangeEvent(SignChangeEvent event) {
      if (!event.getLine(0).equals("[lang]") && !event.getLine(0).startsWith("lang:")) {
         if (!event.getLine(0).startsWith("[") && event.getPlayer().hasPermission("mineland.libs.sign.lang")) {
            User user = User.getUser(event.getPlayer());
            SetTranslate setTranslate = new SetTranslate(event.getBlock());
            String data = this.setTranslateAdapter.toJson(setTranslate);
            ChatComponent component = new ChatComponent();
            String line = (String)Stream.of(event.getLines()).filter((s) -> {
               return !s.isEmpty();
            }).findFirst().orElse("");
            component.addText(Message.getMessage((IUser)user, "сделать.табличку.переводимой", "{line}", line), Hover.show_text, Message.getMessage((IUser)user, "сделать.табличку.переводимой.подсказка"), Click.run_command, this.setTranslateCommand + data);
            component.send(event.getPlayer());
         }
      } else if (!event.getPlayer().hasPermission("mineland.libs.sign.lang")) {
         event.getPlayer().sendMessage(Message.getMessage(event.getPlayer(), "нет_прав"));
         event.getBlock().breakNaturally();
      }

      if (event.getPlayer().hasPermission("mineland.libs.sign.color")) {
         for(int i = 0; i < event.getLines().length; ++i) {
            event.setLine(i, ChatColor.translateAlternateColorCodes('&', event.getLine(i)));
         }
      }

   }

   @EventHandler(
      priority = EventPriority.MONITOR,
      ignoreCancelled = true
   )
   public void on(BlockBreakEvent event) {
      Block block = event.getBlock();
      Player player = event.getPlayer();
      if (block.getType().equals(Material.SIGN_POST) || block.getType().equals(Material.WALL_SIGN)) {
         User user = User.getUser(event.getPlayer());
         Sign sign = (Sign)block.getState();
         if (sign.getLine(0).equals("[lang]") && player.hasPermission("mineland.libs.sign.lang")) {
            ResetSign resetSign = new ResetSign(block, sign.getLine(1));
            String data = this.resetSignAdapter.toJson(resetSign);
            ChatComponent component = new ChatComponent();
            component.addText(Message.getMessage((IUser)user, "вы.сломали.табличку", "{key}", sign.getLine(1)), Hover.show_text, Message.getMessage((IUser)user, "вы.сломали.табличку.подсказка"), Click.run_command, this.resetSignCommand + data);
            component.send(player);
         }
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void on(AsyncPlayerChatEvent event) {
      User user = User.getUser(event.getPlayer());
      if (event.getMessage().startsWith(this.resetSignCommand)) {
         event.setCancelled(true);
         Schedule.run(() -> {
            try {
               String data = event.getMessage().substring(this.resetSignCommand.length());
               ResetSign resetSign = (ResetSign)this.resetSignAdapter.fromJson(data);
               resetSign.reset();
               user.sendMessage("табличка.восстановлена", "{key}", resetSign.getKey());
            } catch (Exception var5) {
            }

         });
      } else if (event.getMessage().startsWith(this.setTranslateCommand)) {
         event.setCancelled(true);
         Schedule.run(() -> {
            try {
               String data = event.getMessage().substring(this.setTranslateCommand.length());
               SetTranslate setTranslate = (SetTranslate)this.setTranslateAdapter.fromJson(data);
               if (setTranslate.isValid()) {
                  user.setMetadata("set.translate", setTranslate);
                  user.sendMessage("введите.ключ.сообщения.таблички", "{text}", setTranslate.getText().replace("\n", "\n §7"));
               }
            } catch (Exception var5) {
            }

         });
      } else {
         SetTranslate var3 = (SetTranslate)user.removeMetadata("set.translate");
      }

   }

   @EventHandler
   public void onSignSendEvent(SignSendEvent event) {
      String[] lines = event.getLines();
      String text;
      if (lines[0].equals("[lang]")) {
         text = Message.getMessage((IUser)event.getUser(), lines[1]);
         String[] newLines = text.split("(::|\n)");
         if (newLines.length < 2) {
            lines[0] = text;
            lines[1] = "";
         } else {
            int size = newLines.length > 4 ? 4 : newLines.length;
            System.arraycopy(newLines, 0, lines, 0, size);
         }
      } else {
         String line;
         if (lines[0].startsWith("`")) {
            text = lines[0] + lines[1] + lines[2] + lines[3];
            text = text.replace(" ", "");
            if (text.endsWith("`")) {
               line = Message.getMessage((IUser)event.getUser(), text);
               String[] newLines = line.split("(::|\n)");
               int size = newLines.length > 4 ? 4 : newLines.length;
               System.arraycopy(newLines, 0, lines, 0, size);
            }
         } else {
            for(int i = 0; i < lines.length; ++i) {
               if (lines[i].startsWith("lang:")) {
                  line = lines[i].substring(5);
                  lines[i] = Message.getMessage((IUser)event.getUser(), line);
                  if (lines[i].length() > 23) {
                     lines[i] = lines[i].substring(0, 23);
                  }
               }
            }
         }
      }

   }
}
