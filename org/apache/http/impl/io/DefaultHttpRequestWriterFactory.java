package org.apache.http.impl.io;

import org.apache.http.HttpRequest;
import org.apache.http.annotation.Immutable;
import org.apache.http.io.HttpMessageWriter;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.message.BasicLineFormatter;
import org.apache.http.message.LineFormatter;

@Immutable
public class DefaultHttpRequestWriterFactory implements HttpMessageWriterFactory<HttpRequest> {
   public static final DefaultHttpRequestWriterFactory INSTANCE = new DefaultHttpRequestWriterFactory();
   private final LineFormatter lineFormatter;

   public DefaultHttpRequestWriterFactory(LineFormatter lineFormatter) {
      this.lineFormatter = (LineFormatter)(lineFormatter != null ? lineFormatter : BasicLineFormatter.INSTANCE);
   }

   public DefaultHttpRequestWriterFactory() {
      this((LineFormatter)null);
   }

   public HttpMessageWriter<HttpRequest> create(SessionOutputBuffer buffer) {
      return new DefaultHttpRequestWriter(buffer, this.lineFormatter);
   }
}
