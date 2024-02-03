package net.mineland.core.bukkit.modules.mysql;

import java.sql.SQLException;
import org.jooq.DSLContext;

public interface SQLFunction<R> {
   R apply(DSLContext var1) throws SQLException;
}
