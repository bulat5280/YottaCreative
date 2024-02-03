package ua.govnojon.libs.myjava;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamUtil {
   public static void writePrimitiveObject(ObjectOutputStream stream, Object o) throws IOException {
      if (o instanceof String) {
         stream.writeUTF((String)o);
      } else if (o instanceof Integer) {
         stream.writeInt((Integer)o);
      } else if (o instanceof Double) {
         stream.writeDouble((Double)o);
      } else if (o instanceof Float) {
         stream.writeFloat((Float)o);
      } else if (o instanceof Character) {
         stream.writeChar((Character)o);
      } else if (o instanceof Boolean) {
         stream.writeBoolean((Boolean)o);
      } else if (o instanceof Long) {
         stream.writeLong((Long)o);
      } else if (o instanceof Short) {
         stream.writeShort((Short)o);
      } else if (o instanceof Byte) {
         stream.writeByte((Byte)o);
      } else {
         stream.writeObject(o);
      }

   }
}
