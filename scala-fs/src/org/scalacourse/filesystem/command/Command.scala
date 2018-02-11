package org.scalacourse.filesystem.command

import org.scalacourse.filesystem.State
/**
  * Represent a command issued to the fs
  */
trait Command {
  def apply(currentState : State): State
}

object Command {
  def from(input: String) : Command = new UnknownCommand;
}