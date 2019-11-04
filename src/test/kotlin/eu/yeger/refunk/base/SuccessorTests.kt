package eu.yeger.refunk.base

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SuccessorTests {

    @Test
    fun testSuccessor() {
        assertEquals(1, Successor()(0))
        assertEquals(42, Successor()(41))
        assertEquals(42, Successor()(41, 10, 20))
    }
}