package eu.yeger.refunk.base

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BaseMacroTests {

    @Test
    fun testConstantMacro() {
        assertEquals(10, c(10)())
        assertEquals(10, c(10)(1, 2, 3, 4, 5))
    }

    @Test
    fun testProjectionMacro() {
        assertEquals(5, p(0)(5, 10, 20))
        assertEquals(10, p(1)(5, 10, 20))
        assertEquals(20, p(2)(5, 10, 20))
    }

    @Test
    fun testProjectionOfMacro() {
        assertEquals(42, projectionOf(1) { c(10) and c(42) }(0))
    }

    @Test
    fun testSuccessorMacro() {
        assertEquals(1, successor(0))
        assertEquals(42, successor(41))
        assertEquals(42, successor(41, 10, 20))
    }

    @Test
    fun testSuccessorOfMacro() {
        assertEquals(42, successorOf(c(41))(0))
    }
}
