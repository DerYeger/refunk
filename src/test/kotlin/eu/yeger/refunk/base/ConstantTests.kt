package eu.yeger.refunk.base

import eu.yeger.refunk.exception.NaturalNumberException
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class ConstantTests {

    @Test
    fun testConstant() {
        assertEquals(10, Constant(10)())
        assertEquals(10, Constant(10)(1, 2, 3, 4, 5))
    }

    @Test
    fun testConstantException() {
        try {
            c(-42)
        } catch (e: NaturalNumberException) {
            return
        }
        Assert.fail()
    }
}
