package org.jooq;

public interface AlterTableStep {
   @Support
   AlterTableFinalStep renameTo(Table<?> var1);

   @Support
   AlterTableFinalStep renameTo(Name var1);

   @Support
   AlterTableFinalStep renameTo(String var1);

   @Support
   AlterTableRenameColumnToStep renameColumn(Field<?> var1);

   @Support
   AlterTableRenameColumnToStep renameColumn(Name var1);

   @Support
   AlterTableRenameColumnToStep renameColumn(String var1);

   @Support
   AlterTableRenameConstraintToStep renameConstraint(Constraint var1);

   @Support
   AlterTableRenameConstraintToStep renameConstraint(Name var1);

   @Support
   AlterTableRenameConstraintToStep renameConstraint(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   <T> AlterTableAlterStep<T> alter(Field<T> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   AlterTableAlterStep<Object> alter(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   AlterTableAlterStep<Object> alter(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   <T> AlterTableAlterStep<T> alterColumn(Field<T> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   AlterTableAlterStep<Object> alterColumn(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   AlterTableAlterStep<Object> alterColumn(String var1);

   @Support
   <T> AlterTableFinalStep add(Field<T> var1, DataType<T> var2);

   @Support
   AlterTableFinalStep add(Name var1, DataType<?> var2);

   @Support
   AlterTableFinalStep add(String var1, DataType<?> var2);

   @Support
   <T> AlterTableFinalStep addColumn(Field<T> var1, DataType<T> var2);

   @Support
   AlterTableFinalStep addColumn(Name var1, DataType<?> var2);

   @Support
   AlterTableFinalStep addColumn(String var1, DataType<?> var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   AlterTableUsingIndexStep add(Constraint var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   AlterTableDropStep drop(Field<?> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   AlterTableDropStep drop(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   AlterTableDropStep drop(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   AlterTableDropStep dropColumn(Field<?> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   AlterTableDropStep dropColumn(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   AlterTableDropStep dropColumn(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   AlterTableFinalStep drop(Constraint var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   AlterTableFinalStep dropConstraint(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   AlterTableFinalStep dropConstraint(String var1);
}
