package net.ghielmetti.utilities;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.LoggerFactory;

/**
 * Read the version number from the pom.properties file.
 *
 * @author Leopoldo Ghielmetti
 */
public final class VersionReader {
  /**
   * A resource entry that allow to query the information of the resources found.<br>
   * This class disallow changes on the backed instances.
   *
   * @author Leopoldo Ghielmetti
   */
  public static class ResourceEntry {
    private final File     file;
    private final JarEntry jarEntry;

    /**
     * Constructor.
     *
     * @param inFile The backed file.
     */
    public ResourceEntry(final File inFile) {
      file = Objects.requireNonNull(inFile, "The file can't be null");
      jarEntry = null;
    }

    /**
     * Constructor.
     *
     * @param inJarEntry The backed jar entry.
     */
    public ResourceEntry(final JarEntry inJarEntry) {
      file = null;
      jarEntry = Objects.requireNonNull(inJarEntry, "The jar entry can't be null");
    }

    /**
     * Returns the <code>Manifest</code> <code>Attributes</code> for this entry, or <code>null</code> if none.
     *
     * @return the <code>Manifest</code> <code>Attributes</code> for this entry, or <code>null</code> if none
     */
    public Attributes getAttributes() {
      try {
        return jarEntry.getAttributes();
      } catch (Exception e) {
        return null;
      }
    }

    /**
     * Returns the comment string for the entry.
     *
     * @return the comment string for the entry, or null if none
     */
    public String getComment() {
      return jarEntry == null ? null : jarEntry.getComment();
    }

    /**
     * Returns the name of the entry.
     *
     * @return the name of the entry
     */
    public String getName() {
      if (file == null) {
        return jarEntry.getName();
      }

      try {
        return file.getCanonicalPath();
      } catch (IOException e) {
        return file.getAbsolutePath();
      }
    }

    /**
     * Returns the uncompressed size of the entry data.
     *
     * @return the uncompressed size of the entry data, or -1 if not known
     */
    public long getSize() {
      return jarEntry == null ? file.length() : jarEntry.getSize();
    }

    /**
     * Returns the last modification time of the entry.
     *
     * @return The last modification time of the entry in milliseconds since the epoch, or -1 if not specified
     */
    public long getTime() {
      if (file != null) {
        try {
          return file.lastModified();
        } catch (Exception e) {
          return -1;
        }
      }

      return jarEntry.getTime();
    }

    /**
     * Returns true if this is a directory entry. A directory entry is defined to be one whose name ends with a '/'.
     *
     * @return true if this is a directory entry
     */
    public boolean isDirectory() {
      return file == null ? jarEntry.isDirectory() : file.isDirectory();
    }

    @Override
    public String toString() {
      return "ResourceEntry[name=\"" + getName() + "\"]";
    }
  }

  /**
   * List resources available from the classpath.
   *
   * @author Leopoldo Ghielmetti
   */
  public static class ResourceList {
    private ResourceList() {
      // nothing to do
    }

    /**
     * For all elements of java.class.path get a Collection of resources Pattern pattern = Pattern.compile(".*"); gets all resources.
     *
     * @param inPattern the pattern to match.
     * @return The resources in the order they are found.
     */
    public static Collection<ResourceEntry> getResources(final Pattern inPattern) {
      Objects.requireNonNull(inPattern, "The pattern can't be null");
      ArrayList<ResourceEntry> retval = new ArrayList<>();
      String classPath = System.getProperty("java.class.path", ".");
      String[] classPathElements = classPath.split(System.getProperty("path.separator"));

      for (String element : classPathElements) {
        try {
          retval.addAll(getResources(element, inPattern));
        } catch (Exception e) {
          LoggerFactory.getLogger(ResourceList.class).error("Unable to search for {}", inPattern.toString(), e);
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

  private static final Map<Pair<String, String>, Pair<String, Long>> infos = new HashMap<>();

  /**
   * Constructor.<br>
   * Gets the pom.properties from the Jar and load the properties.
   */
  static {
    Collection<ResourceEntry> entries = ResourceList.getResources(Pattern.compile(".*META-INF/.*/pom\\.properties"));
    if (entries != null) {
      for (ResourceEntry entry : entries) {
        try (InputStream is = VersionReader.class.getResourceAsStream(entry.getName().replaceFirst(".*META-INF", "/META-INF"))) {
          Properties p = new Properties();
          p.load(is);
          String version = p.getProperty("version");
          String groupId = p.getProperty("groupId");
          String artifactId = p.getProperty("artifactId");
          Pair<String, String> id = Pair.of(groupId, artifactId);
          Pair<String, Long> info = Pair.of(version, Long.valueOf(entry.getTime()));
          infos.put(id, info);
        } catch (Exception e) {
          LoggerFactory.getLogger(VersionReader.class).warn("Unable to read {}", entry, e);
        }
      }
    }
  }

  /** Hidden constructor. */
  private VersionReader() {
  }

  /**
   * Gets all the available packages.
   *
   * @return A set of packages.
   */
  public static Set<Pair<String, String>> getPackages() {
    return Collections.unmodifiableSet(infos.keySet());
  }

  /**
   * Returns the time of the specified package.
   *
   * @param inPackage The package
   * @return The time of the package.
   */
  public static Long getTime(final Pair<String, String> inPackage) {
    Pair<String, Long> info = infos.get(inPackage);
    return info == null ? null : info.getRight();
  }

  /**
   * Returns the version of the specified package.
   *
   * @param inPackage The package
   * @return The version description.
   */
  public static String getVersion(final Pair<String, String> inPackage) {
    Pair<String, Long> info = infos.get(inPackage);
    return info == null ? null : info.getLeft();
  }
}
