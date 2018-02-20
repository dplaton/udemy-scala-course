package example

object PatternExamples extends App {

  // pattern examples time!!

  val x = 2

  // simple example using a named "otherwise" branch
  def matchSometing(something: Int) = something match {
    case 2 => "yeap, this is it"
    // you can name your _ branch so you can use the name later
    case smthElse => "Hey, you sent " + smthElse
  }

  println(matchSometing(x))
  println(matchSometing(3))


  println()
  println("---- Pattern matching on lists ---")
  println()

  def matchStructures(aList: List[Any]): String = {
    aList match {
      case List(5, _, _, _) => "A list of four elements, the first is " + aList.head
      case List(2, _*)      => "A list of whatever elements (at least one), but the first is 2"
      case x :: Nil         => "A list with only ONE element: " + x
      case 1 +: _           => "Non empty list, starting with 1"
      case Nil              => "Empty list"
    }
  }

  println(matchStructures(List()))
  println(matchStructures(List(5, 2, 3, 4)))
  println(matchStructures(List(2, 3, 4)))
  println(matchStructures(List(10)))
  println(matchStructures(List(1, 2, 3, 4, 5, 6, 6)))

  def matchByType(something: Any): String = something match {
    case lint: List[Any] => "We have a list: " + lint
    case _: Int  => "We have an int!!"
    case _       => "We have something else"
  }

  println("Match a list by type: " + matchByType(List(2,3)))
  println("Match an int: " + matchByType(3))
  println("Match a string: " + matchByType("bla"))


  println()
  println("---- Exotic stuff ---")
  println()

  val (a, (b,c)) = (1, (2,3))
  println(s"$a,$b,$c")

  val head :: tail = List((1,2),(3,4),(5,6))
  println(s"$head - $tail")
}
