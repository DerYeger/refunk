package eu.yeger.refunk.recursive

import eu.yeger.refunk.base.p
import org.junit.Assert.assertEquals
import org.junit.Test

class RecursiveFunctionsTests {

    @Test
    fun tests() {

        //f: N -> N
        //x -> 3x + 6
        val f = multiplyBy(3) andThen add(6)

        assertEquals(42, f.apply(12))

        //------------------------------------------------------------------------------
        //------------------------------------------------------------------------------

        //g: N³ -> N
        //(x,y,z) -> x * y + z
        val g = additionOf { multiplication() and p(2) }

        assertEquals(42, g.apply(2, 12, 18))

        //------------------------------------------------------------------------------
        //------------------------------------------------------------------------------

        //h: N³ -> N
        //(x,y,z) -> x * y * z
        val h = multiplicationOf { multiplication() and p(2) }

        assertEquals(42, h.apply(2, 3, 7))

        //------------------------------------------------------------------------------
        //------------------------------------------------------------------------------

        //i: N² -> N
        //(x,y) -> 2x - y
        val i = subtractionOf { multiplyBy(2) and p(1) }

        assertEquals(42, i.apply(25, 8))

        //------------------------------------------------------------------------------
        //------------------------------------------------------------------------------

        //j: N -> N
        //x -> (2x + 42) - x²
        val j = subtractionOf { multiplyBy(2) andThen add(42) and square() }

        assertEquals(42, j.apply(0))

        //------------------------------------------------------------------------------
        //------------------------------------------------------------------------------

        //k: N -> N
        //x -> min(y | j(y) = 0) + 34; if y <= x
        //x -> 34; else
        val k = boundedMuOperator(j) andThen add(34)

        assertEquals(42, k.apply(8))

        //------------------------------------------------------------------------------
        //------------------------------------------------------------------------------

        //l: N -> N
        //x -> min(y | j(y) = 0) + 42; if y <= x
        //x -> 42; else
        val l = boundedMuOperator(j) andThen add(42)

        assertEquals(42, l.apply(7))

        //------------------------------------------------------------------------------
        //------------------------------------------------------------------------------

        //m: N² -> N
        //(x,y) -> 2 * x / y; if y != 0
        //(x,y) -> 0; else
        val m = floorDivision() andThen multiplyBy(2)

        assertEquals(42, m.apply(84, 4))

        //------------------------------------------------------------------------------
        //------------------------------------------------------------------------------

        //n: N² -> N
        //(x,y) -> 2 * x / y + 42; if y != 0
        //(x,y) -> 42; else
        val n = floorDivision() andThen multiplyBy(2) andThen add(42)

        assertEquals(42, n.apply(10, 0))
    }
}