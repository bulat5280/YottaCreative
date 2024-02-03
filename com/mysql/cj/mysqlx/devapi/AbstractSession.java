package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.conf.ReadableProperty;
import com.mysql.cj.api.result.Row;
import com.mysql.cj.api.x.BaseSession;
import com.mysql.cj.api.x.Schema;
import com.mysql.cj.core.conf.PropertyDefinitions;
import com.mysql.cj.core.conf.url.ConnectionUrl;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.core.io.StringValueFactory;
import com.mysql.cj.core.util.StringUtils;
import com.mysql.cj.mysqlx.MysqlxError;
import com.mysql.cj.mysqlx.MysqlxSession;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractSession implements BaseSession {
   protected MysqlxSession session;
   protected String defaultSchemaName;

   public AbstractSession(Properties properties) {
      this.session = new MysqlxSession(properties);
      this.session.changeUser(properties.getProperty("user"), properties.getProperty("password"), properties.getProperty("DBNAME"));
      this.defaultSchemaName = properties.getProperty("DBNAME");
   }

   protected AbstractSession() {
   }

   public List<Schema> getSchemas() {
      Function<Row, String> rowToName = (r) -> {
         return (String)r.getValue(0, new StringValueFactory());
      };
      Function<Row, Schema> rowToSchema = rowToName.andThen((n) -> {
         return new SchemaImpl(this, n);
      });
      return (List)this.session.query("select schema_name from information_schema.schemata", rowToSchema, Collectors.toList());
   }

   public Schema getSchema(String schemaName) {
      return new SchemaImpl(this, schemaName);
   }

   public String getDefaultSchemaName() {
      return this.defaultSchemaName;
   }

   public Schema getDefaultSchema() {
      if (this.defaultSchemaName == null) {
         throw new WrongArgumentException("Default schema not provided");
      } else {
         return new SchemaImpl(this, this.defaultSchemaName);
      }
   }

   public Schema createSchema(String schemaName) {
      StringBuilder stmtString = new StringBuilder("CREATE DATABASE ");
      stmtString.append(StringUtils.quoteIdentifier(schemaName, true));
      this.session.update(stmtString.toString());
      return this.getSchema(schemaName);
   }

   public Schema createSchema(String schemaName, boolean reuseExistingObject) {
      try {
         return this.createSchema(schemaName);
      } catch (MysqlxError var4) {
         if (var4.getErrorCode() == 1007) {
            return this.getSchema(schemaName);
         } else {
            throw var4;
         }
      }
   }

   public void dropSchema(String schemaName) {
      StringBuilder stmtString = new StringBuilder("DROP DATABASE ");
      stmtString.append(StringUtils.quoteIdentifier(schemaName, true));
      this.session.update(stmtString.toString());
   }

   public void dropCollection(String schemaName, String collectionName) {
      this.session.dropCollection(schemaName, collectionName);
   }

   public void dropTable(String schemaName, String tableName) {
      this.session.dropCollection(schemaName, tableName);
   }

   public void startTransaction() {
      this.session.update("START TRANSACTION");
   }

   public void commit() {
      this.session.update("COMMIT");
   }

   public void rollback() {
      this.session.update("ROLLBACK");
   }

   public String getUri() {
      PropertySet pset = this.session.getPropertySet();
      StringBuilder sb = new StringBuilder(ConnectionUrl.Type.MYSQLX_SESSION.getProtocol());
      sb.append("//").append(this.session.getHost()).append(":").append(this.session.getPort()).append("/").append(this.defaultSchemaName).append("?");
      Iterator var3 = PropertyDefinitions.PROPERTY_NAME_TO_PROPERTY_DEFINITION.keySet().iterator();

      while(var3.hasNext()) {
         String propName = (String)var3.next();
         ReadableProperty<?> propToGet = pset.getReadableProperty(propName);
         String propValue = propToGet.getStringValue();
         if (propValue != null && !propValue.equals(propToGet.getPropertyDefinition().getDefaultValue().toString())) {
            sb.append(",");
            sb.append(propName);
            sb.append("=");
            sb.append(propValue);
         }
      }

      return sb.toString();
   }

   public boolean isOpen() {
      return this.session.isOpen();
   }

   public void close() {
      this.session.close();
   }

   public MysqlxSession getMysqlxSession() {
      return this.session;
   }
}
