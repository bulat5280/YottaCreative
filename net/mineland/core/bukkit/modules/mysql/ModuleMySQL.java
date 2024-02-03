package net.mineland.core.bukkit.modules.mysql;

import com.p6spy.engine.spy.P6DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import net.mineland.core.bukkit.module.BukkitModule;
import org.bukkit.plugin.Plugin;
import org.flywaydb.core.Flyway;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;
import ua.govnojon.libs.config.Config;
import ua.govnojon.libs.myjava.Try;

public class ModuleMySQL extends BukkitModule {
   private static ModuleMySQL instance;
   private ExecutorService service;
   private DSLContext create;
   private HikariDataSource hikariDataSource;
   private String URL;
   private String USER;
   private String PASS;

   public ModuleMySQL(int priority, Plugin plugin) {
      super("mysql", priority, plugin, new Config(plugin, "mysql.yml"));
      instance = this;
   }

   public static ModuleMySQL getInstance() {
      return instance;
   }

   public void onFirstEnable() {
      this.registerListenersThis();
      Schedule.timer(() -> {
         if (this.hikariDataSource == null || this.hikariDataSource.isClosed()) {
            this.getLogger().severe("Соединение с базой пропало, восстанавливаем...");
            this.reload();
         }

      }, 10L, 10L, TimeUnit.SECONDS);
   }

   public void onEnable() {
      Config config = this.getConfig();
      config.setDescription("log-query - логировать ли все sql запросы в консоль");
      config.setIfNotExist("url", "jdbc:mysql://localhost:3306/server");
      config.setIfNotExist("username", "root");
      config.setIfNotExist("password", "");
      this.URL = config.getString("url");
      this.USER = config.getString("username");
      this.PASS = config.getString("password");
      this.initExecutorService();
      this.connectMySQL();
   }

   private void initExecutorService() {
      this.service = Executors.newSingleThreadExecutor();
   }

   private void connectMySQL() {
      HikariConfig config = new HikariConfig();
      config.setJdbcUrl(this.URL);
      config.setUsername(this.USER);
      config.setPassword(this.PASS);
      config.setDriverClassName("com.mysql.jdbc.Driver");
      config.addDataSourceProperty("cachePrepStmts", "true");
      config.addDataSourceProperty("prepStmtCacheSize", "250");
      config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
      config.addDataSourceProperty("characterEncoding", "utf8");
      config.addDataSourceProperty("useUnicode", "true");
      config.addDataSourceProperty("useSSL", "false");
      config.addDataSourceProperty("useJDBCCompliantTimezoneShift", "true");
      config.addDataSourceProperty("useLegacyDatetimeCode", "false");
      config.addDataSourceProperty("serverTimezone", TimeZone.getDefault().getID());

      try {
         this.hikariDataSource = new HikariDataSource(config);
         this.migrateDatabase(this.hikariDataSource);
         boolean logQuery = (Boolean)Try.ignore((Try.SupplierThrows)(() -> {
            return (Boolean)this.getConfig().getOrSet("log-query", true);
         }), (Object)true);
         this.create = DSL.using((DataSource)(logQuery ? new P6DataSource(this.hikariDataSource) : this.hikariDataSource), (SQLDialect)SQLDialect.MYSQL);
      } catch (Exception var3) {
         throw new RuntimeException("СОЕДИНЕНИЕ С БД НЕ УСТАНОВЛЕНО! ПИЗДЕЦ!", var3);
      }
   }

   private void migrateDatabase(HikariDataSource dataSource) {
      Flyway flyway = new Flyway(this.getPlugin().getClass().getClassLoader());
      flyway.setDataSource(dataSource);
      flyway.setBaselineOnMigrate(true);
      flyway.setLocations("classpath:db/migration");
      flyway.migrate();
   }

   public void onDisable() {
      this.getLogger().info("Выполняем оставшиеся запросы...");
      Try.ignore(() -> {
         this.service.shutdown();

         try {
            this.service.awaitTermination(15L, TimeUnit.SECONDS);
         } catch (InterruptedException var2) {
            var2.printStackTrace();
         }

      });
      this.getLogger().info("Отключение сессий с базой данных.");
      Try.ignore(() -> {
         this.hikariDataSource.close();
      });
   }

   public DSLContext getCreate() {
      return this.create;
   }

   public ExecutorService getService() {
      return this.service;
   }
}
