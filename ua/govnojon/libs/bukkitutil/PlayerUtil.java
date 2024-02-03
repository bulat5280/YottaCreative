package ua.govnojon.libs.bukkitutil;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.material.ModuleMaterial;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.nms.NMS;
import net.mineland.core.bukkit.modules.user.ModuleUser;
import net.mineland.core.bukkit.modules.user.PlayerId;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.BlockFace;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.craftbukkit.v1_12_R1.scheduler.CraftTask;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import ua.govnojon.libs.bukkitutil.chatcomponent.ChatComponent;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;
import ua.govnojon.libs.myjava.Try;

public class PlayerUtil {
   private static ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
   private static ModuleUser moduleUser = (ModuleUser)Module.getInstance(ModuleUser.class);
   private static ModuleMaterial moduleMaterial = (ModuleMaterial)Module.getInstance(ModuleMaterial.class);
   private static WorldEditPlugin worldEdit = (WorldEditPlugin)Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

   public static void sendActionBar(Player player, String message) {
      sendActionBar(User.getUser(player), message, 2);
   }

   public static void sendActionBar(User user, String message, int count) {
      String select = "§1❉ " + message + " §1❉";
      NMS.getManager().sendActionBar(user.getPlayer(), select);
      BukkitTask task = (BukkitTask)user.getMetadata("action_bar");
      BukkitUtil.cancelTask(task);
      if (count > 1) {
         user.setMetadata("action_bar", (new PlayerUtil.ActionBarRepeat(count - 1, select, user)).start());
      }

   }

   public static void sendActionBarTimer(User user, Long delay, Runnable runnable) {
      BukkitTask task = (BukkitTask)user.getMetadata("action_bar_timer");
      BukkitUtil.cancelTask(task);
      user.setMetadata("action_bar_timer", (new PlayerUtil.ActionBarTimer(user, delay, runnable)).start());
   }

   public static boolean hasActionBarTimer(User user) {
      return user.getMetadata("action_bar_timer") != null;
   }

   public static void cancelActionBarTimer(User user) {
      CraftTask task = (CraftTask)user.getMetadata("action_bar_timer");
      task.cancel();
      user.setMetadata("action_bar_timer", (Object)null);
   }

   public static void playSoundDelayed(User user, Sound sound, int times, int period) {
      BukkitTask task = (BukkitTask)user.getMetadata("delayed_sound");
      BukkitUtil.cancelTask(task);
      user.setMetadata("delayed_sound", (new PlayerUtil.DelayedSoundTimer(user, sound, times, period)).start());
   }

   public static void setMaxHealth(LivingEntity entity) {
      entity.setHealth(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
   }

   public static void setDefaultParameters(Player player, GameMode gameMode) {
      setDefaultParameters(player, gameMode, true);
   }

   public static void setDefaultParameters(Player player, GameMode gameMode, boolean clearIvn) {
      player.getActivePotionEffects().stream().map(PotionEffect::getType).forEach(player::removePotionEffect);
      setDefaultAttribute(player, Attribute.GENERIC_MAX_HEALTH);
      setMaxHealth(player);
      player.setFoodLevel(20);
      player.setGameMode(gameMode);
      PlayerInventory inventory = player.getInventory();
      inventory.clear();
      inventory.setArmorContents((ItemStack[])null);
      inventory.setExtraContents((ItemStack[])null);
      player.leaveVehicle();
      player.setShoulderEntityLeft((Entity)null);
      player.setShoulderEntityRight((Entity)null);
      player.setFlying(false);
      player.setGliding(false);
      player.setVelocity(new Vector());
      player.setFallDistance(0.0F);
      player.setFireTicks(0);
      player.setExp(0.0F);
      player.setLevel(0);
      player.setFlySpeed(0.1F);
      player.setWalkSpeed(0.2F);
   }

   public static void setDefaultAttribute(Player player, Attribute attribute) {
      AttributeInstance attributeInstance = player.getAttribute(attribute);
      if (attributeInstance != null) {
         attributeInstance.setBaseValue(attributeInstance.getDefaultValue());
      }

   }

   public static void openBook(User user, ItemStack book) {
      NMS.getManager().openBook(user.getPlayer(), book);
   }

   public static void openBook(User user, ChatComponent component) {
      ItemStack stack = NMS.getManager().setPages(new ItemStack(Material.WRITTEN_BOOK), component.getBaseComponent());
      NMS.getManager().openBook(user.getPlayer(), stack);
   }

   public static void openBook(User user, String... pages) {
      ItemStack stack = new ItemStack(Material.WRITTEN_BOOK);
      BookMeta itemMeta = (BookMeta)stack.getItemMeta();
      itemMeta.setPages(pages);
      stack.setItemMeta(itemMeta);
      NMS.getManager().openBook(user.getPlayer(), stack);
   }

   public static void sendSoundFail(Player player) {
      player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1.0F, 1.0F);
   }

   public static void teleportIgnoreLook(Player player, Location to) {
      Location loc = player.getLocation();
      to = to.clone();
      to.setYaw(loc.getYaw());
      to.setPitch(loc.getPitch());
      player.teleport(to);
   }

   public static boolean hasMainHand(Player player, Material material) {
      ItemStack mainHand = player.getInventory().getItemInMainHand();
      return mainHand != null && mainHand.getType().equals(material);
   }

   public static Player getDamager(Entity entity) {
      if (entity instanceof Player) {
         return (Player)entity;
      } else {
         Entity damager = EntityUtil.getDamager(entity);
         if (damager == null) {
            return null;
         } else {
            return damager instanceof Player ? (Player)damager : null;
         }
      }
   }

   public static void clearTitle(Player player) {
      sendTitle(player, " ", " ");
   }

   public static void playSound(Player player, Sound sound) {
      playSound(player, sound, 2.14748365E9F, 1.0F);
   }

   public static void playSound(Player player, Sound sound, float volume, float pitch) {
      player.playSound(player.getLocation(), sound, volume, pitch);
   }

   public static void sendSound(Player player, String sound) {
      player.playSound(player.getLocation(), sound, 2.14748365E9F, 1.0F);
   }

   public static void sendSound(Player player, String sound, int distance) {
      player.playSound(player.getLocation(), sound, (float)distance, 1.0F);
   }

   public static void sendSound(Player player, Sound sound, float volume, float pitch) {
      player.playSound(player.getLocation(), sound, volume, pitch);
   }

   public static void resetActionBar(Player player) {
      NMS.getManager().sendActionBar(player, "");
   }

   public static void sendAddBossBar(Player player, String text, BarColor barColor, BarStyle barStyle, float value) {
      sendAddBossBar((Collection)Collections.singletonList(player), text, barColor, barStyle, value);
   }

   public static void sendAddBossBar(Collection<Player> players, String text, BarColor barColor, BarStyle barStyle, float value) {
      NMS.getManagerSend().sendAddBossBar(players, text, barColor, barStyle, value);
   }

   public static void sendUpdateBossBar(Collection<Player> players, String text, BarColor barColor, BarStyle barStyle, float value) {
      NMS.getManagerSend().sendUpdateBossBar(players, text, barColor, barStyle, value);
   }

   public static void sendUpdateBossBar(Player player, String text, BarColor barColor, BarStyle barStyle, float value) {
      NMS.getManagerSend().sendUpdateBossBar(Collections.singletonList(player), text, barColor, barStyle, value);
   }

   public static void getDisplayName(PlayerId playerId, Consumer<String> name) {
      if (playerId != null) {
         name.accept(playerId.getName());
      } else {
         name.accept("");
      }

   }

   public static void getDisplayNamesByPlayerIds(List<PlayerId> playerData, Consumer<List<String>> consumer) {
      consumer.accept(playerData.stream().map(PlayerId::getName).collect(Collectors.toList()));
   }

   public static List<String> getPlayerNamesByPlayerIds(List<PlayerId> playerIds) {
      return (List)playerIds.stream().map(PlayerId::getName).collect(Collectors.toList());
   }

   public static void getDisplayNamesByIds(List<String> ids, Consumer<List<String>> consumer) {
      moduleUser.getPlayerDatas(ids, (playerIds) -> {
         getDisplayNamesByPlayerIds(playerIds, consumer);
      });
   }

   public static void getDisplayName(String name, Consumer<String> consumer) {
      moduleUser.getPlayerData(name, (playerId) -> {
         getDisplayName(playerId, consumer);
      });
   }

   public static void sendTitle(Player player, String title, String subtitle) {
      NMS.getManager().sendTitle(player, title, subtitle, 10, 40, 10);
   }

   public static void sendTitle(Player player, String title, String subtitle, int a, int b, int c) {
      NMS.getManager().sendTitle(player, title, subtitle, a, b, c);
   }

   public static void sendMessageDenyKey(Player player, String key, String... replaced) {
      sendMessageDenyKey(User.getUser(player), key, replaced);
   }

   public static void sendMessageDenyKey(User user, String key, String... replaced) {
      HashMap<String, Long> delay = (HashMap)user.getMetadata("deny_messages");
      if (delay == null) {
         user.setMetadata("deny_messages", delay = new HashMap());
      }

      Long last = (Long)delay.get(key);
      long current = System.currentTimeMillis();
      if (last == null || current - last > 2000L) {
         user.sendMessage(key, replaced);
         delay.put(key, current);
      }

   }

   public static void giveItems(Player player, ItemStack object, int amount) {
      if (player != null) {
         if (object.getMaxStackSize() >= 1) {
            boolean isSave = false;
            Player give = User.getPlayer(player.getName());
            if (give == null) {
               give = player;
               isSave = true;
            }

            Inventory inv = give.getInventory();
            boolean isChest = false;
            boolean isSendMessageFull = false;

            while(true) {
               while(true) {
                  HashMap result;
                  do {
                     if (amount <= 0) {
                        if (isSave) {
                           give.saveData();
                        }

                        return;
                     }

                     ItemStack item = object.clone();
                     if (amount >= item.getMaxStackSize()) {
                        item.setAmount(item.getMaxStackSize());
                        amount -= item.getMaxStackSize();
                     } else {
                        item.setAmount(amount);
                        amount = 0;
                     }

                     result = ((Inventory)inv).addItem(new ItemStack[]{item});
                  } while(result.isEmpty());

                  if (isChest) {
                     if (!isSendMessageFull) {
                        sendMessageDenyKey(give, "забит_эндерсундук");
                        isSendMessageFull = true;
                     }

                     Iterator var10 = result.values().iterator();

                     while(var10.hasNext()) {
                        ItemStack i = (ItemStack)var10.next();
                        player.getWorld().dropItem(player.getLocation(), i);
                     }
                  } else {
                     sendMessageDenyKey(give, "забит_инвентарь");
                     amount += ((ItemStack)result.get(0)).getAmount();
                     inv = player.getEnderChest();
                     isChest = true;
                  }
               }
            }
         }
      }
   }

   public static void giveItems(Player player, List<ItemStack> items) {
      if (player != null) {
         boolean isSave = false;
         Player give = User.getPlayer(player.getName());
         if (give == null) {
            give = player;
            isSave = true;
         }

         Inventory inv = give.getInventory();
         Map<Integer, ItemStack> result = inv.addItem((ItemStack[])items.stream().map(ItemStack::clone).toArray((x$0) -> {
            return new ItemStack[x$0];
         }));
         if (result.size() != 0) {
            give.sendMessage(Message.getMessage(give, "забит_инвентарь"));
            result = player.getEnderChest().addItem((ItemStack[])result.values().toArray(new ItemStack[result.values().size()]));
            if (result.size() != 0) {
               give.sendMessage(Message.getMessage(give, "забит_эндерсундук"));
               Iterator var6 = result.values().iterator();

               while(var6.hasNext()) {
                  ItemStack i = (ItemStack)var6.next();
                  player.getWorld().dropItem(player.getLocation(), i);
               }
            }
         }

         if (isSave) {
            give.saveData();
         }

      }
   }

   public static void giveItemsAndArmor(Player player, List<ItemStack> items) {
      if (player != null) {
         boolean isSave = false;
         Player give = User.getPlayer(player.getName());
         if (give == null) {
            give = player;
            isSave = true;
         }

         PlayerInventory inventory = player.getInventory();
         boolean boots = inventory.getBoots() != null;
         boolean chestplate = inventory.getChestplate() != null;
         boolean helmet = inventory.getHelmet() != null;
         boolean leggings = inventory.getLeggings() != null;
         boolean isSendMessageFull = false;
         Iterator var10 = items.iterator();

         while(true) {
            while(var10.hasNext()) {
               ItemStack item = (ItemStack)var10.next();
               if (!boots && moduleMaterial.isBoots(item.getType())) {
                  inventory.setBoots(item);
                  boots = true;
               } else if (!chestplate && moduleMaterial.isChestplate(item.getType())) {
                  inventory.setChestplate(item);
                  chestplate = true;
               } else if (!helmet && moduleMaterial.isHelmet(item.getType())) {
                  inventory.setHelmet(item);
                  helmet = true;
               } else if (!leggings && moduleMaterial.isLeggings(item.getType())) {
                  inventory.setLeggings(item);
                  leggings = true;
               } else {
                  HashMap<Integer, ItemStack> result = inventory.addItem(new ItemStack[]{item});
                  if (result.size() != 0) {
                     if (!isSendMessageFull) {
                        isSendMessageFull = true;
                        give.sendMessage(Message.getMessage(give, "забит_инвентарь"));
                     }

                     Iterator var13 = result.values().iterator();

                     while(var13.hasNext()) {
                        ItemStack i = (ItemStack)var13.next();
                        give.getWorld().dropItem(give.getEyeLocation(), i);
                     }
                  }
               }
            }

            if (isSave) {
               give.saveData();
            }

            return;
         }
      }
   }

   public static void sendPacket(Player player, PacketContainer packet) {
      NMS.getSenderPacket().submit(() -> {
         Try.unchecked(() -> {
            protocolManager.sendServerPacket(player, packet);
         });
      });
   }

   public static void sendPacket(Collection<Player> players, PacketContainer packet) {
      NMS.getSenderPacket().submit(() -> {
         return Try.ignore(() -> {
            Iterator var2 = players.iterator();

            while(var2.hasNext()) {
               Player player = (Player)var2.next();
               protocolManager.sendServerPacket(player, packet);
            }

         });
      });
   }

   public static BlockFace getDirectionFace(Player player) {
      Location loc = player.getLocation();
      float pitch = loc.getPitch();
      if (pitch > 45.0F) {
         return BlockFace.DOWN;
      } else if (pitch < -45.0F) {
         return BlockFace.UP;
      } else {
         float yaw = loc.getYaw() < 0.0F ? loc.getYaw() + 360.0F : loc.getYaw();
         if (yaw >= 225.0F && yaw <= 315.0F) {
            return BlockFace.EAST;
         } else if ((!(yaw >= 315.0F) || !(yaw <= 360.0F)) && (!(yaw >= 0.0F) || !(yaw <= 45.0F))) {
            if (yaw >= 45.0F && yaw <= 135.0F) {
               return BlockFace.WEST;
            } else {
               return yaw >= 135.0F && yaw <= 225.0F ? BlockFace.NORTH : null;
            }
         } else {
            return BlockFace.SOUTH;
         }
      }
   }

   public static void sendSoundDeny(Player player) {
      player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
   }

   public static void sendSoundSuccess(Player player) {
      player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
   }

   public static void sendSuccessMessage(Player player, String prefix, String message) {
      sendSoundSuccess(player);
      sendMessage(player, prefix, "§a" + message);
   }

   public static void sendMessage(Player player, String prefix, String message) {
      player.sendMessage(prefix + " §8» " + message);
   }

   public static void sendSoundSelect(Player player) {
      player.playSound(player.getLocation(), Sound.ENTITY_ITEMFRAME_PLACE, 1.0F, 1.0F);
   }

   public static void sendSoundMegaReward(Player player) {
      player.playSound(player.getLocation(), "ui.toast.challenge_complete", SoundCategory.RECORDS, 2.14748365E9F, 1.0F);
      player.playSound(player.getLocation(), "ui.toast.challenge_complete", SoundCategory.RECORDS, 2.14748365E9F, 2.0F);
      player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.RECORDS, 2.14748365E9F, 0.5F);
   }

   public static BlockFace getDirectionFaceHorisontaly(Player p) {
      Location loc = p.getLocation();
      return LocationUtil.getDirectionFace(loc.getYaw());
   }

   public static boolean isCriticalHit(EntityDamageByEntityEvent event) {
      if (!event.getDamager().getType().equals(EntityType.PLAYER)) {
         return false;
      } else {
         Player damager = (Player)event.getDamager();
         return !damager.isOnGround() && damager.getFallDistance() > 0.0F;
      }
   }

   public static void addOrSumPotion(Player player, PotionEffectType type, int duration, int amplifer) {
      PotionEffect effect = player.getPotionEffect(type);
      if (effect == null) {
         effect = new PotionEffect(type, duration, amplifer);
      } else {
         effect = new PotionEffect(type, duration + effect.getDuration(), Math.max(effect.getAmplifier(), amplifer));
      }

      player.addPotionEffect(effect, true);
   }

   public static void sendTitleKey(User user, String titleKey, int i, int i1, int i2, String... replaced) {
      String message = Message.getMessage((IUser)user, titleKey, replaced);
      String[] data = message.split("\n");
      String title = data.length < 1 ? "" : data[0];
      String subtitle = data.length < 2 ? "" : data[1];
      sendTitle(user.getPlayer(), title, subtitle, i, i1, i2);
   }

   public static void sendTitleKey(User user, String titleKey, String... replaced) {
      sendTitleKey(user, titleKey, 10, 40, 10, replaced);
   }

   public static Player getNearestPlayer(Location loc, List<Player> players) {
      if (players.isEmpty()) {
         return null;
      } else {
         double min = Double.MAX_VALUE;
         Player result = null;
         Iterator var5 = players.iterator();

         while(var5.hasNext()) {
            Player player = (Player)var5.next();
            double d = player.getLocation().distanceSquared(loc);
            if (d < min) {
               min = d;
               result = player;
            }
         }

         return result;
      }
   }

   public static void sendActionBarKey(User user, String messageKey, String... replaced) {
      sendActionBar(user.getPlayer(), Message.getMessage((IUser)user, messageKey, replaced));
   }

   @Nullable
   public static Location getSelectionMin(Player player) {
      Selection selection = worldEdit.getSelection(player);
      return selection != null ? selection.getMinimumPoint() : null;
   }

   public static void setSelection(Player player, Selection selection) {
      worldEdit.setSelection(player, selection);
   }

   @Nullable
   public static Location getSelectionMax(Player player) {
      Selection selection = worldEdit.getSelection(player);
      return selection != null ? selection.getMaximumPoint() : null;
   }

   @Nullable
   public static Location getSelectionBlock1(Player player) {
      return (Location)User.getUser(player).getMetadata("selection_pos1");
   }

   @Nullable
   public static Location getSelectionBlock2(Player player) {
      return (Location)User.getUser(player).getMetadata("selection_pos2");
   }

   public static String getPrefix(User user) {
      return "prefix";
   }

   public static String getSuffix(User user) {
      return "suffix";
   }

   public static int getTabPriority(IUser user) {
      return 0;
   }

   public static int getTabPriority(Player player) {
      return 0;
   }

   private static class DelayedSoundTimer implements Runnable {
      private int i = 0;
      private User user;
      private Sound sound;
      private int times;
      private BukkitTask task;
      private int period;

      DelayedSoundTimer(User user, Sound sound, int times, int period) {
         this.user = user;
         this.sound = sound;
         this.times = times;
         this.period = period;
      }

      public void run() {
         ++this.i;
         this.user.getPlayer().playSound(this.user.getPlayer().getLocation(), this.sound, 0.5F, 1.0F);
         if (this.i >= this.times) {
            this.task.cancel();
         }

      }

      BukkitTask start() {
         return this.task = Schedule.timer(this, 2L, (long)this.period);
      }
   }

   private static class ActionBarTimer implements Runnable {
      private User user;
      private BukkitTask task;
      private long run;
      private long delay;
      private Runnable runnable;
      private int lineSize = 20;
      private StringBuilder lineFilled = new StringBuilder();
      private StringBuilder lineEmpty = new StringBuilder();

      public ActionBarTimer(User user, long delay, Runnable runnable) {
         this.user = user;
         this.run = delay;
         this.delay = delay;
         this.runnable = runnable;
      }

      public void cancel(boolean runnable) {
         NMS.getManager().sendActionBar(this.user.getPlayer(), "");
         this.user.setMetadata("action_bar_timer", (Object)null);
         this.task.cancel();
         if (runnable) {
            Schedule.run(this.runnable);
         }

      }

      public void run() {
         double oneBlock = (double)this.delay / (double)this.lineSize;
         double emptyBlocks = (double)this.run / oneBlock;
         double filledBlocks = (double)this.lineSize - emptyBlocks;

         int i;
         for(i = this.lineEmpty.length() - 1; (double)i < emptyBlocks; ++i) {
            this.lineEmpty.append("░");
         }

         for(i = this.lineFilled.length() - 1; (double)i < filledBlocks; ++i) {
            this.lineFilled.append("▉");
            this.lineEmpty.deleteCharAt(this.lineEmpty.length() - 1);
         }

         NMS.getManager().sendActionBar(this.user.getPlayer(), Message.getMessage((IUser)this.user, "actionbar.timer", "{lineFilled}", this.lineFilled.toString(), "{lineEmpty}", this.lineEmpty.toString()));
         if (this.run <= 0L) {
            this.cancel(true);
         }

         this.run -= 2L;
      }

      BukkitTask start() {
         return this.task = Schedule.timer(this, 2L, 2L);
      }
   }

   private static class ActionBarRepeat implements Runnable {
      private int count;
      private int i = 0;
      private String message;
      private User user;
      private BukkitTask task;

      public ActionBarRepeat(int count, String message, User user) {
         this.count = Math.max(count, 1);
         this.message = message;
         this.user = user;
      }

      public void run() {
         boolean active = this.user.isActive();
         if (active) {
            NMS.getManager().sendActionBar(this.user.getPlayer(), this.message);
         }

         if (++this.i == this.count || !active) {
            this.task.cancel();
         }

      }

      BukkitTask start() {
         return this.task = Schedule.timer(this, 2L, 2L, TimeUnit.SECONDS);
      }
   }
}
