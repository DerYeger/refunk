package eu.yeger.refunk.recursive

import eu.yeger.refunk.base.c
import org.junit.Assert.assertEquals
import org.junit.Test

class RecursionTests {

    @Test
    fun testRecursion() {
        assertEquals(10, Recursion(c(10), c(42)).apply(0))
        assertEquals(42, Recursion(c(10), c(42)).apply(1))
    }
}
