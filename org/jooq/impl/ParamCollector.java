package org.jooq.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;
import org.jooq.BindContext;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.QueryPartInternal;
import org.jooq.tools.StringUtils;

final class ParamCollector extends AbstractBindContext {
   final Map<String, Param<?>> resultFlat = new LinkedHashMap();
   final Map<String, List<Param<?>>> result = new LinkedHashMap();
   final List<Entry<String, Param<?>>> resultList = new ArrayList();
   private final boolean includeInlinedParams;

   ParamCollector(Configuration configuration, boolean includeInlinedParams) {
      super(configuration, (PreparedStatement)null);
      this.includeInlinedParams = includeInlinedParams;
   }

   protected final void bindInternal(QueryPartInternal internal) {
      if (internal instanceof Param) {
         Param<?> param = (Param)internal;
         if (this.includeInlinedParams || !param.isInline()) {
            String i = String.valueOf(this.nextIndex());
            String paramName = param.getParamName();
            if (StringUtils.isBlank(paramName)) {
               this.resultFlat.put(i, param);
               this.resultList.add(new SimpleImmutableEntry(i, param));
               this.result(i).add(param);
            } else {
               this.resultFlat.put(param.getParamName(), param);
               this.resultList.add(new SimpleImmutableEntry(param.getParamName(), param));
               this.result(param.getParamName()).add(param);
            }
         }
      } else {
         super.bindInternal(internal);
      }

   }

   private final List<Param<?>> result(String key) {
      List<Param<?>> list = (List)this.result.get(key);
      if (list == null) {
         list = new ArrayList();
         this.result.put(key, list);
      }

      return (List)list;
   }

   protected final BindContext bindValue0(Object value, Field<?> field) throws SQLException {
      throw new UnsupportedOperationException();
   }
}
