package org.flywaydb.core.internal.info;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;
import org.flywaydb.core.api.MigrationState;
import org.flywaydb.core.api.MigrationType;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.resolver.MigrationResolver;
import org.flywaydb.core.api.resolver.ResolvedMigration;
import org.flywaydb.core.internal.schemahistory.AppliedMigration;
import org.flywaydb.core.internal.schemahistory.SchemaHistory;
import org.flywaydb.core.internal.util.ObjectUtils;
import org.flywaydb.core.internal.util.Pair;

public class MigrationInfoServiceImpl implements MigrationInfoService {
   private final MigrationResolver migrationResolver;
   private final SchemaHistory schemaHistory;
   private MigrationVersion target;
   private boolean outOfOrder;
   private final boolean pending;
   private final boolean missing;
   private final boolean future;
   private List<MigrationInfoImpl> migrationInfos;

   public MigrationInfoServiceImpl(MigrationResolver migrationResolver, SchemaHistory schemaHistory, MigrationVersion target, boolean outOfOrder, boolean pending, boolean missing, boolean future) {
      this.migrationResolver = migrationResolver;
      this.schemaHistory = schemaHistory;
      this.target = target;
      this.outOfOrder = outOfOrder;
      this.pending = pending;
      this.missing = missing;
      this.future = future;
   }

   public void refresh() {
      Collection<ResolvedMigration> resolvedMigrations = this.migrationResolver.resolveMigrations();
      List<AppliedMigration> appliedMigrations = this.schemaHistory.allAppliedMigrations();
      MigrationInfoContext context = new MigrationInfoContext();
      context.outOfOrder = this.outOfOrder;
      context.pending = this.pending;
      context.missing = this.missing;
      context.future = this.future;
      context.target = this.target;
      Map<Pair<MigrationVersion, Boolean>, ResolvedMigration> resolvedVersioned = new TreeMap();
      Map<String, ResolvedMigration> resolvedRepeatable = new TreeMap();
      Iterator var6 = resolvedMigrations.iterator();

      while(var6.hasNext()) {
         ResolvedMigration resolvedMigration = (ResolvedMigration)var6.next();
         MigrationVersion version = resolvedMigration.getVersion();
         if (version != null) {
            if (version.compareTo(context.lastResolved) > 0) {
               context.lastResolved = version;
            }

            resolvedVersioned.put(Pair.of(version, false), resolvedMigration);
         } else {
            resolvedRepeatable.put(resolvedMigration.getDescription(), resolvedMigration);
         }
      }

      List<Pair<AppliedMigration, AppliedMigrationAttributes>> appliedVersioned = new ArrayList();
      List<AppliedMigration> appliedRepeatable = new ArrayList();
      Iterator var18 = appliedMigrations.iterator();

      MigrationVersion version;
      while(var18.hasNext()) {
         AppliedMigration appliedMigration = (AppliedMigration)var18.next();
         version = appliedMigration.getVersion();
         if (version == null) {
            appliedRepeatable.add(appliedMigration);
         } else {
            if (appliedMigration.getType() == MigrationType.SCHEMA) {
               context.schema = version;
            }

            if (appliedMigration.getType() == MigrationType.BASELINE) {
               context.baseline = version;
            }

            appliedVersioned.add(Pair.of(appliedMigration, new AppliedMigrationAttributes()));
         }
      }

      var18 = appliedVersioned.iterator();

      while(var18.hasNext()) {
         Pair<AppliedMigration, AppliedMigrationAttributes> av = (Pair)var18.next();
         version = ((AppliedMigration)av.getLeft()).getVersion();
         if (version != null) {
            if (version.compareTo(context.lastApplied) > 0) {
               context.lastApplied = version;
            } else {
               ((AppliedMigrationAttributes)av.getRight()).outOfOrder = true;
            }
         }
      }

      if (MigrationVersion.CURRENT == this.target) {
         context.target = context.lastApplied;
      }

      Set<Pair<MigrationVersion, Boolean>> allVersions = new HashSet(resolvedVersioned.keySet());
      Iterator var21 = appliedVersioned.iterator();

      while(var21.hasNext()) {
         Pair<AppliedMigration, AppliedMigrationAttributes> av = (Pair)var21.next();
         allVersions.add(Pair.of(((AppliedMigration)av.getLeft()).getVersion(), false));
      }

      List<MigrationInfoImpl> migrationInfos1 = new ArrayList();
      Set<ResolvedMigration> pendingResolvedVersioned = new HashSet(resolvedVersioned.values());

      Iterator var11;
      Pair av;
      ResolvedMigration prr;
      for(var11 = appliedVersioned.iterator(); var11.hasNext(); migrationInfos1.add(new MigrationInfoImpl(prr, (AppliedMigration)av.getLeft(), context, ((AppliedMigrationAttributes)av.getRight()).outOfOrder))) {
         av = (Pair)var11.next();
         prr = (ResolvedMigration)resolvedVersioned.get(Pair.of(((AppliedMigration)av.getLeft()).getVersion(), ((AppliedMigration)av.getLeft()).getType().isUndo()));
         if (prr != null && ((AppliedMigration)av.getLeft()).isSuccess()) {
            pendingResolvedVersioned.remove(prr);
         }
      }

      var11 = pendingResolvedVersioned.iterator();

      while(var11.hasNext()) {
         ResolvedMigration prv = (ResolvedMigration)var11.next();
         migrationInfos1.add(new MigrationInfoImpl(prv, (AppliedMigration)null, context, false));
      }

      var11 = appliedRepeatable.iterator();

      while(true) {
         AppliedMigration appliedRepeatableMigration;
         do {
            if (!var11.hasNext()) {
               Set<ResolvedMigration> pendingResolvedRepeatable = new HashSet(resolvedRepeatable.values());

               ResolvedMigration resolvedMigration;
               Iterator var28;
               AppliedMigration appliedRepeatableMigration;
               for(var28 = appliedRepeatable.iterator(); var28.hasNext(); migrationInfos1.add(new MigrationInfoImpl(resolvedMigration, appliedRepeatableMigration, context, false))) {
                  appliedRepeatableMigration = (AppliedMigration)var28.next();
                  resolvedMigration = (ResolvedMigration)resolvedRepeatable.get(appliedRepeatableMigration.getDescription());
                  int latestRank = (Integer)context.latestRepeatableRuns.get(appliedRepeatableMigration.getDescription());
                  if (resolvedMigration != null && appliedRepeatableMigration.getInstalledRank() == latestRank && ObjectUtils.nullSafeEquals(appliedRepeatableMigration.getChecksum(), resolvedMigration.getChecksum())) {
                     pendingResolvedRepeatable.remove(resolvedMigration);
                  }
               }

               var28 = pendingResolvedRepeatable.iterator();

               while(var28.hasNext()) {
                  prr = (ResolvedMigration)var28.next();
                  migrationInfos1.add(new MigrationInfoImpl(prr, (AppliedMigration)null, context, false));
               }

               Collections.sort(migrationInfos1);
               this.migrationInfos = migrationInfos1;
               return;
            }

            appliedRepeatableMigration = (AppliedMigration)var11.next();
         } while(context.latestRepeatableRuns.containsKey(appliedRepeatableMigration.getDescription()) && appliedRepeatableMigration.getInstalledRank() <= (Integer)context.latestRepeatableRuns.get(appliedRepeatableMigration.getDescription()));

         context.latestRepeatableRuns.put(appliedRepeatableMigration.getDescription(), appliedRepeatableMigration.getInstalledRank());
      }
   }

   public MigrationInfo[] all() {
      return (MigrationInfo[])this.migrationInfos.toArray(new MigrationInfoImpl[this.migrationInfos.size()]);
   }

   public MigrationInfo current() {
      MigrationInfo current = null;
      Iterator var2 = this.migrationInfos.iterator();

      while(true) {
         MigrationInfoImpl migrationInfo;
         do {
            do {
               do {
                  if (!var2.hasNext()) {
                     if (current != null) {
                        return current;
                     }

                     for(int i = this.migrationInfos.size() - 1; i >= 0; --i) {
                        migrationInfo = (MigrationInfoImpl)this.migrationInfos.get(i);
                        if (migrationInfo.getState().isApplied()) {
                           return migrationInfo;
                        }
                     }

                     return null;
                  }

                  migrationInfo = (MigrationInfoImpl)var2.next();
               } while(!migrationInfo.getState().isApplied());
            } while(migrationInfo.getVersion() == null);
         } while(current != null && migrationInfo.getVersion().compareTo(current.getVersion()) <= 0);

         current = migrationInfo;
      }
   }

   public MigrationInfoImpl[] pending() {
      List<MigrationInfoImpl> pendingMigrations = new ArrayList();
      Iterator var2 = this.migrationInfos.iterator();

      while(var2.hasNext()) {
         MigrationInfoImpl migrationInfo = (MigrationInfoImpl)var2.next();
         if (MigrationState.PENDING == migrationInfo.getState()) {
            pendingMigrations.add(migrationInfo);
         }
      }

      return (MigrationInfoImpl[])pendingMigrations.toArray(new MigrationInfoImpl[pendingMigrations.size()]);
   }

   public MigrationInfoImpl[] applied() {
      List<MigrationInfoImpl> appliedMigrations = new ArrayList();
      Iterator var2 = this.migrationInfos.iterator();

      while(var2.hasNext()) {
         MigrationInfoImpl migrationInfo = (MigrationInfoImpl)var2.next();
         if (migrationInfo.getState().isApplied()) {
            appliedMigrations.add(migrationInfo);
         }
      }

      return (MigrationInfoImpl[])appliedMigrations.toArray(new MigrationInfoImpl[appliedMigrations.size()]);
   }

   public MigrationInfo[] resolved() {
      List<MigrationInfo> resolvedMigrations = new ArrayList();
      Iterator var2 = this.migrationInfos.iterator();

      while(var2.hasNext()) {
         MigrationInfo migrationInfo = (MigrationInfo)var2.next();
         if (migrationInfo.getState().isResolved()) {
            resolvedMigrations.add(migrationInfo);
         }
      }

      return (MigrationInfo[])resolvedMigrations.toArray(new MigrationInfo[resolvedMigrations.size()]);
   }

   public MigrationInfo[] failed() {
      List<MigrationInfo> failedMigrations = new ArrayList();
      Iterator var2 = this.migrationInfos.iterator();

      while(var2.hasNext()) {
         MigrationInfo migrationInfo = (MigrationInfo)var2.next();
         if (migrationInfo.getState().isFailed()) {
            failedMigrations.add(migrationInfo);
         }
      }

      return (MigrationInfo[])failedMigrations.toArray(new MigrationInfo[failedMigrations.size()]);
   }

   public MigrationInfo[] future() {
      List<MigrationInfo> futureMigrations = new ArrayList();
      Iterator var2 = this.migrationInfos.iterator();

      while(true) {
         MigrationInfo migrationInfo;
         do {
            if (!var2.hasNext()) {
               return (MigrationInfo[])futureMigrations.toArray(new MigrationInfo[futureMigrations.size()]);
            }

            migrationInfo = (MigrationInfo)var2.next();
         } while(migrationInfo.getState() != MigrationState.FUTURE_SUCCESS && migrationInfo.getState() != MigrationState.FUTURE_FAILED);

         futureMigrations.add(migrationInfo);
      }
   }

   public MigrationInfo[] outOfOrder() {
      List<MigrationInfo> outOfOrderMigrations = new ArrayList();
      Iterator var2 = this.migrationInfos.iterator();

      while(var2.hasNext()) {
         MigrationInfo migrationInfo = (MigrationInfo)var2.next();
         if (migrationInfo.getState() == MigrationState.OUT_OF_ORDER) {
            outOfOrderMigrations.add(migrationInfo);
         }
      }

      return (MigrationInfo[])outOfOrderMigrations.toArray(new MigrationInfo[outOfOrderMigrations.size()]);
   }

   public String validate() {
      Iterator var1 = this.migrationInfos.iterator();

      String message;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         MigrationInfoImpl migrationInfo = (MigrationInfoImpl)var1.next();
         message = migrationInfo.validate();
      } while(message == null);

      return message;
   }
}
