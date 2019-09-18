package eu.yeger.prf.base

import org.junit.Assert.assertEquals
import org.junit.Test

class CompositionTests {

    @Test
    fun testComposition() {
        assertEquals(42, (s() andThen s()).apply(40)) //increment the argument twice
        assertEquals(2, s().compose(p(1)).apply(0, 1, 2)) //select the second argument then increment
    }
}