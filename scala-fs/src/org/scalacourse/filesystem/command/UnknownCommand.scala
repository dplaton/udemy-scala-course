package org.scalacourse.filesystem.command
import org.scalacourse.filesystem.State

/**
  * Implements an unknownd command. This implementation just updates the state with an error message
  */
class UnknownCommand extends Command {
  override def apply(currentState: State): State = currentState.setMessage("Unknown command")
}
