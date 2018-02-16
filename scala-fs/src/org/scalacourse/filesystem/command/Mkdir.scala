package org.scalacourse.filesystem.command

import org.scalacourse.filesystem.State
import org.scalacourse.filesystem.file.{Directory, FsEntry}

/**
  * Implements the mkdir command which creates a folder in the current path.
  * This command only supports the creation of a single folder, i.e. we don't support stuff like mkdir a/b/c (full paths)
  * @param name the name of folder to be created
  */
class Mkdir(name: String) extends CreateEntry(name) {
  override def createEntry(state: State): FsEntry = Directory.empty(state.wd.path, name)
}