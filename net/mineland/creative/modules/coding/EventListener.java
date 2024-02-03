package net.mineland.creative.modules.coding;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.regions.CuboidRegion;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.gui.ModuleGui;
import net.mineland.core.bukkit.modules.myevents.EntityDamageByEntityMyEvent;
import net.mineland.core.bukkit.modules.myevents.PlayerMoveOnBlockMyEvent;
import net.mineland.core.bukkit.modules.nms.NMS;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.actions.gui.GuiMenuActionCategories;
import net.mineland.creative.modules.coding.actions.gui.GuiMenuActions;
import net.mineland.creative.modules.coding.actions.gui.GuiMenuFunctions;
import net.mineland.creative.modules.coding.actions.gui.GuiMenuPlayerSelection;
import net.mineland.creative.modules.coding.actions.gui.GuiMenuSetVariable;
import net.mineland.creative.modules.coding.activators.gui.GuiMenuPlayerEvents;
import net.mineland.creative.modules.coding.activators.player.EntityDamageCommonEvent;
import net.mineland.creative.modules.coding.activators.player.PlayerBlockBreakActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerBlockPlaceActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerChangeSlotActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerChatActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerConsumeItemActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerDamageMobActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerDamagePlayerActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerDropItemActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerFoodLevelChangeActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerKillMobActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerLeftClickActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerMobDamagePlayerActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerMoveActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerPickupItemActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerProjectileDamageActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerRightClickActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerRightClickEntityActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerSneakActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerSprintActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerStopSprintActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerSwapHandsActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerTakeDamageActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerTakeFallDamageActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerUnsneakActivator;
import net.mineland.creative.modules.coding.variables.GuiMenuParticleEffects;
import net.mineland.creative.modules.coding.variables.GuiMenuPotionEffects;
import net.mineland.creative.modules.coding.variables.GuiMenuValues;
import net.mineland.creative.modules.coding.variables.GuiMenuVariableItems;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.PlayerMode;
import net.mineland.creative.modules.creative.Plot;
import net.mineland.creative.modules.creative.PlotMode;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;
import ua.govnojon.libs.bukkitutil.PlayerUtil;
import ua.govnojon.libs.bukkitutil.SimpleItem;
import ua.govnojon.libs.bukkitutil.WorldUtil;
import ua.govnojon.libs.bukkitutil.chatcomponent.ChatComponentUtil;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;

public class EventListener implements Listener {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
   private static ModuleGui moduleGui = (ModuleGui)Module.getInstance(ModuleGui.class);
   private static ModuleCoding moduleCoding = (ModuleCoding)Module.getInstance(ModuleCoding.class);
   private static DecimalFormat format = new DecimalFormat("#0.00");

   private boolean checkSkipDoubleCall(User user, PlayerInteractEvent event) {
      LocalDateTime time = (LocalDateTime)user.getMetadata("creative.last.interact.time");
      PlayerInteractEvent lastEvent = (PlayerInteractEvent)user.getMetadata("creative.last.interact.event");
      LocalDateTime now = LocalDateTime.now();
      if (lastEvent != null && time != null) {
         if (Objects.equals(event.getHand(), lastEvent.getHand()) && Objects.equals(event.getItem(), lastEvent.getItem())) {
            if (ChronoUnit.MILLIS.between(time, now) > 50L) {
               user.setMetadata("creative.last.interact.time", now);
               user.setMetadata("creative.last.interact.event", event);
               return false;
            } else {
               return true;
            }
         } else {
            user.setMetadata("creative.last.interact.time", now);
            user.setMetadata("creative.last.interact.event", event);
            return false;
         }
      } else {
         user.setMetadata("creative.last.interact.time", now);
         user.setMetadata("creative.last.interact.event", event);
         return false;
      }
   }

   @EventHandler
   public void on(PlayerInteractEvent event) {
      User user = User.getUser((PlayerEvent)event);
      Player player = user.getPlayer();
      Block block = event.getClickedBlock();
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
      if (plot != null) {
         if (!this.checkSkipDoubleCall(user, event)) {
            switch(plot.getPlotMode()) {
            case PLAYING:
               switch(event.getAction()) {
               case RIGHT_CLICK_BLOCK:
               case RIGHT_CLICK_AIR:
                  if (event.getHand() == EquipmentSlot.HAND) {
                     PlayerRightClickActivator.Event gameEvent = new PlayerRightClickActivator.Event(user, plot, event);
                     gameEvent.callEvent();
                     gameEvent.handle();
                  }
                  break;
               case LEFT_CLICK_BLOCK:
               case LEFT_CLICK_AIR:
                  PlayerLeftClickActivator.Event gameEvent = new PlayerLeftClickActivator.Event(user, plot, event);
                  gameEvent.callEvent();
                  gameEvent.handle();
               }
            default:
               if (plot.getPlayerMode(user) == PlayerMode.CODING && player.getWorld() == moduleCreative.getPlotWorld()) {
                  event.setCancelled(true);
               }

               String stringLocation;
               if (user.getPlayer().getWorld() == moduleCoding.getCodingWorld() && event.getAction() == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.WALL_SIGN) {
                  user.setMetadata("coding.selected_sign", block);
                  ItemStack inMainHand = player.getInventory().getItemInMainHand();
                  switch(block.getRelative(BlockFace.SOUTH).getType()) {
                  case DIAMOND_BLOCK:
                     moduleGui.openGui(user, GuiMenuPlayerEvents.class);
                     break;
                  case LAPIS_BLOCK:
                  case EMERALD_BLOCK:
                     stringLocation = ChatComponentUtil.removeColors(CodeUtils.parseText(inMainHand, "", false));
                     Sign sign;
                     if (block.getRelative(BlockFace.SOUTH).getType() == Material.EMERALD_BLOCK) {
                        sign = (Sign)block.getState();
                        String ticksLine = sign.getLine(2);
                        int ticks = Integer.parseInt(ticksLine);
                        if (CodeUtils.INTEGER.matcher(stringLocation).matches()) {
                           if (Integer.parseInt(stringLocation) >= 5) {
                              sign.setLine(2, stringLocation);
                           }
                        } else if (!stringLocation.isEmpty() && inMainHand.getType() == Material.BOOK) {
                           sign.setLine(1, stringLocation);
                        } else {
                           if (player.isSneaking()) {
                              if (ticks > 5) {
                                 --ticks;
                              }
                           } else {
                              ++ticks;
                           }

                           sign.setLine(2, String.valueOf(ticks));
                        }

                        sign.update();
                     } else if (!stringLocation.isEmpty()) {
                        sign = (Sign)block.getState();
                        sign.setLine(1, stringLocation);
                        sign.update();
                        moduleCreative.getPlotManager().parseCoding(plot);
                     }
                     break;
                  case COBBLESTONE:
                     if (!player.isSneaking()) {
                        user.setMetadata("coding.open_categories", "player");
                        moduleGui.openGui(user, GuiMenuActionCategories.class);
                     } else {
                        moduleGui.openGui(user, GuiMenuPlayerSelection.class);
                     }
                     break;
                  case NETHER_BRICK:
                     user.setMetadata("coding.open_categories", "game");
                     moduleGui.openGui(user, GuiMenuActionCategories.class);
                     break;
                  case LAPIS_ORE:
                     moduleGui.openGui(user, GuiMenuFunctions.class);
                     break;
                  case WOOD:
                     if (!CodeUtils.invertIfBlock(block, inMainHand)) {
                        if (!player.isSneaking()) {
                           user.setMetadata("coding.selected_category", net.mineland.creative.modules.coding.actions.Action.Category.IF_PLAYER);
                           moduleGui.openGui(user, GuiMenuActions.class);
                        } else {
                           moduleGui.openGui(user, GuiMenuPlayerSelection.class);
                        }
                     }
                     break;
                  case BRICK:
                     if (!CodeUtils.invertIfBlock(block, inMainHand)) {
                        user.setMetadata("coding.selected_category", net.mineland.creative.modules.coding.actions.Action.Category.IF_ENTITY);
                        moduleGui.openGui(user, GuiMenuActions.class);
                     }
                     break;
                  case OBSIDIAN:
                     if (!CodeUtils.invertIfBlock(block, inMainHand)) {
                        user.setMetadata("coding.selected_category", net.mineland.creative.modules.coding.actions.Action.Category.IF_VARIABLE);
                        moduleGui.openGui(user, GuiMenuActions.class);
                     }
                     break;
                  case RED_NETHER_BRICK:
                     if (!CodeUtils.invertIfBlock(block, inMainHand)) {
                        user.setMetadata("coding.selected_category", net.mineland.creative.modules.coding.actions.Action.Category.IF_GAME);
                        moduleGui.openGui(user, GuiMenuActions.class);
                     }
                     break;
                  case PURPUR_BLOCK:
                     if (!CodeUtils.invertIfBlock(block, inMainHand)) {
                        user.setMetadata("coding.selected_category", net.mineland.creative.modules.coding.actions.Action.Category.SELECT_OBJECT);
                        moduleGui.openGui(user, GuiMenuActions.class);
                     }
                     break;
                  case IRON_BLOCK:
                     user.setMetadata("coding.selected_category", net.mineland.creative.modules.coding.actions.Action.Category.SET_VARIABLE);
                     moduleGui.openGui(user, GuiMenuSetVariable.class);
                  }
               }

               if (plot.getPlayerMode(user) == PlayerMode.CODING) {
                  Location centerPosition;
                  switch(event.getAction()) {
                  case RIGHT_CLICK_BLOCK:
                  case RIGHT_CLICK_AIR:
                     if (event.getItem() != null) {
                        switch(event.getItem().getType()) {
                        case IRON_INGOT:
                           moduleGui.openGui(user, GuiMenuVariableItems.class);
                           break;
                        case POTION:
                        case GLASS_BOTTLE:
                           moduleGui.openGui(user, GuiMenuPotionEffects.class);
                           break;
                        case NETHER_STAR:
                           moduleGui.openGui(user, GuiMenuParticleEffects.class);
                           break;
                        case APPLE:
                           moduleGui.openGui(user, GuiMenuValues.class);
                           break;
                        case PAPER:
                           centerPosition = block != null ? block.getLocation() : player.getLocation();
                           if (centerPosition.getWorld() == moduleCoding.getCodingWorld()) {
                              return;
                           }

                           stringLocation = "§r" + format.format(centerPosition.getX()) + " " + format.format(centerPosition.getY()) + " " + format.format(centerPosition.getZ()) + " " + format.format((double)centerPosition.getYaw()) + " " + format.format((double)centerPosition.getPitch());
                           SimpleItem simpleItem = (new SimpleItem(event.getItem())).displayName(stringLocation.replace(",", "."));
                           player.getInventory().setItemInMainHand(simpleItem);
                           PlayerUtil.playSound(user.getPlayer(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                           PlayerUtil.sendTitleKey(user, "coding.title.value_set", "{value}", stringLocation);
                           break;
                        case MAGMA_CREAM:
                           if (player.isSneaking()) {
                              SimpleItem simpleItem = new SimpleItem(event.getItem());
                              if (simpleItem.getLocalizedName() != null && simpleItem.getLocalizedName().equalsIgnoreCase("save")) {
                                 simpleItem.localizedName((String)null);
                                 List<String> lore = simpleItem.getLore();
                                 simpleItem.lore(lore.subList(1, lore.size()));
                                 player.getInventory().setItemInMainHand(simpleItem);
                              } else {
                                 simpleItem.localizedName("save");
                                 List<String> lore = new ArrayList();
                                 lore.add("lang:coding.item.save");
                                 lore.addAll(simpleItem.getLore());
                                 simpleItem.lore((List)lore);
                                 player.getInventory().setItemInMainHand(simpleItem);
                              }

                              PlayerUtil.playSound(user.getPlayer(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                           }
                        }
                     }
                     break;
                  case LEFT_CLICK_BLOCK:
                  case LEFT_CLICK_AIR:
                     if (event.getItem() != null && event.getHand() == EquipmentSlot.HAND) {
                        switch(event.getItem().getType()) {
                        case PAPER:
                           if (event.getPlayer().getWorld() == moduleCoding.getCodingWorld()) {
                              player.teleport(plot.getSpawnLocation(moduleCreative.getPlotWorld()));
                              centerPosition = plot.getCenterPosition().toLocation(moduleCreative.getPlotWorld());
                              moduleCreative.getPlotManager().sendWorldBorderPacker(user, centerPosition, plot.getSize());
                           } else if (player.getWorld() == moduleCreative.getPlotWorld()) {
                              Schedule.later(() -> {
                                 player.teleport(plot.getSpawnLocation(moduleCoding.getCodingWorld()));
                                 moduleCreative.getPlotManager().sendWorldBorderPacker(user, plot.getCenterPosition().toLocation(moduleCoding.getCodingWorld()), moduleCoding.getCodingSize());
                              }, 20L);
                           }
                        }
                     }
                  }
               }

            }
         }
      }
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void on(AsyncPlayerChatEvent event) {
      User user = User.getUser(event.getPlayer());
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
      if (plot != null) {
         String message = event.getMessage().replace("&", "§").replace("§§", "&");
         if (plot.getPlayerMode(user) == PlayerMode.CODING) {
            SimpleItem item = new SimpleItem(user.getPlayer().getInventory().getItemInMainHand());
            if (!ItemStackUtil.isNullOrAir(item)) {
               boolean isCanceled = event.isCancelled();
               event.setCancelled(true);
               boolean updateItem = true;
               switch(item.getType()) {
               case POTION:
                  message = ChatComponentUtil.removeColors(message);
                  PotionMeta potionMeta = (PotionMeta)item.getItemMeta();
                  boolean updated = false;
                  if (CodeUtils.INTEGER.matcher(message).matches()) {
                     updated = true;
                     int number = Integer.parseInt(message);
                     PotionEffect potionEffect = (PotionEffect)potionMeta.getCustomEffects().get(0);
                     potionMeta.clearCustomEffects();
                     potionMeta.addCustomEffect(new PotionEffect(potionEffect.getType(), potionEffect.getDuration(), number - 1, true), true);
                  } else if (CodeUtils.DURATION.matcher(message).matches()) {
                     updated = true;
                     String[] split = message.split(":");
                     int minutes = Integer.parseInt(split[0]);
                     int seconds = Integer.parseInt(split[1]);
                     int duration = minutes * 1200 + seconds * 20;
                     PotionEffect potionEffect = (PotionEffect)potionMeta.getCustomEffects().get(0);
                     potionMeta.clearCustomEffects();
                     potionMeta.addCustomEffect(new PotionEffect(potionEffect.getType(), duration, potionEffect.getAmplifier(), true), true);
                  }

                  if (updated) {
                     item.setItemMeta(potionMeta);
                     PlayerUtil.playSound(user.getPlayer(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                     PlayerUtil.sendTitleKey(user, "coding.title.value_set", "{value}", message);
                  }
                  break;
               case GLASS_BOTTLE:
               case NETHER_STAR:
               case APPLE:
               case PAPER:
               default:
                  updateItem = false;
                  event.setCancelled(isCanceled);
                  break;
               case MAGMA_CREAM:
                  item.displayName("§r" + ChatComponentUtil.removeColors(message));
                  PlayerUtil.playSound(user.getPlayer(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                  PlayerUtil.sendTitleKey(user, "coding.title.value_set_name", "{name}", message);
                  break;
               case BOOK:
                  item.displayName("§r" + message);
                  PlayerUtil.playSound(user.getPlayer(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                  PlayerUtil.sendTitleKey(user, "coding.title.value_set", "{value}", message);
                  break;
               case SLIME_BALL:
                  message = ChatComponentUtil.removeColors(message);
                  if (CodeUtils.TIME.matcher(message).matches()) {
                     int number = 0;
                     if (message.contains("t")) {
                        number = Integer.parseInt(message.replace("t", ""));
                     }

                     if (message.contains("s")) {
                        number = Integer.parseInt(message.replace("s", "")) * 20;
                     }

                     if (message.contains("m")) {
                        number = Integer.parseInt(message.replace("m", "")) * 1200;
                     }

                     if (message.contains("h")) {
                        number = Integer.parseInt(message.replace("h", "")) * 72000;
                     }

                     item.displayName("§c" + number);
                     PlayerUtil.playSound(user.getPlayer(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                     PlayerUtil.sendTitleKey(user, "coding.title.value_set", "{value}", String.valueOf(number));
                  } else if (CodeUtils.NUMBER.matcher(message).matches()) {
                     try {
                        Double.parseDouble(message);
                        item.displayName("§c" + message);
                        PlayerUtil.playSound(user.getPlayer(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                        PlayerUtil.sendTitleKey(user, "coding.title.value_set", "{value}", message);
                     } catch (NumberFormatException var15) {
                        PlayerUtil.sendTitleKey(user, "coding.title.invalid_value", "{value}", message);
                     }
                  } else {
                     PlayerUtil.sendTitleKey(user, "coding.title.invalid_value", "{value}", message);
                  }
               }

               if (updateItem) {
                  user.getPlayer().getInventory().setItemInMainHand(item);
               }
            }
         } else if (plot.getPlotMode() == PlotMode.PLAYING && !message.startsWith("!")) {
            Schedule.run(() -> {
               PlayerChatActivator.Event gameEvent = new PlayerChatActivator.Event(user, plot, event);
               gameEvent.callEvent();
               gameEvent.handle();
            });
         }

      }
   }

   @EventHandler(
      ignoreCancelled = true,
      priority = EventPriority.HIGHEST
   )
   public void on(BlockBreakEvent event) {
      User user = User.getUser(event.getPlayer());
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
      if (plot != null) {
         Block block = event.getBlock();
         if (plot.getPlotMode() == PlotMode.PLAYING) {
            PlayerBlockBreakActivator.Event gameEvent = new PlayerBlockBreakActivator.Event(user, plot, event);
            gameEvent.callEvent();
            gameEvent.handle();
         }

         if (user.getPlayer().getWorld() == moduleCoding.getCodingWorld()) {
            Block endBlock;
            switch(block.getType()) {
            case DIAMOND_BLOCK:
            case LAPIS_BLOCK:
            case EMERALD_BLOCK:
               if (block.getRelative(BlockFace.NORTH).getType() == Material.WALL_SIGN) {
                  endBlock = block;

                  while(plot.inTerritory(endBlock.getLocation())) {
                     endBlock = endBlock.getRelative(BlockFace.WEST);
                     if (endBlock.getRelative(BlockFace.UP).getType() == Material.CHEST) {
                        endBlock.getRelative(BlockFace.UP).setType(Material.AIR, true);
                     }
                  }

                  WorldUtil.setBlocks(block.getRelative(BlockFace.NORTH).getLocation(), endBlock.getLocation(), new ItemData(Material.AIR));
               }
               break;
            case COBBLESTONE:
            case NETHER_BRICK:
            case LAPIS_ORE:
            case PURPUR_BLOCK:
            case IRON_BLOCK:
               if (block.getRelative(BlockFace.NORTH).getType() == Material.WALL_SIGN) {
                  block.getRelative(BlockFace.WEST).setType(Material.AIR);
                  block.getRelative(BlockFace.UP).setType(Material.AIR);
                  block.getRelative(BlockFace.NORTH).setType(Material.AIR);
                  Location pos1 = block.getRelative(BlockFace.WEST, 2).getLocation();
                  Location pos2 = CodeUtils.getLastBlock(pos1.getBlock().getRelative(BlockFace.WEST).getLocation()).getBlock().getRelative(BlockFace.NORTH).getRelative(BlockFace.UP).getLocation();
                  WorldUtil.moveBlocks(pos1, pos2, BlockFace.EAST, 2);
               }
               break;
            case WOOD:
            case BRICK:
            case OBSIDIAN:
            case RED_NETHER_BRICK:
            case ENDER_STONE:
               endBlock = CodeUtils.getEndPistonBlock(block.getRelative(BlockFace.WEST));
               block.getRelative(BlockFace.WEST).setType(Material.AIR);
               block.getRelative(BlockFace.UP).setType(Material.AIR);
               block.getRelative(BlockFace.NORTH).setType(Material.AIR);
               if (endBlock != null) {
                  for(Block chestBlock = block.getRelative(BlockFace.UP); !chestBlock.getRelative(BlockFace.DOWN).getLocation().equals(endBlock.getLocation()); chestBlock = chestBlock.getRelative(BlockFace.WEST)) {
                     if (chestBlock.getType() == Material.CHEST) {
                        chestBlock.setType(Material.AIR, true);
                     }
                  }

                  WorldUtil.setBlocks(block.getRelative(BlockFace.NORTH).getLocation(), endBlock.getLocation(), new ItemData(Material.AIR), (session) -> {
                     Location pos1 = endBlock.getRelative(BlockFace.WEST).getLocation();
                     Location pos2 = CodeUtils.getLastBlock(pos1.getBlock().getRelative(BlockFace.WEST).getLocation()).getBlock().getRelative(BlockFace.NORTH).getRelative(BlockFace.UP).getLocation();
                     CuboidRegion cuboidRegion = new CuboidRegion(session.getWorld(), new Vector(pos1.getX(), pos1.getY(), pos1.getZ()), new Vector(pos2.getX(), pos2.getY(), pos2.getZ()));
                     session.moveRegion(cuboidRegion, new Vector(BlockFace.EAST.getModX(), BlockFace.EAST.getModY(), BlockFace.EAST.getModZ()), (int)block.getLocation().distance(pos1), true, (BaseBlock)null);
                     session.flushQueue();
                  });
               }
               break;
            case IRON_INGOT:
            case POTION:
            case GLASS_BOTTLE:
            case NETHER_STAR:
            case APPLE:
            case PAPER:
            case MAGMA_CREAM:
            case BOOK:
            case SLIME_BALL:
            default:
               event.setCancelled(true);
               break;
            case WALL_SIGN:
               NMS.getManager().breakBlockByPlayer(event.getPlayer(), block.getRelative(BlockFace.SOUTH).getLocation());
            case WORKBENCH:
            case ANVIL:
            case ENDER_CHEST:
            case SILVER_SHULKER_BOX:
            case BLACK_SHULKER_BOX:
            case BLUE_SHULKER_BOX:
            case BROWN_SHULKER_BOX:
            case CYAN_SHULKER_BOX:
            case GRAY_SHULKER_BOX:
            case GREEN_SHULKER_BOX:
            case LIGHT_BLUE_SHULKER_BOX:
            case LIME_SHULKER_BOX:
            case MAGENTA_SHULKER_BOX:
            case ORANGE_SHULKER_BOX:
            case PINK_SHULKER_BOX:
            case PURPLE_SHULKER_BOX:
            case RED_SHULKER_BOX:
            case WHITE_SHULKER_BOX:
            case YELLOW_SHULKER_BOX:
            case PISTON_BASE:
            }
         }

      }
   }

   @EventHandler(
      ignoreCancelled = true,
      priority = EventPriority.HIGHEST
   )
   public void on(BlockPlaceEvent event) {
      User user = User.getUser(event.getPlayer());
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
      if (plot != null) {
         Block block = event.getBlock();
         if (plot.getPlotMode() == PlotMode.PLAYING) {
            PlayerBlockPlaceActivator.Event gameEvent = new PlayerBlockPlaceActivator.Event(user, plot, event);
            gameEvent.callEvent();
            gameEvent.handle();
         }

         if (user.getPlayer().getWorld() == moduleCoding.getCodingWorld()) {
            if (user.getPlayer().isSneaking()) {
               Schedule.later(() -> {
                  if (block.getRelative(BlockFace.NORTH).getType() == Material.WALL_SIGN) {
                     (new PlayerInteractEvent(user.getPlayer(), Action.RIGHT_CLICK_BLOCK, (ItemStack)null, block.getRelative(BlockFace.NORTH), BlockFace.NORTH)).callEvent();
                  }

               }, 2L);
            }

            switch(block.getType()) {
            case DIAMOND_BLOCK:
               event.setCancelled(!this.placeCodeBlock(block, new ItemData(Material.DIAMOND_ORE), "lang:coding.sign.player_event"));
               break;
            case LAPIS_BLOCK:
               event.setCancelled(!this.placeCodeBlock(block, new ItemData(Material.LAPIS_ORE), "lang:coding.sign.function"));
               break;
            case EMERALD_BLOCK:
               event.setCancelled(!this.placeCodeBlock(block, new ItemData(Material.EMERALD_ORE), "lang:coding.sign.game_loop"));
               break;
            case COBBLESTONE:
               event.setCancelled(!this.placeCodeBlock(block, new ItemData(Material.STONE), "lang:coding.sign.player_action"));
               break;
            case NETHER_BRICK:
               event.setCancelled(!this.placeCodeBlock(block, new ItemData(Material.NETHERRACK), "lang:coding.sign.game_action"));
               break;
            case LAPIS_ORE:
               event.setCancelled(!this.placeCodeBlock(block, new ItemData(Material.STONE), "lang:coding.sign.call_function"));
               break;
            case WOOD:
               event.setCancelled(!this.placeCodeBlock(block, new ItemData(Material.PISTON_BASE, 4), "lang:coding.sign.if_player"));
               break;
            case BRICK:
               event.setCancelled(!this.placeCodeBlock(block, new ItemData(Material.PISTON_BASE, 4), "lang:coding.sign.if_entity"));
               break;
            case OBSIDIAN:
               event.setCancelled(!this.placeCodeBlock(block, new ItemData(Material.PISTON_BASE, 4), "lang:coding.sign.if_variable"));
               break;
            case RED_NETHER_BRICK:
               event.setCancelled(!this.placeCodeBlock(block, new ItemData(Material.PISTON_BASE, 4), "lang:coding.sign.if_game"));
               break;
            case PURPUR_BLOCK:
               event.setCancelled(!this.placeCodeBlock(block, new ItemData(Material.PURPUR_PILLAR, 4), "lang:coding.sign.select_object"));
               break;
            case IRON_BLOCK:
               event.setCancelled(!this.placeCodeBlock(block, new ItemData(Material.IRON_ORE), "lang:coding.sign.set_variable"));
               break;
            case IRON_INGOT:
            case POTION:
            case GLASS_BOTTLE:
            case NETHER_STAR:
            case APPLE:
            case PAPER:
            case MAGMA_CREAM:
            case BOOK:
            case SLIME_BALL:
            case WALL_SIGN:
            default:
               event.setCancelled(true);
               break;
            case ENDER_STONE:
               event.setCancelled(!this.placeCodeBlock(block, new ItemData(Material.PISTON_BASE, 4), "lang:coding.sign.else"));
            case WORKBENCH:
            case ANVIL:
            case ENDER_CHEST:
            case SILVER_SHULKER_BOX:
            case BLACK_SHULKER_BOX:
            case BLUE_SHULKER_BOX:
            case BROWN_SHULKER_BOX:
            case CYAN_SHULKER_BOX:
            case GRAY_SHULKER_BOX:
            case GREEN_SHULKER_BOX:
            case LIGHT_BLUE_SHULKER_BOX:
            case LIME_SHULKER_BOX:
            case MAGENTA_SHULKER_BOX:
            case ORANGE_SHULKER_BOX:
            case PINK_SHULKER_BOX:
            case PURPLE_SHULKER_BOX:
            case RED_SHULKER_BOX:
            case WHITE_SHULKER_BOX:
            case YELLOW_SHULKER_BOX:
            case PISTON_BASE:
            }
         }

      }
   }

   private boolean placeCodeBlock(Block block, ItemData stoneBlockMaterial, String signLine) {
      switch(block.getType()) {
      case DIAMOND_BLOCK:
      case LAPIS_BLOCK:
      case EMERALD_BLOCK:
         if (block.getRelative(BlockFace.DOWN).getData() != 3) {
            return false;
         }
         break;
      default:
         if (block.getRelative(BlockFace.DOWN).getData() != 8) {
            return false;
         }
      }

      Block stoneBlock = block.getRelative(BlockFace.WEST);
      if (stoneBlock.getType() != Material.AIR) {
         Location pos1 = stoneBlock.getLocation();
         Location pos2 = CodeUtils.getLastBlock(pos1).getBlock().getRelative(BlockFace.NORTH).getRelative(BlockFace.UP).getLocation();
         byte distance;
         switch(block.getType()) {
         case WOOD:
         case BRICK:
         case OBSIDIAN:
         case RED_NETHER_BRICK:
         case ENDER_STONE:
            distance = 4;
            break;
         default:
            distance = 2;
         }

         Location moveTo = pos2.getBlock().getRelative(BlockFace.WEST, distance).getLocation();
         Plot plot = moduleCreative.getPlotManager().getPlot(moveTo);
         if (plot == null || !plot.inTerritory(moveTo)) {
            return false;
         }

         WorldUtil.moveBlocks(pos1, pos2, BlockFace.WEST, distance, (editSession) -> {
            this.placeStoneBlock(editSession, stoneBlock, stoneBlockMaterial, signLine);
            editSession.flushQueue();
         });
      } else {
         this.placeStoneBlock((EditSession)null, stoneBlock, stoneBlockMaterial, signLine);
      }

      return true;
   }

   private Vector toVector(Location location) {
      return new Vector(location.getX(), location.getY(), location.getZ());
   }

   private void placeStoneBlock(EditSession session, Block block, ItemData stoneBlockMaterial, String signLine) {
      if (session != null) {
         try {
            session.setBlock(this.toVector(block.getLocation()), new BaseBlock(stoneBlockMaterial.getType().ordinal(), stoneBlockMaterial.getData()));
            if (stoneBlockMaterial.getType() == Material.PISTON_BASE) {
               Location endPiston = block.getRelative(BlockFace.WEST, 2).getLocation();
               if (endPiston.getBlock().getType() != Material.AIR) {
                  Location pos1 = endPiston.getBlock().getRelative(BlockFace.EAST).getLocation();
                  Location pos2 = CodeUtils.getLastBlock(pos1).getBlock().getRelative(BlockFace.NORTH).getRelative(BlockFace.UP).getLocation();
                  Location moveTo = pos2.getBlock().getRelative(BlockFace.WEST, 2).getLocation();
                  Plot plot = moduleCreative.getPlotManager().getPlot(moveTo);
                  if (plot.inTerritory(moveTo)) {
                     WorldUtil.moveBlocks(pos1, pos2, BlockFace.WEST, 2, (editSession) -> {
                        try {
                           editSession.setBlock(this.toVector(endPiston), new BaseBlock(Material.PISTON_BASE.ordinal(), 5));
                        } catch (MaxChangedBlocksException var4) {
                           var4.printStackTrace();
                        }

                        editSession.flushQueue();
                     });
                  }
               } else {
                  session.setBlock(this.toVector(endPiston), new BaseBlock(Material.PISTON_BASE.ordinal(), 5));
               }
            }
         } catch (MaxChangedBlocksException var10) {
            var10.printStackTrace();
         }
      } else {
         block.setType(stoneBlockMaterial.getType());
         block.setData((byte)stoneBlockMaterial.getData());
         if (stoneBlockMaterial.getType() == Material.PISTON_BASE) {
            block.getRelative(BlockFace.WEST, 2).setType(Material.PISTON_BASE);
            block.getRelative(BlockFace.WEST, 2).setData((byte)5);
         }
      }

      Schedule.run(() -> {
         Block signBlock = block.getRelative(BlockFace.NORTH_EAST);
         signBlock.setType(Material.WALL_SIGN, false);
         Sign sign = (Sign)signBlock.getState();
         sign.setLine(0, signLine);
         if (stoneBlockMaterial.getType() == Material.EMERALD_ORE) {
            sign.setLine(2, String.valueOf(20));
         }

         sign.update();
      });
   }

   @EventHandler
   public void on(EntityAddToWorldEvent event) {
      Entity entity = event.getEntity();
      Plot plot = moduleCreative.getPlotManager().getPlot(entity.getLocation());
      if (plot != null && entity.getWorld() == moduleCoding.getCodingWorld()) {
         switch(entity.getType()) {
         case PLAYER:
            break;
         case DROPPED_ITEM:
            switch(((Item)entity).getItemStack().getType()) {
            case SIGN:
               entity.remove();
               return;
            default:
               return;
            }
         default:
            entity.remove();
         }
      }

   }

   @EventHandler
   public void on(PlayerBucketEmptyEvent event) {
      Block block = event.getBlockClicked();
      Plot plot = moduleCreative.getPlotManager().getPlot(block.getLocation());
      if (plot != null && plot.getPlayerMode(User.getUser((PlayerEvent)event)) == PlayerMode.CODING) {
         event.setCancelled(true);
      }

   }

   @EventHandler
   public void on(PlayerItemHeldEvent event) {
      User user = User.getUser((PlayerEvent)event);
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
      if (plot != null) {
         switch(plot.getPlotMode()) {
         case PLAYING:
            PlayerChangeSlotActivator.Event gameEvent = new PlayerChangeSlotActivator.Event(user, plot, event);
            gameEvent.callEvent();
            gameEvent.handle();
         default:
         }
      }
   }

   @EventHandler
   public void on(PlayerItemConsumeEvent event) {
      User user = User.getUser((PlayerEvent)event);
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
      if (plot != null) {
         switch(plot.getPlotMode()) {
         case PLAYING:
            PlayerConsumeItemActivator.Event gameEvent = new PlayerConsumeItemActivator.Event(user, plot, event);
            gameEvent.callEvent();
            gameEvent.handle();
         default:
         }
      }
   }

   @EventHandler
   public void on(FoodLevelChangeEvent event) {
      User user = User.getUser((CommandSender)event.getEntity());
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
      if (plot == null) {
         event.setFoodLevel(20);
         event.setCancelled(true);
      } else {
         switch(plot.getPlotMode()) {
         case PLAYING:
            PlayerFoodLevelChangeActivator.Event gameEvent = new PlayerFoodLevelChangeActivator.Event(user, plot, event);
            gameEvent.callEvent();
            gameEvent.handle();
            break;
         default:
            event.setFoodLevel(20);
            event.setCancelled(true);
         }

      }
   }

   @EventHandler
   public void on(PlayerDropItemEvent event) {
      User user = User.getUser((PlayerEvent)event);
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
      if (plot != null) {
         switch(plot.getPlotMode()) {
         case PLAYING:
            PlayerDropItemActivator.Event gameEvent = new PlayerDropItemActivator.Event(user, plot, event);
            gameEvent.callEvent();
            gameEvent.handle();
         default:
         }
      }
   }

   @EventHandler
   public void on(PlayerMoveOnBlockMyEvent event) {
      User user = User.getUser((PlayerEvent)event);
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
      if (plot != null) {
         switch(plot.getPlotMode()) {
         case PLAYING:
            PlayerMoveActivator.Event gameEvent = new PlayerMoveActivator.Event(user, plot, event);
            gameEvent.callEvent();
            gameEvent.handle();
         default:
         }
      }
   }

   @EventHandler
   public void on(EntityPickupItemEvent event) {
      if (event.getEntity().getType() == EntityType.PLAYER) {
         User user = User.getUser((CommandSender)event.getEntity());
         Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
         if (plot == null) {
            return;
         }

         switch(plot.getPlotMode()) {
         case PLAYING:
            PlayerPickupItemActivator.Event gameEvent = new PlayerPickupItemActivator.Event(user, plot, event);
            gameEvent.callEvent();
            gameEvent.handle();
         }
      }

   }

   @EventHandler
   public void on(PlayerInteractEntityEvent event) {
      User user = User.getUser((PlayerEvent)event);
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
      if (plot != null) {
         switch(plot.getPlotMode()) {
         case PLAYING:
            if (event.getHand() == EquipmentSlot.HAND) {
               PlayerRightClickEntityActivator.Event gameEvent = new PlayerRightClickEntityActivator.Event(user, plot, event);
               gameEvent.callEvent();
               gameEvent.handle();
            }
         default:
         }
      }
   }

   @EventHandler
   public void on(PlayerToggleSprintEvent event) {
      User user = User.getUser((PlayerEvent)event);
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
      if (plot != null) {
         switch(plot.getPlotMode()) {
         case PLAYING:
            if (event.isSprinting()) {
               PlayerSprintActivator.Event gameEvent = new PlayerSprintActivator.Event(user, plot, event);
               gameEvent.callEvent();
               gameEvent.handle();
            } else {
               PlayerStopSprintActivator.Event gameEvent = new PlayerStopSprintActivator.Event(user, plot, event);
               gameEvent.callEvent();
               gameEvent.handle();
            }
         default:
         }
      }
   }

   @EventHandler
   public void on(PlayerToggleSneakEvent event) {
      User user = User.getUser((PlayerEvent)event);
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
      if (plot != null) {
         switch(plot.getPlotMode()) {
         case PLAYING:
            if (event.isSneaking()) {
               PlayerSneakActivator.Event gameEvent = new PlayerSneakActivator.Event(user, plot, event);
               gameEvent.callEvent();
               gameEvent.handle();
            } else {
               PlayerUnsneakActivator.Event gameEvent = new PlayerUnsneakActivator.Event(user, plot, event);
               gameEvent.callEvent();
               gameEvent.handle();
            }
         default:
         }
      }
   }

   @EventHandler
   public void on(PlayerSwapHandItemsEvent event) {
      User user = User.getUser((PlayerEvent)event);
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
      if (plot != null) {
         switch(plot.getPlotMode()) {
         case PLAYING:
            PlayerSwapHandsActivator.Event gameEvent = new PlayerSwapHandsActivator.Event(user, plot, event);
            gameEvent.callEvent();
            gameEvent.handle();
         default:
         }
      }
   }

   @EventHandler
   public void on(EntitySpawnEvent event) {
      Entity entity = event.getEntity();
      Plot plot = moduleCreative.getPlotManager().getPlot(entity.getLocation());
      if (plot != null) {
         switch(plot.getPlotMode()) {
         case PLAYING:
            plot.getCodeHandler().setLastSpawnedEntity(entity);
         default:
         }
      }
   }

   @EventHandler
   public void on(EntityDamageEvent event) {
      if (event.getEntity().getType() == EntityType.PLAYER) {
         User user = User.getUser((Player)event.getEntity());
         Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
         if (plot == null) {
            return;
         }

         switch(plot.getPlotMode()) {
         case PLAYING:
            PlayerTakeDamageActivator.Event damageEvent = new PlayerTakeDamageActivator.Event(user, plot, event);
            damageEvent.callEvent();
            damageEvent.handle();
            switch(event.getCause()) {
            case FALL:
               PlayerTakeFallDamageActivator.Event gameEvent = new PlayerTakeFallDamageActivator.Event(user, plot, event);
               gameEvent.callEvent();
               gameEvent.handle();
            }
         }
      }

   }

   @EventHandler
   public void on(EntityDamageByEntityMyEvent event) {
      this.onDamage(event.getDamager(), event.getEntity(), event, event);
   }

   @EventHandler
   public void on(HangingBreakByEntityEvent event) {
      this.onDamage(event.getRemover(), event.getEntity(), event, event);
   }

   private void onDamage(Entity damager, Entity entity, Event event, Cancellable cancellable) {
      User damagerUser = User.getUser(PlayerUtil.getDamager(damager));
      Plot plot = moduleCreative.getPlotManager().getPlot(entity.getLocation());
      if (plot != null) {
         switch(plot.getPlotMode()) {
         case PLAYING:
            EntityDamageCommonEvent commonEvent = new EntityDamageCommonEvent(event, cancellable, entity, damager);
            if (entity.getType() == EntityType.PLAYER) {
               User user = User.getUser((CommandSender)entity);
               if (damager instanceof Player) {
                  PlayerDamagePlayerActivator.Event gameEvent = new PlayerDamagePlayerActivator.Event(damagerUser, plot, commonEvent);
                  gameEvent.callEvent();
                  gameEvent.handle();
               } else if (damager instanceof Projectile) {
                  PlayerProjectileDamageActivator.Event gameEvent = new PlayerProjectileDamageActivator.Event(user, plot, commonEvent);
                  gameEvent.callEvent();
                  gameEvent.handle();
               } else if (damager instanceof LivingEntity) {
                  PlayerMobDamagePlayerActivator.Event gameEvent = new PlayerMobDamagePlayerActivator.Event(user, plot, commonEvent);
                  gameEvent.callEvent();
                  gameEvent.handle();
               }
            } else if (damager.getType() == EntityType.PLAYER) {
               PlayerDamageMobActivator.Event gameEvent = new PlayerDamageMobActivator.Event(damagerUser, plot, commonEvent);
               gameEvent.callEvent();
               gameEvent.handle();
            }
            break;
         case BUILDING:
            if (damagerUser != null && damagerUser.getPlayer().getGameMode() != GameMode.CREATIVE) {
               cancellable.setCancelled(true);
            }
         }

      }
   }

   @EventHandler
   public void on(EntityDeathEvent event) {
      LivingEntity entity = event.getEntity();
      Plot plot = moduleCreative.getPlotManager().getPlot(entity.getLocation());
      if (plot != null) {
         switch(plot.getPlotMode()) {
         case PLAYING:
            if (entity.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
               EntityDamageByEntityEvent lastDamageCause = (EntityDamageByEntityEvent)entity.getLastDamageCause();
               if (lastDamageCause.getDamager().getType() == EntityType.PLAYER) {
                  User user = User.getUser((CommandSender)lastDamageCause.getDamager());
                  PlayerKillMobActivator.Event gameEvent = new PlayerKillMobActivator.Event(user, plot, event);
                  gameEvent.callEvent();
                  gameEvent.handle();
               }
            }
         default:
         }
      }
   }
}
