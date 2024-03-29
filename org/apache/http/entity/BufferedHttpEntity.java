package org.apache.http.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.util.Args;

@NotThreadSafe
public class BufferedHttpEntity extends HttpEntityWrapper {
   private final byte[] buffer;

   public BufferedHttpEntity(HttpEntity entity) throws IOException {
      super(entity);
      if (entity.isRepeatable() && entity.getContentLength() >= 0L) {
         this.buffer = null;
      } else {
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         entity.writeTo(out);
         out.flush();
         this.buffer = out.toByteArray();
      }

   }

   public long getContentLength() {
      return this.buffer != null ? (long)this.buffer.length : super.getContentLength();
   }

   public InputStream getContent() throws IOException {
      return (InputStream)(this.buffer != null ? new ByteArrayInputStream(this.buffer) : super.getContent());
   }

   public boolean isChunked() {
      return this.buffer == null && super.isChunked();
   }

   public boolean isRepeatable() {
      return true;
   }

   public void writeTo(OutputStream outstream) throws IOException {
      Args.notNull(outstream, "Output stream");
      if (this.buffer != null) {
         outstream.write(this.buffer);
      } else {
         super.writeTo(outstream);
      }

   }

   public boolean isStreaming() {
      return this.buffer == null && super.isStreaming();
   }
}
