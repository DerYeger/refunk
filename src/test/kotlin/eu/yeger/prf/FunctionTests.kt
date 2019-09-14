package eu.yeger.prf

import eu.yeger.prf.exception.ArityException
import eu.yeger.prf.exception.CompositionException
import eu.yeger.prf.exception.NaturalNumberException
import org.junit.Assert.fail
import org.junit.Test

class FunctionTests {

    @Test
    fun testArityException() {
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
    fun testComposeException() {
        try {
            Successor().compose()
        } catch (e : CompositionException) {
            return
        }
        fail()
    }

    @Test
    fun testAndThenException() {
        try {
            Successor().andThen(p(1))
        } catch (e : CompositionException) {
            return
        }
        fail()
    }

    @Test
    fun testNegative() {
        try {
            Successor().andThen(Successor()).apply(Long.MAX_VALUE)
        } catch (e : NaturalNumberException) {
            return
        }
        fail()
    }

    @Test
    fun testNegativeReturn() {
        try {
            Successor().apply(Long.MAX_VALUE)
        } catch (e : NaturalNumberException) {
            return
        }
        fail()
    }
}