package org.jooq.util.jaxb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "Matchers",
   propOrder = {"schemas", "tables", "fields", "routines", "sequences"}
)
public class Matchers implements Serializable {
   private static final long serialVersionUID = 390L;
   @XmlElementWrapper(
      name = "schemas"
   )
   @XmlElement(
      name = "schema"
   )
   protected List<MatchersSchemaType> schemas;
   @XmlElementWrapper(
      name = "tables"
   )
   @XmlElement(
      name = "table"
   )
   protected List<MatchersTableType> tables;
   @XmlElementWrapper(
      name = "fields"
   )
   @XmlElement(
      name = "field"
   )
   protected List<MatchersFieldType> fields;
   @XmlElementWrapper(
      name = "routines"
   )
   @XmlElement(
      name = "routine"
   )
   protected List<MatchersRoutineType> routines;
   @XmlElementWrapper(
      name = "sequences"
   )
   @XmlElement(
      name = "sequence"
   )
   protected List<MatchersSequenceType> sequences;

   public List<MatchersSchemaType> getSchemas() {
      if (this.schemas == null) {
         this.schemas = new ArrayList();
      }

      return this.schemas;
   }

   public void setSchemas(List<MatchersSchemaType> schemas) {
      this.schemas = schemas;
   }

   public List<MatchersTableType> getTables() {
      if (this.tables == null) {
         this.tables = new ArrayList();
      }

      return this.tables;
   }

   public void setTables(List<MatchersTableType> tables) {
      this.tables = tables;
   }

   public List<MatchersFieldType> getFields() {
      if (this.fields == null) {
         this.fields = new ArrayList();
      }

      return this.fields;
   }

   public void setFields(List<MatchersFieldType> fields) {
      this.fields = fields;
   }

   public List<MatchersRoutineType> getRoutines() {
      if (this.routines == null) {
         this.routines = new ArrayList();
      }

      return this.routines;
   }

   public void setRoutines(List<MatchersRoutineType> routines) {
      this.routines = routines;
   }

   public List<MatchersSequenceType> getSequences() {
      if (this.sequences == null) {
         this.sequences = new ArrayList();
      }

      return this.sequences;
   }

   public void setSequences(List<MatchersSequenceType> sequences) {
      this.sequences = sequences;
   }

   public Matchers withSchemas(MatchersSchemaType... values) {
      if (values != null) {
         MatchersSchemaType[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            MatchersSchemaType value = var2[var4];
            this.getSchemas().add(value);
         }
      }

      return this;
   }

   public Matchers withSchemas(Collection<MatchersSchemaType> values) {
      if (values != null) {
         this.getSchemas().addAll(values);
      }

      return this;
   }

   public Matchers withSchemas(List<MatchersSchemaType> schemas) {
      this.setSchemas(schemas);
      return this;
   }

   public Matchers withTables(MatchersTableType... values) {
      if (values != null) {
         MatchersTableType[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            MatchersTableType value = var2[var4];
            this.getTables().add(value);
         }
      }

      return this;
   }

   public Matchers withTables(Collection<MatchersTableType> values) {
      if (values != null) {
         this.getTables().addAll(values);
      }

      return this;
   }

   public Matchers withTables(List<MatchersTableType> tables) {
      this.setTables(tables);
      return this;
   }

   public Matchers withFields(MatchersFieldType... values) {
      if (values != null) {
         MatchersFieldType[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            MatchersFieldType value = var2[var4];
            this.getFields().add(value);
         }
      }

      return this;
   }

   public Matchers withFields(Collection<MatchersFieldType> values) {
      if (values != null) {
         this.getFields().addAll(values);
      }

      return this;
   }

   public Matchers withFields(List<MatchersFieldType> fields) {
      this.setFields(fields);
      return this;
   }

   public Matchers withRoutines(MatchersRoutineType... values) {
      if (values != null) {
         MatchersRoutineType[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            MatchersRoutineType value = var2[var4];
            this.getRoutines().add(value);
         }
      }

      return this;
   }

   public Matchers withRoutines(Collection<MatchersRoutineType> values) {
      if (values != null) {
         this.getRoutines().addAll(values);
      }

      return this;
   }

   public Matchers withRoutines(List<MatchersRoutineType> routines) {
      this.setRoutines(routines);
      return this;
   }

   public Matchers withSequences(MatchersSequenceType... values) {
      if (values != null) {
         MatchersSequenceType[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            MatchersSequenceType value = var2[var4];
            this.getSequences().add(value);
         }
      }

      return this;
   }

   public Matchers withSequences(Collection<MatchersSequenceType> values) {
      if (values != null) {
         this.getSequences().addAll(values);
      }

      return this;
   }

   public Matchers withSequences(List<MatchersSequenceType> sequences) {
      this.setSequences(sequences);
      return this;
   }
}
