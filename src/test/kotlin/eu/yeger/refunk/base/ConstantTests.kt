package eu.yeger.refunk.base

import eu.yeger.refunk.exception.NaturalNumberException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

class ConstantTests {

    @Test
    fun testConstant() {
        assertEquals(10, Constant(10)())
        assertEquals(10, Constant(10)(1, 2, 3, 4, 5))
    }

    @Test
    fun testConstantException() {
        try {
            constant(-42)
        } catch (e: NaturalNumberException) {
            return
        }
        fail<Any>()
    }
}
