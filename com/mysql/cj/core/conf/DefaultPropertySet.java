package com.mysql.cj.core.conf;

import com.mysql.cj.api.conf.ModifiableProperty;
import com.mysql.cj.api.conf.PropertyDefinition;
import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.conf.ReadableProperty;
import com.mysql.cj.api.conf.RuntimeProperty;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.PropertyNotModifiableException;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class DefaultPropertySet implements PropertySet, Serializable {
   private static final long serialVersionUID = -5156024634430650528L;
   private final Map<String, RuntimeProperty<?>> PROPERTY_NAME_TO_RUNTIME_PROPERTY = new HashMap();

   public DefaultPropertySet() {
      Iterator var1 = PropertyDefinitions.PROPERTY_NAME_TO_PROPERTY_DEFINITION.values().iterator();

      while(var1.hasNext()) {
         PropertyDefinition<?> pdef = (PropertyDefinition)var1.next();
         this.addProperty(pdef.createRuntimeProperty());
      }

   }

   public void addProperty(RuntimeProperty<?> prop) {
      this.PROPERTY_NAME_TO_RUNTIME_PROPERTY.put(prop.getPropertyDefinition().getName(), prop);
      this.PROPERTY_NAME_TO_RUNTIME_PROPERTY.put(PropertyDefinitions.PROPERTY_NAME_TO_ALIAS.get(prop.getPropertyDefinition().getName()), prop);
   }

   public void removeProperty(String name) {
      RuntimeProperty<?> prop = (RuntimeProperty)this.PROPERTY_NAME_TO_RUNTIME_PROPERTY.remove(name);
      if (prop != null) {
         if (name.equals(prop.getPropertyDefinition().getName())) {
            this.PROPERTY_NAME_TO_RUNTIME_PROPERTY.remove(PropertyDefinitions.PROPERTY_NAME_TO_ALIAS.get(prop.getPropertyDefinition().getName()));
         } else {
            this.PROPERTY_NAME_TO_RUNTIME_PROPERTY.remove(prop.getPropertyDefinition().getName());
         }
      }

   }

   public <T> ReadableProperty<T> getReadableProperty(String name) {
      try {
         ReadableProperty<T> prop = (ReadableProperty)this.PROPERTY_NAME_TO_RUNTIME_PROPERTY.get(name);
         if (prop != null) {
            return prop;
         } else {
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionProperties.notFound", new Object[]{name}));
         }
      } catch (ClassCastException var3) {
         throw (WrongArgumentException)ExceptionFactory.createException((Class)WrongArgumentException.class, (String)var3.getMessage(), (Throwable)var3);
      }
   }

   public ReadableProperty<Boolean> getBooleanReadableProperty(String name) {
      return this.getReadableProperty(name);
   }

   public ReadableProperty<Integer> getIntegerReadableProperty(String name) {
      return this.getReadableProperty(name);
   }

   public ReadableProperty<Long> getLongReadableProperty(String name) {
      return this.getReadableProperty(name);
   }

   public ReadableProperty<Integer> getMemorySizeReadableProperty(String name) {
      return this.getReadableProperty(name);
   }

   public ReadableProperty<String> getStringReadableProperty(String name) {
      return this.getReadableProperty(name);
   }

   public <T> ModifiableProperty<T> getModifiableProperty(String name) {
      RuntimeProperty<?> prop = (RuntimeProperty)this.PROPERTY_NAME_TO_RUNTIME_PROPERTY.get(name);
      if (prop != null) {
         if (ModifiableProperty.class.isAssignableFrom(prop.getClass())) {
            try {
               return (ModifiableProperty)this.PROPERTY_NAME_TO_RUNTIME_PROPERTY.get(name);
            } catch (ClassCastException var4) {
               throw (WrongArgumentException)ExceptionFactory.createException((Class)WrongArgumentException.class, (String)var4.getMessage(), (Throwable)var4);
            }
         } else {
            throw (PropertyNotModifiableException)ExceptionFactory.createException(PropertyNotModifiableException.class, Messages.getString("ConnectionProperties.dynamicChangeIsNotAllowed", new Object[]{"'" + prop.getPropertyDefinition().getName() + "'"}));
         }
      } else {
         throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionProperties.notFound", new Object[]{name}));
      }
   }

   public void initializeProperties(Properties props) {
      if (props != null) {
         Properties infoCopy = (Properties)props.clone();
         infoCopy.remove("HOST");
         infoCopy.remove("user");
         infoCopy.remove("password");
         infoCopy.remove("DBNAME");
         infoCopy.remove("PORT");
         Iterator var3 = PropertyDefinitions.PROPERTY_NAME_TO_PROPERTY_DEFINITION.keySet().iterator();

         while(var3.hasNext()) {
            String propName = (String)var3.next();

            try {
               ReadableProperty<?> propToSet = this.getReadableProperty(propName);
               propToSet.initializeFrom(infoCopy, (ExceptionInterceptor)null);
            } catch (CJException var6) {
               throw (WrongArgumentException)ExceptionFactory.createException((Class)WrongArgumentException.class, (String)var6.getMessage(), (Throwable)var6);
            }
         }

         this.postInitialization();
      }

   }

   public void postInitialization() {
   }

   public Properties exposeAsProperties(Properties props) {
      if (props == null) {
         props = new Properties();
      }

      Iterator var2 = PropertyDefinitions.PROPERTY_NAME_TO_PROPERTY_DEFINITION.keySet().iterator();

      while(var2.hasNext()) {
         String propName = (String)var2.next();
         ReadableProperty<?> propToGet = this.getReadableProperty(propName);
         String propValue = propToGet.getStringValue();
         if (propValue != null) {
            props.setProperty(propToGet.getPropertyDefinition().getName(), propValue);
         }
      }

      return props;
   }
}
