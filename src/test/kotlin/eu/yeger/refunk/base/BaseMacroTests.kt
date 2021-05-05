package eu.yeger.refunk.base

import eu.yeger.refunk.shouldBe
import org.junit.jupiter.api.Test

class BaseMacroTests {

    @Test
    fun testConstantMacro() {
        constant(10)() shouldBe 10
        constant(10)(1, 2, 3, 4, 5) shouldBe 10
    }

    @Test
    fun testProjectionMacro() {
        projection(0)(5, 10, 20) shouldBe 5
        projection(1)(5, 10, 20) shouldBe 10
        projection(2)(5, 10, 20) shouldBe 20
        first(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) shouldBe 0
        second(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) shouldBe 1
        third(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) shouldBe 2
        fourth(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) shouldBe 3
        fifth(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) shouldBe 4
        sixth(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) shouldBe 5
        seventh(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) shouldBe 6
        eighth(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) shouldBe 7
        ninth(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) shouldBe 8
        tenth(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) shouldBe 9
    }

    @Test
    fun testProjectionOfMacro() {
        projectionOf(1) { constant(10) and constant(42) }(0) shouldBe 42
    }

    @Test
    fun testSuccessorMacro() {
        successor(0) shouldBe 1
        successor(41) shouldBe 42
        successor(41, 10, 20) shouldBe 42
    }

    @Test
    fun testSuccessorOfMacro() {
        successorOf(constant(41))(0) shouldBe 42
    }
}
