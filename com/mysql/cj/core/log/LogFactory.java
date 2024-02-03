package com.mysql.cj.core.log;

import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.log.Log;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class LogFactory {
   public static Log getLogger(String className, String instanceName, ExceptionInterceptor exceptionInterceptor) {
      if (className == null) {
         throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, "Logger class can not be NULL", exceptionInterceptor);
      } else if (instanceName == null) {
         throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, "Logger instance name can not be NULL", exceptionInterceptor);
      } else {
         try {
            Class loggerClass = null;

            try {
               loggerClass = Class.forName(className);
            } catch (ClassNotFoundException var5) {
               loggerClass = Class.forName(LogFactory.class.getPackage().getName() + "." + className);
            }

            Constructor<?> constructor = loggerClass.getConstructor(String.class);
            return (Log)constructor.newInstance(instanceName);
         } catch (ClassNotFoundException var6) {
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, "Unable to load class for logger '" + className + "'", var6, exceptionInterceptor);
         } catch (NoSuchMethodException var7) {
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, "Logger class does not have a single-arg constructor that takes an instance name", var7, exceptionInterceptor);
         } catch (InstantiationException var8) {
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, "Unable to instantiate logger class '" + className + "', exception in constructor?", var8, exceptionInterceptor);
         } catch (InvocationTargetException var9) {
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, "Unable to instantiate logger class '" + className + "', exception in constructor?", var9, exceptionInterceptor);
         } catch (IllegalAccessException var10) {
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, "Unable to instantiate logger class '" + className + "', constructor not public", var10, exceptionInterceptor);
         } catch (ClassCastException var11) {
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, "Logger class '" + className + "' does not implement the '" + Log.class.getName() + "' interface", var11, exceptionInterceptor);
         }
      }
   }
}
