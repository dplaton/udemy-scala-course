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
  val LS = "ls"
  val CD = "cd"
  val PWD = "pwd"
  val TOUCH = "touch"

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
    } else if (LS.equals(args(0))) {
      new Ls
    } else if (CD.equals(args(0))) {
        if (args.length < 2) incompleteCommand(args(0))
        else new Cd(args(1))
    } else if (PWD.equals(args(0))) {
        new Pwd
    } else if (TOUCH.equals(args(0))) {
        if (args.length < 2) incompleteCommand(args(0))
        else new Touch(args(1))
    } else if ("showstate".equals(args(0))) {
        //TODO remove this debugging command
        new ShowState
    } else new UnknownCommand
  }
}