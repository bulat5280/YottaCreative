package org.jooq.util;

class ConstantCatalogVersionProvider implements CatalogVersionProvider {
   private String constant;

   ConstantCatalogVersionProvider(String constant) {
      this.constant = constant;
   }

   public String version(CatalogDefinition catalog) {
      return this.constant;
   }
}
