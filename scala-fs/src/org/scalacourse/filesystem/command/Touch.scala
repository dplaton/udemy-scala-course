package org.scalacourse.filesystem.command
import org.scalacourse.filesystem.State
import org.scalacourse.filesystem.file.{File, FsEntry}

class Touch(name: String) extends CreateEntry(name) {
  override def createEntry(state: State): FsEntry = File.empty(state.wd.path,name)
}
