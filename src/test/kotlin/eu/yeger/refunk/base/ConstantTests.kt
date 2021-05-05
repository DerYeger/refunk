package eu.yeger.refunk.base

import eu.yeger.refunk.exception.NaturalNumberException
import eu.yeger.refunk.shouldBe
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

class ConstantTests {

    @Test
    fun testConstant() {
        constant(10)() shouldBe 10
        constant(10)(1, 2, 3, 4, 5) shouldBe 10
    }

    @Test
    fun testConstantException() {
        try {
            constant(-42)
        } catch (e: NaturalNumberException) {
            return
        }
        fail<Any>()
    }
}
