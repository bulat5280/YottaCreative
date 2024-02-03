package junit.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public abstract class TestCase extends Assert implements Test {
   private String fName;

   public TestCase() {
      this.fName = null;
   }

   public TestCase(String name) {
      this.fName = name;
   }

   public int countTestCases() {
      return 1;
   }

   protected TestResult createResult() {
      return new TestResult();
   }

   public TestResult run() {
      TestResult result = this.createResult();
      this.run(result);
      return result;
   }

   public void run(TestResult result) {
      result.run(this);
   }

   public void runBare() throws Throwable {
      Throwable exception = null;
      this.setUp();

      try {
         this.runTest();
      } catch (Throwable var10) {
         exception = var10;
      } finally {
         try {
            this.tearDown();
         } catch (Throwable var11) {
            if (exception == null) {
               exception = var11;
            }
         }

      }

      if (exception != null) {
         throw exception;
      }
   }

   protected void runTest() throws Throwable {
      assertNotNull("TestCase.fName cannot be null", this.fName);
      Method runMethod = null;

      try {
         runMethod = this.getClass().getMethod(this.fName, (Class[])null);
      } catch (NoSuchMethodException var5) {
         fail("Method \"" + this.fName + "\" not found");
      }

      if (!Modifier.isPublic(runMethod.getModifiers())) {
         fail("Method \"" + this.fName + "\" should be public");
      }

      try {
         runMethod.invoke(this);
      } catch (InvocationTargetException var3) {
         var3.fillInStackTrace();
         throw var3.getTargetException();
      } catch (IllegalAccessException var4) {
         var4.fillInStackTrace();
         throw var4;
      }
   }

   protected void setUp() throws Exception {
   }

   protected void tearDown() throws Exception {
   }

   public String toString() {
      return this.getName() + "(" + this.getClass().getName() + ")";
   }

   public String getName() {
      return this.fName;
   }

   public void setName(String name) {
      this.fName = name;
   }
}
