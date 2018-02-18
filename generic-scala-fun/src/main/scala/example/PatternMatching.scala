package example

/**
  * This is a case class. It has an apply method built-in. They also implement hashCode and equals automatically
  * Pros:
  * - you can define your domain objects using them
  * - pattern matching is easy (doh, that's why they're called 'case classes'
  * - Collections love them - because of the hashCode and equals
  * - serializable by default
  *
  * BUT:
  * - They have some memory overhead
  * - they cannot be extended by other classes
  *
  */
case class Person(name: String, age: Int) {
  val location: String = "here"
}

object PatternMatching extends App {

  // similar to switch - case construct in Java, but more powerful
  // look:

  def isEven(number: Int): Boolean = {
    /*
     of course this function can be written in a simpler way, but we're trying to show
     the pattern matching here
     */
    number match {
      case n if n % 2 == 0 => true
      // the below branch is the default. _ can be *anything*
      case _               => false
    }
  }

  //...but we can make it BETTER
  def isEvenBetter(number: Any /* anything can be here */): Boolean = {
    number match {
      case n: Int if n % 2 == 0 => /* restrict the matching pattern to INTs */
        println(n)
        true
      case _                   =>
        println("Not an int: " + number)
        false
    }
  }

  println("Is 2 even? " + isEven(2))
  println("Is 12390703 even? " + isEven(12390703))

  println("Is 4 even better? " + isEvenBetter(4))

  println("Is something even better " + isEvenBetter("Something"))

  println("/* -- OK, case classes now -- */")

  // no NEW keyword
  val alice = Person("Alice", 10)
  val anotherAlice = Person("Alice", 10)
  val bob = Person("Bob", 11)

  // constructor arguments automatically promoted to immutable fields
  println("Age of " + alice.name + " is " + alice.age)
  println("Age of " + bob.name + " is " + bob.age)
  // equals and hashCode implemented by default
  println("Are these alices the same? " + (alice == anotherAlice))

  // create new ones via the COPY method
  val anotherBob = bob.copy(name="Another Bob")
  println("A new bob is here, and he is still " + anotherBob.age)
  println("Is " + anotherBob.name + " the same as " + bob.name + "? " + (anotherBob == bob))

  println("Let's tostring it! " + anotherAlice.toString)

  println("/*-- Let's try an actual case now --*/")

  def printAge(person: Person): Unit = person match {
      /*
       the fields from the constructor are automatically decomposed in the case branches.
       However, another fields are not
       */
    case Person(name, _) if name.equals("Bob") =>
      println(s"Look everyone, is $name!!")
    case Person(name, age) if age > 10 =>
      println(s"$name is over 10 years old, he's $age!")
    case Person(name, _) =>
      println(s"$name is younger than 10")
  }

  printAge(alice)
  printAge(anotherBob)
  printAge(bob)
}
