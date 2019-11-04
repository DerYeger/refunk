package eu.yeger.refunk.recursive

import eu.yeger.refunk.base.constant
import eu.yeger.refunk.base.of
import eu.yeger.refunk.base.one
import eu.yeger.refunk.base.successor
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RecursionTests {

    @Test
    fun testRecursion() {
        assertEquals(10, Recursion(constant(10), constant(42))(0))
        assertEquals(42, Recursion(constant(10), constant(42))(1))
    }

    @Test
    fun testRecursions() {
        assertEquals(6, (recursive { successor of successor } withBaseCase { successor of one })(2))
    }
}
