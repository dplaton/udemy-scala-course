package example

object Comprehensions extends App {

  // list comprehension:

  val numbers = List(1, 2, 3, 4)

  val powered = for {x <- numbers} yield x * x
  println(powered)

  // the code above is similar to...
  val powered2 = numbers.map(x => x * x)
  println(powered2)

  // ok, time for something really magical
  val res = for {
    x <- List(1, 2, 3) if x % 2 != 0
    y <- List(4, 5, 6)
  } yield x * y

  // x and y are _generators_
  // generators can be filtered, just like we did with x

  println(res) //List(4, 5, 6, 12, 15, 18)

  //let's write it using maps:
  var resWithMap = List(1, 2, 3)
    .withFilter(_ % 2 != 0)
    .flatMap {
      x => List(4, 5, 6).map(y => x * y)
    }
  println(resWithMap) //List(4, 5, 6, 12, 15, 18)

  //let's try to produce side-effects
  //introducing: foreach - map without returning anything
  // instead of List(1,2,3).map(x => println(x)) you can use
  List(1,2,3).foreach(println)
  println("-------")
  //... BUT we can use it with for comprehensions (notice that we don't have the yield keyword since we don't want anything from the for loop)
  for {
    x <- List(1,2,3) if x % 2 != 0
  } println(x)
}

