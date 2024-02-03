package org.flywaydb.core.internal.util.scanner.classpath.android;

import android.content.res.AssetManager;
import java.io.IOException;
import java.io.InputStreamReader;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.internal.util.FileCopyUtils;
import org.flywaydb.core.internal.util.scanner.LoadableResource;

public class AndroidResource implements LoadableResource {
   private final AssetManager assetManager;
   private final String path;
   private final String name;

   public AndroidResource(AssetManager assetManager, String path, String name) {
      this.assetManager = assetManager;
      this.path = path;
      this.name = name;
   }

   public String getLocation() {
      return this.path + "/" + this.name;
   }

   public String getLocationOnDisk() {
      return null;
   }

   public String loadAsString(String encoding) {
      try {
         return FileCopyUtils.copyToString(new InputStreamReader(this.assetManager.open(this.getLocation()), encoding));
      } catch (IOException var3) {
         throw new FlywayException("Unable to load asset: " + this.getLocation(), var3);
      }
   }

   public byte[] loadAsBytes() {
      try {
         return FileCopyUtils.copyToByteArray(this.assetManager.open(this.getLocation()));
      } catch (IOException var2) {
         throw new FlywayException("Unable to load asset: " + this.getLocation(), var2);
      }
   }

   public String getFilename() {
      return this.name;
   }
}
