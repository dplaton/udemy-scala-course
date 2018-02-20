package example

object ListOperations extends App {

  println("Small list operation (List(1,2) :: List(3,4)): " + (List(1, 2) :: List(3, 4)))
  println("Small list operation (List(1,2) :+ List(3,4)): " + (List(1, 2) :+ List(3, 4)))
  println("Small list operation (List(1,2) ++ List(3,4)): " + (List(1, 2) ++ List(3, 4)))
  println("Create a list 1::Nil = " + (1 :: Nil))
  println("Create a list 1::(2::Nil) = " + (1 :: (2 :: Nil)))

}
