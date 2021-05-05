package eu.yeger.refunk.recursive

import eu.yeger.refunk.base.constant
import eu.yeger.refunk.base.of
import eu.yeger.refunk.base.one
import eu.yeger.refunk.base.successor
import eu.yeger.refunk.shouldBe
import org.junit.jupiter.api.Test

class RecursionTests {

    @Test
    fun testRecursionMacros() {
        recursionResult(0, 1, 2, 3, 4) shouldBe 0
        recursionParameter(0, 1, 2, 3, 4) shouldBe 1
        firstRecursionArgument(0, 1, 2, 3, 4) shouldBe 2
        secondRecursionArgument(0, 1, 2, 3, 4) shouldBe 3
        thirdRecursionArgument(0, 1, 2, 3, 4) shouldBe 4
        firstBaseCaseArgument(2, 3, 4) shouldBe 2
        secondBaseCaseArgument(2, 3, 4) shouldBe 3
        thirdBaseCaseArgument(2, 3, 4) shouldBe 4
    }

    @Test
    fun testRecursion() {
        Recursion(constant(10), constant(42))(0) shouldBe 10
        Recursion(constant(10), constant(42))(1) shouldBe 42
    }

    @Test
    fun testRecursions() {
        (recursive { successor of successor } withBaseCase { successor of one })(2) shouldBe 6
    }
}
