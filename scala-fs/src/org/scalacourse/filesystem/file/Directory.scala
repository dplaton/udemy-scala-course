package org.scalacourse.filesystem.file

import org.scalacourse.filesystem.FilesystemException

import scala.annotation.tailrec

class Directory(override val parentPath: String, override val name: String, val contents: List[FsEntry])
  extends FsEntry(parentPath, name) {


  def findDescendant(path: List[String]): Directory = {
    if (path.isEmpty) this
    else findEntry(path.head).asDirectory.findDescendant(path.tail)
  }

  def findDescendant(relativePath: String): Directory = {
    if (relativePath.isEmpty) this
    else ???
  }

  def removeEntry(entryName: String): Directory = ???

  def getAllFolderNamesInPath(): List[String] = {
    path.substring(1).split(Directory.SEPARATOR).toList.filter(x => !x.isEmpty)
  }

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

  def addEntry(newEntry: FsEntry): Directory = new Directory(parentPath, name, contents :+ newEntry)

  def replaceEntry(entryName: String, newEntry: Directory): Directory = new Directory(parentPath, name, contents.filter(e => !e.name.equals(entryName)) :+ newEntry)

  override def asDirectory: Directory = this

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

  def ROOT: Directory = Directory.empty("", "")

  def empty(parentPath: String, name: String): Directory = new Directory(parentPath, name, List())
}