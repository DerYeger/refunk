package eu.yeger.refunk.recursive

import eu.yeger.refunk.base.Successor
import eu.yeger.refunk.base.andThen
import eu.yeger.refunk.base.c
import eu.yeger.refunk.base.one
import org.junit.Assert.assertEquals
import org.junit.Test

class RecursionTests {

    @Test
    fun testRecursion() {
        assertEquals(10, Recursion(c(10), c(42))(0))
        assertEquals(42, Recursion(c(10), c(42))(1))
    }

    @Test
    fun testRecursions() {
        assertEquals(6, (
                recursive { Successor() andThen Successor() }
                        withBaseCase { one andThen Successor() }
                )(2))
    }
}
