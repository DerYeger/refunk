package eu.yeger.prf

import eu.yeger.prf.exception.CompositionException
import eu.yeger.prf.exception.NaturalNumberException
import org.junit.Assert.fail
import org.junit.Test

class FunctionTests {

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
}