package org.scalacourse.filesystem.command
import org.scalacourse.filesystem.State
import org.scalacourse.filesystem.file.{Directory, File}

import scala.annotation.tailrec

class Echo(args: Array[String]) extends Command {
  /**
    * Executes (applies) this command on the filesystem
    *
    * @param currentState the current state of the world
    * @return the new state
    */
  override def apply(currentState: State): State = {

    /*
      if we have one argument - print to console
      if we have multiple arguments - check the next to last one:
          for the > operator to create a file
          for the >> operator to append to a file (or create one)
       no operators - print to console
     */

    if (args.isEmpty) currentState
    else if (args.length == 1) currentState.setMessage(args(0))
    else {
      val operator = args(args.length - 2)
      val filename = args(args.length - 1)
      val contents = createContent(args, args.length - 2)

      if (">>".equals(operator)) doEcho(currentState, contents, filename, append = true)
      else if (">".equals(operator)) doEcho(currentState, contents, filename, append = false)
      else  currentState.setMessage(createContent(args, args.length))
    }

  }

  /**
    * Navigate the whole structure, write to the file and recreate the structure afterwards
    * @param currentDirectory
    * @param path
    * @param content
    * @param append
    * @return
    */
  def rootAfterEcho(currentDirectory: Directory, path: List[String], content: String, append: Boolean): Directory = {
    /*
      path is empty - fail (return current directory)
      if no more elements in path -
        find the file to create (or add content to) to (path.head)
        if file is not found -> create it
        if it's a directory -> fail
        replace (append) content and replace the entry with the NEW file (immutable!!)
      else
        find the next directory to navigate
        rootAfterEcho on the above

      if the call failed -> fail
      else replace entry with the new directory
     */

    if (path.isEmpty) currentDirectory
    else if (path.tail.isEmpty) {
      val entry = currentDirectory.findEntry(path.head)
      if (entry == null)
        // if we don't have a file, create it
        currentDirectory.addEntry(new File(currentDirectory.path, path.head, content))
      else if (!entry.isFile) currentDirectory
        // if we have a file, append or overwrite according the the parameter
      else if (append) currentDirectory.replaceEntry(path.head, entry.asFile.appendContents(content))
        else currentDirectory.replaceEntry(path.head, entry.asFile.setContents(content))
    } else {
      val nextEntry: Directory = currentDirectory.findEntry(path.head).asDirectory
      val newNextEntry: Directory = rootAfterEcho(nextEntry, path.tail, content, append)
      if (newNextEntry == nextEntry) currentDirectory
      else currentDirectory.replaceEntry(path.head, newNextEntry)
    }

  }

  /**
    * Performs the echo command and appends / overwrites a file
    * @param state the state of the world
    * @param content the contents to be written to the file
    * @param filename the filename. We only support simple file names, so no absolute paths or anything
    * @param append the append mode. True to append to a file, false to overwrite the file
    * @return a new state
    */
  def doEcho(state: State, content: String, filename: String, append: Boolean): State = {
    if (filename.contains(Directory.SEPARATOR)) state.setMessage("Echo: file name must not contain separators")
    else {
      val newRoot: Directory = rootAfterEcho(state.wd, state.wd.allFoldersInPath :+ filename, content, append)
      if (newRoot.equals(state.root)) state.setMessage(filename + ": no such file")
      else {
        State(newRoot, newRoot.findDescendant(state.wd.allFoldersInPath))
      }
    }
  }

  /**
    * Concatenates the elements of an array into a single string
    * @param args the array of strings
    * @param topIndex the index up to which the concatenation will occur (non-inclusive)
    * @return the string
    */
  def createContent(args: Array[String], topIndex: Int): String = {

    @tailrec
    def creator(currentIndex: Int, accumulator: String): String = {
      if (currentIndex >= topIndex) accumulator
      else creator(currentIndex+1, accumulator + " " + args(currentIndex))
    }

    creator(0, "")
  }
}
