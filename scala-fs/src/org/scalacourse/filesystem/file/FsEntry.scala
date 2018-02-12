package org.scalacourse.filesystem.file

/**
  * Created by platon on 11/02/2018.
  */
abstract class FsEntry(val parentPath: String, val name: String ) {
  def path: String = parentPath + Directory.SEPARATOR + name
}
