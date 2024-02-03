package net.mineland.core.bukkit.modules.mysql;

import java.sql.SQLException;
import org.jooq.DSLContext;

public interface SQLConsumer {
   void run(DSLContext var1) throws SQLException;
}
