package com.mysql.cj.core.io;

import com.mysql.cj.api.ProfilerEventHandler;
import com.mysql.cj.api.io.ValueFactory;
import com.mysql.cj.core.Constants;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.DataConversionException;
import com.mysql.cj.core.profiler.ProfilerEventImpl;
import com.mysql.cj.core.util.LogUtils;
import com.mysql.cj.core.util.StringUtils;

public class StringConverter<T> extends BaseDecoratingValueFactory<T> {
   private String encoding;
   private boolean emptyStringsConvertToZero = false;
   private ProfilerEventHandler eventSink;

   public StringConverter(String encoding, ValueFactory<T> targetVf) {
      super(targetVf);
      this.encoding = encoding;
   }

   public void setEmptyStringsConvertToZero(boolean val) {
      this.emptyStringsConvertToZero = val;
   }

   public void setEventSink(ProfilerEventHandler eventSink) {
      this.eventSink = eventSink;
   }

   private void issueConversionViaParsingWarning() {
      if (this.eventSink != null) {
         String message = Messages.getString("ResultSet.CostlyConversion", new Object[]{this.targetVf.getTargetTypeName(), -1, "<unknown>", "<unknown>", "<unknown>", "<unknown>", "<unknown>", "<unknown>"});
         this.eventSink.consumeEvent(new ProfilerEventImpl((byte)0, "", "<unknown>", -1L, -1, -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, (String)null, LogUtils.findCallingClassAndMethod(new Throwable()), message));
      }
   }

   public T createFromBytes(byte[] origBytes, int offset, int length) {
      MysqlTextValueDecoder stringInterpreter = new MysqlTextValueDecoder();
      String s = StringUtils.toString(origBytes, offset, length, this.encoding);
      byte[] bytes = s.getBytes();
      ValueFactory<T> vf = this.targetVf;
      this.issueConversionViaParsingWarning();
      if (s.length() == 0) {
         if (this.emptyStringsConvertToZero) {
            return this.targetVf.createFromLong(0L);
         }
      } else {
         if (s.equalsIgnoreCase("true")) {
            return vf.createFromLong(1L);
         }

         if (s.equalsIgnoreCase("false")) {
            return vf.createFromLong(0L);
         }

         if (s.contains("e") || s.contains("E") || s.matches("-?(\\d+)?\\.\\d+")) {
            return stringInterpreter.decodeDouble(bytes, 0, bytes.length, vf);
         }

         if (s.matches("-?\\d+")) {
            if (s.charAt(0) == '-') {
               return stringInterpreter.decodeInt8(bytes, 0, bytes.length, vf);
            }

            return stringInterpreter.decodeUInt8(bytes, 0, bytes.length, vf);
         }

         if (s.length() == 10 && s.charAt(4) == '-' && s.charAt(7) == '-') {
            return stringInterpreter.decodeDate(bytes, 0, bytes.length, vf);
         }

         if (s.length() >= 8 && s.length() <= 17 && s.charAt(2) == ':' && s.charAt(5) == ':') {
            return stringInterpreter.decodeTime(bytes, 0, bytes.length, vf);
         }

         if (s.length() >= 19 && (s.length() <= 26 || s.length() == 29) && s.charAt(4) == '-' && s.charAt(7) == '-' && s.charAt(10) == ' ' && s.charAt(13) == ':' && s.charAt(16) == ':') {
            return stringInterpreter.decodeTimestamp(bytes, 0, bytes.length, vf);
         }
      }

      throw new DataConversionException(Messages.getString("ResultSet.UnableToInterpretString", new Object[]{s}));
   }

   public T createFromBit(byte[] bytes, int offset, int length) {
      MysqlTextValueDecoder stringInterpreter = new MysqlTextValueDecoder();
      ValueFactory<T> vf = this.targetVf;
      return stringInterpreter.decodeBit(bytes, offset, length, vf);
   }
}
