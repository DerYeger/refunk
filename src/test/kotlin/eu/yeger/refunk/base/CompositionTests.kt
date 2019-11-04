package eu.yeger.refunk.base

import org.junit.Assert.assertEquals
import org.junit.Test

class CompositionTests {

    @Test
    fun testComposition() {
        assertEquals(42, (successor andThen successor)(40)) //increment the argument twice
        assertEquals(2, successor.compose(p(1))(0, 1, 2)) //select the second argument then increment
    }
}
