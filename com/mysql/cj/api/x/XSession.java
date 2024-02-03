package com.mysql.cj.api.x;

public interface XSession extends BaseSession {
   NodeSession bindToDefaultShard();
}
