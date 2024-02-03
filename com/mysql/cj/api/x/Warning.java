package com.mysql.cj.api.x;

public interface Warning {
   int getLevel();

   long getCode();

   String getMessage();
}
