package com.mysql.cj.api.io;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;

public interface SocketFactory extends SocketMetadata {
   Socket afterHandshake() throws SocketException, IOException;

   Socket beforeHandshake() throws SocketException, IOException;

   Socket connect(String var1, int var2, Properties var3, int var4) throws SocketException, IOException;
}
