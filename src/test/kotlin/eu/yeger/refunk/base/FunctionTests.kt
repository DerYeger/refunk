package eu.yeger.refunk.base

import eu.yeger.refunk.exception.ArityException
import eu.yeger.refunk.exception.CompositionException
import eu.yeger.refunk.exception.NaturalNumberException
import eu.yeger.refunk.exception.OverflowException
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class FunctionTests {

    @Test
    fun testArityException() {
        try {
            object : Function() {
                init { arity = -1 }
                override fun evaluate(arguments: Array<Argument>) = 0L
            }
        } catch (e : ArityException) {
            return
        }
        fail()
    }

    @Test
    fun testArgumentArityException() {
        try {
            Projection(0).apply()
        } catch (e : ArityException) {
            return
        }
        fail()
    }

    @Test
    fun testApplyException() {
        try {
            Successor().apply(-1)
        } catch (e : NaturalNumberException) {
            return
        }
        fail()
    }

    @Test
    fun testTooFewCompositionException() {
        try {
            Successor().compose()
        } catch (e : CompositionException) {
            return
        }
        fail()
    }

    @Test
    fun testTooManyCompositionException() {
        try {
            Successor().compose(Successor(), Successor())
        } catch (e : CompositionException) {
            return
        }
        fail()
    }

    @Test
    fun testAndThenException() {
        try {
            Successor() andThen Projection(1)
        } catch (e : CompositionException) {
            return
        }
        fail()
    }

    @Test
    fun testNegativeReturn() {
        try {
            Successor().apply(Long.MAX_VALUE)
        } catch (e : OverflowException) {
            return
        }
        fail()
    }

    @Test
    fun testLazyEvaluation() {
        val failingFunction = object : Function() {
            override fun evaluate(arguments: Array<Argument>): Long {
                fail()
                return 0
            }
        }

        assertEquals(42, Projection(1).compose(failingFunction, Constant(42), lazy = true).apply())
    }
}
