package org.jooq;

public interface ResultOrRows {
   Result<Record> result();

   int rows();
}
