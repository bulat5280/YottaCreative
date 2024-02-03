package com.mysql.cj.mysqlx.result;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.mysql.cj.api.io.ValueFactory;
import com.mysql.cj.api.result.Row;
import com.mysql.cj.core.exceptions.DataReadException;
import com.mysql.cj.core.result.Field;
import com.mysql.cj.mysqlx.io.MysqlxDecoder;
import com.mysql.cj.mysqlx.protobuf.MysqlxResultset;
import java.io.IOException;
import java.util.ArrayList;

public class MysqlxRow implements Row {
   private ArrayList<Field> metadata;
   private MysqlxResultset.Row rowMessage;
   private boolean wasNull = false;

   public MysqlxRow(ArrayList<Field> metadata, MysqlxResultset.Row rowMessage) {
      this.metadata = metadata;
      this.rowMessage = rowMessage;
   }

   public <T> T getValue(int columnIndex, ValueFactory<T> vf) {
      if (columnIndex >= this.metadata.size()) {
         throw new DataReadException("Invalid column");
      } else {
         Field f = (Field)this.metadata.get(columnIndex);
         ByteString byteString = this.rowMessage.getField(columnIndex);

         try {
            if (byteString.size() == 0) {
               T result = vf.createFromNull();
               this.wasNull = result == null;
               return result;
            } else if (f.getMysqlTypeId() == 8 && f.isUnsigned()) {
               return MysqlxDecoder.instance.decodeUnsignedLong(CodedInputStream.newInstance(byteString.toByteArray()), vf);
            } else {
               MysqlxDecoder.DecoderFunction decoderFunction = (MysqlxDecoder.DecoderFunction)MysqlxDecoder.MYSQL_TYPE_TO_DECODER_FUNCTION.get(f.getMysqlTypeId());
               if (decoderFunction != null) {
                  this.wasNull = false;
                  return decoderFunction.apply(CodedInputStream.newInstance(byteString.toByteArray()), vf);
               } else {
                  throw new DataReadException("Unknown MySQL type constant: " + f.getMysqlTypeId());
               }
            }
         } catch (IOException var6) {
            throw new DataReadException(var6);
         }
      }
   }

   public boolean getNull(int columnIndex) {
      ByteString byteString = this.rowMessage.getField(columnIndex);
      this.wasNull = byteString.size() == 0;
      return this.wasNull;
   }

   public boolean wasNull() {
      return this.wasNull;
   }
}
