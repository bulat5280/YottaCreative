package com.mysql.cj.mysqla.authentication;

import com.mysql.cj.api.authentication.AuthenticationProvider;
import com.mysql.cj.api.conf.ModifiableProperty;
import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.io.Protocol;
import com.mysql.cj.api.io.ServerSession;
import com.mysql.cj.api.log.Log;
import com.mysql.cj.api.mysqla.authentication.AuthenticationPlugin;
import com.mysql.cj.api.mysqla.io.NativeProtocol;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.core.Constants;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.core.util.StringUtils;
import com.mysql.cj.mysqla.io.Buffer;
import com.mysql.cj.mysqla.io.MysqlaCapabilities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class MysqlaAuthenticationProvider implements AuthenticationProvider {
   protected static final int AUTH_411_OVERHEAD = 33;
   private static final String NONE = "none";
   protected String seed;
   private boolean useConnectWithDb;
   private ExceptionInterceptor exceptionInterceptor;
   private PropertySet propertySet;
   private NativeProtocol protocol;
   private Log log;
   private Map<String, AuthenticationPlugin> authenticationPlugins = null;
   private List<String> disabledAuthenticationPlugins = null;
   private String clientDefaultAuthenticationPlugin = null;
   private String clientDefaultAuthenticationPluginName = null;
   private String serverDefaultAuthenticationPluginName = null;

   public MysqlaAuthenticationProvider(Log log) {
      this.log = log;
   }

   public void init(Protocol prot, PropertySet propSet, ExceptionInterceptor excInterceptor) {
      this.protocol = (NativeProtocol)prot;
      this.propertySet = propSet;
      this.exceptionInterceptor = excInterceptor;
   }

   public void connect(ServerSession sessState, String user, String password, String database) {
      long clientParam = sessState.getClientParam();
      MysqlaCapabilities capabilities = (MysqlaCapabilities)sessState.getCapabilities();
      PacketPayload buf = capabilities.getInitialHandshakePacket();
      this.seed = capabilities.getSeed();
      sessState.setServerDefaultCollationIndex(capabilities.getServerDefaultCollationIndex());
      sessState.setStatusFlags(capabilities.getStatusFlags());
      int capabilityFlags = capabilities.getCapabilityFlags();
      if ((capabilityFlags & '耀') != 0) {
         clientParam |= 32768L;
         int authPluginDataLength = capabilities.getAuthPluginDataLength();
         String seedPart2;
         StringBuilder newSeed;
         if (authPluginDataLength > 0) {
            seedPart2 = buf.readString(NativeProtocol.StringLengthDataType.STRING_FIXED, "ASCII", authPluginDataLength - 8);
            newSeed = new StringBuilder(authPluginDataLength);
         } else {
            seedPart2 = buf.readString(NativeProtocol.StringSelfDataType.STRING_TERM, "ASCII");
            newSeed = new StringBuilder(20);
         }

         newSeed.append(this.seed);
         newSeed.append(seedPart2);
         this.seed = newSeed.toString();
      } else {
         this.protocol.rejectConnection("CLIENT_SECURE_CONNECTION is required");
      }

      if ((capabilityFlags & 32) != 0 && (Boolean)this.propertySet.getBooleanReadableProperty("useCompression").getValue()) {
         clientParam |= 32L;
      }

      this.useConnectWithDb = database != null && database.length() > 0 && !(Boolean)this.propertySet.getBooleanReadableProperty("createDatabaseIfNotExist").getValue();
      if (this.useConnectWithDb) {
         clientParam |= 8L;
      }

      ModifiableProperty<Boolean> useSSL = this.propertySet.getModifiableProperty("useSSL");
      if (this.protocol.versionMeetsMinimum(5, 7, 0) && !(Boolean)useSSL.getValue() && !useSSL.isExplicitlySet()) {
         useSSL.setValue(true);
         this.propertySet.getModifiableProperty("verifyServerCertificate").setValue(false);
         if (this.log != null) {
            this.log.logWarn(Messages.getString("MysqlIO.SSLWarning"));
         }
      }

      if ((capabilityFlags & 2048) == 0 && (Boolean)useSSL.getValue()) {
         if ((Boolean)this.propertySet.getBooleanReadableProperty("requireSSL").getValue()) {
            this.protocol.rejectConnection(Messages.getString("MysqlIO.15"));
         }

         useSSL.setValue(false);
      }

      if ((capabilityFlags & 4) != 0) {
         clientParam |= 4L;
         sessState.setHasLongColumnInfo(true);
      }

      if (!(Boolean)this.propertySet.getBooleanReadableProperty("useAffectedRows").getValue()) {
         clientParam |= 2L;
      }

      if ((Boolean)this.propertySet.getBooleanReadableProperty("allowLoadLocalInfile").getValue()) {
         clientParam |= 128L;
      }

      if ((Boolean)this.propertySet.getBooleanReadableProperty("interactiveClient").getValue()) {
         clientParam |= 1024L;
      }

      if ((capabilityFlags & 8388608) != 0) {
      }

      if ((capabilityFlags & 16777216) != 0) {
         clientParam |= 16777216L;
      }

      if ((capabilityFlags & 524288) != 0) {
         sessState.setClientParam(clientParam);
         this.proceedHandshakeWithPluggableAuthentication(sessState, user, password, database, buf);
      } else {
         this.protocol.rejectConnection("CLIENT_PLUGIN_AUTH is required");
      }

   }

   private void loadAuthenticationPlugins() {
      this.clientDefaultAuthenticationPlugin = (String)this.propertySet.getStringReadableProperty("defaultAuthenticationPlugin").getValue();
      if (this.clientDefaultAuthenticationPlugin != null && !"".equals(this.clientDefaultAuthenticationPlugin.trim())) {
         String disabledPlugins = (String)this.propertySet.getStringReadableProperty("disabledAuthenticationPlugins").getValue();
         if (disabledPlugins != null && !"".equals(disabledPlugins)) {
            this.disabledAuthenticationPlugins = new ArrayList();
            List<String> pluginsToDisable = StringUtils.split(disabledPlugins, ",", true);
            Iterator iter = pluginsToDisable.iterator();

            while(iter.hasNext()) {
               this.disabledAuthenticationPlugins.add(iter.next());
            }
         }

         this.authenticationPlugins = new HashMap();
         boolean defaultIsFound = false;
         List<AuthenticationPlugin> pluginsToInit = new LinkedList();
         pluginsToInit.add(new MysqlNativePasswordPlugin());
         pluginsToInit.add(new MysqlClearPasswordPlugin());
         pluginsToInit.add(new Sha256PasswordPlugin());
         pluginsToInit.add(new MysqlOldPasswordPlugin());
         String authenticationPluginClasses = (String)this.propertySet.getStringReadableProperty("authenticationPlugins").getValue();
         if (authenticationPluginClasses != null && !"".equals(authenticationPluginClasses)) {
            List<String> pluginsToCreate = StringUtils.split(authenticationPluginClasses, ",", true);
            String className = null;

            try {
               int i = 0;

               for(int s = pluginsToCreate.size(); i < s; ++i) {
                  className = (String)pluginsToCreate.get(i);
                  pluginsToInit.add((AuthenticationPlugin)Class.forName(className).newInstance());
               }
            } catch (Throwable var9) {
               throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("AuthenticationProvider.BadAuthenticationPlugin", new Object[]{className}), var9, this.exceptionInterceptor);
            }
         }

         Iterator var12 = pluginsToInit.iterator();

         while(var12.hasNext()) {
            AuthenticationPlugin plugin = (AuthenticationPlugin)var12.next();
            plugin.init(this.protocol);
            if (this.addAuthenticationPlugin(plugin)) {
               defaultIsFound = true;
            }
         }

         if (!defaultIsFound) {
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("AuthenticationProvider.DefaultAuthenticationPluginIsNotListed", new Object[]{this.clientDefaultAuthenticationPlugin}), this.getExceptionInterceptor());
         }
      } else {
         throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("AuthenticationProvider.BadDefaultAuthenticationPlugin", new Object[]{this.clientDefaultAuthenticationPlugin}), this.getExceptionInterceptor());
      }
   }

   private boolean addAuthenticationPlugin(AuthenticationPlugin plugin) {
      boolean isDefault = false;
      String pluginClassName = plugin.getClass().getName();
      String pluginProtocolName = plugin.getProtocolPluginName();
      boolean disabledByClassName = this.disabledAuthenticationPlugins != null && this.disabledAuthenticationPlugins.contains(pluginClassName);
      boolean disabledByMechanism = this.disabledAuthenticationPlugins != null && this.disabledAuthenticationPlugins.contains(pluginProtocolName);
      if (!disabledByClassName && !disabledByMechanism) {
         this.authenticationPlugins.put(pluginProtocolName, plugin);
         if (this.clientDefaultAuthenticationPlugin.equals(pluginClassName)) {
            this.clientDefaultAuthenticationPluginName = pluginProtocolName;
            isDefault = true;
         }
      } else if (this.clientDefaultAuthenticationPlugin.equals(pluginClassName)) {
         throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("AuthenticationProvider.BadDisabledAuthenticationPlugin", new Object[]{disabledByClassName ? pluginClassName : pluginProtocolName}), this.getExceptionInterceptor());
      }

      return isDefault;
   }

   private AuthenticationPlugin getAuthenticationPlugin(String pluginName) {
      AuthenticationPlugin plugin = (AuthenticationPlugin)this.authenticationPlugins.get(pluginName);
      if (plugin != null && !plugin.isReusable()) {
         try {
            plugin = (AuthenticationPlugin)plugin.getClass().newInstance();
            plugin.init(this.protocol);
         } catch (Throwable var4) {
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("AuthenticationProvider.BadAuthenticationPlugin", new Object[]{plugin.getClass().getName()}), var4, this.getExceptionInterceptor());
         }
      }

      return plugin;
   }

   private void checkConfidentiality(AuthenticationPlugin plugin) {
      if (plugin.requiresConfidentiality() && !this.protocol.getSocketConnection().isSSLEstablished()) {
         throw ExceptionFactory.createException(Messages.getString("AuthenticationProvider.AuthenticationPluginRequiresSSL", new Object[]{plugin.getProtocolPluginName()}), this.getExceptionInterceptor());
      }
   }

   private void proceedHandshakeWithPluggableAuthentication(ServerSession sessState, String user, String password, String database, PacketPayload challenge) {
      if (this.authenticationPlugins == null) {
         this.loadAuthenticationPlugins();
      }

      boolean skipPassword = false;
      int passwordLength = 16;
      int userLength = user != null ? user.length() : 0;
      int databaseLength = database != null ? database.length() : 0;
      int packLength = (userLength + passwordLength + databaseLength) * 3 + 7 + 33;
      long clientParam = sessState.getClientParam();
      int serverCapabilities = sessState.getCapabilities().getCapabilityFlags();
      AuthenticationPlugin plugin = null;
      PacketPayload fromServer = null;
      ArrayList<PacketPayload> toServer = new ArrayList();
      boolean done = false;
      PacketPayload last_sent = null;
      boolean old_raw_challenge = false;
      int counter = 100;

      while(true) {
         String enc;
         label207: {
            if (0 < counter--) {
               if (!done) {
                  if (challenge != null) {
                     if (challenge.isOKPacket()) {
                        throw ExceptionFactory.createException(Messages.getString("AuthenticationProvider.UnexpectedAuthenticationApproval", new Object[]{plugin.getProtocolPluginName()}), this.getExceptionInterceptor());
                     }

                     clientParam |= 958977L;
                     if ((Boolean)this.propertySet.getBooleanReadableProperty("allowMultiQueries").getValue()) {
                        clientParam |= 65536L;
                     }

                     if ((serverCapabilities & 4194304) != 0 && !(Boolean)this.propertySet.getBooleanReadableProperty("disconnectOnExpiredPasswords").getValue()) {
                        clientParam |= 4194304L;
                     }

                     if ((serverCapabilities & 1048576) != 0 && !"none".equals(this.propertySet.getStringReadableProperty("connectionAttributes").getValue())) {
                        clientParam |= 1048576L;
                     }

                     if ((serverCapabilities & 2097152) != 0) {
                        clientParam |= 2097152L;
                     }

                     sessState.setClientParam(clientParam);
                     if ((Boolean)this.propertySet.getBooleanReadableProperty("useSSL").getValue()) {
                        this.negotiateSSLConnection(packLength);
                     }

                     enc = null;
                     if ((serverCapabilities & 524288) != 0) {
                        if (!this.protocol.versionMeetsMinimum(5, 5, 10) || this.protocol.versionMeetsMinimum(5, 6, 0) && !this.protocol.versionMeetsMinimum(5, 6, 2)) {
                           enc = challenge.readString(NativeProtocol.StringLengthDataType.STRING_FIXED, "ASCII", ((MysqlaCapabilities)sessState.getCapabilities()).getAuthPluginDataLength());
                        } else {
                           enc = challenge.readString(NativeProtocol.StringSelfDataType.STRING_TERM, "ASCII");
                        }
                     }

                     plugin = this.getAuthenticationPlugin(enc);
                     if (plugin == null) {
                        plugin = this.getAuthenticationPlugin(this.clientDefaultAuthenticationPluginName);
                     } else if (enc.equals(Sha256PasswordPlugin.PLUGIN_NAME) && !this.protocol.getSocketConnection().isSSLEstablished() && this.propertySet.getStringReadableProperty("serverRSAPublicKeyFile").getValue() == null && !(Boolean)this.propertySet.getBooleanReadableProperty("allowPublicKeyRetrieval").getValue()) {
                        plugin = this.getAuthenticationPlugin(this.clientDefaultAuthenticationPluginName);
                        skipPassword = !this.clientDefaultAuthenticationPluginName.equals(enc);
                     }

                     this.serverDefaultAuthenticationPluginName = plugin.getProtocolPluginName();
                     this.checkConfidentiality(plugin);
                     fromServer = new Buffer(StringUtils.getBytes(this.seed));
                  } else {
                     plugin = this.getAuthenticationPlugin(this.serverDefaultAuthenticationPluginName == null ? this.clientDefaultAuthenticationPluginName : this.serverDefaultAuthenticationPluginName);
                     this.checkConfidentiality(plugin);
                     fromServer = new Buffer(StringUtils.getBytes(this.seed));
                  }
                  break label207;
               }

               challenge = this.protocol.checkErrorPacket();
               old_raw_challenge = false;
               if (plugin == null) {
                  plugin = this.getAuthenticationPlugin(this.serverDefaultAuthenticationPluginName == null ? this.clientDefaultAuthenticationPluginName : this.serverDefaultAuthenticationPluginName);
               }

               if (!challenge.isOKPacket()) {
                  if (challenge.isAuthMethodSwitchRequestPacket()) {
                     skipPassword = false;
                     enc = challenge.readString(NativeProtocol.StringSelfDataType.STRING_TERM, "ASCII");
                     if (!plugin.getProtocolPluginName().equals(enc)) {
                        plugin.destroy();
                        plugin = this.getAuthenticationPlugin(enc);
                        if (plugin == null) {
                           throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("AuthenticationProvider.BadAuthenticationPlugin", new Object[]{enc}), this.getExceptionInterceptor());
                        }
                     }

                     this.checkConfidentiality(plugin);
                     fromServer = new Buffer(StringUtils.getBytes(challenge.readString(NativeProtocol.StringSelfDataType.STRING_TERM, "ASCII")));
                  } else {
                     if (!this.protocol.versionMeetsMinimum(5, 5, 16)) {
                        old_raw_challenge = true;
                        challenge.setPosition(challenge.getPosition() - 1);
                     }

                     fromServer = new Buffer(challenge.readBytes(NativeProtocol.StringSelfDataType.STRING_EOF));
                  }
                  break label207;
               }

               plugin.destroy();
            }

            if (counter == 0) {
               throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("CommunicationsException.TooManyAuthenticationPluginNegotiations"), this.getExceptionInterceptor());
            }

            this.protocol.afterHandshake();
            if (!this.useConnectWithDb) {
               this.protocol.changeDatabase(database);
            }

            return;
         }

         plugin.setAuthenticationParameters(user, skipPassword ? null : password);
         done = plugin.nextAuthenticationStep(fromServer, toServer);
         if (toServer.size() > 0) {
            if (challenge == null) {
               enc = this.getEncodingForHandshake();
               last_sent = new Buffer(packLength + 1);
               last_sent.writeInteger(NativeProtocol.IntegerDataType.INT1, 17L);
               last_sent.writeBytes(NativeProtocol.StringSelfDataType.STRING_TERM, StringUtils.getBytes(user, enc));
               if (((PacketPayload)toServer.get(0)).getPayloadLength() < 256) {
                  last_sent.writeInteger(NativeProtocol.IntegerDataType.INT1, (long)((PacketPayload)toServer.get(0)).getPayloadLength());
                  last_sent.writeBytes(NativeProtocol.StringSelfDataType.STRING_EOF, ((PacketPayload)toServer.get(0)).getByteBuffer());
               } else {
                  last_sent.writeInteger(NativeProtocol.IntegerDataType.INT1, 0L);
               }

               if (this.useConnectWithDb) {
                  last_sent.writeBytes(NativeProtocol.StringSelfDataType.STRING_TERM, StringUtils.getBytes(database, enc));
               } else {
                  last_sent.writeInteger(NativeProtocol.IntegerDataType.INT1, 0L);
               }

               last_sent.writeInteger(NativeProtocol.IntegerDataType.INT1, (long)AuthenticationProvider.getCharsetForHandshake(enc, sessState.getCapabilities().getServerVersion()));
               last_sent.writeInteger(NativeProtocol.IntegerDataType.INT1, 0L);
               if ((serverCapabilities & 524288) != 0) {
                  last_sent.writeBytes(NativeProtocol.StringSelfDataType.STRING_TERM, StringUtils.getBytes(plugin.getProtocolPluginName(), enc));
               }

               if ((clientParam & 1048576L) != 0L) {
                  this.appendConnectionAttributes(last_sent, (String)this.propertySet.getStringReadableProperty("connectionAttributes").getValue(), enc);
               }

               this.protocol.send(last_sent, last_sent.getPosition());
            } else if (challenge.isAuthMethodSwitchRequestPacket()) {
               this.protocol.send((PacketPayload)toServer.get(0), ((PacketPayload)toServer.get(0)).getPayloadLength());
            } else if (!challenge.isAuthMoreData() && !old_raw_challenge) {
               enc = this.getEncodingForHandshake();
               last_sent = new Buffer(packLength);
               last_sent.writeInteger(NativeProtocol.IntegerDataType.INT4, clientParam);
               last_sent.writeInteger(NativeProtocol.IntegerDataType.INT4, 16777215L);
               last_sent.writeInteger(NativeProtocol.IntegerDataType.INT1, (long)AuthenticationProvider.getCharsetForHandshake(enc, sessState.getCapabilities().getServerVersion()));
               last_sent.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, new byte[23]);
               last_sent.writeBytes(NativeProtocol.StringSelfDataType.STRING_TERM, StringUtils.getBytes(user, enc));
               if ((serverCapabilities & 2097152) != 0) {
                  last_sent.writeBytes(NativeProtocol.StringSelfDataType.STRING_LENENC, ((PacketPayload)toServer.get(0)).readBytes(NativeProtocol.StringSelfDataType.STRING_EOF));
               } else {
                  last_sent.writeInteger(NativeProtocol.IntegerDataType.INT1, (long)((PacketPayload)toServer.get(0)).getPayloadLength());
                  last_sent.writeBytes(NativeProtocol.StringSelfDataType.STRING_EOF, ((PacketPayload)toServer.get(0)).getByteBuffer());
               }

               if (this.useConnectWithDb) {
                  last_sent.writeBytes(NativeProtocol.StringSelfDataType.STRING_TERM, StringUtils.getBytes(database, enc));
               } else {
                  last_sent.writeInteger(NativeProtocol.IntegerDataType.INT1, 0L);
               }

               if ((serverCapabilities & 524288) != 0) {
                  last_sent.writeBytes(NativeProtocol.StringSelfDataType.STRING_TERM, StringUtils.getBytes(plugin.getProtocolPluginName(), enc));
               }

               if ((clientParam & 1048576L) != 0L) {
                  this.appendConnectionAttributes(last_sent, (String)this.propertySet.getStringReadableProperty("connectionAttributes").getValue(), enc);
               }

               this.protocol.send(last_sent, last_sent.getPosition());
            } else {
               Iterator var23 = toServer.iterator();

               while(var23.hasNext()) {
                  PacketPayload buffer = (PacketPayload)var23.next();
                  this.protocol.send(buffer, buffer.getPayloadLength());
               }
            }
         }
      }
   }

   private Properties getConnectionAttributesAsProperties(String atts) {
      Properties props = new Properties();
      if (atts != null) {
         String[] pairs = atts.split(",");
         String[] var4 = pairs;
         int var5 = pairs.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String pair = var4[var6];
            int keyEnd = pair.indexOf(":");
            if (keyEnd > 0 && keyEnd + 1 < pair.length()) {
               props.setProperty(pair.substring(0, keyEnd), pair.substring(keyEnd + 1));
            }
         }
      }

      props.setProperty("_client_name", "MySQL Connector Java");
      props.setProperty("_client_version", "6.0.5");
      props.setProperty("_runtime_vendor", Constants.JVM_VENDOR);
      props.setProperty("_runtime_version", Constants.JVM_VERSION);
      props.setProperty("_client_license", "GPL");
      return props;
   }

   private void appendConnectionAttributes(PacketPayload buf, String attributes, String enc) {
      PacketPayload lb = new Buffer(100);
      Properties props = this.getConnectionAttributesAsProperties(attributes);
      Iterator var6 = props.keySet().iterator();

      while(var6.hasNext()) {
         Object key = var6.next();
         lb.writeBytes(NativeProtocol.StringSelfDataType.STRING_LENENC, StringUtils.getBytes((String)key, enc));
         lb.writeBytes(NativeProtocol.StringSelfDataType.STRING_LENENC, StringUtils.getBytes(props.getProperty((String)key), enc));
      }

      buf.writeInteger(NativeProtocol.IntegerDataType.INT_LENENC, (long)lb.getPosition());
      buf.writeBytes((NativeProtocol.StringLengthDataType)NativeProtocol.StringLengthDataType.STRING_FIXED, lb.getByteBuffer(), 0, lb.getPosition());
   }

   public String getEncodingForHandshake() {
      String enc = (String)this.propertySet.getStringReadableProperty("characterEncoding").getValue();
      if (enc == null) {
         enc = "UTF-8";
      }

      return enc;
   }

   public ExceptionInterceptor getExceptionInterceptor() {
      return this.exceptionInterceptor;
   }

   private void negotiateSSLConnection(int packLength) {
      this.protocol.negotiateSSLConnection(packLength);
   }

   public void changeUser(ServerSession serverSession, String userName, String password, String database) {
      this.proceedHandshakeWithPluggableAuthentication(serverSession, userName, password, database, (PacketPayload)null);
   }
}
