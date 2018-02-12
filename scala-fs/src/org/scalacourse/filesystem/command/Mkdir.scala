package org.scalacourse.filesystem.command

import org.scalacourse.filesystem.State
import org.scalacourse.filesystem.file.{Directory, FsEntry}

class Mkdir(name: String) extends Command {

  override def apply(currentState: State): State = {

    // the current directory (we'll mkdir here)
    val wd = currentState.wd

    if (wd.hasEntry(name)) currentState.setMessage("A folder with name " + name + " already exists")
    else if (name.contains(Directory.SEPARATOR)) currentState.setMessage("Illegal character " + Directory.SEPARATOR + " found in name")
    else if (!checkLegitName()) currentState.setMessage(name + ": illegal entry name")
    else doMkdir(currentState)
  }

  def checkLegitName(): Boolean = !name.contains(".")

  def doMkdir(state: State): State = {

    def updateStructure(currentDir: Directory, path: List[String], newDir: FsEntry): Directory = {
      if (path.isEmpty) currentDir.addEntry(newDir).asDirectory
      else {
        val oldEntry = currentDir.findEntry(path.head).asDirectory
        currentDir.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newDir))
      }
    }

    val wd = state.wd
    //1. All the directories in the full path
    val dirs = wd.getAllFolderNamesInPath

    //2. Create new directory entry in wd
    val newDir = Directory.empty(wd.path, name)

    //3. update the whole dir structure, starting from the root, because IT IS IMMUTABLE
    val newRoot = updateStructure(state.root, dirs, newDir)

    //4. find the new wd instance
    val newWd = newRoot.findDescendant(dirs)

    //5. update the state
    State(newRoot, newWd)
  }


}
