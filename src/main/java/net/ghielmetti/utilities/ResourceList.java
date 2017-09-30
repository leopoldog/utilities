package net.ghielmetti.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * List resources available from the classpath.
 *
 * @author Leopoldo Ghielmetti
 */
public class ResourceList {
  private static final Logger LOG = LoggerFactory.getLogger(ResourceList.class);

  private ResourceList() {
    // nothing to do
  }

  /**
   * for all elements of java.class.path get a Collection of resources Pattern pattern = Pattern.compile(".*"); gets all
   * resources.
   *
   * @param inPattern the pattern to match.
   * @return The resources in the order they are found.
   */
  public static Collection<ResourceEntry> getResources(final Pattern inPattern) {
    Objects.requireNonNull(inPattern);
    ArrayList<ResourceEntry> retval = new ArrayList<>();
    String classPath = System.getProperty("java.class.path", ".");
    String[] classPathElements = classPath.split(System.getProperty("path.separator"));

    for (String element : classPathElements) {
      try {
        retval.addAll(getResources(element, inPattern));
      } catch (Exception e) {
        LOG.error("Unable to search for {}", inPattern.toString(), e);
      }
    }

    return retval;
  }

  private static Collection<ResourceEntry> getResources(final String inElement, final Pattern inPattern) throws IOException {
    ArrayList<ResourceEntry> retval = new ArrayList<>();
    File file = new File(inElement);

    if (file.isDirectory()) {
      retval.addAll(getResourcesFromDirectory(file, inPattern));
    } else {
      retval.addAll(getResourcesFromJarFile(file, inPattern));
    }

    return retval;
  }

  private static Collection<ResourceEntry> getResourcesFromDirectory(final File inDirectory, final Pattern inPattern) throws IOException {
    ArrayList<ResourceEntry> retval = new ArrayList<>();
    File[] fileList = inDirectory.listFiles();

    for (File file : fileList) {
      if (file.isDirectory()) {
        retval.addAll(getResourcesFromDirectory(file, inPattern));
      } else {
        if (inPattern.matcher(file.getCanonicalPath()).matches()) {
          retval.add(new ResourceEntry(file));
        }
      }
    }

    return retval;
  }

  private static Collection<ResourceEntry> getResourcesFromJarFile(final File inFile, final Pattern inPattern) throws IOException {
    try (JarFile jf = new JarFile(inFile)) {
      ArrayList<ResourceEntry> retval = new ArrayList<>();
      Enumeration<? extends JarEntry> e = jf.entries();

      while (e.hasMoreElements()) {
        JarEntry je = e.nextElement();

        if (inPattern.matcher(je.getName()).matches()) {
          retval.add(new ResourceEntry(je));
        }
      }

      return retval;
    }
  }
}