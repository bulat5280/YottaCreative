package org.flywaydb.core.internal.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AsciiTable {
   private final List<String> columns;
   private final List<List<String>> rows;
   private final String nullText;
   private final String emptyText;

   public AsciiTable(List<String> columns, List<List<String>> rows, String nullText, String emptyText) {
      this.columns = columns;
      this.rows = rows;
      this.nullText = nullText;
      this.emptyText = emptyText;
   }

   public String render() {
      List<Integer> widths = new ArrayList();
      Iterator var2 = this.columns.iterator();

      while(var2.hasNext()) {
         String column = (String)var2.next();
         widths.add(column.length());
      }

      var2 = this.rows.iterator();

      int i;
      while(var2.hasNext()) {
         List<String> row = (List)var2.next();

         for(i = 0; i < row.size(); ++i) {
            widths.set(i, Math.max((Integer)widths.get(i), this.getValue(row, i).length()));
         }
      }

      StringBuilder ruler = new StringBuilder("+");
      Iterator var11 = widths.iterator();

      while(var11.hasNext()) {
         Integer width = (Integer)var11.next();
         ruler.append("-").append(StringUtils.trimOrPad("", width, '-')).append("-+");
      }

      ruler.append("\n");
      StringBuilder header = new StringBuilder("|");

      for(i = 0; i < widths.size(); ++i) {
         header.append(" ").append(StringUtils.trimOrPad((String)this.columns.get(i), (Integer)widths.get(i), ' ')).append(" |");
      }

      header.append("\n");
      StringBuilder result = new StringBuilder();
      result.append(ruler);
      result.append(header);
      result.append(ruler);
      if (this.rows.isEmpty()) {
         result.append("| ").append(StringUtils.trimOrPad(this.emptyText, header.length() - 5)).append(" |\n");
      } else {
         Iterator var5 = this.rows.iterator();

         while(var5.hasNext()) {
            List<String> row = (List)var5.next();
            StringBuilder r = new StringBuilder("|");

            for(int i = 0; i < widths.size(); ++i) {
               r.append(" ").append(StringUtils.trimOrPad(this.getValue(row, i), (Integer)widths.get(i), ' ')).append(" |");
            }

            r.append("\n");
            result.append(r);
         }
      }

      result.append(ruler);
      return result.toString();
   }

   private String getValue(List<String> row, int i) {
      String value = (String)row.get(i);
      if (value == null) {
         value = this.nullText;
      }

      return value;
   }
}
