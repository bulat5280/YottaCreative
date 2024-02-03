package ua.govnojon.libs.bukkitutil.chatcomponent;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

public class ChatComponentUtil {
   private static final HashMap<String, String> mapTypes = load0();
   private static final HashMap<String, String> mapColor = load1();
   private static Pattern color = Pattern.compile("[&§].");

   private static HashMap<String, String> load0() {
      HashMap<String, String> map = new HashMap();
      map.put("obfuscated", "k");
      map.put("bold", "l");
      map.put("strikethrough", "m");
      map.put("underlined", "n");
      map.put("italic", "o");
      return map;
   }

   private static HashMap<String, String> load1() {
      HashMap<String, String> map = new HashMap();
      map.put("black", "0");
      map.put("dark_blue", "1");
      map.put("dark_green", "2");
      map.put("dark_aqua", "3");
      map.put("dark_red", "4");
      map.put("dark_purple", "5");
      map.put("gold", "6");
      map.put("gray", "7");
      map.put("dark_gray", "8");
      map.put("blue", "9");
      map.put("green", "a");
      map.put("aqua", "b");
      map.put("red", "c");
      map.put("light_purple", "d");
      map.put("yellow", "e");
      map.put("white", "f");
      return map;
   }

   public static String jsonToText(String json) {
      if (json.length() < 33) {
         return "";
      } else {
         if (json.contains("{\"extra\":[{\"text\":\"")) {
            json = json.substring(10, json.length() - 12);
         }

         StringBuilder newLine = new StringBuilder();
         char[] value = json.toCharArray();
         int posStart = -1;
         StringBuilder read = new StringBuilder();
         boolean readText = false;

         for(int pos = 0; pos < value.length; ++pos) {
            if (value[pos] == '"') {
               if (posStart == -1) {
                  posStart = pos + 1;
               } else {
                  char[] temp = new char[pos - posStart];
                  System.arraycopy(value, posStart, temp, 0, temp.length);
                  posStart = -1;
                  String text = new String(temp);
                  if (readText) {
                     newLine.append(newLine.length() > 0 ? "§r" : "").append(read).append(text);
                     read = new StringBuilder();
                     readText = false;
                  } else if (text.equals("text")) {
                     readText = true;
                  } else {
                     String color = (String)mapColor.get(text);
                     if (color != null) {
                        read.insert(0, "§" + color);
                     } else {
                        String type = (String)mapTypes.get(text);
                        if (type != null) {
                           read.append("§").append(type);
                        }
                     }
                  }
               }
            }
         }

         return StringEscapeUtils.unescapeJava(newLine.toString());
      }
   }

   public static String textToJson(String text) {
      return "{\"extra\":[{\"text\":\"" + JSONObject.escape(text) + "\"}],\"text\":\"\"}";
   }

   public static String removeColors(String text) {
      return color.matcher(text).replaceAll("");
   }

   public static void fixComponent(BaseComponent component) {
      if (component.getExtra() != null) {
         component.getExtra().forEach(ChatComponentUtil::fixComponent);
      }

      if (component instanceof TextComponent) {
         TextComponent text = (TextComponent)component;
         if (StringUtils.contains(text.getText(), 167)) {
            BaseComponent[] components = TextComponent.fromLegacyText(text.getText());
            List<BaseComponent> extra = text.getExtra();
            if (extra == null) {
               extra = Lists.newArrayList(components);
            } else {
               ((List)extra).addAll(0, Lists.newArrayList(components));
            }

            text.setText("");
            text.setExtra((List)extra);
         }
      }

   }

   public static void fixVisibleColors(BaseComponent component) {
      List<BaseComponent> extra = component.getExtra();
      if (extra != null) {
         extra.forEach(ChatComponentUtil::fixVisibleColors);
      }

      HoverEvent hoverEvent = component.getHoverEvent();
      if (hoverEvent != null) {
         Arrays.stream(hoverEvent.getValue()).forEach(ChatComponentUtil::fixVisibleColors);
      }

      if (component instanceof TextComponent) {
         TextComponent text = (TextComponent)component;
         text.setText(fixVisibleColors(text.getText()));
      }

   }

   public static String fixVisibleColors(String text) {
      char[] chars = text.toCharArray();

      for(int i = 0; i < chars.length - 1; ++i) {
         char c = chars[i];
         if (c == 167) {
            chars[i + 1] = replaceColor0(chars[i + 1]);
         }
      }

      return new String(chars);
   }

   private static char replaceColor0(char c) {
      switch(c) {
      case 'a':
         return '2';
      case 'b':
         return '3';
      case 'c':
      case 'd':
      default:
         return c;
      case 'e':
         return '6';
      case 'f':
         return '0';
      }
   }
}
