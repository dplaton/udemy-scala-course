package org.scalacourse.filesystem.command
import org.scalacourse.filesystem.State
import org.scalacourse.filesystem.file.{Directory, FsEntry}

abstract class CreateEntry(name: String) extends Command {

  override def apply(currentState: State): State = {

    // the current directory (we'll mkdir here)
    val wd = currentState.wd

    if (wd.hasEntry(name)) currentState.setMessage("An entry with name " + name + " already exists")
    else if (name.contains(Directory.SEPARATOR)) currentState.setMessage("Illegal character " + Directory.SEPARATOR + " found in name")
    else if (!checkLegitName()) currentState.setMessage(name + ": illegal entry name")
    else doCreateEntry(currentState)
  }

  def checkLegitName(): Boolean = !name.contains(".")

  def doCreateEntry(state: State): State = {

    def updateStructure(currentDirectory: Directory, path: List[String], newEntry: FsEntry): Directory = {
      if (path.isEmpty) currentDirectory.addEntry(newEntry)
      else {
        val oldEntry = currentDirectory.findEntry(path.head).asDirectory
        currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))
      }
    }

    val wd = state.wd
    //1. All the directories in the full path
    val dirs = wd.getAllFolderNamesInPath

    //2. Create new directory entry in wd
    val newEntry: FsEntry = createEntry(state)

    //3. update the whole dir structure, starting from the root, because IT IS IMMUTABLE
    val newRoot = updateStructure(state.root, dirs, newEntry)

    //4. find the new wd instance
    val newWd = newRoot.findDescendant(dirs)

    //5. update the state
    State(newRoot, newWd)
  }

  def createEntry(state: State): FsEntry

}
