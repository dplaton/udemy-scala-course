package org.scalacourse.filesystem.command
import org.scalacourse.filesystem.State

class Cat(name: String) extends Command {
  /**
    * Executes (applies) this command on the filesystem
    *
    * @param currentState the current state of the world
    * @return the new state
    */
  override def apply(currentState: State): State = {
    val entry = currentState.wd.findEntry(name)
    if (entry == null || !entry.isFile) currentState.setMessage(name + ": is a directory")
    else currentState.setMessage(entry.asFile.content)
  }
}
