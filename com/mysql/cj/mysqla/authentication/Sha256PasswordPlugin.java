package com.mysql.cj.mysqla.authentication;

import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.conf.ReadableProperty;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.io.Protocol;
import com.mysql.cj.api.mysqla.authentication.AuthenticationPlugin;
import com.mysql.cj.api.mysqla.io.NativeProtocol;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.authentication.Security;
import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.UnableToConnectException;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.core.io.ExportControlled;
import com.mysql.cj.core.util.StringUtils;
import com.mysql.cj.mysqla.io.Buffer;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Sha256PasswordPlugin implements AuthenticationPlugin {
   public static String PLUGIN_NAME = "sha256_password";
   private Protocol protocol;
   private String password = null;
   private String seed = null;
   private boolean publicKeyRequested = false;
   private String publicKeyString = null;
   private ReadableProperty<String> serverRSAPublicKeyFile = null;

   public void init(Protocol prot) {
      this.protocol = prot;
      this.serverRSAPublicKeyFile = this.protocol.getPropertySet().getStringReadableProperty("serverRSAPublicKeyFile");
      String pkURL = (String)this.serverRSAPublicKeyFile.getValue();
      if (pkURL != null) {
         this.publicKeyString = readRSAKey(pkURL, this.protocol.getPropertySet(), this.protocol.getExceptionInterceptor());
      }

   }

   public void destroy() {
      this.password = null;
      this.seed = null;
      this.publicKeyRequested = false;
   }

   public String getProtocolPluginName() {
      return PLUGIN_NAME;
   }

   public boolean requiresConfidentiality() {
      return false;
   }

   public boolean isReusable() {
      return true;
   }

   public void setAuthenticationParameters(String user, String password) {
      this.password = password;
   }

   public boolean nextAuthenticationStep(PacketPayload fromServer, List<PacketPayload> toServer) {
      toServer.clear();
      Buffer bresp;
      if (this.password != null && this.password.length() != 0 && fromServer != null) {
         try {
            if (this.protocol.getSocketConnection().isSSLEstablished()) {
               bresp = new Buffer(StringUtils.getBytes(this.password, this.protocol.getPasswordCharacterEncoding()));
               bresp.setPosition(bresp.getPayloadLength());
               bresp.writeInteger(NativeProtocol.IntegerDataType.INT1, 0L);
               bresp.setPosition(0);
               toServer.add(bresp);
            } else if (this.serverRSAPublicKeyFile.getValue() != null) {
               this.seed = fromServer.readString(NativeProtocol.StringSelfDataType.STRING_TERM, (String)null);
               bresp = new Buffer(encryptPassword(this.password, this.seed, this.publicKeyString, this.protocol.getPasswordCharacterEncoding()));
               toServer.add(bresp);
            } else {
               if (!(Boolean)this.protocol.getPropertySet().getBooleanReadableProperty("allowPublicKeyRetrieval").getValue()) {
                  throw (UnableToConnectException)ExceptionFactory.createException(UnableToConnectException.class, Messages.getString("Sha256PasswordPlugin.2"), this.protocol.getExceptionInterceptor());
               }

               if (this.publicKeyRequested && fromServer.getPayloadLength() > 20) {
                  bresp = new Buffer(encryptPassword(this.password, this.seed, fromServer.readString(NativeProtocol.StringSelfDataType.STRING_TERM, (String)null), this.protocol.getPasswordCharacterEncoding()));
                  toServer.add(bresp);
                  this.publicKeyRequested = false;
               } else {
                  this.seed = fromServer.readString(NativeProtocol.StringSelfDataType.STRING_TERM, (String)null);
                  bresp = new Buffer(new byte[]{1});
                  toServer.add(bresp);
                  this.publicKeyRequested = true;
               }
            }
         } catch (CJException var4) {
            throw ExceptionFactory.createException((String)var4.getMessage(), (Throwable)var4, (ExceptionInterceptor)this.protocol.getExceptionInterceptor());
         }
      } else {
         bresp = new Buffer(new byte[]{0});
         toServer.add(bresp);
      }

      return true;
   }

   private static byte[] encryptPassword(String password, String seed, String key, String passwordCharacterEncoding) {
      byte[] input = password != null ? StringUtils.getBytesNullTerminated(password, passwordCharacterEncoding) : new byte[]{0};
      byte[] mysqlScrambleBuff = new byte[input.length];
      Security.xorString(input, mysqlScrambleBuff, seed.getBytes(), input.length);
      return ExportControlled.encryptWithRSAPublicKey(mysqlScrambleBuff, ExportControlled.decodeRSAPublicKey(key));
   }

   private static String readRSAKey(String pkPath, PropertySet propertySet, ExceptionInterceptor exceptionInterceptor) {
      String res = null;
      byte[] fileBuf = new byte[2048];
      BufferedInputStream fileIn = null;

      try {
         File f = new File(pkPath);
         String canonicalPath = f.getCanonicalPath();
         fileIn = new BufferedInputStream(new FileInputStream(canonicalPath));
         int bytesRead = false;
         StringBuilder sb = new StringBuilder();

         int bytesRead;
         while((bytesRead = fileIn.read(fileBuf)) != -1) {
            sb.append(StringUtils.toAsciiString(fileBuf, 0, bytesRead));
         }

         res = sb.toString();
         return res;
      } catch (IOException var17) {
         throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("Sha256PasswordPlugin.0", (Boolean)propertySet.getBooleanReadableProperty("paranoid").getValue() ? new Object[]{""} : new Object[]{"'" + pkPath + "'"}), exceptionInterceptor);
      } finally {
         if (fileIn != null) {
            try {
               fileIn.close();
            } catch (IOException var16) {
               throw ExceptionFactory.createException((String)Messages.getString("Sha256PasswordPlugin.1"), (Throwable)var16, (ExceptionInterceptor)exceptionInterceptor);
            }
         }

      }
   }
}
