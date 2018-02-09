package example

// this is the "definition" the list
// in Java speak: the interface
trait List[+T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T] 
  def add[S >: T](elem: S): List[S]
  def ++[S >: T](other: List[S]): List[S]
  def reverse: List[T]
}

// now, let's define the empty list
object Nil extends List[Nothing] {
  override def isEmpty: Boolean = true
  override def head  = throw new NoSuchElementException
  override def tail = throw new NoSuchElementException
  override def add[S] (elem:S): List[Nothing] = ???
  override def ++[S](other: List[S]): List[S] =  other
  override def reverse: List[Nothing] = Nil

}

// let's build the concrete class now
class Cons[T](val head:T, val tail: List[T]) extends List[T] {
  override def isEmpty: Boolean = false
  override def add[S >: T] (elem:S): List[S] = new Cons(elem, this)
  override def ++[S >: T] (other: List[S]): List[S] = new Cons(head, tail ++ other)
  override def reverse: List[T] = {

    def reverseUtil(input: List[T], accumulator: List[T]):List[T] = {
      if (input.isEmpty) accumulator
      else reverseUtil(input.tail, new Cons(input.head, Nil) ++ accumulator)
    }
    reverseUtil(this, Nil)
  }

  override def toString: String = {
    
    def enumerateAll(list: List[T]): String = {
      if (list.isEmpty) ""
      else if (list.tail.isEmpty) "" + list.head
      else list.head + " " + enumerateAll(tail)
    }

    "[" + enumerateAll(this) + "]"
  }
}

object ListDemo extends App {
 
  val listA = new Cons(4, new Cons(2, Nil))
  val listC = new Cons(1, new Cons(2, new Cons(3, Nil)))

  println("The list A " + listA)
  println("List A reversed " + listA.reverse)
  println("List C " + listC)
  //println("List B: " + (listA ++ new Cons(5, new Cons(6, new Cons(8, Nil)))))

}