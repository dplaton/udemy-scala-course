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
  def filter[S >: T] (predicate: Predicate[S]): List[S]
}

// now, let's define the empty list
object Nil extends List[Nothing] {
  override def isEmpty: Boolean = true
  override def head  = throw new NoSuchElementException
  override def tail = throw new NoSuchElementException
  override def add[S] (elem:S): List[S] = new Cons(elem, Nil)
  override def ++[S](other: List[S]): List[S] =  other
  override def reverse: List[Nothing] = Nil
  override def toString: String = ""
  override def filter[S](predicate: Predicate[S]): List[Nothing] = Nil
}

// let's build the concrete class now
class Cons[T](val head:T, val tail: List[T]) extends List[T] {
  override def isEmpty: Boolean = false
  override def add[S >: T] (elem:S): List[S] = new Cons(elem, this)
  override def ++[S >: T] (other: List[S]): List[S] = new Cons(head, tail ++ other)
  override def reverse: List[T] = {

    def reverseUtil(input: List[T], accumulator: List[T]):List[T] = {
      if (input.isEmpty) accumulator
      else reverseUtil(input.tail, new Cons(input.head, accumulator))
    }
    reverseUtil(this, Nil)
  }

  override def toString: String = {
    
    def enumerateAll(list: List[T]): String = {
      if (list.isEmpty) ""
      else if (list.tail.isEmpty) "" + list.head
      else list.head + " " + enumerateAll(list.tail)
    }

    "[" + enumerateAll(this) + "]"
  }

  override def filter[S >: T](predicate: Predicate[S]): List[S] = {
    if (predicate.apply(head)) new Cons(head, tail filter predicate)
    else tail filter predicate
  }
}

// this is a companion object.
object List {
  /** 
   * Flatten a list - return a list from a list of lists
   */
  def flatten[T] (deepList: List[List[T]]) : List[T] = {
    /**
     * Utility function for the tail rec
     * @param remaining - the remainder of the deep list which is unflattened yet
     * @param currentlyExpanding - the list within deeplist which we are currently processing (expanding)
     * @param acc - our good friend, the accumulator. This stores our resulting list
     */
    def flattenUtil(remaining: List[List[T]], currentlyExpanding: List[T], acc: List[T] ) : List[T]= {
      if (currentlyExpanding.isEmpty) {
        // if we don't have anything left to process in "deepList" return the accumulator
        if (remaining.isEmpty) acc
        // if we do have something to process call again with head/ tail
        else flattenUtil(remaining.tail, remaining.head, acc)
      } else flattenUtil(remaining, currentlyExpanding.tail, new Cons(currentlyExpanding.head, acc))
    }

    flattenUtil(deepList, Nil, Nil).reverse
  }

  // below we have a stack recursive implementation, which yields poor performance
  def flattenStackRec[T] (list: List[List[T]]): List[T] = {
    if (list.isEmpty) Nil
    else list.head ++ flatten(list.tail)
  } 
}

trait Predicate[T] {
  def apply(elem: T): Boolean
}


object ListDemo extends App {
 
  val listA = new Cons(4, new Cons(2, Nil))
  val listC = new Cons(1, new Cons(2, new Cons(3, Nil)))

  println("The list A " + listA)
  println("List A reversed " + listA.reverse)
  println("List C " + listC)
  val listB = listA ++ new Cons(5, new Cons(6, new Cons(8, Nil)))
  println("List B: " + listB)
  println("Reversing list B... " + listB.reverse)

  val noneList = Nil
  // this will crash with an exception
  // println(noneList.head)

  val deepList = new Cons(listA, new Cons(listC, new Cons(listB, Nil)))
  println("A deep list: " + deepList)
  val flatList = List.flatten(deepList)
  println("Flattened: " + List.flatten(deepList))

  val filtered = flatList.filter(new Predicate[Int] { 
    override def apply(element: Int) :Boolean = {
      element % 2 != 0
    }
  })
  println("Filtered list: " + filtered)
  
}