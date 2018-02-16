package org.scalacourse.filesystem.command
import org.scalacourse.filesystem.State
import org.scalacourse.filesystem.file.FsEntry

/**
  * Implements the entries listing (ls) command
  */
class Ls extends Command {
  override def apply(currentState: State): State = {
    val wdContents = currentState.wd.contents

    currentState.setMessage(prettyFormat(wdContents))

  }

  /**
    * Transforms the contents of the directory in a pretty formatted list
    * @param contents a list of entries
    * @return a multi-line string containing each entry and its type
    */
  def prettyFormat(contents: List[FsEntry]): String = {
    if (contents.isEmpty) ""
    else {
      val entry = contents.head
      entry.name + " [" + entry.getType + "]" + "\n" + prettyFormat(contents.tail)
    }
  }
}
