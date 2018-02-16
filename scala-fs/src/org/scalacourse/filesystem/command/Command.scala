package org.scalacourse.filesystem.command

import org.scalacourse.filesystem.State

/**
  * Represent a command issued to the fs
  */
trait Command {
  /**
    * Executes (applies) this command on the filesystem
    * @param currentState the current state of the world
    * @return the new state
    */
  def apply(currentState: State): State
}

/**
  * Companion object containing various helper functions
  */
object Command {

  // constants, constants constants
  val MKDIR = "mkdir"
  val LS = "ls"
  val CD = "cd"
  val PWD = "pwd"
  val TOUCH = "touch"
  val RM="rm"
  val ECHO = "echo"
  val CAT = "cat"

  /**
    * Updates the state after an empty command has been issued
    * @return the same state of the world since an empty command has no effect
    */
  def emptyCommand: Command = (currentState: State) => currentState

  /**
    * Updates the state after an incomplete command has been issued
    * @param name the name of the command
    * @return the new state with an error message
    */
  def incompleteCommand(name: String): Command = (currentState: State) => currentState.setMessage("Not enough arguments for " + name)

  /**
    * Creates a concrete command object from a command string (name)
    * @param input the name of the command (e.g. mkdir, cat etc)
    * @return the Command object
    */
  def from(input: String): Command = {

    val args: Array[String] = input.split(" ")

    if (input.isEmpty || args.isEmpty) emptyCommand
    else args(0) match {
      case MKDIR => {
        if (args.length < 2) incompleteCommand(args(0))
        else new Mkdir(args(1))
      }
      case LS => new Ls
      case CD => {
        if (args.length < 2) incompleteCommand(args(0))
        else new Cd(args(1))
      }
      case PWD => new Pwd
      case TOUCH => {
        if (args.length < 2) incompleteCommand(args(0))
        else new Touch(args(1))
      }
      case RM => {
        if (args.length < 2) incompleteCommand(args(0))
        else new Rm(args(1))
      }
      case ECHO => {
        if (args.length < 2) incompleteCommand(args(0))
        else new Echo(args.tail)
      }
      case CAT => {
        if (args.length < 2) incompleteCommand(args(0))
        else new Cat(args(1))
      }
      case "showstate" => new ShowState

      case _ => new UnknownCommand
    }

    // the above statement replaces this whole IF statement below:

    /*
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
    } else if(RM.equals(args(0))) {
      if (args.length < 2) incompleteCommand(args(0))
      else new Rm(args(1))
    } else if (ECHO.equals(args(0))) {
      if (args.length < 2) incompleteCommand(args(0))
      else new Echo(args.tail)
    } else if (CAT.equals(args(0))) {
      if (args.length < 2) incompleteCommand(args(0))
      else new Cat(args(1))
    } else new UnknownCommand
    */
  }
}