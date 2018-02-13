package org.scalacourse.filesystem.command
import org.scalacourse.filesystem.State

//TODO remove this once we're done debugging
class ShowState extends Command {
  override def apply(currentState: State): State = currentState.setMessage(currentState.toString)
}
