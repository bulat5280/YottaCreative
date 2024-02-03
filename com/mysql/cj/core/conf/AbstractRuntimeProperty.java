package com.mysql.cj.core.conf;

import com.mysql.cj.api.conf.PropertyDefinition;
import com.mysql.cj.api.conf.RuntimeProperty;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.naming.RefAddr;
import javax.naming.Reference;

public abstract class AbstractRuntimeProperty<T> implements RuntimeProperty<T>, Serializable {
   private static final long serialVersionUID = -3424722534876438236L;
   private PropertyDefinition<T> propertyDefinition;
   protected T valueAsObject;
   protected T initialValueAsObject;
   protected boolean wasExplicitlySet = false;
   private List<WeakReference<RuntimeProperty.RuntimePropertyListener>> listeners;

   public AbstractRuntimeProperty() {
   }

   protected AbstractRuntimeProperty(PropertyDefinition<T> propertyDefinition) {
      this.propertyDefinition = propertyDefinition;
      this.valueAsObject = this.getPropertyDefinition().getDefaultValue();
   }

   public PropertyDefinition<T> getPropertyDefinition() {
      return this.propertyDefinition;
   }

   public void initializeFrom(Properties extractFrom, ExceptionInterceptor exceptionInterceptor) {
      String extractedValue = extractFrom.getProperty(this.getPropertyDefinition().getName());
      extractFrom.remove(this.getPropertyDefinition().getName());
      this.initializeFrom(extractedValue, exceptionInterceptor);
   }

   public void initializeFrom(Reference ref, ExceptionInterceptor exceptionInterceptor) {
      RefAddr refAddr = ref.get(this.getPropertyDefinition().getName());
      if (refAddr != null) {
         String refContentAsString = (String)refAddr.getContent();
         this.initializeFrom(refContentAsString, exceptionInterceptor);
      }

   }

   protected void initializeFrom(String extractedValue, ExceptionInterceptor exceptionInterceptor) {
      if (extractedValue != null) {
         this.setFromString(extractedValue, exceptionInterceptor);
      }

   }

   public void setFromString(String value, ExceptionInterceptor exceptionInterceptor) {
      this.valueAsObject = this.getPropertyDefinition().parseObject(value, exceptionInterceptor);
      this.wasExplicitlySet = true;
   }

   public void resetValue() {
   }

   public boolean isExplicitlySet() {
      return this.wasExplicitlySet;
   }

   public void addListener(RuntimeProperty.RuntimePropertyListener l) {
      if (this.listeners == null) {
         this.listeners = new ArrayList();
      }

      if (!this.listeners.contains(l)) {
         this.listeners.add(new WeakReference(l));
      }

   }

   public void removeListener(RuntimeProperty.RuntimePropertyListener listener) {
      if (this.listeners != null) {
         Iterator var2 = this.listeners.iterator();

         while(var2.hasNext()) {
            WeakReference<RuntimeProperty.RuntimePropertyListener> wr = (WeakReference)var2.next();
            RuntimeProperty.RuntimePropertyListener l = (RuntimeProperty.RuntimePropertyListener)wr.get();
            if (l == listener) {
               this.listeners.remove(wr);
               break;
            }
         }
      }

   }

   protected void invokeListeners() {
      if (this.listeners != null) {
         Iterator var1 = this.listeners.iterator();

         while(var1.hasNext()) {
            WeakReference<RuntimeProperty.RuntimePropertyListener> wr = (WeakReference)var1.next();
            RuntimeProperty.RuntimePropertyListener l = (RuntimeProperty.RuntimePropertyListener)wr.get();
            if (l != null) {
               l.handlePropertyChange(this);
            } else {
               this.listeners.remove(wr);
            }
         }
      }

   }
}
