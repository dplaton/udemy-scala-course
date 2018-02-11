package org.scalacourse.filesystem.command
import org.scalacourse.filesystem.State

/**
  * Created by platon on 11/02/2018.
  */
class UnknownCommand extends Command {
  override def apply(currentState: State): State = currentState.setMessage("Unknown command")
}
