package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.mysqla.io.ProtocolEntityFactory;
import com.mysql.cj.api.mysqla.result.ProtocolEntity;
import com.mysql.cj.api.mysqla.result.Resultset;
import com.mysql.cj.api.mysqla.result.ResultsetRows;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.mysqla.result.MysqlaResultset;
import com.mysql.cj.mysqla.result.OkPacket;

public class ResultsetFactory implements ProtocolEntityFactory<Resultset> {
   private Resultset.Type type;
   private Resultset.Concurrency concurrency;

   public ResultsetFactory(Resultset.Type type, Resultset.Concurrency concurrency) {
      this.type = Resultset.Type.FORWARD_ONLY;
      this.concurrency = Resultset.Concurrency.READ_ONLY;
      this.type = type;
      this.concurrency = concurrency;
   }

   public Resultset.Type getResultSetType() {
      return this.type;
   }

   public Resultset.Concurrency getResultSetConcurrency() {
      return this.concurrency;
   }

   public Resultset createFromProtocolEntity(ProtocolEntity protocolEntity) {
      if (protocolEntity instanceof OkPacket) {
         return new MysqlaResultset((OkPacket)protocolEntity);
      } else if (protocolEntity instanceof ResultsetRows) {
         return new MysqlaResultset((ResultsetRows)protocolEntity);
      } else {
         throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, "Unknown ProtocolEntity class " + protocolEntity);
      }
   }
}
