package org.scalacourse.filesystem.command
import org.scalacourse.filesystem.State
import org.scalacourse.filesystem.file.{Directory, FsEntry}

/**
  * An abstract "create" command which creates elements in our fs (files and folders)
  * @param name the name of the entry to be created
  */
abstract class CreateEntry(name: String) extends Command {

  /**
    * Executes this command against the filesystem
    * @param currentState the current state of the world
    * @return the new state
    */
  override def apply(currentState: State): State = {

    // the current directory (we'll mkdir here)
    val wd = currentState.wd

    // check if the parameters are sane
    if (wd.hasEntry(name)) currentState.setMessage("An entry with name " + name + " already exists")
    else if (name.contains(Directory.SEPARATOR)) currentState.setMessage("Illegal character " + Directory.SEPARATOR + " found in name")
    else if (!checkLegitName()) currentState.setMessage(name + ": illegal entry name")
    else doCreateEntry(currentState)
  }

  /**
    * Checks the name of the folder for illegal characters. Since this is just an exercise, the only restriction we have is that the name should not contain dots
    * @return true if the name is "legal", "false" otherwise
    */
  def checkLegitName(): Boolean = !name.contains(".")

  def doCreateEntry(state: State): State = {

    /**
      * Updates the structure of the directory after a new mkdir command.
      * We need to do this since the structure is IMMUTABLE, i.e. a new structure is created after every command that affects it (mkdir, rm, touch)
      * @param currentDirectory the current directory
      * @param path the full path to the directory
      * @param newEntry the new entry
      * @return the new root of the filesystem, created using the new structure (which includes the new entry)
      */
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

  /**
    * Abstract method to be implemented by the consumers
    * @param state the state of the world
    * @return the new entry
    */
  def createEntry(state: State): FsEntry

}
