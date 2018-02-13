package org.scalacourse.filesystem.command

import org.scalacourse.filesystem.State
import org.scalacourse.filesystem.file.{Directory, FsEntry}

class Mkdir(name: String) extends CreateEntry(name) {
  override def createEntry(state: State): FsEntry = Directory.empty(state.wd.path, name)
}