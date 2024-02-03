package org.jooq;

import java.util.List;

public interface Results extends List<Result<Record>>, Attachable {
   List<ResultOrRows> resultsOrRows();

   void attach(Configuration var1);

   void detach();
}
