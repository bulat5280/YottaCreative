package org.jooq.impl;

import java.util.Arrays;
import org.jooq.AlterTableAlterStep;
import org.jooq.AlterTableDropStep;
import org.jooq.AlterTableFinalStep;
import org.jooq.AlterTableRenameColumnToStep;
import org.jooq.AlterTableRenameConstraintToStep;
import org.jooq.AlterTableStep;
import org.jooq.AlterTableUsingIndexStep;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Constraint;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.Table;

final class AlterTableImpl extends AbstractQuery implements AlterTableStep, AlterTableDropStep, AlterTableAlterStep, AlterTableUsingIndexStep, AlterTableRenameColumnToStep, AlterTableRenameConstraintToStep {
   private static final long serialVersionUID = 8904572826501186329L;
   private static final Clause[] CLAUSES;
   private final Table<?> table;
   private final boolean ifExists;
   private Table<?> renameTo;
   private Field<?> renameColumn;
   private Field<?> renameColumnTo;
   private Constraint renameConstraint;
   private Constraint renameConstraintTo;
   private Field<?> addColumn;
   private DataType<?> addColumnType;
   private Constraint addConstraint;
   private Field<?> alterColumn;
   private DataType<?> alterColumnType;
   private Field<?> alterColumnDefault;
   private Field<?> dropColumn;
   private boolean dropColumnCascade;
   private Constraint dropConstraint;

   AlterTableImpl(Configuration configuration, Table<?> table) {
      this(configuration, table, false);
   }

   AlterTableImpl(Configuration configuration, Table<?> table, boolean ifExists) {
      super(configuration);
      this.table = table;
      this.ifExists = ifExists;
   }

   public final AlterTableImpl renameTo(Table<?> newName) {
      this.renameTo = newName;
      return this;
   }

   public final AlterTableImpl renameTo(Name newName) {
      return this.renameTo(DSL.table(newName));
   }

   public final AlterTableImpl renameTo(String newName) {
      return this.renameTo(DSL.name(newName));
   }

   public final AlterTableImpl renameColumn(Field<?> oldName) {
      this.renameColumn = oldName;
      return this;
   }

   public final AlterTableImpl renameColumn(Name oldName) {
      return this.renameColumn(DSL.field(oldName));
   }

   public final AlterTableImpl renameColumn(String oldName) {
      return this.renameColumn(DSL.name(oldName));
   }

   public final AlterTableImpl renameConstraint(Constraint oldName) {
      this.renameConstraint = oldName;
      return this;
   }

   public final AlterTableImpl renameConstraint(Name oldName) {
      return this.renameConstraint((Constraint)DSL.constraint(oldName));
   }

   public final AlterTableImpl renameConstraint(String oldName) {
      return this.renameConstraint(DSL.name(oldName));
   }

   public final AlterTableImpl to(Field<?> newName) {
      if (this.renameColumn != null) {
         this.renameColumnTo = newName;
         return this;
      } else {
         throw new IllegalStateException();
      }
   }

   public final AlterTableImpl to(Constraint newName) {
      if (this.renameConstraint != null) {
         this.renameConstraintTo = newName;
         return this;
      } else {
         throw new IllegalStateException();
      }
   }

   public final AlterTableImpl to(Name newName) {
      if (this.renameColumn != null) {
         return this.to(DSL.field(newName));
      } else if (this.renameConstraint != null) {
         return this.to((Constraint)DSL.constraint(newName));
      } else {
         throw new IllegalStateException();
      }
   }

   public final AlterTableImpl to(String newName) {
      return this.to(DSL.name(newName));
   }

   public final <T> AlterTableImpl add(Field<T> field, DataType<T> type) {
      return this.addColumn(field, type);
   }

   public final AlterTableImpl add(Name field, DataType<?> type) {
      return this.addColumn(field, type);
   }

   public final AlterTableImpl add(String field, DataType<?> type) {
      return this.addColumn(field, type);
   }

   public final AlterTableImpl addColumn(String field, DataType<?> type) {
      return this.addColumn(DSL.name(field), type);
   }

   public final AlterTableImpl addColumn(Name field, DataType<?> type) {
      return this.addColumn(DSL.field(field, type), type);
   }

   public final <T> AlterTableImpl addColumn(Field<T> field, DataType<T> type) {
      this.addColumn = field;
      this.addColumnType = type;
      return this;
   }

   public final AlterTableImpl add(Constraint constraint) {
      this.addConstraint = constraint;
      return this;
   }

   public final <T> AlterTableImpl alter(Field<T> field) {
      return this.alterColumn(field);
   }

   public final AlterTableImpl alter(Name field) {
      return this.alterColumn(field);
   }

   public final AlterTableImpl alter(String field) {
      return this.alterColumn(field);
   }

   public final AlterTableImpl alterColumn(Name field) {
      return this.alterColumn(DSL.field(field));
   }

   public final AlterTableImpl alterColumn(String field) {
      return this.alterColumn(DSL.name(field));
   }

   public final <T> AlterTableImpl alterColumn(Field<T> field) {
      this.alterColumn = field;
      return this;
   }

   public final AlterTableImpl set(DataType type) {
      this.alterColumnType = type;
      return this;
   }

   public final AlterTableImpl defaultValue(Object literal) {
      return this.defaultValue(Tools.field(literal));
   }

   public final AlterTableImpl defaultValue(Field expression) {
      this.alterColumnDefault = expression;
      return this;
   }

   public final AlterTableImpl drop(Field<?> field) {
      return this.dropColumn(field);
   }

   public final AlterTableImpl drop(Name field) {
      return this.dropColumn(field);
   }

   public final AlterTableImpl drop(String field) {
      return this.dropColumn(field);
   }

   public final AlterTableImpl dropColumn(Name field) {
      return this.dropColumn(DSL.field(field));
   }

   public final AlterTableImpl dropColumn(String field) {
      return this.dropColumn(DSL.name(field));
   }

   public final AlterTableImpl dropColumn(Field<?> field) {
      this.dropColumn = field;
      return this;
   }

   public final AlterTableImpl drop(Constraint constraint) {
      this.dropConstraint = constraint;
      return this;
   }

   public final AlterTableImpl dropConstraint(Name constraint) {
      return this.drop((Constraint)DSL.constraint(constraint));
   }

   public final AlterTableImpl dropConstraint(String constraint) {
      return this.drop((Constraint)DSL.constraint(constraint));
   }

   public final AlterTableFinalStep cascade() {
      this.dropColumnCascade = true;
      return this;
   }

   public final AlterTableFinalStep restrict() {
      this.dropColumnCascade = false;
      return this;
   }

   public final void accept(Context<?> ctx) {
      SQLDialect family = ctx.configuration().dialect().family();
      this.accept0(ctx);
   }

   private final void accept0(Context<?> ctx) {
      SQLDialect family = ctx.family();
      boolean omitAlterTable = family == SQLDialect.HSQLDB && this.renameConstraint != null;
      if (!omitAlterTable) {
         ctx.start(Clause.ALTER_TABLE_TABLE).keyword("alter table");
         if (this.ifExists) {
            ctx.sql(' ').keyword("if exists");
         }

         ctx.sql(' ').visit(this.table).end(Clause.ALTER_TABLE_TABLE).formatIndentStart().formatSeparator();
      }

      boolean qualify;
      if (this.renameTo != null) {
         qualify = ctx.qualify();
         ctx.start(Clause.ALTER_TABLE_RENAME).qualify(false).keyword("rename to").sql(' ').visit(this.renameTo).qualify(qualify).end(Clause.ALTER_TABLE_RENAME);
      } else if (this.renameColumn != null) {
         qualify = ctx.qualify();
         ctx.start(Clause.ALTER_TABLE_RENAME_COLUMN).qualify(false);
         switch(ctx.family()) {
         case H2:
         case HSQLDB:
            ctx.keyword("alter column").sql(' ').visit(this.renameColumn).formatSeparator().keyword("rename to").sql(' ').visit(this.renameColumnTo);
            break;
         default:
            ctx.keyword("rename column").sql(' ').visit(this.renameColumn).formatSeparator().keyword("to").sql(' ').visit(this.renameColumnTo);
         }

         ctx.qualify(qualify).end(Clause.ALTER_TABLE_RENAME_COLUMN);
      } else if (this.renameConstraint != null) {
         qualify = ctx.qualify();
         ctx.start(Clause.ALTER_TABLE_RENAME_CONSTRAINT);
         ctx.data(Tools.DataKey.DATA_CONSTRAINT_REFERENCE, true);
         if (family == SQLDialect.HSQLDB) {
            ctx.qualify(false).keyword("alter constraint").sql(' ').visit(this.renameConstraint).formatSeparator().keyword("rename to").sql(' ').visit(this.renameConstraintTo).qualify(qualify);
         } else {
            ctx.qualify(false).keyword("rename constraint").sql(' ').visit(this.renameConstraint).formatSeparator().keyword("to").sql(' ').visit(this.renameConstraintTo).qualify(qualify);
         }

         ctx.data().remove(Tools.DataKey.DATA_CONSTRAINT_REFERENCE);
         ctx.end(Clause.ALTER_TABLE_RENAME_CONSTRAINT);
      } else if (this.addColumn != null) {
         qualify = ctx.qualify();
         ctx.start(Clause.ALTER_TABLE_ADD).keyword("add").sql(' ');
         ctx.qualify(false).visit(this.addColumn).sql(' ').qualify(qualify);
         Tools.toSQLDDLTypeDeclaration(ctx, this.addColumnType);
         if (!this.addColumnType.nullable()) {
            ctx.sql(' ').keyword("not null");
         } else if (!Arrays.asList(SQLDialect.DERBY, SQLDialect.FIREBIRD).contains(family)) {
            ctx.sql(' ').keyword("null");
         }

         ctx.end(Clause.ALTER_TABLE_ADD);
      } else if (this.addConstraint != null) {
         qualify = ctx.qualify();
         ctx.start(Clause.ALTER_TABLE_ADD);
         ctx.keyword("add").sql(' ').qualify(false).visit(this.addConstraint).qualify(qualify);
         ctx.end(Clause.ALTER_TABLE_ADD);
      } else if (this.alterColumn != null) {
         ctx.start(Clause.ALTER_TABLE_ALTER);
         switch(family) {
         case CUBRID:
         case MARIADB:
         case MYSQL:
            if (this.alterColumnDefault == null) {
               ctx.keyword("change column").sql(' ').qualify(false).visit(this.alterColumn).qualify(true);
            } else {
               ctx.keyword("alter column");
            }
            break;
         default:
            ctx.keyword("alter");
         }

         ctx.sql(' ').qualify(false).visit(this.alterColumn).qualify(true);
         if (this.alterColumnType != null) {
            switch(family) {
            case DERBY:
               ctx.sql(' ').keyword("set data type");
               break;
            case POSTGRES:
               ctx.sql(' ').keyword("type");
            }

            ctx.sql(' ');
            Tools.toSQLDDLTypeDeclaration(ctx, this.alterColumnType);
            if (!this.alterColumnType.nullable()) {
               ctx.sql(' ').keyword("not null");
            }
         } else if (this.alterColumnDefault != null) {
            ctx.start(Clause.ALTER_TABLE_ALTER_DEFAULT);
            switch(family) {
            default:
               ctx.keyword("set default");
               ctx.sql(' ').visit(this.alterColumnDefault).end(Clause.ALTER_TABLE_ALTER_DEFAULT);
            }
         }

         ctx.end(Clause.ALTER_TABLE_ALTER);
      } else if (this.dropColumn != null) {
         ctx.start(Clause.ALTER_TABLE_DROP);
         switch(family) {
         default:
            ctx.keyword("drop");
            ctx.sql(' ').qualify(false).visit(this.dropColumn).qualify(true);
            switch(family) {
            default:
               if (this.dropColumnCascade) {
                  ctx.sql(' ').keyword("cascade");
               }

               ctx.end(Clause.ALTER_TABLE_DROP);
            }
         }
      } else if (this.dropConstraint != null) {
         ctx.start(Clause.ALTER_TABLE_DROP);
         ctx.data(Tools.DataKey.DATA_CONSTRAINT_REFERENCE, true);
         ctx.keyword("drop constraint").sql(' ').visit(this.dropConstraint);
         ctx.data().remove(Tools.DataKey.DATA_CONSTRAINT_REFERENCE);
         ctx.end(Clause.ALTER_TABLE_DROP);
      }

      if (!omitAlterTable) {
         ctx.formatIndentEnd();
      }

   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   static {
      CLAUSES = new Clause[]{Clause.ALTER_TABLE};
   }
}
