package org.scalacourse.filesystem.command

import org.scalacourse.filesystem.State

/**
  * Implements the "pwd" command - shows the current working directory
  */
class Pwd extends Command {
  override def apply(currentState: State): State = currentState.setMessage(currentState.wd.path)

}
