package org.jooq.impl;

import java.sql.PreparedStatement;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import org.jooq.BindContext;
import org.jooq.Configuration;
import org.jooq.Constants;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.QueryPart;
import org.jooq.QueryPartInternal;
import org.jooq.RenderContext;
import org.jooq.SQLDialect;
import org.jooq.conf.ParamType;
import org.jooq.conf.RenderKeywordStyle;
import org.jooq.conf.RenderNameStyle;
import org.jooq.conf.Settings;
import org.jooq.exception.ControlFlowSignal;
import org.jooq.exception.DataAccessException;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;

class DefaultRenderContext extends AbstractContext<RenderContext> implements RenderContext {
   private static final JooqLogger log = JooqLogger.getLogger(DefaultRenderContext.class);
   private static final Pattern IDENTIFIER_PATTERN = Pattern.compile("[A-Za-z][A-Za-z0-9_]*");
   private static final Pattern NEWLINE = Pattern.compile("[\\n\\r]");
   private static final Set<String> SQLITE_KEYWORDS = new HashSet();
   private final StringBuilder sql;
   private final QueryPartList<Param<?>> bindValues;
   private int params;
   private int alias;
   private int indent;
   private Deque<Integer> indentLock;
   private int printMargin;
   private boolean separator;
   private boolean newline;
   RenderKeywordStyle cachedRenderKeywordStyle;
   RenderNameStyle cachedRenderNameStyle;
   boolean cachedRenderFormatted;

   DefaultRenderContext(Configuration configuration) {
      super(configuration, (PreparedStatement)null);
      this.printMargin = 80;
      Settings settings = configuration.settings();
      this.sql = new StringBuilder();
      this.bindValues = new QueryPartList();
      this.cachedRenderKeywordStyle = settings.getRenderKeywordStyle();
      this.cachedRenderFormatted = Boolean.TRUE.equals(settings.isRenderFormatted());
      this.cachedRenderNameStyle = settings.getRenderNameStyle();
   }

   DefaultRenderContext(RenderContext context) {
      this(context.configuration());
      this.paramType(context.paramType());
      this.qualifyCatalog(context.qualifyCatalog());
      this.qualifySchema(context.qualifySchema());
      this.castMode(context.castMode());
      this.data().putAll(context.data());
      this.declareCTE = context.declareCTE();
      this.declareWindows = context.declareWindows();
      this.declareFields = context.declareFields();
      this.declareTables = context.declareTables();
      this.declareAliases = context.declareAliases();
   }

   public final BindContext bindValue(Object value, Field<?> field) throws DataAccessException {
      throw new UnsupportedOperationException();
   }

   final QueryPartList<Param<?>> bindValues() {
      return this.bindValues;
   }

   public final String peekAlias() {
      return "alias_" + (this.alias + 1);
   }

   public final String nextAlias() {
      return "alias_" + ++this.alias;
   }

   public final String render() {
      return this.sql.toString();
   }

   public final String render(QueryPart part) {
      return ((RenderContext)(new DefaultRenderContext(this)).visit(part)).render();
   }

   public final RenderContext keyword(String keyword) {
      if (RenderKeywordStyle.UPPER == this.cachedRenderKeywordStyle) {
         return this.sql(keyword.toUpperCase(), true);
      } else {
         return RenderKeywordStyle.LOWER == this.cachedRenderKeywordStyle ? this.sql(keyword.toLowerCase(), true) : this.sql(keyword, true);
      }
   }

   public final RenderContext sql(String s) {
      return this.sql(s, s == null || !this.cachedRenderFormatted);
   }

   public final RenderContext sql(String s, boolean literal) {
      if (!literal) {
         s = NEWLINE.matcher(s).replaceAll("$0" + this.indentation());
      }

      if (this.stringLiteral()) {
         s = StringUtils.replace(s, "'", this.stringLiteralEscapedApos);
      }

      this.sql.append(s);
      this.separator = false;
      this.newline = false;
      return this;
   }

   public final RenderContext sql(char c) {
      this.sql.append(c);
      if (c == '\'' && this.stringLiteral()) {
         this.sql.append(c);
      }

      this.separator = false;
      this.newline = false;
      return this;
   }

   public final RenderContext sql(int i) {
      this.sql.append(i);
      this.separator = false;
      this.newline = false;
      return this;
   }

   public final RenderContext formatNewLine() {
      if (this.cachedRenderFormatted) {
         this.sql("\n", true);
         this.sql(this.indentation(), true);
         this.newline = true;
      }

      return this;
   }

   public final RenderContext formatNewLineAfterPrintMargin() {
      if (this.cachedRenderFormatted && this.printMargin > 0 && this.sql.length() - this.sql.lastIndexOf("\n") > this.printMargin) {
         this.formatNewLine();
      }

      return this;
   }

   private final String indentation() {
      return StringUtils.leftPad("", this.indent, " ");
   }

   public final RenderContext format(boolean format) {
      this.cachedRenderFormatted = format;
      return this;
   }

   public final boolean format() {
      return this.cachedRenderFormatted;
   }

   public final RenderContext formatSeparator() {
      if (!this.separator && !this.newline) {
         if (this.cachedRenderFormatted) {
            this.formatNewLine();
         } else {
            this.sql(" ", true);
         }

         this.separator = true;
      }

      return this;
   }

   public final RenderContext formatIndentStart() {
      return this.formatIndentStart(2);
   }

   public final RenderContext formatIndentEnd() {
      return this.formatIndentEnd(2);
   }

   public final RenderContext formatIndentStart(int i) {
      if (this.cachedRenderFormatted) {
         this.indent += i;
      }

      return this;
   }

   public final RenderContext formatIndentEnd(int i) {
      if (this.cachedRenderFormatted) {
         this.indent -= i;
      }

      return this;
   }

   private final Deque<Integer> indentLock() {
      if (this.indentLock == null) {
         this.indentLock = new ArrayDeque();
      }

      return this.indentLock;
   }

   public final RenderContext formatIndentLockStart() {
      if (this.cachedRenderFormatted) {
         this.indentLock().push(this.indent);
         String[] lines = this.sql.toString().split("[\\n\\r]");
         this.indent = lines[lines.length - 1].length();
      }

      return this;
   }

   public final RenderContext formatIndentLockEnd() {
      if (this.cachedRenderFormatted) {
         this.indent = (Integer)this.indentLock().pop();
      }

      return this;
   }

   public final RenderContext formatPrintMargin(int margin) {
      this.printMargin = margin;
      return this;
   }

   public final RenderContext literal(String literal) {
      if (literal == null) {
         return this;
      } else {
         SQLDialect family = this.family();
         boolean needsQuote = family != SQLDialect.SQLITE && RenderNameStyle.QUOTED == this.cachedRenderNameStyle || family == SQLDialect.SQLITE && SQLITE_KEYWORDS.contains(literal.toUpperCase()) || family == SQLDialect.SQLITE && !IDENTIFIER_PATTERN.matcher(literal).matches();
         if (!needsQuote) {
            if (RenderNameStyle.LOWER == this.cachedRenderNameStyle) {
               literal = literal.toLowerCase();
            } else if (RenderNameStyle.UPPER == this.cachedRenderNameStyle) {
               literal = literal.toUpperCase();
            }

            this.sql(literal, true);
         } else {
            String[][] quotes = (String[][])Identifiers.QUOTES.get(family);
            char start = quotes[0][0].charAt(0);
            char end = quotes[1][0].charAt(0);
            this.sql(start);
            if (literal.indexOf(end) > -1) {
               this.sql(StringUtils.replace(literal, quotes[1][0], quotes[2][0]), true);
            } else {
               this.sql(literal, true);
            }

            this.sql(end);
         }

         return this;
      }
   }

   /** @deprecated */
   @Deprecated
   public final RenderContext sql(QueryPart part) {
      return (RenderContext)this.visit(part);
   }

   protected final void visit0(QueryPartInternal internal) {
      int before = this.bindValues.size();
      internal.accept(this);
      int after = this.bindValues.size();
      if (after == before && this.paramType != ParamType.INLINED && internal instanceof Param) {
         Param<?> param = (Param)internal;
         if (!param.isInline()) {
            this.bindValues.add((QueryPart)param);
            switch(this.family()) {
            case POSTGRES:
               this.checkForceInline(32767);
               break;
            case SQLITE:
               this.checkForceInline(999);
            }
         }
      }

   }

   private final void checkForceInline(int max) throws DefaultRenderContext.ForceInlineSignal {
      if (this.bindValues.size() > max && Boolean.TRUE.equals(this.data(Tools.DataKey.DATA_COUNT_BIND_VALUES))) {
         throw new DefaultRenderContext.ForceInlineSignal();
      }
   }

   /** @deprecated */
   @Deprecated
   public final boolean inline() {
      return this.paramType == ParamType.INLINED;
   }

   /** @deprecated */
   @Deprecated
   public final boolean namedParams() {
      return this.paramType == ParamType.NAMED;
   }

   /** @deprecated */
   @Deprecated
   public final RenderContext inline(boolean i) {
      this.paramType = i ? ParamType.INLINED : ParamType.INDEXED;
      return this;
   }

   /** @deprecated */
   @Deprecated
   public final RenderContext namedParams(boolean r) {
      this.paramType = r ? ParamType.NAMED : ParamType.INDEXED;
      return this;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("rendering    [");
      sb.append(this.render());
      sb.append("]\n");
      sb.append("parameters   [");
      sb.append(this.paramType);
      sb.append("]\n");
      this.toString(sb);
      return sb.toString();
   }

   static {
      SQLITE_KEYWORDS.addAll(Arrays.asList("ABORT", "ACTION", "ADD", "AFTER", "ALL", "ALTER", "ANALYZE", "AND", "AS", "ASC", "ATTACH", "AUTOINCREMENT", "BEFORE", "BEGIN", "BETWEEN", "BY", "CASCADE", "CASE", "CAST", "CHECK", "COLLATE", "COLUMN", "COMMIT", "CONFLICT", "CONSTRAINT", "CREATE", "CROSS", "CURRENT_DATE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "DATABASE", "DEFAULT", "DEFERRABLE", "DEFERRED", "DELETE", "DESC", "DETACH", "DISTINCT", "DROP", "EACH", "ELSE", "END", "ESCAPE", "EXCEPT", "EXCLUSIVE", "EXISTS", "EXPLAIN", "FAIL", "FOR", "FOREIGN", "FROM", "FULL", "GLOB", "GROUP", "HAVING", "IF", "IGNORE", "IMMEDIATE", "IN", "INDEX", "INDEXED", "INITIALLY", "INNER", "INSERT", "INSTEAD", "INTERSECT", "INTO", "IS", "ISNULL", "JOIN", "KEY", "LEFT", "LIKE", "LIMIT", "MATCH", "NATURAL", "NO", "NOT", "NOTNULL", "NULL", "OF", "OFFSET", "ON", "OR", "ORDER", "OUTER", "PLAN", "PRAGMA", "PRIMARY", "QUERY", "RAISE", "REFERENCES", "REGEXP", "REINDEX", "RELEASE", "RENAME", "REPLACE", "RESTRICT", "RIGHT", "ROLLBACK", "ROW", "SAVEPOINT", "SELECT", "SET", "TABLE", "TEMP", "TEMPORARY", "THEN", "TO", "TRANSACTION", "TRIGGER", "UNION", "UNIQUE", "UPDATE", "USING", "VACUUM", "VALUES", "VIEW", "VIRTUAL", "WHEN", "WHERE"));
      if (!Boolean.getBoolean("org.jooq.no-logo")) {
         JooqLogger l = JooqLogger.getLogger(Constants.class);
         String message = "Thank you for using jOOQ 3.9.1";
         l.info("\n                                      \n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n@@@@@@@@@@@@@@@@  @@        @@@@@@@@@@\n@@@@@@@@@@@@@@@@@@@@        @@@@@@@@@@\n@@@@@@@@@@@@@@@@  @@  @@    @@@@@@@@@@\n@@@@@@@@@@  @@@@  @@  @@    @@@@@@@@@@\n@@@@@@@@@@        @@        @@@@@@@@@@\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n@@@@@@@@@@        @@        @@@@@@@@@@\n@@@@@@@@@@    @@  @@  @@@@  @@@@@@@@@@\n@@@@@@@@@@    @@  @@  @@@@  @@@@@@@@@@\n@@@@@@@@@@        @@  @  @  @@@@@@@@@@\n@@@@@@@@@@        @@        @@@@@@@@@@\n@@@@@@@@@@@@@@@@@@@@@@@  @@@@@@@@@@@@@\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  " + message + "\n                                      ");
      }

   }

   class ForceInlineSignal extends ControlFlowSignal {
      private static final long serialVersionUID = -9131368742983295195L;

      public ForceInlineSignal() {
         if (DefaultRenderContext.log.isDebugEnabled()) {
            DefaultRenderContext.log.debug("Re-render query", (Object)("Forcing bind variable inlining as " + DefaultRenderContext.this.configuration().dialect() + " does not support " + DefaultRenderContext.this.params + " bind variables (or more) in a single query"));
         }

      }
   }
}
