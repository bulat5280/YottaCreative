package com.p6spy.engine.common;

public interface Loggable {
   String getSql();

   String getSqlWithValues();

   ConnectionInformation getConnectionInformation();
}
