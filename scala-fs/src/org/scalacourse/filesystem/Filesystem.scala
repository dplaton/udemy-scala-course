package org.scalacourse.filesystem

import java.util.Scanner

import org.scalacourse.filesystem.command.Command
import org.scalacourse.filesystem.file.Directory

/**
  * The main class of our app
  */
object Filesystem extends App {

  val rootFolder = Directory.ROOT

  /*
    The elegant / functional way to deal with infinte input
   */
  io.Source.stdin.getLines().foldLeft(State(rootFolder, rootFolder).show)((currentState, newLine) => {
    Command.from(newLine).apply(currentState).show
  })


//  var state = State(rootFolder, rootFolder)
//  val scanner = new Scanner(System.in)
//
//  while(true) {
//    state.show
//    val input = scanner.nextLine()
//    state = Command.from(input).apply(state)
//  }

}
