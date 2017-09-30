package net.ghielmetti.utilities;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.LoggerFactory;

/**
 * Read the version number from the pom.properties file.
 *
 * @author lghi
 */
public class VersionReader {
  private Map<ImmutablePair<String, String>, ImmutablePair<String, Long>> infos = new HashMap<>();

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
          ImmutablePair<String, String> id = ImmutablePair.of(groupId, artifactId);
          ImmutablePair<String, Long> info = ImmutablePair.of(version, Long.valueOf(entry.getTime()));
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
  public Set<ImmutablePair<String, String>> getPackages() {
    return Collections.unmodifiableSet(infos.keySet());
  }

  /**
   * Returns the time of the specified package.
   *
   * @param inPackage The package
   * @return The time of the package.
   */
  public Long getTime(final ImmutablePair<String, String> inPackage) {
    ImmutablePair<String, Long> info = infos.get(inPackage);
    return info == null ? null : info.right;
  }

  /**
   * Returns the version of the specified package.
   *
   * @param inPackage The package
   * @return The version description.
   */
  public String getVersion(final ImmutablePair<String, String> inPackage) {
    ImmutablePair<String, Long> info = infos.get(inPackage);
    return info == null ? null : info.left;
  }

  @Override
  public String toString() {
    return "VersionReader[infos=" + infos + "]";
  }
}
