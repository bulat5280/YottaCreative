package org.flywaydb.core.internal.info;

import java.util.Date;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationState;
import org.flywaydb.core.api.MigrationType;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.resolver.ResolvedMigration;
import org.flywaydb.core.internal.schemahistory.AppliedMigration;
import org.flywaydb.core.internal.util.AbbreviationUtils;
import org.flywaydb.core.internal.util.ObjectUtils;

public class MigrationInfoImpl implements MigrationInfo {
   private final ResolvedMigration resolvedMigration;
   private final AppliedMigration appliedMigration;
   private final MigrationInfoContext context;
   private final boolean outOfOrder;

   MigrationInfoImpl(ResolvedMigration resolvedMigration, AppliedMigration appliedMigration, MigrationInfoContext context, boolean outOfOrder) {
      this.resolvedMigration = resolvedMigration;
      this.appliedMigration = appliedMigration;
      this.context = context;
      this.outOfOrder = outOfOrder;
   }

   public ResolvedMigration getResolvedMigration() {
      return this.resolvedMigration;
   }

   public AppliedMigration getAppliedMigration() {
      return this.appliedMigration;
   }

   public MigrationType getType() {
      return this.appliedMigration != null ? this.appliedMigration.getType() : this.resolvedMigration.getType();
   }

   public Integer getChecksum() {
      return this.appliedMigration != null ? this.appliedMigration.getChecksum() : this.resolvedMigration.getChecksum();
   }

   public MigrationVersion getVersion() {
      return this.appliedMigration != null ? this.appliedMigration.getVersion() : this.resolvedMigration.getVersion();
   }

   public String getDescription() {
      return this.appliedMigration != null ? this.appliedMigration.getDescription() : this.resolvedMigration.getDescription();
   }

   public String getScript() {
      return this.appliedMigration != null ? this.appliedMigration.getScript() : this.resolvedMigration.getScript();
   }

   public MigrationState getState() {
      if (this.appliedMigration == null) {
         if (this.resolvedMigration.getVersion() != null) {
            if (this.resolvedMigration.getVersion().compareTo(this.context.baseline) < 0) {
               return MigrationState.BELOW_BASELINE;
            }

            if (this.context.target != null && this.resolvedMigration.getVersion().compareTo(this.context.target) > 0) {
               return MigrationState.ABOVE_TARGET;
            }

            if (this.resolvedMigration.getVersion().compareTo(this.context.lastApplied) < 0 && !this.context.outOfOrder) {
               return MigrationState.IGNORED;
            }
         }

         return MigrationState.PENDING;
      } else if (this.resolvedMigration == null) {
         if (MigrationType.SCHEMA == this.appliedMigration.getType()) {
            return MigrationState.SUCCESS;
         } else if (MigrationType.BASELINE == this.appliedMigration.getType()) {
            return MigrationState.BASELINE;
         } else if (this.appliedMigration.getVersion() != null && this.getVersion().compareTo(this.context.lastResolved) >= 0) {
            return this.appliedMigration.isSuccess() ? MigrationState.FUTURE_SUCCESS : MigrationState.FUTURE_FAILED;
         } else {
            return this.appliedMigration.isSuccess() ? MigrationState.MISSING_SUCCESS : MigrationState.MISSING_FAILED;
         }
      } else if (!this.appliedMigration.isSuccess()) {
         return MigrationState.FAILED;
      } else if (this.appliedMigration.getVersion() == null) {
         if (this.appliedMigration.getInstalledRank() == (Integer)this.context.latestRepeatableRuns.get(this.appliedMigration.getDescription())) {
            return ObjectUtils.nullSafeEquals(this.appliedMigration.getChecksum(), this.resolvedMigration.getChecksum()) ? MigrationState.SUCCESS : MigrationState.OUTDATED;
         } else {
            return MigrationState.SUPERSEDED;
         }
      } else {
         return this.outOfOrder ? MigrationState.OUT_OF_ORDER : MigrationState.SUCCESS;
      }
   }

   public Date getInstalledOn() {
      return this.appliedMigration != null ? this.appliedMigration.getInstalledOn() : null;
   }

   public String getInstalledBy() {
      return this.appliedMigration != null ? this.appliedMigration.getInstalledBy() : null;
   }

   public Integer getInstalledRank() {
      return this.appliedMigration != null ? this.appliedMigration.getInstalledRank() : null;
   }

   public Integer getExecutionTime() {
      return this.appliedMigration != null ? this.appliedMigration.getExecutionTime() : null;
   }

   public String validate() {
      if (MigrationState.ABOVE_TARGET.equals(this.getState())) {
         return null;
      } else if (!this.getState().isFailed() || this.context.future && MigrationState.FUTURE_FAILED == this.getState()) {
         if (this.resolvedMigration != null || this.appliedMigration.getType() == MigrationType.SCHEMA || this.appliedMigration.getType() == MigrationType.BASELINE || this.appliedMigration.getVersion() == null || this.context.missing && (MigrationState.MISSING_SUCCESS == this.getState() || MigrationState.MISSING_FAILED == this.getState()) || this.context.future && (MigrationState.FUTURE_SUCCESS == this.getState() || MigrationState.FUTURE_FAILED == this.getState())) {
            if ((this.context.pending || MigrationState.PENDING != this.getState()) && MigrationState.IGNORED != this.getState()) {
               if (!this.context.pending && MigrationState.OUTDATED == this.getState()) {
                  return "Detected outdated resolved repeatable migration that should be re-applied to database: " + this.getDescription();
               } else {
                  if (this.resolvedMigration != null && this.appliedMigration != null) {
                     String migrationIdentifier = this.appliedMigration.getVersion() == null ? this.appliedMigration.getScript() : "version " + this.appliedMigration.getVersion();
                     if (this.getVersion() == null || this.getVersion().compareTo(this.context.baseline) > 0) {
                        if (this.resolvedMigration.getType() != this.appliedMigration.getType()) {
                           return this.createMismatchMessage("type", migrationIdentifier, this.appliedMigration.getType(), this.resolvedMigration.getType());
                        }

                        if ((this.resolvedMigration.getVersion() != null || this.context.pending && MigrationState.OUTDATED != this.getState() && MigrationState.SUPERSEDED != this.getState()) && !ObjectUtils.nullSafeEquals(this.resolvedMigration.getChecksum(), this.appliedMigration.getChecksum())) {
                           return this.createMismatchMessage("checksum", migrationIdentifier, this.appliedMigration.getChecksum(), this.resolvedMigration.getChecksum());
                        }

                        if (!AbbreviationUtils.abbreviateDescription(this.resolvedMigration.getDescription()).equals(this.appliedMigration.getDescription())) {
                           return this.createMismatchMessage("description", migrationIdentifier, this.appliedMigration.getDescription(), this.resolvedMigration.getDescription());
                        }
                     }
                  }

                  return null;
               }
            } else {
               return this.getVersion() != null ? "Detected resolved migration not applied to database: " + this.getVersion() : "Detected resolved repeatable migration not applied to database: " + this.getDescription();
            }
         } else {
            return "Detected applied migration not resolved locally: " + this.getVersion();
         }
      } else {
         return this.getVersion() == null ? "Detected failed repeatable migration: " + this.getDescription() : "Detected failed migration to version " + this.getVersion() + " (" + this.getDescription() + ")";
      }
   }

   private String createMismatchMessage(String mismatch, String migrationIdentifier, Object applied, Object resolved) {
      return String.format("Migration " + mismatch + " mismatch for migration %s\n-> Applied to database : %s\n-> Resolved locally    : %s", migrationIdentifier, applied, resolved);
   }

   public int compareTo(MigrationInfo o) {
      if (this.getInstalledRank() != null && o.getInstalledRank() != null) {
         return this.getInstalledRank() - o.getInstalledRank();
      } else {
         MigrationState state = this.getState();
         MigrationState oState = o.getState();
         if (state == MigrationState.BELOW_BASELINE && oState.isApplied()) {
            return Integer.MIN_VALUE;
         } else if (state.isApplied() && oState == MigrationState.BELOW_BASELINE) {
            return Integer.MAX_VALUE;
         } else if (state == MigrationState.IGNORED && oState.isApplied()) {
            return this.getVersion() != null && o.getVersion() != null ? this.getVersion().compareTo(o.getVersion()) : Integer.MIN_VALUE;
         } else if (state.isApplied() && oState == MigrationState.IGNORED) {
            return this.getVersion() != null && o.getVersion() != null ? this.getVersion().compareTo(o.getVersion()) : Integer.MAX_VALUE;
         } else if (this.getInstalledRank() != null) {
            return Integer.MIN_VALUE;
         } else if (o.getInstalledRank() != null) {
            return Integer.MAX_VALUE;
         } else if (this.getVersion() != null && o.getVersion() != null) {
            int v = this.getVersion().compareTo(o.getVersion());
            return v != 0 ? v : 0;
         } else if (this.getVersion() != null) {
            return Integer.MIN_VALUE;
         } else {
            return o.getVersion() != null ? Integer.MAX_VALUE : this.getDescription().compareTo(o.getDescription());
         }
      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         MigrationInfoImpl that;
         label39: {
            that = (MigrationInfoImpl)o;
            if (this.appliedMigration != null) {
               if (this.appliedMigration.equals(that.appliedMigration)) {
                  break label39;
               }
            } else if (that.appliedMigration == null) {
               break label39;
            }

            return false;
         }

         if (!this.context.equals(that.context)) {
            return false;
         } else {
            boolean var10000;
            label53: {
               if (this.resolvedMigration != null) {
                  if (this.resolvedMigration.equals(that.resolvedMigration)) {
                     break label53;
                  }
               } else if (that.resolvedMigration == null) {
                  break label53;
               }

               var10000 = false;
               return var10000;
            }

            var10000 = true;
            return var10000;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.resolvedMigration != null ? this.resolvedMigration.hashCode() : 0;
      result = 31 * result + (this.appliedMigration != null ? this.appliedMigration.hashCode() : 0);
      result = 31 * result + this.context.hashCode();
      return result;
   }
}
