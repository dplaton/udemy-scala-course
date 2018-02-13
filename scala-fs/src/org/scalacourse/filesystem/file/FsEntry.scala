package org.scalacourse.filesystem.file

/**
  * Created by platon on 11/02/2018.
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
