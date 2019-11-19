package eu.yeger.refunk.recursive

import eu.yeger.refunk.base.and
import eu.yeger.refunk.base.constant
import eu.yeger.refunk.base.first
import eu.yeger.refunk.base.second
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RecursiveMacroTests {

    @Test
    fun testAddition() {
        assertEquals(42, addition(20, 22))
    }

    @Test
    fun testAdd() {
        assertEquals(42, add(2)(40))
    }

    @Test
    fun testPredecessor() {
        assertEquals(42, predecessor(43))
        assertEquals(0, predecessor(0))
    }

    @Test
    fun testSubtraction() {
        assertEquals(42, subtraction(50, 8))
        assertEquals(0, subtraction(5, 10))
    }

    @Test
    fun testSubtract() {
        assertEquals(42, subtract(8)(50))
    }

    @Test
    fun testSubtractFrom() {
        assertEquals(42, subtractFrom(50)(8))
    }

    @Test
    fun testNot() {
        assertEquals(1, not(0))
        assertEquals(0, not(1))
        assertEquals(0, not(42))
    }

    @Test
    fun testMultiplication() {
        assertEquals(42, multiplication(2, 21))
    }

    @Test
    fun testMultiplyBy() {
        assertEquals(42, multiplyBy(21)(2))
    }

    @Test
    fun testSquare() {
        assertEquals(81, square(9))
    }

    @Test
    fun testExp() {
        assertEquals(27, exp(3, 3))
    }

    @Test
    fun testCaseDifferentiation() {
        assertEquals(10, caseDifferentiation(second, constant(42), constant(10))(0, 1))
        assertEquals(42, caseDifferentiation(first, constant(42), constant(10))(0, 1))
    }

    @Test
    fun testBoundedMuOperator() {
        assertEquals(2, boundedMuOperator(subtractFrom(2))(2))
        assertEquals(0, boundedMuOperator(subtractFrom(2))(1))
    }

    @Test
    fun testCeilingDivision() {
        assertEquals(0, ceilingDivision(42, 0))
        assertEquals(2, ceilingDivisionOf { constant(4) and first }(2))
        assertEquals(3, ceilingDivisionOf { constant(5) and first }(2))
    }

    @Test
    fun testFloorDivision() {
        assertEquals(0, floorDivision(42, 0))
        assertEquals(2, floorDivisionOf { constant(4) and first }(2))
        assertEquals(2, floorDivisionOf { constant(5) and first }(2))
    }

    @Test
    fun testDivision() {
        assertEquals(0, division(42, 0))
        assertEquals(2, divisionOf { constant(4) and first }(2))
        assertEquals(0, divisionOf { constant(5) and first }(2))
    }

    @Test
    fun testLog() {
        assertEquals(0, log(1)(1))
        assertEquals(0, log(2)(1))
        assertEquals(2, log(2)(4))
        assertEquals(0, log(2)(1))
        assertEquals(0, log(2)(5))
    }
}
