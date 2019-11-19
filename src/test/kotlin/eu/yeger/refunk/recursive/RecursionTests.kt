package eu.yeger.refunk.recursive

import eu.yeger.refunk.base.constant
import eu.yeger.refunk.base.of
import eu.yeger.refunk.base.one
import eu.yeger.refunk.base.successor
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RecursionTests {

    @Test
    fun testRecursionMacros() {
        assertEquals(0, recursionResult(0, 1, 2, 3, 4))
        assertEquals(1, recursionParameter(0, 1, 2, 3, 4))

        assertEquals(2, firstRecursionArgument(0, 1, 2, 3, 4))
        assertEquals(3, secondRecursionArgument(0, 1, 2, 3, 4))
        assertEquals(4, thirdRecursionArgument(0, 1, 2, 3, 4))

        assertEquals(2, firstBaseCaseArgument(2, 3, 4))
        assertEquals(3, secondBaseCaseArgument(2, 3, 4))
        assertEquals(4, thirdBaseCaseArgument(2, 3, 4))
    }

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
