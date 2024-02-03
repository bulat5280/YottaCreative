package org.junit.rules;

import java.io.File;
import java.io.IOException;

public class TemporaryFolder extends ExternalResource {
   private File folder;

   protected void before() throws Throwable {
      this.create();
   }

   protected void after() {
      this.delete();
   }

   public void create() throws IOException {
      this.folder = this.newFolder();
   }

   public File newFile(String fileName) throws IOException {
      File file = new File(this.getRoot(), fileName);
      file.createNewFile();
      return file;
   }

   public File newFile() throws IOException {
      return File.createTempFile("junit", (String)null, this.folder);
   }

   public File newFolder(String... folderNames) {
      File file = this.getRoot();
      String[] arr$ = folderNames;
      int len$ = folderNames.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         String folderName = arr$[i$];
         file = new File(file, folderName);
         file.mkdir();
      }

      return file;
   }

   public File newFolder() throws IOException {
      File createdFolder = File.createTempFile("junit", "", this.folder);
      createdFolder.delete();
      createdFolder.mkdir();
      return createdFolder;
   }

   public File getRoot() {
      if (this.folder == null) {
         throw new IllegalStateException("the temporary folder has not yet been created");
      } else {
         return this.folder;
      }
   }

   public void delete() {
      this.recursiveDelete(this.folder);
   }

   private void recursiveDelete(File file) {
      File[] files = file.listFiles();
      if (files != null) {
         File[] arr$ = files;
         int len$ = files.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            File each = arr$[i$];
            this.recursiveDelete(each);
         }
      }

      file.delete();
   }
}
