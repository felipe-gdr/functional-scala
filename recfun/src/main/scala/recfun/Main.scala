package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int =
    if (c == 0 || c == r) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def move(opened: Int, remain: List[Char]): Boolean = {
      if (opened < 0) false
      else if (remain.isEmpty) opened == 0
      
      else {
        def head = remain.head
        def tail = remain.tail

        if (head == '(') move(opened + 1, tail)
        else if (head == ')') move(opened - 1, tail)
        else move(opened, tail)
      }
    }

    move(0, chars)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = ???
}
