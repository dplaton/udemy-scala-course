package org.scalacourse.filesystem.command

import org.scalacourse.filesystem.State

/**
  * Represent a command issued to the fs
  */
trait Command {
  def apply(currentState: State): State
}

object Command {

  val MKDIR = "mkdir"

  def emptyCommand: Command = (currentState: State) => currentState

  def incompleteCommand(name: String): Command = (currentState: State) => currentState.setMessage("Not enough arguments for " + name)

  /**
    * Creates a concrete command object from a command string (name)
    * @param input the name of the command (e.g. mkdir, cat etc)
    * @return the Command object
    */
  def from(input: String): Command = {

    val args: Array[String] = input.split(" ")

    if (input.isEmpty || args.isEmpty) emptyCommand
    else if (MKDIR.equals(args(0))) {
      if (args.length < 2) incompleteCommand(args(0))
      else new Mkdir(args(1))
    } else new UnknownCommand
  }
}