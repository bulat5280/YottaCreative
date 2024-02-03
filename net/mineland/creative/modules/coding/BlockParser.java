package net.mineland.creative.modules.coding;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.region.ModuleRegion;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionIf;
import net.mineland.creative.modules.coding.actions.ActionSelect;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.actions.ArrayAction;
import net.mineland.creative.modules.coding.actions.PlayerSelection;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.activators.FunctionActivator;
import net.mineland.creative.modules.coding.activators.GameLoopActivator;
import net.mineland.creative.modules.coding.worldactivators.WorldActivator;
import net.mineland.creative.modules.coding.worldactivators.WorldActivatorType;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.chatcomponent.ChatComponentUtil;

public class BlockParser {
   private static ModuleRegion moduleRegion = (ModuleRegion)Module.getInstance(ModuleRegion.class);
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
   private Plot plot;
   private List<Location> positions;

   public BlockParser(Plot plot, List<Location> positions) {
      this.positions = positions;
      this.plot = plot;
   }

   public List<Activator> parseActivators() {
      List<Activator> activators = new LinkedList();
      List<WorldActivator> activatorsWorld = new LinkedList();
      Map<Block, List<Action>> cache = new HashMap();
      this.positions.forEach((position) -> {
         Block signBlock = position.getBlock();
         String name = this.getSignLine(signBlock);
         if (!name.isEmpty()) {
            List actionList;
            switch(signBlock.getRelative(BlockFace.SOUTH).getType()) {
            case REDSTONE_BLOCK:
               WorldActivator worldActivator = WorldActivatorType.valueOf(name).create(this.plot);
               signBlock = signBlock.getRelative(BlockFace.WEST, 2);
               actionList = this.parseActions(signBlock.getLocation(), worldActivator, cache);
               worldActivator.getActionList().addAll(actionList);
               activatorsWorld.add(worldActivator);
               break;
            case DIAMOND_BLOCK:
               Activator activator1 = ActivatorType.valueOf(name).create(this.plot);
               signBlock = signBlock.getRelative(BlockFace.WEST, 2);
               actionList = this.parseActions(signBlock.getLocation(), activator1, cache);
               activator1.getActionList().addAll(actionList);
               activators.add(activator1);
               break;
            case LAPIS_BLOCK:
               FunctionActivator functionActivator = (FunctionActivator)ActivatorType.FUNCTION.create(this.plot);
               signBlock = signBlock.getRelative(BlockFace.WEST, 2);
               actionList = this.parseActions(signBlock.getLocation(), (Activator)functionActivator, cache);
               functionActivator.getActionList().addAll(actionList);
               functionActivator.setCustomName(name);
               functionActivator.setLocation(position);
               activators.add(functionActivator);
               break;
            case EMERALD_BLOCK:
               GameLoopActivator activator = (GameLoopActivator)ActivatorType.GAME_LOOP.create(this.plot);
               signBlock = signBlock.getRelative(BlockFace.WEST, 2);
               actionList = this.parseActions(signBlock.getLocation(), (Activator)activator, cache);
               activator.getActionList().addAll(actionList);
               activator.setCustomName(name);
               activator.setTicks(Integer.parseInt(this.getSignLine(position.getBlock(), 2)));
               activators.add(activator);
            }

         }
      });
      return activators;
   }

   public List<Action> parseActions(Location position, Activator activator, Map<Block, List<Action>> cache) {
      Block signBlock = position.getBlock();
      LinkedList actionList = new LinkedList();

      while(true) {
         while(signBlock.getType() == Material.WALL_SIGN) {
            Block mainBlock = signBlock.getRelative(BlockFace.SOUTH);
            String name = this.getSignLine(signBlock);
            if (name.isEmpty()) {
               if (mainBlock.getType() != Material.ENDER_STONE) {
                  signBlock = signBlock.getRelative(BlockFace.WEST, 2);
                  continue;
               }

               name = this.getSignLine(signBlock, 0);
            }

            Action action = ActionType.create(name, activator);
            if (action == null) {
               if (mainBlock.getType() == Material.LAPIS_ORE) {
                  action = ActionType.CALL_FUNCTION.create(activator);
                  action.putVar("name", (Object)this.getSignLine(signBlock, 1));
                  actionList.add(action);
               }

               signBlock = signBlock.getRelative(BlockFace.WEST, 2);
            } else {
               switch(mainBlock.getType()) {
               case WOOD:
               case BRICK:
               case OBSIDIAN:
               case RED_NETHER_BRICK:
               case IRON_BLOCK:
               case ENDER_STONE:
               case NETHER_BRICK:
               case PURPUR_BLOCK:
               case COBBLESTONE:
                  Block chestBlock = mainBlock.getRelative(BlockFace.UP);
                  ChestParser chestParser = new ChestParser(new ItemStack[0]);
                  if (chestBlock.getType() == Material.CHEST) {
                     Chest chest = (Chest)chestBlock.getState();
                     ItemStack[] itemStacks = chest.getBlockInventory().getContents();
                     chestParser = new ChestParser(itemStacks);
                     action.parseChest(itemStacks);
                     action.parseChest(new ChestParser(itemStacks));
                  }

                  String inverted;
                  if (mainBlock.getType() == Material.PURPUR_BLOCK) {
                     ActionSelect selectObjectAction = (ActionSelect)action;
                     inverted = this.getSignLine(signBlock, 2);
                     if (!inverted.isEmpty()) {
                        ActionIf conditionAction = (ActionIf)ActionType.create(inverted, activator);
                        if (conditionAction != null) {
                           String inverted = this.getSignLine(signBlock, 3);
                           boolean isInverted = !inverted.isEmpty() && inverted.equalsIgnoreCase("not");
                           conditionAction.setInverted(isInverted);
                           conditionAction.parseChest(chestParser);
                           selectObjectAction.setCondition(conditionAction);
                        }
                     }
                  } else {
                     String selection = this.getSignLine(signBlock, 2);

                     try {
                        PlayerSelection playerSelection = PlayerSelection.valueOf(selection.toUpperCase());
                        action.setSelection(playerSelection);
                     } catch (IllegalArgumentException var16) {
                     }
                  }

                  actionList.add(action);
                  if (action instanceof ActionIf) {
                     ActionIf actionIf = (ActionIf)action;
                     inverted = this.getSignLine(signBlock, 3);
                     if (!inverted.isEmpty() && inverted.equalsIgnoreCase("not")) {
                        actionIf.setInverted(true);
                     } else {
                        actionIf.setInverted(false);
                     }
                  }

                  signBlock = signBlock.getRelative(BlockFace.WEST, 2);
                  if (action instanceof ArrayAction) {
                     ArrayAction arrayAction = (ArrayAction)action;
                     List<Action> actions = (List)cache.get(signBlock);
                     if (actions != null) {
                        arrayAction.setActions(actions);
                     } else {
                        List<Action> result = this.parseActions(signBlock.getLocation(), activator, cache);
                        arrayAction.getActions().addAll(result);
                        cache.put(signBlock, result);
                     }

                     Block startPistonBlock = mainBlock.getRelative(BlockFace.WEST);
                     Block endPistonBlock = CodeUtils.getEndPistonBlock(startPistonBlock);
                     if (endPistonBlock != null) {
                        signBlock = endPistonBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.NORTH);
                     }
                  }
                  break;
               default:
                  signBlock = signBlock.getRelative(BlockFace.WEST, 2);
               }
            }
         }

         return actionList;
      }
   }

   public List<Action> parseActions(Location position, WorldActivator activator, Map<Block, List<Action>> cache) {
      Block signBlock = position.getBlock();
      LinkedList actionList = new LinkedList();

      while(true) {
         while(signBlock.getType() == Material.WALL_SIGN) {
            Block mainBlock = signBlock.getRelative(BlockFace.SOUTH);
            String name = this.getSignLine(signBlock);
            if (name.isEmpty()) {
               if (mainBlock.getType() != Material.ENDER_STONE) {
                  signBlock = signBlock.getRelative(BlockFace.WEST, 2);
                  continue;
               }

               name = this.getSignLine(signBlock, 0);
            }

            Action action = ActionType.createTwo(name, activator);
            if (action == null) {
               if (mainBlock.getType() == Material.LAPIS_ORE) {
                  action = ActionType.CALL_FUNCTION.createTwo(activator);
                  action.putVar("name", (Object)this.getSignLine(signBlock, 1));
                  actionList.add(action);
               }

               signBlock = signBlock.getRelative(BlockFace.WEST, 2);
            } else {
               switch(mainBlock.getType()) {
               case WOOD:
               case BRICK:
               case OBSIDIAN:
               case RED_NETHER_BRICK:
               case IRON_BLOCK:
               case ENDER_STONE:
               case NETHER_BRICK:
               case PURPUR_BLOCK:
               case COBBLESTONE:
                  Block chestBlock = mainBlock.getRelative(BlockFace.UP);
                  ChestParser chestParser = new ChestParser(new ItemStack[0]);
                  if (chestBlock.getType() == Material.CHEST) {
                     Chest chest = (Chest)chestBlock.getState();
                     ItemStack[] itemStacks = chest.getBlockInventory().getContents();
                     chestParser = new ChestParser(itemStacks);
                     action.parseChest(itemStacks);
                     action.parseChest(new ChestParser(itemStacks));
                  }

                  String inverted;
                  if (mainBlock.getType() == Material.PURPUR_BLOCK) {
                     ActionSelect selectObjectAction = (ActionSelect)action;
                     inverted = this.getSignLine(signBlock, 2);
                     if (!inverted.isEmpty()) {
                        ActionIf conditionAction = (ActionIf)ActionType.createTwo(inverted, activator);
                        if (conditionAction != null) {
                           String inverted = this.getSignLine(signBlock, 3);
                           boolean isInverted = !inverted.isEmpty() && inverted.equalsIgnoreCase("not");
                           conditionAction.setInverted(isInverted);
                           conditionAction.parseChest(chestParser);
                           selectObjectAction.setCondition(conditionAction);
                        }
                     }
                  } else {
                     String selection = this.getSignLine(signBlock, 2);

                     try {
                        PlayerSelection playerSelection = PlayerSelection.valueOf(selection.toUpperCase());
                        action.setSelection(playerSelection);
                     } catch (IllegalArgumentException var16) {
                     }
                  }

                  actionList.add(action);
                  if (action instanceof ActionIf) {
                     ActionIf actionIf = (ActionIf)action;
                     inverted = this.getSignLine(signBlock, 3);
                     if (!inverted.isEmpty() && inverted.equalsIgnoreCase("not")) {
                        actionIf.setInverted(true);
                     } else {
                        actionIf.setInverted(false);
                     }
                  }

                  signBlock = signBlock.getRelative(BlockFace.WEST, 2);
                  if (action instanceof ArrayAction) {
                     ArrayAction arrayAction = (ArrayAction)action;
                     List<Action> actions = (List)cache.get(signBlock);
                     if (actions != null) {
                        arrayAction.setActions(actions);
                     } else {
                        List<Action> result = this.parseActions(signBlock.getLocation(), activator, cache);
                        arrayAction.getActions().addAll(result);
                        cache.put(signBlock, result);
                     }

                     Block startPistonBlock = mainBlock.getRelative(BlockFace.WEST);
                     Block endPistonBlock = CodeUtils.getEndPistonBlock(startPistonBlock);
                     if (endPistonBlock != null) {
                        signBlock = endPistonBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.NORTH);
                     }
                  }
                  break;
               default:
                  signBlock = signBlock.getRelative(BlockFace.WEST, 2);
               }
            }
         }

         return actionList;
      }
   }

   public String getSignLine(Block signBlock) {
      return this.getSignLine(signBlock, 1);
   }

   public String getSignLine(Block signBlock, int line) {
      if (signBlock.getType() == Material.WALL_SIGN) {
         Sign sign = (Sign)signBlock.getState();
         String text = ChatComponentUtil.removeColors(sign.getLine(line));
         return text.startsWith("lang:coding.sign.") ? text.substring(17).toUpperCase() : text;
      } else {
         return "";
      }
   }
}
