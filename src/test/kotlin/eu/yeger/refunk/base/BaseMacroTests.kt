package eu.yeger.refunk.base

import org.junit.Assert
import org.junit.Test

class BaseMacroTests {

    @Test
    fun testConstantMacro() {
        Assert.assertEquals(10, c(10).apply())
        Assert.assertEquals(10, c(10).apply(1, 2, 3, 4, 5))
    }

    @Test
    fun testProjectionMacro() {
        Assert.assertEquals(5, p(0).apply(5, 10, 20))
        Assert.assertEquals(10, p(1).apply(5, 10, 20))
        Assert.assertEquals(20, p(2).apply(5, 10, 20))
    }

    @Test
    fun testProjectionOfMacro() {
        Assert.assertEquals(42, projectionOf(1) { c(10) and c(42)}.apply(0))
    }

    @Test
    fun testSuccessorMacro() {
        Assert.assertEquals(1, s().apply(0))
        Assert.assertEquals(42, s().apply(41))
        Assert.assertEquals(42, s().apply(41, 10, 20))
    }

    @Test
    fun testSuccessorOfMacro() {
        Assert.assertEquals(42, successorOf(c(41)).apply(0))
    }
}
