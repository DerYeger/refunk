package eu.yeger.refunk.base

import eu.yeger.refunk.exception.ArityException
import eu.yeger.refunk.exception.CompositionException
import eu.yeger.refunk.exception.NaturalNumberException
import eu.yeger.refunk.exception.OverflowException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

class FunctionTests {

    @Test
    fun testArgumentArityException() {
        try {
            Projection(0)()
        } catch (e: ArityException) {
            return
        }
        fail<Any>()
    }

    @Test
    fun testApplyException() {
        try {
            Successor()(-1)
        } catch (e: NaturalNumberException) {
            return
        }
        fail<Any>()
    }

    @Test
    fun testTooFewCompositionException() {
        try {
            Successor().compose()
        } catch (e: CompositionException) {
            return
        }
        fail<Any>()
    }

    @Test
    fun testTooManyCompositionException() {
        try {
            Successor().compose(Successor(), Successor())
        } catch (e: CompositionException) {
            return
        }
        fail<Any>()
    }

    @Test
    fun testAndThenException() {
        try {
            Successor() andThen Projection(1)
        } catch (e: CompositionException) {
            return
        }
        fail<Any>()
    }

    @Test
    fun testNegativeReturn() {
        try {
            Successor()(Long.MAX_VALUE)
        } catch (e: OverflowException) {
            return
        }
        fail<Any>()
    }

    @Test
    fun testLazyEvaluation() {
        val failingFunction = object : Function() {
            override val arity = 0

            override fun evaluate(arguments: Array<Argument>): Long {
                fail<Any>()
                return 0
            }
        }

        assertEquals(42, Projection(1).compose(failingFunction, Constant(42), lazy = true)())
    }
}
