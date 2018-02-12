package org.scalacourse.filesystem.file

class Directory(override val parentPath: String, override val name: String, val contents: List[FsEntry])
  extends FsEntry(parentPath, name) {
  def replaceEntry(name: String, newEntry: Directory): Directory = ???

  def findEntry(head: String): FsEntry = ???

  def findDescendant(dirs: List[String]): Directory = ???

  def getAllFolderNamesInPath(): List[String] = {
    parentPath.substring(1).split(Directory.SEPARATOR).toList
  }

  def hasEntry(name: String): Boolean = ???

  def addEntry(newEntry: FsEntry): FsEntry = ???
}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def ROOT: Directory = Directory.empty("", "")

  def empty(parentPath: String, name: String): Directory = new Directory(parentPath, name, List())
}