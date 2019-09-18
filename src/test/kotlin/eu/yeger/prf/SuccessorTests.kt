package eu.yeger.prf

import eu.yeger.prf.base.Successor
import eu.yeger.prf.base.s
import org.junit.Assert.assertEquals
import org.junit.Test

class SuccessorTests {

    @Test
    fun testSuccessor() {
        assertEquals(1, Successor().apply(0))
        assertEquals(42, Successor().apply(41))
        assertEquals(42, Successor().apply(41, 10, 20))
    }

    @Test
    fun testSuccessorMacro() {
        assertEquals(1, s().apply(0))
        assertEquals(42, s().apply(41))
        assertEquals(42, s().apply(41, 10, 20))
    }
}