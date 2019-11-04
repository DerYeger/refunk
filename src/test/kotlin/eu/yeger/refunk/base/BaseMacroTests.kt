package eu.yeger.refunk.base

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BaseMacroTests {

    @Test
    fun testConstantMacro() {
        assertEquals(10, constant(10)())
        assertEquals(10, constant(10)(1, 2, 3, 4, 5))
    }

    @Test
    fun testProjectionMacro() {
        assertEquals(5, projection(0)(5, 10, 20))
        assertEquals(10, projection(1)(5, 10, 20))
        assertEquals(20, projection(2)(5, 10, 20))
    }

    @Test
    fun testProjectionOfMacro() {
        assertEquals(42, projectionOf(1) { constant(10) and constant(42) }(0))
    }

    @Test
    fun testSuccessorMacro() {
        assertEquals(1, successor(0))
        assertEquals(42, successor(41))
        assertEquals(42, successor(41, 10, 20))
    }

    @Test
    fun testSuccessorOfMacro() {
        assertEquals(42, successorOf(constant(41))(0))
    }
}
