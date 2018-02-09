package example

object Factorial extends App  {

  def factorial(x: Int, acc: Int = 1): Int = {
    if (x <= 1) acc
    else factorial(x - 1, x * acc)
  }

  println("3! = " + factorial(3))
}
  
