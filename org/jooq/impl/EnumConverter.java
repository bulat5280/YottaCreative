package org.jooq.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import org.jooq.tools.Convert;

public class EnumConverter<T, U extends Enum<U>> extends AbstractConverter<T, U> {
   private static final long serialVersionUID = -6094337837408829491L;
   private final Map<T, U> lookup;
   private final EnumConverter.EnumType enumType;

   public EnumConverter(Class<T> fromType, Class<U> toType) {
      super(fromType, toType);
      this.enumType = Number.class.isAssignableFrom(fromType) ? EnumConverter.EnumType.ORDINAL : EnumConverter.EnumType.STRING;
      this.lookup = new LinkedHashMap();
      Enum[] var3 = (Enum[])toType.getEnumConstants();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         U u = var3[var5];
         this.lookup.put(this.to(u), u);
      }

   }

   public final U from(T databaseObject) {
      return (Enum)this.lookup.get(databaseObject);
   }

   public T to(U userObject) {
      if (userObject == null) {
         return null;
      } else {
         return this.enumType == EnumConverter.EnumType.ORDINAL ? Convert.convert((Object)userObject.ordinal(), (Class)this.fromType()) : Convert.convert((Object)userObject.name(), (Class)this.fromType());
      }
   }

   public String toString() {
      return "EnumConverter [ " + this.fromType().getName() + " -> " + this.toType().getName() + " ]";
   }

   static enum EnumType {
      ORDINAL,
      STRING;
   }
}
