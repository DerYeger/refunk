package eu.yeger.prf.base

import org.junit.Assert.assertEquals
import org.junit.Test

class SuccessorTests {

    @Test
    fun testSuccessor() {
        assertEquals(1, Successor().apply(0))
        assertEquals(42, Successor().apply(41))
        assertEquals(42, Successor().apply(41, 10, 20))
    }
}