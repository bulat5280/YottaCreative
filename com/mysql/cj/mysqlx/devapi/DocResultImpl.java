package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.result.RowList;
import com.mysql.cj.api.x.DocResult;
import com.mysql.cj.core.io.DbDocValueFactory;
import com.mysql.cj.core.io.StatementExecuteOk;
import com.mysql.cj.x.json.DbDoc;
import java.util.function.Supplier;

public class DocResultImpl extends AbstractDataResult<DbDoc> implements DocResult {
   public DocResultImpl(RowList rows, Supplier<StatementExecuteOk> completer) {
      super(rows, completer, (r) -> {
         return (DbDoc)r.getValue(0, new DbDocValueFactory());
      });
      this.rows = rows;
      this.completer = completer;
   }
}
