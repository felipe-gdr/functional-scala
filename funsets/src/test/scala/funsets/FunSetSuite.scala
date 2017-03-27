package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }

  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains only elements that are in both sets") {
    new TestSets {
      val s = intersect(s1, s2)
      assert(!contains(s, 1), "Intersect 1")
      assert(!contains(s, 2), "Intersect 2")
      assert(!contains(s, 3), "Intersect 3")
    }
  }

  test("diff contains only elements that in the first set and not in the second set") {
    new TestSets {
      val s = diff(s1, s2)
      assert(contains(s, 1), "Diff 1")
      assert(!contains(s, 2), "Diff 2")
      assert(!contains(s, 3), "Diff 3")
    }
  }

  test("filtered set contains only elements accepted by the predicate") {
    new TestSets {
      val p1 = filter(s1, x => x > 1)
      assert(!contains(p1, 1), "Filter 1")

      val p2 = filter(s2, x => x > 1)
      assert(contains(p2, 2), "Filter 2")
    }
  }

  test("forall() tests all elements for compliance to rule function ") {
    new TestSets {
      val oneToThree = union(s3, union(s1, s2))
      
      assert(!forall(oneToThree, x => x % 2 == 0), "Not all are even")
      assert(forall(oneToThree, x => x > 0 && x < 10), "All are greater than zero and smaller than 10") 
      
      val all = union(oneToThree, singletonSet(1000))
      
      assert(!forall(all, x => x > 0 && x < 10), "Not all are greater than zero and smaller than 10")       
    }
  }

  test("exists() tests all elements for compliance to rule function") {
    new TestSets {
      val oneToThree = union(s3, union(s1, s2))
      
      assert(exists(oneToThree, x => x % 2 == 0), "At least one element is even")
      assert(!exists(oneToThree, x => x > 5), "No element is greater than five") 
      
      val all = union(oneToThree, singletonSet(1000))
      
      assert(exists(all, x => x > 5), "At least one element is greater than five")       
    }
  }
  
   test("map() transforms each element in the set") {
    new TestSets {
      val oneToThree = union(s3, union(s1, s2))
      val plusHundred = map(oneToThree, x => x + 100)
            
      assert(contains(oneToThree, 3), "Original set contains the number three")
      assert(!contains(plusHundred, 3), "Transformed set does not contain the number three")
      assert(contains(plusHundred, 103), "Transformed contains the number one-hundred and three")        
    }
  } 
}
