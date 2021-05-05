package eu.yeger.refunk.base

import eu.yeger.refunk.shouldBe
import org.junit.jupiter.api.Test

class CompositionTests {

    @Test
    fun testComposition() {
        (successor andThen successor)(40) shouldBe 42 // increment the argument twice
        successor.compose(projection(1))(0, 1, 2) shouldBe 2 // select the second argument then increment
        successor(successor(successor))(0) shouldBe 3
    }
}
