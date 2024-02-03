package org.flywaydb.core.internal.util.scanner.classpath.jboss;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.util.UrlUtils;
import org.flywaydb.core.internal.util.scanner.classpath.ClassPathLocationScanner;
import org.jboss.vfs.VFS;
import org.jboss.vfs.VirtualFile;
import org.jboss.vfs.VirtualFileFilter;

public class JBossVFSv3ClassPathLocationScanner implements ClassPathLocationScanner {
   private static final Log LOG = LogFactory.getLog(JBossVFSv3ClassPathLocationScanner.class);

   public Set<String> findResourceNames(String location, URL locationUrl) throws IOException {
      String filePath = UrlUtils.toFilePath(locationUrl);
      String classPathRootOnDisk = filePath.substring(0, filePath.length() - location.length());
      if (!classPathRootOnDisk.endsWith("/")) {
         classPathRootOnDisk = classPathRootOnDisk + "/";
      }

      LOG.debug("Scanning starting at classpath root on JBoss VFS: " + classPathRootOnDisk);
      Set<String> resourceNames = new TreeSet();
      List<VirtualFile> files = VFS.getChild(filePath).getChildrenRecursively(new VirtualFileFilter() {
         public boolean accepts(VirtualFile file) {
            return file.isFile();
         }
      });
      Iterator var7 = files.iterator();

      while(var7.hasNext()) {
         VirtualFile file = (VirtualFile)var7.next();
         resourceNames.add(file.getPathName().substring(classPathRootOnDisk.length()));
      }

      return resourceNames;
   }
}
