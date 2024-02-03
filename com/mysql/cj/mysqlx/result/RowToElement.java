package com.mysql.cj.mysqlx.result;

import com.mysql.cj.api.result.Row;
import java.util.function.Function;

public interface RowToElement<EL_T> extends Function<Row, EL_T> {
}
