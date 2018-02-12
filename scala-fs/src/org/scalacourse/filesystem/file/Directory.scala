package org.scalacourse.filesystem.file

import scala.annotation.tailrec

class Directory(override val parentPath: String, override val name: String, val contents: List[FsEntry])
  extends FsEntry(parentPath, name) {


  def findEntry(name: String): FsEntry = {

    @tailrec
    def findEntryHelper(name: String, contentList: List[FsEntry]): FsEntry = {
      if (contentList.isEmpty) null
      else if (contentList.head.name.equals(name)){
        contentList.head
      }
      else findEntryHelper(name, contentList.tail)
    }

    findEntryHelper(name, contents)

  }

  def findDescendant(path: List[String]): Directory = {
    if (path.isEmpty) this
    else findEntry(path.head).asDirectory.findDescendant(path.tail)
  }

  def getAllFolderNamesInPath(): List[String] = {
    path.substring(1).split(Directory.SEPARATOR).toList.filter(x => !x.isEmpty)
  }

  def hasEntry(name: String): Boolean = (findEntry(name) != null)

  def addEntry(newEntry: FsEntry): FsEntry = new Directory(parentPath, name, contents :+ newEntry)

  def replaceEntry(name: String, newEntry: Directory): Directory = new Directory(parentPath, name, contents.filter(e => !e.equals(name)) :+ newEntry)

  override def asDirectory: Directory = this

  override def getType: String = "Directory"

}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"
  val PARENT_FOLDER = ".."

  def ROOT: Directory = Directory.empty("","")

  def empty(parentPath: String, name: String): Directory = new Directory(parentPath, name, List())
}