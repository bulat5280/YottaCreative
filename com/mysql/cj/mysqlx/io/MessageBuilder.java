package com.mysql.cj.mysqlx.io;

import com.google.protobuf.ByteString;
import com.mysql.cj.core.authentication.Security;
import com.mysql.cj.core.util.StringUtils;
import com.mysql.cj.mysqlx.CreateIndexParams;
import com.mysql.cj.mysqlx.ExprUtil;
import com.mysql.cj.mysqlx.FilterParams;
import com.mysql.cj.mysqlx.FindParams;
import com.mysql.cj.mysqlx.InsertParams;
import com.mysql.cj.mysqlx.UpdateParams;
import com.mysql.cj.mysqlx.UpdateSpec;
import com.mysql.cj.mysqlx.protobuf.MysqlxConnection;
import com.mysql.cj.mysqlx.protobuf.MysqlxCrud;
import com.mysql.cj.mysqlx.protobuf.MysqlxDatatypes;
import com.mysql.cj.mysqlx.protobuf.MysqlxExpr;
import com.mysql.cj.mysqlx.protobuf.MysqlxSession;
import com.mysql.cj.mysqlx.protobuf.MysqlxSql;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.Sasl;
import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslException;

public class MessageBuilder {
   private static final String XPLUGIN_NAMESPACE = "mysqlx";

   public MysqlxConnection.CapabilitiesSet buildCapabilitiesSet(String name, Object value) {
      MysqlxDatatypes.Any v = ExprUtil.argObjectToScalarAny(value);
      MysqlxConnection.Capability cap = MysqlxConnection.Capability.newBuilder().setName(name).setValue(v).build();
      MysqlxConnection.Capabilities caps = MysqlxConnection.Capabilities.newBuilder().addCapabilities(cap).build();
      return MysqlxConnection.CapabilitiesSet.newBuilder().setCapabilities(caps).build();
   }

   public MysqlxSql.StmtExecute buildCreateCollectionIndex(String schemaName, String collectionName, CreateIndexParams params) {
      MysqlxDatatypes.Object.Builder builder = MysqlxDatatypes.Object.newBuilder();
      builder.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("name").setValue(ExprUtil.buildAny(params.getIndexName()))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("collection").setValue(ExprUtil.buildAny(collectionName))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("schema").setValue(ExprUtil.buildAny(schemaName))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("unique").setValue(ExprUtil.buildAny(params.isUnique())));
      MysqlxDatatypes.Array.Builder abuilder = MysqlxDatatypes.Array.newBuilder();

      for(int i = 0; i < params.getDocPaths().size(); ++i) {
         abuilder.addValue(MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("member").setValue(ExprUtil.buildAny("$" + (String)params.getDocPaths().get(i)))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("required").setValue(ExprUtil.buildAny((Boolean)params.getNotNulls().get(i)))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("type").setValue(ExprUtil.buildAny((String)params.getTypes().get(i))))));
      }

      builder.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("constraint").setValue(MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.ARRAY).setArray(abuilder)));
      return this.buildXpluginCommand(MessageBuilder.XpluginStatementCommand.XPLUGIN_STMT_CREATE_COLLECTION_INDEX, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(builder).build());
   }

   public MysqlxSql.StmtExecute buildDropCollectionIndex(String schemaName, String collectionName, String indexName) {
      return this.buildXpluginCommand(MessageBuilder.XpluginStatementCommand.XPLUGIN_STMT_DROP_COLLECTION_INDEX, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("name").setValue(ExprUtil.buildAny(indexName))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("collection").setValue(ExprUtil.buildAny(collectionName))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("schema").setValue(ExprUtil.buildAny(schemaName)))).build());
   }

   public MysqlxSql.StmtExecute buildXpluginCommand(MessageBuilder.XpluginStatementCommand command, MysqlxDatatypes.Any... args) {
      MysqlxSql.StmtExecute.Builder builder = MysqlxSql.StmtExecute.newBuilder();
      builder.setNamespace("mysqlx");
      builder.setStmt(ByteString.copyFromUtf8(command.commandName));
      Arrays.stream(args).forEach((a) -> {
         builder.addArgs(a);
      });
      return builder.build();
   }

   public MysqlxSql.StmtExecute buildSqlStatement(String statement, List<MysqlxDatatypes.Any> args) {
      MysqlxSql.StmtExecute.Builder builder = MysqlxSql.StmtExecute.newBuilder();
      if (args != null) {
         builder.addAllArgs(args);
      }

      builder.setStmt(ByteString.copyFromUtf8(statement));
      return builder.build();
   }

   public MysqlxCrud.Find buildFind(FindParams findParams) {
      MysqlxCrud.Find.Builder builder = MysqlxCrud.Find.newBuilder().setCollection((MysqlxCrud.Collection)findParams.getCollection());
      builder.setDataModel(findParams.isRelational() ? MysqlxCrud.DataModel.TABLE : MysqlxCrud.DataModel.DOCUMENT);
      if (findParams.getFields() != null) {
         builder.addAllProjection((List)findParams.getFields());
      }

      if (findParams.getGrouping() != null) {
         builder.addAllGrouping((List)findParams.getGrouping());
      }

      if (findParams.getGroupingCriteria() != null) {
         builder.setGroupingCriteria((MysqlxExpr.Expr)findParams.getGroupingCriteria());
      }

      applyFilterParams(findParams, builder::addAllOrder, builder::setLimit, builder::setCriteria, builder::addAllArgs);
      return builder.build();
   }

   public MysqlxCrud.Update buildDocUpdate(FilterParams filterParams, List<UpdateSpec> updates) {
      MysqlxCrud.Update.Builder builder = MysqlxCrud.Update.newBuilder().setCollection((MysqlxCrud.Collection)filterParams.getCollection());
      updates.forEach((u) -> {
         MysqlxCrud.UpdateOperation.Builder opBuilder = MysqlxCrud.UpdateOperation.newBuilder();
         opBuilder.setOperation((MysqlxCrud.UpdateOperation.UpdateType)u.getUpdateType());
         opBuilder.setSource((MysqlxExpr.ColumnIdentifier)u.getSource());
         if (u.getValue() != null) {
            opBuilder.setValue((MysqlxExpr.Expr)u.getValue());
         }

         builder.addOperation(opBuilder.build());
      });
      applyFilterParams(filterParams, builder::addAllOrder, builder::setLimit, builder::setCriteria, builder::addAllArgs);
      return builder.build();
   }

   public MysqlxCrud.Update buildRowUpdate(FilterParams filterParams, UpdateParams updateParams) {
      MysqlxCrud.Update.Builder builder = MysqlxCrud.Update.newBuilder().setDataModel(MysqlxCrud.DataModel.TABLE).setCollection((MysqlxCrud.Collection)filterParams.getCollection());
      ((Map)updateParams.getUpdates()).entrySet().stream().map((e) -> {
         return MysqlxCrud.UpdateOperation.newBuilder().setOperation(MysqlxCrud.UpdateOperation.UpdateType.SET).setSource((MysqlxExpr.ColumnIdentifier)e.getKey()).setValue((MysqlxExpr.Expr)e.getValue()).build();
      }).forEach(builder::addOperation);
      applyFilterParams(filterParams, builder::addAllOrder, builder::setLimit, builder::setCriteria, builder::addAllArgs);
      return builder.build();
   }

   public MysqlxCrud.Delete buildDelete(FilterParams filterParams) {
      MysqlxCrud.Delete.Builder builder = MysqlxCrud.Delete.newBuilder().setCollection((MysqlxCrud.Collection)filterParams.getCollection());
      applyFilterParams(filterParams, builder::addAllOrder, builder::setLimit, builder::setCriteria, builder::addAllArgs);
      return builder.build();
   }

   public MysqlxCrud.Insert buildDocInsert(String schemaName, String collectionName, List<String> json) {
      MysqlxCrud.Insert.Builder builder = MysqlxCrud.Insert.newBuilder().setCollection(ExprUtil.buildCollection(schemaName, collectionName));
      json.stream().map((str) -> {
         return MysqlxCrud.Insert.TypedRow.newBuilder().addField(ExprUtil.argObjectToExpr(str, false)).build();
      }).forEach(builder::addRow);
      return builder.build();
   }

   public MysqlxCrud.Insert buildRowInsert(String schemaName, String tableName, InsertParams insertParams) {
      MysqlxCrud.Insert.Builder builder = MysqlxCrud.Insert.newBuilder().setDataModel(MysqlxCrud.DataModel.TABLE).setCollection(ExprUtil.buildCollection(schemaName, tableName));
      if (insertParams.getProjection() != null) {
         builder.addAllProjection((List)insertParams.getProjection());
      }

      builder.addAllRow((List)insertParams.getRows());
      return builder.build();
   }

   private static void applyFilterParams(FilterParams filterParams, Consumer<List<MysqlxCrud.Order>> setOrder, Consumer<MysqlxCrud.Limit> setLimit, Consumer<MysqlxExpr.Expr> setCriteria, Consumer<List<MysqlxDatatypes.Scalar>> setArgs) {
      filterParams.verifyAllArgsBound();
      if (filterParams.getOrder() != null) {
         setOrder.accept((List)filterParams.getOrder());
      }

      if (filterParams.getLimit() != null) {
         MysqlxCrud.Limit.Builder lb = MysqlxCrud.Limit.newBuilder().setRowCount(filterParams.getLimit());
         if (filterParams.getOffset() != null) {
            lb.setOffset(filterParams.getOffset());
         }

         setLimit.accept(lb.build());
      }

      if (filterParams.getCriteria() != null) {
         setCriteria.accept((MysqlxExpr.Expr)filterParams.getCriteria());
      }

      if (filterParams.getArgs() != null) {
         setArgs.accept((List)filterParams.getArgs());
      }

   }

   public MysqlxSession.AuthenticateContinue buildMysql41AuthContinue(String user, String password, byte[] salt, String database) {
      String encoding = "UTF8";
      byte[] userBytes = user == null ? new byte[0] : StringUtils.getBytes(user, encoding);
      byte[] passwordBytes = password != null && password.length() != 0 ? StringUtils.getBytes(password, encoding) : new byte[0];
      byte[] databaseBytes = database == null ? new byte[0] : StringUtils.getBytes(database, encoding);
      byte[] hashedPassword = passwordBytes;
      if (password != null && password.length() > 0) {
         hashedPassword = Security.scramble411(passwordBytes, salt);
         hashedPassword = String.format("*%040x", new BigInteger(1, hashedPassword)).getBytes();
      }

      byte[] reply = new byte[databaseBytes.length + userBytes.length + hashedPassword.length + 2];
      System.arraycopy(databaseBytes, 0, reply, 0, databaseBytes.length);
      int pos = databaseBytes.length;
      reply[pos++] = 0;
      System.arraycopy(userBytes, 0, reply, pos, userBytes.length);
      pos += userBytes.length;
      reply[pos++] = 0;
      System.arraycopy(hashedPassword, 0, reply, pos, hashedPassword.length);
      MysqlxSession.AuthenticateContinue.Builder builder = MysqlxSession.AuthenticateContinue.newBuilder();
      builder.setAuthData(ByteString.copyFrom(reply));
      return builder.build();
   }

   public MysqlxSession.AuthenticateStart buildPlainAuthStart(final String user, final String password, String database) {
      CallbackHandler callbackHandler = new CallbackHandler() {
         public void handle(Callback[] callbacks) throws UnsupportedCallbackException {
            Callback[] var2 = callbacks;
            int var3 = callbacks.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               Callback c = var2[var4];
               if (NameCallback.class.isAssignableFrom(c.getClass())) {
                  ((NameCallback)c).setName(user);
               } else {
                  if (!PasswordCallback.class.isAssignableFrom(c.getClass())) {
                     throw new UnsupportedCallbackException(c);
                  }

                  ((PasswordCallback)c).setPassword(password.toCharArray());
               }
            }

         }
      };

      try {
         String[] mechanisms = new String[]{"PLAIN"};
         String protocol = "X Protocol";
         Map<String, ?> props = null;
         String serverName = "<unknown>";
         SaslClient saslClient = Sasl.createSaslClient(mechanisms, database, protocol, serverName, (Map)props, callbackHandler);
         MysqlxSession.AuthenticateStart.Builder authStartBuilder = MysqlxSession.AuthenticateStart.newBuilder();
         authStartBuilder.setMechName("PLAIN");
         authStartBuilder.setAuthData(ByteString.copyFrom(saslClient.evaluateChallenge((byte[])null)));
         return authStartBuilder.build();
      } catch (SaslException var12) {
         throw new RuntimeException(var12);
      }
   }

   public static enum XpluginStatementCommand {
      XPLUGIN_STMT_CREATE_COLLECTION("create_collection"),
      XPLUGIN_STMT_CREATE_COLLECTION_INDEX("create_collection_index"),
      XPLUGIN_STMT_DROP_COLLECTION("drop_collection"),
      XPLUGIN_STMT_DROP_COLLECTION_INDEX("drop_collection_index"),
      XPLUGIN_STMT_PING("ping"),
      XPLUGIN_STMT_LIST_OBJECTS("list_objects"),
      XPLUGIN_STMT_ENABLE_NOTICES("enable_notices"),
      XPLUGIN_STMT_DISABLE_NOTICES("disable_notices"),
      XPLUGIN_STMT_LIST_NOTICES("list_notices");

      public String commandName;

      private XpluginStatementCommand(String commandName) {
         this.commandName = commandName;
      }
   }
}
