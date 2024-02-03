package org.apache.commons.math3.ode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractParameterizable implements Parameterizable {
   private final Collection<String> parametersNames = new ArrayList();

   protected AbstractParameterizable(String... names) {
      String[] arr$ = names;
      int len$ = names.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         String name = arr$[i$];
         this.parametersNames.add(name);
      }

   }

   protected AbstractParameterizable(Collection<String> names) {
      this.parametersNames.addAll(names);
   }

   public Collection<String> getParametersNames() {
      return this.parametersNames;
   }

   public boolean isSupported(String name) {
      Iterator i$ = this.parametersNames.iterator();

      String supportedName;
      do {
         if (!i$.hasNext()) {
            return false;
         }

         supportedName = (String)i$.next();
      } while(!supportedName.equals(name));

      return true;
   }

   public void complainIfNotSupported(String name) throws UnknownParameterException {
      if (!this.isSupported(name)) {
         throw new UnknownParameterException(name);
      }
   }
}
