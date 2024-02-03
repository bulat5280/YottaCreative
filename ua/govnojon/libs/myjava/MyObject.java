package ua.govnojon.libs.myjava;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;

public class MyObject {
   private Object object;
   private Class clazz;

   public MyObject(Object object) {
      this.object = object;
      this.clazz = object.getClass();
   }

   public MyObject(Class clazz) {
      this.object = null;
      this.clazz = clazz;
   }

   public static MyObject wrap(Object player) {
      return new MyObject(player);
   }

   public static MyObject wrap(Class clazz) {
      return new MyObject(clazz);
   }

   public MyObject getField(String name) {
      return (MyObject)Try.ignore((Try.SupplierThrows)(() -> {
         Field field = null;
         Class c = this.clazz;

         do {
            try {
               field = c.getDeclaredField(name);
            } catch (NoSuchFieldException var6) {
            } catch (Exception var7) {
               var7.printStackTrace();
            }
         } while(field == null && (c = c.getSuperclass()) != null);

         if (field == null) {
            throw new NoSuchFieldException(name);
         } else {
            boolean isSetAccessible = false;
            if (!field.isAccessible()) {
               field.setAccessible(true);
               isSetAccessible = true;
            }

            Object get = field.get(this.object);
            if (isSetAccessible) {
               field.setAccessible(false);
            }

            return get == null ? null : new MyObject(get);
         }
      }), (Object)null);
   }

   public <T> T get(String field) {
      return this.getField(field).getObject();
   }

   public void set(String field, Object value) {
      this.setField(field, value);
   }

   public void invoke(String method, Object... parameters) {
      this.invokeMethod(method, parameters);
   }

   public void setField(String name, Object value) {
      Try.ignore(() -> {
         Class c = this.clazz;
         Field field = null;

         do {
            try {
               field = c.getDeclaredField(name);
            } catch (Exception var6) {
            }
         } while(field == null && (c = c.getSuperclass()) != null);

         boolean isSetAccessible = false;
         if (!field.isAccessible()) {
            field.setAccessible(true);
            isSetAccessible = true;
         }

         field.set(this.object, value instanceof MyObject ? ((MyObject)value).getObject() : value);
         if (isSetAccessible) {
            field.setAccessible(false);
         }

      }, (e) -> {
         System.out.println("Переменная '" + name + "' не найдена.");
      });
   }

   public MyObject invokeMethod(String name, Object... args) {
      try {
         Method method = null;
         this.fixArgs(args);
         Class c = this.clazz;

         label60:
         do {
            Method[] var5 = c.getDeclaredMethods();
            int var6 = var5.length;

            label58:
            for(int var7 = 0; var7 < var6; ++var7) {
               Method m = var5[var7];
               if (m.getName().equals(name) && m.getParameterCount() == args.length) {
                  for(int i = 0; i < m.getParameterCount(); ++i) {
                     if (args[i] != null && !m.getParameterTypes()[i].isInstance(args[i])) {
                        continue label58;
                     }
                  }

                  method = m;
                  break label60;
               }
            }
         } while((c = c.getSuperclass()) != null);

         if (method == null) {
            throw new NullPointerException("Метод не найден.");
         } else {
            boolean isSetAccessible = false;
            if (!method.isAccessible()) {
               method.setAccessible(true);
               isSetAccessible = true;
            }

            Object returnObject = method.invoke(this.object, args);
            if (isSetAccessible) {
               method.setAccessible(false);
            }

            return returnObject == null ? null : new MyObject(returnObject);
         }
      } catch (Exception var10) {
         Bukkit.getLogger().severe("Error in: " + this.getObject().getClass());
         var10.printStackTrace();
         return null;
      }
   }

   public <T> T getObject(Class<T> cast) {
      return this.object;
   }

   public <T> T getObject() {
      return this.object;
   }

   private void fixArgs(Object[] args) {
      for(int i = 0; i < args.length; ++i) {
         if (args[i] instanceof MyObject) {
            args[i] = ((MyObject)args[i]).getObject();
         }
      }

   }
}
