package eu.yeger.prf

import eu.yeger.prf.Macros.c
import eu.yeger.prf.exception.NaturalNumberException
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class ConstantTests {

    @Test
    fun testConstant() {
        assertEquals(10, Constant(10).apply())
        assertEquals(10, Constant(10).apply(1, 2, 3, 4, 5))
    }

    @Test
    fun testConstantMacro() {
        assertEquals(10, c(10).apply())
        assertEquals(10, c(10).apply(1, 2, 3, 4, 5))
    }

    @Test
    fun testConstantException() {
        try {
            c(-42)
        } catch (e : NaturalNumberException) {
            return
        }
        Assert.fail()
    }
}