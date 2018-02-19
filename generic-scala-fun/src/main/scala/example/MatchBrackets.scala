package example

/**
  * A solution for the "Balanced Brackets" problem (description at https://www.hackerrank.com/challenges/balanced-brackets/problem)
  */
object MatchBrackets extends App {

  val opening: String = "([{"
  val closing: String = ")]}"

  println(isBalanced("{[()]}"))
  println(isBalanced("[]{}()"))
  println(isBalanced("[{}]("))
  println(isBalanced("[]][{]"))
  println(isBalanced("[]][{]{(({{)[})(}[[))}{}){[{]}{})()[{}]{{]]]){{}){({(}](({[{[{)]{)}}}({[)}}([{{]]({{"))
  println(isBalanced("}}}}}}"))

  def isBalanced(s: String): String = {

    /*
        We go through the string in a recursive fashion and:
          - if the current element is an opening bracket we add it to the stack and move on
          - if it's closing bracket we check the top of the stack for the corresponding opening
              - if it's not then we just move on with the same stack
              - if it is we remove it from the stack and move on
         At the end we must have both the original list and the stack empty. If the stack is not empty when the list is empty
         it means that some brackets are unmatched.
     */

    def isBalancedHelper(brackets: List[String], stack: List[String]): Boolean = {
      if (brackets.isEmpty) stack.isEmpty
      else if (closing.contains(brackets.head)) {
        if (stack.isEmpty) isBalancedHelper(brackets.tail, stack :+ brackets.head)
        else if (closing.indexOf(brackets.head) == opening.indexOf(stack.last)) isBalancedHelper(brackets.tail, stack.init)
        else isBalancedHelper(brackets.tail, stack)
      }
      else isBalancedHelper(brackets.tail, stack :+ brackets.head)
    }

    val sList = s.split("").toList

    if (closing.contains(sList.head)) "NO"
    else if (isBalancedHelper(sList, List())) "YES"
    else "NO"

  }

}
