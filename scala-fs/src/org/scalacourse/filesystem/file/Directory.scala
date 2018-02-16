package org.scalacourse.filesystem.file

import org.scalacourse.filesystem.FilesystemException

import scala.annotation.tailrec

/**
  * A directory entry in the filesystem
  *
  * @param parentPath the parent path
  * @param name       the name of the directory
  * @param contents   the contents of a directory, as a list of [[org.scalacourse.filesystem.file.FsEntry]] (could be files or folders)
  */
class Directory(override val parentPath: String, override val name: String, val contents: List[FsEntry])
  extends FsEntry(parentPath, name) {

  /**
    * Finds a descendant of this directory
    *
    * @param path the path relative to this directory (as a list of directories)
    * @return the descendant. If this is not found it returns the current directory
    */
  def findDescendant(path: List[String]): Directory = {
    if (path.isEmpty) this
    else findEntry(path.head).asDirectory.findDescendant(path.tail)
  }

  /**
    * Same as the findDescendant function but using relative paths
    *
    * @param relativePath the relative path (as a String)
    * @return the descendant or the current directory
    */
  def findDescendant(relativePath: String): Directory = {
    if (relativePath.isEmpty) this
    else findDescendant(relativePath.split(Directory.SEPARATOR).toList)
  }

  /**
    * Removes an entry from this directory
    *
    * @param entryName the name of the entry
    * @return the new directory with the entry removed (remember, we're IMMUTABLE)
    */
  def removeEntry(entryName: String): Directory = {
    if (!hasEntry(entryName)) this
    else new Directory(parentPath, name, contents.filter(e => !e.name.equals(entryName)))
  }

  /**
    * Checks if this directory has a specific entry (NOT descendant)
    *
    * @param name the name of the entry
    * @return true if this directory has the entry, false otherwise
    */
  def hasEntry(name: String): Boolean = findEntry(name) != null

  def findEntry(name: String): FsEntry = {

    @tailrec
    def findEntryHelper(name: String, contentList: List[FsEntry]): FsEntry = {
      if (contentList.isEmpty) null
      else if (contentList.head.name.equals(name)) contentList.head
      else findEntryHelper(name, contentList.tail)
    }

    findEntryHelper(name, contents)

  }

  /**
    * Returns a list of parent folders names
    *
    * @return the parent folder names, as a [[List]]
    */
  def allFoldersInPath(): List[String] = {
    path.substring(1).split(Directory.SEPARATOR).toList.filter(x => !x.isEmpty)
  }

  /**
    * Adds an entry to this directory
    * @param newEntry the new entry as a [[FsEntry]]
    * @return the new directory wit the new contents (remember, immutable)
    */
  def addEntry(newEntry: FsEntry): Directory = new Directory(parentPath, name, contents :+ newEntry)

  /**
    * Replaces an entry in this directory with a new one
    * @param entryName the name of the entry to be replaced
    * @param newEntry the new entry, as a [[FsEntry]]
    * @return a new directory instance with the entry updated
    */
  def replaceEntry(entryName: String, newEntry: FsEntry): Directory = new Directory(parentPath, name, contents.filter(e => !e.name.equals(entryName)) :+ newEntry)

  /**
    * Converts a [[FsEntry]] to a [[Directory]] (just like a cast)
    * @return
    */
  override def asDirectory: Directory = this

  /**
    * Returns the type of the entry (Directory, in our case)
    * @return
    */
  override def getType: String = "Directory"

  override def asFile: File = throw new FilesystemException("A directory cannot be converted to a file")

  def isRoot: Boolean = parentPath.isEmpty

  override def isDirectory: Boolean = true

  override def isFile: Boolean = false

}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"
  val PARENT_FOLDER = ".."

  /**
    * Defines the ROOT directory of the filesystem, which is empty
    * @return
    */
  def ROOT: Directory = Directory.empty("", "")

  /**
    * Constructs an empty directory
    * @param parentPath the parent path of the directory
    * @param name the name of the directory
    * @return a new [[Directory]] instance
    */
  def empty(parentPath: String, name: String): Directory = new Directory(parentPath, name, List())
}