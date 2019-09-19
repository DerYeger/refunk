package eu.yeger.refunk.base

import eu.yeger.refunk.recursive.Recursion
import org.junit.Assert.assertEquals
import org.junit.Test

class RecursionTests {

    @Test
    fun testRecursion() {
        assertEquals(10, Recursion(c(10), c(42)).apply(0))
        assertEquals(42, Recursion(c(10), c(42)).apply(1))
    }
}
