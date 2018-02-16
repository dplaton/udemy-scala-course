package org.scalacourse.filesystem.command
import org.scalacourse.filesystem.State
import org.scalacourse.filesystem.file.{File, FsEntry}

/**
  * Implements the file creation (touch) command
  * @param name the name of the entry to be created
  */
class Touch(name: String) extends CreateEntry(name) {
  override def createEntry(state: State): FsEntry = File.empty(state.wd.path,name)
}
