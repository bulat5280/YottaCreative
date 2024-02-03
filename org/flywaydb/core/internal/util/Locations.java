package org.flywaydb.core.internal.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;

public class Locations {
   private static final Log LOG = LogFactory.getLog(Locations.class);
   private final List<Location> locations = new ArrayList();

   public Locations(String... rawLocations) {
      List<Location> normalizedLocations = new ArrayList();
      String[] var3 = rawLocations;
      int var4 = rawLocations.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String rawLocation = var3[var5];
         normalizedLocations.add(new Location(rawLocation));
      }

      Collections.sort(normalizedLocations);
      Iterator var7 = normalizedLocations.iterator();

      while(var7.hasNext()) {
         Location normalizedLocation = (Location)var7.next();
         if (this.locations.contains(normalizedLocation)) {
            LOG.warn("Discarding duplicate location '" + normalizedLocation + "'");
         } else {
            Location parentLocation = this.getParentLocationIfExists(normalizedLocation, this.locations);
            if (parentLocation != null) {
               LOG.warn("Discarding location '" + normalizedLocation + "' as it is a sublocation of '" + parentLocation + "'");
            } else {
               this.locations.add(normalizedLocation);
            }
         }
      }

   }

   public List<Location> getLocations() {
      return this.locations;
   }

   private Location getParentLocationIfExists(Location location, List<Location> finalLocations) {
      Iterator var3 = finalLocations.iterator();

      Location finalLocation;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         finalLocation = (Location)var3.next();
      } while(!finalLocation.isParentOf(location));

      return finalLocation;
   }
}
