package org.flywaydb.core.internal.resolver;

import org.flywaydb.core.api.MigrationType;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.resolver.MigrationExecutor;
import org.flywaydb.core.api.resolver.ResolvedMigration;
import org.flywaydb.core.internal.util.ObjectUtils;

public class ResolvedMigrationImpl implements ResolvedMigration {
   private MigrationVersion version;
   private String description;
   private String script;
   private Integer checksum;
   private MigrationType type;
   private String physicalLocation;
   private MigrationExecutor executor;

   public MigrationVersion getVersion() {
      return this.version;
   }

   public void setVersion(MigrationVersion version) {
      this.version = version;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getScript() {
      return this.script;
   }

   public void setScript(String script) {
      this.script = script;
   }

   public Integer getChecksum() {
      return this.checksum;
   }

   public void setChecksum(Integer checksum) {
      this.checksum = checksum;
   }

   public MigrationType getType() {
      return this.type;
   }

   public void setType(MigrationType type) {
      this.type = type;
   }

   public String getPhysicalLocation() {
      return this.physicalLocation;
   }

   public void setPhysicalLocation(String physicalLocation) {
      this.physicalLocation = physicalLocation;
   }

   public MigrationExecutor getExecutor() {
      return this.executor;
   }

   public void setExecutor(MigrationExecutor executor) {
      this.executor = executor;
   }

   public int compareTo(ResolvedMigrationImpl o) {
      return this.version.compareTo(o.version);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ResolvedMigrationImpl migration = (ResolvedMigrationImpl)o;
         if (this.checksum != null) {
            if (!this.checksum.equals(migration.checksum)) {
               return false;
            }
         } else if (migration.checksum != null) {
            return false;
         }

         label42: {
            if (this.description != null) {
               if (this.description.equals(migration.description)) {
                  break label42;
               }
            } else if (migration.description == null) {
               break label42;
            }

            return false;
         }

         if (this.script != null) {
            if (!this.script.equals(migration.script)) {
               return false;
            }
         } else if (migration.script != null) {
            return false;
         }

         if (this.type != migration.type) {
            return false;
         } else {
            return ObjectUtils.nullSafeEquals(this.version, migration.version);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.version != null ? this.version.hashCode() : 0;
      result = 31 * result + (this.description != null ? this.description.hashCode() : 0);
      result = 31 * result + (this.script != null ? this.script.hashCode() : 0);
      result = 31 * result + (this.checksum != null ? this.checksum.hashCode() : 0);
      result = 31 * result + this.type.hashCode();
      return result;
   }

   public String toString() {
      return "ResolvedMigrationImpl{version=" + this.version + ", description='" + this.description + '\'' + ", script='" + this.script + '\'' + ", checksum=" + this.checksum + ", type=" + this.type + ", physicalLocation='" + this.physicalLocation + '\'' + ", executor=" + this.executor + '}';
   }
}
