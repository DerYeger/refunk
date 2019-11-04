package eu.yeger.refunk.base

import org.junit.Assert.assertEquals
import org.junit.Test

class SuccessorTests {

    @Test
    fun testSuccessor() {
        assertEquals(1, Successor()(0))
        assertEquals(42, Successor()(41))
        assertEquals(42, Successor()(41, 10, 20))
    }
}