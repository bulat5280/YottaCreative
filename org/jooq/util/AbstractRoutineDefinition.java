package org.jooq.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.jooq.tools.JooqLogger;

public abstract class AbstractRoutineDefinition extends AbstractDefinition implements RoutineDefinition {
   private static final JooqLogger log = JooqLogger.getLogger(AbstractRoutineDefinition.class);
   private static final String INOUT = "(?i:(IN|OUT|INOUT)\\s+?)?";
   private static final String PARAM_NAME = "(?:(\\S+?)\\s+?)";
   private static final String PARAM_TYPE = "([^\\s\\(]+)(?:\\s*\\((\\d+)(?:\\s*,\\s*(\\d+))?\\))?";
   private static final String PARAMETER = "((?i:(IN|OUT|INOUT)\\s+?)?(?:(\\S+?)\\s+?)([^\\s\\(]+)(?:\\s*\\((\\d+)(?:\\s*,\\s*(\\d+))?\\))?)";
   protected static final Pattern PARAMETER_PATTERN = Pattern.compile("((?i:(IN|OUT|INOUT)\\s+?)?(?:(\\S+?)\\s+?)([^\\s\\(]+)(?:\\s*\\((\\d+)(?:\\s*,\\s*(\\d+))?\\))?)");
   protected static final Pattern TYPE_PATTERN = Pattern.compile("([^\\s\\(]+)(?:\\s*\\((\\d+)(?:\\s*,\\s*(\\d+))?\\))?");
   protected List<ParameterDefinition> inParameters;
   protected List<ParameterDefinition> outParameters;
   protected ParameterDefinition returnValue;
   protected List<ParameterDefinition> allParameters;
   private final PackageDefinition pkg;
   private final boolean aggregate;

   public AbstractRoutineDefinition(SchemaDefinition schema, PackageDefinition pkg, String name, String comment, String overload) {
      this(schema, pkg, name, comment, overload, false);
   }

   public AbstractRoutineDefinition(SchemaDefinition schema, PackageDefinition pkg, String name, String comment, String overload, boolean aggregate) {
      super(schema.getDatabase(), schema, name, comment, overload);
      this.pkg = pkg;
      this.aggregate = aggregate;
   }

   public List<Definition> getDefinitionPath() {
      List<Definition> result = new ArrayList();
      result.addAll(this.getSchema().getDefinitionPath());
      if (this.pkg != null) {
         result.add(this.pkg);
      }

      result.add(this);
      return result;
   }

   protected void init() {
      this.inParameters = new ArrayList();
      this.outParameters = new ArrayList();
      this.allParameters = new ArrayList();

      try {
         if (this.returnValue != null) {
            this.addParameter(InOutDefinition.RETURN, this.returnValue);
         }

         this.init0();
      } catch (Exception var2) {
         log.error("Error while initialising routine", (Throwable)var2);
      }

   }

   protected abstract void init0() throws SQLException;

   public final PackageDefinition getPackage() {
      return this.pkg;
   }

   public final List<ParameterDefinition> getInParameters() {
      if (this.inParameters == null) {
         this.init();
      }

      return this.inParameters;
   }

   public final List<ParameterDefinition> getOutParameters() {
      if (this.outParameters == null) {
         this.init();
      }

      return this.outParameters;
   }

   public final List<ParameterDefinition> getAllParameters() {
      if (this.allParameters == null) {
         this.init();
      }

      return this.allParameters;
   }

   public final ParameterDefinition getReturnValue() {
      if (this.allParameters == null) {
         this.init();
      }

      return this.returnValue;
   }

   public final DataTypeDefinition getReturnType() {
      return (DataTypeDefinition)(this.getReturnValue() != null ? this.getReturnValue().getType() : new DefaultDataTypeDefinition(this.getDatabase(), this.getSchema(), "unknown"));
   }

   public boolean isSQLUsable() {
      return this.getReturnValue() != null && this.getOutParameters().isEmpty();
   }

   public final boolean isAggregate() {
      return this.aggregate;
   }

   protected final void addParameter(InOutDefinition inOut, ParameterDefinition parameter) {
      this.allParameters.add(parameter);
      switch(inOut) {
      case IN:
         this.inParameters.add(parameter);
         break;
      case OUT:
         this.outParameters.add(parameter);
         break;
      case INOUT:
         this.inParameters.add(parameter);
         this.outParameters.add(parameter);
         break;
      case RETURN:
         this.returnValue = parameter;
      }

   }
}
