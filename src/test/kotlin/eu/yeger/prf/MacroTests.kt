package eu.yeger.prf

import eu.yeger.prf.Macros.add
import eu.yeger.prf.Macros.addition
import eu.yeger.prf.Macros.boundedMuOperator
import eu.yeger.prf.Macros.c
import eu.yeger.prf.Macros.caseDifferentiation
import eu.yeger.prf.Macros.ceilingDivision
import eu.yeger.prf.Macros.division
import eu.yeger.prf.Macros.exp
import eu.yeger.prf.Macros.floorDivision
import eu.yeger.prf.Macros.log
import eu.yeger.prf.Macros.multiplication
import eu.yeger.prf.Macros.multiplyBy
import eu.yeger.prf.Macros.p
import eu.yeger.prf.Macros.predecessor
import eu.yeger.prf.Macros.square
import eu.yeger.prf.Macros.subtract
import eu.yeger.prf.Macros.subtractFrom
import eu.yeger.prf.Macros.subtraction
import org.junit.Assert.assertEquals
import org.junit.Test

class MacroTests {

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
    }

    @Test
    fun testSubtraction() {
        assertEquals(42, subtraction().apply(50, 8))
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
        assertEquals(10, caseDifferentiation(p(1), c(42), c(10)).apply(0, 1))
        assertEquals(42, caseDifferentiation(p(0), c(42), c(10)).apply(0, 1))
    }

//    TODO FIX
//    @Test
//    fun testBoundedMuOperator() {
//        assertEquals(10, boundedMuOperator(predecessor()).apply(3, 2, 1))
//    }

    @Test
    fun testCeilingDivision() {
        assertEquals(2, ceilingDivision().apply(4, 2))
        assertEquals(3, ceilingDivision().apply(5, 2))
    }

    @Test
    fun testFloorDivision() {
        assertEquals(2, floorDivision().apply(4, 2))
        assertEquals(2, floorDivision().apply(5, 2))
    }

    @Test
    fun testDivision() {
        assertEquals(2, division().apply(4, 2))
        assertEquals(0, division().apply(5, 2))
    }

//    TODO FIX java.lang.StackOverflowError
//    @Test
//    fun testLog() {
//        assertEquals(5, log(2).apply(32))
//        assertEquals(0, log(2).apply(5))
//    }

}
