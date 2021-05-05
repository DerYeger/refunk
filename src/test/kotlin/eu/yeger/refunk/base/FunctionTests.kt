package eu.yeger.refunk.base

import eu.yeger.refunk.exception.ArityException
import eu.yeger.refunk.exception.CompositionException
import eu.yeger.refunk.exception.NaturalNumberException
import eu.yeger.refunk.exception.OverflowException
import eu.yeger.refunk.shouldBe
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

class FunctionTests {

    @Test
    fun testArgumentArityException() {
        try {
            Projection(0U)()
        } catch (e: ArityException) {
            return
        }
        fail<Any>()
    }

    @Test
    fun testApplyException() {
        try {
            successor(-1)
        } catch (e: NaturalNumberException) {
            return
        }
        fail<Any>()
    }

    @Test
    fun testTooFewCompositionException() {
        try {
            successor.compose()
        } catch (e: CompositionException) {
            return
        }
        fail<Any>()
    }

    @Test
    fun testTooManyCompositionException() {
        try {
            successor.compose(successor, successor)
        } catch (e: CompositionException) {
            return
        }
        fail<Any>()
    }

    @Test
    fun testAndThenException() {
        try {
            successor andThen Projection(1U)
        } catch (e: CompositionException) {
            return
        }
        fail<Any>()
    }

    @Test
    fun testNegativeReturn() {
        try {
            successor(ULong.MAX_VALUE)
        } catch (e: OverflowException) {
            return
        }
        fail<Any>()
    }

    @Test
    fun testLazyEvaluation() {
        val failingFunction = object : Function() {
            override val arity = 0U

            override fun evaluate(arguments: Array<Argument>): ULong {
                fail<Any>()
                return 0UL
            }
        }

        Projection(1U).compose(failingFunction, constant(42), lazy = true)() shouldBe 42
    }
}
