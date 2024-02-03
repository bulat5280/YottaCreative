package org.jooq.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jooq.tools.StringUtils;

public abstract class GeneratorWriter<W extends GeneratorWriter<W>> {
   private static final Pattern PATTERN_LIST = Pattern.compile("\\[(?:\\[before=([^\\]]+)\\])?(?:\\[separator=([^\\]]+)\\])?(?:\\[after=([^\\]]+)\\])?(?:\\[(.*)\\])\\]", 32);
   private final File file;
   private final String encoding;
   private final StringBuilder sb;
   private int indentTabs;
   private String tabString;
   private boolean newline;

   protected GeneratorWriter(File file) {
      this(file, (String)null);
   }

   protected GeneratorWriter(File file, String encoding) {
      this.tabString = "    ";
      this.newline = true;
      file.getParentFile().mkdirs();
      this.file = file;
      this.encoding = encoding;
      this.sb = new StringBuilder();
   }

   public void tabString(String string) {
      this.tabString = string;
   }

   public File file() {
      return this.file;
   }

   public W print(char value) {
      this.print("" + value);
      return this;
   }

   public W print(int value) {
      this.print("" + value);
      return this;
   }

   public W print(String string) {
      this.print(string);
      return this;
   }

   public W print(String string, Object... args) {
      string = string.replaceAll("\t", this.tabString);
      if (this.newline && this.indentTabs > 0) {
         for(int i = 0; i < this.indentTabs; ++i) {
            this.sb.append(this.tabString);
         }

         this.newline = false;
         this.indentTabs = 0;
      }

      if (args.length > 0) {
         List<Object> originals = Arrays.asList(args);
         ArrayList translated = new ArrayList();

         while(true) {
            Iterator var5 = ((List)originals).iterator();

            while(true) {
               while(var5.hasNext()) {
                  Object arg = var5.next();
                  if (arg instanceof Class) {
                     translated.add(this.ref((Class)arg));
                  } else if (!(arg instanceof Object[]) && !(arg instanceof Collection)) {
                     translated.add(arg);
                  } else {
                     if (arg instanceof Collection) {
                        arg = ((Collection)arg).toArray();
                     }

                     int start = string.indexOf("[[");
                     int end = string.indexOf("]]");
                     String expression = string.substring(start, end + 2);
                     StringBuilder replacement = new StringBuilder();
                     Matcher m = PATTERN_LIST.matcher(expression);
                     m.find();
                     String gBefore = StringUtils.defaultString(m.group(1));
                     String gSeparator = StringUtils.defaultString(m.group(2), ", ");
                     String gAfter = StringUtils.defaultString(m.group(3));
                     String gContent = m.group(4);
                     String separator = gBefore;
                     Object[] var17 = (Object[])((Object[])arg);
                     int var18 = var17.length;

                     for(int var19 = 0; var19 < var18; ++var19) {
                        Object o = var17[var19];
                        translated.add(o);
                        replacement.append(separator);
                        replacement.append(gContent);
                        separator = gSeparator;
                     }

                     if (((Object[])((Object[])arg)).length > 0) {
                        replacement.append(gAfter);
                     }

                     string = string.substring(0, start) + replacement + string.substring(end + 2);
                  }
               }

               if (!string.contains("[[")) {
                  this.sb.append(String.format(string, translated.toArray()));
                  return this;
               }

               originals = translated;
               translated = new ArrayList();
               break;
            }
         }
      } else {
         this.sb.append(string);
         return this;
      }
   }

   public W println() {
      if (this.sb.length() > 0) {
         this.sb.append("\n");
         this.newline = true;
      }

      return this;
   }

   public W println(int value) {
      this.print(value);
      this.println();
      return this;
   }

   public W println(String string) {
      this.print(string);
      this.println();
      return this;
   }

   public W println(String string, Object... args) {
      this.print(string, args);
      this.println();
      return this;
   }

   public W tab(int tabs) {
      this.indentTabs = tabs;
      return this;
   }

   public int tab() {
      return this.indentTabs;
   }

   public boolean close() {
      String newContent = this.beforeClose(this.sb.toString());
      if (StringUtils.isBlank(newContent)) {
         return false;
      } else {
         try {
            String oldContent = null;
            if (this.file.exists()) {
               RandomAccessFile old = null;

               try {
                  old = new RandomAccessFile(this.file, "r");
                  byte[] oldBytes = new byte[(int)old.length()];
                  old.readFully(oldBytes);
                  oldContent = new String(oldBytes, this.encoding());
               } finally {
                  if (old != null) {
                     old.close();
                  }

               }
            }

            if (oldContent == null || !oldContent.equals(newContent)) {
               PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.file), this.encoding()));
               writer.append(newContent);
               writer.flush();
               writer.close();
            }

            return true;
         } catch (IOException var9) {
            throw new GeneratorException("Error writing " + this.file.getAbsolutePath(), var9);
         }
      }
   }

   protected String encoding() {
      return this.encoding != null ? this.encoding : "UTF-8";
   }

   protected String beforeClose(String string) {
      return string;
   }

   public String ref(Class<?> clazz) {
      return clazz == null ? null : this.ref(clazz.getName());
   }

   public String ref(String clazzOrId) {
      return clazzOrId == null ? null : (String)this.ref((List)Arrays.asList(clazzOrId), 1).get(0);
   }

   public String[] ref(String[] clazzOrId) {
      return clazzOrId == null ? new String[0] : (String[])this.ref((List)Arrays.asList(clazzOrId), 1).toArray(new String[clazzOrId.length]);
   }

   public List<String> ref(List<String> clazzOrId) {
      return clazzOrId == null ? Collections.emptyList() : this.ref((List)clazzOrId, 1);
   }

   protected String ref(String clazzOrId, int keepSegments) {
      return clazzOrId == null ? null : (String)this.ref(Arrays.asList(clazzOrId), keepSegments).get(0);
   }

   protected String[] ref(String[] clazzOrId, int keepSegments) {
      return clazzOrId == null ? new String[0] : (String[])this.ref(Arrays.asList(clazzOrId), keepSegments).toArray(new String[clazzOrId.length]);
   }

   protected List<String> ref(List<String> clazzOrId, int keepSegments) {
      return clazzOrId == null ? Collections.emptyList() : clazzOrId;
   }

   public String toString() {
      return "GenerationWriter [" + this.file + "]";
   }
}
