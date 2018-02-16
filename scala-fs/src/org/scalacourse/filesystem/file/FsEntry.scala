package org.scalacourse.filesystem.file

/**
  * Represents a filesystem entry. Every filesystem entry must have a parent path (even if it's the root path) and a name
  */
abstract class FsEntry(val parentPath: String, val name: String) {
  def path: String = {
    val separator: String = if (Directory.SEPARATOR.equals(parentPath)) "" else Directory.SEPARATOR
    parentPath + separator + name
  }

  def asDirectory: Directory

  def asFile: File

  def getType: String

  def isDirectory: Boolean

  def isFile: Boolean

}
