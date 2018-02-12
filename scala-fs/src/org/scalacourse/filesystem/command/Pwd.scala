package org.scalacourse.filesystem.command

import org.scalacourse.filesystem.State

class Pwd extends Command {
  override def apply(currentState: State): State = currentState.setMessage(currentState.wd.path)

}
