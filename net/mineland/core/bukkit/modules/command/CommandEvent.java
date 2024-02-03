package net.mineland.core.bukkit.modules.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.locale.ModuleLocale;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.nms.NMS;
import net.mineland.core.bukkit.modules.user.Lang;
import net.mineland.core.bukkit.modules.user.PlayerId;
import net.mineland.core.bukkit.modules.user.User;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.BukkitUtil;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;
import ua.govnojon.libs.bukkitutil.PlayerUtil;
import ua.govnojon.libs.bukkitutil.chatcomponent.ChatComponent;
import ua.govnojon.libs.bukkitutil.chatcomponent.Click;
import ua.govnojon.libs.bukkitutil.chatcomponent.Hover;

public class CommandEvent {
   private static ModuleLocale moduleLocale = (ModuleLocale)Module.getInstance(ModuleLocale.class);
   private static ModuleCommand moduleCommand = (ModuleCommand)Module.getInstance(ModuleCommand.class);
   private static Map<Character, Long> timeValue = new HashMap();
   private static List<Character> numbers = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
   private Command command;
   private String[] args;
   private CommandSender sender;
   private String currentName;

   public CommandEvent(Command command, String[] args, CommandSender sender, String currentName) {
      this.command = command;
      this.args = args;
      this.sender = sender;
      this.currentName = currentName;
   }

   public CommandSender getSender() {
      return this.sender;
   }

   public Player getPlayer() {
      return (Player)this.sender;
   }

   public User getUser() {
      return User.getUser(this.sender);
   }

   public String arg(int index) {
      return this.args[index];
   }

   public String[] getArgs() {
      return this.args;
   }

   public boolean has(int index, String name) {
      return this.args[index].equals(name);
   }

   public boolean has(int index, String... variables) {
      return ArrayUtils.contains(variables, this.args[index]);
   }

   public boolean isEmpty() {
      return this.args.length == 0;
   }

   public int size() {
      return this.args.length;
   }

   public boolean hasSize(int size) {
      return this.size() == size;
   }

   public void sendCompleted() {
      this.sendMessage("command_send_completed");
   }

   public void sendMessage(String key, String... replaced) {
      Message.sendMessage(this.sender, key, replaced);
   }

   public void setNextUse(long delay) {
      moduleCommand.setTimeSinceLastUse(this.command, User.getUser(this.sender), delay);
   }

   public World getWorld(String arg) {
      World world = Bukkit.getWorld(arg);
      if (world == null) {
         this.sendMessage("мир_не_найден", "%worlds%", String.join(", ", this.getWorlds()));
         throw new CommandException();
      } else {
         return world;
      }
   }

   public void tabComplete(@Nullable List<String> list) {
      if (list == null) {
         list = Collections.emptyList();
      } else {
         list = Command.filterTabResponse(list, this.args);
      }

      NMS.getManager().sendTabComplete(this.getPlayer(), (String[])list.toArray(new String[0]));
   }

   public boolean isWorld(String arg) {
      return Bukkit.getWorld(arg) != null;
   }

   public int getInt(String arg) {
      try {
         return Integer.parseInt(arg);
      } catch (Exception var3) {
         this.errorBadValue(arg);
         return 0;
      }
   }

   public float getFloat(String arg) {
      try {
         return Float.parseFloat(arg);
      } catch (Exception var3) {
         this.errorBadValue(arg);
         return 0.0F;
      }
   }

   public ItemData getItemData(String arg) {
      ItemData itemData = ItemData.byString(arg);
      if (itemData == null) {
         this.errorBadValue(arg);
      }

      return itemData;
   }

   public double getDouble(String arg) {
      try {
         return Double.parseDouble(arg);
      } catch (Exception var3) {
         this.errorBadValue(arg);
         return 0.0D;
      }
   }

   public void errorNotFound(String arg) {
      this.sender.sendMessage(Message.getMessage(this.sender, "не_найдено").replace("%name%", arg));
      throw new CommandException();
   }

   public void errorBadValue(String arg) {
      this.sender.sendMessage(Message.getMessage(this.sender, "некорректное_значение").replace("%value%", arg));
      throw new CommandException();
   }

   public void errorArgumentNotFound() {
      this.sender.sendMessage(Message.getMessage(this.sender, "аргумент_не_найден"));
      throw new CommandException();
   }

   public void checkDiapason(double value, double min, double max) {
      if (value < min || value > max) {
         this.sender.sendMessage(Message.getMessage(this.sender, "укажите_значение_в_диапазоне").replace("%value1%", this.getDisplay(min)).replace("%value2%", "" + this.getDisplay(max)));
         throw new CommandException();
      }
   }

   private String getDisplay(double d) {
      return d == (double)((int)d) ? String.valueOf((int)d) : String.valueOf(d);
   }

   private double getCoord(String arg) {
      double xyz = this.getDouble(arg);
      this.checkDiapason(xyz, -2.9999999E7D, 2.9999999E7D);
      return xyz;
   }

   public double getCoordX(String arg) {
      if (arg.contains("~")) {
         this.checkIsConsole();
         return ((Player)this.sender).getLocation().getX() + this.getMegaCoord(arg);
      } else {
         return this.getCoord(arg);
      }
   }

   public double getCoordY(String arg) {
      if (arg.contains("~")) {
         this.checkIsConsole();
         return ((Player)this.sender).getLocation().getY() + this.getMegaCoord(arg);
      } else {
         return this.getCoord(arg);
      }
   }

   public double getCoordZ(String arg) {
      if (arg.contains("~")) {
         this.checkIsConsole();
         return ((Player)this.sender).getLocation().getZ() + this.getMegaCoord(arg);
      } else {
         return this.getCoord(arg);
      }
   }

   private double getMegaCoord(String arg) {
      return arg.equals("~") ? 0.0D : this.getDouble(arg.replace("~", ""));
   }

   public boolean isPlayer(String name) {
      return User.getPlayer(name) != null;
   }

   public boolean hasPerm(String perm) {
      return !(this.sender instanceof Player) || this.sender.hasPermission(perm);
   }

   public void checkPerm(String perm) {
      this.checkPerm(perm, "нет_прав");
   }

   public void checkPerm(String perm, String denyMessageKey) {
      if (!this.hasPerm(perm)) {
         this.sender.sendMessage(Message.getMessage(this.sender, denyMessageKey));
         throw new CommandException();
      }
   }

   public boolean isSenderPlayer() {
      return this.sender instanceof Player;
   }

   public void checkIsConsole() {
      if (!this.isSenderPlayer()) {
         this.sender.sendMessage("Нельзя писать с консоли.");
         throw new CommandException();
      }
   }

   public void checkIsThisPlayer(String arg) {
      if (this.sender.getName().equalsIgnoreCase(arg)) {
         this.sender.sendMessage(Message.getMessage(this.sender, "нельзя_указывать_себя"));
         throw new CommandException();
      }
   }

   public void checkSizeArguments(int min) throws CommandException {
      if (this.args.length < min) {
         String message = Message.getMessage(this.sender, "неправильное_количество_аргументов");
         String name = this.currentName;
         StringBuilder usage = new StringBuilder("/" + name + " ");
         String[] var5 = this.args;
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String arg = var5[var7];
            usage.append(arg).append(" ");
         }

         for(int i = this.args.length; i < min; ++i) {
            usage.append("[???] ");
         }

         message = StringUtils.replace(message, "%usage%", usage.toString());
         message = StringUtils.replace(message, "%cmd%", "/" + name);
         this.sender.sendMessage(message);
         throw new CommandException();
      }
   }

   public boolean getBoolean(String value) {
      try {
         return Boolean.parseBoolean(value);
      } catch (Exception var3) {
         this.errorBadValue(value);
         return false;
      }
   }

   public List<String> getPlayers() {
      return User.getPlayersNames();
   }

   public List<String> getWorlds() {
      return (List)Bukkit.getWorlds().stream().map(World::getName).collect(Collectors.toList());
   }

   public String getArguments(int start) {
      StringBuilder s = new StringBuilder();

      for(int i = start; i < this.args.length; ++i) {
         s.append(i == start ? this.args[i] : ' ' + this.args[i]);
      }

      return s.toString();
   }

   public String getArguments(int start, String[] args) {
      StringBuilder s = new StringBuilder();

      for(int i = start; i < args.length; ++i) {
         s.append(i == start ? args[i] : ' ' + args[i]);
      }

      return s.toString();
   }

   public Material getMaterial(String arg) {
      Material material = BukkitUtil.getMaterial(arg);
      if (material == null) {
         this.sender.sendMessage(Message.getMessage(this.sender, "материал_не_найден"));
         throw new CommandException();
      } else {
         return material;
      }
   }

   public void argsToLowerCase0() {
      this.argsToLowerCase(0);
   }

   public void argsToLowerCase(int i) {
      try {
         this.args[i] = this.args[i].toLowerCase();
      } catch (ArrayIndexOutOfBoundsException var3) {
      }

   }

   public Block getTargetBlock() {
      Block block = ((Player)this.sender).getTargetBlock((Set)null, 7);
      if (block != null && !block.getType().equals(Material.AIR)) {
         return block;
      } else {
         this.sender.sendMessage(Message.getMessage(this.sender, "блок_не_найден"));
         throw new CommandException();
      }
   }

   public Block getTargetBlock(Material material) {
      Block block = this.getTargetBlock();
      if (!block.getType().equals(material)) {
         User user = User.getUser(this.sender);
         Message.sendMessage(user, "смотрите_на_материал", "%name%", moduleLocale.getName(material, user.getLang()));
         throw new CommandException();
      } else {
         return block;
      }
   }

   public Lang getLangSender() {
      return this.isSenderPlayer() ? User.getUser((Player)this.sender).getLang() : Lang.RU;
   }

   public long getLong(String arg) {
      try {
         return Long.parseLong(arg);
      } catch (Exception var3) {
         this.errorBadValue(arg);
         throw new CommandException();
      }
   }

   public void errorNotFoundPlayer() {
      Message.sendMessage(this.sender, "этого_игрока_никогда_не_было");
      throw new CommandException();
   }

   public Player getPlayer(String name) {
      Player player = User.getPlayer(name);
      if (player != null && this.isSenderPlayer()) {
         User userIgnore = User.getUser(player);
         User user = this.getUser();
         if (userIgnore.hasMetadata("vanish") && !user.hasMetadata("vanish")) {
            player = null;
         }
      }

      if (player != null) {
         return player;
      } else {
         List<PlayerId> players = (List)User.getUsers().stream().filter((userx) -> {
            return !userx.hasMetadata("vanish") || !this.isSenderPlayer() || this.getUser().hasMetadata("vanish");
         }).filter((userx) -> {
            return StringUtils.containsIgnoreCase(userx.getPlayer().getName(), name);
         }).limit(10L).map(PlayerId::new).collect(Collectors.toList());
         if (players.isEmpty()) {
            Message.sendMessage(this.sender, "игрок_не_найден");
            throw new CommandException();
         } else {
            this.selectName(players, name, "игрок_не_найден_выбор");
            throw new CommandException();
         }
      }
   }

   public User getUser(String name) {
      return User.getUser(this.getPlayer(name));
   }

   public void getPlayerData(String name, boolean check, Consumer<PlayerId> consumer) {
      User.getModuleUser().getPlayerData(name, (playerId) -> {
         if (playerId != null && this.isSenderPlayer()) {
            User userIgnore = playerId.getUser();
            User user = this.getUser();
            if (userIgnore != null && userIgnore.hasMetadata("vanish") && !user.hasMetadata("vanish")) {
               playerId = null;
            }
         }

         consumer.accept(playerId);
      });
   }

   private void selectName(List<PlayerId> players, String name, String messageKey) {
      Lang lang = Lang.forSender(this.sender);
      ChatComponent component = new ChatComponent();
      component.addText(Message.getMessage(lang, messageKey));
      String select = Message.getMessage(lang, "выбрать_ник");
      PlayerUtil.getDisplayNamesByPlayerIds(players, (names) -> {
         boolean first = true;
         Iterator<String> iterator = names.iterator();
         Iterator var8 = players.iterator();

         while(var8.hasNext()) {
            PlayerId player = (PlayerId)var8.next();
            String display = (String)iterator.next();
            if (first) {
               first = false;
            } else {
               component.addText("§c, ");
            }

            StringBuilder command = new StringBuilder();
            command.append("/").append(this.command.getName());
            Arrays.stream(this.args).forEach((arg) -> {
               command.append(" ").append(arg);
            });
            component.addText(display, Hover.show_text, select.replace("{player}", display), Click.suggest_command, command.toString().replace(name, player.getName()));
         }

         component.addText("§c.");
         component.send(this.sender);
      });
   }

   public ItemStack getItemInMainHand(Material... material) {
      ItemStack item = this.getItemInMainHand();
      List<Material> materials = Arrays.asList(material);
      if (!materials.contains(item.getType())) {
         User user = User.getUser(this.sender);
         StringBuilder display = new StringBuilder();
         Iterator var6 = materials.iterator();

         while(var6.hasNext()) {
            Material mat = (Material)var6.next();
            display.append(display.length() == 0 ? "" : ", ").append(moduleLocale.getName(mat, user.getLang()));
         }

         user.sendMessage("возьмите_предмет_в_руку_типа", "%name%", display.toString());
         throw new CommandException();
      } else {
         return item;
      }
   }

   public ItemStack getItemInMainHand() {
      Player player = (Player)this.sender;
      ItemStack item = player.getInventory().getItemInMainHand();
      if (ItemStackUtil.isNullOrAir(item)) {
         Message.sendMessage(player, "возьмите_предмет_в_руку");
         throw new CommandException();
      } else {
         return item;
      }
   }

   public <T extends Enum<T>> T getEnum(T[] enums, String name) {
      return this.getEnum(enums, name, true);
   }

   public <T extends Enum<T>> T getEnum(T[] enums, String name, boolean throwException) {
      Enum[] var4 = enums;
      int var5 = enums.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Enum<T> tEnum = var4[var6];
         if (tEnum.name().equalsIgnoreCase(name)) {
            return tEnum;
         }
      }

      if (throwException) {
         Message.sendMessage(this.sender, "укажите_одно_из_перечисленных", "%value%", name, "%values%", (String)Stream.of(enums).map(Enum::name).collect(Collectors.joining("§c, ")));
         throw new CommandException();
      } else {
         return null;
      }
   }

   public void errorFound(String name) {
      Message.sendMessage(this.sender, "значеие_уже_есть", "{name}", name);
      throw new CommandException();
   }

   public <T extends Enum> List<String> getEnumNames(T[] values) {
      return (List)Stream.of(values).map(Enum::name).collect(Collectors.toList());
   }

   public long getTime(String arg) {
      long time = 0L;
      char[] chars = arg.toCharArray();
      StringBuilder value = new StringBuilder();
      char[] var6 = chars;
      int var7 = chars.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         char c = var6[var8];
         if (numbers.contains(c)) {
            value.append(c);
         } else {
            Long type = (Long)timeValue.get(c);
            if (type == null) {
               this.errorTime(value.toString() + c, this.sender);
               throw new CommandException();
            }

            if (value.length() == 0) {
               this.errorTime("?" + c, this.sender);
               throw new CommandException();
            }

            time += type * (long)this.getInt(value.toString());
            value = new StringBuilder();
         }
      }

      if (value.length() != 0) {
         this.errorTime(value.toString() + "?", this.sender);
         throw new CommandException();
      } else if (time == 0L) {
         this.errorTime(arg, this.sender);
         throw new CommandException();
      } else {
         return time;
      }
   }

   private void errorTime(String data, CommandSender sender) {
      Message.sendMessage(sender, "некорректное.время", "{value}", data);
   }

   static {
      timeValue.put('д', 86400000L);
      timeValue.put('Д', 86400000L);
      timeValue.put('d', 86400000L);
      timeValue.put('D', 86400000L);
      timeValue.put('ч', 3600000L);
      timeValue.put('Ч', 3600000L);
      timeValue.put('h', 3600000L);
      timeValue.put('H', 3600000L);
      timeValue.put('м', 60000L);
      timeValue.put('М', 60000L);
      timeValue.put('m', 60000L);
      timeValue.put('M', 60000L);
      timeValue.put('с', 1000L);
      timeValue.put('С', 1000L);
      timeValue.put('s', 1000L);
      timeValue.put('S', 1000L);
   }
}
