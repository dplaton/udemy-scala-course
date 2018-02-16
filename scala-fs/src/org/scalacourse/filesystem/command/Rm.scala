package org.scalacourse.filesystem.command
import org.scalacourse.filesystem.State
import org.scalacourse.filesystem.file.{Directory, FsEntry}

/**
  * Created by platon on 15/02/2018.
  */
class Rm(name: String) extends Command {
  override def apply(currentState: State): State = {

    //1. Get working directory
    val wd = currentState.wd

    //2. Get full path of the wd
    val absolutePath: String =
      if (name.startsWith(Directory.SEPARATOR)) name
        else if (wd.isRoot) wd.path + name
        else wd.path + Directory.SEPARATOR + name

    //3. Do some checks
    if (Directory.ROOT_PATH.equals(absolutePath)) currentState.setMessage("Cannot delete the root folder")
    else {
      doRm(absolutePath, currentState)
    }


  }

  def doRm(path: String, state:State) : State = {


    def updateStructure(currentDirectory: Directory, path: List[String]): Directory = {
      if (path.isEmpty) currentDirectory
      else if (path.tail.isEmpty) currentDirectory.removeEntry(path.head)
      else {
        val nextDirectory = currentDirectory.findEntry(path.head)
        if (!nextDirectory.isDirectory) currentDirectory
        else {
          val newNextDirectory: Directory = updateStructure(nextDirectory.asDirectory, path.tail)
          if (newNextDirectory.equals(nextDirectory)) currentDirectory
          else currentDirectory.replaceEntry(path.head, newNextDirectory)
        }
      }
    }

    val pathComponents: List[String] = path.substring(1).split("/").toList

    val newRoot: Directory = updateStructure(state.root, pathComponents)

    if (newRoot.equals(state.root)) state.setMessage(path + ": No such file or directory")
    else State(newRoot, newRoot.findDescendant(state.wd.path.substring(1)))

  }
}
