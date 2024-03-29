package com.mysql.cj.mysqlx.io;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.MessageLite;
import com.google.protobuf.Parser;
import com.mysql.cj.mysqlx.protobuf.Mysqlx;
import com.mysql.cj.mysqlx.protobuf.MysqlxConnection;
import com.mysql.cj.mysqlx.protobuf.MysqlxCrud;
import com.mysql.cj.mysqlx.protobuf.MysqlxNotice;
import com.mysql.cj.mysqlx.protobuf.MysqlxResultset;
import com.mysql.cj.mysqlx.protobuf.MysqlxSession;
import com.mysql.cj.mysqlx.protobuf.MysqlxSql;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MessageConstants {
   public static final Map<Class<? extends GeneratedMessage>, Parser<? extends GeneratedMessage>> MESSAGE_CLASS_TO_PARSER;
   public static final Map<Class<? extends GeneratedMessage>, Integer> MESSAGE_CLASS_TO_TYPE;
   public static final Map<Integer, Class<? extends GeneratedMessage>> MESSAGE_TYPE_TO_CLASS;
   public static final Map<Class<? extends MessageLite>, Integer> MESSAGE_CLASS_TO_CLIENT_MESSAGE_TYPE;

   static {
      Map<Class<? extends GeneratedMessage>, Parser<? extends GeneratedMessage>> messageClassToParser = new HashMap();
      Map<Class<? extends GeneratedMessage>, Integer> messageClassToType = new HashMap();
      Map<Integer, Class<? extends GeneratedMessage>> messageTypeToClass = new HashMap();
      messageClassToParser.put(Mysqlx.Error.class, Mysqlx.Error.getDefaultInstance().getParserForType());
      messageClassToParser.put(Mysqlx.Ok.class, Mysqlx.Ok.getDefaultInstance().getParserForType());
      messageClassToParser.put(MysqlxSession.AuthenticateContinue.class, MysqlxSession.AuthenticateContinue.getDefaultInstance().getParserForType());
      messageClassToParser.put(MysqlxSession.AuthenticateOk.class, MysqlxSession.AuthenticateOk.getDefaultInstance().getParserForType());
      messageClassToParser.put(MysqlxConnection.Capabilities.class, MysqlxConnection.Capabilities.getDefaultInstance().getParserForType());
      messageClassToParser.put(MysqlxResultset.ColumnMetaData.class, MysqlxResultset.ColumnMetaData.getDefaultInstance().getParserForType());
      messageClassToParser.put(MysqlxResultset.FetchDone.class, MysqlxResultset.FetchDone.getDefaultInstance().getParserForType());
      messageClassToParser.put(MysqlxResultset.FetchDoneMoreResultsets.class, MysqlxResultset.FetchDoneMoreResultsets.getDefaultInstance().getParserForType());
      messageClassToParser.put(MysqlxNotice.Frame.class, MysqlxNotice.Frame.getDefaultInstance().getParserForType());
      messageClassToParser.put(MysqlxResultset.Row.class, MysqlxResultset.Row.getDefaultInstance().getParserForType());
      messageClassToParser.put(MysqlxSql.StmtExecuteOk.class, MysqlxSql.StmtExecuteOk.getDefaultInstance().getParserForType());
      messageClassToParser.put(MysqlxNotice.SessionStateChanged.class, MysqlxNotice.SessionStateChanged.getDefaultInstance().getParserForType());
      messageClassToParser.put(MysqlxNotice.SessionVariableChanged.class, MysqlxNotice.SessionVariableChanged.getDefaultInstance().getParserForType());
      messageClassToParser.put(MysqlxNotice.Warning.class, MysqlxNotice.Warning.getDefaultInstance().getParserForType());
      messageClassToType.put(Mysqlx.Error.class, 1);
      messageClassToType.put(Mysqlx.Ok.class, 0);
      messageClassToType.put(MysqlxSession.AuthenticateContinue.class, 3);
      messageClassToType.put(MysqlxSession.AuthenticateOk.class, 4);
      messageClassToType.put(MysqlxConnection.Capabilities.class, 2);
      messageClassToType.put(MysqlxResultset.ColumnMetaData.class, 12);
      messageClassToType.put(MysqlxResultset.FetchDone.class, 14);
      messageClassToType.put(MysqlxResultset.FetchDoneMoreResultsets.class, 16);
      messageClassToType.put(MysqlxNotice.Frame.class, 11);
      messageClassToType.put(MysqlxResultset.Row.class, 13);
      messageClassToType.put(MysqlxSql.StmtExecuteOk.class, 17);
      Iterator var3 = messageClassToType.entrySet().iterator();

      while(var3.hasNext()) {
         Entry<Class<? extends GeneratedMessage>, Integer> entry = (Entry)var3.next();
         messageTypeToClass.put(entry.getValue(), entry.getKey());
      }

      MESSAGE_CLASS_TO_PARSER = Collections.unmodifiableMap(messageClassToParser);
      MESSAGE_CLASS_TO_TYPE = Collections.unmodifiableMap(messageClassToType);
      MESSAGE_TYPE_TO_CLASS = Collections.unmodifiableMap(messageTypeToClass);
      Map<Class<? extends MessageLite>, Integer> messageClassToClientMessageType = new HashMap();
      messageClassToClientMessageType.put(MysqlxSession.AuthenticateStart.class, 4);
      messageClassToClientMessageType.put(MysqlxSession.AuthenticateContinue.class, 5);
      messageClassToClientMessageType.put(MysqlxConnection.CapabilitiesGet.class, 1);
      messageClassToClientMessageType.put(MysqlxConnection.CapabilitiesSet.class, 2);
      messageClassToClientMessageType.put(MysqlxSession.Close.class, 7);
      messageClassToClientMessageType.put(MysqlxCrud.Delete.class, 20);
      messageClassToClientMessageType.put(MysqlxCrud.Find.class, 17);
      messageClassToClientMessageType.put(MysqlxCrud.Insert.class, 18);
      messageClassToClientMessageType.put(MysqlxSession.Reset.class, 6);
      messageClassToClientMessageType.put(MysqlxSql.StmtExecute.class, 12);
      messageClassToClientMessageType.put(MysqlxCrud.Update.class, 19);
      MESSAGE_CLASS_TO_CLIENT_MESSAGE_TYPE = Collections.unmodifiableMap(messageClassToClientMessageType);
   }
}
