package org.scalacourse.filesystem.command
import org.scalacourse.filesystem.State
import org.scalacourse.filesystem.file.FsEntry

class Ls extends Command {
  override def apply(currentState: State): State = {
    val wdContents = currentState.wd.contents

    currentState.setMessage(prettyFormat(wdContents))

  }

  def prettyFormat(contents: List[FsEntry]): String = {
    if (contents.isEmpty) ""
    else {
      val entry = contents.head
      entry.name + " [" + entry.getType + "]" + "\n" + prettyFormat(contents.tail)
    }
  }
}
