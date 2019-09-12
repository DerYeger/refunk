package eu.yeger.prf

import eu.yeger.prf.Macros.add
import eu.yeger.prf.Macros.multiplyBy
import eu.yeger.prf.Macros.subtractFrom
import org.junit.Assert.assertEquals
import org.junit.Test

class MacroTests {

    @Test
    fun testAddition() {
        assertEquals(42, add(21).apply(21))
    }

    @Test
    fun testSubtraction() {
        assertEquals(42, subtractFrom(50).apply(8))
    }

    @Test
    fun testMultiplication() {
        assertEquals(42, multiplyBy(21).apply(2))
    }
}
