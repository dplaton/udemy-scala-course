package org.scalacourse.filesystem.file

import org.scalacourse.filesystem.FilesystemException

class File(override val parentPath: String, override val name: String, val contents:String ) extends FsEntry(parentPath, name) {

  override def asDirectory: Directory = throw new FilesystemException("A file cannot be converted to a directory")

  override def asFile: File = this

  override def getType: String = "File"

  override def isDirectory: Boolean = false

  override def isFile: Boolean = true

}

object File {
  /**
    * Constructs an empty (no contents) file
    * @param parentPath the parent path of the file
    * @param name the name of the file
    * @return a File instance
    */
  def empty(parentPath: String, name: String): File = new File(parentPath, name, "")
}