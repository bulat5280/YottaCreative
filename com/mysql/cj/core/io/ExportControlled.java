package com.mysql.cj.core.io;

import com.mysql.cj.api.MysqlConnection;
import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.io.SocketConnection;
import com.mysql.cj.api.io.SocketFactory;
import com.mysql.cj.core.ServerVersion;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.FeatureNotAvailableException;
import com.mysql.cj.core.exceptions.RSAException;
import com.mysql.cj.core.exceptions.SSLParamsException;
import com.mysql.cj.core.util.Base64Decoder;
import com.mysql.cj.core.util.StringUtils;
import com.mysql.cj.core.util.Util;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class ExportControlled {
   public static boolean enabled() {
      return true;
   }

   public static void transformSocketToSSLSocket(SocketConnection socketConnection, ServerVersion serverVersion) throws IOException, SSLParamsException, FeatureNotAvailableException {
      SocketFactory sslFact = new ExportControlled.StandardSSLSocketFactory(getSSLSocketFactoryDefaultOrConfigured(socketConnection.getPropertySet(), socketConnection.getExceptionInterceptor()), socketConnection.getSocketFactory(), socketConnection.getMysqlSocket());
      socketConnection.setMysqlSocket(sslFact.connect(socketConnection.getHost(), socketConnection.getPort(), (Properties)null, 0));
      List<String> allowedProtocols = new ArrayList();
      List<String> supportedProtocols = Arrays.asList(((SSLSocket)socketConnection.getMysqlSocket()).getSupportedProtocols());
      String[] var5 = serverVersion.meetsMinimum(ServerVersion.parseVersion("5.6.0")) && Util.isEnterpriseEdition(serverVersion.toString()) ? new String[]{"TLSv1.2", "TLSv1.1", "TLSv1"} : new String[]{"TLSv1.1", "TLSv1"};
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String protocol = var5[var7];
         if (supportedProtocols.contains(protocol)) {
            allowedProtocols.add(protocol);
         }
      }

      ((SSLSocket)socketConnection.getMysqlSocket()).setEnabledProtocols((String[])allowedProtocols.toArray(new String[0]));
      String enabledSSLCipherSuites = (String)socketConnection.getPropertySet().getStringReadableProperty("enabledSSLCipherSuites").getValue();
      boolean overrideCiphers = enabledSSLCipherSuites != null && enabledSSLCipherSuites.length() > 0;
      int var10;
      ArrayList allowedCiphers;
      if (overrideCiphers) {
         allowedCiphers = new ArrayList();
         List<String> availableCiphers = Arrays.asList(((SSLSocket)socketConnection.getMysqlSocket()).getEnabledCipherSuites());
         String[] var9 = enabledSSLCipherSuites.split("\\s*,\\s*");
         var10 = var9.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            String cipher = var9[var11];
            if (availableCiphers.contains(cipher)) {
               allowedCiphers.add(cipher);
            }
         }

         ((SSLSocket)socketConnection.getMysqlSocket()).setEnabledCipherSuites((String[])allowedCiphers.toArray(new String[0]));
      } else if (!serverVersion.meetsMinimum(ServerVersion.parseVersion("5.7.6")) && (!serverVersion.meetsMinimum(ServerVersion.parseVersion("5.6.26")) || serverVersion.meetsMinimum(ServerVersion.parseVersion("5.7.0"))) && (!serverVersion.meetsMinimum(ServerVersion.parseVersion("5.5.45")) || serverVersion.meetsMinimum(ServerVersion.parseVersion("5.6.0")))) {
         allowedCiphers = new ArrayList();
         String[] var17 = ((SSLSocket)socketConnection.getMysqlSocket()).getEnabledCipherSuites();
         int var18 = var17.length;

         for(var10 = 0; var10 < var18; ++var10) {
            String cipher = var17[var10];
            if (cipher.indexOf("_DHE_") == -1 && cipher.indexOf("_DH_") == -1) {
               allowedCiphers.add(cipher);
            }
         }

         ((SSLSocket)socketConnection.getMysqlSocket()).setEnabledCipherSuites((String[])allowedCiphers.toArray(new String[0]));
      }

      ((SSLSocket)socketConnection.getMysqlSocket()).startHandshake();
      if ((Boolean)socketConnection.getPropertySet().getBooleanReadableProperty("useUnbufferedInput").getValue()) {
         socketConnection.setMysqlInput(socketConnection.getMysqlSocket().getInputStream());
      } else {
         socketConnection.setMysqlInput(new BufferedInputStream(socketConnection.getMysqlSocket().getInputStream(), 16384));
      }

      socketConnection.setMysqlOutput(new BufferedOutputStream(socketConnection.getMysqlSocket().getOutputStream(), 16384));
      socketConnection.getMysqlOutput().flush();
      socketConnection.setSocketFactory(sslFact);
   }

   private ExportControlled() {
   }

   private static SSLSocketFactory getSSLSocketFactoryDefaultOrConfigured(PropertySet propertySet, ExceptionInterceptor exceptionInterceptor) throws SSLParamsException {
      String clientCertificateKeyStoreUrl = (String)propertySet.getStringReadableProperty("clientCertificateKeyStoreUrl").getValue();
      String trustCertificateKeyStoreUrl = (String)propertySet.getStringReadableProperty("trustCertificateKeyStoreUrl").getValue();
      return StringUtils.isNullOrEmpty(clientCertificateKeyStoreUrl) && StringUtils.isNullOrEmpty(trustCertificateKeyStoreUrl) && (Boolean)propertySet.getBooleanReadableProperty("verifyServerCertificate").getValue() ? (SSLSocketFactory)SSLSocketFactory.getDefault() : getSSLContext(propertySet, exceptionInterceptor).getSocketFactory();
   }

   public static SSLContext getSSLContext(PropertySet propertySet, ExceptionInterceptor exceptionInterceptor) throws SSLParamsException {
      String clientCertificateKeyStoreUrl = (String)propertySet.getStringReadableProperty("clientCertificateKeyStoreUrl").getValue();
      String trustCertificateKeyStoreUrl = (String)propertySet.getStringReadableProperty("trustCertificateKeyStoreUrl").getValue();
      String clientCertificateKeyStoreType = (String)propertySet.getStringReadableProperty("clientCertificateKeyStoreType").getValue();
      String clientCertificateKeyStorePassword = (String)propertySet.getStringReadableProperty("clientCertificateKeyStorePassword").getValue();
      String trustCertificateKeyStoreType = (String)propertySet.getStringReadableProperty("trustCertificateKeyStoreType").getValue();
      String trustCertificateKeyStorePassword = (String)propertySet.getStringReadableProperty("trustCertificateKeyStorePassword").getValue();
      TrustManagerFactory tmf = null;
      KeyManagerFactory kmf = null;

      try {
         tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
         kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
      } catch (NoSuchAlgorithmException var72) {
         throw (SSLParamsException)ExceptionFactory.createException(SSLParamsException.class, "Default algorithm definitions for TrustManager and/or KeyManager are invalid.  Check java security properties file.", var72, exceptionInterceptor);
      }

      InputStream ksIS;
      KeyStore trustKeyStore;
      URL ksURL;
      char[] password;
      if (!StringUtils.isNullOrEmpty(clientCertificateKeyStoreUrl)) {
         ksIS = null;

         try {
            if (!StringUtils.isNullOrEmpty(clientCertificateKeyStoreType)) {
               trustKeyStore = KeyStore.getInstance(clientCertificateKeyStoreType);
               ksURL = new URL(clientCertificateKeyStoreUrl);
               password = clientCertificateKeyStorePassword == null ? new char[0] : clientCertificateKeyStorePassword.toCharArray();
               ksIS = ksURL.openStream();
               trustKeyStore.load(ksIS, password);
               kmf.init(trustKeyStore, password);
            }
         } catch (UnrecoverableKeyException var66) {
            throw (SSLParamsException)ExceptionFactory.createException(SSLParamsException.class, "Could not recover keys from client keystore.  Check password?", var66, exceptionInterceptor);
         } catch (NoSuchAlgorithmException var67) {
            throw (SSLParamsException)ExceptionFactory.createException(SSLParamsException.class, "Unsupported keystore algorithm [" + var67.getMessage() + "]", var67, exceptionInterceptor);
         } catch (KeyStoreException var68) {
            throw (SSLParamsException)ExceptionFactory.createException(SSLParamsException.class, "Could not create KeyStore instance [" + var68.getMessage() + "]", var68, exceptionInterceptor);
         } catch (CertificateException var69) {
            throw (SSLParamsException)ExceptionFactory.createException(SSLParamsException.class, "Could not load client" + clientCertificateKeyStoreType + " keystore from " + clientCertificateKeyStoreUrl, var69, exceptionInterceptor);
         } catch (MalformedURLException var70) {
            throw (SSLParamsException)ExceptionFactory.createException(SSLParamsException.class, clientCertificateKeyStoreUrl + " does not appear to be a valid URL.", var70, exceptionInterceptor);
         } catch (IOException var71) {
            throw (SSLParamsException)ExceptionFactory.createException(SSLParamsException.class, "Cannot open " + clientCertificateKeyStoreUrl + " [" + var71.getMessage() + "]", var71, exceptionInterceptor);
         } finally {
            if (ksIS != null) {
               try {
                  ksIS.close();
               } catch (IOException var58) {
               }
            }

         }
      }

      if (!StringUtils.isNullOrEmpty(trustCertificateKeyStoreUrl)) {
         ksIS = null;

         try {
            if (!StringUtils.isNullOrEmpty(trustCertificateKeyStoreType)) {
               trustKeyStore = KeyStore.getInstance(trustCertificateKeyStoreType);
               ksURL = new URL(trustCertificateKeyStoreUrl);
               password = trustCertificateKeyStorePassword == null ? new char[0] : trustCertificateKeyStorePassword.toCharArray();
               ksIS = ksURL.openStream();
               trustKeyStore.load(ksIS, password);
               tmf.init(trustKeyStore);
            }
         } catch (NoSuchAlgorithmException var61) {
            throw (SSLParamsException)ExceptionFactory.createException(SSLParamsException.class, "Unsupported keystore algorithm [" + var61.getMessage() + "]", var61, exceptionInterceptor);
         } catch (KeyStoreException var62) {
            throw (SSLParamsException)ExceptionFactory.createException(SSLParamsException.class, "Could not create KeyStore instance [" + var62.getMessage() + "]", var62, exceptionInterceptor);
         } catch (CertificateException var63) {
            throw (SSLParamsException)ExceptionFactory.createException(SSLParamsException.class, "Could not load trust" + trustCertificateKeyStoreType + " keystore from " + trustCertificateKeyStoreUrl, var63, exceptionInterceptor);
         } catch (MalformedURLException var64) {
            throw (SSLParamsException)ExceptionFactory.createException(SSLParamsException.class, trustCertificateKeyStoreUrl + " does not appear to be a valid URL.", var64, exceptionInterceptor);
         } catch (IOException var65) {
            throw (SSLParamsException)ExceptionFactory.createException(SSLParamsException.class, "Cannot open " + trustCertificateKeyStoreUrl + " [" + var65.getMessage() + "]", var65, exceptionInterceptor);
         } finally {
            if (ksIS != null) {
               try {
                  ksIS.close();
               } catch (IOException var57) {
               }
            }

         }
      }

      ksIS = null;

      try {
         SSLContext sslContext = SSLContext.getInstance("TLS");
         sslContext.init(StringUtils.isNullOrEmpty(clientCertificateKeyStoreUrl) ? null : kmf.getKeyManagers(), (TrustManager[])((Boolean)propertySet.getBooleanReadableProperty("verifyServerCertificate").getValue() ? tmf.getTrustManagers() : new X509TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
               return null;
            }
         }}), (SecureRandom)null);
         return sslContext;
      } catch (NoSuchAlgorithmException var59) {
         throw new SSLParamsException("TLS is not a valid SSL protocol.", var59);
      } catch (KeyManagementException var60) {
         throw new SSLParamsException("KeyManagementException: " + var60.getMessage(), var60);
      }
   }

   public static boolean isSSLEstablished(Socket socket) {
      return SSLSocket.class.isAssignableFrom(socket.getClass());
   }

   public static RSAPublicKey decodeRSAPublicKey(String key) throws RSAException {
      if (key == null) {
         throw (RSAException)ExceptionFactory.createException(RSAException.class, "Key parameter is null");
      } else {
         int offset = key.indexOf("\n") + 1;
         int len = key.indexOf("-----END PUBLIC KEY-----") - offset;
         byte[] certificateData = Base64Decoder.decode(key.getBytes(), offset, len);
         X509EncodedKeySpec spec = new X509EncodedKeySpec(certificateData);

         try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return (RSAPublicKey)kf.generatePublic(spec);
         } catch (InvalidKeySpecException | NoSuchAlgorithmException var6) {
            throw (RSAException)ExceptionFactory.createException((Class)RSAException.class, (String)"Unable to decode public key", (Throwable)var6);
         }
      }
   }

   public static byte[] encryptWithRSAPublicKey(byte[] source, RSAPublicKey key) throws RSAException {
      try {
         Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
         cipher.init(1, key);
         return cipher.doFinal(source);
      } catch (NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException var3) {
         throw (RSAException)ExceptionFactory.createException((Class)RSAException.class, (String)var3.getMessage(), (Throwable)var3);
      }
   }

   public static class StandardSSLSocketFactory implements SocketFactory {
      private SSLSocket rawSocket = null;
      private final SSLSocketFactory sslFact;
      private final SocketFactory existingSocketFactory;
      private final Socket existingSocket;

      public StandardSSLSocketFactory(SSLSocketFactory sslFact, SocketFactory existingSocketFactory, Socket existingSocket) {
         this.sslFact = sslFact;
         this.existingSocketFactory = existingSocketFactory;
         this.existingSocket = existingSocket;
      }

      public Socket afterHandshake() throws SocketException, IOException {
         this.existingSocketFactory.afterHandshake();
         return this.rawSocket;
      }

      public Socket beforeHandshake() throws SocketException, IOException {
         return this.rawSocket;
      }

      public Socket connect(String host, int portNumber, Properties props, int loginTimeout) throws SocketException, IOException {
         this.rawSocket = (SSLSocket)this.sslFact.createSocket(this.existingSocket, host, portNumber, true);
         return this.rawSocket;
      }

      public boolean isLocallyConnected(MysqlConnection conn) {
         return this.existingSocketFactory.isLocallyConnected(conn);
      }
   }
}
