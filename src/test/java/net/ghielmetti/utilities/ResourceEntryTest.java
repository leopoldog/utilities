package net.ghielmetti.utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.spi.FileSystemProvider;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link ResourceEntry} class.
 *
 * @author Leopoldo Ghielmetti
 */
public class ResourceEntryTest {
  private ResourceEntry fileResource;
  private ResourceEntry jarEntryResource;
  private File          file;
  private JarEntry      jarEntry;

  /** Tests {@link ResourceEntry#ResourceEntry(File)}. */
  @Test(expected = NullPointerException.class)
  public void constructor_nullFile_throwsAnException() {
    assertNull(new ResourceEntry((File) null));
  }

  /** Tests {@link ResourceEntry#ResourceEntry(JarEntry)}. */
  @Test(expected = NullPointerException.class)
  public void constructor_nullJarEntry_throwsAnException() {
    assertNull(new ResourceEntry((JarEntry) null));
  }

  /** Tests {@link ResourceEntry#getAttributes()}. */
  @Test
  public void getAttributes_onAFileResource_returnsNull() {
    assertNull(fileResource.getAttributes());
  }

  /**
   * Tests {@link ResourceEntry#getAttributes()}.
   *
   * @throws Exception Not expected.
   */
  @Test
  public void getAttributes_onAJarEntryResource_returnsTheJarEntryAttributes() throws Exception {
    Attributes attr = mock(Attributes.class);
    when(jarEntry.getAttributes()).thenReturn(attr);
    assertEquals(attr, jarEntryResource.getAttributes());
  }

  /** Tests {@link ResourceEntry#getComment()}. */
  @Test
  public void getComment_onAFileResource_returnsNull() {
    assertNull(fileResource.getComment());
  }

  /** Tests {@link ResourceEntry#getComment()}. */
  @Test
  public void getComment_onAJarEntryResource_returns() {
    when(jarEntry.getComment()).thenReturn("A comment");
    assertEquals("A comment", jarEntryResource.getComment());
  }

  /**
   * Tests {@link ResourceEntry#getName()}.
   *
   * @throws Exception Not expected.
   */
  @Test
  public void getName_onAFileAndCanonicalNameHasExceptions_returnsTheFileAbsolutePath() throws Exception {
    doThrow(new IOException("Test")).when(file).getCanonicalPath();
    when(file.getAbsolutePath()).thenReturn("Canonical");
    assertEquals("Canonical", fileResource.getName());
  }

  /**
   * Tests {@link ResourceEntry#getName()}.
   *
   * @throws Exception Not expected.
   */
  @Test
  public void getName_onAFileResource_returnsTheFileCanonicalName() throws Exception {
    when(file.getCanonicalPath()).thenReturn("Canonical");
    assertEquals("Canonical", fileResource.getName());
  }

  /** Tests {@link ResourceEntry#getName()}. */
  @Test
  public void getName_onAJarEntryResource_returnsTheJarEntryName() {
    when(jarEntry.getName()).thenReturn("A name");
    assertEquals("A name", jarEntryResource.getName());
  }

  /** Tests {@link ResourceEntry#getSize()}. */
  @Test
  public void getSize_onAFileResource_returnsTheFileLength() {
    when(Long.valueOf(file.length())).thenReturn(Long.valueOf(1234L));
    assertEquals(1234L, fileResource.getSize());
  }

  /** Tests {@link ResourceEntry#getSize()}. */
  @Test
  public void getSize_onAJarEntryResource_returnsTheEntrySize() {
    when(Long.valueOf(jarEntry.getSize())).thenReturn(Long.valueOf(1234L));
    assertEquals(1234L, jarEntryResource.getSize());
  }

  /**
   * Tests {@link ResourceEntry#getTime()}.
   *
   * @throws Exception Not expected.
   */
  @Test
  public void getTime_onAFileResource_returnTheFileTime() throws Exception {
    @SuppressWarnings("resource")
    FileSystem fileSystem = mock(FileSystem.class);
    FileSystemProvider provider = mock(FileSystemProvider.class);
    BasicFileAttributes attr = mock(BasicFileAttributes.class);
    Path path = mock(Path.class);
    when(provider.readAttributes(eq(path), eq(BasicFileAttributes.class))).thenReturn(attr);
    when(fileSystem.provider()).thenReturn(provider);
    when(file.toPath()).thenReturn(path);
    when(path.getFileSystem()).thenReturn(fileSystem);
    when(attr.lastModifiedTime()).thenReturn(FileTime.fromMillis(1234L));
    assertEquals(1234L, fileResource.getTime());
  }

  /** Tests {@link ResourceEntry#getTime()}. */
  @Test
  public void getTime_onAFileResourceWithAnException_returnMinusOne() {
    assertEquals(-1, fileResource.getTime());
  }

  /** Tests {@link ResourceEntry#getTime()}. */
  @Test
  public void getTime_onAJarEntryResource_returnsTheJarEntryTime() {
    when(Long.valueOf(jarEntry.getTime())).thenReturn(Long.valueOf(1234L));
    assertEquals(1234L, jarEntryResource.getTime());
  }

  /** Tests {@link ResourceEntry#isDirectory()}. */
  @Test
  public void isDirectory_onAFileResource_returnsTrueIfTheFileIsADirectory() {
    when(Boolean.valueOf(file.isDirectory())).thenReturn(Boolean.TRUE);
    assertTrue(fileResource.isDirectory());

    when(Boolean.valueOf(file.isDirectory())).thenReturn(Boolean.FALSE);
    assertFalse(fileResource.isDirectory());
  }

  /** Tests {@link ResourceEntry#isDirectory()}. */
  @Test
  public void isDirectory_onAJarEntryResource_returnsTrueIfTheFileIsADirectory() {
    when(Boolean.valueOf(jarEntry.isDirectory())).thenReturn(Boolean.TRUE);
    assertTrue(jarEntryResource.isDirectory());

    when(Boolean.valueOf(jarEntry.isDirectory())).thenReturn(Boolean.FALSE);
    assertFalse(jarEntryResource.isDirectory());
  }

  /** Initializes the tests. */
  @Before
  public void setUp() {
    file = mock(File.class);
    fileResource = new ResourceEntry(file);

    jarEntry = mock(JarEntry.class);
    jarEntryResource = new ResourceEntry(jarEntry);
  }

  /** Tests {@link ResourceEntry#toString()}. */
  @Test
  public void toString_always_returnsAString() {
    assertNotNull(fileResource.toString());
    assertNotNull(jarEntryResource.toString());
  }
}
