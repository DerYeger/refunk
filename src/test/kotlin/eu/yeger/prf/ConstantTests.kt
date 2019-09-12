package eu.yeger.prf

import eu.yeger.prf.Macros.c
import org.junit.Assert.assertEquals
import org.junit.Test

class ConstantTests {

    @Test
    fun testConstant() {
        assertEquals(10, Constant(10).apply())
        assertEquals(10, Constant(10).value)
    }

    @Test
    fun testConstantMacro() {
        assertEquals(10, c(10).apply())
        assertEquals(10, c(10).value)
    }
}