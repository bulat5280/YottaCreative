package org.jooq.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jooq.tools.StringUtils;

public class JavaWriter extends GeneratorWriter<JavaWriter> {
   private static final String SERIAL_STATEMENT = "__SERIAL_STATEMENT__";
   private static final String IMPORT_STATEMENT = "__IMPORT_STATEMENT__";
   private final Pattern fullyQualifiedTypes;
   private final Set<String> qualifiedTypes;
   private final Map<String, String> unqualifiedTypes;
   private final String className;
   private final boolean isJava;
   private final boolean isScala;
   private final Pattern REF_PATTERN;
   private final Pattern PLAIN_GENERIC_TYPE_PATTERN;

   public JavaWriter(File file, String fullyQualifiedTypes) {
      this(file, fullyQualifiedTypes, (String)null);
   }

   public JavaWriter(File file, String fullyQualifiedTypes, String encoding) {
      super(file, encoding);
      this.qualifiedTypes = new TreeSet();
      this.unqualifiedTypes = new TreeMap();
      this.REF_PATTERN = Pattern.compile("((?:[\\p{L}_$][\\p{L}\\p{N}_$]*\\.)*[\\p{L}_$][\\p{L}\\p{N}_$]*)((?:<.*>|\\[.*\\])*)");
      this.PLAIN_GENERIC_TYPE_PATTERN = Pattern.compile("[<\\[]((?:[\\p{L}_$][\\p{L}\\p{N}_$]*\\.)*[\\p{L}_$][\\p{L}\\p{N}_$]*)[>\\]]");
      this.className = file.getName().replaceAll("\\.(java|scala)$", "");
      this.isJava = file.getName().endsWith(".java");
      this.isScala = file.getName().endsWith(".scala");
      this.fullyQualifiedTypes = fullyQualifiedTypes == null ? null : Pattern.compile(fullyQualifiedTypes);
      if (this.isJava) {
         this.tabString("    ");
      } else if (this.isScala) {
         this.tabString("  ");
      }

   }

   public JavaWriter print(Class<?> clazz) {
      this.printClass(clazz.getCanonicalName());
      return this;
   }

   public JavaWriter printClass(String clazz) {
      this.print(this.ref(clazz));
      return this;
   }

   public JavaWriter javadoc(String string, Object... args) {
      int t = this.tab();
      String escaped = this.escapeJavadoc(string);
      Object[] escapedArgs = Arrays.copyOf(args, args.length);

      for(int i = 0; i < escapedArgs.length; ++i) {
         if (escapedArgs[i] instanceof String) {
            escapedArgs[i] = this.escapeJavadoc((String)escapedArgs[i]);
         }
      }

      ((JavaWriter)this.tab(t)).println();
      ((JavaWriter)this.tab(t)).println("/**");
      ((JavaWriter)this.tab(t)).println(" * " + escaped, escapedArgs);
      ((JavaWriter)this.tab(t)).println(" */");
      return this;
   }

   private String escapeJavadoc(String string) {
      return string.replace("/*", "/ *").replace("*/", "* /");
   }

   public JavaWriter header(String header, Object... args) {
      int t = this.tab();
      ((JavaWriter)this.tab(t)).println();
      ((JavaWriter)this.tab(t)).println("// -------------------------------------------------------------------------");
      ((JavaWriter)this.tab(t)).println("// " + header, args);
      ((JavaWriter)this.tab(t)).println("// -------------------------------------------------------------------------");
      return this;
   }

   public JavaWriter override() {
      this.println("@Override");
      return this;
   }

   public JavaWriter overrideIf(boolean override) {
      if (override) {
         this.println("@Override");
      }

      return this;
   }

   public JavaWriter overrideInherit() {
      int t = this.tab();
      ((JavaWriter)this.tab(t)).javadoc("{@inheritDoc}");
      ((JavaWriter)this.tab(t)).override();
      return this;
   }

   public JavaWriter overrideInheritIf(boolean override) {
      int t = this.tab();
      if (override) {
         ((JavaWriter)this.tab(t)).javadoc("{@inheritDoc}");
         ((JavaWriter)this.tab(t)).override();
      } else {
         ((JavaWriter)this.tab(t)).println();
      }

      return this;
   }

   public void printSerial() {
      if (this.isJava) {
         this.println();
         this.println("\tprivate static final long serialVersionUID = %s;", new Object[]{"__SERIAL_STATEMENT__"});
      }

   }

   public void printImports() {
      this.println("__IMPORT_STATEMENT__");
   }

   protected String beforeClose(String string) {
      StringBuilder importString = new StringBuilder();
      String pkg = "";
      Matcher m = Pattern.compile("(?s:^.*?[\\r\\n]+package\\s+(.*?);?[\\r\\n]+.*?$)").matcher(string);
      if (m.find()) {
         pkg = m.group(1);
      }

      Pattern samePackagePattern = Pattern.compile(pkg + "\\.[^\\.]+");
      String previous = "";
      Iterator var7 = this.qualifiedTypes.iterator();

      while(true) {
         String imp;
         do {
            do {
               do {
                  if (!var7.hasNext()) {
                     string = string.replaceAll("__IMPORT_STATEMENT__", Matcher.quoteReplacement(importString.toString()));
                     string = string.replaceAll("__SERIAL_STATEMENT__", Matcher.quoteReplacement(String.valueOf(string.hashCode())));
                     return string;
                  }

                  imp = (String)var7.next();
               } while(this.isJava && imp.startsWith("java.lang."));
            } while(imp.endsWith("." + this.className));
         } while(pkg.length() > 0 && samePackagePattern.matcher(imp).matches());

         String topLevelPackage = imp.split("\\.")[0];
         if (!topLevelPackage.equals(previous)) {
            importString.append("\n");
         }

         importString.append("import ").append(imp).append(this.isScala ? "\n" : ";\n");
         previous = topLevelPackage;
      }
   }

   protected List<String> ref(List<String> clazz, int keepSegments) {
      List<String> result = new ArrayList();
      String c;
      if (clazz != null) {
         for(Iterator var4 = clazz.iterator(); var4.hasNext(); result.add(c)) {
            c = (String)var4.next();
            if (c.contains(".") && (this.fullyQualifiedTypes == null || !this.fullyQualifiedTypes.matcher(c).matches())) {
               Matcher m = this.REF_PATTERN.matcher(c);
               if (m.find()) {
                  List<String> split = Arrays.asList(m.group(1).split("\\."));
                  String qualifiedType = StringUtils.join(split.subList(0, split.size() - keepSegments + 1).toArray(), ".");
                  String unqualifiedType = (String)split.get(split.size() - keepSegments);
                  String remainder = StringUtils.join(split.subList(split.size() - keepSegments, split.size()).toArray(), ".");
                  if (!this.className.equals(unqualifiedType) && (!this.unqualifiedTypes.containsKey(unqualifiedType) || qualifiedType.equals(this.unqualifiedTypes.get(unqualifiedType)))) {
                     this.unqualifiedTypes.put(unqualifiedType, qualifiedType);
                     this.qualifiedTypes.add(qualifiedType);
                     String generic = m.group(2);
                     c = remainder + (this.PLAIN_GENERIC_TYPE_PATTERN.matcher(generic).matches() ? generic.substring(0, 1) + this.ref(generic.substring(1, generic.length() - 1)) + generic.substring(generic.length() - 1) : generic);
                  }
               }
            }
         }
      }

      return result;
   }
}
