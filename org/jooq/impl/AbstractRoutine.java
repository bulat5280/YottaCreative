package org.jooq.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jooq.AggregateFunction;
import org.jooq.AttachableInternal;
import org.jooq.BindContext;
import org.jooq.Binding;
import org.jooq.BindingGetStatementContext;
import org.jooq.Catalog;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Converter;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Field;
import org.jooq.Package;
import org.jooq.Parameter;
import org.jooq.QueryPart;
import org.jooq.RenderContext;
import org.jooq.Result;
import org.jooq.Results;
import org.jooq.Routine;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.exception.ControlFlowSignal;
import org.jooq.tools.Convert;

public abstract class AbstractRoutine<T> extends AbstractQueryPart implements Routine<T>, AttachableInternal {
   private static final long serialVersionUID = 6330037113167106443L;
   private static final Clause[] CLAUSES;
   private final Schema schema;
   private final Package pkg;
   private final String name;
   private final List<Parameter<?>> allParameters;
   private final List<Parameter<?>> inParameters;
   private final List<Parameter<?>> outParameters;
   private final DataType<T> type;
   private Parameter<T> returnParameter;
   private ResultsImpl results;
   private boolean overloaded;
   private boolean hasUnnamedParameters;
   private final Map<Parameter<?>, Field<?>> inValues;
   private final Set<Parameter<?>> inValuesDefaulted;
   private final Set<Parameter<?>> inValuesNonDefaulted;
   private transient Field<T> function;
   private Configuration configuration;
   private final Map<Parameter<?>, Object> outValues;
   private final Map<Parameter<?>, Integer> resultIndexes;

   protected AbstractRoutine(String name, Schema schema) {
      this(name, schema, (Package)null, (DataType)null, (Converter)null, (Binding)null);
   }

   protected AbstractRoutine(String name, Schema schema, Package pkg) {
      this(name, schema, pkg, (DataType)null, (Converter)null, (Binding)null);
   }

   protected AbstractRoutine(String name, Schema schema, DataType<T> type) {
      this(name, schema, (Package)null, type, (Converter)null, (Binding)null);
   }

   protected <X> AbstractRoutine(String name, Schema schema, DataType<X> type, Converter<X, T> converter) {
      this(name, schema, (Package)null, type, converter, (Binding)null);
   }

   protected <X> AbstractRoutine(String name, Schema schema, DataType<X> type, Binding<X, T> binding) {
      this(name, schema, (Package)null, type, (Converter)null, binding);
   }

   protected <X, Y> AbstractRoutine(String name, Schema schema, DataType<X> type, Converter<Y, T> converter, Binding<X, Y> binding) {
      this(name, schema, (Package)null, type, converter, binding);
   }

   protected AbstractRoutine(String name, Schema schema, Package pkg, DataType<T> type) {
      this(name, schema, pkg, type, (Converter)null, (Binding)null);
   }

   protected <X> AbstractRoutine(String name, Schema schema, Package pkg, DataType<X> type, Converter<X, T> converter) {
      this(name, schema, pkg, type, converter, (Binding)null);
   }

   protected <X> AbstractRoutine(String name, Schema schema, Package pkg, DataType<X> type, Binding<X, T> binding) {
      this(name, schema, pkg, type, (Converter)null, binding);
   }

   protected <X, Y> AbstractRoutine(String name, Schema schema, Package pkg, DataType<X> type, Converter<Y, T> converter, Binding<X, Y> binding) {
      this.resultIndexes = new HashMap();
      this.schema = schema;
      this.pkg = pkg;
      this.name = name;
      this.allParameters = new ArrayList();
      this.inParameters = new ArrayList();
      this.outParameters = new ArrayList();
      this.results = new ResultsImpl((Configuration)null);
      this.inValues = new HashMap();
      this.inValuesDefaulted = new HashSet();
      this.inValuesNonDefaulted = new HashSet();
      this.outValues = new HashMap();
      this.type = converter == null && binding == null ? type : type.asConvertedDataType(DefaultBinding.newBinding(converter, type, binding));
   }

   protected final <N extends Number> void setNumber(Parameter<N> parameter, Number value) {
      this.setValue(parameter, Convert.convert((Object)value, (Class)parameter.getType()));
   }

   protected final void setNumber(Parameter<? extends Number> parameter, Field<? extends Number> value) {
      this.setField(parameter, value);
   }

   public final <Z> void setValue(Parameter<Z> parameter, Z value) {
      this.set(parameter, value);
   }

   public final <Z> void set(Parameter<Z> parameter, Z value) {
      this.setField(parameter, DSL.val(value, parameter.getDataType()));
   }

   protected final void setField(Parameter<?> parameter, Field<?> value) {
      if (value == null) {
         this.setField(parameter, DSL.val((Object)null, (DataType)parameter.getDataType()));
      } else {
         this.inValues.put(parameter, value);
         this.inValuesDefaulted.remove(parameter);
         this.inValuesNonDefaulted.add(parameter);
      }

   }

   public final void attach(Configuration c) {
      this.configuration = c;
   }

   public final void detach() {
      this.attach((Configuration)null);
   }

   public final Configuration configuration() {
      return this.configuration;
   }

   public final int execute(Configuration c) {
      Configuration previous = this.configuration();

      int var3;
      try {
         this.attach(c);
         var3 = this.execute();
      } finally {
         this.attach(previous);
      }

      return var3;
   }

   public final int execute() {
      SQLDialect family = this.configuration.family();
      this.results.clear();
      this.outValues.clear();
      if (family == SQLDialect.POSTGRES) {
         return this.executeSelectFromPOSTGRES();
      } else if (this.type == null) {
         return this.executeCallableStatement();
      } else {
         switch(family) {
         case HSQLDB:
            if (SQLDataType.RESULT.equals(this.type.getSQLDataType())) {
               return this.executeSelectFromHSQLDB();
            }
         case H2:
            return this.executeSelect();
         default:
            return this.executeCallableStatement();
         }
      }
   }

   private final int executeSelectFromHSQLDB() {
      DSLContext create = this.create(this.configuration);
      Result<?> result = create.selectFrom(DSL.table(this.asField())).fetch();
      this.outValues.put(this.returnParameter, result);
      return 0;
   }

   private final int executeSelectFromPOSTGRES() {
      DSLContext create = this.create(this.configuration);
      List<Field<?>> fields = new ArrayList();
      if (this.returnParameter != null) {
         fields.add(DSL.field(DSL.name(this.getName()), this.returnParameter.getDataType()));
      }

      Iterator var3 = this.outParameters.iterator();

      while(var3.hasNext()) {
         Parameter<?> p = (Parameter)var3.next();
         fields.add(DSL.field(DSL.name(p.getName()), p.getDataType()));
      }

      Result<?> result = create.select((Collection)fields).from("{0}", new QueryPart[]{this.asField()}).fetch();
      int i = 0;
      if (this.returnParameter != null) {
         this.outValues.put(this.returnParameter, this.returnParameter.getDataType().convert(result.getValue(0, i++)));
      }

      Iterator var5 = this.outParameters.iterator();

      while(var5.hasNext()) {
         Parameter<?> p = (Parameter)var5.next();
         this.outValues.put(p, p.getDataType().convert(result.getValue(0, i++)));
      }

      return 0;
   }

   private final int executeSelect() {
      Field<T> field = this.asField();
      this.outValues.put(this.returnParameter, this.create(this.configuration).select((SelectField)field).fetchOne(field));
      return 0;
   }

   private final int executeCallableStatement() {
      ExecuteContext ctx = new DefaultExecuteContext(this.configuration, this);
      ExecuteListeners listener = new ExecuteListeners(ctx);

      byte var4;
      try {
         Connection connection = ctx.connection();
         listener.renderStart(ctx);
         ctx.sql(this.create(this.configuration).render(this));
         listener.renderEnd(ctx);
         listener.prepareStart(ctx);
         ctx.statement(connection.prepareCall(ctx.sql()));
         listener.prepareEnd(ctx);
         listener.bindStart(ctx);
         DSL.using(this.configuration).bindContext(ctx.statement()).visit(this);
         this.registerOutParameters(ctx);
         listener.bindEnd(ctx);
         this.execute0(ctx, listener);
         if (ctx.family() != SQLDialect.FIREBIRD) {
            Tools.consumeResultSets(ctx, listener, this.results, (Intern)null);
         }

         listener.outStart(ctx);
         this.fetchOutParameters(ctx);
         listener.outEnd(ctx);
         var4 = 0;
      } catch (ControlFlowSignal var10) {
         throw var10;
      } catch (RuntimeException var11) {
         ctx.exception(var11);
         listener.exception(ctx);
         throw ctx.exception();
      } catch (SQLException var12) {
         ctx.sqlException(var12);
         listener.exception(ctx);
         throw ctx.exception();
      } finally {
         Tools.safeClose(listener, ctx);
      }

      return var4;
   }

   private final void execute0(ExecuteContext ctx, ExecuteListener listener) throws SQLException {
      try {
         listener.executeStart(ctx);
         if (ctx.statement().execute()) {
            ctx.resultSet(ctx.statement().getResultSet());
         }

         listener.executeEnd(ctx);
      } catch (SQLException var4) {
         Tools.consumeExceptions(ctx.configuration(), ctx.statement(), var4);
         throw var4;
      }
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   public void accept(Context<?> ctx) {
      if (ctx instanceof RenderContext) {
         this.toSQL0((RenderContext)ctx);
      } else {
         this.bind0((BindContext)ctx);
      }

   }

   final void bind0(BindContext context) {
      Iterator var2 = this.getParameters().iterator();

      while(true) {
         Parameter parameter;
         do {
            if (!var2.hasNext()) {
               return;
            }

            parameter = (Parameter)var2.next();
         } while(this.getInParameters().contains(parameter) && this.inValuesDefaulted.contains(parameter));

         this.bind1(context, parameter, this.getInValues().get(parameter) != null, this.resultParameter(parameter));
      }
   }

   private final void bind1(BindContext context, Parameter<?> parameter, boolean bindAsIn, boolean bindAsOut) {
      int index = context.peekIndex();
      if (bindAsOut) {
         this.resultIndexes.put(parameter, index);
      }

      if (bindAsIn) {
         context.visit((QueryPart)this.getInValues().get(parameter));
         if (index == context.peekIndex() && bindAsOut) {
            context.nextIndex();
         }
      } else {
         context.nextIndex();
      }

   }

   final void toSQL0(RenderContext context) {
      this.toSQLDeclare(context);
      this.toSQLBegin(context);
      if (this.getReturnParameter() != null) {
         this.toSQLAssign(context);
      }

      this.toSQLCall(context);
      context.sql('(');
      String separator = "";
      List<Parameter<?>> parameters = this.getParameters();

      for(int i = 0; i < parameters.size(); ++i) {
         Parameter<?> parameter = (Parameter)parameters.get(i);
         if (!parameter.equals(this.getReturnParameter())) {
            if (this.getOutParameters().contains(parameter)) {
               context.sql(separator);
               this.toSQLOutParam(context, parameter, i);
            } else {
               if (this.inValuesDefaulted.contains(parameter)) {
                  continue;
               }

               context.sql(separator);
               this.toSQLInParam(context, parameter, i, (Field)this.getInValues().get(parameter));
            }

            separator = ", ";
         }
      }

      context.sql(')');
      this.toSQLEnd(context);
   }

   private final void toSQLEnd(RenderContext context) {
      context.sql(" }");
   }

   private final void toSQLDeclare(RenderContext context) {
   }

   private final void toSQLBegin(RenderContext context) {
      context.sql("{ ");
   }

   private final void toSQLAssign(RenderContext context) {
      context.sql("? = ");
   }

   private final void toSQLCall(RenderContext context) {
      context.sql("call ");
      this.toSQLQualifiedName(context);
   }

   private final void toSQLOutParam(RenderContext context, Parameter<?> parameter, int index) {
      context.sql('?');
   }

   private final void toSQLInParam(RenderContext context, Parameter<?> parameter, int index, Field<?> value) {
      context.visit(value);
   }

   private final void toSQLQualifiedName(RenderContext context) {
      Schema mappedSchema = Tools.getMappedSchema(context.configuration(), this.getSchema());
      if (context.qualify()) {
         if (mappedSchema != null) {
            context.visit(mappedSchema);
            context.sql('.');
         }

         if (this.getPackage() != null) {
            context.visit(DSL.name(this.getPackage().getName()));
            context.sql('.');
         }
      }

      context.literal(this.getName());
   }

   private final void fetchOutParameters(ExecuteContext ctx) throws SQLException {
      Iterator var2 = this.getParameters().iterator();

      while(var2.hasNext()) {
         Parameter<?> parameter = (Parameter)var2.next();
         if (this.resultParameter(parameter)) {
            this.fetchOutParameter(ctx, parameter);
         }
      }

   }

   private final <U> void fetchOutParameter(ExecuteContext ctx, Parameter<U> parameter) throws SQLException {
      DefaultBindingGetStatementContext<U> out = new DefaultBindingGetStatementContext(ctx.configuration(), ctx.data(), (CallableStatement)ctx.statement(), (Integer)this.resultIndexes.get(parameter));
      parameter.getBinding().get((BindingGetStatementContext)out);
      this.outValues.put(parameter, out.value());
   }

   private final void registerOutParameters(ExecuteContext ctx) throws SQLException {
      Configuration c = ctx.configuration();
      Map<Object, Object> data = ctx.data();
      CallableStatement statement = (CallableStatement)ctx.statement();
      Iterator var5 = this.getParameters().iterator();

      while(var5.hasNext()) {
         Parameter<?> parameter = (Parameter)var5.next();
         if (this.resultParameter(parameter)) {
            this.registerOutParameter(c, data, statement, parameter);
         }
      }

   }

   private final <U> void registerOutParameter(Configuration c, Map<Object, Object> data, CallableStatement statement, Parameter<U> parameter) throws SQLException {
      parameter.getBinding().register(new DefaultBindingRegisterContext(c, data, statement, (Integer)this.resultIndexes.get(parameter)));
   }

   public final T getReturnValue() {
      return this.returnParameter != null ? this.getValue(this.returnParameter) : null;
   }

   public final Results getResults() {
      return this.results;
   }

   public final <Z> Z getValue(Parameter<Z> parameter) {
      return this.get(parameter);
   }

   public final <Z> Z get(Parameter<Z> parameter) {
      return this.outValues.get(parameter);
   }

   protected final Map<Parameter<?>, Field<?>> getInValues() {
      return this.inValues;
   }

   public final List<Parameter<?>> getOutParameters() {
      return Collections.unmodifiableList(this.outParameters);
   }

   public final List<Parameter<?>> getInParameters() {
      return Collections.unmodifiableList(this.inParameters);
   }

   public final List<Parameter<?>> getParameters() {
      return Collections.unmodifiableList(this.allParameters);
   }

   public final Catalog getCatalog() {
      return this.getSchema() == null ? null : this.getSchema().getCatalog();
   }

   public final Schema getSchema() {
      return this.schema;
   }

   public final Package getPackage() {
      return this.pkg;
   }

   public final String getName() {
      return this.name;
   }

   public final Parameter<T> getReturnParameter() {
      return this.returnParameter;
   }

   protected final void setOverloaded(boolean overloaded) {
      this.overloaded = overloaded;
   }

   protected final boolean isOverloaded() {
      return this.overloaded;
   }

   private final boolean pgArgNeedsCasting(Parameter<?> parameter) {
      return this.isOverloaded() || Arrays.asList(Byte.class, Short.class).contains(parameter.getType());
   }

   private final boolean hasUnnamedParameters() {
      return this.hasUnnamedParameters;
   }

   private final void addParameter(Parameter<?> parameter) {
      this.allParameters.add(parameter);
      this.hasUnnamedParameters |= parameter.isUnnamed();
   }

   private final boolean resultParameter(Parameter<?> parameter) {
      return parameter.equals(this.getReturnParameter()) || this.getOutParameters().contains(parameter);
   }

   protected final void addInParameter(Parameter<?> parameter) {
      this.addParameter(parameter);
      this.inParameters.add(parameter);
      this.inValues.put(parameter, DSL.val((Object)null, (DataType)parameter.getDataType()));
      if (parameter.isDefaulted()) {
         this.inValuesDefaulted.add(parameter);
      } else {
         this.inValuesNonDefaulted.add(parameter);
      }

   }

   protected final void addInOutParameter(Parameter<?> parameter) {
      this.addInParameter(parameter);
      this.outParameters.add(parameter);
   }

   protected final void addOutParameter(Parameter<?> parameter) {
      this.addParameter(parameter);
      this.outParameters.add(parameter);
   }

   protected final void setReturnParameter(Parameter<T> parameter) {
      this.addParameter(parameter);
      this.returnParameter = parameter;
   }

   public final Field<T> asField() {
      if (this.function == null) {
         this.function = new AbstractRoutine.RoutineField();
      }

      return this.function;
   }

   public final Field<T> asField(String alias) {
      return this.asField().as(alias);
   }

   public final AggregateFunction<T> asAggregateFunction() {
      Field<?>[] array = new Field[this.getInParameters().size()];
      int i = 0;

      for(Iterator var3 = this.getInParameters().iterator(); var3.hasNext(); ++i) {
         Parameter<?> p = (Parameter)var3.next();
         array[i] = (Field)this.getInValues().get(p);
      }

      List<String> names = new ArrayList();
      if (this.schema != null) {
         names.add(this.schema.getName());
      }

      if (this.pkg != null) {
         names.add(this.pkg.getName());
      }

      names.add(this.name);
      return (AggregateFunction)DSL.function(DSL.name((String[])names.toArray(Tools.EMPTY_STRING)), this.type, array);
   }

   /** @deprecated */
   @Deprecated
   protected static final <T> Parameter<T> createParameter(String name, DataType<T> type) {
      return createParameter(name, type, false, (Converter)null, (Binding)null);
   }

   /** @deprecated */
   @Deprecated
   protected static final <T> Parameter<T> createParameter(String name, DataType<T> type, boolean isDefaulted) {
      return createParameter(name, type, isDefaulted, (Converter)null, (Binding)null);
   }

   /** @deprecated */
   @Deprecated
   protected static final <T, U> Parameter<U> createParameter(String name, DataType<T> type, boolean isDefaulted, Converter<T, U> converter) {
      return createParameter(name, type, isDefaulted, converter, (Binding)null);
   }

   /** @deprecated */
   @Deprecated
   protected static final <T, U> Parameter<U> createParameter(String name, DataType<T> type, boolean isDefaulted, Binding<T, U> binding) {
      return createParameter(name, type, isDefaulted, (Converter)null, binding);
   }

   /** @deprecated */
   @Deprecated
   protected static final <T, X, U> Parameter<U> createParameter(String name, DataType<T> type, boolean isDefaulted, Converter<X, U> converter, Binding<T, X> binding) {
      return createParameter(name, type, isDefaulted, false, converter, binding);
   }

   protected static final <T> Parameter<T> createParameter(String name, DataType<T> type, boolean isDefaulted, boolean isUnnamed) {
      return createParameter(name, type, isDefaulted, isUnnamed, (Converter)null, (Binding)null);
   }

   protected static final <T, U> Parameter<U> createParameter(String name, DataType<T> type, boolean isDefaulted, boolean isUnnamed, Converter<T, U> converter) {
      return createParameter(name, type, isDefaulted, isUnnamed, converter, (Binding)null);
   }

   protected static final <T, U> Parameter<U> createParameter(String name, DataType<T> type, boolean isDefaulted, boolean isUnnamed, Binding<T, U> binding) {
      return createParameter(name, type, isDefaulted, isUnnamed, (Converter)null, binding);
   }

   protected static final <T, X, U> Parameter<U> createParameter(String name, DataType<T> type, boolean isDefaulted, boolean isUnnamed, Converter<X, U> converter, Binding<T, X> binding) {
      Binding<T, U> actualBinding = DefaultBinding.newBinding(converter, type, binding);
      DataType<U> actualType = converter == null && binding == null ? type : type.asConvertedDataType(actualBinding);
      return new ParameterImpl(name, actualType, actualBinding, isDefaulted, isUnnamed);
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   static {
      CLAUSES = new Clause[]{Clause.FIELD, Clause.FIELD_FUNCTION};
   }

   private class RoutineField extends AbstractField<T> {
      private static final long serialVersionUID = -5730297947647252624L;

      RoutineField() {
         super(AbstractRoutine.this.getName(), AbstractRoutine.this.type == null ? SQLDataType.RESULT : AbstractRoutine.this.type);
      }

      public void accept(Context<?> ctx) {
         RenderContext local = this.create(ctx).renderContext();
         AbstractRoutine.this.toSQLQualifiedName(local);
         List<Field<?>> fields = new ArrayList();
         Iterator var4 = AbstractRoutine.this.getInParameters().iterator();

         while(var4.hasNext()) {
            Parameter<?> parameter = (Parameter)var4.next();
            if (!AbstractRoutine.this.inValuesDefaulted.contains(parameter)) {
               if (ctx.family() == SQLDialect.POSTGRES) {
                  if (AbstractRoutine.this.hasUnnamedParameters()) {
                     if (AbstractRoutine.this.pgArgNeedsCasting(parameter)) {
                        fields.add(new Cast((Field)AbstractRoutine.this.getInValues().get(parameter), parameter.getDataType()));
                     } else {
                        fields.add(AbstractRoutine.this.getInValues().get(parameter));
                     }
                  } else if (AbstractRoutine.this.pgArgNeedsCasting(parameter)) {
                     fields.add(DSL.field("{0} := {1}", DSL.name(parameter.getName()), new Cast((Field)AbstractRoutine.this.getInValues().get(parameter), parameter.getDataType())));
                  } else {
                     fields.add(DSL.field("{0} := {1}", DSL.name(parameter.getName()), (QueryPart)AbstractRoutine.this.getInValues().get(parameter)));
                  }
               } else {
                  fields.add(AbstractRoutine.this.getInValues().get(parameter));
               }
            }
         }

         Field<T> result = DSL.function(local.render(), this.getDataType(), (Field[])fields.toArray(Tools.EMPTY_FIELD));
         if (Boolean.TRUE.equals(Tools.settings(ctx.configuration()).isRenderScalarSubqueriesForStoredFunctions())) {
            result = DSL.select((SelectField)result).asField();
         }

         ctx.visit(result);
      }
   }
}
