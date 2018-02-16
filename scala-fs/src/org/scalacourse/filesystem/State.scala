package org.scalacourse.filesystem

import org.scalacourse.filesystem.file.Directory

/**
  * Represents the state of the world
  *
  * @param root   the root folder of our fs
  * @param wd     the working directory
  * @param output the output of the previous command. As the state updates after each command we need the output of the last one so we can update it
  */
class State(val root: Directory, val wd: Directory, val output: String) {

  def show: State = {
    println (output)
    print(State.SHELL_TOKEN)
    this
  }

  def setMessage(msg: String) : State = State(root, wd, msg)

  override def toString: String = {
    "root dir: " + root.path + "\nworking dir: " + wd.name
  }

}

object State {
  val SHELL_TOKEN = "$ "

  def apply(root: Directory, wd: Directory, output: String = ""): State = new State(root, wd, output)
}
