package com.mysql.cj.api.x;

public interface SqlResult extends Result, RowResult {
   boolean nextResult();
}
