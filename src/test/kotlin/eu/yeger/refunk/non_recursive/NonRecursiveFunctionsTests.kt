package eu.yeger.refunk.non_recursive

import eu.yeger.refunk.base.and
import eu.yeger.refunk.base.andThen
import eu.yeger.refunk.base.second
import eu.yeger.refunk.base.third
import eu.yeger.refunk.shouldBe
import org.junit.jupiter.api.Test

class NonRecursiveFunctionsTests {

    @Test
    fun tests() {

        // f: N -> N
        // x -> 3x + 6
        val f = multiplyBy(3) andThen add(6)

        f(12) shouldBe 42

        // ------------------------------------------------------------------------------
        // ------------------------------------------------------------------------------

        // g: N³ -> N
        // (x,y,z) -> x * y + z
        val g = additionOf { multiplication and third }

        g(2, 12, 18) shouldBe 42

        // ------------------------------------------------------------------------------
        // ------------------------------------------------------------------------------

        // h: N³ -> N
        // (x,y,z) -> x * y * z
        val h = multiplicationOf { multiplication and third }

        h(2, 3, 7) shouldBe 42

        // ------------------------------------------------------------------------------
        // ------------------------------------------------------------------------------

        // i: N² -> N
        // (x,y) -> 2x - y
        val i = subtractionOf { multiplyBy(2) and second }

        i(25, 8) shouldBe 42

        // ------------------------------------------------------------------------------
        // ------------------------------------------------------------------------------

        // j: N -> N
        // x -> (2x + 42) - x²
        val j = subtractionOf { multiplyBy(2) andThen add(42) and square }

        j(0) shouldBe 42

        // ------------------------------------------------------------------------------
        // ------------------------------------------------------------------------------

        // k: N -> N
        // x -> min(y | j(y) = 0) + 34; if y <= x
        // x -> 34; else
        val k = boundedMuOperator(j) andThen add(34)

        k(8) shouldBe 42

        // ------------------------------------------------------------------------------
        // ------------------------------------------------------------------------------

        // l: N -> N
        // x -> min(y | j(y) = 0) + 42; if y <= x
        // x -> 42; else
        val l = boundedMuOperator(j) andThen add(42)

        l(7) shouldBe 42

        // ------------------------------------------------------------------------------
        // ------------------------------------------------------------------------------

        // m: N² -> N
        // (x,y) -> 2 * x / y; if y != 0
        // (x,y) -> 0; else
        val m = floorDivision andThen multiplyBy(2)

        m(84, 4) shouldBe 42

        // ------------------------------------------------------------------------------
        // ------------------------------------------------------------------------------

        // n: N² -> N
        // (x,y) -> 2 * x / y + 42; if y != 0
        // (x,y) -> 42; else
        val n = floorDivision andThen multiplyBy(2) andThen add(42)

        n(10, 0) shouldBe 42
    }
}
