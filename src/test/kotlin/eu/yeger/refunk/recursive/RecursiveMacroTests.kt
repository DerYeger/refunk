package eu.yeger.refunk.recursive

import eu.yeger.refunk.base.*
import org.junit.Assert.assertEquals
import org.junit.Test

class RecursiveMacroTests {

    @Test
    fun testAddition() {
        assertEquals(42, addition().apply(20, 22))
    }

    @Test
    fun testAdd() {
        assertEquals(42, add(2).apply(40))
    }

    @Test
    fun testPredecessor() {
        assertEquals(42, predecessor().apply(43))
        assertEquals(0, predecessor().apply(0))
    }

    @Test
    fun testSubtraction() {
        assertEquals(42, subtraction().apply(50, 8))
        assertEquals(0, subtraction().apply(5, 10))
    }

    @Test
    fun testSubtract() {
        assertEquals(42, subtract(8).apply(50))
    }

    @Test
    fun testSubtractFrom() {
        assertEquals(42, subtractFrom(50).apply(8))
    }

    @Test
    fun testMultiplication() {
        assertEquals(42, multiplication().apply(2, 21))
    }

    @Test
    fun testMultiplyBy() {
        assertEquals(42, multiplyBy(21).apply(2))
    }

    @Test
    fun testSquare() {
        assertEquals(81, square().apply(9))
    }

    @Test
    fun testExp() {
        assertEquals(27, exp().apply(3, 3))
    }

    @Test
    fun testCaseDifferentiation() {
        assertEquals(10, caseDifferentiation(second(), c(42), c(10)).apply(0, 1))
        assertEquals(42, caseDifferentiation(first(), c(42), c(10)).apply(0, 1))
    }

    @Test
    fun testBoundedMuOperator() {
        assertEquals(2, boundedMuOperator(subtractFrom(2)).apply(2))
        assertEquals(0, boundedMuOperator(subtractFrom(2)).apply(1))
    }

    @Test
    fun testCeilingDivision() {
        assertEquals(0, ceilingDivision().apply(42, 0))
        assertEquals(2, ceilingDivisionOf { c(4) and first()}.apply(2))
        assertEquals(3, ceilingDivisionOf { c(5) and first() }.apply(2))
    }

    @Test
    fun testFloorDivision() {
        assertEquals(0, floorDivision().apply(42, 0))
        assertEquals(2, floorDivisionOf { c(4) and first() }.apply(2))
        assertEquals(2, floorDivisionOf { c(5) and first() }.apply(2))
    }

    @Test
    fun testDivision() {
        assertEquals(0, division().apply(42, 0))
        assertEquals(2, divisionOf { c(4) and first() }.apply(2))
        assertEquals(0, divisionOf { c(5) and first() }.apply(2))
    }

    @Test
    fun testLog() {
        assertEquals(0, log(1).apply(1))
        assertEquals(0, log(2).apply(1))
        assertEquals(2, log(2).apply(4))
        assertEquals(0, log(2).apply(1))
        assertEquals(0, log(2).apply(5))
    }
}
