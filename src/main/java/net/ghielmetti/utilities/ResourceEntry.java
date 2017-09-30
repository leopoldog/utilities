package net.ghielmetti.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;

/**
 * A resource entry that allow to query the information of the resources found.<br>
 * This class disallow changes on the backed instances.
 *
 * @author Leopoldo Ghielmetti
 */
public class ResourceEntry {
  private final File     file;
  private final JarEntry jarEntry;

  /**
   * Constructor.
   *
   * @param inFile The backed file.
   */
  public ResourceEntry(final File inFile) {
    file = Objects.requireNonNull(inFile);
    jarEntry = null;
  }

  /**
   * Constructor.
   *
   * @param inJarEntry The backed jar entry.
   */
  public ResourceEntry(final JarEntry inJarEntry) {
    file = null;
    jarEntry = Objects.requireNonNull(inJarEntry);
  }

  /**
   * Returns the <code>Manifest</code> <code>Attributes</code> for this entry, or <code>null</code> if none.
   *
   * @return the <code>Manifest</code> <code>Attributes</code> for this entry, or <code>null</code> if none
   */
  public Attributes getAttributes() {
    try {
      return jarEntry.getAttributes();
    } catch (@SuppressWarnings("unused") Exception e) {
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
    } catch (@SuppressWarnings("unused") IOException e) {
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
        return Files.readAttributes(file.toPath(), BasicFileAttributes.class).lastModifiedTime().toMillis();
      } catch (@SuppressWarnings("unused") Exception e) {
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
