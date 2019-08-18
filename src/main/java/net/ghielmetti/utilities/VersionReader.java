package net.ghielmetti.utilities;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.LoggerFactory;

/**
 * Read the version number from the pom.properties file.
 *
 * @author lghi
 */
public class VersionReader {
  private Map<Pair<String, String>, Pair<String, Long>> infos = new HashMap<>();

  /**
   * Constructor.<br>
   * Gets the pom.properties from the Jar and load the properties.
   */
  public VersionReader() {
    Collection<ResourceEntry> entries = ResourceList.getResources(Pattern.compile(".*META-INF/.*/pom\\.properties"));
    if (entries != null) {
      for (ResourceEntry entry : entries) {
        try (InputStream is = getClass().getResourceAsStream(entry.getName().replaceFirst(".*META-INF", "/META-INF"))) {
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

  /**
   * Gets all the available packages.
   *
   * @return A set of packages.
   */
  public Set<Pair<String, String>> getPackages() {
    return Collections.unmodifiableSet(infos.keySet());
  }

  /**
   * Returns the time of the specified package.
   *
   * @param inPackage The package
   * @return The time of the package.
   */
  public Long getTime(final Pair<String, String> inPackage) {
    Pair<String, Long> info = infos.get(inPackage);
    return info == null ? null : info.getRight();
  }

  /**
   * Returns the version of the specified package.
   *
   * @param inPackage The package
   * @return The version description.
   */
  public String getVersion(final Pair<String, String> inPackage) {
    Pair<String, Long> info = infos.get(inPackage);
    return info == null ? null : info.getLeft();
  }

  @Override
  public String toString() {
    return "VersionReader[infos=" + infos + "]";
  }
}
