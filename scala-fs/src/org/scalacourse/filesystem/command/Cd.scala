package org.scalacourse.filesystem.command
import org.scalacourse.filesystem.State
import org.scalacourse.filesystem.file.{Directory, FsEntry}

import scala.annotation.tailrec

/**
  * Created by platon on 12/02/2018.
  */
class Cd(path: String) extends Command {
  override def apply(currentState: State): State = {

    val wd = currentState.wd

    //1. find root
    val root = currentState.root

    //2. find the absolute path of the directory we want to cd into
    val absolutePath =
      if (path.startsWith(Directory.SEPARATOR)) path
      else if (wd.isRoot) wd.path + path
      else wd.path + Directory.SEPARATOR + path

    //3. find the directory to cd to (given the path above)
    val destinationDirectory = doFindEntry(root, absolutePath)

    //4. change the state given the new dir
    if (destinationDirectory == null || !destinationDirectory.isDirectory) currentState.setMessage(path + ": No such directory")
    else State(root, destinationDirectory.asDirectory, "Current directory: " + destinationDirectory.path)
  }

  def doFindEntry(root: Directory, path: String): FsEntry = {

    @tailrec
    def navigate(currentDirectory: Directory, path: List[String]): FsEntry = {
      if (path.isEmpty || path.head.isEmpty) currentDirectory
      else if (path.tail.isEmpty) currentDirectory.findEntry(path.head)
      else {
        val nextDirectory = currentDirectory.findEntry(path.head)
        if (nextDirectory == null || !nextDirectory.isDirectory) null
        else navigate(nextDirectory.asDirectory, path.tail)
      }
    }

    @tailrec
    def collapseRelativeTokes(path: List[String], accumulator: List[String]) : List[String] = {
      if (path.isEmpty) accumulator
      else if (".".equals(path.head)) collapseRelativeTokes(path.tail, accumulator)
      else if ("..".equals(path.head)) {
        if (accumulator.isEmpty) null
        else collapseRelativeTokes(path.tail, accumulator.init) //list.init returns the list without the last element
      }
      else collapseRelativeTokes(path.tail, accumulator :+ path.head)
    }

    //1. Extract the components in the path
    val pathComponents: List[String] = path.substring(1).split(Directory.SEPARATOR).toList

    //1.1 eliminate / collapse relative tokens
    /*

      ["a","."] => ["a"]
      ["a","b",".","."] => ["a", "b"]

      /a/../ => ["a",".."] => [] (.. is the parent of a)
      /a/b/.. => ["a","b",".."] => ["a"]

     */

    val newPathComponents = collapseRelativeTokes(pathComponents, List())
    if (newPathComponents == null) null
    else navigate(root, newPathComponents)


  }
}
