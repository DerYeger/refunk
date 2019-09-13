package eu.yeger.prf

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
}