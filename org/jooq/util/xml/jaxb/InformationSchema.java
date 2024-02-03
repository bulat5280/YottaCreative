package org.jooq.util.xml.jaxb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "",
   propOrder = {}
)
@XmlRootElement(
   name = "information_schema"
)
public class InformationSchema implements Serializable {
   private static final long serialVersionUID = 354L;
   @XmlElementWrapper(
      name = "schemata"
   )
   @XmlElement(
      name = "schema"
   )
   protected List<Schema> schemata;
   @XmlElementWrapper(
      name = "sequences"
   )
   @XmlElement(
      name = "sequence"
   )
   protected List<Sequence> sequences;
   @XmlElementWrapper(
      name = "tables"
   )
   @XmlElement(
      name = "table"
   )
   protected List<Table> tables;
   @XmlElementWrapper(
      name = "columns"
   )
   @XmlElement(
      name = "column"
   )
   protected List<Column> columns;
   @XmlElementWrapper(
      name = "table_constraints"
   )
   @XmlElement(
      name = "table_constraint"
   )
   protected List<TableConstraint> tableConstraints;
   @XmlElementWrapper(
      name = "key_column_usages"
   )
   @XmlElement(
      name = "key_column_usage"
   )
   protected List<KeyColumnUsage> keyColumnUsages;
   @XmlElementWrapper(
      name = "referential_constraints"
   )
   @XmlElement(
      name = "referential_constraint"
   )
   protected List<ReferentialConstraint> referentialConstraints;

   public List<Schema> getSchemata() {
      if (this.schemata == null) {
         this.schemata = new ArrayList();
      }

      return this.schemata;
   }

   public void setSchemata(List<Schema> schemata) {
      this.schemata = schemata;
   }

   public List<Sequence> getSequences() {
      if (this.sequences == null) {
         this.sequences = new ArrayList();
      }

      return this.sequences;
   }

   public void setSequences(List<Sequence> sequences) {
      this.sequences = sequences;
   }

   public List<Table> getTables() {
      if (this.tables == null) {
         this.tables = new ArrayList();
      }

      return this.tables;
   }

   public void setTables(List<Table> tables) {
      this.tables = tables;
   }

   public List<Column> getColumns() {
      if (this.columns == null) {
         this.columns = new ArrayList();
      }

      return this.columns;
   }

   public void setColumns(List<Column> columns) {
      this.columns = columns;
   }

   public List<TableConstraint> getTableConstraints() {
      if (this.tableConstraints == null) {
         this.tableConstraints = new ArrayList();
      }

      return this.tableConstraints;
   }

   public void setTableConstraints(List<TableConstraint> tableConstraints) {
      this.tableConstraints = tableConstraints;
   }

   public List<KeyColumnUsage> getKeyColumnUsages() {
      if (this.keyColumnUsages == null) {
         this.keyColumnUsages = new ArrayList();
      }

      return this.keyColumnUsages;
   }

   public void setKeyColumnUsages(List<KeyColumnUsage> keyColumnUsages) {
      this.keyColumnUsages = keyColumnUsages;
   }

   public List<ReferentialConstraint> getReferentialConstraints() {
      if (this.referentialConstraints == null) {
         this.referentialConstraints = new ArrayList();
      }

      return this.referentialConstraints;
   }

   public void setReferentialConstraints(List<ReferentialConstraint> referentialConstraints) {
      this.referentialConstraints = referentialConstraints;
   }

   public InformationSchema withSchemata(Schema... values) {
      if (values != null) {
         Schema[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Schema value = var2[var4];
            this.getSchemata().add(value);
         }
      }

      return this;
   }

   public InformationSchema withSchemata(Collection<Schema> values) {
      if (values != null) {
         this.getSchemata().addAll(values);
      }

      return this;
   }

   public InformationSchema withSchemata(List<Schema> schemata) {
      this.setSchemata(schemata);
      return this;
   }

   public InformationSchema withSequences(Sequence... values) {
      if (values != null) {
         Sequence[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Sequence value = var2[var4];
            this.getSequences().add(value);
         }
      }

      return this;
   }

   public InformationSchema withSequences(Collection<Sequence> values) {
      if (values != null) {
         this.getSequences().addAll(values);
      }

      return this;
   }

   public InformationSchema withSequences(List<Sequence> sequences) {
      this.setSequences(sequences);
      return this;
   }

   public InformationSchema withTables(Table... values) {
      if (values != null) {
         Table[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Table value = var2[var4];
            this.getTables().add(value);
         }
      }

      return this;
   }

   public InformationSchema withTables(Collection<Table> values) {
      if (values != null) {
         this.getTables().addAll(values);
      }

      return this;
   }

   public InformationSchema withTables(List<Table> tables) {
      this.setTables(tables);
      return this;
   }

   public InformationSchema withColumns(Column... values) {
      if (values != null) {
         Column[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Column value = var2[var4];
            this.getColumns().add(value);
         }
      }

      return this;
   }

   public InformationSchema withColumns(Collection<Column> values) {
      if (values != null) {
         this.getColumns().addAll(values);
      }

      return this;
   }

   public InformationSchema withColumns(List<Column> columns) {
      this.setColumns(columns);
      return this;
   }

   public InformationSchema withTableConstraints(TableConstraint... values) {
      if (values != null) {
         TableConstraint[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            TableConstraint value = var2[var4];
            this.getTableConstraints().add(value);
         }
      }

      return this;
   }

   public InformationSchema withTableConstraints(Collection<TableConstraint> values) {
      if (values != null) {
         this.getTableConstraints().addAll(values);
      }

      return this;
   }

   public InformationSchema withTableConstraints(List<TableConstraint> tableConstraints) {
      this.setTableConstraints(tableConstraints);
      return this;
   }

   public InformationSchema withKeyColumnUsages(KeyColumnUsage... values) {
      if (values != null) {
         KeyColumnUsage[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            KeyColumnUsage value = var2[var4];
            this.getKeyColumnUsages().add(value);
         }
      }

      return this;
   }

   public InformationSchema withKeyColumnUsages(Collection<KeyColumnUsage> values) {
      if (values != null) {
         this.getKeyColumnUsages().addAll(values);
      }

      return this;
   }

   public InformationSchema withKeyColumnUsages(List<KeyColumnUsage> keyColumnUsages) {
      this.setKeyColumnUsages(keyColumnUsages);
      return this;
   }

   public InformationSchema withReferentialConstraints(ReferentialConstraint... values) {
      if (values != null) {
         ReferentialConstraint[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ReferentialConstraint value = var2[var4];
            this.getReferentialConstraints().add(value);
         }
      }

      return this;
   }

   public InformationSchema withReferentialConstraints(Collection<ReferentialConstraint> values) {
      if (values != null) {
         this.getReferentialConstraints().addAll(values);
      }

      return this;
   }

   public InformationSchema withReferentialConstraints(List<ReferentialConstraint> referentialConstraints) {
      this.setReferentialConstraints(referentialConstraints);
      return this;
   }
}
