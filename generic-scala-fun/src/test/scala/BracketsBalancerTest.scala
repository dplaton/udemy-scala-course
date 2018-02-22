import example.MatchBrackets
import org.scalatest.FlatSpec

class BracketsBalancerTest extends FlatSpec{

  "A brackets balancer" should "check if the brackets are properly balanced" in {
    val matcher = new MatchBrackets

    assert(matcher.isBalanced("{[()]}").equals("YES"))
    assert(matcher.isBalanced("[]{}()").equals("YES"))
    assert(matcher.isBalanced("[{}](").equals("NO"))
    assert(matcher.isBalanced("[]][{]{(({{)[})(}[[))}{}){[{]}{})()[{}]{{]]]){{}){({(}](({[{[{)]{)}}}({[)}}([{{]]({{))").equals("NO"))
    assert(matcher.isBalanced("{{{{").equals("NO"))
  }

}
