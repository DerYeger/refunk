package eu.yeger.prf

import eu.yeger.prf.Macros.s
import org.junit.Assert
import org.junit.Test

class SuccessorTests {

    @Test
    fun testSuccessor() {
        Assert.assertEquals(1, Successor().apply(0))
        Assert.assertEquals(42, Successor().apply(41))
        Assert.assertEquals(42, Successor().apply(41, 10, 20))
    }

    @Test
    fun testSuccessorMacro() {
        Assert.assertEquals(1, s().apply(0))
        Assert.assertEquals(42, s().apply(41))
        Assert.assertEquals(42, s().apply(41, 10, 20))
    }
}