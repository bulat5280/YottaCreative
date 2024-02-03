package org.jooq.impl;

enum ExpressionOperator {
   CONCAT("||"),
   ADD("+"),
   SUBTRACT("-"),
   MULTIPLY("*"),
   DIVIDE("/"),
   MODULO("%"),
   BIT_NOT("~"),
   BIT_AND("&"),
   BIT_OR("|"),
   BIT_XOR("^"),
   BIT_NAND("~&"),
   BIT_NOR("~|"),
   BIT_XNOR("~^"),
   SHL("<<"),
   SHR(">>");

   private final String sql;

   private ExpressionOperator(String sql) {
      this.sql = sql;
   }

   public String toSQL() {
      return this.sql;
   }
}
