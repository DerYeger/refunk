package eu.yeger.refunk.recursive

import eu.yeger.refunk.base.and
import eu.yeger.refunk.base.constant
import eu.yeger.refunk.base.first
import eu.yeger.refunk.base.second
import eu.yeger.refunk.shouldBe
import org.junit.jupiter.api.Test

class RecursiveMacroTests {

    @Test
    fun testAddition() {
        addition(20, 22) shouldBe 42
    }

    @Test
    fun testAdd() {
        add(2)(40) shouldBe 42
    }

    @Test
    fun testPredecessor() {
        predecessor(43) shouldBe 42
        predecessor(0) shouldBe 0
    }

    @Test
    fun testSubtraction() {
        subtraction(50, 8) shouldBe 42
        subtraction(5, 10) shouldBe 0
    }

    @Test
    fun testSubtract() {
        subtract(8)(50) shouldBe 42
    }

    @Test
    fun testSubtractFrom() {
        subtractFrom(50)(8) shouldBe 42
    }

    @Test
    fun testNot() {
        not(0) shouldBe 1
        not(1) shouldBe 0
        not(42) shouldBe 0
    }

    @Test
    fun testMultiplication() {
        multiplication(2, 21) shouldBe 42
    }

    @Test
    fun testMultiplyBy() {
        multiplyBy(21)(2) shouldBe 42
    }

    @Test
    fun testSquare() {
        square(9) shouldBe 81
    }

    @Test
    fun testExp() {
        exp(3, 3) shouldBe 27
    }

    @Test
    fun testCaseDifferentiation() {
        caseDifferentiation(second, constant(42), constant(10))(0, 1) shouldBe 10
        caseDifferentiation(first, constant(42), constant(10))(0, 1) shouldBe 42
    }

    @Test
    fun testBoundedMuOperator() {
        boundedMuOperator(subtractFrom(2))(2) shouldBe 2
        boundedMuOperator(subtractFrom(2))(1) shouldBe 0
    }

    @Test
    fun testCeilingDivision() {
        ceilingDivision(42, 0) shouldBe 0
        ceilingDivisionOf { constant(4) and first }(2) shouldBe 2
        ceilingDivisionOf { constant(5) and first }(2) shouldBe 3
    }

    @Test
    fun testFloorDivision() {
        floorDivision(42, 0) shouldBe 0
        floorDivisionOf { constant(4) and first }(2) shouldBe 2
        floorDivisionOf { constant(5) and first }(2) shouldBe 2
    }

    @Test
    fun testDivision() {
        division(42, 0) shouldBe 0
        divisionOf { constant(4) and first }(2) shouldBe 2
        divisionOf { constant(5) and first }(2) shouldBe 0
    }

    @Test
    fun testLog() {
        log(1)(1) shouldBe 0
        log(2)(1) shouldBe 0
        log(2)(4) shouldBe 2
        log(2)(1) shouldBe 0
        log(2)(5) shouldBe 0
    }
}
